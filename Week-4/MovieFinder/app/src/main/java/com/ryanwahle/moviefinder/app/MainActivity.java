/*
    Ryan Wahle
    Java 1 - 1405
    Full Sail University
    May 22, 2014
 */

package com.ryanwahle.moviefinder.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ryanwahle.theaterlisting.Helper;
import com.ryanwahle.theaterlisting.Movie;
import com.ryanwahle.theaterlisting.Movies;
import com.ryanwahle.theaterlisting.Showtimes;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {
    String[]    movieNamesList;

    Context     mContext;

    ListView    movieDetailsListView;
    EditText    editTextRequestedDate;
    EditText    editTextZipcode;
    Button      buttonSearch;
    Spinner     movieListSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        // Setup references to the GUI
        movieDetailsListView = (ListView) findViewById(R.id.listViewMovieDetails);
        editTextRequestedDate = (EditText) findViewById(R.id.editTextRequestedDate);
        editTextZipcode = (EditText) findViewById(R.id.editTextZipcode);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        movieListSpinner = (Spinner) findViewById(R.id.spinnerMovieListing);


        if (Helper.isNetworkAvailable(mContext)) {
            Toast.makeText(mContext, "Network Available", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Network Not Available", Toast.LENGTH_LONG).show();
        }

        // Set the date
        editTextRequestedDate.setText(Helper.properlyFormattedCurrentDateForRemoteSource());

        // Setup the onClick for the Search button
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Helper.isNetworkAvailable(mContext)) {
                    showAlert(getString(R.string.alertNoInternetConnection));
                    return;
                }

                String zipcode = String.format("%s", editTextZipcode.getText());
                String searchDate = String.format("%s", editTextRequestedDate.getText());

                // Check to make sure zipcode is in proper format
                if (zipcode.isEmpty() || (zipcode.length() != 5)) {
                    showAlert(getString(R.string.alertInvalidZipcode));
                    return;
                }

                if (searchDate.isEmpty() || (searchDate.length() != 10) || (searchDate.charAt(4) != '-') || (searchDate.charAt(7) != '-')) {
                    showAlert(getString(R.string.alertInvalidDate));
                    return;
                }

                // Close the keyboard if it is up. Found this by searching google.com
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextZipcode.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(editTextRequestedDate.getWindowToken(), 0);

                // Get the JSON Data
                new getData().execute("http://data.tmsapi.com/v1/movies/showings?startDate=" + searchDate + "&zip=" + zipcode + "&api_key=uzufrrp7tnztmej3d37zns85");
            }
        });


    }


    private void showAlert (String alertText) {
        AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
        alertBox.setTitle(getString(R.string.alertTitle));
        alertBox.setMessage(alertText);
        alertBox.setPositiveButton("OK", null);
        alertBox.setCancelable(false);
        alertBox.create().show();
    }

    // Get a list of movies from theaters in a specific zip code
    private void loadMovieList (String jsonDataString) {

        final Movies moviesData = new Movies(mContext, jsonDataString);

        final Movie[] movieList = moviesData.movieList;

        if (moviesData.movieList.length == 0) {
            movieNamesList = new String[1];
            movieNamesList[0] = getString(R.string.NoMoviesFound);
        } else {
            movieNamesList = new String[movieList.length];
        }

        // Populate spinner array with list of movie titles
        for (int index = 0; index < movieList.length; index++) {
            movieNamesList[index] = movieList[index].movie_name;
        }

        ArrayAdapter<String> spinnerAdaptor = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, movieNamesList);
        spinnerAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        movieListSpinner.setAdapter(spinnerAdaptor);

        // When user selects a movie, load up the details and put into ListView
        movieListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (moviesData.movieList.length == 0) {
                    movieDetailsListView.setAdapter(null);
                    return;
                }

                int numberOfShowtimes = 1;
                if (!movieList[position].showtimes.isEmpty()) {
                    numberOfShowtimes = movieList[position].showtimes.size();
                }

                String[] movieDetailsList = new String[2 + numberOfShowtimes];
                movieDetailsList[0] = movieList[position].movie_length;
                movieDetailsList[1] = "Rated - " + movieList[position].rating;

                if (movieList[position].showtimes.isEmpty()) {
                    movieDetailsList[2] = "No Showtimes";
                } else {
                    int movieDetailsListIndex = 2;
                    for (Showtimes showtimeObject : movieList[position].showtimes) {
                        movieDetailsList[movieDetailsListIndex++] = String.format("%s - %s", showtimeObject.theaterName, showtimeObject.movieShowtimes);
                    }
                }

                ArrayAdapter<String> listAdaptor = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, movieDetailsList);
                movieDetailsListView.setAdapter(listAdaptor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }





    private class getData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";

            URL remoteDataURL = null;
            try {
                remoteDataURL = new URL(strings[0]);
                responseString = Helper.getRemoteData(remoteDataURL);
            } catch (MalformedURLException e) {
                responseString = "Something went wrong from getData";
                Log.e("MAIN", "ERROR: " + e);
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.v("PostExecute", "Update the interface");
            loadMovieList(s);
            super.onPostExecute(s);
        }
    }


}


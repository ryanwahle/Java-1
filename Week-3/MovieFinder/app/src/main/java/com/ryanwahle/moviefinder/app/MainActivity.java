/*
    Ryan Wahle
    Java 1 - 1405
    Full Sail University
    May 22, 2014
 */

package com.ryanwahle.moviefinder.app;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.ryanwahle.theaterlisting.Movies;
import com.ryanwahle.theaterlisting.Movie;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends Activity {
    String[]    movieNamesList;
    Button      buttonSearch;
    Spinner     movieListSpinner;
    ListView    movieDetailsListView;
    Context     mContext;
    String      remoteDataLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        if (isNetworkAvailable()) {
            Log.v("MAIN", "Network Available");
            Toast.makeText(mContext, "Network Available", Toast.LENGTH_SHORT).show();

        } else {
            Log.v("MAIN", "Network Unavailable");
            Toast.makeText(mContext, "Network Not Available", Toast.LENGTH_LONG).show();
        }

        // Setup the onClick for the Search button
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextZipcode = (EditText) findViewById(R.id.editTextZipcode);
                String zipcode = editTextZipcode.getText().toString();

                // Close the keyboard if it is up. Found this by searching google.com
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextZipcode.getWindowToken(), 0);

                // Get the JSON Data
                new getData().execute("http://data.tmsapi.com/v1/movies/showings?startDate=2014-05-29&zip=" + zipcode + "&api_key=uzufrrp7tnztmej3d37zns85");

            }
        });


    }

    // Get a list of movies from theaters in a specific zip code
    private void loadMovieList (String jsonDataString) {

        Movies moviesData = new Movies(mContext, jsonDataString);
        //for (Movie movie : moviesData.movieList) {
        //    Log.v("MAIN", "Movie Name: " + movie.movie_name + " -- " + movie.movie_length + " -- " + movie.rating + " -- " + movie.showtimes);
        //}

        final Movie[] movieList = moviesData.movieList;
        movieNamesList = new String[movieList.length];

        // Populate spinner array with list of movie titles
        for (int index = 0; index < movieList.length; index++) {
            movieNamesList[index] = movieList[index].movie_name;
        }

        //for (String movie : movieNamesList) {
        //    Log.v("list name", movie);
        //}


        movieListSpinner = (Spinner) findViewById(R.id.spinnerMovieListing);
        ArrayAdapter<String> spinnerAdaptor = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, movieNamesList);
        spinnerAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        movieListSpinner.setAdapter(spinnerAdaptor);



        // When user selects a movie, load up the details and put into ListView
        movieListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Spinner", "Selected: " + movieList[position].movie_name);
                movieDetailsListView = (ListView) findViewById(R.id.listViewMovieDetails);

                String[] movieDetailsList = new String[4];
                movieDetailsList[0] = "Name: " + movieList[position].movie_name;
                movieDetailsList[1] = "ShowTimes: " + movieList[position].showtimes;
                movieDetailsList[2] = "Length: " + movieList[position].movie_length;
                movieDetailsList[3] = "Rated: " + movieList[position].rating;

                ArrayAdapter<String> listAdaptor = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, movieDetailsList);
                movieDetailsListView.setAdapter(listAdaptor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public boolean isNetworkAvailable ()
    {
        boolean returnCode = false;

        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            if (ni.isConnected()) {
                returnCode = true;
            }
        }

        return returnCode;
    }

    public static String getResponse(URL url) {
        String response = "";
        URLConnection connection = null;

        try {
            connection = url.openConnection();


            BufferedInputStream data = new BufferedInputStream(connection.getInputStream());

            byte[] contentBytes = new byte[1024];
            int bytesRead = 0;
            StringBuffer responseBuffer = new StringBuffer();

            while ((bytesRead = data.read(contentBytes)) != -1) {
                response = new String(contentBytes, 0, bytesRead);
                responseBuffer.append(response);
            }

            response = responseBuffer.toString();
            Log.v("RESPONSE", response);

        } catch (IOException e) {
            response = "Something happened and we didn't get the info";
            Log.e("getResponse", "Something went wrong");
        }

        return response;
    }

    private class getData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";

            URL remoteDataURL = null;
            try {
                remoteDataURL = new URL(strings[0]);
                responseString = getResponse(remoteDataURL);
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


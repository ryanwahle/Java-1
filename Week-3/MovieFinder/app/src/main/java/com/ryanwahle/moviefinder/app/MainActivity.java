/*
    Ryan Wahle
    Java 1 - 1405
    Full Sail University
    May 15, 2014
 */

package com.ryanwahle.moviefinder.app;

import android.app.Activity;
import android.content.Context;
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

import com.ryanwahle.theaterlisting.Movies;
import com.ryanwahle.theaterlisting.Theaters;
import com.ryanwahle.theaterlisting.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {
    String[]    movieNamesList;
    Button      buttonSearch;
    Spinner     movieListSpinner;
    ListView    movieDetailsListView;
    Context     mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Movies moviesData = new Movies(this);

        for (Movie movie : moviesData.movieList) {
            Log.v("MAIN", "Movie Name: " + movie.movie_name);
        }


        //Log.v("Movies", "Length of moviesData.movieList: " + moviesData.movieList.length);

        // Setup the onClick for the Search button
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextZipcode = (EditText) findViewById(R.id.editTextZipcode);

                // Close the keyboard if it is up. Found this by searching google.com
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextZipcode.getWindowToken(), 0);

                // Load the spinner
                loadMovieList(editTextZipcode.getText().toString());
            }
        });


    }

    // Get a list of movies from theaters in a specific zip code
    private void loadMovieList (String zipcode) {
        final Movie[] movieList = Theaters.getTheaterList(zipcode);
        movieNamesList = new String[movieList.length];

        // Populate spinner array with list of movie titles
        for (int index = 0; index < movieList.length; index++) {
            movieNamesList[index] = movieList[index].movie_name;
        }


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

                String[] movieDetailsList = new String[6];
                movieDetailsList[0] = "Name: " + movieList[position].movie_name;
                movieDetailsList[1] = "Date: " + movieList[position].showtime_date;
                movieDetailsList[2] = "Times: " + movieList[position].showtimes;
                movieDetailsList[3] = "Theater: " + movieList[position].theater_name;
                movieDetailsList[4] = "Length: " + movieList[position].length_in_minutes + " minutes";
                //movieDetailsList[5] = "Rated: " + movieList[position].rating;

                ArrayAdapter<String> listAdaptor = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, movieDetailsList);
                movieDetailsListView.setAdapter(listAdaptor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}

/*
    Ryan Wahle
    Java 1 - 1405
    Full Sail University
    May 15, 2014
 */

package com.ryanwahle.theaterlisting;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ryanwahle on 5/14/14.
 */

// Static Class that gets a list of movies from a specific zipcode
public class Theaters {
    public static Movie[] getTheaterList(String zipcode) {
        // Grab the JSON Data from remote source
        String theaterJSONData = getTheaterJSONData(zipcode);
        Movie[] movieObjects = new Movie[0];

        // Parse the JSON and create Movie objects from the data
        try {
            JSONObject theaterJSONObject = new JSONObject(theaterJSONData);
            Log.e("JSON Parse", "Theater Name: " + theaterJSONObject.getString("theater_name"));

            JSONArray movieListJSONArray = theaterJSONObject.getJSONArray("movies");
            Integer numberOfMovies = movieListJSONArray.length();

            movieObjects = new Movie[numberOfMovies];
            for (int index = 0; index < numberOfMovies; index++) {
                movieObjects[index] = new Movie();
                movieObjects[index].movie_name = movieListJSONArray.getJSONObject(index).getString("movie_name");
                movieObjects[index].showtime_date = movieListJSONArray.getJSONObject(index).getString("showtime_date");
                movieObjects[index].showtimes = movieListJSONArray.getJSONObject(index).getString("showtimes");
                movieObjects[index].theater_name = movieListJSONArray.getJSONObject(index).getString("theater_name");
                movieObjects[index].length_in_minutes = movieListJSONArray.getJSONObject(index).getInt("length_in_minutes");
                movieObjects[index].rating = Rating.R;
            }



        } catch (JSONException e) {
            Log.e("Theaters", "Error parsing JSON Data");
            e.printStackTrace();
        }

        return movieObjects;
    }

    // This will be the method that retrieves the data from the remote source.
    // Since we do not have a remote source yet, this function creates fake JSON data.
    private static String getTheaterJSONData(String zipcode) {

        JSONObject theaterJSONData = new JSONObject();
        try {
            JSONArray movieListJSONArray = new JSONArray();

            JSONObject movieJSONData1 = new JSONObject();
            movieJSONData1.put("movie_name", "Bad Boys 3");
            movieJSONData1.put("showtime_date", "5/14/14");
            movieJSONData1.put("showtimes", "10:00 11:45 12:45");
            movieJSONData1.put("theater_name", "Century Cinemas");
            movieJSONData1.put("length_in_minutes", 124);
            movieListJSONArray.put(movieJSONData1);

            JSONObject movieJSONData2 = new JSONObject();
            movieJSONData2.put("movie_name", "Star Wars VII");
            movieJSONData2.put("showtime_date", "5/15/14");
            movieJSONData2.put("showtimes", "1:00 2:45 3:45");
            movieJSONData2.put("theater_name", "Century Cinemas");
            movieJSONData2.put("length_in_minutes", 137);
            movieListJSONArray.put(movieJSONData2);

            theaterJSONData.put("theater_name", "Century Cinemas");
            theaterJSONData.put("zipcode", zipcode);
            theaterJSONData.put("movies", movieListJSONArray);
        } catch (JSONException e) {
            Log.e("Theaters", "Error writing Theater JSON Data");
            e.printStackTrace();
        }

        return theaterJSONData.toString();
    }
}

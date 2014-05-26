/*
    Ryan Wahle
    Java 1 - 1405
    Full Sail University
    May 22, 2014
 */

package com.ryanwahle.theaterlisting;

import android.content.Context;
import android.util.Log;

import com.ryanwahle.moviefinder.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Movies {
    public Movie [] movieList;
    private String jsonDataRaw;

    public Movies (Context mContext, String jsonDataString) {
        //jsonDataRaw = mContext.getResources().getString(R.string.json_data);
        Log.v("MOVIES JSON", jsonDataString);
        jsonDataRaw = new String(jsonDataString);
        parseJSON();

    }

    private void parseJSON () {
        try {
            JSONArray movieListJSONArray = new JSONArray(jsonDataRaw);
            int jsonObjectLength = movieListJSONArray.length();

            movieList = new Movie[jsonObjectLength];

            for (int i = 0; i < jsonObjectLength; i++) {
                movieList[i] = new Movie();

                // Set the Movie Name
                movieList[i].movie_name = movieListJSONArray.getJSONObject(i).getString("title");

                // Set the Runtime Length
                try
                {
                    String runTime = movieListJSONArray.getJSONObject(i).getString("runTime");
                    movieList[i].movie_length = Helper.convertRemoteSourceMovieLengthToProperString(runTime);
                } catch (JSONException ex) {
                    movieList[i].movie_length = "Length Unknown";
                }

                // Set the Rating
                try
                {
                    JSONArray jsonRatingArray = movieListJSONArray.getJSONObject(i).getJSONArray("ratings");
                    for (int indexRating = 0; indexRating < jsonRatingArray.length(); indexRating++) {
                        movieList[i].rating = jsonRatingArray.getJSONObject(indexRating).getString("code");
                    }
                } catch (JSONException ex) {
                    movieList[i].rating = "Not Rated";
                }

                // Set the Showtimes
                try
                {
                    JSONArray jsonShowtimesArray = movieListJSONArray.getJSONObject(i).getJSONArray("showtimes");
                    for (int indexShowtimes = 0; indexShowtimes < jsonShowtimesArray.length(); indexShowtimes++) {
                        String showtime = jsonShowtimesArray.getJSONObject(indexShowtimes).getString("dateTime");
                        movieList[i].showtimes = String.format("%s | %s", movieList[i].showtimes, Helper.convertRemoteSourceMovieShowtimeToProperString(showtime));
                    }
                } catch (JSONException ex) {
                    movieList[i].showtimes = "No Showtimes";
                }
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}

/*
    Ryan Wahle
    Java 1 - 1405
    Full Sail University
    May 22, 2014
 */

package com.ryanwahle.theaterlisting;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

public class Movies {
    public Movie [] movieList;
    private String jsonDataRaw;

    public Movies (Context mContext, String jsonDataString) {
        //Log.v("MOVIES JSON", jsonDataString);
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

                try
                {
                    JSONArray jsonShowtimesArray = movieListJSONArray.getJSONObject(i).getJSONArray("showtimes");
                    for (int indexShowtimes = 0; indexShowtimes < jsonShowtimesArray.length(); indexShowtimes++) {
                        String theaterName = jsonShowtimesArray.getJSONObject(indexShowtimes).getJSONObject("theatre").getString("name");
                        String showtime = jsonShowtimesArray.getJSONObject(indexShowtimes).getString("dateTime");

                        if (movieList[i].showtimes.isEmpty()) {
                            Showtimes newShowtimeTheater = new Showtimes();
                            newShowtimeTheater.theaterName = theaterName;
                            newShowtimeTheater.movieShowtimes = Helper.convertRemoteSourceMovieShowtimeToProperString(showtime);

                            movieList[i].showtimes.add(newShowtimeTheater);
                        } else {
                            boolean didFindTheater = false;

                            for (Showtimes showtimeObject : movieList[i].showtimes) {
                                if (showtimeObject.theaterName.equals(theaterName)) {
                                    showtimeObject.movieShowtimes = String.format("%s %s", showtimeObject.movieShowtimes, Helper.convertRemoteSourceMovieShowtimeToProperString(showtime));
                                    didFindTheater = true;
                                }
                            }

                            if (!didFindTheater) {
                                Showtimes newShowtimeTheater = new Showtimes();
                                newShowtimeTheater.theaterName = theaterName;
                                newShowtimeTheater.movieShowtimes = Helper.convertRemoteSourceMovieShowtimeToProperString(showtime);

                                movieList[i].showtimes.add(newShowtimeTheater);
                            }
                        }
                    }
                } catch (JSONException ex) {

                }
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}

/*
    Ryan Wahle
    Java 1 - 1405
    Full Sail University
    May 22, 2014
 */

package com.ryanwahle.theaterlisting;

import android.util.Log;

// Stores the details of a movie
public class Movie {
    public String   movie_name;
    public String   showtimes;
    public String   movie_length;
    public String   rating;

    public Movie() {
        //Log.e("Movie Class:", "Object Created");
        movie_name = "";
        showtimes = "";
        movie_length = "";
        rating = "";
    }
}

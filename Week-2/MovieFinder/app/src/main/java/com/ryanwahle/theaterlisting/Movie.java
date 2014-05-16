/*
    Ryan Wahle
    Java 1 - 1405
    Full Sail University
    May 15, 2014
 */

package com.ryanwahle.theaterlisting;

import android.util.Log;

// Stores the details of a movie
public class Movie {
    public String   movie_name;
    public String   showtime_date;
    public String   showtimes;
    public String   theater_name;
    public int      length_in_minutes;
    public Rating   rating;

    public Movie() {
        Log.e("Movie Class:", "Object Created");
    }
}

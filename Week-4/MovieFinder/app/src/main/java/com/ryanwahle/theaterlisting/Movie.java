/*
    Ryan Wahle
    Java 1 - 1405
    Full Sail University
    May 22, 2014
 */

package com.ryanwahle.theaterlisting;

import java.util.ArrayList;

// Stores the details of a movie
public class Movie {
    public String   movie_name;
    public String   movie_length;
    public String   rating;

    public ArrayList<Showtimes> showtimes;

    public Movie() {
        movie_name = "";
        movie_length = "";
        rating = "";

        showtimes = new ArrayList<Showtimes>();
    }
}

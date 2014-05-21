/*
    Ryan Wahle
    Java 1 - 1405
    Full Sail University
    May 15, 2014
 */

package com.ryanwahle.theaterlisting;

// Rating ENUM for MPAA Ratings
public enum Rating {
    G(1),
    PG(2),
    PG13(3),
    R(4);

    private final int rating;

    Rating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public String ratingDescription () {
        String ratingDescription;

        switch (rating) {
            case 1:
                ratingDescription = "Rated G";
                break;
            case 2:
                ratingDescription = "Rated PG";
                break;
            case 3:
                ratingDescription = "Rated PG-13";
                break;
            case 4:
                ratingDescription = "Rated R";
                break;
            default:
                ratingDescription = "Unknown Rating";
        }

        return ratingDescription;
    }
}

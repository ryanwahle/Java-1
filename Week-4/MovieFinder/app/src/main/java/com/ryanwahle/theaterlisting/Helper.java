package com.ryanwahle.theaterlisting;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Helper {
    // Get the current date and change to yyyy-MM-dd format that is required by the remote source.
    static public String properlyFormattedCurrentDateForRemoteSource () {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(c.getTime());
    }

    // Change the remote source movie length to format needed for the app
    static public String convertRemoteSourceMovieLengthToProperString(String remoteSourceDate) {
        // Format: ("--HH-MM-")

        Integer intHours = Integer.valueOf(String.format("%c%c", remoteSourceDate.charAt(2), remoteSourceDate.charAt(3)));
        Integer intMinutes = Integer.valueOf(String.format("%c%c", remoteSourceDate.charAt(5), remoteSourceDate.charAt(6)));

        String stringProperMovieLength = "";

        if (intHours > 1) {
            stringProperMovieLength = String.format("%d hours", intHours);
        } else {
            stringProperMovieLength = String.format("%d hour", intHours);
        }

        stringProperMovieLength = String.format("%s and %d minutes", stringProperMovieLength, intMinutes);

        return stringProperMovieLength;
    }

    // Change the remote source showtime to the format needed for the app
    static public String convertRemoteSourceMovieShowtimeToProperString(String remoteSourceShowtime) {
        // Format: "(-----------HH-MM")

        return String.format("%c%c:%c%c", remoteSourceShowtime.charAt(11), remoteSourceShowtime.charAt(12), remoteSourceShowtime.charAt(14), remoteSourceShowtime.charAt(15));
    }
}

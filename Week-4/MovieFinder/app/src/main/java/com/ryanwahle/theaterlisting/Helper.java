package com.ryanwahle.theaterlisting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
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

    static public boolean isNetworkAvailable (Context mContext)
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

    public static String getRemoteData(URL url) {
        String response = "";
        URLConnection connection = null;

        try {
            connection = url.openConnection();


            BufferedInputStream data = new BufferedInputStream(connection.getInputStream());

            byte[] contentBytes = new byte[1024];
            int bytesRead = 0;
            StringBuilder responseBuffer = new StringBuilder();

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
}

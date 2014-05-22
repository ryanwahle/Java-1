package com.ryanwahle.theaterlisting;

import android.content.Context;
import android.util.Log;

import com.ryanwahle.moviefinder.app.R;

import org.json.JSONArray;
import org.json.JSONException;

public class Movies {
    public Movie [] movieList;
    private String jsonDataRaw;

    public Movies (Context mContext) {
        jsonDataRaw = mContext.getResources().getString(R.string.json_data);

        parseJSON();

    }

    private void parseJSON () {
        try {
            JSONArray movieListJSONArray = new JSONArray(jsonDataRaw);
            int jsonObjectLength = movieListJSONArray.length();

            movieList = new Movie[jsonObjectLength];

            for (int i = 0; i < jsonObjectLength; i++) {
                movieList[i] = new Movie();
                movieList[i].movie_name = movieListJSONArray.getJSONObject(i).getString("title");
            }

            Log.v("JSON", String.format("Number of JSON Objects: %d", movieListJSONArray.length()));

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}

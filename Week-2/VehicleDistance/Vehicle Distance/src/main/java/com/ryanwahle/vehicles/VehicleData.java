package com.ryanwahle.vehicles;

import android.util.Log;

import com.ryanwahle.vehicles.Vehicle;
import com.ryanwahle.vehicles.VehicleType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ryanwahle on 5/10/14.
 */
public class VehicleData {

     // Load data from database
    public static Vehicle[] getVehicleList () {

        String vehicleJSONData = getJSONData();

        return null;
    }

    // Save the array to the database
    public static void saveVehicleList (Vehicle[] vehicles) {

    }

    private static String getJSONData () {

        JSONObject jsonVehicle = new JSONObject();
        try {
            jsonVehicle.put("vehicle_type", VehicleType.CAR);
            jsonVehicle.put("vehicle_vin", "1234");
            jsonVehicle.put("vehicle_make", "Dodge");
            jsonVehicle.put("vehicle_model", "Dakota");
            jsonVehicle.put("vehicle_year", 2002);
            jsonVehicle.put("vehicle_fueltanksize", 11.0f);
            jsonVehicle.put("vehicle_mpg", 23.0f);
            jsonVehicle.put("vehicle_topspeed", 119);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("JSON", jsonVehicle.toString());

        return jsonVehicle.toString();
    }
}

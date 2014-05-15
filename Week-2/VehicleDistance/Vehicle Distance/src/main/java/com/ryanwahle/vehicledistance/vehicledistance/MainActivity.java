package com.ryanwahle.vehicledistance.vehicledistance;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ryanwahle.vehicles.VehicleData;
import com.ryanwahle.vehicles.Vehicle;
import com.ryanwahle.vehicles.VehicleType;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Vehicle[] vehicle_list = VehicleData.getVehicleList();
        if (vehicle_list == null) {
            Log.d("MAIN", "Vehicle List is Blank.");
        }
    }
}

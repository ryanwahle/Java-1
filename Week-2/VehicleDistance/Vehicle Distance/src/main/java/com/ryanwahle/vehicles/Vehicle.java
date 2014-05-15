package com.ryanwahle.vehicles;

import android.util.Log;
import com.ryanwahle.vehicles.VehicleType;

/**
 * Created by ryanwahle on 5/10/14.
 */

public class Vehicle {
    public VehicleType vehicle_type;
    public String       vehicle_vin;
    public String       vehicle_make;
    public String       vehicle_model;
    public int          vehicle_year;
    public float        vehicle_fueltanksize;
    public float        vehicle_mpg;
    public int          vehicle_topspeed;

    public Vehicle() {
        Log.d("Vehicle", "New Vehicle Created");
    }

    public void calculateMilesPerTank () {

    }

    public void calculateHoursAtTopsSeed() {

    }
}

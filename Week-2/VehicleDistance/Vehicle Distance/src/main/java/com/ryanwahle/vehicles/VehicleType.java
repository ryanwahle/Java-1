package com.ryanwahle.vehicles;

/**
 * Created by ryanwahle on 5/10/14.
 */
public enum VehicleType {
    CAR(1),
    TRUCK(2),
    MOTORCYCLE(3);

    private final int vehicleType;

    VehicleType(int i) {
        this.vehicleType = i;
    }

    public int getVehicleType() {
        return vehicleType;
    }

    public int numberOfWheels() {
        if (vehicleType == 3) {
            return 2;
        }

        return 4;
    }
}

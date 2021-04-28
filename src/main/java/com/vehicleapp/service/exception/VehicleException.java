package com.vehicleapp.service.exception;

public class VehicleException extends Exception{
    public VehicleException() {
    }

    public VehicleException(String message) {
        super(message);
    }

    public VehicleException(String message, Throwable cause) {
        super(message, cause);
    }

    public VehicleException(Throwable cause) {
        super(cause);
    }


}

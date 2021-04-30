package com.vehicleapp.service.vehicle;

import com.vehicleapp.data.model.Vehicle;
import com.vehicleapp.service.exception.VehicleException;

import java.util.List;

public interface VehicleService {

    void registerVehicle(Vehicle vehicle);

    Vehicle findVehicleById(Long id);

    List<Vehicle> findAllVehicles();

    void updateVehicle(Vehicle vehicle) throws VehicleException;

    void deleteVehicleById(Long id) throws VehicleException;

}

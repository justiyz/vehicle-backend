package com.vehicleapp.data.repository;

import com.vehicleapp.data.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Vehicle findByVehicleName(String name);

    default void register (Vehicle vehicle){

        save(vehicle);
    }

}

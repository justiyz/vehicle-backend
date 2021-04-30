package com.vehicleapp.data.repository;

import com.vehicleapp.data.model.Vehicle;
import com.vehicleapp.data.model.VehicleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleUserRepository extends JpaRepository<VehicleUser, Long> {

    VehicleUser findVehicleUserByEmail(String email);

    default void register (VehicleUser vehicleUser){
        save(vehicleUser);
    }

}

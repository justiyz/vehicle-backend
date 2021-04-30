package com.vehicleapp.service.vehicleUser;

import com.vehicleapp.data.model.VehicleUser;

import java.util.List;

public interface VehicleUserService {

    void registerVehicleUser(VehicleUser vehicleUser, String url);

    VehicleUser findVehicleUserById(Long id);

    List<VehicleUser> findAllVehicles();

    void updateVehicleUser(VehicleUser vehicleUser);

    void deleteVehicleUserById(Long id);

    VehicleUser findVehicleUserByEmail(String email);
}

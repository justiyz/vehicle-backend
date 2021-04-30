package com.vehicleapp.service.vehicleUser;

import com.vehicleapp.data.model.Vehicle;
import com.vehicleapp.data.model.VehicleUser;
import com.vehicleapp.data.repository.VehicleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleUserServiceImpl  implements VehicleUserService{

    @Autowired
    VehicleUserRepository vehicleUserRepository;


    private String encryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public void registerVehicleUser(VehicleUser vehicleUser, String url) {
        vehicleUser = VehicleUser.builder()
                .firstName(vehicleUser.getFirstName())
                .lastName(vehicleUser.getLastName())
                .role(vehicleUser.getRole())
                .email(vehicleUser.getEmail())
                .password(encryptPassword(vehicleUser.getPassword()))
                .gender(vehicleUser.getGender())
                .registeredDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))
                .modifiedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))
                .build();
         vehicleUserRepository.save(vehicleUser);
    }

    @Override
    public VehicleUser findVehicleUserById(Long id) {
       return vehicleUserRepository.findById(id).orElse(null);
    }

    @Override
    public List<VehicleUser> findAllVehicles() {
        return vehicleUserRepository.findAll();
    }

    @Override
    public void updateVehicleUser(VehicleUser vehicleUser) {

        vehicleUserRepository.save(vehicleUser);
    }

    @Override
    public void deleteVehicleUserById(Long id) {
        vehicleUserRepository.deleteById(id);
    }

    @Override
    public VehicleUser findVehicleUserByEmail(String email) {
        return vehicleUserRepository.findVehicleUserByEmail(email);

    }

//    public void addVehicle(VehicleUser vehicleUser){
//        List<Vehicle> vehicles = new ArrayList<>();
//
//        vehicleUser.setId(vehicleUser.getId());
//        vehicleUser.setVehicles(vehicles);
//
//    }
}

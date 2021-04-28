package com.vehicleapp.service;

import com.vehicleapp.data.model.Vehicle;
import com.vehicleapp.data.repository.VehicleRepository;
import com.vehicleapp.service.exception.VehicleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class VehicleServiceImpl implements VehicleService{

    @Autowired
    VehicleRepository vehicleRepository;

    private final Logger logger = LoggerFactory.getLogger(Vehicle.class);




//    private String encryptPassword(String password) {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        return bCryptPasswordEncoder.encode(password);
//    }


    @Override
    public void registerVehicle(Vehicle vehicle) {

        vehicle = Vehicle.builder()
//                .password(encryptPassword(vehicle.getPassword()))
                .vehicleName(vehicle.getVehicleName())
                .vehicleNumber(vehicle.getVehicleNumber())
                .password(vehicle.getPassword())
                .email(vehicle.getEmail())
                .imageUrl(vehicle.getImageUrl())
                .registeredDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))
                .modifiedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))
                .build();

        vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle findVehicleById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public void updateVehicle(Vehicle vehicle) throws VehicleException {

        Vehicle existingVehicle = vehicleRepository.findById(vehicle.getId()).orElse(null);

        if (existingVehicle == null){
            throw new VehicleException("vehicle with id "+vehicle.getId() +" does not exist");
        }
        if (vehicle.getVehicleName() != null){
            existingVehicle.setVehicleName(vehicle.getVehicleName());
        }
        if (vehicle.getVehicleNumber() != null){
            existingVehicle.setVehicleNumber(vehicle.getVehicleNumber());
        }
        if (vehicle.getEmail() != null){
            existingVehicle.setEmail(vehicle.getEmail());
        }
        if (vehicle.getRegisteredDate() != null){
            existingVehicle.setRegisteredDate(vehicle.getRegisteredDate());
        }
        if (vehicle.getImageUrl() != null){
            existingVehicle.setImageUrl(vehicle.getImageUrl());
        }
        existingVehicle.setModifiedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
        vehicleRepository.save(existingVehicle);
    }

    @Override
    public void deleteVehicleById(Long id) throws VehicleException {

        if (id == null ){
            throw  new VehicleException("id can not be null");
        }
        Vehicle vehicle = vehicleRepository.findById(id).orElse(null);
        if (vehicle == null){
            throw new VehicleException("Vehicle with that id does not exist");
        }
        vehicleRepository.deleteById(id);
    }





}

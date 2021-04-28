package com.vehicleapp.web.controller;


import com.vehicleapp.data.model.Vehicle;
import com.vehicleapp.service.VehicleServiceImpl;
import com.vehicleapp.service.exception.VehicleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    VehicleServiceImpl vehicleServiceImpl;


    @GetMapping("/all")
    public ResponseEntity<?> getAllVehicles(){
        List<Vehicle> vehicles = vehicleServiceImpl.findAllVehicles();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable("id") Long id){
        Vehicle vehicle = vehicleServiceImpl.findVehicleById(id);
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerVehicle(@RequestBody Vehicle vehicle){
        vehicleServiceImpl.registerVehicle(vehicle);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateVehicle(@RequestBody Vehicle vehicle) throws VehicleException {
        vehicleServiceImpl.updateVehicle(vehicle);
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) throws VehicleException {
        vehicleServiceImpl.deleteVehicleById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }







}

package com.vehicleapp.data.repository;

import com.vehicleapp.data.model.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
@Sql(scripts = {"classpath:db/insert.sql"})
class VehicleRepositoryTest {

    @Autowired
    VehicleRepository vehicleRepository;

    Vehicle vehicle;


    @BeforeEach
    void setUp() {
        vehicle = new Vehicle();
    }

    @Test
    void saveVehicleTest(){
        vehicle = Vehicle.builder()
                .vehicleName("Volvo")
                .vehicleNumber("VLV5643")
                .build();
        vehicleRepository.save(vehicle);
    }

    @Test
    void registerVehicleMethodTest(){
        vehicle = Vehicle.builder()
                .vehicleName("Rolls Royce")
                .vehicleNumber("RRV5643")
                .imageUrl("********RR.png*******")
                .registeredDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))
                .modifiedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))
                .build();
        vehicleRepository.register(vehicle);
    }


    @Test
    void testFindByIdMethod(){
        Optional <Vehicle> optionalVehicle = vehicleRepository.findById(10L);
        vehicle = optionalVehicle.get();
        assertNotNull(vehicle);
        log.info("found vehicle -> {}", vehicle);
    }

    @Test
    void testFindByEmailMethod(){
        vehicle = vehicleRepository.findByVehicleName("Ford");
        assertNotNull(vehicle);
        log.info("found vehicle by email -> {}", vehicle);
    }

    @Test
    void testFindAllVehiclesMethod(){
        List<Vehicle> vehicles = vehicleRepository.findAll();
        assertNotNull(vehicles);
        log.info("list of found vehicles -> {}", vehicles);
    }

    @Test
    void testUpdateVehicleMethod(){
        Optional <Vehicle> existingVehicle = vehicleRepository.findById(11L);
        vehicle = existingVehicle.get();
        assertNotNull(vehicle);

        vehicle.setVehicleNumber("MCDnewMob");
        vehicleRepository.save(vehicle);

        Optional <Vehicle> updatedVehicle = vehicleRepository.findById(11L);
        vehicle = updatedVehicle.get();
        assertNotNull(vehicle);
        assertThat(vehicle.getVehicleNumber()).isEqualTo("MCDnewMob");

    }

    @Test
    void testDeleteByIdMethod(){
        Optional <Vehicle> existingVehicle = vehicleRepository.findById(11L);
        vehicle = existingVehicle.get();
        assertNotNull(vehicle);
        vehicleRepository.deleteById(vehicle.getId());

        Vehicle vehicle1 = vehicleRepository.findById(11L).orElse(null);
        assertNull(vehicle1);
    }




}
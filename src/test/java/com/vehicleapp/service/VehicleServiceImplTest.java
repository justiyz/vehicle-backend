package com.vehicleapp.service;

import com.vehicleapp.data.model.Vehicle;
import com.vehicleapp.data.repository.VehicleRepository;
import com.vehicleapp.service.exception.VehicleException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
@Sql(scripts = {"classpath:db/insert.sql"})
class VehicleServiceImplTest {

    @Mock
    VehicleRepository vehicleRepository;

    //use it to mock the test calls without hitting the database
    @InjectMocks
    VehicleServiceImpl vehicleServiceImpl;

    //use to actually hit the database to make sure the service logic is methodical
    @Autowired
    VehicleServiceImpl vehicleService;

    Vehicle vehicle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        vehicle = new Vehicle();
//        vehicle.setId(13L);
        vehicle.setVehicleName("Ford");
        vehicle.setPassword("123456i");
        vehicle.setEmail("test@gmail.com");
        vehicle.setImageUrl("img.pg");
        vehicle.setVehicleNumber("iF440");
        vehicle.setRegisteredDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
        vehicle.setModifiedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
        vehicleService.registerVehicle(vehicle);
    }


    @Test
    void mockTheRegisterVehicleServiceTest(){
        when(vehicleRepository.save(any())).thenReturn(any());
        vehicleServiceImpl.registerVehicle(vehicle);
        verify(vehicleRepository, times(1)).save(any());
    }

    @Test
    void registerVehicleWithEmptyFields(){
        assertThrows(ConstraintViolationException.class, () -> {
            vehicleService.registerVehicle(vehicle);
        });
    }

    @Test
    void registerVehicleWithNullValues(){
        vehicle.setVehicleName(null);
        vehicle.setPassword(null);
        vehicle.setEmail(null);
        vehicle.setVehicleNumber(null);
        assertThrows(ConstraintViolationException.class, () -> {
            vehicleService.registerVehicle(vehicle);
        });
    }

    @Test
    void mockFindVehicleByIdServiceTest(){
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicle));
        vehicleServiceImpl.findVehicleById(vehicle.getId());
        verify(vehicleRepository, times(1)).findById(any());
    }

    @Test
    void testThatWeCanActuallyFindVehicleById(){

        Vehicle existingVehicle = vehicleService.findVehicleById(10L);
        assertNotNull(existingVehicle);
        log.info("found vehicle -> {}", existingVehicle);
    }

    @Test
    void mockTheFindAllVehiclesServiceTest(){
        when(vehicleRepository.findAll()).thenReturn(List.of(vehicle));
        vehicleServiceImpl.findAllVehicles();
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void testThatWeCanActuallyFindAllVehicles(){
        List<Vehicle> vehicles = vehicleService.findAllVehicles();
        assertNotNull(vehicles);
        log.info("List of found vehicles -> {}", vehicles);
    }

    @Test
    void mockThatWeCanDeleteVehicleServiceTest() throws VehicleException {
        doNothing().when(vehicleRepository).deleteById(any());
        vehicleServiceImpl.deleteVehicleById(any());
        verify(vehicleRepository, times(1)).deleteById(any());
    }

    @Test
    void testThatWeCanActuallyDeleteVehicleById() throws VehicleException {
        vehicle = vehicleService.findVehicleById(10L);
        assertNotNull(vehicle);
        vehicleService.deleteVehicleById(vehicle.getId());

        vehicle = vehicleService.findVehicleById(10L);
        assertNull(vehicle);
    }

    @Test
    void deleteByNullIdTest()  {
        assertThrows(VehicleException.class, () -> {
            vehicleService.deleteVehicleById(null);
        });
    }

    @Test
    void deleteAnAccountWithInvalidId()  {
        assertThrows(VehicleException.class, () -> {
            vehicleService.deleteVehicleById(6544004190L);
        });
    }

    @Test
    void testThatWeCanUpdateVehicleDetails(){
        vehicle = vehicleService.findVehicleById(11L);
        assertNotNull(vehicle);
        vehicle.setVehicleNumber("MCD67009");
        try {
            vehicleService.updateVehicle(vehicle);
        } catch (VehicleException e){
            log.info(e.getMessage());
        }

        vehicle = vehicleService.findVehicleById(11L);
        assertNotNull(vehicle);
        assertThat(vehicle.getVehicleNumber()).isEqualTo("MCD67009");
    }



}
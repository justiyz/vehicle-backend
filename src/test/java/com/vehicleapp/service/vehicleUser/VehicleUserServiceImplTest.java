package com.vehicleapp.service.vehicleUser;

import com.vehicleapp.data.model.Vehicle;
import com.vehicleapp.data.model.VehicleUser;
import com.vehicleapp.data.repository.VehicleUserRepository;
import com.vehicleapp.service.vehicle.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VehicleUserServiceImplTest {

    @Mock
    VehicleUserRepository vehicleUserRepository;

    @InjectMocks
    VehicleUserServiceImpl vehicleUserServiceImpl;

    @Autowired
    VehicleUserServiceImpl vehicleUserService;

    VehicleUser vehicleUser;

    @Autowired
    VehicleServiceImpl vehicleService;

    Vehicle vehicle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vehicleUser = new VehicleUser();
    }

    @Test
    void addVehicleToAUserTest(){
        VehicleUser vehicleUser = vehicleUserService.findVehicleUserById(2L);
        assertNotNull(vehicleUser);

        Vehicle vehicle = Vehicle.builder()
                .vehicleUser(vehicleUser)
                .vehicleName("VolksWagen")
                .vehicleNumber("VL1234")
                .imageUrl("**img.png**")
                .registeredDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))
                .modifiedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))
                .build();
        vehicleService.registerVehicle(vehicle);
    }
}
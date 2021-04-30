package com.vehicleapp.data.repository;

import com.vehicleapp.data.model.Gender;
import com.vehicleapp.data.model.Role;
import com.vehicleapp.data.model.Vehicle;
import com.vehicleapp.data.model.VehicleUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class VehicleUserRepositoryTest {

    @Autowired
    VehicleUserRepository vehicleUserRepository;

    VehicleUser vehicleUser;

    @BeforeEach
    void setUp() {
        vehicleUser = new VehicleUser();
    }

    @Test
    void registerVehicleUserTest(){
        vehicleUser = VehicleUser.builder()
                .firstName("firstname")
                .lastName("lastname")
                .email("fl@yahoo.com")
                .gender(Gender.FEMALE)
                .role(Role.ADMIN)
                .password("justfl123432")
                .modifiedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))
                .registeredDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))
                .build();
        vehicleUserRepository.register(vehicleUser);
    }

    @Test
    void updateVehicleUserTest(){
        VehicleUser existingUser = vehicleUserRepository.findById(7L).orElse(null);
        assertNotNull(existingUser);
        existingUser.setFirstName("updated first name");
        vehicleUserRepository.save(existingUser);

        VehicleUser updatedUser = vehicleUserRepository.findById(7L).orElse(null);
        assertNotNull(updatedUser);
        assertThat(updatedUser.getFirstName()).isEqualTo("updated first name");

    }

    @Test
    void findVehicleUserByIdTest(){
        VehicleUser existingUser = vehicleUserRepository.findById(6L).orElse(null);
        assertNotNull(existingUser);
        log.info("found vehicle user -> {}", vehicleUser);

    }

    @Test
    void findVehicleUserByEmailTest(){
        VehicleUser existingUser = vehicleUserRepository.findVehicleUserByEmail("jp@gmail.com");
        assertNotNull(existingUser);
        log.info("found vehicle user -> {}", vehicleUser);

    }

     @Test
    void findAllVehicleUsersTest(){
        List<VehicleUser> existingUser = vehicleUserRepository.findAll();
        assertNotNull(existingUser);
        log.info("all vehicle users -> {}", vehicleUser);

    }

    @Test
    void deleteVehicleUserTest(){
        VehicleUser existingUser = vehicleUserRepository.findById(6L).orElse(null);
        assertNotNull(existingUser);
        vehicleUserRepository.deleteById(existingUser.getId());

        VehicleUser deletedUser = vehicleUserRepository.findById(6L).orElse(null);
        assertNull(deletedUser);

    }






}
package com.vehicleapp.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicleapp.data.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper;

    Vehicle vehicle;



    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        vehicle = new Vehicle();
    }

    @Test
    void testRegisterVehicleEndpoint() throws Exception {
        vehicle = Vehicle.builder()
                .email("fe@yahoo.com")
                .vehicleName("Ferrari")
                .vehicleNumber("FEV5643")
                .imageUrl("********fe.png*******")
                .password("FE1234321")
                .registeredDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))
                .modifiedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")))
                .build();

        this.mockMvc.perform(post("/vehicle/register")
                .contentType("application/json")
                .content(mapper.writeValueAsString(vehicle)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
    }

    @Test
    void testFindAllVehiclesEndpoint() throws Exception {

        this.mockMvc.perform(get("/vehicle/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testFindVehicleByIdEndpoint() throws Exception {

        this.mockMvc.perform(get("/vehicle/find/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testTheUpdateVehicleEndpoint() throws Exception {
         Vehicle vehicle = new Vehicle();
         vehicle.setId(10L);
         vehicle.setVehicleName("New Vehicle Name");

        this.mockMvc.perform(put("/vehicle/update")
                .contentType("application/json")
                .content(mapper.writeValueAsString(vehicle)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();


    }





}
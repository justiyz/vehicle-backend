package com.vehicleapp.web.controller.vehicleUser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicleapp.data.model.Gender;
import com.vehicleapp.data.model.Role;
import com.vehicleapp.data.model.VehicleUser;
import org.apache.tomcat.util.http.parser.Authorization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class VehicleUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper;

    VehicleUser vehicleUser;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        vehicleUser = new VehicleUser();
    }


    @Test
    void testRegisterVehicleUser() throws Exception {
        vehicleUser = VehicleUser.builder()
                .firstName("Julio")
                .lastName("Ceasar")
                .email("jp@gmail.com")
                .gender(Gender.FEMALE)
                .role(Role.USER)
                .password("JJ1234509")
                .build();


        this.mockMvc.perform(post("/vehicle-user/register")
                .contentType("application/json")
                .content(mapper.writeValueAsString(vehicleUser)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void testThatWeCanCallTheFindUserByIdEndPoint() throws Exception {

        this.mockMvc.perform(get("/vehicle-user/find/5")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqcEBnbWFpbC5jb20iLCJleHAiOjE2MjA1OTMzMjN9.lQh6IWaQt2QwzD00PoDkzrwl6aR1fKBqDIVco2Gd24-qR5SD5tjWcM_bq4emR6N5hOwHEHb56NpgWg9ieBoRBQ"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testThatWeCanCallTheFindAllUserEndPoint() throws Exception {

        this.mockMvc.perform(get("/vehicle-user/all")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqcEBnbWFpbC5jb20iLCJleHAiOjE2MjA1OTMzMjN9.lQh6IWaQt2QwzD00PoDkzrwl6aR1fKBqDIVco2Gd24-qR5SD5tjWcM_bq4emR6N5hOwHEHb56NpgWg9ieBoRBQ"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testThatWeCanCallTheUpdateUserEndpoint() throws Exception{
        VehicleUser vehicleUser = new VehicleUser();
        vehicleUser.setId(2L);
        vehicleUser.setFirstName("New First Name");

        this.mockMvc.perform(put("/vehicle-user/update")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqcEBnbWFpbC5jb20iLCJleHAiOjE2MjA1OTMzMjN9.lQh6IWaQt2QwzD00PoDkzrwl6aR1fKBqDIVco2Gd24-qR5SD5tjWcM_bq4emR6N5hOwHEHb56NpgWg9ieBoRBQ")
                .contentType("application/json")
                .content(mapper.writeValueAsString(vehicleUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testThatWeCanCallTheDeactivateUserByIdEndpoint() throws Exception {
        this.mockMvc.perform(delete("/vehicle-user/delete/1")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqcEBnbWFpbC5jb20iLCJleHAiOjE2MjA1OTMzMjN9.lQh6IWaQt2QwzD00PoDkzrwl6aR1fKBqDIVco2Gd24-qR5SD5tjWcM_bq4emR6N5hOwHEHb56NpgWg9ieBoRBQ"))
                .andExpect(status().isOk())
                .andReturn();
    }




}
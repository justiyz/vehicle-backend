package com.vehicleapp.web.controller.vehicleUser;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.vehicleapp.data.model.Vehicle;
import com.vehicleapp.data.model.VehicleUser;
import com.vehicleapp.dto.ResponseDto;
import com.vehicleapp.service.exception.VehicleException;
import com.vehicleapp.service.vehicleUser.VehicleUserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.vehicleapp.security.SecurityConstants.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/vehicle-user")
public class VehicleUserController {

    @Autowired
    VehicleUserServiceImpl vehicleUserServiceImpl;

    ModelMapper modelMapper = new ModelMapper();



    @PostMapping("/register")
    public ResponseEntity<?> registerVehicleUser(@RequestBody @Valid VehicleUser vehicleUser, WebRequest request,
                                                 HttpServletRequest httpServletRequest, HttpServletResponse response,
                                                 Authentication authResult) throws Exception {

        VehicleUser existingVehicleUser = vehicleUserServiceImpl.findVehicleUserByEmail(vehicleUser.getEmail());
        if (existingVehicleUser == null){
            String url = httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getServletPath(), "");
            vehicleUserServiceImpl.registerVehicleUser(vehicleUser, url);
        } else {
            throw  new VehicleException("User with that email address already exist");
        }
//        vehicleUserServiceImpl.registerVehicleUser(modelMapper.map(vehicleUser, VehicleUser.class), url);

        String token = JWT.create()
                .withSubject(vehicleUser.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes(StandardCharsets.UTF_8)));

        VehicleUser user = vehicleUserServiceImpl.findVehicleUserByEmail(vehicleUser.getEmail());
        ResponseDto responseDto = ResponseDto.builder()
                .user(user)
                .token(token)
                .build();

        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.flushBuffer();

        ResponseDetailsWithObject responseDetails = new ResponseDetailsWithObject(LocalDateTime.now(), "Your account has been created successfully", responseDto, request.getDescription(false));
        return ResponseEntity.status(200).body(responseDetails);

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllVehicleUsers(){
        List<VehicleUser> vehicleUsers = vehicleUserServiceImpl.findAllVehicles();
        return new ResponseEntity<>(vehicleUsers, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getVehicleUserById(@PathVariable("id") Long id){
        VehicleUser vehicleUser = vehicleUserServiceImpl.findVehicleUserById(id);
        return new ResponseEntity<>(vehicleUser, HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateVehicleUser(@Valid @RequestBody VehicleUser vehicleUser)  {
        vehicleUserServiceImpl.updateVehicleUser(vehicleUser);
        return new ResponseEntity<>(vehicleUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVehicleUser(@PathVariable Long id) {
        vehicleUserServiceImpl.deleteVehicleUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

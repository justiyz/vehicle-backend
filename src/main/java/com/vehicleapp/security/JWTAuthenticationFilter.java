package com.vehicleapp.security;

import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicleapp.data.model.VehicleUser;
import com.vehicleapp.data.repository.VehicleRepository;
import com.vehicleapp.data.repository.VehicleUserRepository;
import com.vehicleapp.dto.LoginDto;
import com.vehicleapp.dto.ResponseDto;
import com.vehicleapp.dto.UnsuccessfulLoginDto;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import static com.vehicleapp.security.SecurityConstants.*;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final VehicleUserRepository vehicleUserRepository;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext context) {
        this.authenticationManager = authenticationManager;
        vehicleUserRepository = context.getBean(VehicleUserRepository.class);
        setFilterProcessesUrl("/api/user/login");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDto credential = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginDto.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credential.getEmail(),
                            credential.getPassword(),
                            new ArrayList<>())
            );
        }
        catch (IOException e) {
            throw new RuntimeException("User does not exist");
        }
    }



    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        String token = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes(StandardCharsets.UTF_8)));

        ObjectMapper oMapper = new ObjectMapper();
        String email = ((User) authResult.getPrincipal()).getUsername();
        VehicleUser user = vehicleUserRepository.findVehicleUserByEmail(email);
//        List<OrganizationProcurementParty> organizationProcurementParties = organizationProcurementPartyRepository.findAllByProcurementParty(user);
//        List<OutletProcurementParty> outletProcurementParties = outletProcurementPartyRepository.findAllByProcurementParty(user);

        ResponseDto responseDto = ResponseDto.builder()
                .user(user)
                .token(token)
                .build();

//        if (organizationProcurementParties.size() != 0) {
//            Set<Outlet> outlets = new HashSet<>();
//
//            for (OutletProcurementParty outletProcurementParty: outletProcurementParties
//            ) {
//                outlets.add(outletProcurementParty.getOutlet());
//            }
//
//            responseDto.setOrganization(organizationProcurementParties.get(0).getOrganization());
//            responseDto.setOutlets(outlets);
//            responseDto.setRole(organizationProcurementParties.get(0).getRole());
//        }

        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.getOutputStream().print("{ \"data\":"  + oMapper.writeValueAsString(responseDto) +  "}");
        response.flushBuffer();

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
        UnsuccessfulLoginDto responseDetails = new UnsuccessfulLoginDto(LocalDateTime.now(), "Incorrect email or password", "Bad request", "api/user/login");

        response.getOutputStream().print("{ \"message\":"  + responseDetails +  "}");
    }









}

package com.vehicleapp.service;

import com.vehicleapp.data.model.Role;
import com.vehicleapp.data.model.VehicleUser;
import com.vehicleapp.data.repository.VehicleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    VehicleUserRepository applicationVehicleUserRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        VehicleUser vehicleUser = applicationVehicleUserRepository.findVehicleUserByEmail(s);
        if (vehicleUser == null){
            throw new UsernameNotFoundException("");
        }
        return new User(vehicleUser.getEmail(), vehicleUser.getPassword(), getAuthorities(vehicleUser.getRole()));
    }


    private Collection<GrantedAuthority> getGrantedAuthorities(Role role) {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(role)));

        return grantedAuthorities;
    }



    public Collection<? extends GrantedAuthority> getAuthorities(Role role){
        return getGrantedAuthorities(role);
    }





}

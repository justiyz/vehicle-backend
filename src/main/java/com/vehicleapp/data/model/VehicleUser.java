package com.vehicleapp.data.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private Role role;

    private Gender gender;

    @OneToMany
    @ToString.Exclude
    private List<Vehicle> vehicles;

    private String registeredDate;

    private String modifiedDate;

    public void setVehicles(Vehicle vehicle){
        if (vehicles == null){
            vehicles = new ArrayList<>();
        }
        vehicles.add(vehicle);
    }

}

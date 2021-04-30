package com.vehicleapp.data.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicle")
public class Vehicle  {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String vehicleName;

    @NotNull
    private String vehicleNumber;

    @NotNull
    private String imageUrl;

    @ManyToOne
    @ToString.Exclude
    private VehicleUser vehicleUser;

    @NotNull
    private String registeredDate;

    @NotNull
    private String modifiedDate;

}

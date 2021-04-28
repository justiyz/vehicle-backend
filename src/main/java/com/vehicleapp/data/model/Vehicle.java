package com.vehicleapp.data.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Vehicle implements Serializable {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String vehicleName;

    @NotNull
    private String vehicleNumber;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String imageUrl;

    @NotNull
    private String registeredDate;

    @NotNull
    private String modifiedDate;

}

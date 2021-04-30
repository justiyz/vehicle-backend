package com.vehicleapp.dto;

import com.vehicleapp.data.model.VehicleUser;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {

    private String token;

    private VehicleUser user;

}

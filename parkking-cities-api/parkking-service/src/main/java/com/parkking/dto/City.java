
package com.parkking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class City {

    private String code;

    private String label;

    @JsonIgnore
    private String connector;

    public static City fromEntity(com.parkking.entity.City entity) {
        return City.builder().code(entity.getCode()).label(entity.getLabel()).connector(entity.getConnector()).build();
    }

}

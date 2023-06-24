
package com.parkking.api;

import lombok.Data;

@Data
public class ParkkingApiResponse<T> {

    private T data;

    private String error;

}

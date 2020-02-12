package com.toyota.rentalcar.dev.commons.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.toyota.rentalcar.dev.commons.model.ApiExceptionData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Setter
@Slf4j
@NoArgsConstructor
@JsonPropertyOrder(value = {"error_code", "error_message", "error_data"})
public class ApiException extends Exception {

    @JsonProperty("error_code")
    private String code;

    @JsonProperty("error_message")
    private String message;

    @JsonProperty("error_data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map data;

    public ApiException(String code, String message){
        this.code = code;
        this.message = message;
    }

    public ApiException(String code, String message, ApiExceptionData data){
        this.code = code;
        this.message = message;
        this.data  = data.getData();
    }
}

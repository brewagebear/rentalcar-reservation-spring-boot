package com.toyota.rentalcar.dev.commons.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ApiExceptionData {

    private Map<String, Object> data;

    public ApiExceptionData add(String key, Object value){

        if(this.data == null){
            this.data = new HashMap<String, Object>();
        }
        this.data.put(key, value);
        return this;
    }
}

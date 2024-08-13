package com.example.InsuranceSystem.v11.DTO;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY) // Exclude empty maps from serialization
public class DynamicDTO {
    private Map<String, Object> fields = new HashMap<>();

    public Object getField(String key) {
        return fields.get(key);
    }

    public void setField(String key, Object value) {
        fields.put(key, value);
    }

    @JsonAnyGetter // Allows for serialization of map entries as individual properties
    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }
    
}

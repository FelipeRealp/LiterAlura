package com.feliperealp.literatura.service;

import tools.jackson.databind.ObjectMapper;

public class ConversorDatos {

    ObjectMapper mapper = new ObjectMapper();

    public <T> T convertirDatos(String json, Class<T> clase){
        try {
            return mapper.readValue(json, clase);
        } catch (Exception e) {
            throw new RuntimeException("Error conviertiendo JSON",e);
        }
    }

}

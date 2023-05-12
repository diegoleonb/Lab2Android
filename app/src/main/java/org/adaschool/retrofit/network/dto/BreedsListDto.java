package org.adaschool.retrofit.network.dto;

import java.util.Map;

public class BreedsListDto {
    private String status;
    private Map<String, String[]> message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String[]> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String[]> message) {
        this.message = message;
    }
}
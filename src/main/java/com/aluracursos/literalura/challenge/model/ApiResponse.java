package com.aluracursos.literalura.challenge.model;

public class ApiResponse {
    private int statusCode;
    private String body;
    private boolean success;
    
    public ApiResponse(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
        this.success = statusCode >= 200 && statusCode < 300;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public String getBody() {
        return body;
    }
    
    public boolean isSuccess() {
        return success;
    }
}
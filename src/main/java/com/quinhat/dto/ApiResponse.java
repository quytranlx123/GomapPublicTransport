package com.quinhat.dto;

public class ApiResponse<T> {

    private T data;
    private int status;

    public ApiResponse() {
    }

    public ApiResponse(T data, int status) {
        this.data = data;
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

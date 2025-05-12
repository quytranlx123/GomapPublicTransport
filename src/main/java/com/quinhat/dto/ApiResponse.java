package com.quinhat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ApiResponse<T> {

    private T data;
    private int status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer page;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pageSize;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer total;

    public ApiResponse() {
    }

    public ApiResponse(T data, int status) {
        this.data = data;
        this.status = status;
    }

    public ApiResponse(T data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public ApiResponse(T data, int status, String message, Integer total) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.total = total;
    }

    public ApiResponse(T data, int status, String message, Integer page, Integer pageSize, Integer total) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return the pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

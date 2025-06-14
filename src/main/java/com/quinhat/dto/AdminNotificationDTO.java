package com.quinhat.dto;

import java.util.Date;

public class AdminNotificationDTO {

    private Integer id;
    private String title;
    private String message;
    private Date createdAt;
    private String messageType;

    public AdminNotificationDTO() {
    }

    public AdminNotificationDTO(Integer id, String title, String message, Date createdAt, String messageType) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
        this.messageType = messageType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}

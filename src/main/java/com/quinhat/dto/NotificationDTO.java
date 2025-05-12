/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.dto;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class NotificationDTO {

    private Integer notificationId;
    private Integer userId;
    private String title;
    private String message;
    private String messageType;
    private Date sendAt;
    private Boolean isRead;

    // Constructors
    public NotificationDTO() {
    }

    public NotificationDTO(Integer notificationId, Integer userId, String title, String message, String messageType, Date sendAt, Boolean isRead) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.messageType = messageType;
        this.sendAt = sendAt;
        this.isRead = isRead;
    }

    /**
     * @return the notificationId
     */
    public Integer getNotificationId() {
        return notificationId;
    }

    /**
     * @param notificationId the notificationId to set
     */
    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the messageType
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * @return the sendAt
     */
    public Date getSendAt() {
        return sendAt;
    }

    /**
     * @param sendAt the sendAt to set
     */
    public void setSendAt(Date sendAt) {
        this.sendAt = sendAt;
    }

    /**
     * @return the isRead
     */
    public Boolean getIsRead() {
        return isRead;
    }

    /**
     * @param isRead the isRead to set
     */
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

}

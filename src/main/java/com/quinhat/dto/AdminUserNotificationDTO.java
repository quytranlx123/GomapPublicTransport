package com.quinhat.dto;

import java.util.Date;

public class AdminUserNotificationDTO {

    private Integer id;
    private Date sendAt;
    private boolean isRead;
    private Integer notificationId;
    private Integer userId;

    public AdminUserNotificationDTO() {
    }

    public AdminUserNotificationDTO(Integer id, Date sendAt, boolean isRead, Integer notificationId, Integer userId) {
        this.id = id;
        this.sendAt = sendAt;
        this.isRead = isRead;
        this.notificationId = notificationId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSendAt() {
        return sendAt;
    }

    public void setSendAt(Date sendAt) {
        this.sendAt = sendAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

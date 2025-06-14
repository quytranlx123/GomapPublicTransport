/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "user_notification")
@NamedQueries({
    @NamedQuery(name = "UserNotification.findAll", query = "SELECT u FROM UserNotification u"),
    @NamedQuery(name = "UserNotification.findById", query = "SELECT u FROM UserNotification u WHERE u.id = :id"),
    @NamedQuery(name = "UserNotification.findBySendAt", query = "SELECT u FROM UserNotification u WHERE u.sendAt = :sendAt"),
    @NamedQuery(name = "UserNotification.findByIsRead", query = "SELECT u FROM UserNotification u WHERE u.isRead = :isRead")})
public class UserNotification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "send_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_read")
    private boolean isRead;
    @JoinColumn(name = "notification_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Notification notificationId;
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    @ManyToOne
    private User userId;

    public UserNotification() {
    }

    public UserNotification(Integer id) {
        this.id = id;
    }

    public UserNotification(Integer id, boolean isRead) {
        this.id = id;
        this.isRead = isRead;
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

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Notification getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Notification notificationId) {
        this.notificationId = notificationId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserNotification)) {
            return false;
        }
        UserNotification other = (UserNotification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.quinhat.pojo.UserNotification[ id=" + id + " ]";
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "traffic_report")
@NamedQueries({
    @NamedQuery(name = "TrafficReport.findAll", query = "SELECT t FROM TrafficReport t"),
    @NamedQuery(name = "TrafficReport.findById", query = "SELECT t FROM TrafficReport t WHERE t.id = :id"),
    @NamedQuery(name = "TrafficReport.findByAddress", query = "SELECT t FROM TrafficReport t WHERE t.address = :address"),
    @NamedQuery(name = "TrafficReport.findByLatitude", query = "SELECT t FROM TrafficReport t WHERE t.latitude = :latitude"),
    @NamedQuery(name = "TrafficReport.findByLongitude", query = "SELECT t FROM TrafficReport t WHERE t.longitude = :longitude"),
    @NamedQuery(name = "TrafficReport.findByImage", query = "SELECT t FROM TrafficReport t WHERE t.image = :image"),
    @NamedQuery(name = "TrafficReport.findByDescription", query = "SELECT t FROM TrafficReport t WHERE t.description = :description"),
    @NamedQuery(name = "TrafficReport.findByCreatedAt", query = "SELECT t FROM TrafficReport t WHERE t.createdAt = :createdAt"),
    @NamedQuery(name = "TrafficReport.findByIsVerified", query = "SELECT t FROM TrafficReport t WHERE t.isVerified = :isVerified")})
public class TrafficReport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @NotNull
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude")
    private float latitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude")
    private float longitude;
    @Size(max = 255)
    @Column(name = "image")
    private String image;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_verified")
    private boolean isVerified;
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true) //chấp nhận null để xoá user không bị xoá lan truyền
    @ManyToOne
    private User userId;
//    @Transient
//    private MultipartFile file;

    public TrafficReport() {
    }

    public TrafficReport(Integer id) {
        this.id = id;
    }

    public TrafficReport(Integer id, String address, float latitude, float longitude, boolean isVerified) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isVerified = isVerified;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
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
        if (!(object instanceof TrafficReport)) {
            return false;
        }
        TrafficReport other = (TrafficReport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.quinhat.pojo.TrafficReport[ id=" + id + " ]";
    }
//
//    /**
//     * @return the file
//     */
//    public MultipartFile getFile() {
//        return file;
//    }
//
//    /**
//     * @param file the file to set
//     */
//    public void setFile(MultipartFile file) {
//        this.file = file;
//    }
}

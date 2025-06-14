package com.quinhat.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity đại diện cho báo cáo giao thông
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
    @NamedQuery(name = "TrafficReport.findByIsVerified", query = "SELECT t FROM TrafficReport t WHERE t.isVerified = :isVerified")
})
public class TrafficReport implements Serializable {

    private static final long serialVersionUID = 1L;

    // ==== ID ====
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // ==== Title ====
    @NotNull
    @Size(max = 255)
    @Column(name = "title", nullable = false)
    private String title;

    // ==== Address ====
    @Size(max = 255)
    @Column(name = "address", nullable = true)
    private String address;

    // ==== Coordinates ====
    @Column(name = "latitude", nullable = true)
    private float latitude;

    @Column(name = "longitude", nullable = true)
    private float longitude;

    // ==== Image & Description ====
    @Size(max = 255)
    @Column(name = "image")
    private String image;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    // ==== Date ====
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    // ==== Verification ====
    @NotNull
    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;

    // ==== User (nullable) ====
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private User userId;

    // ==== Notification Type (Enum) ====
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ReportType type;

    public enum ReportType {
        report,
        rating
    }

    // ==== Constructors ====
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

    // ==== Getters & Setters ====
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

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    // ==== Hashcode/Equals ====
    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TrafficReport)) {
            return false;
        }
        TrafficReport other = (TrafficReport) obj;
        return this.id != null && this.id.equals(other.getId());
    }

    @Override
    public String toString() {
        return "TrafficReport[id=" + id + "]";
    }

    // ==== Nếu cần upload file tạm thời (không lưu DB) ====
    /*
    @Transient
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
     */
}

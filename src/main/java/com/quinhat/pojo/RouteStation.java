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
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "route_station")
@NamedQueries({
    @NamedQuery(name = "RouteStation.findAll", query = "SELECT r FROM RouteStation r"),
    @NamedQuery(name = "RouteStation.findById", query = "SELECT r FROM RouteStation r WHERE r.id = :id"),
    @NamedQuery(name = "RouteStation.findByOrderStation", query = "SELECT r FROM RouteStation r WHERE r.orderStation = :orderStation"),
    @NamedQuery(name = "RouteStation.findByDistance", query = "SELECT r FROM RouteStation r WHERE r.distance = :distance"),
    @NamedQuery(name = "RouteStation.findByDuration", query = "SELECT r FROM RouteStation r WHERE r.duration = :duration")})
public class RouteStation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "order_station")
    private int orderStation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "distance")
    private float distance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "duration")
    private int duration;
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Route routeId;
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Station stationId;

    public RouteStation() {
    }

    public RouteStation(Integer id) {
        this.id = id;
    }

    public RouteStation(Integer id, int orderStation, float distance, int duration) {
        this.id = id;
        this.orderStation = orderStation;
        this.distance = distance;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOrderStation() {
        return orderStation;
    }

    public void setOrderStation(int orderStation) {
        this.orderStation = orderStation;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Route getRouteId() {
        return routeId;
    }

    public void setRouteId(Route routeId) {
        this.routeId = routeId;
    }

    public Station getStationId() {
        return stationId;
    }

    public void setStationId(Station stationId) {
        this.stationId = stationId;
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
        if (!(object instanceof RouteStation)) {
            return false;
        }
        RouteStation other = (RouteStation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.quinhat.pojo.RouteStation[ id=" + id + " ]";
    }
    
}

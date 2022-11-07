package model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Venue {
    @Id
    private int id;
    private String venueName;
    private String linkMoreInfo;
    private int capacity;
    private boolean foodProvided;
    private boolean indoor;
    private boolean outdoor;
    private boolean freeParkingAvailable;
    private String city;
    private int distanceFromPublicTransportInKm;

    public Venue() {

    }


    public Venue(String venueName, String linkMoreInfo, int capacity, boolean isFoodProvided, boolean isIndoor, boolean isOutdoor, boolean isFreeParkingAvailable, String city, int distanceFromPublicTransportInKm) {
        this.venueName = venueName;
        this.linkMoreInfo = linkMoreInfo;
        this.capacity = capacity;
        this.foodProvided = isFoodProvided;
        this.indoor = isIndoor;
        this.outdoor = isOutdoor;
        this.freeParkingAvailable = isFreeParkingAvailable;
        this.city = city;
        this.distanceFromPublicTransportInKm = distanceFromPublicTransportInKm;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getLinkMoreInfo() {
        return linkMoreInfo;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isFoodProvided() {
        return foodProvided;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public boolean isOutdoor() {
        return outdoor;
    }

    public boolean isFreeParkingAvailable() {
        return freeParkingAvailable;
    }

    public String getCity() {
        return city;
    }

    public int getDistanceFromPublicTransportInKm() {
        return distanceFromPublicTransportInKm;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public void setLinkMoreInfo(String linkMoreInfo) {
        this.linkMoreInfo = linkMoreInfo;
    }

    public int getId() {
        return id;
    }
}

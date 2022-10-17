package be.thomasmore.party.model;

public class Venue {
    private String venueName;
    private String linkMoreInfo;
    private int capacity;
    private boolean isFoodProvided;
    private boolean isIndoor;
    private boolean isOutdoor;
    private boolean isFreeParkingAvailable;
    private String city;
    private int distanceFromPublicTransportInKm;

    public Venue(String venueName, String linkMoreInfo, int capacity, boolean isFoodProvided, boolean isIndoor, boolean isOutdoor, boolean isFreeParkingAvailable, String city, int distanceFromPublicTransportInKm) {
        this.venueName = venueName;
        this.linkMoreInfo = linkMoreInfo;
        this.capacity = capacity;
        this.isFoodProvided = isFoodProvided;
        this.isIndoor = isIndoor;
        this.isOutdoor = isOutdoor;
        this.isFreeParkingAvailable = isFreeParkingAvailable;
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
        return isFoodProvided;
    }

    public boolean isIndoor() {
        return isIndoor;
    }

    public boolean isOutdoor() {
        return isOutdoor;
    }

    public boolean isFreeParkingAvailable() {
        return isFreeParkingAvailable;
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
}

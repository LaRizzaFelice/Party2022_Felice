package be.thomasmore.party.model;

public class Venue {
    private String venueName;
    private String linkMoreInfo;

    public Venue(String venueName, String linkMoreInfo) {
        this.venueName = venueName;
        this.linkMoreInfo = linkMoreInfo;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getLinkMoreInfo() {
        return linkMoreInfo;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public void setLinkMoreInfo(String linkMoreInfo) {
        this.linkMoreInfo = linkMoreInfo;
    }
}

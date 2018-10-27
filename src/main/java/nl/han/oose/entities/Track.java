package nl.han.oose.entities;

public class Track {
    private int id;
    private String title;
    private String performer;
    private int duration;
    private int playcount;
    private String publicationdate;
    private String description;
    private boolean offlineAvailible;

    public Track() {
    }

    public Track(int id, String title, String performer, int duration, int playcount, String publicationdate, String description, boolean offlineAvailible) {
        this.id = id;
        this.title = title;
        this.performer = performer;
        this.duration = duration;
        this.playcount = playcount;
        this.publicationdate = publicationdate;
        this.description = description;
        this.offlineAvailible = offlineAvailible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public String getPublicationdate() {
        return publicationdate;
    }

    public void setPublicationdate(String publicationdate) {
        this.publicationdate = publicationdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOfflineAvailible() {
        return offlineAvailible;
    }

    public void setOfflineAvailible(boolean offlineAvailible) {
        this.offlineAvailible = offlineAvailible;
    }
}

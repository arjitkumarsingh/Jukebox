package com.niit.jukebox.database;

public class Podcast {
    private int podcastId;
    private String title;
    private String celebrity;
    private String genre;
    private String released;
    private String filePath;

    public Podcast(String title, String celebrity, String genre, String released, String filePath) {
        this.title = title;
        this.celebrity = celebrity;
        this.genre = genre;
        this.released = released;
        this.filePath = filePath;
    }

    public Podcast() {
    }

    public int getPodcastId() {
        return podcastId;
    }

    public void setPodcastId(int podcastId) {
        this.podcastId = podcastId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCelebrity() {
        return celebrity;
    }

    public void setCelebrity(String celebrity) {
        this.celebrity = celebrity;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return String.format("%-13s %-45s %-25s %-13s %-15s", podcastId, title, celebrity, genre, released);
    }
}

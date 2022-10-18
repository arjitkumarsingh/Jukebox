package com.niit.jukebox.database;

public class PlaylistItem {
    private int id;
    private int playlistId;
    private String type;
    private int itemId;
    private Song song;
    private Podcast podcast;
    public PlaylistItem(int id, int playlistId, String type, int itemId) {
        this.id = id;
        this.playlistId = playlistId;
        this.type = type;
        this.itemId = itemId;
    }

    public PlaylistItem() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPlaylistId() {
        return playlistId;
    }
    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Podcast getPodcast() {
        return podcast;
    }

    public void setPodcast(Podcast podcast) {
        this.podcast = podcast;
    }

    @Override
    public String toString() {
        if (type.equalsIgnoreCase("song"))
            return String.format("%-15s %-25s %-45s", id, type, song.getTitle());
        else
            return String.format("%-15s %-25s %-45s", id, type, podcast.getTitle());
    }
}

package com.niit.jukebox.database;

public class Playlist {
    private int playlistId;
    private String playlistName;
    private User user;

    public Playlist(String playlistName, User user) {
        this.playlistName = playlistName;
        this.user = user;
    }

    public Playlist() {
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("%-15s %-15s", playlistId, playlistName);
    }
}

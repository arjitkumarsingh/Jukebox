package com.niit.jukebox.jukebox;

import com.niit.jukebox.database.*;
import com.niit.jukebox.exception.JukeboxException;

import java.util.List;

public interface IJukebox {
    boolean addUser(User user);
    void loginToAccount(User user) throws JukeboxException;
    List<Song> readSongs();
    List<Podcast> readPodcasts();
    List<Playlist> readPlaylists(User user);
    List<PlaylistItem> readPlaylistItems(int playlistId);
    boolean createPlaylist(int userId, String name);
    void addSongIntoPlaylist(int playlistId, int songId) throws JukeboxException;
    void addPodcastIntoPlaylist(int playlistId, int podcastId) throws JukeboxException;
    void removeItemFromPlaylist(String sql);
    void deletePlaylist(String sql, int id) throws JukeboxException;
    List<Song> filterSongs(String search, String filterOn, List<Song> songList);
    List<Podcast> filterPodcasts(String search, String filterOn, List<Podcast> podcastList);
    void playMenu(List list, int id);
}
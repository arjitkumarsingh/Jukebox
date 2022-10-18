package com.niit.jukebox.jukebox;

import com.niit.jukebox.Table;
import com.niit.jukebox.connection.MySQLConnection;
import com.niit.jukebox.database.*;
import com.niit.jukebox.exception.JukeboxException;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class JukeboxImpl extends Table implements IJukebox {

    public boolean addUser(User user) {
        boolean flag = false;
        String sql = "insert into users(username, pin) values(?, ?)";
        try {
            PreparedStatement st = MySQLConnection.con.prepareStatement(sql);
            st.setString(1, user.getUserName());
            st.setInt(2, user.getPin());
            int line = st.executeUpdate();
            if (line > 0)
                flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void loginToAccount(User user) throws JukeboxException {
        ResultSet rs = getTable("select * from users where username = '" + user.getUserName() + "' and pin = '" + user.getPin() + "'");
        try {
            if (rs.next())
                user.setUserId(rs.getInt(1));
            else
                throw new JukeboxException(" No such user exist");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Song> readSongs() {
        List<Song> songList = new ArrayList<>();
        ResultSet rs = getTable("select * from songs");
        try {
            while (rs.next()) {
                Song song = new Song();
                song.setSongId(rs.getInt(1));
                song.setTitle(rs.getString(2));
                song.setArtist(rs.getString(3));
                song.setAlbum(rs.getString(4));
                song.setGenre(rs.getString(5));
                song.setReleased(rs.getString(6));
                song.setFilePath(rs.getString(7));
                songList.add(song);
            }
            songList.sort((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songList;
    }

    public List<Podcast> readPodcasts() {
        List<Podcast> podcastList = new ArrayList<>();
        ResultSet rs = getTable("select * from podcasts");
        try {
            while (rs.next()) {
                Podcast podcast = new Podcast();
                podcast.setPodcastId(rs.getInt(1));
                podcast.setTitle(rs.getString(2));
                podcast.setCelebrity(rs.getString(3));
                podcast.setGenre(rs.getString(4));
                podcast.setReleased(rs.getString(5));
                podcast.setFilePath(rs.getString(6));
                podcastList.add(podcast);
            }
            podcastList.sort((o1, o2) -> o1.getCelebrity().compareToIgnoreCase(o2.getCelebrity()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return podcastList;
    }
    public List<Playlist> readPlaylists(User user) {
        List<Playlist> playlistList = new ArrayList<>();
        ResultSet rs = getTable("select * from playlists where user_id = " + user.getUserId());
        try {
            while (rs.next()) {
                Playlist playlist = new Playlist();
                playlist.setPlaylistId(rs.getInt(1));
                playlist.setPlaylistName(rs.getString(2));
                playlistList.add(playlist);
            }
            playlistList.sort((o1, o2) -> o1.getPlaylistName().compareToIgnoreCase(o2.getPlaylistName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlistList;
    }

    public List<PlaylistItem> readPlaylistItems(int playlistId) {
        List<PlaylistItem> playlistItems = new ArrayList<>();
        ResultSet rs = getTable("select * from playlist_items where playlist_id = " + playlistId);
        ResultSet set;
        try {
            while (rs.next()) {
                PlaylistItem item = new PlaylistItem();
                item.setId(rs.getInt(1));
                item.setPlaylistId(rs.getInt(2));
                item.setType(rs.getString(3));
                item.setItemId(rs.getInt(4));
                if (item.getType().equalsIgnoreCase("song")) {
                    set = getTable("select * from songs where song_id = " + item.getItemId());
                    if (set.next()) {
                        Song song = new Song();
                        song.setSongId(set.getInt(1));
                        song.setTitle(set.getString(2));
                        song.setArtist(set.getString(3));
                        song.setAlbum(set.getString(4));
                        song.setGenre(set.getString(5));
                        song.setReleased(set.getString(6));
                        song.setFilePath(set.getString(7));
                        item.setSong(song);
                    }
                }
                else {
                    set = getTable("select * from podcasts where podcast_id = " + item.getItemId());
                    if (set.next()) {
                        Podcast podcast = new Podcast();
                        podcast.setPodcastId(set.getInt(1));
                        podcast.setTitle(set.getString(2));
                        podcast.setCelebrity(set.getString(3));
                        podcast.setGenre(set.getString(4));
                        podcast.setReleased(set.getString(5));
                        podcast.setFilePath(set.getString(6));
                        item.setPodcast(podcast);
                    }
                }
                playlistItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlistItems;
    }

    public boolean createPlaylist(int userId, String name) {
        boolean flag = false;
        String sql = "insert into playlists(playlist_name, user_id) values(?, ?)";
        try {
            PreparedStatement st = MySQLConnection.con.prepareStatement(sql);
            st.setString(1, name);
            st.setInt(2, userId);
            int line = st.executeUpdate();
            if (line > 0)
                flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void addSongIntoPlaylist(int playlistId, int songId) throws JukeboxException {
        if (!readSongs().stream().filter(p -> p.getSongId() == songId).toList().isEmpty()) {
            String sql = "insert into playlist_items(playlist_id, type, item_id) values(?, 'song', ?)";
            try {
                PreparedStatement st = MySQLConnection.con.prepareStatement(sql);
                st.setInt(1, playlistId);
                st.setInt(2, songId);
                int line = st.executeUpdate();
                if (line > 0)
                    System.out.println("\nSuccessfully added the song");
            } catch (SQLException e) {
                throw new JukeboxException(" Song already exist in the playlist");
            }
        } else
            throw new JukeboxException(" No such song exist");
    }

    public void addPodcastIntoPlaylist(int playlistId, int podcastId) throws JukeboxException {
        if (!readPodcasts().stream().filter(p -> p.getPodcastId() == podcastId).toList().isEmpty()) {
            String sql = "insert into playlist_items(playlist_id, type, item_id) values(?, 'podcast', ?)";
            try {
                PreparedStatement st = MySQLConnection.con.prepareStatement(sql);
                st.setInt(1, playlistId);
                st.setInt(2, podcastId);
                int line = st.executeUpdate();
                if (line > 0)
                    System.out.println("\nSuccessfully added the podcast");
            } catch (SQLException e) {
                throw new JukeboxException(" Podcast already exist in the playlist");
            }
        } else
            throw new JukeboxException(" No such podcast exist");
    }

    public void removeItemFromPlaylist(String sql) {
        try {
            PreparedStatement st = MySQLConnection.con.prepareStatement(sql);
            int line = st.executeUpdate();
            if (line > 0)
                System.out.println("\nItem removed successfully from the playlist");
            else
                System.out.println("No item exist in the playlist");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deletePlaylist(String sql, int id) throws JukeboxException {
        try {
            removeItemFromPlaylist(sql);
            String delSql = "delete from playlists where playlist_Id = " + id;
            PreparedStatement st = MySQLConnection.con.prepareStatement(delSql);
            int line = st.executeUpdate();
            if (line > 0)
                System.out.println("\nPlaylist deleted successfully");
            else
                throw new JukeboxException("\nPlease check playlist Id and try again");
        } catch (SQLException | JukeboxException e) {
            e.printStackTrace();
        }
    }

    public List<Song> filterSongs(String search, String filterOn, List<Song> songList) {
        if (filterOn.equalsIgnoreCase("title"))
            songList = songList.stream().filter(p -> p.getTitle().matches("(?i).*" + search + ".*")).sorted((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle())).toList();
        if (filterOn.equalsIgnoreCase("artist"))
            songList = songList.stream().filter(p -> p.getArtist().matches("(?i).*" + search + ".*")).sorted((o1, o2) -> o1.getArtist().compareToIgnoreCase(o2.getArtist())).toList();
        if (filterOn.equalsIgnoreCase("album"))
            songList = songList.stream().filter(p -> p.getAlbum().matches("(?i).*" + search + ".*")).sorted((o1, o2) -> o1.getAlbum().compareToIgnoreCase(o2.getAlbum())).toList();
        if (filterOn.equalsIgnoreCase("genre"))
            songList = songList.stream().filter(p -> p.getGenre().matches("(?i).*" + search + ".*")).sorted((o1, o2) -> o1.getGenre().compareToIgnoreCase(o2.getGenre())).toList();
        if (filterOn.equalsIgnoreCase("released"))
            songList = songList.stream().filter(p -> p.getReleased().matches("(?i).*" + search + ".*")).sorted((o1, o2) -> o1.getReleased().compareToIgnoreCase(o2.getReleased())).toList();
        return songList;
    }

    public List<Podcast> filterPodcasts(String search, String filterOn, List<Podcast> podcastList) {
        if (filterOn.equalsIgnoreCase("title"))
            podcastList = podcastList.stream().filter(p -> p.getTitle().matches("(?i).*" + search + ".*")).sorted((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle())).toList();
        if (filterOn.equalsIgnoreCase("celebrity"))
            podcastList = podcastList.stream().filter(p -> p.getCelebrity().matches("(?i).*" + search + ".*")).sorted((o1, o2) -> o1.getCelebrity().compareToIgnoreCase(o2.getCelebrity())).toList();
        if (filterOn.equalsIgnoreCase("genre"))
            podcastList = podcastList.stream().filter(p -> p.getGenre().matches("(?i).*" + search + ".*")).sorted((o1, o2) -> o1.getGenre().compareToIgnoreCase(o2.getGenre())).toList();
        if (filterOn.equalsIgnoreCase("released"))
            podcastList = podcastList.stream().filter(p -> p.getReleased().matches("(?i).*" + search + ".*")).sorted((o1, o2) -> o1.getReleased().compareToIgnoreCase(o2.getReleased())).toList();
        return podcastList;
    }
/*    public <T> List<T> filter(String search, String filterOn, List<T> list) {
        if (filterOn.equalsIgnoreCase("title"))
            if (list instanceof Song);
        return list;
    }*/

    private String getFilePath(List list, int id) {
        String filePath = "";
        for (Object o: list) {
            if (o instanceof Song && ((Song) o).getSongId() == id) {
                filePath = ((Song) o).getFilePath();
                break;
            }
            else if (o instanceof Podcast && ((Podcast) o).getPodcastId() == id) {
                filePath = ((Podcast) o).getFilePath();
                break;
            }
            else if (o instanceof PlaylistItem && ((PlaylistItem) o).getId() == id) {
                if (((PlaylistItem) o).getType().equalsIgnoreCase("song"))
                    filePath = ((PlaylistItem) o).getSong().getFilePath();
                else
                    filePath = ((PlaylistItem) o).getPodcast().getFilePath();
            }
        }
        return filePath;
    }

    public void playMenu(List list, int id) {
        if (JukeboxPlayer.status.equalsIgnoreCase("play"))
            JukeboxPlayer.clip.close();
        JukeboxPlayer.filePath = getFilePath(list, id);
        JukeboxPlayer jukeboxPlayer = new JukeboxPlayer();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 6) {
            System.out.println("""
                                        
                    1. Start
                    2. Pause
                    3. Resume
                    4. Restart
                    5. Stop
                    6. Back""");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    play();
                    break;
                case 2:
                    pause(jukeboxPlayer);
                    break;
                case 3:
                    resume(jukeboxPlayer);
                    break;
                case 4:
                    restart(jukeboxPlayer);
                    break;
                case 5:
                    stop(jukeboxPlayer);
                    break;
                default:
                    if (choice != 6)
                        System.out.println("Invalid input!\nGoing back to previous menu");
                    choice = 6;
            }
        }
    }

    private void play() {
        JukeboxPlayer.clip.start();        //start the clip
        JukeboxPlayer.status = "play";
    }

    private void pause(JukeboxPlayer jukeboxPlayer) {
        if (JukeboxPlayer.status.equals("paused")) {
            System.out.println("audio is already paused");
            return;
        }
        jukeboxPlayer.currentFrame = JukeboxPlayer.clip.getMicrosecondPosition();
        JukeboxPlayer.clip.stop();
        JukeboxPlayer.status = "paused";
    }

    private void resume(JukeboxPlayer jukeboxPlayer) {
        if (JukeboxPlayer.status.equals("play")) {
            System.out.println("Audio is already being played");
            return;
        }
        JukeboxPlayer.clip.close();
        resetAudioStream(jukeboxPlayer);
        JukeboxPlayer.clip.setMicrosecondPosition(jukeboxPlayer.currentFrame);
        play();
    }

    private void restart(JukeboxPlayer jukeboxPlayer) {
        JukeboxPlayer.clip.stop();
        JukeboxPlayer.clip.close();
        resetAudioStream(jukeboxPlayer);
        jukeboxPlayer.currentFrame = 0L;
        JukeboxPlayer.clip.setMicrosecondPosition(0);
        play();
    }

    private void stop(JukeboxPlayer jukeboxPlayer) {
        jukeboxPlayer.currentFrame = 0L;
        JukeboxPlayer.clip.stop();
        JukeboxPlayer.clip.close();
    }

    private void resetAudioStream(JukeboxPlayer jukeboxPlayer) {
        try {
            jukeboxPlayer.audioInputStream = AudioSystem.getAudioInputStream(new File(JukeboxPlayer.filePath));
            JukeboxPlayer.clip.open(jukeboxPlayer.audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}

/*
     * Listens to the START and STOP events of the audio line.

    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
        if (type == LineEvent.Type.START) {
            jukebox.status = "play";
            System.out.println("Playback started.");
        } else if (type == LineEvent.Type.STOP) {
            jukebox.status = "stop";
            System.out.println("Playback stopped.");
        }
    }
}


    public void addSong(Song song) {
        String sql = "insert into songs(title, artist, album, genre, released, filepath) values(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = MySQLConnection.con.prepareStatement(sql);
            st.setString(1, song.getTitle());
            st.setString(2, song.getArtist());
            st.setString(3, song.getAlbum());
            st.setString(4, song.getGenre());
            st.setString(5, song.getReleased());
            st.setString(6, song.getFilePath());
            int line = st.executeUpdate();
            if (line > 0)
                System.out.println("Song added");
            else
                System.out.println("Failed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPodcast(Podcast podcast) {
        String sql = "insert into podcasts(title, celebrity, genre, released, filepath) values(?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = MySQLConnection.con.prepareStatement(sql);
            st.setString(1, podcast.getTitle());
            st.setString(2, podcast.getCelebrity());
            st.setString(3, podcast.getGenre());
            st.setString(4, podcast.getReleased());
            st.setString(5, podcast.getFilePath());
            int line = st.executeUpdate();
            if (line > 0)
                System.out.println("Podcast added");
            else
                System.out.println("Failed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } */
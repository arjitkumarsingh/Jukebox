package com.niit.jukebox;

import com.niit.jukebox.connection.MySQLConnection;
import com.niit.jukebox.database.Podcast;
import com.niit.jukebox.database.Song;
import com.niit.jukebox.database.User;

import com.niit.jukebox.jukebox.JukeboxImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JukeboxTests {
    MySQLConnection connection = new MySQLConnection();
    JukeboxImpl jukebox;
    Song song;
    Podcast podcast;
    User user;
    List<Song> songList;
    List<Podcast> podcastList;

    @BeforeEach
    void setUp() {
        jukebox = new JukeboxImpl();
        user = new User();
        user.setUserName("arjit");
        user.setPin(1234);

     /*
        songList = new ArrayList<>();
        song = new Song();
        song.setSongId(1);
        song.setTitle("Zombie");
        song.setArtist("The Cranberries");
        song.setAlbum("No Need to Argue");
        song.setGenre("Rock");
        song.setReleased("1994-09-19");
        song.setFilePath("src/main/media/Songs/The_Cranberries-Zombie.wav");
        songList.add(song);

        song = new Song();
        song.setSongId(2);
        song.setTitle("Daddy Cool");
        song.setArtist("Boney M.");
        song.setAlbum("Take the Heat off Me");
        song.setGenre("Disco");
        song.setReleased("1976-05-31");
        song.setFilePath("src/main/media/Songs/DaddyCool.wav");
        songList.add(song);

        song = new Song();
        song.setSongId(3);
        song.setTitle("Gal Ban Gai");
        song.setArtist("The Sahotas");
        song.setAlbum("Brotherhood");
        song.setGenre("Folk");
        song.setReleased("1999-1-1");
        song.setFilePath("src/main/media/Songs/The Sahotas-Gal Ban Gai.wav");
        songList.add(song);

        song = new Song();
        song.setSongId(4);
        song.setTitle("White Rabbit");
        song.setArtist("Jefferson Airplane");
        song.setAlbum("Surrealistic Pillow");
        song.setGenre("Rock");
        song.setReleased("1967-06-24");
        song.setFilePath("src/main/media/Songs/Jefferson_Airplane-White_Rabbit.wav");
        songList.add(song);*/
    }

    @AfterEach
    void tearDown() {
        songList = null;
        podcastList = null;
        song = null;
        podcast = null;
        user = null;
    }

    @Test
    public void givenNonExistingUserForCreatingPlaylist() {
        assertFalse(jukebox.createPlaylist(0, "junit"));
    }
    @Test
    public void addingExistingUserAgain() {
        assertFalse(jukebox.addUser(user));
    }
    @Test
    public void creatingPlaylistForNonExistingUser() {
        assertFalse(jukebox.createPlaylist(0, "nonExistingUser"));
    }

}

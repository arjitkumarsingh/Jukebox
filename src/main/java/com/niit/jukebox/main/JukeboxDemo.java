package com.niit.jukebox.main;

import com.niit.jukebox.database.*;
import com.niit.jukebox.exception.JukeboxException;
import com.niit.jukebox.jukebox.JukeboxImpl;
import com.niit.jukebox.connection.MySQLConnection;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class JukeboxDemo {
    public static void main(String[] args) {
        MySQLConnection connection = new MySQLConnection();
        JukeboxImpl jukeboxDao = new JukeboxImpl();
        User user = new User();
        Scanner scanner = new Scanner(System.in);

        List<Song> songs;
        List<Podcast> podcasts;
        List<PlaylistItem> playlistItems;
        List<Song> filteredSongs;
        List<Podcast> filteredPodcasts;
        String name;
        int playlistId;
        int choice;
        do {
            System.out.println("""
                                        
                    Jukebox Program:    1. Sign up
                                        2. Sign in
                                        3. Exit""");
            choice = scanner.nextInt();
            switch (choice) {
                case 1: // Sign up
                    System.out.println("Please create a username and 4 digit pin to login");
                    user.setUserName(scanner.next());
                    user.setPin(scanner.nextInt());
                    if (jukeboxDao.addUser(user))
                        System.out.println("New user created successfully!" +
                                "\nusername: " + user.getUserName() + "\npin : " + user.getPin());
                    else
                        System.out.println("Please check username or pin and try again");
                    break;
                case 2:     // Sign in
                    System.out.println("Please enter your username and 4 digit pin to login");
                    try {
                        user.setUserName(scanner.next());
                        user.setPin(scanner.nextInt());
                        jukeboxDao.loginToAccount(user);
                        System.out.println("\nWelcome back '" + user.getUserName() + "'");
                        while (choice != 4) {
                            System.out.println("""
                                                                        
                                    Main Menu:  1. Songs
                                                2. Podcasts
                                                3. Playlists
                                                4. Sign out""");
                            choice = scanner.nextInt();
                            switch (choice) {
                                case 1:     // Songs
                                    songs = jukeboxDao.readSongs();
                                    while (choice != 6) {
                                        System.out.printf("%-10s %-20s %-25s %-30s %-10s %-15s %n", "Song Id", "Title", "Artist", "Album", "Genre", "Released");
                                        songs.forEach(System.out::println);
                                        System.out.println("""
                                                                                                                                          
                                                Search by:  1. Title
                                                            2. Artist
                                                            3. Album
                                                            4. Genre
                                                            5. Released Date
                                                            6. Back""");
                                        choice = scanner.nextInt();
                                        switch (choice) {
                                            case 1:     // Title
                                                System.out.println("Enter title");
                                                scanner.nextLine();
                                                name = scanner.nextLine();
                                                System.out.println("Search result for " + name);
                                                filteredSongs = jukeboxDao.filterSongs(name, "title", songs);
                                                if (!filteredSongs.isEmpty()) {
                                                    System.out.printf("%n%-10s %-20s %-25s %-30s %-10s %-15s %n", "Song Id", "Title", "Artist", "Album", "Genre", "Released");
                                                    filteredSongs.forEach(i -> System.out.println(i));
                                                    System.out.println("\nEnter song Id to select the song");
                                                    int id = scanner.nextInt();
                                                    if (filteredSongs.stream().anyMatch(p -> p.getSongId() == id))
                                                        jukeboxDao.playMenu(filteredSongs, id);
                                                    else
                                                        System.out.println("Invalid input!\nGoing back to previous menu\n");
                                                } else
                                                    System.out.println("No match found");
                                                break;
                                            case 2:     // Artist
                                                System.out.println("Enter artist");
                                                scanner.nextLine();
                                                name = scanner.nextLine();
                                                System.out.println("Search result for " + name);
                                                filteredSongs = jukeboxDao.filterSongs(name, "artist", songs);
                                                if (!filteredSongs.isEmpty()) {
                                                    System.out.printf("%n%-10s %-20s %-25s %-30s %-10s %-15s %n", "Song Id", "Title", "Artist", "Album", "Genre", "Released");
                                                    filteredSongs.forEach(i -> System.out.println(i));
                                                    System.out.println("\nEnter song Id to select the song");
                                                    int id = scanner.nextInt();
                                                    if (filteredSongs.stream().anyMatch(p -> p.getSongId() == id))
                                                        jukeboxDao.playMenu(filteredSongs, id);
                                                    else
                                                        System.out.println("Invalid input!\nGoing back to previous menu\n");
                                                } else
                                                    System.out.println("No match found");
                                                break;
                                            case 3:     // Album
                                                System.out.println("Enter album");
                                                scanner.nextLine();
                                                name = scanner.nextLine();
                                                System.out.println("search result for " + name);
                                                filteredSongs = jukeboxDao.filterSongs(name, "album", songs);
                                                if (!filteredSongs.isEmpty()) {
                                                    System.out.printf("%n%-10s %-20s %-25s %-30s %-10s %-15s %n", "Song Id", "Title", "Artist", "Album", "Genre", "Released");
                                                    filteredSongs.forEach(i -> System.out.println(i));
                                                    System.out.println("\nEnter song Id to select the song");
                                                    int id = scanner.nextInt();
                                                    if (filteredSongs.stream().anyMatch(p -> p.getSongId() == id))
                                                        jukeboxDao.playMenu(filteredSongs, id);
                                                    else
                                                        System.out.println("Invalid input!\nGoing back to previous menu\n");
                                                } else
                                                    System.out.println("No match found");
                                                break;
                                            case 4:     // Genre
                                                System.out.println("Enter genre");
                                                scanner.nextLine();
                                                name = scanner.nextLine();
                                                System.out.println("search result for " + name);
                                                filteredSongs = jukeboxDao.filterSongs(name, "genre", songs);
                                                if (!filteredSongs.isEmpty()) {
                                                    System.out.printf("%n%-10s %-20s %-25s %-30s %-10s %-15s %n", "Song Id", "Title", "Artist", "Album", "Genre", "Released");
                                                    filteredSongs.forEach(i -> System.out.println(i));
                                                    System.out.println("\nEnter song Id to select the song");
                                                    int id = scanner.nextInt();
                                                    if (filteredSongs.stream().anyMatch(p -> p.getSongId() == id))
                                                        jukeboxDao.playMenu(filteredSongs, id);
                                                    else
                                                        System.out.println("Invalid input!\nGoing back to previous menu\n");
                                                } else
                                                    System.out.println("No match found");
                                                break;
                                            case 5:     // Released
                                                System.out.println("Enter released date");
                                                scanner.nextLine();
                                                name = scanner.nextLine();
                                                System.out.println("search result for " + name);
                                                filteredSongs = jukeboxDao.filterSongs(name, "released", songs);
                                                if (!filteredSongs.isEmpty()) {
                                                    System.out.printf("%n%-10s %-20s %-25s %-30s %-10s %-15s %n", "Song Id", "Title", "Artist", "Album", "Genre", "Released");
                                                    filteredSongs.forEach(i -> System.out.println(i));
                                                    System.out.println("\nEnter song Id to select the song");
                                                    int id = scanner.nextInt();
                                                    if (filteredSongs.stream().anyMatch(p -> p.getSongId() == id))
                                                        jukeboxDao.playMenu(filteredSongs, id);
                                                    else
                                                        System.out.println("Invalid input!\nGoing back to previous menu\n");
                                                } else
                                                    System.out.println("No match found");
                                                break;
                                            default:
                                                choice = 6;
                                        }
                                    }
                                    break;
                                case 2:     // Podcast
                                    podcasts = jukeboxDao.readPodcasts();
                                    while (choice != 5) {
                                        System.out.printf("%-13s %-45s %-25s %-13s %-15s %n", "Podcast Id", "Title", "Celebrity", "Genre", "Released");
                                        podcasts.forEach(System.out::println);
                                        System.out.println("""
                                                                                                                                          
                                                Search by:  1. Title
                                                            2. Celebrity
                                                            3. Genre
                                                            4. Released Date
                                                            5. Back""");
                                        choice = scanner.nextInt();
                                        switch (choice) {
                                            case 1:     // Title
                                                System.out.println("Enter title");
                                                scanner.nextLine();
                                                name = scanner.nextLine();
                                                System.out.println("Search result for " + name);
                                                filteredPodcasts = jukeboxDao.filterPodcasts(name, "title", podcasts);
                                                if (!filteredPodcasts.isEmpty()) {
                                                    System.out.printf("%n%-13s %-45s %-25s %-13s %-15s %n", "Podcast Id", "Title", "Celebrity", "Genre", "Released");
                                                    filteredPodcasts.forEach(i -> System.out.println(i));
                                                    System.out.println("\nEnter podcast Id to select the podcast");
                                                    int id = scanner.nextInt();
                                                    if (filteredPodcasts.stream().anyMatch(p -> p.getPodcastId() == id))
                                                        jukeboxDao.playMenu(filteredPodcasts, id);
                                                    else
                                                        System.out.println("Invalid input!\nGoing back to previous menu\n");
                                                } else
                                                    System.out.println("No match found");
                                                break;
                                            case 2:     // Celebrity
                                                System.out.println("Enter celebrity");
                                                scanner.nextLine();
                                                name = scanner.nextLine();
                                                System.out.println("Search result for " + name);
                                                filteredPodcasts = jukeboxDao.filterPodcasts(name, "celebrity", podcasts);
                                                if (!filteredPodcasts.isEmpty()) {
                                                    System.out.printf("%n%-13s %-45s %-25s %-13s %-15s %n", "Podcast Id", "Title", "Celebrity", "Genre", "Released");
                                                    filteredPodcasts.forEach(i -> System.out.println(i));
                                                    System.out.println("\nEnter podcast Id to select the podcast");
                                                    int id = scanner.nextInt();
                                                    if (filteredPodcasts.stream().anyMatch(p -> p.getPodcastId() == id))
                                                        jukeboxDao.playMenu(filteredPodcasts, id);
                                                    else
                                                        System.out.println("Invalid input!\nGoing back to previous menu\n");
                                                } else
                                                    System.out.println("No match found");
                                                break;
                                            case 3:     // Genre
                                                System.out.println("Enter genre");
                                                scanner.nextLine();
                                                name = scanner.nextLine();
                                                System.out.println("search result for " + name);
                                                filteredPodcasts = jukeboxDao.filterPodcasts(name, "genre", podcasts);
                                                if (!filteredPodcasts.isEmpty()) {
                                                    System.out.printf("%n%-13s %-45s %-25s %-13s %-15s %n", "Podcast Id", "Title", "Celebrity", "Genre", "Released");
                                                    filteredPodcasts.forEach(i -> System.out.println(i));
                                                    System.out.println("\nEnter podcast Id to select the podcast");
                                                    int id = scanner.nextInt();
                                                    if (filteredPodcasts.stream().anyMatch(p -> p.getPodcastId() == id))
                                                        jukeboxDao.playMenu(filteredPodcasts, id);
                                                    else
                                                        System.out.println("Invalid input!\nGoing back to previous menu\n");
                                                } else
                                                    System.out.println("No match found");
                                                break;
                                            case 4:     // Released
                                                System.out.println("Enter released date");
                                                scanner.nextLine();
                                                name = scanner.nextLine();
                                                System.out.println("search result for " + name);
                                                filteredPodcasts = jukeboxDao.filterPodcasts(name, "released", podcasts);
                                                if (!filteredPodcasts.isEmpty()) {
                                                    System.out.printf("%n%-13s %-45s %-25s %-13s %-15s %n", "Podcast Id", "Title", "Celebrity", "Genre", "Released");
                                                    filteredPodcasts.forEach(i -> System.out.println(i));
                                                    System.out.println("\nEnter podcast Id to select the podcast");
                                                    int id = scanner.nextInt();
                                                    if (filteredPodcasts.stream().anyMatch(p -> p.getPodcastId() == id))
                                                        jukeboxDao.playMenu(filteredPodcasts, id);
                                                    else
                                                        System.out.println("Invalid input!\nGoing back to previous menu\n");
                                                } else
                                                    System.out.println("No match found");
                                                break;
                                            default:
                                                choice = 5;
                                        }
                                    }
                                    break;
                                case 3:     // Playlist
                                    while (choice != 4) {
                                        List<Playlist> playlists = jukeboxDao.readPlaylists(user);
                                        if (!playlists.isEmpty()) {
                                            System.out.printf("%n%-15s %-15s %n", "Playlist Id", "Playlist Name");
                                            playlists.forEach(System.out::println);
                                            System.out.println("""
                                                                                                                                              
                                                    Select an option:   1. Add
                                                                        2. Open
                                                                        3. Delete
                                                                        4. Back""");
                                            choice = scanner.nextInt();
                                        } else {
                                            System.out.println("""
                                                                                                    
                                                    No playlist found:   1. Add
                                                                         2. Back""");
                                            choice = scanner.nextInt();
                                            if (choice != 1 && choice != 2) {
                                                System.out.println("Invalid input\nGoing back to previous menu");
                                                choice = 4;
                                            } else if (choice == 2)
                                                choice = 4;
                                        }
                                        switch (choice) {
                                            case 1:     // Add playlist
                                                System.out.println("Enter name of the playlist");
                                                scanner.nextLine();
                                                name = scanner.nextLine();
                                                if (jukeboxDao.createPlaylist(user.getUserId(), name))
                                                    playlists = jukeboxDao.readPlaylists(user);
                                                else
                                                    System.out.println("Sorry! Couldn't create playlist");
                                                break;
                                            case 2:     // Open playlist
                                                System.out.println("Enter playlist Id to open");
                                                playlistId = scanner.nextInt();
                                                while (choice != 5) {
                                                    playlistItems = jukeboxDao.readPlaylistItems(playlistId);
                                                    if (!playlistItems.isEmpty()) {
                                                        System.out.printf("%n%-15s %-25s %-45s %n", "Id", "Type", "Title");
                                                        playlistItems.forEach(i -> System.out.println(i));
                                                        System.out.println("""
                                                                                                                        
                                                                Select an option:   1. Add item
                                                                                    2. play
                                                                                    3. Remove item
                                                                                    4. Delete playlist
                                                                                    5. Back""");
                                                        choice = scanner.nextInt();
                                                    } else {
                                                        System.out.println("""
                                                                                                                                
                                                                Playlist is empty
                                                                                                                                
                                                                Select an option:   1. Add item
                                                                                    2. Delete playlist
                                                                                    3. Back""");
                                                        choice = scanner.nextInt();
                                                        if (choice == 2)
                                                            choice = 4;
                                                        if (choice == 3)
                                                            choice = 5;
                                                    }
                                                    switch (choice) {
                                                        case 1:     // Add item
                                                            while (choice != 3) {
                                                                System.out.println("""
                                                                                                                                        
                                                                        1. Song
                                                                        2. Podcast
                                                                        3. Back""");
                                                                choice = scanner.nextInt();
                                                                switch (choice) {
                                                                    case 1:
                                                                        System.out.println();
                                                                        jukeboxDao.readSongs().forEach(i -> System.out.println(i));
                                                                        System.out.println("\nEnter song Id to add the song");
                                                                        choice = scanner.nextInt();
                                                                        try {
                                                                            jukeboxDao.addSongIntoPlaylist(playlistId, choice);
                                                                            System.out.println("""
                                                                                                                                                            
                                                                                    Add more items:   1. Yes
                                                                                                      2. No""");
                                                                            choice = scanner.nextInt();
                                                                            if (choice == 2)
                                                                                choice = 3;
                                                                        } catch (JukeboxException e) {
                                                                            System.out.println(e);
                                                                        }
                                                                        break;
                                                                    case 2:
                                                                        System.out.println();
                                                                        jukeboxDao.readPodcasts().forEach(i -> System.out.println(i));
                                                                        System.out.println("\nEnter podcast Id to add the podcast");
                                                                        choice = scanner.nextInt();
                                                                        try {
                                                                            jukeboxDao.addPodcastIntoPlaylist(playlistId, choice);
                                                                            System.out.println("""
                                                                                                                                                            
                                                                                    Add more items:   1. Yes
                                                                                                      2. No""");
                                                                            choice = scanner.nextInt();
                                                                            if (choice == 2)
                                                                                choice = 3;
                                                                        } catch (JukeboxException e) {
                                                                            System.out.println(e);
                                                                        }
                                                                        break;
                                                                    default:
                                                                        if (choice != 3)
                                                                            System.out.println("Invalid input\nGoing back to main menu");
                                                                        choice = 3;
                                                                }
                                                            }
                                                            break;
                                                        case 2:     // Play
                                                            System.out.println("Enter Id to play the title");
                                                            choice = scanner.nextInt();
                                                            jukeboxDao.playMenu(playlistItems, choice);
                                                            break;
                                                        case 3:     // Remove item
                                                            System.out.println("Enter id to remove from the playlist");
                                                            choice = scanner.nextInt();
                                                            name = "delete from playlist_items where id = " + choice;
                                                            jukeboxDao.removeItemFromPlaylist(name);
                                                            break;
                                                        case 4:     // Delete playlist
                                                            name = "delete from playlist_items where playlist_id = " + playlistId;
                                                            try {
                                                                jukeboxDao.deletePlaylist(name, playlistId);
                                                            } catch (JukeboxException e) {
                                                                System.out.println(e);
                                                            }
                                                            break;
                                                        default:
                                                            if (choice != 5)
                                                                System.out.println("Invalid input\nGoing back to previous menu");
                                                            choice = 5;
                                                    }
                                                }
                                                choice = 0;
                                                break;
                                            case 3:     // Delete playlist
                                                System.out.println("Enter playlist Id to delete it");
                                                playlistId = scanner.nextInt();
                                                name = "delete from playlist_items where playlist_id = " + playlistId;
                                                try {
                                                    jukeboxDao.deletePlaylist(name, playlistId);
                                                } catch (JukeboxException e) {
                                                    System.out.println(e);
                                                }
                                                break;
                                            default:
                                                choice = 4;
                                        }
                                    }
                                    choice = 0;
                                    break;
                                default:
                                    choice = 4;
                            }
                        }
                    } catch (JukeboxException e) {
                        System.out.println(e);
                    }
                    break;
                default:
                    try {
                        MySQLConnection.con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("Thank you for using JukeBox services!");
                        choice = 3;
                    }
            }
        } while (choice != 3);
    }
}
create database JukeBox;
use jukebox;

create table users(
	user_id int auto_increment primary key,
	username varchar(25) not null unique,
	pin int not null check (length(pin) = 4));
    
create table playlists(
	playlist_id int auto_increment primary key,
    playlist_name varchar(50) not null,
    user_id int,
    foreign key(user_id) references users(user_id));
    
create table playlist_items(
	id int auto_increment primary key, -- not null check(id = 1 or id = 2),
    playlist_id int,
    type varchar(10),
    item_id int,
    unique key(playlist_id, type, item_id), foreign key(playlist_id) references playlists(playlist_id));
    
create table songs(
	song_id int auto_increment primary key,
    title varchar(100) not null,
    artist varchar(50),
    album varchar(50),
    genre varchar(25),
    released date,
    filepath varchar(255));
    
create table podcasts(
	podcast_id int auto_increment primary key,
    title varchar(100) not null,
    celebrity varchar(50),
    genre varchar(25),
    released date,
    filepath varchar(255));

insert into songs values(1, 'Zombie', 'The Cranberries', 'No Need to Argue', 'Rock', str_to_date('19-09-1994', '%d-%m-%Y'), 'src/main/media/Songs/The_Cranberries-Zombie.wav');
insert into songs values(2, 'Daddy Cool', 'Boney M.', 'Take the Heat off Me', 'Disco', str_to_date('31-05-1976', '%d-%m-%Y'), 'src/main/media/Songs/DaddyCool.wav');
insert into songs values(3, 'Gal Ban Gai', 'The Sahotas', 'Brotherhood', 'Folk', str_to_date('1-1-1999', '%d-%m-%Y'), 'src/main/media/Songs/The Sahotas-Gal Ban Gai.wav');
insert into songs values(4, 'White Rabbit', 'Jefferson Airplane', 'Surrealistic Pillow', 'Rock', str_to_date('24-06-1967', '%d-%m-%Y'), 'src/main/media/Songs/Jefferson_Airplane-White_Rabbit.wav');

insert into podcasts values(1, 'Wade\'s Unsettling Solicitor', 'Distractible Podcast', 'Life', str_to_date('01-09-2022', '%d-%m-%Y'), 'src/main/media/Podcasts/Wade\'s Unsettling Solicitor.wav');
insert into podcasts values(2, 'Samsung is ROASTING iPhone 14', 'XeeTechCare', 'Mobile', str_to_date('2-9-2022', '%d-%m-%Y'), 'src/main/media/Podcasts/Samsung is ROASTING iPhone 14.wav');
insert into podcasts values(3, 'Kobo Releases the Kobo Clara 2E  Good News', 'Good e-Reader', 'e Reader', str_to_date('02-9-2022', '%d-%m-%Y'), 'src/main/media/Podcasts/Kobo Releases the Kobo Clara 2E Good News.wav');


select * from songs;
select * from podcasts;
select * from users;
select * from playlists;
select * from playlist_items;

delete from songs;
delete from podcasts;
delete from users;
delete from playlist_items;

drop table songs;
drop table podcasts;
drop table items;
drop table users;
drop table playlists;
drop table playlist_items;
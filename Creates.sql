use myMovies

create table movie(
	ID varchar(15) primary key,
	title varchar(100) not null,
	type varchar(10) not null check( type in('series', 'movie')),
	plot varchar(350),
	released date,
	imdbRating float,
	runTime int,
	poster varchar(250)
)

create table actor(
	actorName varchar(50) not null primary key
)

create table genre(
	genre varchar(25) not null primary key
)

create table movieActor(
	movieID varchar(15) not null,
	actorName varchar(50),
	movieTitle varchar(100),
	primary key(movieID, actorName),
	foreign key (movieID) references movie(ID),
	foreign key (actorName) references actor (actorName)
)

create table movieGenre(
	movieID varchar(15) not null,
	genre varchar(25),
	movieTitle varchar(100),
	primary key(movieID, genre),
	foreign key (movieID) references movie(ID),
	foreign key (genre) references genre (genre)
)

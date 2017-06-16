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
	actorName varchar(50) primary key
)

create table genre(
	genre varchar(25) primary key
)

create table movieActor(
	movieID varchar(15) not null,
	actorName varchar(50),
	movieTitle varchar(100),
	primary key(movieID, actorName),
	foreign key (movieID) references movie(ID) on delete cascade,
	foreign key (actorName) references actor (actorName) on delete cascade
)

create table movieGenre(
	movieID varchar(15) not null,
	genre varchar(25),
	movieTitle varchar(100),
	primary key(movieID, genre),
	foreign key (movieID) references movie(ID) on delete cascade,
	foreign key (genre) references genre (genre) on delete cascade
)

create table dBase(
	name varchar(100) primary key
)

create table movieDataBase(
	movieID varchar(15),
	dbName varchar(100),
	primary key(movieID, dbName),
	foreign key (movieID) references movie(ID) on delete cascade,
	foreign key (dbName) references dBase (name)
)
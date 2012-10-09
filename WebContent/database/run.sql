create table item ( 
	id				INTEGER PRIMARY KEY,
	description 	VARCHAR(100),
	startlocation	INTEGER REFERENCES locations(id),
	shipsto			INTEGER REFERENCES locations(id),
	category		varchar(20),
	startDate 		DATE,
	endDate 		DATE,
	owner			varchar(20),
	firstBid		integer,
	secondBid		integer,
	firstBidUser	INTEGER REFERENCES user(id),
	secondBidUser	INTEGER REFERENCES user(id),
	minBid 			INTEGER
);

create table user (
	id 				INTEGER PRIMARY KEY,
	name 			VARCHAR(10) not null,
	password 		VARCHAR(15) not null,
	userdetails 	INTEGER REFERENCES details(id)
);

create table locations (
	id				INTEGER PRIMARY KEY,
	name 			VARCHAR(15) not null
);

create table details (
	id				INTEGER PRIMARY KEY,
	birthday		DATE,
	address			VARCHAR(50),
	rating			INTEGER,
	ratingcount		INTEGER,
	paypalAcct		INTEGER,
	email			VARCHAR(50),
	about			VARCHAR(50)
);
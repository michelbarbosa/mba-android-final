CREATE TABLE usuarios (
    id integer NOT NULL PRIMARY KEY AUTOINCREMENT,
    login varchar(100) NOT NULL,
	senha varchar(1000) NOT NULL
);

CREATE TABLE filmes (
    id integer NOT NULL PRIMARY KEY AUTOINCREMENT,
    titulo varchar(100) NOT NULL,
    ano int NOT NULL,
    genero varchar(100) NOT NULL,
	sinopse varchar(1000) NOT NULL
);

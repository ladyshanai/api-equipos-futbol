DROP TABLE IF EXISTS equipos;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE equipos
(
    id     INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    liga   VARCHAR(255) NOT NULL,
    pais   VARCHAR(255) NOT NULL
);

CREATE TABLE usuarios
(
    id     INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL
);

INSERT INTO equipos (nombre, liga, pais)
VALUES ('Real Madrid', 'La Liga', 'España'),
       ('FC Barcelona', 'La Liga', 'España'),
       ('Manchester United', 'Premier League', 'Inglaterra'),
       ('Liverpool FC', 'Premier League', 'Inglaterra'),
       ('Juventus FC', 'Serie A', 'Italia'),
       ('AC Milan', 'Serie A', 'Italia'),
       ('Bayern Munich', 'Bundesliga', 'Alemania'),
       ('Borussia Dortmund', 'Bundesliga', 'Alemania'),
       ('Paris Saint-Germain', 'Ligue 1', 'Francia'),
       ('Olympique de Marseille', 'Ligue 1', 'Francia'),
       ('FC Porto', 'Primeira Liga', 'Portugal'),
       ('Sporting CP', 'Primeira Liga', 'Portugal'),
       ('Ajax Amsterdam', 'Eredivisie', 'Países Bajos'),
       ('Feyenoord', 'Eredivisie', 'Países Bajos'),
       ('Celtic FC', 'Scottish Premiership', 'Escocia'),
       ('Rangers FC', 'Scottish Premiership', 'Escocia'),
       ('Galatasaray SK', 'Süper Lig', 'Turquía'),
       ('Fenerbahçe SK', 'Süper Lig', 'Turquía'),
       ('FC Zenit Saint Petersburg', 'Premier League Rusa', 'Rusia'),
       ('Spartak Moscow', 'Premier League Rusa', 'Rusia'),
       ('SL Benfica', 'Primeira Liga', 'Portugal'),
       ('Besiktas JK', 'Süper Lig', 'Turquía'),
       ('SSC Napoli', 'Serie A', 'Italia'),
       ('Atlético Madrid', 'La Liga', 'España');

INSERT INTO usuarios (username, password)
VALUES ('test', '$2a$12$gtfH4UuESLcu6UpJGRxn0OttucpjsJqwPxhjW4uyl/ozDZc16KETO');

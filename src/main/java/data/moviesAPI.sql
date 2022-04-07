USE wesley;

CREATE TABLE if not exists movies
(
    id           int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title        varchar(255) UNIQUE,
    director     varchar(255),
    poster       varchar(255),
    year         int,
    genre        varchar(255),
    plot         varchar(1024),
    rating       int,
    actors       varchar(255)
);

INSERT INTO movies (title, director, poster, year, genre, plot, rating, actors)
VALUES  ('Gangs of New York',
         'Martin Scorsese',
         'https://m.media-amazon.com/images/M/MV5BNDg3MmI1ZDYtMDZjYi00ZWRlLTk4NzEtZjY4Y2U0NjE5YmRiXkEyXkFqcGdeQXVyNzAxMjE1NDg@._V1_SX300.jpg',
         2001,
         'Crime, Drama',
         'In 1862, Amsterdam Vallon returns to the Five Points area of New York City seeking revenge against Bill the Butcher, his father''s killer.',
         '8',
         'Leonardo DiCaprio, Cameron Diaz, Daniel Day-Lewis');


TRUNCATE movies;
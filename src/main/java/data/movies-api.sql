USE raymond;

CREATE TABLE IF NOT EXISTS movies
(
    id           int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title        varchar(255) UNIQUE,
    director     varchar(255),
    poster       varchar(255),
    dateReleased varchar(255),
    yearMade     int,
    genre        varchar(255),
    plot         varchar(1024),
    rating       varchar(2),
    imdb         double,
    runtime      int,
    actors       varchar(255)
);

INSERT INTO movies (title, director, poster, dateReleased, yearMade, genre, plot, rating, imdb, runtime, actors)
VALUES  ('Gangs of New York',
         'Martin Scorsese',
         'https://m.media-amazon.com/images/M/MV5BNDg3MmI1ZDYtMDZjYi00ZWRlLTk4NzEtZjY4Y2U0NjE5YmRiXkEyXkFqcGdeQXVyNzAxMjE1NDg@._V1_SX300.jpg',
         '20 Dec 2002',
         2001,
         'Crime, Drama',
         'In 1862, Amsterdam Vallon returns to the Five Points area of New York City seeking revenge against Bill the Butcher, his father''s killer.',
         'R',
         7.5,
         167,
         'Leonardo DiCaprio, Cameron Diaz, Daniel Day-Lewis');


TRUNCATE movies;

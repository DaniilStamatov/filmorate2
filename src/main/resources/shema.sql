drop table if exists film cascade;
drop table if exists genre cascade;
drop table if exists mpa cascade;
drop table if exists likes cascade;
drop table if exists users cascade;
drop table if exists film_genre cascade;
drop table if exists friends cascade;

CREATE TABLE IF NOT EXISTS mpa
(
    id INTEGER not null auto_increment,
    name VARCHAR(20) NOT NULL,
    CONSTRAINT mpa_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS film
(
      id integer not null auto_increment,
      name VARCHAR(100) NOT NULL,
      description VARCHAR(200) NOT NULL,
      mpa INTEGER NOT NULL,
      release_date DATE NOT NULL,
      duration INTEGER NOT NULL,
      likes_amount INTEGER,
      CONSTRAINT film_pk PRIMARY KEY (id),
      CONSTRAINT films_fk FOREIGN KEY (mpa) REFERENCES mpa
);




CREATE TABLE IF NOT EXISTS users
(
    id integer not null auto_increment,
    email VARCHAR(50) NOT  NULL,
    name VARCHAR(50),
    login VARCHAR(50) NOT NULL,
    birthday DATE,
    CONSTRAINT user_pk PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS genre
(
    id integer not null auto_increment,
    name VARCHAR(50),
    CONSTRAINT genre_pk PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS film_genre
(
    film_id INTEGER NOT NULL,
    genre_id INTEGER NOT NULL,
    CONSTRAINT film_fk FOREIGN KEY (film_id) REFERENCES film on update cascade on delete cascade,
    CONSTRAINT genre_fk FOREIGN KEY (genre_id) REFERENCES genre on update cascade on delete cascade
);

CREATE TABLE IF NOT EXISTS friends
(
    first_user_id INTEGER NOT NULL,
    second_user_id INTEGER NOT NULL,
    constraint FRIENDSHIP_PK
        primary key (first_user_id, second_user_id),
    CONSTRAINT first_user_fk FOREIGN KEY (first_user_id) REFERENCES users(id) on update cascade on delete cascade,
    CONSTRAINT second_user_fk FOREIGN KEY (second_user_id) REFERENCES users(id) on update cascade on delete cascade
);

CREATE TABLE IF NOT EXISTS likes
(
    film_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    CONSTRAINT user_like_fk FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT film_like_fk FOREIGN KEY(film_id) REFERENCES film(id)
);


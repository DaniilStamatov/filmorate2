package StamatovTeam.filmorate20.storage.film;

import StamatovTeam.filmorate20.model.Film;

import java.util.List;

public interface FilmStorage {

    Film addFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getFilms();

    Film removeFilm(Integer id);

    Film getFilmById(Integer id);

    void checkFilmExists(int id);

    List<Film> getMostLikedFilms(int limit);
}

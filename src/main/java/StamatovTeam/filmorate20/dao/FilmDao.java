package StamatovTeam.filmorate20.dao;

import StamatovTeam.filmorate20.model.Film;

import java.util.List;

public interface FilmDao {
    Film getFilmById(Integer id);

    List<Film> getFilms();

    void addFilm(Film film);

    Film updateFilm(Film film);

    Film removeFilm(int id);
}

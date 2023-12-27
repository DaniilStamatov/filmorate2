package StamatovTeam.filmorate20.dao;

import StamatovTeam.filmorate20.model.Film;
import StamatovTeam.filmorate20.model.FilmGenre;
import StamatovTeam.filmorate20.model.Genre;

import java.util.List;

public interface FilmGenreDao {

    List<Genre> getFilmGenre(int id);

    Film importFilmGenre(Film film);

    void deleteFilmGenresByFilmId(int id);

    List<FilmGenre> getAllGenres();
}

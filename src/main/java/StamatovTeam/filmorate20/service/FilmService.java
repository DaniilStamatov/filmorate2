package StamatovTeam.filmorate20.service;

import StamatovTeam.filmorate20.dao.*;
import StamatovTeam.filmorate20.exceptions.EntityAlreadyExistsException;
import StamatovTeam.filmorate20.exceptions.EntityDoesNotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import StamatovTeam.filmorate20.model.Film;
import org.springframework.stereotype.Service;
import StamatovTeam.filmorate20.storage.film.FilmStorage;
import StamatovTeam.filmorate20.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;
    private final FilmGenreDao filmGenreDao;
    private final GenreDao genreDao;
    private final LikeDao likeDao;
    private final MpaDao mpaDao;


    public Film addFilm(Film film){

        filmStorage.addFilm(film);
        if(film.getGenres()!=null){
            filmGenreDao.importFilmGenre(film);
        }
        return film;
    }

    public Film updateFilm(Film film){
        return filmStorage.updateFilm(film);
    }

    public Film removeFilm(Integer id){
        return filmStorage.removeFilm(id);
    }

    public Film getFilm(Integer id){
        Film film = filmStorage.getFilmById(id);
        film.setMpa(mpaDao.findById(film.getMpa().getId()));
        film.setLikes(likeDao.getAllFilmLikes(id));
        film.setGenres(filmGenreDao.getFilmGenre(id));
        return film;
    }

    public void likeFilm(int filmId, int userId){
        userStorage.getUser(userId);
        filmStorage.getFilmById(filmId);
        likeDao.addLike(filmId, userId);
    }

    public void deleteLikeFromFilm(int id, int userId){
        filmStorage.checkFilmExists(id);
        userStorage.checkUserDoesExist(userId);
        likeDao.deleteLike(id, userId);
    }

    public List<Film> getAllFilms(){

        List<Film> films = filmStorage.getFilms();
        films.forEach(film -> {
            film.setMpa(mpaDao.findById(film.getMpa().getId()));
            film.setLikes(likeDao.getAllFilmLikes(film.getId()));
            film.setGenres(filmGenreDao.getFilmGenre(film.getId()));
        });
        return films;
    }

    public List<Film> getMostLikedFilms(int size){
        if(filmStorage.getFilms().isEmpty()){
            return null;
        }

        return filmStorage.getFilms().stream()
                .sorted((o1,o2)-> o2.getLikesAmount()-o1.getLikesAmount())
                .limit(size)
                .collect(Collectors.toList());
    }
}

package StamatovTeam.filmorate20.service;

import StamatovTeam.filmorate20.dao.FilmDao;
import StamatovTeam.filmorate20.dao.FilmGenreDao;
import StamatovTeam.filmorate20.dao.GenreDao;
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
        return filmStorage.getFilmById(id);
    }

    public void likeFilm(int id, int userId){
        filmStorage.checkFilmExists(id);
        userStorage.checkUserDoesExist(userId);

        Film film = filmStorage.getFilmById(id);
        if(film.getLikes()==null){
            film.setLikes(new HashSet<>());
        }
        if(film.getLikes().contains(userId)){
            throw new EntityAlreadyExistsException("Пользователь уже поставил лайк");
        }

        film.getLikes().add(userId);
        film.setLikesAmount(film.getLikes().size());
    }

    public void deleteLikeFromFilm(int id, int userId){
        filmStorage.checkFilmExists(id);
        userStorage.checkUserDoesExist(userId);
        Film film = filmStorage.getFilmById(id);
        if(film.getLikes()==null){
            film.setLikes(new HashSet<>());
        }
        if(!film.getLikes().contains(userId)){
            throw new EntityDoesNotExistException("Пользователь не ставил лайк этому фильму");
        }

        film.getLikes().remove(userId);
        film.setLikesAmount(film.getLikes().size());
    }

    public List<Film> getAllFilms(){
        return filmStorage.getFilms();
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

package StamatovTeam.filmorate20.storage.film;

import StamatovTeam.filmorate20.exceptions.EntityDoesNotExistException;
import StamatovTeam.filmorate20.exceptions.InvalidPathVariableOrParameterException;
import lombok.extern.slf4j.Slf4j;
import StamatovTeam.filmorate20.model.Film;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class InMemoryFilmStorage implements FilmStorage{
    private final Map<Integer,Film> films = new HashMap<>();

    private final static LocalDate firstReleaseDate = LocalDate.of(1895,12,28);

    private Integer filmId = 1;

    private Integer generateId(){
        filmId++;
        return filmId;
    }

    @Override
    public Film addFilm(Film film) {
        checkFilmDate(film);
        int filmID = generateId();
        film.setId(filmID);
        films.put(filmID,film);
        log.info("Добавлен новый фильм, {}", film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        checkFilmExists(film.getId());
            checkFilmDate(film);
            films.put(film.getId(), film);
            log.info("Фильм ,{}, Был обновлен", film);
            return film;
    }


    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(Integer id){
        checkFilmExists(id);
        return films.get(id);
    }
    @Override
    public Film removeFilm(Integer id) {
        checkFilmExists(id);
        return films.remove(id);
    }

    private void checkFilmDate(Film film){
        if(film.getReleaseDate().isBefore(firstReleaseDate)){
            log.error("Дата релиза не должна быть раньше 28 декабря 1895!, {}", film);
        }
    }

    @Override
    public void checkFilmExists(int id){
        if(!films.containsKey(id)){
            log.error("Фильм не был найден,{}", id);
            throw new EntityDoesNotExistException("Фильм не был найден");
        }
        if (id < 1) {
            String exceptionMessage = String.format("Фильм с id = %d не может существовать", id);
            log.warn("Ошибка при запросе фильма. Сообщение исключения: {}",
                    exceptionMessage);
            throw new InvalidPathVariableOrParameterException("id", exceptionMessage);
        }
    }
}

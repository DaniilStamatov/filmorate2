package controllers;

import jakarta.validation.Valid;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@Slf4j
public class FilmController {
    private Map<Integer,Film> films = new HashMap();
    private final static LocalDate firstReleaseDate = LocalDate.of(1895,12,28);
    private Integer filmId;

    private Integer generateId(){
        filmId++;
        return filmId;
    }

    @GetMapping
    public List<Film> getFilms(){
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film){
        checkFilmDate(film);
        int filmID = generateId();
        film.setId(filmID);
        films.put(filmID,film);
        log.info("Добавлен новый фильм, {}", film);
        return film;
    }


    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film){
        if(!films.containsKey(film.getId())){
            log.error("Данного фильма не существует,{}", film);
            throw new IllegalStateException();
        }else {
            checkFilmDate(film);
            films.put(film.getId(), film);
            log.info("Фильм ,{}, Был обновлен", film);
            return film;
        }

    }


    private void checkFilmDate(Film film){
        if(film.getReleaseDate().isBefore(firstReleaseDate)){
            log.error("Дата релиза не должна быть раньше 28 декабря 1895!, {}", film);

        }
    }
}

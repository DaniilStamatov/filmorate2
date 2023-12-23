package StamatovTeam.filmorate20.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import StamatovTeam.filmorate20.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import StamatovTeam.filmorate20.service.FilmService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    @Autowired
    public FilmController(FilmService filmService){
        this.filmService = filmService;
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id){
        log.info(String.format("Получен GET запрос '/films/%d'", id));
        return filmService.getFilm(id);
    }

    @GetMapping
    public List<Film> getFilms(){
        log.info("Получен GET запрос /films");
        return filmService.getAllFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable int id, @PathVariable int userId){
        log.info(String.format("Получен PUT запрос '/films/%d/like/%d'", id,userId));
        filmService.likeFilm(id,userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId){
        log.info(String.format("Получен DELETE запрос '/films/%d/like/%d'", id,userId));
        filmService.deleteLikeFromFilm(id,userId);
    }

    @GetMapping("/popular")
    public List<Film> getMostPopularFilms(@RequestParam(defaultValue = "10") int count){
        log.info(String.format("Получен Get запрос '/films/popular?count=%d'", count));
        return filmService.getMostLikedFilms(count);
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film){
        log.info("Получен POST запрос");
        return filmService.addFilm(film);
    }


    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film){
        log.info("Получен PUT запрос ");
        return filmService.updateFilm(film);
    }

}

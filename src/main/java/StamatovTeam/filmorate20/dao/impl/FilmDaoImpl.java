package StamatovTeam.filmorate20.dao.impl;

import StamatovTeam.filmorate20.dao.FilmDao;
import StamatovTeam.filmorate20.exceptions.EntityDoesNotExistException;
import StamatovTeam.filmorate20.model.Film;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component

public class FilmDaoImpl implements FilmDao {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Film getFilmById(Integer id) {
        String sql = "SELECT * FROM film WHERE id = ?";
        try{
            Film film = jdbcTemplate.queryForObject(sql,  (rs, rowNum) -> Film.makeFilm(rs),id);
            if(film!=null){
                log.info("Найден фильм с id {} и названием {}" , film.getId(), film.getName());
            }
            return film;
        } catch (EmptyResultDataAccessException e){
            log.debug("Фильм с данным id {} не найден" , id);
            throw new EntityDoesNotExistException(String.format("Фильм с id %d не существует", id));
        }

    }

    @Override
    public List<Film> getFilms() {
        String sql = "SELECT * FROM film";
        return jdbcTemplate.query(sql, (rs, rowNum) -> Film.makeFilm(rs));
    }

    @Override
    public void addFilm(Film film) {
        String sql = "INSERT INTO film (name, description, mpa_id, release_date, duration, likes_amount) VALUES(?,?,?,?,?,?)";
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }
}

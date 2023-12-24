package StamatovTeam.filmorate20.dao.impl;

import StamatovTeam.filmorate20.dao.FilmDao;
import StamatovTeam.filmorate20.exceptions.EntityDoesNotExistException;
import StamatovTeam.filmorate20.model.Film;
import StamatovTeam.filmorate20.storage.film.FilmStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Component
public class FilmDaoImpl implements FilmStorage {
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
    public void checkFilmExists(int id) {

    }

    @Override
    public List<Film> getFilms() {
        String sql = "SELECT * FROM film";
        return jdbcTemplate.query(sql, (rs, rowNum) -> Film.makeFilm(rs));
    }

    @Override
    public Film addFilm(Film film) {
        String sql = "INSERT INTO film (name, description, mpa_id, release_date, duration, likes_amount) VALUES(?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[] {"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setInt(3, film.getMpa().getId());
            stmt.setDate(4, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(5, (int)film.getDuration().toSeconds());
            stmt.setInt(6, 0);
            return  stmt;
        }, keyHolder);

        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sql = "UPDATE film SET name = ?, description = ?, mpa_id = ?, release_date = ?, duration = ?, likes_amount = ? WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getMpa().getId(),
                Date.valueOf(film.getReleaseDate()),
                (int)film.getDuration().toSeconds(),
                film.getLikesAmount());

        if(updatedRows == 0){
            log.debug("Фильм  с таким идентификатором {} не найден", film.getId());
            throw new EntityDoesNotExistException(String.format("Фильм  с идентификатором %d не найден ", film.getId()));
        }
        return film;
    }

    @Override
    public Film removeFilm(Integer id) {
        String sql = "DELETE FROM film WHERE id  = ?";
        Film film = getFilmById(id);
        jdbcTemplate.update(sql, id);
        return film;
    }


}

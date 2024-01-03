package StamatovTeam.filmorate20.dao.impl;

import StamatovTeam.filmorate20.dao.GenreDao;
import StamatovTeam.filmorate20.exceptions.EntityDoesNotExistException;
import StamatovTeam.filmorate20.model.Genre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Genre findById(Integer id) {
        String sql = "SELECT * FROM GENRE WHERE id = ?";
        try{
            Genre mpa = jdbcTemplate.queryForObject(sql,(rs, rowNum)-> Genre.makeGenre(rs), id);
            if(mpa!= null){
                log.info("Найден жанр c id: {}, name = {}", mpa.getId(), mpa.getName());
            }
            return mpa;
        } catch (EmptyResultDataAccessException e){
            log.info("Жанр с таким id {} не существует ", id);
            throw new EntityDoesNotExistException(String.format("Такого жанра не существует %d", id));
        }
    }



    @Override
    public List<Genre> getAllGenres() {
        String sql = "SELECT * FROM genre";
        return jdbcTemplate.query(sql,  (rs, rowNum) -> Genre.makeGenre(rs));

        }

    @Override
    public List<Genre> getAllGenresFromFilm(Integer filmId) {
        return null;
    }

}

package StamatovTeam.filmorate20.dao.impl;

import StamatovTeam.filmorate20.dao.FilmGenreDao;
import StamatovTeam.filmorate20.model.Film;
import StamatovTeam.filmorate20.model.FilmGenre;
import StamatovTeam.filmorate20.model.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FilmGenreDaoImpl implements FilmGenreDao {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Genre getFilmGenre(int id) {
        String sql = "SELECT g.id, g.name FROM film_genre as fg JOIN genre as g ON fg.genre_id = g.id";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum)-> Genre.makeGenre(rs));
    }

    @Override
    public Film importFilmGenre(Film film) {
        String sql = "INSERT INTO film_genre (film_id, genre_id) VALUES(?,?)";
        List<Genre> filmGenres = film.getGenres()
                .stream()
                .distinct()
                .toList();

       jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
           @Override
           public void setValues(PreparedStatement ps, int i) throws SQLException {
               ps.setInt(0,film.getId());
               ps.setInt(1,filmGenres.get(i).getGenreId());
           }

           @Override
           public int getBatchSize() {
               return filmGenres.size();
           }
       });
       return film;
    }

    @Override
    public void deleteFilmGenresByFilmId(int id) {
        String sql = "DELETE FROM film_genre WHERE film_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<FilmGenre> getAllGenres() {
        String sql = "SELECT * FROM film_genre";
        return jdbcTemplate.query(sql, (rs, rowNum)-> FilmGenre.makeFilmGenre(rs));
    }
}

package StamatovTeam.filmorate20.dao.impl;

import StamatovTeam.filmorate20.dao.LikeDao;
import StamatovTeam.filmorate20.exceptions.EntityDoesNotExistException;
import StamatovTeam.filmorate20.model.Like;
import StamatovTeam.filmorate20.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class LikeDaoImpl implements LikeDao {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public void addLike(int filmId, int userId) {
        String sql = "MERGE INTO LIKES AS L USING (VALUES(?,?)) " +
                "AS S(filmId, userId) ON l.user_id = s.userId and l.film_id = s.filmId " +
                "WHEN NOT MATCHED THEN INSERT VALUES(s.filmId, s.userId)";

        try {
            jdbcTemplate.update(sql, filmId, userId);
        } catch (DataIntegrityViolationException e){
            log.debug("Пользователь с {} id или фильм с {} id", userId, filmId);
            throw new EntityDoesNotExistException("Пользователя или фильма не существует");
        }

        String sql2 = "UPDATE FILM SET likes_amount = likes_amount + 1 WHERE id = ?";
        jdbcTemplate.update(sql2, filmId);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        if(userId<0 ){
            log.debug("Пользователь с отрицательным id {} не может существовать.", userId);
            throw new EntityDoesNotExistException(
                    String.format("Пользователь с отрицательным id %d не может существовать.", userId));
        }
        String sql = "DELETE FROM likes WHERE film_id = ? and user_id = ?";
        try {
            jdbcTemplate.update(sql, filmId, userId);
        } catch (DataIntegrityViolationException e) {
            log.debug("Фильм с id = {} или Пользователь с id = {} не найден.", filmId, userId);
            throw new EntityDoesNotExistException(
                    String.format("Фильм с id = %d или Пользователь с id = %d не найден.", filmId, userId));
        }

        String sql2 = "UPDATE FILM SET likes_amount = likes_amount -1 WHERE id = ?";

        jdbcTemplate.update(sql2, filmId);
    }

    @Override
    public List<Like> getLikesById(int userId) {
        String sql = "SELECT * from likes where user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> Like.makeLike(rs), userId);
    }

    @Override
    public List<Like> getAllLikes() {
        String sql = "SELECT * from likes";
        return jdbcTemplate.query(sql, (rs, rowNum) -> Like.makeLike(rs));
    }

    @Override
    public List<User> getAllFilmLikes(int filmId) {
        String sql = "SELECT u.* from users as u left join likes as l on u.id = l.user_id where l.film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> User.makeUser(rs), filmId);
    }
}

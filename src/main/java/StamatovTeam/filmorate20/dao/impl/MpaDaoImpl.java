package StamatovTeam.filmorate20.dao.impl;

import StamatovTeam.filmorate20.dao.MpaDao;
import StamatovTeam.filmorate20.exceptions.EntityDoesNotExistException;
import StamatovTeam.filmorate20.model.Mpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MpaDaoImpl implements MpaDao {

    private final JdbcTemplate jdbcTemplate;
    @Override
    public Mpa findById(Integer id) {
        String sql = "SELECT id, name FROM mpa WHERE id = ?";
        try {
            Mpa mpa =  jdbcTemplate.queryForObject(sql, (rs, rowNum) -> Mpa.makeMpa(rs), id);

            if(mpa!=null){
                log.info("Найден MPA - рейтинг с id {} и названием {}", mpa.getId(), mpa.getName());
            }
            return mpa;
        } catch (EmptyResultDataAccessException e){
            log.debug("Не найден Mpa с таким id {}" , id);
            throw new EntityDoesNotExistException("Не найден Mpa с таким id" + id);
        }
    }

    @Override
    public List<Mpa> findAll() {
        String sql = "SELECT * FROM mpa";
        return jdbcTemplate.query(sql, (rs, rowNum) -> Mpa.makeMpa(rs));
    }
}

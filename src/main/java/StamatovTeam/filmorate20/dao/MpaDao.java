package StamatovTeam.filmorate20.dao;

import StamatovTeam.filmorate20.model.Mpa;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MpaDao {
    String findById(Integer id);
    List<Mpa> findAll();
}

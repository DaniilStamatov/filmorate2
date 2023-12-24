package StamatovTeam.filmorate20.dao;

import StamatovTeam.filmorate20.model.Genre;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GenreDao {
    Genre findById(Integer id);

    List<Genre> getAllGenres();

    List<Genre> getAllGenresFromFilm(Integer filmId);


}

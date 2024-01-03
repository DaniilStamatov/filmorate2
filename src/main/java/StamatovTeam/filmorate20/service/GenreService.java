package StamatovTeam.filmorate20.service;

import StamatovTeam.filmorate20.dao.GenreDao;
import StamatovTeam.filmorate20.model.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreService {
    private final GenreDao genreDao;
    public List<Genre> getAllGenresFromDb() {
        return genreDao.getAllGenres();
    }

    public Genre getGenreByIdFromDb(int id) {
        return genreDao.findById(id);
    }
}

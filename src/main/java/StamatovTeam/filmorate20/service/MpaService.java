package StamatovTeam.filmorate20.service;

import StamatovTeam.filmorate20.dao.MpaDao;
import StamatovTeam.filmorate20.model.Mpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MpaService {
    private final MpaDao mpaDao;

    public List<Mpa> getAllMpaFromDb() {
        return mpaDao.findAll();
    }

    public Mpa getMpaByIdFromDb(int id) {
        return mpaDao.findById(id);
    }
}

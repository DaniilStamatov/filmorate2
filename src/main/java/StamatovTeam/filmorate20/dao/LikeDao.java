package StamatovTeam.filmorate20.dao;

import StamatovTeam.filmorate20.model.Like;
import StamatovTeam.filmorate20.model.User;

import java.util.List;

public interface LikeDao {
    void addLike(int filmId, int userId);
    void deleteLike(int filmId, int userId);
    List<Like> getLikesById(int userId);
    List<Like> getAllLikes();

    List<User> getAllFilmLikes(int filmId);
}

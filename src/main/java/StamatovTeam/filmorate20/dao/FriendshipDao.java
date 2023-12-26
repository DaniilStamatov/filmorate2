package StamatovTeam.filmorate20.dao;

import StamatovTeam.filmorate20.model.Friendship;
import StamatovTeam.filmorate20.model.User;

import java.util.List;

public interface FriendshipDao {

    void addFriends(int user1, int user2);
    void deleteFriend(int user1, int user2);

    List<User> getAllFriends(int id);

    List<User> getMutualFriends(int user1, int user2);

    List<Friendship> getAllFriends();
}

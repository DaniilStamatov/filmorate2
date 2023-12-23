package StamatovTeam.filmorate20.storage.user;

import StamatovTeam.filmorate20.model.User;

import java.util.List;

public interface UserStorage {
    User addUser(User user);

    User updateUser(User user);

    List<User> getUsers();

    User removeUser(Integer id);

    User getUser(Integer id);

    void checkUserDoesExist(Integer id);
}

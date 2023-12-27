package StamatovTeam.filmorate20.service;

import StamatovTeam.filmorate20.dao.FriendshipDao;
import StamatovTeam.filmorate20.exceptions.EntityDoesNotExistException;
import StamatovTeam.filmorate20.exceptions.EntityAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import StamatovTeam.filmorate20.model.User;
import org.springframework.stereotype.Service;
import StamatovTeam.filmorate20.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserStorage userStorage;
    private final FriendshipDao friendshipStorage;

    public  User addUser(User user){
        setUserNameIfNeeded(user);
        return userStorage.addUser(user);
    }

    public User updateUser(User user){
        return userStorage.updateUser(user);
    }

    public List<User> getUsers(){
        List<User> users = userStorage.getUsers();
        users.forEach(user->{
            user.setFriends(friendshipStorage.getAllFriends(user.getId()));
        });
        return users;
    }

    public User removeUser(Integer id){
        userStorage.checkUserDoesExist(id);
        return userStorage.removeUser(id);
    }

    public void addToFriends(int id, int friendId){
        userStorage.checkUserDoesExist(id);
        userStorage.checkUserDoesExist(friendId);
        userStorage.getUser(id);
        userStorage.getUser(friendId);
        friendshipStorage.addFriends(id, friendId);
    }

    public void deleteUsersFromFriends(int id, int friendId){
            friendshipStorage.deleteFriend(id, friendId);
    }

    public List<User> getMutualFriends(int id, int friendId){
       return friendshipStorage.getMutualFriends(id, friendId);
    }

    public List<User> getUserFriends(int id){
       return friendshipStorage.getAllFriends(id);
    }

    public User getUser(Integer id){
        userStorage.checkUserDoesExist(id);
        return userStorage.getUser(id);
    }

    private void setUserNameIfNeeded(User user) {
        if (user.getName()==null||user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }



}

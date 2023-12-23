package StamatovTeam.filmorate20.service;

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

    public  User addUser(User user){
        setUserNameIfNeeded(user);
        return userStorage.addUser(user);
    }

    public User updateUser(User user){
        return userStorage.updateUser(user);
    }

    public List<User> getUsers(){
        return userStorage.getUsers();
    }

    public User removeUser(Integer id){
        userStorage.checkUserDoesExist(id);
        return userStorage.removeUser(id);
    }

    public void addToFriends(int id, int friendId){
        userStorage.checkUserDoesExist(id);
        userStorage.checkUserDoesExist(friendId);
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);


        if(user.getFriends()==null){
            user.setFriends(new HashSet<>());
        }

        if(!user.getFriends().add(friendId)){
            throw new EntityAlreadyExistsException("Пользователи уже в друзьях");
        }

        if(friend.getFriends()==null){
            friend.setFriends(new HashSet<>());
        }

        friend.getFriends().add(id);
    }

    public void deleteUsersFromFriends(int id, int friendId){
        userStorage.checkUserDoesExist(id);
        userStorage.checkUserDoesExist(friendId);

        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);

        if(user.getFriends()==null){
            user.setFriends(new HashSet<>());
        }

        if(friend.getFriends()==null){
            friend.setFriends(new HashSet<>());
        }

        if(!user.getFriends().remove(friendId)){
            throw new EntityDoesNotExistException("Пользователи не в друзьях");
        }

        friend.getFriends().remove(id);
    }

    public List<User> getMutualFriends(int id, int friendId){
        userStorage.checkUserDoesExist(id);
        userStorage.checkUserDoesExist(friendId);

        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);

        Set<Integer> userFriends = userStorage.getUser(id).getFriends();
        Set<Integer> friendFriends = userStorage.getUser(friendId).getFriends();

        if(userFriends ==null){
            return new ArrayList<>();
        }

        return userFriends.stream()
                .filter(friendFriends::contains)
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getUserFriends(int id){
        userStorage.checkUserDoesExist(id);
        Set<Integer> userFriends = userStorage.getUser(id).getFriends();
        if(userFriends ==null){
            return new ArrayList<>();
        }

        return userFriends.stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
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

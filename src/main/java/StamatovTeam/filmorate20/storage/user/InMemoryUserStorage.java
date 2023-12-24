package StamatovTeam.filmorate20.storage.user;

import StamatovTeam.filmorate20.exceptions.EntityDoesNotExistException;
import StamatovTeam.filmorate20.exceptions.InvalidPathVariableOrParameterException;
import StamatovTeam.filmorate20.exceptions.UserBirthdayException;
import StamatovTeam.filmorate20.exceptions.UserLoginValidationException;
import lombok.extern.slf4j.Slf4j;
import StamatovTeam.filmorate20.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Slf4j
public class InMemoryUserStorage implements UserStorage{

    private final Map<Integer, User> users = new HashMap<>();
    private Integer userId = 1;

    private Integer setUserId(){
        userId++;
        return userId;
    }

    @Override
    public User addUser(User user) {
        validateUser(user);
        int userID = setUserId();
        user.setId(userID);
        users.put(userID, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if(!users.containsKey(user.getId())){
            log.error("Пользователь не был найден,{}", user);
            throw new EntityDoesNotExistException("Пользователь не был найден");
        }
        validateUser(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User removeUser(Integer id) {
        checkUserDoesExist(id);
        return users.remove(id);
    }

    @Override
    public User getUser(Integer id) {
        checkUserDoesExist(id);
        return users.get(id);
    }

    @Override
    public void checkUserDoesExist(Integer id) {
        if(!users.containsKey(id)){
            log.error("Пользователь не был найден,{}", id);
            throw new EntityDoesNotExistException("Пользователь не был найден");
        }

        if(id<1){
            log.error("Пользователь с ID<1 не может существовать");
            throw new InvalidPathVariableOrParameterException("Id", "Пользователь с ID<1 не может существовать");
        }
    }

    private void validateUser(User user){
        if(user.getLogin().isBlank()||user.getLogin().contains(" ")){
            log.error("Логин не может быть пустым и содержать пробелы, {}" , user);
            throw new UserLoginValidationException("Логин не может быть пустым и содержать пробелы");
        }

        if(user.getName().isBlank()){
            user.setName(user.getLogin());
        }

        if(user.getBirthday().isAfter(LocalDate.now())){
            log.error("Дата рождения не должна быть в будущем");
            throw new UserBirthdayException("Дата рождения не должна быть в будущем");
        }
    }

}

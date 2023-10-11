package controllers;


import exceptions.UserBirthdayException;
import exceptions.UserLoginValidationException;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
        private Map<Integer, User> users = new HashMap<>();
        private Integer userId;

        private Integer setUserId(){
            userId++;
            return userId;
        }

        @GetMapping
        public List<User> getUsers(){
            return new ArrayList<>(users.values());
        }

        @PostMapping
        public User addUser(User user){
            validateUser(user);
            int userID = setUserId();
            user.setId(userID);
            users.put(userID, user);
            return user;
        }

        @PutMapping
        public User updateUser(User user){
            if(!users.containsKey(user.getId())){
                log.error("Пользователь не был найден,{}", user);
                throw new RuntimeException("Пользователь не был найден");
            }
            validateUser(user);
            users.put(user.getId(), user);
            return user;
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

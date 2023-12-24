package StamatovTeam.filmorate20.controllers;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import StamatovTeam.filmorate20.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import StamatovTeam.filmorate20.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id){
        log.info("Получен GET запрос");
        return userService.getUser(id);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user){
        log.info("Получен POST запрос");
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user){
        return userService.updateUser(user);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable int id){
        return userService.getUserFriends(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteUserFromFriends(@PathVariable int id, @PathVariable int friendId){
        userService.deleteUsersFromFriends(id, friendId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId){
        userService.addToFriends(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{friendId}")
    public List<User> getMutualFriends(@PathVariable int id, @PathVariable int friendId){
        log.info("Получены общие друзья с пользователем c id = , {}", friendId);
       return userService.getMutualFriends(id, friendId);
    }
}

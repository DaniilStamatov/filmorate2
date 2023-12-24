package StamatovTeam.filmorate20.dao.impl;

import StamatovTeam.filmorate20.model.User;
import StamatovTeam.filmorate20.storage.user.UserStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDao implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO users (name, email, login, birthday)VALUES(?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
            stmt.setString(1,user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3,user.getLogin());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);

        user.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User removeUser(Integer id) {
        return null;
    }

    @Override
    public User getUser(Integer id) {
        return null;
    }

    @Override
    public void checkUserDoesExist(Integer id) {

    }
}

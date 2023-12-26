package StamatovTeam.filmorate20.dao.impl;

import StamatovTeam.filmorate20.dao.FriendshipDao;
import StamatovTeam.filmorate20.exceptions.EntityDoesNotExistException;
import StamatovTeam.filmorate20.model.User;
import StamatovTeam.filmorate20.storage.user.UserStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
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
public class UserDaoImpl implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final FriendshipDao friendshipDao;
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
        String sql  = "UPDATE users SET name = ? , email = ? login = ? , birthday = ? WHERE id =?";
        int updatedRows = jdbcTemplate.update(sql, user.getName(),  user.getEmail(), user.getLogin(), Date.valueOf(user.getBirthday()), user.getId());

        if(updatedRows == 0) {
            log.debug("Пользователя с id {} не существует", user.getId());
            throw new EntityDoesNotExistException("Пользователя с id " + user.getId() + " не существует");
        } else {
            return user;
        }

    }

    @Override
    public List<User> getUsers() {
        String sql = "SELECT id, name, email, login, birthday FROM users";
        return jdbcTemplate.query(sql, (rs,rowNum)-> User.makeUser(rs));
    }

    @Override
    public User removeUser(Integer id) {
        String sql = "DELETE FROM users WHERE id =?";
        User user = getUser(id);
        jdbcTemplate.update(sql, id);
        return user;
    }

    @Override
    public User getUser(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ? LIMIT 1";
        try {
            User user = jdbcTemplate.queryForObject(sql, (rs, rowNum)-> User.makeUser(rs), id);

            if (user != null) {
                log.info("Найден пользователь с id {}", user.getId());

                user.setFriends(getFriendsOfUser(id));
            }
            return user;
        } catch (EmptyResultDataAccessException e) {
             log.debug("Пользователя с id {} не существует", id);
             throw new EntityDoesNotExistException("Пользователя с id " + id + " не существует");
        }
    }

    public List<User> getFriendsOfUser(int id){
        String sql = "SELECT UF.id, UF.email, UF.login, UF.name, UF.birthday" +
                " FROM friends as f LEFT JOIN users as UF ON UF.id = f.second_user_id" +
                " WHERE f.first_user_id = ?";

      return jdbcTemplate.query(sql, (rs, rowNum) -> User.makeUser(rs), id);
    }

    @Override
    public void checkUserDoesExist(Integer id) {

    }
}

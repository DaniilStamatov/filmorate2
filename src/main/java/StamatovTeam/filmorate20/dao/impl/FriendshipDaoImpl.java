package StamatovTeam.filmorate20.dao.impl;

import StamatovTeam.filmorate20.dao.FriendshipDao;
import StamatovTeam.filmorate20.exceptions.EntityDoesNotExistException;
import StamatovTeam.filmorate20.model.Friendship;
import StamatovTeam.filmorate20.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class FriendshipDaoImpl implements FriendshipDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriends(int friend1, int friend2) {
        String sql = "MERGE INTO friends AS F USING (VALUES(?,?)) AS S (friend1, friend2) ON " +
                "F.first_user_id = S.friend1 AND F.second_user_id = S.friend2 " +
                "WHEN NOT MATCHED THEN INSERT VALUES(S.friend1, S.friend2)";

        try{
            jdbcTemplate.update(sql, friend1, friend2);
        } catch (DataIntegrityViolationException e){
            log.debug("Пользователи с id {} или {} не найдены", friend1, friend2);
            throw new EntityDoesNotExistException(String.format("Пользователь с идентификатором %d или %d не найден.", friend1, friend2));
        }
    }

    @Override
    public void deleteFriend(int user1, int user2) {
        String sql = "MERGE INTO friends AS F USING (VALUES(?, ?)) AS S(friend1, friend2) ON " +
                "F.first_name_id = S.friend1 and F.second_name_id = S.friend2 " +
                "WHEN MATCHED THEN DELETE";
        jdbcTemplate.update(sql, user1, user2);
    }

    @Override
    public List<User> getAllFriends(int id) {
        String sql = "SELECT  id, email,login,name,birthday FROM users WHERE id = ?";
        try {
            jdbcTemplate.query(sql , (rs, rowNum) -> User.makeUser(rs), id);
        } catch (EmptyResultDataAccessException e){
            log.debug("Пользователи с id {} не найден", id);
            throw new EntityDoesNotExistException(String.format("Пользователь с идентификатором %d  не найден.",id));
        }

        String friendSql = "SELECT  UF.id, UF.email,UF.login,UF.name,UF.birthday FROM friends as f LEFT JOIN users as UF ON UF.id = f.second_user_id WHERE f.first_user_id = ?";
        return jdbcTemplate.query(friendSql, (rs, rowNum) -> User.makeUser(rs), id);
    }

    @Override
    public List<User> getMutualFriends(int user1, int user2) {
        String sql = "SELECT * FROM friends AS f LEFT JOIN users as UF ON UF.ID = f.second_user_id " +
                "WHERE f.first_user_id = ? AND f.second_user_id in (SELECT second_user_id FROM friends WHERE first_user_id = ?)";

        return jdbcTemplate.query(sql, (rs, rowNum) -> User.makeUser(rs), user1, user2);
    }

    @Override
    public List<Friendship> getAllFriends() {
        String sql = "SELECT * FROM friends";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFriendShip(rs));
    }


    private Friendship makeFriendShip(ResultSet rs) throws SQLException {
        return Friendship.builder()
                .friend1Id(rs.getInt("first_user_id"))
                .friend2Id(rs.getInt("second_user_id"))
                .build();
    }


}

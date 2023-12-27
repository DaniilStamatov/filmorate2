package StamatovTeam.filmorate20.model;

import lombok.Builder;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Builder
public class Like {
    private int filmId;
    private int userId;

    public static Like makeLike(ResultSet rs) throws SQLException {
        return Like.builder()
                .filmId(rs.getInt("film_id"))
                .userId(rs.getInt("user_id"))
                .build();
    }

}

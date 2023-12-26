package StamatovTeam.filmorate20.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
@Builder
public class Mpa {
    private Integer id;
    private String name;

    public Mpa(int id){
        this.id = id;
    }

    public static Mpa makeMpa(ResultSet rs) throws SQLException {
        return Mpa.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}

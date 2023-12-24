package StamatovTeam.filmorate20.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Builder
@AllArgsConstructor
public class FilmGenre {
    private int filmId;
    private int genreId;

    public static FilmGenre makeFilmGenre(ResultSet rs) throws SQLException {
        return FilmGenre.builder()
                .filmId(rs.getInt("film_id"))
                .genreId(rs.getInt("genre_id"))
                .build();
    }
}

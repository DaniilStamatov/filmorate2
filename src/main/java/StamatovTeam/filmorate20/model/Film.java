package StamatovTeam.filmorate20.model;

import StamatovTeam.filmorate20.util.DurationPositiveOrZero;
import StamatovTeam.filmorate20.util.FilmDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForReadableInstant;
import org.springframework.web.servlet.tags.form.SelectTag;

import javax.print.attribute.standard.MediaPrintableArea;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Film {
    private  int id;
    @NotBlank(message = "имя не может быть пустым")
    private String name;
    @Size(max = 200, message = "Длина описания не должна превышать 200 символов")
    private String description;
    @FilmDate
    private LocalDate releaseDate;

    @DurationPositiveOrZero
    private Duration duration;
    private Mpa mpa;
    private List<Genre> genres;
    private List<User> likes;
    private int likesAmount;

    public static Film makeFilm(ResultSet rs) throws SQLException {
        return builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .duration(Duration.ofSeconds(rs.getInt("duration")))
                .mpa(new Mpa(rs.getInt("mpa")))
                .likesAmount(rs.getInt("likes_amount"))
                .build();
    }
}

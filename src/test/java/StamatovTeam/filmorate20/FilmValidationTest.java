package StamatovTeam.filmorate20;

import StamatovTeam.filmorate20.controllers.FilmController;
import StamatovTeam.filmorate20.model.Film;
import org.junit.jupiter.api.BeforeAll;

import java.time.Duration;
import java.time.LocalDate;

public class FilmValidationTest {
    private static Film titanicWithoutName;
    private static Film titanicWithTooLongDescription;
    private static Film titanicWithNegativeDuration;
    private static Film titanicWithTooOldReleaseDate;
    public static FilmController filmController;

    @BeforeAll
    static void beforeAll(){
        titanicWithoutName = Film.builder()
                .id(1)
                .description("Test description")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

    }
}

package StamatovTeam.filmorate20.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = FilmDateValidator.class)
@Documented
public @interface FilmDate {

    String message() default "{Слишком старая дата релиза. Можно добавить фильмы с датой релиза после 28.12.1895}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
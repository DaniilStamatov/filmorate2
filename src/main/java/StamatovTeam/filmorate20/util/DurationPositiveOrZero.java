package StamatovTeam.filmorate20.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DurationPositiveOrZeroValidator.class)
@Documented
public @interface DurationPositiveOrZero {
    String message() default "{Длительность фильма должна быть больше или равна 0 сек.}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

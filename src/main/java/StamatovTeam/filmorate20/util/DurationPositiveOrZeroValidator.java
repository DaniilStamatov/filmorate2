package StamatovTeam.filmorate20.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Duration;

public class DurationPositiveOrZeroValidator implements ConstraintValidator<DurationPositiveOrZero, Duration> {
    @Override
    public boolean isValid(Duration duration, ConstraintValidatorContext constraintValidatorContext) {
        return duration.toSeconds()>=0;
    }
}

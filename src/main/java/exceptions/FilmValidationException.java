package exceptions;

public class FilmValidationException extends RuntimeException{
    public FilmValidationException(final String message){
        super(message);
    }
}

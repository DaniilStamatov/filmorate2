package exceptions;

public class UserLoginValidationException extends RuntimeException{
    public UserLoginValidationException(String message){
        super(message);
    }
}

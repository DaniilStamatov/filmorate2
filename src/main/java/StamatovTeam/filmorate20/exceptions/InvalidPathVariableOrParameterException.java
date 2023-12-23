package StamatovTeam.filmorate20.exceptions;

public class InvalidPathVariableOrParameterException extends RuntimeException{
    private String param;
    public InvalidPathVariableOrParameterException(String param,String message) {
        super(message);
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}

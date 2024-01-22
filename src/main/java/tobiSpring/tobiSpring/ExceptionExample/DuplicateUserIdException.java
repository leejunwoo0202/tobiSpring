package tobiSpring.tobiSpring.ExceptionExample;

public class DuplicateUserIdException extends RuntimeException{

    public DuplicateUserIdException(Throwable cause)
    {
        super(cause);
    }
}

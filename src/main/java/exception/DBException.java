package exception;

public class DBException extends Throwable {
    public DBException(Throwable throwable) {
        super(throwable);
    }
}
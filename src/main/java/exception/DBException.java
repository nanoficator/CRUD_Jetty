package exception;

import java.io.IOException;

public class DBException extends IOException {
    public DBException(Throwable throwable) {
        super(throwable);
    }
}

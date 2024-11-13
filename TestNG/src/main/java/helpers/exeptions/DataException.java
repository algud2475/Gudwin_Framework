package helpers.exeptions;

import org.testng.Assert;

public class DataException extends RuntimeException {
    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        Assert.fail(message + ": " + cause);
    }
}

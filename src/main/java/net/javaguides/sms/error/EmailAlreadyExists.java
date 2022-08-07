package net.javaguides.sms.error;

public class EmailAlreadyExists extends Exception {
    public EmailAlreadyExists() {
        super();
    }

    public EmailAlreadyExists(String message) {
        super(message);
    }

    public EmailAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyExists(Throwable cause) {
        super(cause);
    }

    protected EmailAlreadyExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

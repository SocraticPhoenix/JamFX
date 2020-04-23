package io.github.socraticphoenix.jamfx;

public class JamLoadException extends RuntimeException {

    public JamLoadException() {
    }

    public JamLoadException(String message) {
        super(message);
    }

    public JamLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public JamLoadException(Throwable cause) {
        super(cause);
    }

    public JamLoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

package net.orpiske.hhp.plot.exceptions;

public class HdrException extends RuntimeException {
    public HdrException() {
    }

    public HdrException(String message, Object...args) {
        super(String.format(message, args));
    }

    public HdrException(String message, Throwable cause) {
        super(message, cause);
    }

    public HdrException(Throwable cause) {
        super(cause);
    }

    public HdrException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

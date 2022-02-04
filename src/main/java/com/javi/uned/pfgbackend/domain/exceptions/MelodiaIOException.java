package com.javi.uned.pfgbackend.domain.exceptions;

public class MelodiaIOException extends Exception {

    public MelodiaIOException() {
    }

    public MelodiaIOException(String message) {
        super(message);
    }

    public MelodiaIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public MelodiaIOException(Throwable cause) {
        super(cause);
    }
}

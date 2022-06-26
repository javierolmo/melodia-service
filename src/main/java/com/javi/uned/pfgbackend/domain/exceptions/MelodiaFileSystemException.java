package com.javi.uned.pfgbackend.domain.exceptions;

public class MelodiaFileSystemException extends Exception {

    public MelodiaFileSystemException() {
    }

    public MelodiaFileSystemException(String message) {
        super(message);
    }

    public MelodiaFileSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public MelodiaFileSystemException(Throwable cause) {
        super(cause);
    }
}

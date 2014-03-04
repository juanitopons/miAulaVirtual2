package com.juanitopons.miaulavirtual.model;

public class BadDataException extends Exception {
    public BadDataException() { super(); }
    public BadDataException(String message) { super(message); }
    public BadDataException(String message, Throwable cause) { super(message, cause); }
    public BadDataException(Throwable cause) { super(cause); }
}

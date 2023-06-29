package br.com.incode.base.common.exceptions;

public class InvalidTokenException extends RuntimeException {

	public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
    
}

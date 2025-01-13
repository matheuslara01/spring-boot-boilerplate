package br.com.incode.base.infrastructure.exceptions;

public class EntityDeleteException extends RuntimeException {

	public EntityDeleteException(String message) {
        super(message);
    }

    public EntityDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}

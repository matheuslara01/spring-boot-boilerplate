package br.com.incode.base.common.exceptions;

public class EntityPersistenceException extends RuntimeException {

	public EntityPersistenceException(String message) {
        super(message);
    }

    public EntityPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}

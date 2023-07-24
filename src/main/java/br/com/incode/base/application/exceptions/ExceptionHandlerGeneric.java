package br.com.incode.base.application.exceptions;

import javax.persistence.NoResultException;

import org.hibernate.id.IdentifierGenerationException;
import org.modelmapper.MappingException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.incode.base.application.dto.ResponseDTO;
import io.jsonwebtoken.MalformedJwtException;

@ControllerAdvice
public class ExceptionHandlerGeneric extends ResponseEntityExceptionHandler {

	@Autowired
	private Logger logger;

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(RuntimeException ex, WebRequest request) {
		if (ex.getMessage().contains("CPF")) {
			return new ResponseEntity<>(new ResponseDTO(false, "CPF já existe na base de dados!"), HttpStatus.BAD_REQUEST);
		} else if (ex.getMessage().contains("EMAIL")) {
			return new ResponseEntity<>(new ResponseDTO(false, "EMAIL já existe na base de dados!"),
					HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(new ResponseDTO(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	@ExceptionHandler(IdentifierGenerationException.class)
	public ResponseEntity<?> identifierGenerationException(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<>(
				new ResponseDTO(false, "Sequência de ID não configurado, contate o administrador do sistema: "
						+ ex.getMessage().split(":")[1].split(";")[0]),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<?> noResultExceptionException(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<>(
				new ResponseDTO(false, ex.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MappingException.class)
	public ResponseEntity<?> mappingException(RuntimeException ex, WebRequest request) {

		if (ex.getMessage().contains("DATE")) {
			return new ResponseEntity<>(
					new ResponseDTO(false, "Formato de data inválido. Formato aceito: yyyy-mm-dd"),
					HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(
					new ResponseDTO(false, ex.getMessage()),
					HttpStatus.BAD_REQUEST);
		}

	}

	@ExceptionHandler(EntityPersistenceException.class)
	protected ResponseEntity<?> handleEntityPersistenceException(EntityPersistenceException ex, WebRequest request) {
		return new ResponseEntity<>(new ResponseDTO(false, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(EntityDeleteException.class)
	protected ResponseEntity<?> handleEntityDeleteException(EntityDeleteException ex, WebRequest request) {
		return new ResponseEntity<>(new ResponseDTO(false, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<?> handleInvalidTokenException(InvalidTokenException ex, WebRequest request) {
		return new ResponseEntity<>(new ResponseDTO(false, ex.getMessage()), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(MalformedJwtException.class)
	public ResponseEntity<?> handleMalformedJwtException(MalformedJwtException ex) {
		return new ResponseEntity<>(new ResponseDTO(false, ex.getMessage()), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<?> badCredentialsException(BadCredentialsException ex, WebRequest request) {
		return new ResponseEntity<>(new ResponseDTO(false, "Login ou senha inválidos!"), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGenericException(Exception ex, WebRequest request) {
		logger.error(ex.getMessage(), ex.getCause());
		return new ResponseEntity<>(
				new ResponseDTO(false, "Erro interno no servidor. Contate o administrador do sistema."),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}
}

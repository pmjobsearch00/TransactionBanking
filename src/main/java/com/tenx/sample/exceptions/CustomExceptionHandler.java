package com.tenx.sample.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(ServiceException.class)
	public ResponseEntity handleServiceException(final ServiceException exception) {
		log.warn("Processing service exception: {}", exception.getMessage());

		return new ResponseEntity<>(exception.getLocalizedMessage(),
				HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity handleUserNotFoundException(final AccountNotFoundException exception) {
		log.warn("Processing account not found exception: {}", exception.getMessage());

		return new ResponseEntity<>(exception.getLocalizedMessage(),
				HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(Exception.class)
	public ResponseEntity handleAbstractException(final Exception exception) {
		log.warn("Processing abstract exception: {}", exception.getMessage());

		return new ResponseEntity<>(exception.getLocalizedMessage(),
				HttpStatus.BAD_REQUEST);
	}
}

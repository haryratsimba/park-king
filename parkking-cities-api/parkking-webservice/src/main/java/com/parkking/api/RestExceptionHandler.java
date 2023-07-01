package com.parkking.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.parkking.exception.ParkkingApiException;

import lombok.extern.slf4j.Slf4j;

/**
 * Centralise la gestion des exception et la reponse HTTP associee.
 * 
 * @author hary.ratsimba
 *
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { ParkkingApiException.class })
	public ResponseEntity<Object> handleTravelplugzAdminApiException(final ParkkingApiException ex,
			final WebRequest request) {

		ParkkingApiResponse<?> response = new ParkkingApiResponse<>();
		response.setError(ex.getMessage());

		return this.handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatusCode.valueOf(ex.getHttpCode()), request);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleGeneralException(final Exception ex, final WebRequest request) {
		log.error("General exception : {}", ex);

		ParkkingApiResponse<?> response = new ParkkingApiResponse<>();
		response.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

		return this.handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

}

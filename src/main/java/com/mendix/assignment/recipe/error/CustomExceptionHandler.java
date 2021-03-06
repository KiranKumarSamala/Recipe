/**
 * 
 */
package com.mendix.assignment.recipe.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author kiran
 *
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidRecipeExpcetion.class)
    public ResponseEntity<Object> customHandleNotFound(InvalidRecipeExpcetion ex, WebRequest request) {
		Map<String, Object> errorBody = new HashMap();
		errorBody.put("errors", ex.getErrorMessages());
		
		return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

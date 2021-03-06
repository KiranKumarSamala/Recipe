/**
 * 
 */
package com.mendix.assignment.recipe.error;

import java.util.List;

import lombok.Getter;

/**
 * @author kiran
 *
 */
public class InvalidRecipeExpcetion extends RuntimeException {

	@Getter
	private final List<String> errorMessages;

	public InvalidRecipeExpcetion(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	
}

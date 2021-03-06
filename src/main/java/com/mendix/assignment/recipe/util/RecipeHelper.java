/**
 * 
 */
package com.mendix.assignment.recipe.util;

import java.util.stream.Collectors;

import com.mendix.assignment.recipe.model.Recipeml;

/**
 * @author kiran
 *
 */
public class RecipeHelper {

	public static boolean doesRecipeContainsCategory(Recipeml recipeml, String category) {
		return isValidCategory(category) 
				&& recipeml.getRecipe().getHead().getCategories().getName().stream()
				.map(cat -> cat.trim().toLowerCase()).collect(Collectors.toList())
				.contains(category.trim().toLowerCase());
	}
	
	public static boolean isValidCategory(String category) {
		return category != null;
	}
	
}

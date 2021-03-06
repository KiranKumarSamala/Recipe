/**
 * 
 */
package com.mendix.assignment.recipe.util;

import java.util.ArrayList;
import java.util.List;

import com.mendix.assignment.recipe.error.InvalidRecipeExpcetion;
import com.mendix.assignment.recipe.model.Ingredients;
import com.mendix.assignment.recipe.model.Recipeml;

/**
 * @author kiran
 *
 */
public class ValidateRecipe {

	public static void validateNewRecipe(Recipeml recipe, List<Recipeml> existingRecipes) {

		List<String> errorMessages = new ArrayList<>();

		if (recipe == null || recipe.getRecipe() == null) {
			errorMessages.add("No Recipe Found");
			throw new InvalidRecipeExpcetion(errorMessages);
		}

		if (recipe.getRecipe().getHead() == null) {
			errorMessages.add("Recipe head with title / category is missing");
		} else {

			if (recipe.getRecipe().getHead().getTitle() == null) {
				errorMessages.add("Recipe title is missing");
			} else if (checkIfRecipeExists(recipe, existingRecipes)) {
				errorMessages.add("Recipe title already exists");
			}
			
			if(recipe.getRecipe().getHead().getCategories() == null) {
				errorMessages.add("Recipe category is missing");
			}
		}
		
		Ingredients ingredients =  recipe.getRecipe().getIngredients();
		if(ingredients == null || (ingredients.getIngredientGroupList() == null && ingredients.getIngredientList() == null) ) {
			errorMessages.add("Ingredients is missing");
		}
		
		if(recipe.getRecipe().getDirections() == null || recipe.getRecipe().getDirections().getStep() == null) {
			errorMessages.add("Steps for recipe is missing");
		}

		if (!errorMessages.isEmpty()) {
			throw new InvalidRecipeExpcetion(errorMessages);
		}
	}

	/**
	 * @param recipe
	 * @param existingRecipes
	 * @return
	 */
	private static boolean checkIfRecipeExists(Recipeml newRecipe, List<Recipeml> existingRecipes) {
		return existingRecipes.stream().map(recipe -> recipe.getRecipe().getHead().getTitle())
				.anyMatch(title -> title.equals(newRecipe.getRecipe().getHead().getTitle()));

	}
}

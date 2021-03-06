/**
 * 
 */
package com.mendix.assignment.recipe.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mendix.assignment.recipe.model.Category;
import com.mendix.assignment.recipe.model.Head;
import com.mendix.assignment.recipe.model.Recipe;
import com.mendix.assignment.recipe.model.Recipeml;

/**
 * @author kiran
 *
 */
class RecipeHelperTest {

	private static Recipeml recipeml;

	@BeforeAll
	private static void init() {
		recipeml = createRecipe(Arrays.asList("Chili", "Main dish"), "30 Minute Chili") ;
	}

	private static Recipeml createRecipe(List<String> categoryList, String title) {

		Category catagories = new Category();
		catagories.setName(categoryList);

		Head head = new Head();
		head.setCategories(catagories);
		head.setTitle(title);

		Recipeml recipeml = new Recipeml();
		Recipe recipe = new Recipe();
		recipeml.setRecipe(recipe);
		recipe.setHead(head);

		return recipeml;
	}
	
	@Test
	void shouldTrimSpaceForCategorySearch() {		
		assertTrue(RecipeHelper.doesRecipeContainsCategory(recipeml, " Chili "));
	}
	
	@Test
	void shouldIgnoreCaseForCategorySearch() {
		assertTrue(RecipeHelper.doesRecipeContainsCategory(recipeml, " chili "));

	}
	
	@Test
	void shouldReturnFalseIfCategoryIsNull() {
		assertFalse(RecipeHelper.doesRecipeContainsCategory(recipeml, null));

	}
	
	@Test
	void shouldReturnFalseIfCategoryDoesNotExists() {
		assertFalse(RecipeHelper.doesRecipeContainsCategory(recipeml, "sample"));

	}
}

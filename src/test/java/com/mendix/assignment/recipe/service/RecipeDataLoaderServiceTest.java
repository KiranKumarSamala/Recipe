/**
 * 
 */
package com.mendix.assignment.recipe.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mendix.assignment.recipe.model.Recipeml;

/**
 * @author kiran
 *
 */
class RecipeDataLoaderServiceTest {

	static List<Recipeml> recipeml;

	@BeforeAll
	static void loadData() throws IOException, JAXBException {
		RecipeDataLoaderService service = new RecipeDataLoaderService();
		recipeml = service.loadData();
	}

	@Test
	void shouldLoadRecipeData() {
		assertNotNull(recipeml);
	}

	@Test
	void shouldHave3Recipes() {
		assertEquals(3, recipeml.size());
	}

	@Test
	void shouldValidate30MinuteChili() {
		List<Recipeml> result = recipeml.stream()
				.filter(recipe -> recipe.getRecipe().getHead().getTitle().equals("30 Minute Chili"))
				.collect(Collectors.toList());
		
		assertNotNull(result);
		assertEquals(1, result.size());
		
		Recipeml chiliRecipe = result.get(0);
		
		assertNotNull(chiliRecipe.getRecipe());
		assertNotNull(chiliRecipe.getRecipe().getHead());
		assertNotNull(chiliRecipe.getRecipe().getHead().getCategories());
		assertNotNull(chiliRecipe.getRecipe().getHead().getCategories().getName());
		assertEquals(2, chiliRecipe.getRecipe().getHead().getCategories().getName().size());
		
		assertNotNull(chiliRecipe.getRecipe().getDirections());
		assertNotNull(chiliRecipe.getRecipe().getDirections().getStep());




	}

}

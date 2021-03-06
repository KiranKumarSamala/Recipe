/**
 * 
 */
package com.mendix.assignment.recipe.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mendix.assignment.recipe.model.Category;
import com.mendix.assignment.recipe.model.Head;
import com.mendix.assignment.recipe.model.Recipe;
import com.mendix.assignment.recipe.model.Recipeml;

/**
 * @author kiran
 *
 */
@ExtendWith(MockitoExtension.class)
class RecipeSearchServiceTest {

	@Mock
	private RecipeCacheService recipeCacheService;
	
	RecipeSearchService service;

	static List<Recipeml> recipemlSample;

	@BeforeAll
	private static void init() {
		recipemlSample = new ArrayList<>();

		recipemlSample.add(createRecipe(Arrays.asList("Chili", "Main dish"), "30 Minute Chili"));
		recipemlSample.add(createRecipe(Arrays.asList("Liquor", "Cakes"), "Amaretto Cake"));

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
	
	@BeforeEach
	private void initService() {
		service = new RecipeSearchService(recipeCacheService);
		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipemlSample);

	}

	@Test
	void shouldBeAbleToSearchForTitle() {
		
		List<Recipeml> searchResult = service.searchInRecipe("30 Minute Chili");
		assertEquals(1, searchResult.size());
		assertEquals("30 Minute Chili", searchResult.get(0).getRecipe().getHead().getTitle());

	}
	
	@Test
	void shouldBeAbleToSearchForCategoryInChiliRecipe() {

		List<Recipeml> searchResult = service.searchInRecipe("Chili");
		List<String> categoryList = searchResult.get(0).getRecipe().getHead().getCategories().getName();
		
		assertEquals(1, searchResult.size());
		assertEquals(2, categoryList.size());
		assertEquals("Chili", categoryList.get(0));
		assertEquals("Main dish", categoryList.get(1));
	}
	
	@Test
	void shouldIgnoreCaseForTitleSearch() {
		
		List<Recipeml> searchResult = service.searchInRecipe("30 minute chili");
		assertEquals(1, searchResult.size());
		assertEquals("30 Minute Chili", searchResult.get(0).getRecipe().getHead().getTitle());

	}
	
	@Test
	void shouldTrimSpaceForTitleSearch() {
		
		List<Recipeml> searchResult = service.searchInRecipe(" Amaretto Cake  ");
		assertEquals(1, searchResult.size());
		assertEquals("Amaretto Cake", searchResult.get(0).getRecipe().getHead().getTitle());

	}

	
	@Test
	void shouldReturnEmptyListIfSearchNotFound() {

		List<Recipeml> searchResult = service.searchInRecipe("bread");	
		assertEquals(0, searchResult.size());
	}
}

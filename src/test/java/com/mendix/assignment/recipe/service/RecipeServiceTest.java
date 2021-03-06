/**
 * 
 */
package com.mendix.assignment.recipe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mendix.assignment.recipe.error.InvalidRecipeExpcetion;
import com.mendix.assignment.recipe.model.Category;
import com.mendix.assignment.recipe.model.Directions;
import com.mendix.assignment.recipe.model.Head;
import com.mendix.assignment.recipe.model.Ingredients;
import com.mendix.assignment.recipe.model.Recipe;
import com.mendix.assignment.recipe.model.Recipeml;

/**
 * @author kiran
 *
 */
@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

	@Mock
	private RecipeCacheService recipeCacheService;
	
	RecipeService recipeService;
	
	static List<Recipeml> recipemlSample;

	@BeforeAll
	private static void init() {
		recipemlSample = new ArrayList<>();
		recipemlSample.addAll(createRecipes());
	}
	
	static List<Recipeml> createRecipes() {
		
		List<Recipeml> recipeList = new ArrayList<>();
		
		recipeList.add(createRecipe(Arrays.asList("Microwave", "Vegetables"), "Another Zucchini Dish"));
		recipeList.add(createRecipe(Arrays.asList("Microwave", "Vegetables"), "Another Potato Dish"));
		recipeList.add(createRecipe(Arrays.asList("Liquor", "Cakes"), "Amaretto Cake"));
		
		return recipeList;
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
		
		Ingredients ingredients = new Ingredients();
		ingredients.setIngredientGroupList(Collections.emptyList());
		recipe.setIngredients(ingredients);
		
		Directions directions = new Directions();
		directions.setStep("Demo steps");
		recipe.setDirections(directions);

		return recipeml;
	}
	
	@BeforeEach
	private void initService() {
		recipeService = new RecipeService(recipeCacheService);
	}

	@Test
	void shouldReturnRecipes() {
		List<Recipeml> recipleml = Arrays.asList( new Recipeml(), new Recipeml());

		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipleml);
		assertEquals(2, recipeService.getRecipes().size());
	}
	
	@Test
	void shouldReturnEmptyRecipes() {
		List<Recipeml> recipleml = Arrays.asList();

		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipleml);
		assertEquals(0, recipeService.getRecipes().size());
	}
	
	@Test
	void shouldFindRecipesByCategory() {

		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipemlSample);
		List<Recipeml> findByCategoryResult = recipeService.findByCategory("Vegetables");
		assertEquals(2, findByCategoryResult.size());
		assertEquals("Another Zucchini Dish", findByCategoryResult.get(0).getRecipe().getHead().getTitle());
		assertEquals("Another Potato Dish", findByCategoryResult.get(1).getRecipe().getHead().getTitle());

	}
	
	@Test
	void shouldReturnEmptyRecipesIfCategoryNotFOund() {
		List<Recipeml> recipleml = new ArrayList<>();

		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipleml);
		assertEquals(0, recipeService.findByCategory("Vegetables").size());
	}
	
	@Test
	void shouldGetAllCategories() {
		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipemlSample);
		Set<String> categoriesResult = recipeService.getCategories();
		
		assertEquals(4, categoriesResult.size());
		
		assertTrue(categoriesResult.contains("Microwave"));
		assertTrue(categoriesResult.contains("Vegetables"));
		assertTrue(categoriesResult.contains("Liquor"));
		assertTrue(categoriesResult.contains("Cakes"));

	}
	
	@Test
	void shouldSaveNewRecipe() {
 
		List<Recipeml> recipeList = new ArrayList<>();
		recipeList.addAll(createRecipes());
		Recipeml recipeml = createRecipe(Arrays.asList("Vegetables"), "Sandwich");

		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipeList);
		assertNotNull(recipeService.saveRecipe(recipeml));
	}
	
	@Test
	void shouldThrowExceptionWhenRecipeIsNull() {
 
		List<Recipeml> recipeList = new ArrayList<>();
		recipeList.addAll(createRecipes());
		
		Recipeml recipeml = createRecipe(Arrays.asList("Vegetables"), "Sandwich");
		recipeml.setRecipe(null);

		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipeList);
		List<String> errorMsg = assertThrows(InvalidRecipeExpcetion.class, () -> {
			recipeService.saveRecipe(recipeml);
		}).getErrorMessages();
		
		assertTrue(errorMsg.contains("No Recipe Found"));
	}
	
	@Test
	void shouldThrowExceptionWhenHeadIsNull() {
 
		List<Recipeml> recipeList = new ArrayList<>();
		recipeList.addAll(createRecipes());
		
		Recipeml recipeml = createRecipe(Arrays.asList("Vegetables"), "Sandwich");
		recipeml.getRecipe().setHead(null);

		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipeList);
		List<String> errorMsg = assertThrows(InvalidRecipeExpcetion.class, () -> {
			recipeService.saveRecipe(recipeml);
		}).getErrorMessages();
		
		assertTrue(errorMsg.contains("Recipe head with title / category is missing"));
	}
	
	@Test
	void shouldThrowExceptionWhenTitleIsNull() {
 
		List<Recipeml> recipeList = new ArrayList<>();
		recipeList.addAll(createRecipes());
		
		Recipeml recipeml = createRecipe(Arrays.asList("Vegetables"), "Sandwich");
		recipeml.getRecipe().getHead().setTitle(null);

		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipeList);
		List<String> errorMsg = assertThrows(InvalidRecipeExpcetion.class, () -> {
			recipeService.saveRecipe(recipeml);
		}).getErrorMessages();
		
		assertTrue(errorMsg.contains("Recipe title is missing"));
	}
	
	@Test
	void shouldThrowExceptionWhenTitleAlreadyExists() {
 
		List<Recipeml> recipeList = new ArrayList<>();
		recipeList.addAll(createRecipes());
		
		Recipeml recipeml = createRecipe(Arrays.asList("Vegetables"), "Sandwich");
		recipeml.getRecipe().getHead().setTitle("Amaretto Cake");

		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipeList);
		List<String> errorMsg = assertThrows(InvalidRecipeExpcetion.class, () -> {
			recipeService.saveRecipe(recipeml);
		}).getErrorMessages();
		
		assertTrue(errorMsg.contains("Recipe title already exists"));
	}


	@Test
	void shouldThrowExceptionWhenCategoryIsNull() {
	
		List<Recipeml> recipeList = new ArrayList<>();
		recipeList.addAll(createRecipes());
		
		Recipeml recipeml = createRecipe(Arrays.asList("Vegetables"), "Sandwich");
		recipeml.getRecipe().getHead().setCategories(null);
	
		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipeList);
		List<String> errorMsg = assertThrows(InvalidRecipeExpcetion.class, () -> {
			recipeService.saveRecipe(recipeml);
		}).getErrorMessages();
		
		assertTrue(errorMsg.contains("Recipe category is missing"));
	}
	
	@Test
	void shouldThrowExceptionWhenIngredientIsNull() {
	
		List<Recipeml> recipeList = new ArrayList<>();
		recipeList.addAll(createRecipes());
		
		Recipeml recipeml = createRecipe(Arrays.asList("Vegetables"), "Sandwich");
		recipeml.getRecipe().setIngredients(null);
	
		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipeList);
		List<String> errorMsg = assertThrows(InvalidRecipeExpcetion.class, () -> {
			recipeService.saveRecipe(recipeml);
		}).getErrorMessages();
		
		assertTrue(errorMsg.contains("Ingredients is missing"));
		
		
		recipeml.getRecipe().setIngredients(new Ingredients());
		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipeList);
		errorMsg = assertThrows(InvalidRecipeExpcetion.class, () -> {
			recipeService.saveRecipe(recipeml);
		}).getErrorMessages();
		
		assertTrue(errorMsg.contains("Ingredients is missing"));
		
	}
	
	
	@Test
	void shouldThrowExceptionWhenStepsIsNull() {
	
		List<Recipeml> recipeList = new ArrayList<>();
		recipeList.addAll(createRecipes());
		
		Recipeml recipeml = createRecipe(Arrays.asList("Vegetables"), "Sandwich");
		recipeml.getRecipe().setDirections(null);
	
		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipeList);
		List<String> errorMsg = assertThrows(InvalidRecipeExpcetion.class, () -> {
			recipeService.saveRecipe(recipeml);
		}).getErrorMessages();
		
		assertTrue(errorMsg.contains("Steps for recipe is missing"));
		
		
		recipeml.getRecipe().setDirections(new Directions());
		Mockito.when(recipeCacheService.getRecipesCache()).thenReturn(recipeList);
		errorMsg = assertThrows(InvalidRecipeExpcetion.class, () -> {
			recipeService.saveRecipe(recipeml);
		}).getErrorMessages();
		
		assertTrue(errorMsg.contains("Steps for recipe is missing"));
		
	}
}

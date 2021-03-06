package com.mendix.assignment.recipe;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.mendix.assignment.recipe.controller.RecipeController;
import com.mendix.assignment.recipe.model.Category;
import com.mendix.assignment.recipe.model.Head;
import com.mendix.assignment.recipe.model.Recipe;
import com.mendix.assignment.recipe.model.Recipeml;
import com.mendix.assignment.recipe.service.RecipeSearchService;
import com.mendix.assignment.recipe.service.RecipeService;

@WebMvcTest(controllers = RecipeController.class)
class RecipeApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private RecipeService recipeService;
	
	@MockBean
	private RecipeSearchService recipeSearchService;

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
	void shouldReadRecipes() throws Exception {

		List<Recipeml> recipemlSample = new ArrayList<>();

		recipemlSample.add(createRecipe(Arrays.asList("Chili", "Main dish"), "30 Minute Chili"));
		recipemlSample.add(createRecipe(Arrays.asList("Liquor", "Cakes"), "Amaretto Cake"));

		Mockito.when(recipeService.getRecipes()).thenReturn(recipemlSample);
		mvc.perform(get("/recipe").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()").value(2))
				.andExpect(jsonPath("$[0].recipe.head.title").value("30 Minute Chili"))
				.andExpect(jsonPath("$[1].recipe.head.title").value("Amaretto Cake"))
				.andExpect(jsonPath("$[1].recipe.head.categories.name.size()").value(2));

	}

	@Test
	void shouldFilterRecipeByCategory() throws Exception {

		List<Recipeml> recipemlSample = new ArrayList<>();

		recipemlSample.add(createRecipe(Arrays.asList("Chili", "Main dish"), "30 Minute Chili"));

		Mockito.when(recipeService.findByCategory(Mockito.anyString())).thenReturn(recipemlSample);
		mvc.perform(get("/recipe?category=30 Minute Chili").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()").value(1))
				.andExpect(jsonPath("$[0].recipe.head.title").value("30 Minute Chili"));
	}
	
	@Test
	void shouldReturnEmptyListFilterRecipeByCategory() throws Exception {

		List<Recipeml> recipemlSample = new ArrayList<>();

		Mockito.when(recipeService.findByCategory(Mockito.anyString())).thenReturn(recipemlSample);
		mvc.perform(get("/recipe?category=30 Minute Chili").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()").value(0));
	}

	@Test
	void shouldSearchRecipes() throws Exception {

		List<Recipeml> recipemlSample = new ArrayList<>();

		recipemlSample.add(createRecipe(Arrays.asList("Chili", "Main dish"), "30 Minute Chili"));

		Mockito.when(recipeSearchService.searchInRecipe(Mockito.anyString())).thenReturn(recipemlSample);
		mvc.perform(get("/recipe?search=Chili").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()").value(1))
				.andExpect(jsonPath("$[0].recipe.head.title").value("30 Minute Chili"));
	}

	@Test
	void shouldReturnEmptyListWhenSearchNotFound() throws Exception {

		List<Recipeml> recipemlSample = new ArrayList<>();

		Mockito.when(recipeSearchService.searchInRecipe(Mockito.anyString())).thenReturn(recipemlSample);
		mvc.perform(get("/recipe?search=Chili").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()").value(0));
	}
	
	@Test
	void shouldReadCategories() throws Exception {

		Set<String> categoriesList = new HashSet<>(Arrays.asList("Chili", "Main dish","Liquor", "Cakes"));

		Mockito.when(recipeService.getCategories()).thenReturn(categoriesList);
		mvc.perform(get("/recipe/categories").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()").value(4));

	}
}

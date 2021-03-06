/**
 * 
 */
package com.mendix.assignment.recipe.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mendix.assignment.recipe.model.Recipeml;
import com.mendix.assignment.recipe.service.RecipeSearchService;
import com.mendix.assignment.recipe.service.RecipeService;

/**
 * 
 * RecipeController handles all the end points for recipe 
 * 
 * @author kiran
 *
 */
@RestController
@RequestMapping("/recipe")
public class RecipeController {
	
	
	private RecipeService recipeService;
	
	private RecipeSearchService recipeSearchService;
	
	/**
	 * 
	 */
	public RecipeController(RecipeService recipeService,  RecipeSearchService recipeSearchService) {
		this.recipeService = recipeService;
		this.recipeSearchService = recipeSearchService;

	}
	
	/**
	 * 
	 * getRecipes() method will fetch all the recipes from Recipe Cache. Category and Search is added as optinal parameters
	 * When Category is passed it will search categories in all recipes
	 * When search is passed it will search for title and categories in all recipes
	 * 
	 * @param category
	 * @param search
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Recipeml>> getRecipes(@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "search", required = false) String search) {
	
		if(category != null) {
			return new ResponseEntity<>(recipeService.findByCategory(category), HttpStatus.OK);
		}

		if(search != null) {
			return new ResponseEntity<>(recipeSearchService.searchInRecipe(search), HttpStatus.OK);
		}
		return new ResponseEntity<>(recipeService.getRecipes(), HttpStatus.OK);
		
	}
	
	/**
	 * getCategories will return list of unique categories across all the recipes
	 * 
	 * @return
	 */
	@GetMapping("/categories")
	public ResponseEntity<Set<String>> getCategories() {
		return new ResponseEntity<>(recipeService.getCategories(), HttpStatus.OK);
	}

}

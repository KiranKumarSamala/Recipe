/**
 * 
 */
package com.mendix.assignment.recipe.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mendix.assignment.recipe.model.Recipeml;
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
	
	
	/**
	 * 
	 */
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	/**
	 * 
	 * getRecipes() method will fetch all the recipes from Recipe Cache
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Recipeml>> getRecipes() {
	
		return new ResponseEntity<>(recipeService.getRecipes(), HttpStatus.OK);
		
	}
	

}

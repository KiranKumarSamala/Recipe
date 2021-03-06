/**
 * 
 */
package com.mendix.assignment.recipe.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendix.assignment.recipe.model.Recipeml;
import com.mendix.assignment.recipe.util.RecipeHelper;


/**
 * RecipeSearchService provides generic search in recipe.
 * @author kiran
 *
 */
@Service
public class RecipeSearchService {


	private RecipeCacheService recipeCacheService;
	
	/**
	 * 
	 * @param recipeCacheService
	 */
	@Autowired
	public RecipeSearchService(RecipeCacheService recipeCacheService) {
		this.recipeCacheService = recipeCacheService;
	}
	
	
	/**
	 * searchInRecipe provides generic search in recipe. It will search in category and title
	 * 
	 * @param search
	 * @return
	 */
	public List<Recipeml> searchInRecipe(String searchString) {
		
		String search = searchString.trim().toLowerCase();
		
		return recipeCacheService.getRecipesCache().stream().filter(recipeml -> 			
			recipeml.getRecipe().getHead().getTitle().equalsIgnoreCase(search) ||
			RecipeHelper.doesRecipeContainsCategory(recipeml, search)
		).collect(Collectors.toList());
	}
	

}


/**
 * 
 */
package com.mendix.assignment.recipe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendix.assignment.recipe.model.Recipeml;

/**
 * RecipeService provides services required for controller to read recipes from cache
 * 
 * @author kiran
 *
 */
@Service
public class RecipeService {

	private RecipeCacheService recipeCacheService;

	/**
	 * 
	 */
	@Autowired
	public RecipeService(RecipeCacheService recipeCacheService) {
		this.recipeCacheService = recipeCacheService;
	}

	public List<Recipeml> getRecipes() {
		return recipeCacheService.getRecipesCache();
	}

}

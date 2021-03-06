/**
 * 
 */
package com.mendix.assignment.recipe.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendix.assignment.recipe.model.Recipeml;
import com.mendix.assignment.recipe.util.RecipeHelper;

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

	/**
	 * getRecipes() fetches all the recipes
	 * 
	 * @return
	 */
	public List<Recipeml> getRecipes() {
		return recipeCacheService.getRecipesCache();
	}

	/**
	 * findByCategory will filter recipes based on the category
	 * 
	 * @param category
	 * @return
	 */
	public List<Recipeml> findByCategory(String category) {
		return recipeCacheService.getRecipesCache().stream()
				.filter(recipeml -> RecipeHelper.doesRecipeContainsCategory(recipeml, category))
				.collect(Collectors.toList());
	}
	
	/**
	 * getCategories will return list of unique categories across all the recipes
	 * 
	 * @return
	 */
	public Set<String> getCategories() {
		return recipeCacheService.getRecipesCache().stream()
				.map(recipe -> recipe.getRecipe().getHead().getCategories().getName()).flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}

}

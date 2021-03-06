/**
 * 
 */
package com.mendix.assignment.recipe.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendix.assignment.recipe.model.Recipeml;

import lombok.Getter;

/**
 * RecipeCacheService holds all the recipe as acts as cache for Recipe Service
 * 
 * @author kiran
 *
 */
@Service
public class RecipeCacheService {

	@Getter
	private List<Recipeml> recipesCache = new ArrayList<>();
	
	private RecipeDataLoaderService recipeDataLoaderService;
	
	/**
	 * 
	 * @param recipeDataLoaderService
	 */
	@Autowired
	public RecipeCacheService(RecipeDataLoaderService recipeDataLoaderService) {
		this.recipeDataLoaderService = recipeDataLoaderService;
	}
	
	/**
	 * 
	 * initCache loads the cache with default data
	 * 
	 * @throws IOException
	 * @throws JAXBException
	 */
	@PostConstruct
	public void initCache() throws IOException, JAXBException {
		recipesCache = recipeDataLoaderService.loadData();
	}
}

/**
 * 
 */
package com.mendix.assignment.recipe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mendix.assignment.recipe.model.Recipeml;

/**
 * @author kiran
 *
 */
@ExtendWith(MockitoExtension.class)
class RecipeCacheServiceTest {

	@Mock
	private RecipeDataLoaderService recipeDataLoaderService;

	private RecipeCacheService recipeCacheService;
	
	static List<Recipeml> recipemlSample;
	
	@BeforeAll
	static void prepareSampleData() {
		recipemlSample = Arrays.asList( new Recipeml(), new Recipeml(), new Recipeml());
	}
	
	
	@Test
	void shouldLoadCacheOnInit() throws IOException, JAXBException {
		recipeCacheService = new RecipeCacheService(recipeDataLoaderService);
		
	
		Mockito.when(recipeDataLoaderService.loadData()).thenReturn(recipemlSample);
		recipeCacheService.initCache();
	
		assertNotNull(recipeCacheService.getRecipesCache());
		assertEquals(3, recipeCacheService.getRecipesCache().size());
	}

}

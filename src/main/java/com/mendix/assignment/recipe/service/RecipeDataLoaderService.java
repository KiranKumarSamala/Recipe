package com.mendix.assignment.recipe.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;

import com.mendix.assignment.recipe.model.Directions;
import com.mendix.assignment.recipe.model.Head;
import com.mendix.assignment.recipe.model.Ingredients;
import com.mendix.assignment.recipe.model.ObjectFactory;
import com.mendix.assignment.recipe.model.Recipeml;

/**
 * 
 */

/**
 * 
 * RecipeDataLoaderService reads the sample data from xml file stored in resources folder.
 * @author kiran
 *
 */
@Service
public class RecipeDataLoaderService {

	/**
	 * 
	 * loadData read the sample data from xml file stored in resources and converts XML data to java models.
	 * It loads only valid data. Recipe with head, categories list and title is consider valid
	 * 
	 * 
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	public List<Recipeml> loadData() throws IOException, JAXBException {

		String recipeFilesPath = getClass().getClassLoader().getResource("recipes").getPath();
		List<Recipeml> recipeList = new ArrayList<>();

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(recipeFilesPath))) {

			for (Path path : stream) {
				if (!Files.isDirectory(path) && path.toString().endsWith(".xml")) {
					recipeList.add(parseXmlFile(path.toString()));
				}
			}
		}
		
		
		return recipeList.stream().filter(this::validRecipe).collect(Collectors.toList());
	}

	/**
	 * @param recipeml
	 * @return
	 */
	private boolean validRecipe(Recipeml recipeml) {
		return recipeml != null
				&& recipeml.getRecipe() != null
				&& containsValidHead(recipeml.getRecipe().getHead());
				
	}
	
	private boolean containsValidHead(Head recipeHead) {
		return recipeHead != null
				&& recipeHead.getTitle() != null
				&& recipeHead.getCategories() != null
				&& recipeHead.getCategories().getName() != null;
	}
	

	private Recipeml parseXmlFile(String filePath) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);

		File file = new File(filePath);

		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		return ((JAXBElement<Recipeml>) unmarshaller.unmarshal(file)).getValue();
	}
	
	

}

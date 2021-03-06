/**
 * 
 */
package com.mendix.assignment.recipe.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author kiran
 *
 */
@Setter
@Getter
@XmlAccessorType(XmlAccessType.FIELD)
@ToString

public class Ingredients {

    @XmlElement(name = "ing-div")
	private List<IngredientGroup> ingredientGroupList;
    
    @XmlElement(name = "ing")
	private List<Ingredient> ingredientList;
}

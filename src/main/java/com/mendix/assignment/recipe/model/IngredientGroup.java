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
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@ToString

public class IngredientGroup {

    @XmlElement
	private String title;
    
    @XmlElement(name = "ing")
	private List<Ingredient> ingredientList;
}

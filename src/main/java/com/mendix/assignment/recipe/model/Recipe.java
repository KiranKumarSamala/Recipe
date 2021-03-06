/**
 * 
 */
package com.mendix.assignment.recipe.model;

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
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class Recipe {

    @XmlElement
	private Head head;
    
    @XmlElement
	private Ingredients ingredients;
    
    @XmlElement
	private Directions directions;
}

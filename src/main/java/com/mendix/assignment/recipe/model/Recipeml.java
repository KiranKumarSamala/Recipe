/**
 * 
 */
package com.mendix.assignment.recipe.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author kiran
 *
 */
@XmlRootElement( name = "recipeml" )
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder  = { "recipe" })
@Getter
@Setter
@ToString
public class Recipeml {

    @XmlElement
	private Recipe recipe;
}

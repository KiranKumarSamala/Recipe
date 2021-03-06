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
@XmlAccessorType(XmlAccessType.FIELD)
@ToString

public class Amount {

	@XmlElement( name = "qty")
	private int quantity;
	
	@XmlElement
	private String unit;
}

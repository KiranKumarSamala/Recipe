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
@ToString
@XmlAccessorType(XmlAccessType.FIELD)

public class Head {

    @XmlElement
	private String title;
    
    @XmlElement
	private int yeild;
   
    @XmlElement
	private Category categories;
}

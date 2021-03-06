/**
 * 
 */
package com.mendix.assignment.recipe.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * @author kiran
 *
 */
@XmlRegistry
public class ObjectFactory {
	
   private final static QName Recipeml_QNAME = new QName("", "foo");

    
   public Recipeml createRecipeml() {
        return new Recipeml();
    }
   
   @XmlElementDecl(namespace = "", name = "recipeml")
   public JAXBElement<Recipeml> createFoo(Recipeml value) {
       return new JAXBElement<>(Recipeml_QNAME, Recipeml.class, null, value);
   }
}
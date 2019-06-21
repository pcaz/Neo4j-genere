package com.neo4j.genere.dom;


import java.io.File;
import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;


public class XMLValidator {

	 
    /**
     * Cette fonction permet de savoir si un document XML est valide ou non.
     * @param fichierXML On recoit le nom du fichier XML à valider en paramètre.
     * @throws SAXException On jette les erreurs liées au parseur SAX.
     * @throws IOException On jette les erreurs liées au IO.
     * @throws org.xml.sax.SAXException 
     */
    public static Boolean validate(String XSDFile, String XMLFile)  {
 
        // 1. Récupère une "factory" pour le XML Schema du W3C
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
         
        // 2. Compile le schéma
        try {
        	File schemaLocation = new File(XSDFile);
        Schema schema = factory.newSchema(schemaLocation);
     
        // 3. Récupère un validateur depuis le schéma
        Validator validator = schema.newValidator();
         
        // 4. Parse le document que l'on veut vérifier
        Source source = new StreamSource(XMLFile);
       ;
         
        // 5. Vérifie le document
       
            validator.validate(source);
            return true;
        }
        catch (Exception ex) {
        	return false;
        }  
    }
}


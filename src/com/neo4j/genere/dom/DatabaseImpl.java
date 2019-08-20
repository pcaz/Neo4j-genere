package com.neo4j.genere.dom;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.neo4j.genere.DatabaseInterface;
import com.neo4j.genere.dom.IntNode;
import com.neo4j.genere.dom.StringNode;

import com.neo4j.genere.dom.Util;
import com.neo4j.genere.dom.Semantic;

public class DatabaseImpl implements DatabaseInterface {

	Element racine=null;
	NodeList noeuds=null;
	NodeList fields=null;
	NodeList fieldList = null;
	int numberFields=0;
	int noField=0;

	IntNode GetInt;
	StringNode GetString;
	DateNode GetDate;
	
	Util util;
	
	String schemaName;
	
	@Override
	public void dataBaseInitialyse(String file) {
		schemaName = System.getProperty("user.dir")+System.getProperty("file.separator")+file+".xsd";
	}
	@Override
	public void dataSourceInitialyse(String file) {
		
		Document document = null;
		
		DocumentBuilderFactory factory = null;
		
		String fileName= System.getProperty("user.dir")+System.getProperty("file.separator")+file+".xml";
		
		
		// validate the xml file against the schema
		
		if(!XMLValidator.validate(schemaName,fileName)) {
			System.out.println("Problem: "+fileName+" is not compliant with the schema : "+ schemaName);
			System.exit(0);
		}
		
		
		// ok, try to manage the xml file
		
	    try{
		    	factory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder builder = factory.newDocumentBuilder();
		    	document = builder.parse(fileName);		
		    	
		    }catch(Exception e){
		    	System.out.println("File :"+fileName+" not found");
		    	System.exit(0);
		    }
		
	    racine = document.getDocumentElement();
		 noeuds = racine.getChildNodes();
		 
		 util = new Util();
		 util.removeText(noeuds);
		
		//Semantic is a Singleton	 
		Semantic.getInstance().setDatabaseName(getDatabaseName());
		
		// initialisation des interpréteurs
		
		GetInt = new IntNode();
		GetString = new StringNode();
		GetDate = new DateNode();
	}
		
		
		
		
	
		
	
	
	@Override
	public String getDatabaseName() {
	
		NodeList databaseName = racine.getElementsByTagName("name");
		return databaseName.item(0).getTextContent().trim();
	}
	@Override
	public Integer getDatabaseNumber() {
	
		NodeList databaseNumber = racine.getElementsByTagName("number");
		return Integer.parseInt(databaseNumber.item(0).getTextContent().trim());
	}

	@Override
	public Node getNextField() {
	
	if(fields == null) {
		// first, go to fields array
		
	    Node fl=null;
	    
	    for(int i=0; i < noeuds.getLength();i++) {
	    	if(noeuds.item(i).getNodeName().equals("fields")) {
	    		fl = noeuds.item(i);
	    		break;
	    	}
	    }
	    
	   fields=fl.getChildNodes();
 
	   util.removeText(fields);

		 
		numberFields = fields.getLength();
		noField=0;
		
	
		

	}		

	   Node value=null;
		if(noField < numberFields) {
			value= fields.item(noField);
			noField++;
			
		}
		if(value!=null) {
			return value;
		}
		return null;
		
	}
	@Override
    public void	resetLine() {
		Semantic.getInstance().reset();
		fields = null;
	}
	public void resetDataSource() {
		GetInt.resetDataSource();
		GetDate.resetDataSource();
		GetString.resetDataSource();
	}
	@Override
	public String getInt(Node field) {
		return GetInt.getNodeGenere(field);
	}
	
	@Override
	public String getString(Node field) {
		return GetString.getNodeGenere(field);
	}
	
	@Override
	public String getDate(Node field) {
		return GetDate.getNodeGenere(field);
	}
	
	@Override
	
	public String getHeader() {
		return Semantic.getInstance().getHeader(noeuds);
		
	}
}

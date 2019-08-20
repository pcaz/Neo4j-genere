package com.neo4j.genere;

import org.w3c.dom.Node;

public interface DatabaseInterface {
	
	//initialize
	public void dataBaseInitialyse(String fileName);
	
	public void dataSourceInitialyse(String fileName);
	
	// get Database name
	public String getDatabaseName();
	// get number of items
	public Integer getDatabaseNumber();
	// next field and initialze parser
	public Node getNextField();
	// reset parser
	public void resetLine();
	// reset parser
	public void resetDataSource();
	// get header of file
	public String getHeader();
	// get int
	public String getInt(Node field);
	// get string
	public String getString(Node field);
	// get date
	public String getDate(Node field);

	
	

}

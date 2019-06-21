package com.neo4j.genere;

import org.w3c.dom.Node;

public interface DatabaseInterface {
	
	public void initialize(String file);
	public String getDatabaseName();
	public Integer getDatabaseNumber();
	public Node getNextField();
	public void reset();
	public String getInt(Node field);
	public String getString(Node field);
	public String getDate(Node field);
	public String getEntete();

}

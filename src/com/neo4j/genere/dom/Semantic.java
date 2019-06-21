package com.neo4j.genere.dom;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.neo4j.genere.dom.DatabaseImpl;

public class Semantic {
	
	private static Semantic instance;
	private Map<String,String> semantic;
	private List<String> entete;
	private String DatabaseName;
	
	private Semantic() {
		semantic = new TreeMap<String,String>();
		entete= new LinkedList<String>();
	}
	
	public static Semantic getInstance(){
        if(instance == null){
            instance = new Semantic();
        }     
        
        
        
        return instance;
    }

	public void reset() {
		semantic.clear();
	}
	public void setEntry(String name, String value) {
		
		//To do "what if the entrys already exist?
		semantic.put(name, value);
	}
	
	public void setHeader(String name, String type) {
		
		if(!type.contentEquals("id")) {
			entete.add(name+type);
		}
		else
		{
			entete.add(DatabaseName+"ID:ID("+DatabaseName+")");
		
		}
		
	}
	
	public void setDatabaseName(String DatabaseName) {
		this.DatabaseName = DatabaseName;
	}
	public String getEntry(String name) {
		return semantic.get(name);
	}
	
	public String getEntete() {
		
	StringBuffer line = new StringBuffer();
		
	
	Iterator<String> it = entete.iterator();
	while (it.hasNext()) {
		line.append(it.next()+",");
	}
	return (String) (line.substring(0, line.length() - 1));
	}
}

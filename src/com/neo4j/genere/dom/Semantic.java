package com.neo4j.genere.dom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.neo4j.genere.dom.DatabaseImpl;

public class Semantic { 
// Singleton
	
	private static Semantic instance;
	private Map<String,String> semantic;
	private ArrayList<String> entete;
	private String DatabaseName;
	
	private Semantic() {
		semantic = new TreeMap<String,String>();
		entete= new ArrayList<String>();
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
	
	
	public void setDatabaseName(String DatabaseName) {
		this.DatabaseName = DatabaseName;
	}
	public String getEntry(String name) {
		return semantic.get(name);
	}
	
	public boolean isEntry(String key) {
		return semantic.containsKey(key);
	}
	
	

	public String getHeader(NodeList noeuds) {
		
		StringBuffer line = new StringBuffer();	

	NodeList fields=null;
	NodeList field=null;
	entete.clear();
	for(int i=0;i < noeuds.getLength(); i++) {
		if(noeuds.item(i).getNodeName().equals("fields")) {
			fields = noeuds.item(i).getChildNodes();
			break;
		}
	}
	;

String val;
String name;
String type;

	for(int i=0;i < fields.getLength(); i++) {
		val=fields.item(i).getNodeName();
		field = fields.item(i).getChildNodes();
		for(int j=0;j<field.getLength();j++) {
		if(field.item(j).getNodeName().equals("name")) {
			Node node = field.item(j);
			val=field.item(j).getNodeName();
			name = node.getTextContent();
			type = node.getParentNode().getNodeName();
			
			if(name.equals("id")) {
				entete.add(DatabaseName+"ID:ID("+DatabaseName+")");
				
			}
			else
			{
				entete.add(name+":"+type.toUpperCase());
			
			}
		}
		}
	}

		
	
	Iterator<String> it = entete.iterator();
	while (it.hasNext()) {
		line.append(it.next()+",");
	}
	return (String) (line.substring(0, line.length() - 1));
	}
	
	
}

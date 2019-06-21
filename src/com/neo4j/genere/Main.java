package com.neo4j.genere;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.neo4j.genere.dom.IntNode;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Iterator;

public class Main {

	static int no=1;
	static String method="dom";
	static StringBuffer line;
	static String entete;
	static boolean isEntete = false;
	static PrintWriter writer;
	
	
	public static void main(String[] args) {
		
		
		
 		DatabaseFactory databaseFactory = new DatabaseFactory();
		DatabaseInterface impl=databaseFactory.getDatabase(method);
		impl.initialize("Neo4j-genere");
		String db= impl.getDatabaseName();
		Integer nb = impl.getDatabaseNumber();
		//System.out.println(db);
		//System.out.println(nb);
		
		try {
			writer = new PrintWriter("/home/pascaz10/eclipse-workspace/Neo4j-genere/"+db+".csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0;i<nb;i++) {
			
		Node field=null;
		StringBuffer line= new StringBuffer();;
		impl.reset();
		do {
			field = impl.getNextField();
			if(field!=null) {
				switch(field.getNodeName()) {
				case  "int":
					line.append(impl.getInt(field));
					break;
				case "string":
					line.append(impl.getString(field));
					break;
				case "date":
					line.append(impl.getDate(field));
					break;
				default:
					line.append("unknow");
					break;
				}
				line.append(",");
			
			}
		
		} while(field != null);
		
		if(!isEntete) {	
			writer.println(impl.getHeader());
			isEntete = true;
		}

		
		writer.println(line.substring(0, line.length() - 1));
	}
	writer.close();
	}
	

}

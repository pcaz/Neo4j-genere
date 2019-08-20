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
	
	static StringBuffer line;
	static boolean isHeader = false;
	static PrintWriter writer;
	static enum Type  {Entity, Relation};
	
	public static void main(String[] args) {
	
	Type type;
	
	int nbargs=args.length;
	
	if((nbargs == 0)) {
		Usage();
		return;
	}
	if(args[0].equals("-v")) {
		System.out.println(Constant.version);
		return;
	}
	else if (args[0].equals("-entity")) {
		type = Type.Entity;

	}
	
	else if(args[0].equals("-relation")) {
		type = Type.Relation;

	}
	
	else {
		Usage();
		return;
	}
	
	
	
		
	// get an implementation	
 		DatabaseFactory databaseFactory = new DatabaseFactory();
		DatabaseInterface impl=databaseFactory.getDatabase(Constant.method);
	// initiliaze schema	
		
	impl.dataBaseInitialyse("Neo4j-genere-v"+Constant.version);
	
	
	for(int a=1; a<args.length; a++) {
	// new file	
	String fileName = args[a];
	
	impl.dataSourceInitialyse(fileName);	

	// values for the new file
		
		String db= impl.getDatabaseName();
		Integer nb = impl.getDatabaseNumber();
	// open file
		System.out.println("Write "+db+".csv");
		try {
			writer = new PrintWriter(db+".csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	// write header	
		if(!isHeader) {	
			writer.println(impl.getHeader());
			isHeader = true;
		}

	// search fieds
		
		for(int i=0;i<nb;i++) {
			
		Node field=null;
		StringBuffer line= new StringBuffer();;
		impl.resetLine();
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
		
		
	// write line	
		writer.println(line.substring(0, line.length() - 1));
	}

	// close file	
	writer.close();
	System.out.println("done");
	
	impl.resetDataSource();
	isHeader=false;
	}
	}
	
	static void Usage() {
		
		System.out.println("Neo4J-genere -v :version");
		System.out.println("Neo4J-genere -entity file [files..] : Entity files");
		System.out.println("Neo4J-genere -relation file [files...] Relation files");
		
	}

}

package com.neo4j.genere.dom;

import org.w3c.dom.Node;
import com.neo4j.genere.dom.Util;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class DateNode {
	
	Util util;
	public String getNodeGenere(Node field) {
		
		String Ret="";
        util = new Util();		
		util.removeText(field.getChildNodes());
		String Name=field.getFirstChild().getTextContent();
		
		int opt;
		if(((opt = util.getOptionnal(field)) != 100)){
			if(Math.round(Math.random() * 100) > opt ) {
				return "";
			}	
		}
		
		Node space = util.getSpace(field);
		
		util.removeText(space.getChildNodes());
		
		Node first = space.getFirstChild();
		String name=first.getNodeName();
		switch(name) {
		case "after":
			String after;
			String before;
			//util.removeText(first.getChildNodes());
			after=first.getTextContent();
			Node nodeBefore=first.getNextSibling();
			before = nodeBefore.getTextContent();
			Ret = getDate(after,before);
			break;
		}
		
			
	    
		
		Semantic.getInstance().setEntry(Name,Ret);
		Semantic.getInstance().setHeader(Name,":DATE");
		return Ret;
	}

	String getDate(String after, String before) {
		
		
		long afterDate = LocalDate.parse(after).toEpochDay();
		long beforeDate = LocalDate.parse(before).toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(afterDate, beforeDate);
	    LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
	    return randomDate.toString();
	    
	}
}

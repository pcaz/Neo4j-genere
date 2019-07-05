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

// if optionnal field, evaluate		
		int opt;
		if(((opt = util.getOptionnal(field)) != 100)){
			if(Math.round(Math.random() * 100) > opt ) {
				return "";
			}	
		}
		
// go to space field		
		Node space = util.getSpace(field);			
		util.removeText(space.getChildNodes());

// get date field		
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
		
	// manage semantic list (it's a singleton)		
	Semantic.getInstance().setEntry(Name,Ret);
	
		return Ret;
	}

	private String getDate(String after, String before) {
		
		// random date in range
		
		long afterDate = LocalDate.parse(after).toEpochDay();
		long beforeDate = LocalDate.parse(before).toEpochDay();
		long randomDay = afterDate + (int)(Math.random() * ((beforeDate - afterDate) + 1));
	    LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
	    return randomDate.toString();
	    
	}
	
	public void reset() {
		
	}
}

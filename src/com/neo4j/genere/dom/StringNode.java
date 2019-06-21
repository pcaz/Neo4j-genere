package com.neo4j.genere.dom;

import java.util.Random;
import java.util.stream.Collectors;

import org.w3c.dom.Node;
import com.neo4j.genere.dom.Util;

public class StringNode {
	
	public String getNodeGenere(Node field) {
		
		String Ret="";
		Util util = new Util(); 
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
		case "long":
				Integer min=0;
				Integer max=0;
				util.removeText(first.getChildNodes());
				Node nodeMin=first.getFirstChild();
				min = Integer.parseInt(nodeMin.getTextContent());
				Node nodeMax=nodeMin.getNextSibling();
				if((nodeMax != null) &&(nodeMax.getNodeName().contentEquals("max"))){
					max = Integer.parseInt(nodeMax.getTextContent());
				}
				if(min < 3) {
					System.out.println(Name+":minimum string min must be 3 or greater ");
					System.exit(2);
				}
				if (! (max > min)) {
					System.out.println("incorrect min/max to string"+Name);
					System.exit(2);
				}
			Ret = newString(min,max,Name); // setEntry of semantic is done in newString
		
			break;
		case "regular":
				RegularExpression rg=new RegularExpression();
				String val=first.getTextContent();
				Ret=rg.getString(first.getTextContent());
				Semantic.getInstance().setEntry(Name,Ret);
				break;
			
	    }
		
	
			
		return Ret;
	}

	private String newString(int min, int max, String name) {
		
		int length=0;
		boolean done=false;
		String str;
		
		do{
			length = (int)((Math.round(Math.random() * max)));
		} while (length < min);
		
		String chars = "abcdefghijklmnopqrstuvwxyz";
		
		do {
			str = new Random().ints(length, 0, chars.length())	
		                         .mapToObj(i -> "" + chars.charAt(i))
		                         .collect(Collectors.joining());
		
		
			if(!Semantic.getInstance().isEntry(str)) {
				Semantic.getInstance().setEntry(name,str);
				done = true;
			}
		} while(!done);
		
		return str;
	}
}

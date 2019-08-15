package com.neo4j.genere.dom;



import org.w3c.dom.Node;
import com.neo4j.genere.dom.Semantic;
import com.neo4j.genere.dom.Util;

public class IntNode {
	
	private static int ind=0;
	int optionnal;
	Node space;
	
	
	
	public String getNodeGenere(Node field) {
	
		Util util = new Util(); 
		String Ret="";
		util.removeText(field.getChildNodes());
		String Name=field.getFirstChild().getTextContent();
		
		int opt;
		if(((opt = util.getOptionnal(field.getFirstChild())) != 100)){
			if(Math.round(Math.random() * 100) > opt ) {
				return "";
			}	
		}
		space = util.getSpace(field);
		
		util.removeText(space.getChildNodes());
		
		Node first = space.getFirstChild();
		String name=first.getNodeName();
		switch(name) {
		case "identifiant":
				ind++;
				Ret = ((Integer)ind).toString();
				
				break;
		case "values":
			Integer min=0;
			Integer max=0;
			util.removeText(first.getChildNodes());
			Node nodeMin=first.getFirstChild();
			min = Integer.parseInt(nodeMin.getTextContent());
			Node nodeMax=nodeMin.getNextSibling();
			if((nodeMax != null) &&(nodeMax.getNodeName().contentEquals("max"))){
				max = Integer.parseInt(nodeMax.getTextContent());
			}
			else
			{Ret = "0";}
	
			if(min <= max) {int val = min + (int)(Math.random() * ((max - min) + 1));
			Ret=((Integer)val).toString();}
			else {Ret = "0";}
	
			break;
			
		}
		Semantic.getInstance().setEntry(Name,Ret);
		
		return Ret;
	}

	public void reset() {
		
	}
}

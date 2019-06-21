package com.neo4j.genere.dom;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Util {
	
	public int getOptionnal(Node field) {

		NodeList ls=field.getChildNodes();
		
		removeText(ls);
		
		Node found = null;
		
		for(int i=0; i < ls.getLength();i++) {
			if(ls.item(i).getNodeName().equals("optionnal")) {
				found=ls.item(i);
				break;
			}
		}
		if (found == null) { return 100; }
		else { return Integer.parseInt(found.getTextContent());}  
	}
	
	public Node getSpace(Node field) {
	
		NodeList ls=field.getChildNodes();
		
		removeText(ls);
		
		for(int i=0; i < ls.getLength();i++) {
			if(ls.item(i).getNodeName().equals("space")) {
				return ls.item(i);
			}
		}
		
			
	return null;
	}
	
	
	public void removeText(NodeList noeuds) {	
	 Set<Node> targetElements = new HashSet<Node>();
	
	 // first, we find the 'false' node in the nodelist (#text)
	 
	 for (int i = 0; i < noeuds.getLength(); i++) {
	   Node e = noeuds.item(i);
	   if (e.getNodeType()!= Node.ELEMENT_NODE) {
	     targetElements.add(e);
	   }
	 }
	 
	 // then, we remove them
	 
	 for (Node f:targetElements) {
	   f.getParentNode().removeChild(f);
	 }
	}
}



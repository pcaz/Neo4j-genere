package com.neo4j.genere.dom;

import java.util.Random;
import java.util.Date;
import java.util.stream.Collectors;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import com.neo4j.genere.dom.Util;

public class StringNode {
	
	private static int num=0;
	private static boolean fixedLenghtToIterate=true;
	
	public String getNodeGenere(Node field) {
		
		String Ret="";
		Util util = new Util(); 
		util.removeText(field.getChildNodes());
		String Name=field.getFirstChild().getTextContent();
		
		int opt;
		if(((opt = util.getOptionnal(field.getFirstChild())) != 100)){
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
		case "iterate":
			// default is fixed="true", "false" is attribute in XML
			   String fixed= ((Element) first).getAttribute("fixed");
			   if(fixed.equals("false")) {fixedLenghtToIterate=false;}
			   
			   Ret= Iterate();
			   Semantic.getInstance().setEntry(Name,Ret);
			
	    }
		
	
			
		return Ret;
	}
	
	// reset parser
	public void resetLine() {
		
	}
	
	public void resetDataSource() {
		num=0;
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
			str = new Random(new Date().getTime()).ints(length, 0, chars.length())	
		                         .mapToObj(i -> "" + chars.charAt(i))
		                         .collect(Collectors.joining());
		
		
			if(!Semantic.getInstance().isEntry(str)) {
				Semantic.getInstance().setEntry(name,str);
				done = true;
			}
		} while(!done);
		
		return str;
	}
	
	private String Iterate() {
	
		String car="abcdefghijklmnopqrstuvwxyz";
		
		StringBuffer res=new StringBuffer();
		int val=num;
		int val1=num;
		
		if(fixedLenghtToIterate) {val1=val+26+676+17576;}
		
		// compute number of bits of result, must be 1,2 or 3
	
	
	int nbytes=0;
		
	if(val1<26) nbytes=1;
	if((26<= val1) && (val1 < 26+676)) nbytes = 2;
	if( (26+676 <= val1) && (val1 < 26+476+17576)) nbytes = 3;
	if( (26+476+17576 <= val1) && (val1 < 26+676+17576+456976)) nbytes=4;
	if (val1 > 26+676+17576+456976)  throw new IllegalArgumentException ("Maximum value is 475254 or 456976 in fixedLength ! ");
		
	val=val1;
	
	switch (nbytes) {
	case 4:
		val = (val1 - 26 - 676);
		val = val1 / 17576;
		res.append(car.substring(val-1,val));
		val1= val1 - (val * 17576);	
	case 3:
		val = (val1 - 26);
		val = val / 676;
		res.append(car.substring(val-1,val));
		val1= val1 - (val * 676);
	case 2:	
		val=val1 / 26;
		res.append(car.substring(val-1,val));
		val= val1 % 26;
	case 1:
		res.append(car.substring(val,val+1));
	}
				

        num++;		
		return res.toString();
		
		
	}
		
}
	
		

	
	


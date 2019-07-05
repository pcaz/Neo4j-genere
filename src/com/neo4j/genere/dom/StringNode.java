package com.neo4j.genere.dom;

import java.util.Random;
import java.util.Date;
import java.util.stream.Collectors;

import org.w3c.dom.Node;
import com.neo4j.genere.dom.Util;

public class StringNode {
	
	private static int num=0;
	
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
		case "iterate":
			   Ret= Iterate();
			   Semantic.getInstance().setEntry(Name,Ret);
			
	    }
		
	
			
		return Ret;
	}
	
	
	public void reset() {
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
	
		int val;

		String chars = "abcdefghijklmnopqrstuvwxyz";
		
		StringBuffer str = new StringBuffer();
		
		
		// d'abord, on calcule le nombre de bits
		
		val = num;
		
		int nbits=0;
		int vv;
		int pow=1;
		
		if(num<chars.length()) {
			nbits=1;
			pow=1;
		}
		
		if((num>= chars.length()) && (num < (chars.length()*chars.length()+chars.length()))){
			nbits=2;
			pow = chars.length();
		}
		
		if((num >= (chars.length()*chars.length()+chars.length())) && (num < chars.length()*chars.length()*chars.length() + chars.length()*chars.length()+chars.length())){
			nbits=3;
			pow = chars.length()*chars.length();
					
		}
		
/*		vv=val;
			
		do {			
			nbits++;
			vv= vv/chars.length();
		} while (vv>0);

	    

		// nbits est le nombre de byte du resultat
		int pow =1; 

		for(int i=1; i<nbits;i++) {
			 pow = pow * chars.length();
		}

		//les 26 premiers nombres ne sont pas comptabilisees dans le nombre de bits
		// 0..25->1
		// 26-801  -> 2
		
		if((nbits>2) && ((pow-val)<chars.length())){
			nbits--;
			pow=pow/chars.length();
			}
*/		
	
/*		
		int v=val/pow;
		str.append(chars.charAt(v));
		pow=pow/chars.length();
		v=val-pow;
*/		
		int val1=0;
		
		
		
		do {
			if (pow==1){
				val1=val;
			} 
			else {val1 = val/pow-1;
			}
			
			try {
			str.append(chars.charAt(val1));
			}catch (Exception e){
				System.out.println("num="+ num+ " val1="+val1+" pow="+pow);
				e.printStackTrace();
			}
			
			val = val - (val/pow)*pow;
			pow = pow / chars.length();
			nbits--;
		} while( nbits >0);

		num++;
		return str.toString();
		
/*		int n=0;
		do {
			int v = val/ pow;
			str.append(chars.charAt(v));
			val = val - pow;
			pow =pow / chars.length();
			
			n++;
		} while ( val>0);
		
		
		return str.toString();
*/
		
	}
	
		
}
	
	


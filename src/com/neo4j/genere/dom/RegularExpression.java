package com.neo4j.genere.dom;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression {
	
	public String getString(String expr) {
		
		String Ret="";
		Pattern p;
        Matcher m;
        String newExp;
        String beginExpr;
        String endExpr;
        String value;
        
		int indEval=expr.indexOf("eval(");
		if(indEval != -1) {
			beginExpr= expr.substring(0,indEval);
			newExp = expr.substring(indEval+("eval(".length()));
			int lastIndex = newExp.indexOf(')');
			value=newExp.substring(0,lastIndex);
		    endExpr = newExp.substring(lastIndex+1);
		    
		    String newValue=Semantic.getInstance().getEntry(value);
		
		    newExp = beginExpr+newValue+endExpr;
		    
		    Ret=(newExp);
	        //tous les chiffre de 0 à 9 sauf 345
	        p = Pattern.compile(newExp);
	        //m = p.matcher(newExp)
	      //  System.out.println(p.toString());

				
		
		}
		return Ret;
	}

}

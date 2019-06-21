package com.neo4j.genere;
import java.util.HashMap;
import java.util.Map;


public class DatabaseFactory {
	
		public String type=null;
		private Map<String,DatabaseInterface>  databaseImpl;
		

		@SuppressWarnings("unused")
		public DatabaseFactory() {
			databaseImpl = new HashMap<String,DatabaseInterface>();
		}
		
		
		public DatabaseInterface getDatabase(String method) {
		
		String name=method.substring(0, 1).toUpperCase()+method.substring(1);
		    	
		for ( Map.Entry<String, DatabaseInterface> entry : databaseImpl.entrySet()) {
			    if ( name.equals(entry.getKey())) {
			    	return entry.getValue();
			    }
			}
		// non impl found
		
		DatabaseInterface impl = null;
		String pack = this.getClass().getPackage().toString().substring(8)+"."+method.toLowerCase();
		String implName = pack+"."+"DatabaseImpl";
		try {
			impl = (DatabaseInterface)Class.forName(implName).newInstance();
		} catch (Exception e) {
			System.out.println("Unable to load the class: "+implName);
			System.exit(2);
			}
		
		if(impl!=null) {
			databaseImpl.put(name,impl);
		}
		return impl;
		}

}

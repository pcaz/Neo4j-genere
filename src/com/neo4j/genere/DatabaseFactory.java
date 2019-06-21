package com.neo4j.genere;
import java.util.HashMap;
import java.util.Map;


public class DatabaseFactory {

	// get an implementaion of database 
	
		public String type=null;
		private Map<String,DatabaseInterface>  databaseImpl;
		
	// list of immplementations
		
		@SuppressWarnings("unused")
		public DatabaseFactory() {
			databaseImpl = new HashMap<String,DatabaseInterface>();
		}
		
	// search for immplementation
		
		public DatabaseInterface getDatabase(String method) {
		
		String name=method.substring(0, 1).toUpperCase()+method.substring(1);
		
		// if already exists, return it

		for ( Map.Entry<String, DatabaseInterface> entry : databaseImpl.entrySet()) {
			    if ( name.equals(entry.getKey())) {
			    	return entry.getValue();
			    }
			}
		// non impl found, create a new if possible, each one in his package
		
		DatabaseInterface impl = null;
		String pack = this.getClass().getPackage().toString().substring(8)+"."+method.toLowerCase();
		String implName = pack+"."+"DatabaseImpl";
		
		// try to create
		try {
			impl = (DatabaseInterface)Class.forName(implName).newInstance();
		} catch (Exception e) {
			System.out.println("Unable to load the class: "+implName);
			System.exit(2);
			}
		
		// put it in list
		if(impl!=null) {
			databaseImpl.put(name,impl);
		}
		
		//return implementation
		return impl;
		}

}

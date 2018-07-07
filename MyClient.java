package estate;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.ws.rs.client.Client; 
import javax.ws.rs.client.ClientBuilder; 
import javax.ws.rs.client.Entity; 
import javax.ws.rs.core.Form; 
import javax.ws.rs.core.GenericType; 
import javax.ws.rs.core.MediaType;

public class MyClient {

	private Client client; 
	private Map<String, Integer> biddingHistory = new HashMap<String, Integer>();
	
	 public static void main(String[] args){ 
		 MyClient myClient = new MyClient();
	     myClient.client = ClientBuilder.newClient(); 
	     @SuppressWarnings("resource")
		 Scanner scanner = new Scanner(System.in);
	     int n = 4;
	      
	     // There's no user input validation for these commands
	     while(n != 0) {
	    	if(n == 1) {
	    		myClient.displayProperties(myClient.getProperties());
		    }
		      
		    else if(n == 2) {
		    	  System.out.println("Please enter 'H' if you're putting a House or 'A' if you're putting an Apartment up for sale. (String)");
		    	  String type = scanner.next();
		    	  System.out.println("Please enter the district number. (Integer)");
		    	  int district = scanner.nextInt();
		    	  System.out.println("Please enter the number of rooms. (Integer)");
		    	  int rooms = scanner.nextInt();
		    	  System.out.println("Please enter the price. (Integer)");
		    	  int price = scanner.nextInt();
		    	  System.out.println("How many months will the property be up for sale? (Integer)");
		    	  int time = scanner.nextInt();
		    	  
		    	  myClient.addProperty(district, rooms, price, type, time);
		      }

		    else if(n == 3){
		    	System.out.println("Enter property name: (String) eg. H1, A3");
		    	String name = scanner.next();
		    	System.out.println("Please enter bid: (Integer)");
		    	int bid = scanner.nextInt();
		    	myClient.bidding(name, bid);
		    }
		    else if(n == 4){
		    	System.out.println("Generating Options");
		    	myClient.menu();
		    }
		    else if(n == 5){
		    	System.out.println("Enter name of property: (String) eg. H1, A3");
		    	String id = scanner.next();
		    	myClient.viewTime(id);
		    }
		    else{
		    	System.out.println("Please enter a valid menu option!");
		    }
	    	n = scanner.nextInt();
	    	
	    	// Check if the user has been out bid on any properties they recently bid on
	    	myClient.checkBids(myClient);
	    }
	    System.out.println("Exiting");
	 }

	 private void menu(){
	 	System.out.println("1. Please enter 1 to display property listings");
	 	System.out.println("2. Please enter 2 to register a house for sale");
	 	System.out.println("3. Please enter 3 to place bid on a property");
	 	System.out.println("4. Please enter 4 to renew menu");
	 	System.out.println("5. Plaese enter 5 to view the viewing times of a property");
	 	System.out.println("0. Please enter 0 to exit client");
	}

	private void viewTime(String name){
	 	Form form = new Form();
	 	form.param("name", ""+name);
	   	String callResult = client 
	        .target("http://localhost:8080/EstateManager/rest/PropertyService/properties") 
	        .request(MediaType.APPLICATION_XML) 
	        .post(Entity.entity(form, 
	        MediaType.APPLICATION_FORM_URLENCODED_TYPE), 
	        String.class);

	    System.out.println(callResult); 
	}  
	 
	 private List<Property> getProperties(){
	      GenericType<List<Property>> list = new GenericType<List<Property>>() {}; 
	      List<Property> property = client 
	         .target("http://localhost:8080/EstateManager/rest/PropertyService/properties") 
	         .request(MediaType.APPLICATION_XML) 
	         .get(list); 
	      
	      return property;
	 }
	 
	 private void displayProperties(List<Property> property) {
		 Calendar cal = Calendar.getInstance();
		 Calendar cal2 = Calendar.getInstance();
		 
		 System.out.println("Name	Dist.	Rooms	Price	 Added		 Ends		 Max bid");
	      for(int i = 0; i < property.size(); i++) {
	    	  if(!property.get(i).getSold()) {
	    		  // If property is not sold already
	    		  cal.setTime(new Date(property.get(i).getStartTime()));
		    	  cal2.setTime(new Date(property.get(i).getEndTime()));
		    	  System.out.println(property.get(i).getName() + " \t" + 
		    			  property.get(i).getDistrict() + " \t" + 
		    			  property.get(i).getRooms() + " \t" + 
		    			  property.get(i).getPrice() + "\t " + 
		    			  cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR) + "\t " + 
		    			  cal2.get(Calendar.DAY_OF_MONTH)+"/"+(cal2.get(Calendar.MONTH)+1)+"/"+cal2.get(Calendar.YEAR) + "\t " + 
		    			  property.get(i).getBid());
	    	  }
	      }
	 }
	 
	 private void addProperty(int district, int rooms, int price, String type, int time){ 
		 Form form = new Form(); 
	      form.param("district", ""+district);
	      form.param("rooms", ""+rooms);
	      form.param("price", ""+price);
	      form.param("type", type);
	      form.param("time", ""+time);
	      String callResult = client 
	         .target("http://localhost:8080/EstateManager/rest/PropertyService/properties") 
	         .request(MediaType.APPLICATION_XML) 
	         .post(Entity.entity(form, 
	         MediaType.APPLICATION_FORM_URLENCODED_TYPE), 
	         String.class);
	      
	      System.out.println("Property added: " + callResult); 
	 }

	 private void bidding(String name, int bid){
	 	Form form = new Form();
	 	form.param("name", ""+name);
	    form.param("bid", ""+bid);
	   	String callResult = client 
	        .target("http://localhost:8080/EstateManager/rest/PropertyService/bid")
	        .request(MediaType.APPLICATION_XML) 
	        .post(Entity.entity(form, 
	        MediaType.APPLICATION_FORM_URLENCODED_TYPE), 
	        String.class);
	   	
	    System.out.println(callResult);
	    if(callResult.equals("Successful bid")) {
	    	// Add name of property and successful bid amount to hashmap
	    	biddingHistory.put(name, bid);
	    }
	 } 
	 
	 private void checkBids(MyClient c) {
		 List<Property> propertyList = c.getProperties();
		 
		 for(int i = 0; i < propertyList.size(); i++) {
			 // Check on the property name in the biddingHistory
			 // If the highest bid is higher than the user's highest bid then prompt for counter offer
			 if(biddingHistory.containsKey(propertyList.get(i).getName()) && propertyList.get(i).getSold()) {
				 // Property has sold that a user has bid on
				 System.out.println("Property " + propertyList.get(i).getName() + " has been sold.");
			 } else if(biddingHistory.containsKey(propertyList.get(i).getName())) {
				 // Check if the bid has been beaten
				 if(biddingHistory.get(propertyList.get(i).getName()) < propertyList.get(i).getBid()) {
					 // Prompt user to counter offer
					 System.out.println("Property " + propertyList.get(i).getName() + " now has a highest bid for " + propertyList.get(i).getBid() + ".");
					 System.out.println("Would you like to counter offer? Type 'y' or 'n' ");
					 @SuppressWarnings("resource")
					 Scanner scanner = new Scanner(System.in);
				     String n = scanner.next();
				     if(n.equals("y")) {
				    	System.out.println("Please enter bid: (Integer)");
				    	int bid = scanner.nextInt();
				    	c.bidding(propertyList.get(i).getName(), bid);
				     } else {
				    	 // User typed 'n' so remove from biddingHistory
				    	 biddingHistory.remove(propertyList.get(i).getName());
				     }
				 }
			 }
		 }
	 }
}

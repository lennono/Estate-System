package estate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class PropertyDao {
	private final Lock lock = new ReentrantLock();
	
	public int[] getNumOfProperties() {
		lock.lock();
		try {
			List<Property> propertyList = getProperties();
			int houses = 1;
			int apartments = 1;
			
			for(int i = 0; i < propertyList.size(); i++ ) {
				if(propertyList.get(i).getType().equals("H")) {
					houses++;
				} else {
					apartments++;
				}
			}
			int[] result = {houses, apartments};
			return result;
		} finally {
			lock.unlock();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Property> getProperties() {
		List<Property> propertyList = new LinkedList<Property>();
		lock.lock();
		try {
			try { 
		         File file = new File("Properties.dat");
		         if (!file.exists()) {
		        	 // Create initial properties if the file doesn't exist
		        	 Property p1 = new Property("H1", 3, 2, 375000, "H");
		        	 p1.setStartTime(new Date().getTime());
		        	 p1.setEndTime(generateTime(2));
		        	 Property p2 = new Property("H2", 5, 3, 360000, "H");
		        	 p2.setStartTime(new Date().getTime());
		        	 p2.setEndTime(generateTime(3));
		        	 Property p3 = new Property("H3", 3, 3, 500000, "H");
		        	 p3.setStartTime(new Date().getTime());
		        	 p3.setEndTime(generateTime(4));
		        	 propertyList.add(p1);
	     			 propertyList.add(p2);
	     			 propertyList.add(p3);
		             savePropertyList(propertyList);   
		          }
		          else{ 
		             FileInputStream fis = new FileInputStream(file); 
		             ObjectInputStream ois = new ObjectInputStream(fis); 
		             propertyList = (List<Property>) ois.readObject(); 
		             ois.close(); 
		          }
			  } catch (IOException e) { 
		         e.printStackTrace(); 
		      } catch (ClassNotFoundException e) { 
		         e.printStackTrace(); 
		      }
			
			// Update property sold status if end date has been reached
			setSoldStatus(propertyList);
			
			return propertyList;
		} finally {
			lock.unlock();
		}
	}

	private void updateProperty(Property home){
		lock.lock();
		try {
		   try { 
				File file = new File("Properties.dat");
				String property = home.getName();
				List<Property> props = getProperties();

				for(int i = 0; i < props.size(); i++) {
					if(property.equals(props.get(i).getName())){
						props.remove(i);
						break;
					}
				}
				props.add(home);

				FileOutputStream change = new FileOutputStream(file, false);
				ObjectOutputStream oos = new ObjectOutputStream(change);
				oos.writeObject(props); 
		        oos.close();
		        } catch (FileNotFoundException e) { 
		         e.printStackTrace(); 
		      } catch (IOException e) { 
		         e.printStackTrace(); 
		      }
	   } finally {
		   lock.unlock();
	   }  
	}


	public String bidding(String name1, int bid)
	{
		List<Property> props = getProperties();

		for(int i = 0; i < props.size(); i++) {
			if(props.get(i).getName().equals(name1)){
				if(bid > props.get(i).getBid() && bid > props.get(i).getPrice()){
					props.get(i).setBid(bid);
					updateProperty(props.get(i));
					return "Successful bid";
				}
			}
		}
		return "Unsuccessful bid";
	}
	
	public void addProperty(Property p) {
		List<Property> propertyList = getProperties();
		
		// Add to list and save
		propertyList.add(p);
		savePropertyList(propertyList);
	}
	
   private void savePropertyList(List<Property> propertyList){ 
	   lock.lock();
	   try {
		   try { 
		         File file = new File("Properties.dat"); 
		         FileOutputStream fos;  
		         fos = new FileOutputStream(file);
		         ObjectOutputStream oos = new ObjectOutputStream(fos);   
		         oos.writeObject(propertyList); 
		         oos.close(); 
		      } catch (FileNotFoundException e) { 
		         e.printStackTrace(); 
		      } catch (IOException e) { 
		         e.printStackTrace(); 
		      }
	   } finally {
		   lock.unlock();
	   }
   } 
   
	private long generateTime(int months){
	 	Date date = new Date();
	 	long current = date.getTime();
	 	Long month = 2628000000L;
	 	// 2628000000 is a month in milliseconds
	 	Long endDate = current +(new Long(months)*month);
	 	return endDate;
	}
	
	private void setSoldStatus(List<Property> p) {
		lock.lock();
		try {
			for(int i = 0; i < p.size(); i++) {
				Date date = new Date();
				if(date.getTime() > p.get(i).getEndTime()) {
					// Time has ended for the property so its status is set to sold
					p.get(i).setSold(true);
				}
			}
		} finally {
			lock.unlock();
		}
	}
}

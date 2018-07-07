package estate;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement; 
@XmlRootElement(name = "property") 

public class Property implements Serializable {

   private static final long serialVersionUID = 1L; 
   private String name;
   private int district;
   private int rooms;
   private int price;
   private String type;
   private long startTime;
   private long endTime;
   private int highestBid = 0;
   private boolean sold = false;
   
   public Property(){}
   
   public Property(String name, int district, int rooms, int price, String type){
	   this.setName(name);
	   this.setDistrict(district);
	   this.setRooms(rooms);
	   this.setPrice(price);
	   this.setType(type);
   }

   public String getName() {
		return name;
   }

   @XmlElement
   public void setName(String name) {
		this.name = name;
   }  

   public int getDistrict() {
	   return district;
   }

   @XmlElement
   public void setDistrict(int district) {
	   this.district = district;
   }

   public int getRooms() {
	   return rooms;
   }

   @XmlElement
   public void setRooms(int rooms) {
	   this.rooms = rooms;
   }

   public int getPrice() {
	   return price;
   }

   @XmlElement
   public void setPrice(int price) {
	   this.price = price;
   }

   public String getType() {
	   return type;
   }

   @XmlElement
   public void setType(String type) {
	   this.type = type;
   }
   
   public long getStartTime() {
	   return this.startTime;
   }
   
   @XmlElement
   public void setStartTime(long startTime) {
	   this.startTime = startTime;
   }

   public long getEndTime() {
	   return this.endTime;
   }
   
   @XmlElement
   public void setEndTime(long endTime) {
	   this.endTime = endTime;
   }

   public int getBid() {
      return highestBid;
   }
   
   @XmlElement
   public void setBid(int highestBid) {
      this.highestBid = highestBid;
   }
   
   public boolean getSold() {
	   return sold;
   }
   
   @XmlElement
   public void setSold(boolean sold) {
	   this.sold = sold;
   }
}

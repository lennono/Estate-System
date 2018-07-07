package estate;

import java.util.Date;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/PropertyService")


public class PropertyService {

	PropertyDao propertyDao = new PropertyDao();
	
	@GET
	@Path("/properties")
    @Produces(MediaType.APPLICATION_XML)
    public List<Property> getProperties(){
		return propertyDao.getProperties();
    }
	
	@POST
	@Path("/properties") 
	@Produces(MediaType.APPLICATION_XML) 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	public String addProperty(@FormParam("district") int district, 
		@FormParam("rooms") int rooms, 
		@FormParam("price") int price, 
		@FormParam("type") String type, 
		@FormParam("time") int time, 
		@Context HttpServletResponse servletResponse) throws IOException{ 
		Property property;
		if(type.equals("H")) {
			property = new Property("" + type + propertyDao.getNumOfProperties()[0], district, rooms, price, type); 
		} else {
			property = new Property("" + type + propertyDao.getNumOfProperties()[1], district, rooms, price, type); 
		}
		property.setStartTime(new Date().getTime());
		property.setEndTime(generateTime(time));
		
		propertyDao.addProperty(property);
		
		return ""+ property.getName();
	
	}

	@POST
	@Path("/bid")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
    public String bidding(@FormParam("name") String name, 
		@FormParam("bid") int bid,  
		@Context HttpServletResponse servletResponse) throws IOException{ 
		//Property property;
		String successBid = propertyDao.bidding(name, bid);
		return successBid;
	}

	private long generateTime(int months){
	 	Date date = new Date();
	 	long current = date.getTime();
	 	Long month = 2628000000L;
	 	// 2628000000 is a month in milliseconds
	 	Long endDate = current +(new Long(months)*month);
	 	return endDate;
	}
	
}

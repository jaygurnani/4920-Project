package main;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
	
	private int id;
	private String Description;
	private String startLocation;
	private String shipsTo;
	private String category;
	private Date startDate;
	private Date endDate;
	private String owner;
	private int minBid;
	private int firstBid;
	private String firstBidUser;
	private int secondBid;
	private String secondBidUser;
	
	//set up object with empty values
	public Item() {
	}
	
	//set up object with specified values
	public Item(int id, String Description, String startLocation, 
			    String shipsTo, String category, Date startDate,
			    Date endDate, String owner, int minBid, 
			    int firstBid, String firstBidUser, 
			    int secondBid,String secondBidUser) {
		this.id = id;
		this.Description = Description;
		this.startLocation = startLocation;
		this.shipsTo = shipsTo;
		this.category = category;
		this.startDate = startDate;
		this.endDate = endDate;
		this.owner = owner;
		this.minBid = minBid;
		this.firstBid = firstBid;
		this.firstBidUser = firstBidUser;
		this.secondBid = secondBid;
		this.secondBidUser = secondBidUser;
	}

	//Custom field getters
	
	public String getStartDateString() {
		return df.format(startDate);
	}
	
	public String getEndDateString() {
		return df.format(endDate);
	}
	
	public String getEndsIn() {
		StringBuilder sb = new StringBuilder();
		Date now = new Date();
		long endsIn = endDate.getTime() - now.getTime();
		
		if (endsIn > 1000*60*60*24) {
			return (endsIn / (1000*60*60*24)) + " days";
		} else {
			if (endsIn > 1000*60*60) {
				sb.append(endsIn / (1000*60*60));
				sb.append(" hours, ");
			}
			if (endsIn > 1000*60) {
				sb.append(endsIn / (1000*60));
				sb.append(" minutes, ");
			}
			sb.append(endsIn / 1000);
			sb.append(" seconds");
			return sb.toString();
		}
	}
	
	public String getMinBidString() {
		return String.format("%d.%02d", (minBid / 100), (minBid % 100));
	}
	
	public String getFirstBidString() {
		return String.format("%d.%02d", (firstBid / 100), (firstBid % 100));
	}
	
	public String getSecondBidString() {
		return String.format("%d.%02d", (secondBid / 100), (secondBid % 100));
	}
	
	//Generated Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getShipsTo() {
		return shipsTo;
	}

	public void setShipsTo(String shipsTo) {
		this.shipsTo = shipsTo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getMinBid() {
		return minBid;
	}

	public void setMinBid(int minBid) {
		this.minBid = minBid;
	}

	public int getFirstBid() {
		return firstBid;
	}

	public void setFirstBid(int firstBid) {
		this.firstBid = firstBid;
	}

	public String getFirstBidUser() {
		return firstBidUser;
	}

	public void setFirstBidUser(String firstBidUser) {
		this.firstBidUser = firstBidUser;
	}

	public int getSecondBid() {
		return secondBid;
	}

	public void setSecondBid(int secondBid) {
		this.secondBid = secondBid;
	}

	public String getSecondBidUser() {
		return secondBidUser;
	}

	public void setSecondBidUser(String secondBidUser) {
		this.secondBidUser = secondBidUser;
	}
	
}
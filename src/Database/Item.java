package Database;

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
	private int ownerId;
	private String ownerName;
	private int minBid;
	private int firstBid;
	private int firstBidUserId;
	private String firstBidUserName;
	private int secondBidUserId;
	private int secondBid;
	private String secondBidUserName;
	private boolean finished;
	
	//set up object with empty values
	public Item() {
	}
	
	//set up object with specified values
	public Item(int id, String Description, String startLocation, 
			    String shipsTo, String category, Date startDate,
			    Date endDate, int ownerId, String ownerName, int minBid, 
			    int firstBid, int firstBidUserId, String firstBidUserName, 
			    int secondBid,int secondBidUserId, String secondBidUserName,
			    boolean finished) {
		this.id = id;
		this.Description = Description;
		this.startLocation = startLocation;
		this.shipsTo = shipsTo;
		this.category = category;
		this.startDate = startDate;
		this.endDate = endDate;
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.minBid = minBid;
		this.firstBid = firstBid;
		this.firstBidUserId = firstBidUserId;
		this.firstBidUserName = firstBidUserName;
		this.secondBid = secondBid;
		this.secondBidUserId = secondBidUserId;
		this.secondBidUserName = secondBidUserName;
		this.finished = finished;
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
	
	//the winning amount is the secondBid if it has been made,
	//if it hasn't it's the first bid
	public String getWinBidString() {
		if (secondBid != 0 && secondBid > minBid) {
			return getSecondBidString();
		} else {
			return getFirstBidString();
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

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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

	public int getFirstBidUserId() {
		return firstBidUserId;
	}

	public void setFirstBidUserId(int firstBidUserId) {
		this.firstBidUserId = firstBidUserId;
	}

	
	public String getFirstBidUserName() {
		return firstBidUserName;
	}

	public void setFirstBidUserName(String firstBidUserName) {
		this.firstBidUserName = firstBidUserName;
	}

	public int getSecondBid() {
		return secondBid;
	}

	public void setSecondBid(int secondBid) {
		this.secondBid = secondBid;
	}


	public int getSecondBidUserId() {
		return secondBidUserId;
	}

	public void setSecondBidUserId(int secondBidUserId) {
		this.secondBidUserId = secondBidUserId;
	}
	
	public String getSecondBidUserName() {
		return secondBidUserName;
	}

	public void setSecondBidUserName(String secondBidUserName) {
		this.secondBidUserName = secondBidUserName;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
}
package main;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Database {
	
	//db connection
	private Connection connection = null;
	
	//Constants
	private final static int timeout = 30;
	private final static String dbPath = "D:/auction";
	private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public Database() throws Exception {
		//Register Database Connector Driver
		Class.forName("org.sqlite.JDBC");
		//Establish Database Connection
		connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	}
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Couldn't close db? " + e);
		}
	}
	
	public List<Item> search(String search) throws Exception {
		List<Item> items = new ArrayList<Item>();
		
		//Query database
		String query = "SELECT i.id AS id, " +
		               "i.description AS description, " +
		               "sellLoc.name AS startLocation, " +
		               "shipLoc.name AS shipsTo, " +
		               "i.category AS category, " +
		               "i.startDate AS startDate, " +
		               "i.endDate AS endDate, " +
		               "seller.name AS owner, " +
		               "i.minBid AS minBid, " +
		               "i.firstBid AS firstBid, " +
		               "bidder1.name AS firstBidUser, " +
		               "i.secondBid AS secondBid, " +
		               "bidder2.name AS secondBidUser " +
					   "FROM item i " + 
					   "LEFT OUTER JOIN locations sellLoc ON (i.startlocation = sellLoc.id)" +
					   "LEFT OUTER JOIN locations shipLoc ON (i.shipsto = shipLoc.id) " +
					   "LEFT OUTER JOIN user seller ON (i.owner = seller.id) " +
					   "LEFT OUTER JOIN user bidder1 ON (i.firstBidUser = bidder1.id) " +
					   "LEFT OUTER JOIN user bidder2 ON (i.secondBidUser = bidder2.id) " +
					   "WHERE i.description LIKE ?";
		PreparedStatement statement = connection.prepareStatement(query);
	  	statement.setString(1, "%"+search+"%");
    	statement.setQueryTimeout(timeout);
    	ResultSet rs = statement.executeQuery();
    	    	
    	//add all results to items
    	while (rs.next()) {
    		items.add(new Item(rs.getInt("id"),
    					rs.getString("Description"), 
    					rs.getString("startlocation"),
    					rs.getString("shipsto"),
    					rs.getString("category"),
    					df.parse(rs.getString("startDate")),
    					df.parse(rs.getString("endDate")),
    					rs.getString("owner"),
    					rs.getInt("minBid"),
    					rs.getInt("firstBid"),
    					rs.getString("firstBidUser"),
    					rs.getInt("secondBid"),
    					rs.getString("secondBidUser")));
    	}
    	rs.close();
    	
    	return items;
	}
	
	//sortBy isn't validated, never get from user
	public List<Item> sortedSearch(String search, String sortBy) throws Exception {
		List<Item> items = new ArrayList<Item>();
		
		//Query database
		String query = "SELECT i.id AS id, " +
	               	   "i.description AS description, " +
	                   "sellLoc.name AS startLocation, " +
	                   "shipLoc.name AS shipsTo, " +
	                   "i.category AS category, " +
	                   "i.startDate AS startDate, " +
	                   "i.endDate AS endDate, " +
	                   "seller.name AS owner, " +
		               "i.minBid AS minBid, " +
	                   "i.firstBid AS firstBid, " +
	                   "bidder1.name AS firstBidUser, " +
	                   "i.secondBid AS secondBid, " +
	                   "bidder2.name AS secondBidUser " +
		    		   "FROM item i " + 
				       "LEFT OUTER JOIN locations sellLoc ON (i.startlocation = sellLoc.id)" +
				       "LEFT OUTER JOIN locations shipLoc ON (i.shipsto = shipLoc.id) " +
				       "LEFT OUTER JOIN user seller ON (i.owner = seller.id) " +
				       "LEFT OUTER JOIN user bidder1 ON (i.firstBidUser = bidder1.id) " +
				       "LEFT OUTER JOIN user bidder2 ON (i.secondBidUser = bidder2.id) " +
					   "WHERE i.description like ? " +
					   "ORDER BY " + sortBy;
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, "%"+search+"%");
		statement.setQueryTimeout(timeout);
		ResultSet rs = statement.executeQuery();
		
		//add all results to items
		while (rs.next()) {
    		items.add(new Item(rs.getInt("id"),
    				   rs.getString("Description"), 
					   rs.getString("startlocation"),
					   rs.getString("shipsto"),
					   rs.getString("category"),
					   df.parse(rs.getString("startDate")),
					   df.parse(rs.getString("endDate")),
					   rs.getString("owner"),
					   rs.getInt("minBid"),
					   rs.getInt("firstBid"),
					   rs.getString("firstBidUser"),
					   rs.getInt("secondBid"),
					   rs.getString("secondBidUser")));
		}
		rs.close();
		
		return items;
	}
	
	public Item getItemById(int id) throws Exception {
		
		//Query database for item
		String query = "SELECT i.id AS id, " +
            	       "i.description AS description, " +
                       "sellLoc.name AS startLocation, " +
                       "shipLoc.name AS shipsTo, " +
                       "i.category AS category, " +
                       "i.startDate AS startDate, " +
                       "i.endDate AS endDate, " +
                       "seller.name AS owner, " +
		               "i.minBid AS minBid, " +
                       "i.firstBid AS firstBid, " +
                       "bidder1.name AS firstBidUser, " +
                       "i.secondBid AS secondBid, " +
                       "bidder2.name AS secondBidUser " +
	    		       "FROM item i " + 
	    		       "LEFT OUTER JOIN locations sellLoc ON (i.startlocation = sellLoc.id)" +
		    	       "LEFT OUTER JOIN locations shipLoc ON (i.shipsto = shipLoc.id) " +
			           "LEFT OUTER JOIN user seller ON (i.owner = seller.id) " +
			           "LEFT OUTER JOIN user bidder1 ON (i.firstBidUser = bidder1.id) " +
			           "LEFT OUTER JOIN user bidder2 ON (i.secondBidUser = bidder2.id) " +
				       "WHERE i.id = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		statement.setQueryTimeout(timeout);
		ResultSet rs = statement.executeQuery();
		
		//Create item from result
		Item item = new Item(rs.getInt("id"),
				             rs.getString("Description"),
						     rs.getString("startlocation"),
						     rs.getString("shipsto"),
						     rs.getString("category"),
						     df.parse(rs.getString("startDate")),
						     df.parse(rs.getString("endDate")),
						     rs.getString("owner"),
						     rs.getInt("minBid"),
						     rs.getInt("firstBid"),
						     rs.getString("firstBidUser"),
						     rs.getInt("secondBid"),
						     rs.getString("secondBidUser"));
		rs.close();

		return item;
	}
	
	public List<Item> getItemsByCategory(String category) throws Exception {
		List<Item> items = new ArrayList<Item>();
		
		//Query database
		String query = "SELECT i.id AS id, " +
     	               "i.description AS description, " +
                       "sellLoc.name AS startLocation, " +
                       "shipLoc.name AS shipsTo, " +
                       "i.category AS category, " +
                       "i.startDate AS startDate, " +
                       "i.endDate AS endDate, " +
                       "seller.name AS owner, " +
		               "i.minBid AS minBid, " +
                       "i.firstBid AS firstBid, " +
                       "bidder1.name AS firstBidUser, " +
                       "i.secondBid AS secondBid, " +
                       "bidder2.name AS secondBidUser " +
 	        	       "FROM item i " + 
 	         	       "LEFT OUTER JOIN locations sellLoc ON (i.startlocation = sellLoc.id)" +
	    	           "LEFT OUTER JOIN locations shipLoc ON (i.shipsto = shipLoc.id) " +
		               "LEFT OUTER JOIN user seller ON (i.owner = seller.id) " +
		               "LEFT OUTER JOIN user bidder1 ON (i.firstBidUser = bidder1.id) " +
		               "LEFT OUTER JOIN user bidder2 ON (i.secondBidUser = bidder2.id) " +
			           "WHERE i.category = ?";
		PreparedStatement statement = connection.prepareStatement(query);
	  	statement.setString(1, category);
    	statement.setQueryTimeout(timeout);
    	ResultSet rs = statement.executeQuery();
    	    	
    	//add all results to items
    	while (rs.next()) {
    		items.add(new Item(rs.getInt("id"),
    						   rs.getString("Description"), 
    						   rs.getString("startlocation"),
    						   rs.getString("shipsto"),
    						   rs.getString("category"),
    						   df.parse(rs.getString("startDate")),
    						   df.parse(rs.getString("endDate")),
    						   rs.getString("owner"),
    						   rs.getInt("minBid"),
    						   rs.getInt("firstBid"),
    						   rs.getString("firstBidUser"),
    						   rs.getInt("secondBid"),
    						   rs.getString("secondBidUser")));
    	}
    	rs.close();
    	
    	return items;
	}
	
	public void updateItem(int id, Item item) throws Exception {
		
		//Construct update
		String update = "UPDATE item " +
						"SET Description = ?, " +
						"startLocation = ?, " +
						"shipsto = ?, " +
						"category = ?, " +
						"startDate = ?, " +
						"endDate = ?, " +
						"owner = ?, " +
						"firstBid = ?, " +
						"firstBidUser = ?, " +
						"secondBid = ?, " +
						"secondBidUser = ? " +
						"WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(update);
		statement.setString(1, item.getDescription());
		statement.setString(2, item.getStartLocation());
		statement.setString(3, item.getShipsTo());
		statement.setString(4, item.getCategory());
		statement.setString(5, df.format(item.getStartDate()));
		statement.setString(6, df.format(item.getEndDate()));
		statement.setString(7, item.getOwner());
		statement.setInt(8, item.getFirstBid());
		statement.setString(9, item.getFirstBidUser());
		statement.setInt(10, item.getSecondBid());
		statement.setString(11, item.getSecondBidUser());
		statement.setInt(12, item.getId());
		statement.setQueryTimeout(timeout);
		
		//execute update
		statement.executeUpdate();
	}
}

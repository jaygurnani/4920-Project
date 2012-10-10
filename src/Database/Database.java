package Database;

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
		               "seller.id AS ownerId, " +
		               "seller.name AS ownerName, " +
		               "i.minBid AS minBid, " +
		               "i.firstBid AS firstBid, " +
		               "bidder1.id as firstBidUserId, " +
		               "bidder1.name AS firstBidUserName, " +
		               "i.secondBid AS secondBid, " +
		               "bidder2.id AS secondBidUserId, " +
		               "bidder2.name AS secondBidUserName " +
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
    					rs.getInt("ownerId"),
    					rs.getString("ownerName"),
    					rs.getInt("minBid"),
    					rs.getInt("firstBid"),
    					rs.getInt("firstBidUserId"),
    					rs.getString("firstBidUserName"),
    					rs.getInt("secondBid"),
    					rs.getInt("secondBidUserId"),
    					rs.getString("secondBidUserName")));
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
	                   "seller.id AS ownerId, " +
	                   "seller.name AS ownerName, " +
		               "i.minBid AS minBid, " +
	                   "i.firstBid AS firstBid, " +
		               "bidder1.id as firstBidUserId, " +
		               "bidder1.name AS firstBidUserName, " +
	                   "i.secondBid AS secondBid, " +
		               "bidder2.id AS secondBidUserId, " +
		               "bidder2.name AS secondBidUserName " +
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
					   rs.getInt("ownerId"),
					   rs.getString("ownerName"),
					   rs.getInt("minBid"),
   					   rs.getInt("firstBid"),
   					   rs.getInt("firstBidUserId"),
   				       rs.getString("firstBidUserName"),
   					   rs.getInt("secondBid"),
   					   rs.getInt("secondBidUserId"),
   					   rs.getString("secondBidUserName")));
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
                       "seller.id AS ownerId, " +
                       "seller.name AS ownerName, " +
		               "i.minBid AS minBid, " +
                       "i.firstBid AS firstBid, " +
		               "bidder1.id as firstBidUserId, " +
		               "bidder1.name AS firstBidUserName, " +
                       "i.secondBid AS secondBid, " +
		               "bidder2.id AS secondBidUserId, " +
		               "bidder2.name AS secondBidUserName " +
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
						     rs.getInt("ownerId"),
						     rs.getString("ownerName"),
						     rs.getInt("minBid"),
		    				 rs.getInt("firstBid"),
		    				 rs.getInt("firstBidUserId"),
		    				 rs.getString("firstBidUserName"),
		    				 rs.getInt("secondBid"),
		    				 rs.getInt("secondBidUserId"),
		    				 rs.getString("secondBidUserName"));
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
                       "seller.id AS ownerId, " +
                       "seller.name AS ownerName, " +
		               "i.minBid AS minBid, " +
                       "i.firstBid AS firstBid, " +
		               "bidder1.id as firstBidUserId, " +
		               "bidder1.name AS firstBidUserName, " +
                       "i.secondBid AS secondBid, " +
		               "bidder2.id AS secondBidUserId, " +
		               "bidder2.name AS secondBidUserName " +
 	        	       "FROM item i " + 
 	         	       "LEFT OUTER JOIN locations sellLoc ON (i.startlocation = sellLoc.id) " +
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
    						   rs.getInt("ownerId"),
    						   rs.getString("ownerName"),
    						   rs.getInt("minBid"),
    	    			       rs.getInt("firstBid"),
    	    				   rs.getInt("firstBidUserId"),
    	    				   rs.getString("firstBidUserName"),
    	    				   rs.getInt("secondBid"),
    	    				   rs.getInt("secondBidUserId"),
    	    				   rs.getString("secondBidUserName")));
    	}
    	rs.close();
    	
    	return items;
	}
	
	public User getUserById(int id) throws Exception {
		
		String defaultPayPalAcct = "buyer_1349884489_per@parafox.net";
		
		//set up query
		String query = "SELECT u.id AS id, " +
					   "u.name AS name, " +
					   "u.password AS password, " +
					   "d.id AS detailsID, " +
					   "d.birthday AS birthdate, " +
					   "d.address AS address, " +
					   "d.rating AS rating, " +
					   "d.ratingcount AS ratingCount, " +
					   "d.paypalAcct AS payPalAcct, " +
					   "d.email AS email, " +
					   "d.about AS about " +
					   "FROM user u " +
					   "INNER JOIN details d ON (u.userdetails = d.id) " +
					   "WHERE u.id = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		statement.setQueryTimeout(timeout);
		ResultSet rs = statement.executeQuery();
				
		//Create User from result
		User user = new User(rs.getInt("id"),
							 rs.getString("name"),
							 rs.getString("password"),
							 rs.getInt("detailsId"),
							 df.parse(rs.getString("birthdate")),
							 rs.getString("address"),
							 rs.getInt("rating"),
							 rs.getInt("ratingCount"),
							 defaultPayPalAcct,
							 rs.getString("email"),
							 rs.getString("about"));
		rs.close();
		
		return user;
	}
	
	public void updateItemBids(int id, int firstBid, int firstBidUserId,
							   int secondBid, int secondBidUserId) throws Exception {
		
		//Construct update
		String update = "UPDATE item " +
						"SET firstBid = ?, " +
						"firstBidUser = ?, " +
						"secondBid = ?, " +
						"secondBidUser = ? " +
						"WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(update);
		statement.setInt(1, firstBid);
		statement.setInt(2, firstBidUserId);
		statement.setInt(3, secondBid);
		statement.setInt(4, secondBidUserId);
		statement.setInt(5, id);
		statement.setQueryTimeout(timeout);
		
		//execute update
		statement.executeUpdate();
	}
	
	public void updateItemBids(int id, int secondBid, int secondBidUserId) throws Exception {

		//Construct update
		String update = "UPDATE item " +
				"SET secondBid = ?, " +
				"secondBidUser = ? " +
				"WHERE id = ? ";
		PreparedStatement statement = connection.prepareStatement(update);
		statement.setInt(1, secondBid);
		statement.setInt(2, secondBidUserId);
		statement.setInt(3, id);
		statement.setQueryTimeout(timeout);
		
		//execute update
		statement.executeUpdate();
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
		statement.setInt(9, item.getFirstBidUserId());
		statement.setInt(10, item.getSecondBid());
		statement.setInt(11, item.getSecondBidUserId());
		statement.setInt(12, item.getId());
		statement.setQueryTimeout(timeout);
		
		//execute update
		statement.executeUpdate();
	}
	
	public boolean checkLogin(String username, String password) throws Exception {
		boolean valid = false;
			
			String query = "SELECT COUNT(id) AS count " +
					"FROM user u " +
					"WHERE u.name = ? " +
					"AND u.password = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, username);
		statement.setString(2, password);
		statement.setQueryTimeout(timeout);
		ResultSet rs = statement.executeQuery();
		    	
		//add all results to items
		rs.next();
		if(rs.getString("count").equals("1")){
			valid = true;
		}
		rs.close();
		
		return valid;
	}

}

package Database;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import main.Hash;


public class Database {
	
	//db connection
	private Connection connection = null;
	
	//Constants
	private final static int timeout = 30;
	private final static String dbPath = "D:/auction";
	//private final static String dbPath = "/Users/mac/Documents/workspace/4920-Project/WebContent/database/newAuction";
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
		               "bidder2.name AS secondBidUserName, " +
		               "i.finished AS finished " +
					   "FROM item i " + 
					   "LEFT OUTER JOIN locations sellLoc ON (i.startlocation = sellLoc.id)" +
					   "LEFT OUTER JOIN locations shipLoc ON (i.shipsto = shipLoc.id) " +
					   "LEFT OUTER JOIN user seller ON (i.owner = seller.id) " +
					   "LEFT OUTER JOIN user bidder1 ON (i.firstBidUser = bidder1.id) " +
					   "LEFT OUTER JOIN user bidder2 ON (i.secondBidUser = bidder2.id) " +
					   "WHERE (i.description LIKE ?) AND (i.finished = 0)";
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
    					rs.getString("secondBidUserName"),
    					rs.getBoolean("finished")));
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
		               "bidder2.name AS secondBidUserName, " +
		               "i.finished AS finished " +
		    		   "FROM item i " + 
				       "LEFT OUTER JOIN locations sellLoc ON (i.startlocation = sellLoc.id)" +
				       "LEFT OUTER JOIN locations shipLoc ON (i.shipsto = shipLoc.id) " +
				       "LEFT OUTER JOIN user seller ON (i.owner = seller.id) " +
				       "LEFT OUTER JOIN user bidder1 ON (i.firstBidUser = bidder1.id) " +
				       "LEFT OUTER JOIN user bidder2 ON (i.secondBidUser = bidder2.id) " +
					   "WHERE (i.description like ?) AND (i.finished = 0) " +
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
   					   rs.getString("secondBidUserName"),
   					   rs.getBoolean("finished")));
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
		               "bidder2.name AS secondBidUserName, " +
		               "i.finished AS finished " +
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
		    				 rs.getString("secondBidUserName"),
		    				 rs.getBoolean("finished"));
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
		               "bidder2.name AS secondBidUserName, " +
		               "i.finished AS finished " +
 	        	       "FROM item i " + 
 	         	       "LEFT OUTER JOIN locations sellLoc ON (i.startlocation = sellLoc.id) " +
	    	           "LEFT OUTER JOIN locations shipLoc ON (i.shipsto = shipLoc.id) " +
		               "LEFT OUTER JOIN user seller ON (i.owner = seller.id) " +
		               "LEFT OUTER JOIN user bidder1 ON (i.firstBidUser = bidder1.id) " +
		               "LEFT OUTER JOIN user bidder2 ON (i.secondBidUser = bidder2.id) " +
			           "WHERE (i.category = ?) AND (i.finished = 0)";
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
    	    				   rs.getString("secondBidUserName"),
    	    				   rs.getBoolean("finished")));
    	}
    	rs.close();
    	
    	return items;
	}
	
	public List<Item> getItemsBySeller(int userId) throws Exception {
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
		               "bidder2.name AS secondBidUserName, " +
		               "i.finished AS finished " +
 	        	       "FROM item i " + 
 	         	       "LEFT OUTER JOIN locations sellLoc ON (i.startlocation = sellLoc.id) " +
	    	           "LEFT OUTER JOIN locations shipLoc ON (i.shipsto = shipLoc.id) " +
		               "LEFT OUTER JOIN user seller ON (i.owner = seller.id) " +
		               "LEFT OUTER JOIN user bidder1 ON (i.firstBidUser = bidder1.id) " +
		               "LEFT OUTER JOIN user bidder2 ON (i.secondBidUser = bidder2.id) " +
			           "WHERE (i.owner = ?) AND (i.finished = 0)";
		PreparedStatement statement = connection.prepareStatement(query);
	  	statement.setInt(1, userId);
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
    	    				   rs.getString("secondBidUserName"),
    	    				   rs.getBoolean("finished")));
    	}
    	rs.close();
    	
    	return items;
	}
	
	public List<Item> getItemsByWinner(int userId) throws Exception {
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
		               "bidder2.name AS secondBidUserName, " +
		               "i.finished AS finished " +
 	        	       "FROM item i " + 
 	         	       "LEFT OUTER JOIN locations sellLoc ON (i.startlocation = sellLoc.id) " +
	    	           "LEFT OUTER JOIN locations shipLoc ON (i.shipsto = shipLoc.id) " +
		               "LEFT OUTER JOIN user seller ON (i.owner = seller.id) " +
		               "LEFT OUTER JOIN user bidder1 ON (i.firstBidUser = bidder1.id) " +
		               "LEFT OUTER JOIN user bidder2 ON (i.secondBidUser = bidder2.id) " +
			           "WHERE (i.firstBidUser = ?) AND (i.finished = 1)";
		PreparedStatement statement = connection.prepareStatement(query);
	  	statement.setInt(1, userId);
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
    	    				   rs.getString("secondBidUserName"),
    	    				   rs.getBoolean("finished")));
    	}
    	rs.close();
    	
    	return items;
	}
	
	public User getUserByName(String name) throws Exception {
		
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
					   "WHERE u.name = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, name);
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
							 rs.getString("payPalAcct"),
							 rs.getString("email"),
							 rs.getString("about"));
		rs.close();
		
		return user;
	}
	
	public User getUserById(int id) throws Exception {
		
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
							 rs.getString("payPalAcct"),
							 rs.getString("email"),
							 rs.getString("about"));
		rs.close();
		
		return user;
	}
	
	public int getLocationIndex(String location) throws Exception {
		
		int index = 0;
		
		//Construct query
		String query = "SELECT id " +
					   "FROM locations " +
				       "WHERE name = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, location);
		statement.setQueryTimeout(timeout);
		ResultSet rs = statement.executeQuery();
		
		while (rs.next()) {
			index = rs.getInt("id");
		}
		rs.close();
		
		return index;
	}
	
	public List<String> getLocations() throws Exception {
		
		List<String> locations = new ArrayList<String>();
		
		//Construct query
		String query = "SELECT name " +
					   "FROM locations";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setQueryTimeout(timeout);
		ResultSet rs = statement.executeQuery();
		
		//put locations into list
		while (rs.next()) {
			locations.add(rs.getString("name"));
		}
		rs.close();
		
		return locations;
	}
	
	public List<String> getCategories() throws Exception {
		
		Set<String> categories = new HashSet<String>();
		
		//Construct query
		String query = "SELECT category " +
					   "FROM item";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setQueryTimeout(timeout);
		ResultSet rs = statement.executeQuery();
		
		//put locations into list
		while (rs.next()) {
			categories.add(rs.getString("category"));
		}
		rs.close();
		
		return Arrays.asList(categories.toArray(new String[0]));
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
		statement.setString(7, item.getOwnerName());
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
	
	public boolean isUser(String username) throws Exception {
		boolean valid = false;
			
			String query = "SELECT COUNT(id) AS count " +
					"FROM user u " +
					"WHERE u.name = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, username);
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

	public int countUsers() throws Exception {
		int count = 0;
		
		String query = "SELECT id FROM user";
		PreparedStatement statement = connection.prepareStatement(query);
		
		statement.setQueryTimeout(timeout);
		ResultSet rs = statement.executeQuery();
		
		while (rs.next()){
			count++;
		}
		rs.close();
		
		return count;
	}

	public void createNewUser(int count, String username, String password,
			String payPal, String email, String birthday, String address,
			String about) throws Exception {
		
		//Hash the password first
		Hash hs = new Hash();
		String hsEnter = hs.calculate(password);
		
		Statement statement = connection.createStatement();
		statement.executeUpdate("INSERT INTO user VALUES("+ count + ",\"" + username + "\",\"" + hsEnter +"\"," + count +  ")");
		statement.executeUpdate("INSERT INTO details VALUES("+ count+ ",\"" + birthday + "\",\"" + address +"\"," + 0 + "," + 0 + ",\"" + payPal + "\",\"" + email +"\",\"" + about + "\")"); 
		
		//INSERT INTO "user" VALUES(1,"Jay Gurnani", "abc123", null);
		//INSERT INTO "details" VALUES (1,  "1991-01-01", "101 George St, Sydney, NSW, 2000", 5, 100, "jay_1349952995_biz@parafox.net",    "jay.gurnani@gmail.com",    "I love Fire&Ice!");

		
	}
	
	public int createNewAuction(String name, int minBid, int startLocation, 
			 int shipsTo, String category, Date startDate, 
			 Date endDate, int owner) throws Exception {

		//Construct insert
		String insert = "INSERT INTO item " +
			"(description, startLocation, shipsTo, category, " +
			"startDate, endDate, owner, minBid, finished, " +
			"firstBid, firstBidUser, secondBid, secondBidUser) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(insert);
		statement.setString(1, name);
		statement.setInt(2, startLocation);
		statement.setInt(3, shipsTo);
		statement.setString(4, category);
		statement.setString(5, df.format(startDate));
		statement.setString(6, df.format(endDate));
		statement.setInt(7, owner);
		statement.setInt(8, minBid);
		statement.setBoolean(9, false);
		statement.setNull(10, java.sql.Types.INTEGER);
		statement.setNull(11, java.sql.Types.VARCHAR);
		statement.setNull(12, java.sql.Types.INTEGER);
		statement.setNull(13, java.sql.Types.VARCHAR);
		statement.setQueryTimeout(timeout);
		
		//execute insert
		statement.executeUpdate();
		
		//get id of inserted item
		Statement st = connection.createStatement();
		st.setQueryTimeout(timeout);
		ResultSet rs = st.executeQuery("SELECT last_insert_rowid() FROM item");
		
		return rs.getInt("last_insert_rowid()");
	}
	
	public void deleteItemById(int id) throws Exception {
		
		//Construct delete statement
		String delete = "DELETE FROM item " +
						"WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(delete);
		statement.setInt(1, id);
		statement.setQueryTimeout(timeout);
		statement.executeUpdate();
	}
	
	public Queue<TreeMap<String,Object> > endedItems() throws Exception 
	{
		Queue<TreeMap<String,Object> > items = new LinkedList<TreeMap<String,Object> >();

		String q = "select * from item where id == 1";
		PreparedStatement qstatement = connection.prepareStatement(q);
		qstatement.setQueryTimeout(30);
    	ResultSet qrs = qstatement.executeQuery();
		qrs.close();
    	
    	
		String query = 
				"SELECT item.id             AS id, " +
				"       item.description    AS description, " +
				"       item.enddate        AS endDate, " +
				"       item.owner          AS owner, " +
				"       sellerDetails.email AS ownerEmail, " +
				"       item.firstbiduser   AS winner, " +
				"       winnerDetails.email AS winnerEmail, " +
				"       item.firstbid       AS firstBid, " +
				"       item.secondbid      AS secondBid " +
				"FROM   item " +
				"       LEFT OUTER JOIN details sellerDetails ON ( item.owner = sellerDetails.id ) " +
				"       LEFT OUTER JOIN details winnerDetails ON ( item.firstbiduser = winnerDetails.id ) " +
				"WHERE  Date(item.enddate) < Date('now') " +
				"       AND finished==\"false\";";
				
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setQueryTimeout(30);
    	ResultSet rs = statement.executeQuery();
    	while (rs.next()) 
    	{
    		TreeMap<String,Object> map = new TreeMap<String,Object>();
    		map.put("id", rs.getInt("id"));
    		map.put("description", rs.getString("description"));
    		map.put("endDate", "" + df.parse(rs.getString("endDate")));
    		map.put("owner", rs.getInt("owner"));
    		map.put("ownerEmail", rs.getString("ownerEmail"));
    		map.put("winner", rs.getInt("winner"));
    		map.put("winnerEmail", rs.getString("winnerEmail"));
    		map.put("firstbid", rs.getInt("firstbid"));
    		map.put("secondbid", rs.getInt("secondbid"));   
    		items.add(map);
    	}
    	
    	rs.close();
    	return items;
	}
	
	public void closeAuction(int id) throws SQLException
	{
		String query = "UPDATE item set finished=\"true\" where id==" + id + ";";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setQueryTimeout(30);
		statement.executeUpdate();
	}
}

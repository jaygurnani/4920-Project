package main;

import java.io.File;

import java.util.Queue;
import java.util.TreeMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import Database.Database;

import main.MailerDaemon.Email;

/**
 * Bid daemon. 
 * Checks the database periodically for ended auction that need attention and closes them appropriately.
 */
public class BidDaemon extends Thread
{
	/**
	 * Host url. TODO FIX THIS
	 */
	private static String HOST = "http://localhost:8080/4920_Project/finished?id=";
			//"http://localhost:8080/4920_Project/displayFinished.jsp?id=";
	
	/**
	 * Logger for class
	 */
	private static final 	Logger 			logger 			= Logger.getLogger(BidDaemon.class.getName());
	//sets up the logger
	static
	{
		try 
		{
			logger.setLevel(Level.INFO);  
			File file = new File(BidDaemon.class.getName() + ".log");
			file.createNewFile();
			FileHandler handler = new FileHandler(file.getAbsolutePath(),true);
			
            SimpleFormatter formatter = new SimpleFormatter();  
            handler.setFormatter(formatter);  
            
			logger.addHandler(handler);
			System.out.println(file.getAbsolutePath());
		} 
		catch (Exception e)
		{
			System.out.println("Failed to create log.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Rate at which to check the database for new old auctions in miliseconds.
	 */
	private static long pollRate = 1000;
	
	/**
	 * Database reference.
	 */
	private static Database db;
	//sets up the database.
	static
	{ 
		try 
		{
			db = new Database();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IllegalStateException("Failed to open database.");
		}
	}
	
	/**
	 * Reference to the daemon Thread. Initialised when the makeDaemon is called.
	 */
	private static 			Thread			daemon			= null;
	
	/**
	 * Daemon loop for bid checking. Pulls auction that have ended but not been handled from the database
	 * and sends the appropriate email's.
	 */
	private static void daemonLoop()
	{
		logger.entering(MailerDaemon.class.getName(), "daemonLoop");
		while(true)
		{
			try 
			{
				logger.log(Level.FINE, "Getting auctions that require handling.");
				Queue<TreeMap<String, Object>> queue = db.endedItems();
				if(queue.size() != 0)
				{
					logger.log(Level.INFO, queue.size() + " auctions needing attention.");
					for(TreeMap<String, Object> auction : queue)
					{
						logger.log(Level.INFO, "Auction id: " + auction.get("id") + ".");
						logger.log(Level.INFO, "End Date: " + auction.get("endDate") + ".");
						//if no one bid, alert seller
						if((Integer) auction.get("winner") == 0)
						{
							logger.log(Level.INFO, "No one bid. Alterting seller.");
							String content = "Sorry but nobody bidded on your item: " +
									"\n\nID: " + auction.get("id") +
									  "\nEnd Time: " + auction.get("endDate") +
									  "\nDescription: " + auction.get("description");
							Email email = new Email((String)auction.get("ownerEmail"), "Auction: " + auction.get("id"), content);
							while(!MailerDaemon.addMail(email));
						} 
						//otherwise work out winning bid and send
						else
						{
							Integer bid = (Integer) auction.get("firstbid");
							if((Integer) auction.get("secondbid") != 0)
								bid = (Integer) auction.get("secondbid");
							logger.log(Level.INFO, "Winning bid: " + bid + ".");
							logger.log(Level.INFO, "Winner: " + auction.get("winner") + ".");
							
							//email for seller
							logger.log(Level.INFO, "Alerting seller.");
							String content = "Congratulations, your auction has ended in a sale: " +
									"\n\nID: " + auction.get("id") +
									  "\nEnd Time: " + auction.get("endDate") + 
									  "\nDescription: " + auction.get("description") +
									  "\nWinning Bid: $" + bid + 
									  "\nWinner ID: " + auction.get("winner") +
									  "\nEmail: " + auction.get("winnerEmail")
									  ;
							Email email = new Email((String)auction.get("ownerEmail"), "Auction: " + auction.get("id"), content);
							while(!MailerDaemon.addMail(email));
							
							//email for bidder
							logger.log(Level.INFO, "Alerting winner.");
							content = "Congratulations, you have won an auction: " +
									"\n\nID: " + auction.get("id") +
									  "\nEnd Time: " + auction.get("endDate") +
									  "\nDescription: " + auction.get("description") +
									  "\nWinning Bid: $" + bid + 
									  "\nSeller ID: " + auction.get("owner") +
									  "\nEmail: " + auction.get("winnerEmail") +
									  "\n\nTo pay and leave feedback please go to the following address: " + HOST + auction.get("id")
									  ;
							email = new Email((String)auction.get("winnerEmail"), "Auction: " + auction.get("id"), content);
							while(!MailerDaemon.addMail(email));
						}
						//close the auction.
						logger.log(Level.INFO, "Closing auction.");
						db.closeAuction((Integer)auction.get("id"));
					}
				}
				else
					Thread.sleep(pollRate);
			} 
			catch (Exception e) 
			{
				logger.log(Level.SEVERE, "And error occured: ", e);
			}
		}
	}
	
	/**
	 * Start the daemon. Use this instead of start() or run().
	 * Multiple calls when the daemon is alive will simply return the
	 * same thread and update the poll rate.
	 * @param pollRate
	 * The period of time that the daemon will wait between each poll.
	 * @return
	 * The daemon thread.
	 */
	public static Thread makeDaemon(long pollRate)
	{
		BidDaemon.pollRate = pollRate;
		if(daemon != null && daemon.isAlive())
			return daemon;
		
		logger.info("Starting Daemon");
		BidDaemon m = new BidDaemon();
		m.setDaemon(true);
		m.setName("Mailer Daemon");
		m.start();
		BidDaemon.daemon = m;
		return m;
	}
	
	@Override
	/**
	 * Do not call. Use makeDaemon().
	 */
	public void run() 
	{
		daemonLoop();
	}
}

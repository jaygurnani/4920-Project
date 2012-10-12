package main;

/**
 * Starts daemon services.
 */
public class DaemonStarter 
{
	public static void main(String [] args) throws Exception
	{
		//single line format for loggers
		System.getProperties().put("java.util.logging.SimpleFormatter.format", "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n");
		
		//start mail daemon
		MailerDaemon.makeDaemon(1000);
		//start bid daemon
		BidDaemon.makeDaemon(1000);
		
		//sleep until forcibly ended.
		while(true)
			Thread.sleep(Long.MAX_VALUE);
	}
}
package main;

import java.io.File;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.mail.*;
import javax.mail.internet.*;

/**
 * A deamon process that sends emails placed into its queue.
 * Note that this is a daemon thread which will close when all other threads end.
 * This means any emails in the queue will lost. TODO fix that.
 */
public class MailerDaemon extends Thread
{	
	/**
	 * Gmail host.
	 */
	private static final	String 			host 			= "smtp.gmail.com";
	
	/**
	 * Our gmail account.
	 */
	private static final	String			 from 			= "4920.fire.ice"; 
	
	/**
	 * The password for the account.
	 */
	private static final	String 			password 		= "pwFireIce";
	
	/**
	 * Daemon poll rate.
	 */
	
	private static 			long			pollRate		= 1000;
	/**
	 * Logger for class
	 */
	private static final 	Logger 			logger 			= Logger.getLogger(MailerDaemon.class.getName());
	//sets up the logger
	static
	{
		try 
		{
			logger.setLevel(Level.INFO);  
			File file = new File(MailerDaemon.class.getName() + ".log");
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
	 * Holds system properties.
	 */
	private static final	Properties 		properties		;
	//Set up the properties.
	static
	{
		properties = System.getProperties();
		properties.setProperty("smtp.gmail.com", host);
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.user", from);
		properties.put("mail.smtp.password", "pwFireIce");
		properties.put("mail.smtp.port", "587"); 
		properties.put("mail.smtp.auth", "true");
	}
	
	/**
	 * Session for emails.
	 */
	private static final	Session 		session 		= Session.getDefaultInstance(properties);

	
	/**
	 * Queue of emails to be sent. Daemon pops them off as they come in.
	 */
	private static final	Queue<Email>	toSend			= new LinkedList<Email>();
	
	/**
	 * Semaphore to modify queue.
	 */
	private static final	Semaphore		queueSem		= new Semaphore(1);
	
	/**
	 * Reference to the daemon Thread. Initialised when the makeDaemon is called.
	 */
	private static 			Thread			daemon			= null;
	
	/**
	 * 
	 * Adds an email to the queue.
	 * @param email
	 * The email to add.
	 * @return
	 * True if the email was successfully added. False otherwise.
	 */
	public static boolean addMail(Email email)
	{
		logger.entering(MailerDaemon.class.getName(), "addMail");
		boolean ret = true;
		try
		{
			logger.log(Level.FINE, "Taking semaphore.");
			queueSem.acquire();
			logger.log(Level.FINE, "Acquired semaphore.");
			logger.log(Level.FINE, "Adding email :\"" + email.subject + "\"");
			toSend.add(email);
			logger.log(Level.FINE, "Releasing semaphore.");
			queueSem.release();
		}
		catch(Exception e)
		{
			logger.log(Level.WARNING, "Failed to add email.", e);
			ret = false;
		}
		logger.exiting(MailerDaemon.class.getName(), "addMail", ret);
		return ret;
	}
	
	/**
	 * Daemon loop that pops off emails from the queue and sends them.
	 */
	private static void daemonLoop()
	{
		logger.entering(MailerDaemon.class.getName(), "daemonLoop");
		while(true)
		{
			try
			{
				if(toSend.size() != 0)
				{
					logger.log(Level.FINE, "Taking semaphore.");
					boolean failed = false;
					try 
					{
						queueSem.acquire();
					} 
					catch (InterruptedException e) 
					{
						logger.log(Level.WARNING, "Failed to take semaphore.", e);
						failed = true;
					}
					if(!failed)
					{
						Email email = toSend.poll();
						logger.log(Level.INFO, "Got email");
						logger.log(Level.INFO, "TO:      [" + email.to + "]");
						logger.log(Level.INFO, "SUBJECT: [" + email.subject + "]");
						
						logger.log(Level.FINE, "Releasing semaphore.");
						queueSem.release();
						
						logger.log(Level.INFO, "Compiling email.");
						MimeMessage m = null;
						try 
						{
							m = email.compile();
							logger.log(Level.INFO, "Successfully compiled email.");
						} 
						catch (Exception e) 
						{
							failed = true;
							logger.log(Level.WARNING, "Failed to compile email.", e);
							logger.log(Level.WARNING, "Adding at the end of the queue.");
							int count = 0;
							while(!addMail(email) && count < 5)
							{
								count++;
								if(count == 5)
									logger.log(Level.WARNING, "Failed to add email to queue.");
							}
						} 
						if(!failed)
						{
							int count = 0;
							do
							{
								try 
								{
									logger.log(Level.INFO, "Attempting to send email.");
									Transport transport = session.getTransport("smtps");
									transport.connect(host, from, password);
									transport.sendMessage(m, m.getAllRecipients());
									transport.close();
									logger.log(Level.INFO, "Email sent.");
									failed = false;
								} 
								catch (Exception e) 
								{
									failed = true;
									count++;
									logger.log(Level.WARNING, "Failed to send email.", e);
								} 
							}while(failed && count < 5);
						}
					}
				}
				//sleep if no more emails
				else
					Thread.sleep(pollRate);
			}
			catch(Exception e)
			{
				logger.log(Level.SEVERE, "Unhandeled Exception.", e);
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
		MailerDaemon.pollRate = pollRate;
		if(daemon != null && daemon.isAlive())
			return daemon;
		
		logger.info("Starting Daemon");
		MailerDaemon m = new MailerDaemon();
		m.setDaemon(true);
		m.setName("Mailer Daemon");
		m.start();
		MailerDaemon.daemon = m;
		return m;
	}
	

	@Override
	public void run() 
	{
		daemonLoop();
	}
	
	/**
	 * Represents simple emails.
	 */
	public static class Email
	{
		/**
		 * Email of recipient.
		 */
		private String 		to;
		
		/**
		 * Message.
		 */
		private String 		message;
		
		/**
		 * Subject of email.
		 */
		private String 		subject;
		
		/**
		 * Set up an email.
		 * 
		 * @param to
		 * Email of recipient.
		 * @param from
		 * Email of sender.
		 * @param subject
		 * Subject of email.
		 * @param message
		 * Message.
		 */
		public Email(String to, String subject, String message)
		{
			this.to = to;
			this.subject = subject;
			this.message = message;
		}
		
		/**
		 * Compiles the email contents into a MimeMessage.
		 * @return
		 * A MimeMessage containing the contents of the email.
		 * @throws AddressException
		 * When the email contains a bad address.
		 * @throws MessagingException
		 * When the email contains a bad element.
		 */
		private MimeMessage compile() throws AddressException, MessagingException
		{
			Session session = Session.getDefaultInstance(properties);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(this.message);
			return message;
		}
	}
}
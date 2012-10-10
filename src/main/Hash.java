package main;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	private  String salt = "default hash";
	private  int hashIterations = 100;
	
	
	public Hash(int hashIterations, String salt){
		this.hashIterations = hashIterations;
		this.salt = salt;
	}
	
	public Hash(int hashIterations){
		this.hashIterations = hashIterations;
	}
	
	public Hash(String salt){
		this.salt = salt;
	}
	
	public Hash(){
	}
	
	
	public String calculate(String in) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		String hash = "";
		
		byte[] saltB = salt.getBytes("utf8");
				
		MessageDigest cript = MessageDigest.getInstance("SHA-1");
        cript.reset();
        cript.update(saltB);
        //hash = new String(Hex.encodeHex(cript.digest()), CharSet.forName("UTF-8"));
        byte[] digest = cript.digest(in.getBytes("utf8"));
        
        for(int i = 0; i < hashIterations; i++){
        	cript.reset();
            cript.update(saltB);
            digest = cript.digest(digest);
        }
        for(int i = 0; i < digest.length; i++){
        	hash += (String.format("%02X", digest[i]) );
        }
        
		return hash;		
	}
}

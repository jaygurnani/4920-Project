package Database;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable  {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String password;
	private int detailsId;
	private Date birthDate;
	private String address;
	private int rating;
	private int ratingCount;
	private String payPalAcct;
	private String email;
	private String about;
	
	public User(int id, String name, String password, int detailsId, 
			    Date birthDate, String address, int rating, int ratingCount,
			    String payPalAcct, String email, String about) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.detailsId = detailsId;
		this.birthDate = birthDate;
		this.address = address;
		this.rating = rating;
		this.ratingCount = ratingCount;
		this.payPalAcct = payPalAcct;
		this.email = email;
		this.about = about;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDetailsId() {
		return detailsId;
	}

	public void setDetailsId(int detailsId) {
		this.detailsId = detailsId;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}

	public String getPayPalAcct() {
		return payPalAcct;
	}

	public void setPayPalAcct(String payPalAcct) {
		this.payPalAcct = payPalAcct;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}
	
}

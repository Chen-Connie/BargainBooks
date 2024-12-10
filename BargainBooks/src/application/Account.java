package application;

public class Account {
	private int asuID;	
	private String password;
	private boolean adminAccess;
	private boolean blockedStatus;
	private String username;
	private String emailAddress;
	
	
	Account()
	{
		asuID = -1;
		password = "null";
		adminAccess = false;
		blockedStatus = false;
		username = "null";
		emailAddress = "null";
	}
	
	Account(int asuID, String password, boolean adminAccess, boolean blockedStatus, String username, String emailAddress)
	{
		this.asuID = asuID;
		this.password = password;
		this.adminAccess = adminAccess;
		this.blockedStatus = blockedStatus;
		this.username = username;
		this.emailAddress = emailAddress;
	}
	
	//Setters
	public void setAsuID(int asuID) {this.asuID = asuID;}
	public void setPassword(String password) {this.password = password;}
	public void setAdminAccess(boolean adminAccess) {this.adminAccess = adminAccess;}
	public void setBlockedStatus(boolean blockedStatus) {this.blockedStatus = blockedStatus;}
	public void setUsername(String username) {this.username = username;}
	public void setEmailAddress(String emailAddress) {this.emailAddress = emailAddress;}
	
	//Getters
	public int getAsuID() {return asuID;}
	public String getPassword() {return password;}
	public boolean getAdminAccess() {return adminAccess;}
	public boolean getBlockedStatus() {return blockedStatus;}
	public String getUsername() {return username;}
	public String getEmailAddress() {return emailAddress;}
	
	//Testing function
	public void printAccount()
	{
		System.out.println("ASU ID   : " + asuID);
		System.out.println("Password : " + password);
		System.out.println("Admin?   : " + adminAccess);
		System.out.println("Blocked? : " + blockedStatus);
		System.out.println("Username : " + username);
		System.out.println("Email    : " + emailAddress);
	}
	

}








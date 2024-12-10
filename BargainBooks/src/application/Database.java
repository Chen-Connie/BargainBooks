package application;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Database {
	//Important to set this for each person's system. This should be the path to the folder titled "BargainBooks"
	private static String projectFilePath = "C:/Users/brend/OneDrive/Documents/GitHub/CSE360-GROUP10/BarginBooks";
	
	private static String accountDBPath = "resource/accountDB/";
	private static String availableForSaleDBPath = "resource/availableForSaleDB/";
	private static String systemConfigDBPath = "resource/systemConfigDB/";
	private static String transactionDBPath = "resource/transactionDB/";
	
	//add : methods add the file if it does not exist or overwrites files that already exist under the same name
	//get : checks *IF* the file exists, then returns it (returns false if does not exist)
	//delete : methods simply remove that file if it exists
	
	
	//Useful Helper methods
	private static boolean fileExists(String filePath)
	{
		boolean checkExists = new File(filePath).isFile();
		
		if(checkExists) {return true;}
		return false;
	}
	
	private static ArrayList<String> folderRead(String path)
	{
		File folder = new File(path);
		File[] listWithinFolder = folder.listFiles();
		ArrayList<String> returnThis = new ArrayList<String>();
		
		for(File element : listWithinFolder)
		{
			if(element.isFile())
			{
				returnThis.add(element.getName().replace(".txt", ""));
			}
		}
		
		return returnThis;
	}
	
	
	
	
	/*  [Transaction DB] ------------------------------------------------------------------------------------------------
	 * B : String 	: title
	 * B : String 	: author
	 * B : String 	: subject
	 * B : int 		: year
	 * B : String 	: condition 
	 * B : double 	: original price
	 * T : int 		: sellerID
	 * T : int 		: buyerID
	 * T : double	: sellerPrice
	 * T : double	: buyerPrice
	 */
	
	public static int generateTransactionID()
	{
		String infoPath = projectFilePath + transactionDBPath + "info.txt";
		int transactionCount;
		
		try
		{
			BufferedReader fileRead = new BufferedReader(new FileReader(infoPath));
			transactionCount = Integer.parseInt(fileRead.readLine().substring(20));
			fileRead.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return -1;
		}
		
		transactionCount++;
		
		try
		{
			BufferedWriter fileWrite = new BufferedWriter(new FileWriter(infoPath));
			fileWrite.write("Transaction Count = " + transactionCount);
			fileWrite.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return -1;
		}
		
		return transactionCount;
	}
	
	public static boolean addToTransactionDB(Transaction storeThis)
	{
		String filePath = projectFilePath + transactionDBPath + storeThis.getTransactionID() + ".txt";
		
		try
		{
			BufferedWriter fileWrite = new BufferedWriter(new FileWriter(filePath));
			fileWrite.write(storeThis.getItem().getTitle() + "\n");
			fileWrite.write(storeThis.getItem().getAuthor()+ "\n");
			fileWrite.write(storeThis.getItem().getSubject()+ "\n");
			fileWrite.write(storeThis.getItem().getYear()+ "\n");
			fileWrite.write(storeThis.getItem().getCondition()+ "\n");
			fileWrite.write(storeThis.getItem().getOriginalPrice()+ "\n"); //converted to string, cant store a double
			fileWrite.write(storeThis.getSellerID()+ "\n");
			fileWrite.write(storeThis.getBuyerID()+ "\n");
			fileWrite.write(storeThis.getSellerPrice() + "\n"); //converted to string, cant store a double
			fileWrite.write(storeThis.getBuyerPrice() + ""); //converted to string, cant store a double
			
			fileWrite.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static Transaction getFromTransactionDB(int transactionID)
	{
		String filePath = projectFilePath + transactionDBPath + transactionID + ".txt";
		if(!fileExists(filePath)) {return null;}
		
		Book tempBook = new Book();
		Transaction tempTransaction = new Transaction();
		
		try
		{
			BufferedReader fileRead = new BufferedReader(new FileReader(filePath));
			tempBook.setTitle(fileRead.readLine());
			tempBook.setAuthor(fileRead.readLine());
			tempBook.setSubject(fileRead.readLine());
			tempBook.setYear(Integer.parseInt(fileRead.readLine())); //conversion to integer
			tempBook.setCondition(fileRead.readLine());
			tempBook.setOriginalPrice(Double.parseDouble(fileRead.readLine())); //conversion to double
			
			//add completed book to the newly made transaction object
			tempTransaction.setItem(tempBook);
			tempTransaction.setTransactionID(transactionID);
			tempTransaction.setSellerID(Integer.parseInt(fileRead.readLine())); //conversion to int
			tempTransaction.setBuyerID(Integer.parseInt(fileRead.readLine())); //conversion to int
			tempTransaction.setSellerPrice(Double.parseDouble(fileRead.readLine())); //conversion to double
			tempTransaction.setBuyerPrice(Double.parseDouble(fileRead.readLine())); //conversion to double
			
			fileRead.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		
		return tempTransaction;
	}
	
	
	// [Available for Sale DB (Active Transactions)]----------------------------------------------------------
	public static boolean addToAvailableForSaleDB(Transaction storeThis)
	{
		String filePath = projectFilePath + availableForSaleDBPath + storeThis.getTransactionID() + ".txt";
		
		try
		{
			BufferedWriter fileWrite = new BufferedWriter(new FileWriter(filePath));
			fileWrite.write(storeThis.getItem().getTitle() + "\n");
			fileWrite.write(storeThis.getItem().getAuthor()+ "\n");
			fileWrite.write(storeThis.getItem().getSubject()+ "\n");
			fileWrite.write(storeThis.getItem().getYear()+ "\n");
			fileWrite.write(storeThis.getItem().getCondition()+ "\n");
			fileWrite.write(storeThis.getItem().getOriginalPrice()+ "\n"); //converted to string, cant store a double
			fileWrite.write(storeThis.getSellerID()+ "\n");
			fileWrite.write(storeThis.getBuyerID()+ "\n");
			fileWrite.write(storeThis.getSellerPrice() + "\n"); //converted to string, cant store a double
			fileWrite.write(storeThis.getBuyerPrice() + ""); //converted to string, cant store a double
			
			fileWrite.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static Transaction getFromAvailableForSaleDB(int transactionID)
	{
		String filePath = projectFilePath + availableForSaleDBPath + transactionID + ".txt";
		if(!fileExists(filePath)) {return null;}
		
		Book tempBook = new Book();
		Transaction tempTransaction = new Transaction();
		
		try
		{
			BufferedReader fileRead = new BufferedReader(new FileReader(filePath));
			tempBook.setTitle(fileRead.readLine());
			tempBook.setAuthor(fileRead.readLine());
			tempBook.setSubject(fileRead.readLine());
			tempBook.setYear(Integer.parseInt(fileRead.readLine())); //conversion to integer
			tempBook.setCondition(fileRead.readLine());
			tempBook.setOriginalPrice(Double.parseDouble(fileRead.readLine())); //conversion to double
			
			//add completed book to the newly made transaction object
			tempTransaction.setItem(tempBook);
			tempTransaction.setTransactionID(transactionID);
			tempTransaction.setSellerID(Integer.parseInt(fileRead.readLine())); //conversion to int
			tempTransaction.setBuyerID(Integer.parseInt(fileRead.readLine())); //conversion to int
			tempTransaction.setSellerPrice(Double.parseDouble(fileRead.readLine())); //conversion to double
			tempTransaction.setBuyerPrice(Double.parseDouble(fileRead.readLine())); //conversion to double
			
			fileRead.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		
		return tempTransaction;
	}
	
	public static boolean removeFromAvailableForSaleDB(int transactionID)
	{
		String filePath = projectFilePath + availableForSaleDBPath + transactionID + ".txt";
		if(!fileExists(filePath)) {return false;}
		
		File deleteThis = new File(filePath);
		if(deleteThis.delete())
		{
			return true; //successfully deleted
		}
		else
		{
			return false; //failed to delete
		}
	}
	
	
	
	// [Account Database]
	/* String password
	 * boolean adminAccess
	 * boolean blockedStatus
	 */
	public static boolean addToAccountDB(Account storeThis)
	{
		String filePath = projectFilePath + accountDBPath + storeThis.getAsuID() + ".txt";
		
		try
		{
			BufferedWriter fileWrite = new BufferedWriter(new FileWriter(filePath));
			fileWrite.write(storeThis.getPassword() + "\n");
			fileWrite.write(storeThis.getAdminAccess() + "\n");
			fileWrite.write(storeThis.getBlockedStatus() + "\n");
			fileWrite.write(storeThis.getUsername() + "\n");
			fileWrite.write(storeThis.getEmailAddress() + "");
			
			fileWrite.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static Account getFromAccountDB(int asuID)
	{
		String filePath = projectFilePath + accountDBPath + asuID + ".txt";
		if(!fileExists(filePath)) {return null;}
		
	
		Account tempAccount = new Account();
		
		try
		{
			BufferedReader fileRead = new BufferedReader(new FileReader(filePath));
			tempAccount.setAsuID(asuID); //already an int
			tempAccount.setPassword(fileRead.readLine());
			tempAccount.setAdminAccess(Boolean.parseBoolean(fileRead.readLine())); //back to bool
			tempAccount.setBlockedStatus(Boolean.parseBoolean(fileRead.readLine())); //back to bool
			tempAccount.setUsername(fileRead.readLine());
			tempAccount.setEmailAddress(fileRead.readLine());
			
			fileRead.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		
		return tempAccount;
	}
	
	public static boolean removeFromAccountDB(int asuID)
	{
		String filePath = projectFilePath + accountDBPath + asuID + ".txt";
		if(!fileExists(filePath)) {return false;}
		
		File deleteThis = new File(filePath);
		if(deleteThis.delete())
		{
			return true; //successfully deleted
		}
		else
		{
			return false; //failed to delete
		}
	}
	
	
	// [System Config Database]
	/*	Computer, English, Math, and Natural Science categories for condition multipliers.
	 * 	price_range.txt is for the acceptable book price ranges (min and max for seller page)
	 */	
	public static boolean setComputerMultipliers(double usedLikeNew, double moderatelyUsed, double heavilyUsed)
	{
		String filePath = projectFilePath + systemConfigDBPath + "computer.txt";
		
		try
		{
			BufferedWriter fileWrite = new BufferedWriter(new FileWriter(filePath));
			fileWrite.write("Used Like New = " + usedLikeNew + "\n");
			fileWrite.write("Moderately Used = " + moderatelyUsed + "\n");
			fileWrite.write("Heavily Used = " + heavilyUsed + "\n");
			
			fileWrite.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean setEnglishMultipliers(double usedLikeNew, double moderatelyUsed, double heavilyUsed)
	{
		String filePath = projectFilePath + systemConfigDBPath + "english.txt";
		
		try
		{
			BufferedWriter fileWrite = new BufferedWriter(new FileWriter(filePath));
			fileWrite.write("Used Like New = " + usedLikeNew + "\n");
			fileWrite.write("Moderately Used = " + moderatelyUsed + "\n");
			fileWrite.write("Heavily Used = " + heavilyUsed + "\n");
			
			fileWrite.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean setMathMultipliers(double usedLikeNew, double moderatelyUsed, double heavilyUsed)
	{
		String filePath = projectFilePath + systemConfigDBPath + "math.txt";
		
		try
		{
			BufferedWriter fileWrite = new BufferedWriter(new FileWriter(filePath));
			fileWrite.write("Used Like New = " + usedLikeNew + "\n");
			fileWrite.write("Moderately Used = " + moderatelyUsed + "\n");
			fileWrite.write("Heavily Used = " + heavilyUsed + "\n");
			
			fileWrite.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean setNaturalScienceMultipliers(double usedLikeNew, double moderatelyUsed, double heavilyUsed)
	{
		String filePath = projectFilePath + systemConfigDBPath + "natural_science.txt";
		
		try
		{
			BufferedWriter fileWrite = new BufferedWriter(new FileWriter(filePath));
			fileWrite.write("Used Like New = " + usedLikeNew + "\n");
			fileWrite.write("Moderately Used = " + moderatelyUsed + "\n");
			fileWrite.write("Heavily Used = " + heavilyUsed + "\n");
			
			fileWrite.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean setOtherMultipliers(double usedLikeNew, double moderatelyUsed, double heavilyUsed)
	{
		String filePath = projectFilePath + systemConfigDBPath + "other.txt";
		
		try
		{
			BufferedWriter fileWrite = new BufferedWriter(new FileWriter(filePath));
			fileWrite.write("Used Like New = " + usedLikeNew + "\n");
			fileWrite.write("Moderately Used = " + moderatelyUsed + "\n");
			fileWrite.write("Heavily Used = " + heavilyUsed + "\n");
			
			fileWrite.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean setPriceRanges(double minimum, double maximum)
	{
		String filePath = projectFilePath + systemConfigDBPath + "price_range.txt";
		
		try
		{
			BufferedWriter fileWrite = new BufferedWriter(new FileWriter(filePath));
			fileWrite.write("Minimum = " + minimum + "\n");
			fileWrite.write("Maximum = " + maximum + "\n");
			
			fileWrite.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	//Returns double array. So compMult[0] = usedLikeNew, compMult[1] = moderatelyUsed, compMult[2] = heavilyUsed
	public static Double[] getComputerMultipliers()
	{
		String filePath = projectFilePath + systemConfigDBPath + "computer.txt";
		Double[] returnArray = new Double[3];
		
		try
		{
			BufferedReader fileRead = new BufferedReader(new FileReader(filePath));
			returnArray[0] = Double.parseDouble(fileRead.readLine().substring(17));
			returnArray[1] = Double.parseDouble(fileRead.readLine().substring(19));
			returnArray[2] = Double.parseDouble(fileRead.readLine().substring(16));
			
			fileRead.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return returnArray;
	}
	
	public static Double[] getEnglishMultipliers()
	{
		String filePath = projectFilePath + systemConfigDBPath + "english.txt";
		Double[] returnArray = new Double[3];
		
		try
		{
			BufferedReader fileRead = new BufferedReader(new FileReader(filePath));
			returnArray[0] = Double.parseDouble(fileRead.readLine().substring(17));
			returnArray[1] = Double.parseDouble(fileRead.readLine().substring(19));
			returnArray[2] = Double.parseDouble(fileRead.readLine().substring(16));
			
			fileRead.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return returnArray;
	}
	
	public static Double[] getMathMultipliers()
	{
		String filePath = projectFilePath + systemConfigDBPath + "math.txt";
		Double[] returnArray = new Double[3];
		
		try
		{
			BufferedReader fileRead = new BufferedReader(new FileReader(filePath));
			returnArray[0] = Double.parseDouble(fileRead.readLine().substring(17));
			returnArray[1] = Double.parseDouble(fileRead.readLine().substring(19));
			returnArray[2] = Double.parseDouble(fileRead.readLine().substring(16));
			
			fileRead.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return returnArray;
	}
	
	public static Double[] getNaturalScienceMultipliers()
	{
		String filePath = projectFilePath + systemConfigDBPath + "natural_science.txt";
		Double[] returnArray = new Double[3];
		
		try
		{
			BufferedReader fileRead = new BufferedReader(new FileReader(filePath));
			returnArray[0] = Double.parseDouble(fileRead.readLine().substring(17));
			returnArray[1] = Double.parseDouble(fileRead.readLine().substring(19));
			returnArray[2] = Double.parseDouble(fileRead.readLine().substring(16));
			
			fileRead.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return returnArray;
	}
	
	public static Double[] getOtherMultipliers()
	{
		String filePath = projectFilePath + systemConfigDBPath + "other.txt";
		Double[] returnArray = new Double[3];
		
		try
		{
			BufferedReader fileRead = new BufferedReader(new FileReader(filePath));
			returnArray[0] = Double.parseDouble(fileRead.readLine().substring(17));
			returnArray[1] = Double.parseDouble(fileRead.readLine().substring(19));
			returnArray[2] = Double.parseDouble(fileRead.readLine().substring(16));
			
			fileRead.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return returnArray;
	}
	
	public static Double[] getPriceRanges()
	{
		String filePath = projectFilePath + systemConfigDBPath + "price_range.txt";
		Double[] returnArray = new Double[2];
		
		try
		{
			BufferedReader fileRead = new BufferedReader(new FileReader(filePath));
			returnArray[0] = Double.parseDouble(fileRead.readLine().substring(11));
			returnArray[1] = Double.parseDouble(fileRead.readLine().substring(11));
			
			fileRead.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return returnArray;
	}
	
	
	//Class specific functions. Some help the buyer page, seller page, ect
	public static ArrayList<Account> retrieveAllAccounts()
	{
		String filePath = projectFilePath + accountDBPath;
		ArrayList<String> fileList = Database.folderRead(filePath);
		
		ArrayList<Account> returnList = new ArrayList<Account>();
		for(String element : fileList)
		{
			returnList.add(Database.getFromAccountDB(Integer.parseInt(element)));
		}
	
		return returnList;
	}
	
	public static ArrayList<Transaction> retrieveAllAvailableForSale()
	{
		String filePath = projectFilePath + availableForSaleDBPath;
		ArrayList<String> fileList = Database.folderRead(filePath);
		
		ArrayList<Transaction> returnList = new ArrayList<Transaction>();
		for(String element : fileList)
		{
			returnList.add(Database.getFromAvailableForSaleDB(Integer.parseInt(element)));
		}
	
		return returnList;
	}
	
	public static ArrayList<Transaction> retrieveAllTransactions()
	{
		String filePath = projectFilePath + transactionDBPath;
		ArrayList<String> fileList = Database.folderRead(filePath);
		
		ArrayList<Transaction> returnList = new ArrayList<Transaction>();
		for(String element : fileList)
		{
			returnList.add(Database.getFromTransactionDB(Integer.parseInt(element)));
		}
	
		return returnList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}












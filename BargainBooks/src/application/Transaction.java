package application;

public class Transaction {
	private Book item;
	private int transactionID;
	private int sellerID;
	private int buyerID;
	private double sellerPrice;
	private double buyerPrice;
	
	
	
	//Constructors with either no data or all data (exccept for books, which should be retrived then modified)
	//Transaction IDs are generated automatically,
	public Transaction()
	{
		item = new Book();
		transactionID = -1;
		sellerID = -1;
		buyerID = -1;
		sellerPrice = -1.1;
		buyerPrice = -1.1;
	}
	
	public Transaction(Book item, int transactionID, int sellerID, int buyerID, double sellerPrice, double buyerPrice)
	{
		this.item = item;
		this.transactionID = transactionID;
		this.sellerID = sellerID;
		this.buyerID = buyerID;
		this.sellerPrice = sellerPrice;
		this.buyerPrice = buyerPrice;
	}
	
	//Setter Methods
	public void setItem(Book item) {this.item = item;}
	public void setTransactionID(int transactionID) {this.transactionID = transactionID;}
	public void setSellerID(int sellerID) {this.sellerID = sellerID;}
	public void setBuyerID(int buyerID) {this.buyerID = buyerID;}
	public void setSellerPrice(double sellerPrice) {this.sellerPrice = sellerPrice;}
	public void setBuyerPrice(double buyerPrice) {this.buyerPrice = buyerPrice;}
	
	//Getter Methods
	public Book getItem() {return item;}
	public int getTransactionID() {return transactionID;}
	public int getSellerID() {return sellerID;}
	public int getBuyerID() {return buyerID;}
	public double getSellerPrice() {return sellerPrice;}
	public double getBuyerPrice() {return buyerPrice;}
	
	//Test Functions
	public void printTransaction()
	{
		item.printBook();
		System.out.println("\nTransactionID :" + transactionID);
		System.out.println("Seller ID     :" + sellerID);
		System.out.println("Buyer ID      :" + buyerID);
		System.out.println("Seller Price  :" + sellerPrice);
		System.out.println("Buyer Price   :" + buyerPrice);
	}
	
	
}









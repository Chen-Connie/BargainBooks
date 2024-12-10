package application;

public class Book {
	private String title;
	private String author;
	private String subject;
	private int year;
	private String condition;
	private double originalPrice;
	
	
	
	//Constructors with either no data or all data.
	public Book()
	{
		title = "null";
		author = "null";
		subject = "null";
		year = -1;
		condition = "null";
		originalPrice = -1.1;
	}
	
	public Book(String title, String author, String subject, int year, String condition, double originalPrice)
	{
		this.title = title;
		this.author = author;
		this.subject = subject;
		this.year = year;
		this.condition = condition;
		this.originalPrice = originalPrice;
	}
	
	//Setter Methods
	public void setTitle(String title) {this.title = title;}
	public void setAuthor(String author) {this.author = author;}
	public void setSubject(String subject) {this.subject = subject;}
	public void setYear(int year) {this.year = year;}
	public void setCondition(String condition) {this.condition = condition;}
	public void setOriginalPrice(double originalPrice) {this.originalPrice = originalPrice;}
	
	//Getter Methods
	public String getTitle() {return title;}
	public String getAuthor() {return author;}
	public String getSubject() {return subject;}
	public int getYear() {return year;}
	public String getCondition() {return condition;}
	public double getOriginalPrice() {return originalPrice;}
	
	//Testing functions
	public void printBook()
	{
		System.out.println("Title          :" + title);
		System.out.println("Author         :" + author);
		System.out.println("Subject        :" + subject);
		System.out.println("Year           :" + year);
		System.out.println("Condition      :" + condition);
		System.out.println("Original Price :" + originalPrice);
	}
	
	
	
	
	
	
}







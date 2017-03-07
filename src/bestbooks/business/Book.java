package bestbooks.business;

import java.text.NumberFormat;

public class Book
{
	private String code;
	private String description;
	private double price;
	
	public Book()
	{
		this("", "", 0);
	}
	
	public Book(String code, String description, double price)
	{
		this.code = code;
		this.description = description;
		this.price = price;
		
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}
	
	public String getPriceFormatted()
	{
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		
		return currencyFormat.format(getPrice());
	}
}

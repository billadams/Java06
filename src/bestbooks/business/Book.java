package bestbooks.business;

import java.text.NumberFormat;

public class Book
{
	private String productCode;
	private String description;
	private double price;
	
	public Book()
	{
		this("", "", 0);
	}
	
	public Book(String productCode, String description, double price)
	{
		this.productCode = productCode;
		this.description = description;
		this.price = price;
		
	}

	public String getProductCode()
	{
		return productCode;
	}

	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
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

package bestbooks.business;

import java.util.List;

import bestbooks.db.BookDB;
import bestbooks.db.DBException;
import bestbooks.ui.StringUtil;
import bestbooks.ui.SwingValidator;

public class Order implements IPrintable
{
	private final double USED_BOOK_DISCOUNT_PRICE = 10.50;
	private final double SALES_TAX = 7.50;
	private double shippingCost;
	private double subTotal;
	private double orderTax;
	private double orderTotal;
	private double bookPrice;
	
	private List<Book> books;
	private int[] selectedItems;
	
	public Order () 
	{
		this.shippingCost = 0;
		this.subTotal = 0;
		this.orderTax = 0;
		this.orderTotal = 0;
		try
		{
			this.books = BookDB.getAll();
		} catch (DBException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Order(int[] selectedItems) 
	{
		this.shippingCost = 0;
		this.subTotal = 0;
		this.orderTax = 0;
		this.orderTotal = 0;
		try
		{
			this.books = BookDB.getAll();
		} catch (DBException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.selectedItems = selectedItems;
	}
	
	public Order(double shippingCost, double subTotal, double orderTax, double orderTotal)
	{
		this.shippingCost = shippingCost;
		this.subTotal = subTotal;
		this.orderTax = orderTax;
		this.orderTotal = orderTotal;
		try
		{
			this.books = BookDB.getAll();
		} catch (DBException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int[] getSelectedItems()
	{
		return selectedItems;
	}
	
	public void setSelectedItems(int[] selectedItems)
	{
		this.selectedItems = selectedItems;
	}
	
	public double getBookPrice()
	{
		return bookPrice;
	}
	
	public void setBookPrice(double bookPrice)
	{
		this.bookPrice = bookPrice;
	}
	
	public List<Book> getBooks()
	{
		return books;
	}
	
	public void setBooks(List<Book> books)
	{
		this.books = books;
	}
	
	public double getShippingCost()
	{
		return shippingCost;
	}
	
	public void setShippingCost(double shippingCost)
	{
		this.shippingCost = shippingCost;
	}
	
	public double getSubTotal()
	{
		return subTotal;
	}
	
	public void setSubTotal(double subTotal)
	{
		this.subTotal = subTotal;
	}
	
	public double getOrderTax()
	{
		return orderTax;
	}
	
	public void setOrderTax(double orderTax)
	{
		this.orderTax = orderTax;
	}
	
	public double getOrderTotal()
	{
		return orderTotal;
	}
	
	public void setOrderTotal(double orderTotal)
	{
		this.orderTotal = orderTotal;
	}
	
	public double getUSED_BOOK_DISCOUNT_PRICE()
	{
		return USED_BOOK_DISCOUNT_PRICE;
	}
	
	public double getSALES_TAX()
	{
		return SALES_TAX;
	}
	
	public void calculateOrderTotal(boolean useNewPrice, boolean calculateShipping)
	{		
		for (int i = 0; i < selectedItems.length; i++)
		{
			bookPrice = books.get(selectedItems[i]).getPrice();
			if (!useNewPrice)
			{
				bookPrice -= USED_BOOK_DISCOUNT_PRICE;
			}

			subTotal += bookPrice;
		}
		
		orderTax = subTotal * (SALES_TAX / 100);
		orderTotal = subTotal + orderTax;
	
		if (calculateShipping)
		{
			shippingCost = 2.5;
			orderTotal += shippingCost;
		}
	}

	@Override
	public StringBuilder print()
	{
		String bookTitle = "";
//		double bookPrice = 0;
		StringBuilder sb = new StringBuilder();
		
		sb.append(StringUtil.padWithSpaces("Book Title", 40));
		sb.append("Book Price\n");
		for (int i = 0; i < selectedItems.length; i++)
		{
			bookTitle = books.get(selectedItems[i]).getDescription();
//			bookPrice = books.get(selectedItems[i]).getPrice();
			
			sb.append(StringUtil.padWithSpaces(bookTitle, 40));
			sb.append(SwingValidator.formatRound(bookPrice) + "\n");
		}
		
		sb.append("\n\n");
		sb.append(StringUtil.padWithSpaces("Subtotal", 40) + SwingValidator.formatRound(this.getSubTotal()) + "\n");
		sb.append(StringUtil.padWithSpaces("Sales Tax", 40) + SwingValidator.formatRound(this.getOrderTax()) + "\n");
		sb.append(StringUtil.padWithSpaces("Shipping Total", 40) + SwingValidator.formatRound(this.getShippingCost()) + "\n");
		sb.append(StringUtil.padWithSpaces("Order Total", 40) + SwingValidator.formatRound(this.getOrderTotal()));
		
		return sb;
	}
}

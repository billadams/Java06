package bestbooks.ui;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.text.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.math.*;
import java.util.Date;

public class SwingValidator
{
	public static boolean isPresent(JTextComponent c, String title)
	{
		if (c.getText().isEmpty())
		{
		    showMessage(c, title + " is a required field.\n"
				+ "Please re-enter.");
			c.requestFocusInWindow();
			c.selectAll();
			return false;
		}
		return true;
	}

	public static boolean isInteger(JTextComponent c, String title)
	{
		try
		{
			int i = Integer.parseInt(c.getText());
			return true;
		}
		catch (NumberFormatException e)
		{
		    showMessage(c, title + " must be an integer.\n"
				+ "Please re-enter.");
			c.requestFocusInWindow();
			c.selectAll();
			return false;
		}
	}

	public static boolean isDouble(JTextComponent c, String title)
	{
		try
		{
			double d = Double.parseDouble(c.getText());
			return true;
		}
		catch (NumberFormatException e)
		{
		    showMessage(c, title + " must be a valid number.\n"
				+ "Please re-enter.");
			c.requestFocusInWindow();
			c.selectAll();
			return false;
		}
	}
	public static boolean isWithinRangeDouble(JTextComponent c, double min, double max, String title)
	{
		double d = Double.parseDouble(c.getText());
		if (d < min | d > max)
		{
			showMessage(c, title + " must be between " + min + " and " + max + ".\n"
				+ "Please re-enter.");
			c.requestFocusInWindow();
			c.selectAll();
			return false;
		}
		return true;
	}
	public static boolean isWithinRangeInteger(JTextComponent c, int min, int max, String title)
	{
		int i = Integer.parseInt(c.getText());
		if (i < min | i > max)
		{
			showMessage(c, title + " must be between " + min + " and " + max + ".\n"
				+ "Please re-enter.");
			c.requestFocusInWindow();
			c.selectAll();
			return false;
		}
		return true;
	}
	//specific String is passed to method if you don't have a range(ex. you must be 18 or older)
	public static boolean isValidEntryInteger(JTextComponent c, int min, String title)
	{
		int i = Integer.parseInt(c.getText());
		if (i < min)
		{
			showMessage(c, title +  " please re-enter.");
			c.requestFocusInWindow();
			c.selectAll();
			return false;
		}
		return true;
	}
	public static boolean isWithinRangeLong(JTextComponent c, long min, long max, String title)
	{
		long l = Long.parseLong(c.getText());
		if (l < min | l > max)
		{
			
			showMessage(c, title + " must be between " + min + " and " + max + ".\n"
				+ "Please re-enter.");
			c.requestFocusInWindow();
			c.selectAll();
			return false;
		}
		return true;
	}
	//method that test for a valid date
	public static boolean isDateValid(JTextComponent c, String date)
	{		
		try
		{
			
			LocalDate parsedDate = LocalDate.parse(c.getText());//parses the String into a LocalDate object
			
						
			return true;
		}
		catch (DateTimeParseException e)
		{
			showMessage(c, date + " must be yyyy-mm-dd " + ".\n"
			+ "Please re-enter.");
			c.requestFocusInWindow();
			c.selectAll();
			return false;
		}
	}

	private static void showMessage(JTextComponent c, String message)
	{
		    JOptionPane.showMessageDialog(c, message, "Invalid Entry",
		    	JOptionPane.ERROR_MESSAGE);
	}
	
	//*******************************************
   public static BigDecimal formatRound(double number)
   {
	    BigDecimal decimalRound  = new BigDecimal(Double.toString(number));	    
	    return decimalRound = decimalRound.setScale(2, RoundingMode.HALF_UP);		
   }
 
 //**************************************************************
 
 	public static String formatRoundDollar(double number)
 	{
	 	NumberFormat num = NumberFormat.getCurrencyInstance();

		BigDecimal decimalRound  = new BigDecimal(Double.toString(number));
		return num.format(decimalRound = decimalRound.setScale(2, RoundingMode.HALF_UP));
    }
    
    
}
    

	

    
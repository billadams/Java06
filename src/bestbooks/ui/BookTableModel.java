package bestbooks.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import bestbooks.business.Book;
import bestbooks.db.BookDB;
import bestbooks.db.DBException;

@SuppressWarnings("serial")
public class BookTableModel extends AbstractTableModel
{
	private List<Book> books;
	private static final String[] COLUMN_NAMES = { "ProductCode", "Description", "Price" };
	
	public BookTableModel()
	{
		try
		{
			books = BookDB.getAll();
		}
		catch (DBException e)
		{
			System.out.println(e);;
		}
	}
	
	Book getBook(int rowIndex)
	{
		return books.get(rowIndex);
	}
	
	void databaseUpdated()
	{
        try {
            books = BookDB.getAll();
            fireTableDataChanged();
        } catch (DBException e) {
            System.out.println(e);
        }   
	}

	@Override
	public int getRowCount()
	{
		return books.size();
	}

	@Override
	public int getColumnCount()
	{
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnName(int columnIndex)
	{
		return COLUMN_NAMES[columnIndex];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		switch (columnIndex)
		{
			case 0:
				return books.get(rowIndex).getProductCode();
			case 1:
				return books.get(rowIndex).getDescription();
			case 2:
				return books.get(rowIndex).getPriceFormatted();
			default:
				return null;
		}
	}
	
}

package bestbooks.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import bestbooks.business.Book;

@SuppressWarnings("serial")
public class BookTableModel extends AbstractTableModel
{
	private List<Book> books;
	private static final String[] COLUMN_NAMES = { "ProductCode", "Description", "Price" };
	
	public BookTableModel()
	{
		// TODO Code to connect to DB and fill books list.
	}
	
	Book getBook(int rowIndex)
	{
		return books.get(rowIndex);
	}
	
	void databaseUpdated()
	{
		// TODO Code to update database and fireTableDataChanged
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
				return books.get(rowIndex).getCode();
			case 1:
				return books.get(rowIndex).getDescription();
			case 2:
				return books.get(rowIndex).getPriceFormatted();
			default:
				return null;
		}
	}
	
}

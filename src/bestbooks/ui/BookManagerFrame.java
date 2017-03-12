package bestbooks.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import bestbooks.ui.SwingValidator;
import bestbooks.business.Book;
import bestbooks.db.BookDB;
import bestbooks.db.DBException;
	
@SuppressWarnings("serial")
public class BookManagerFrame extends JFrame
{
	private JMenuItem mnuFileLogin;
	private JMenuItem mnuFileExit;
	private JTable bookTable;
	private BookTableModel bookTableModel;
	private JPanel panel;
	
	private JList listBooks;
	private DefaultListModel listModel;
	private JRadioButton rdoNewBook;
	private JRadioButton rdoUsedBook;
	ButtonGroup selection = new ButtonGroup();
	private JCheckBox chkShipping;
	private JTextField nameField, addressField, cityField, stateField, zipField, phoneField;
	private JButton btnSubmit;
	
	private List<Book> books;
	
	public BookManagerFrame()
	{
        try 
        {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
        setTitle("Best Books Inventory Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 400, 450, 300);
		setResizable(false);
		
		// Add the menubar to the form.
		this.setJMenuBar(buildMenuBar());

		setVisible(true);
	}
	
	private JMenuBar buildMenuBar()
	{
		// Build the menu.
		JMenuBar menuBar = new JMenuBar();
		
		JMenu mnuFile = new JMenu("File");
		mnuFile.setMnemonic(KeyEvent.VK_F);
		menuBar.add(mnuFile);
		
		mnuFileLogin = new JMenuItem("Login");
		mnuFileLogin.setMnemonic(KeyEvent.VK_L);
		mnuFileLogin.addActionListener((ActionEvent) ->
		{
			// Get the login type and build the relevant form based on the login credentials.
			getLoginType();
//			BookManagerFrame.this.mnuFileLogin.setText("Logout");
		});
		mnuFile.add(mnuFileLogin);
		
		mnuFile.addSeparator();
		mnuFileExit = new JMenuItem("Exit");
		mnuFileExit.setMnemonic(KeyEvent.VK_X);
		mnuFileExit.addActionListener((ActionEvent) ->
		{
			dispose();
		});
		mnuFile.add(mnuFileExit);
		
		return menuBar;
	}
	
	private String getLoginType()
	{
		String userType = "";
			
		LoginDialog loginDialog = new LoginDialog(this, "User Login", true);
		loginDialog.setLocationRelativeTo(this);
		loginDialog.setVisible(true);
		
		return userType;
	}
	
	public void buildAdminForm()
	{
		panel = new JPanel();
		
		JButton addButton = new JButton("Add");
		addButton.setToolTipText("Add book");
		addButton.addActionListener((ActionEvent) ->
		{
			addNewBook();
		});
		panel.add(addButton);
		
		JButton editButton = new JButton("Edit");
		editButton.setToolTipText("Edit selected book");
		editButton.addActionListener((ActionEvent) ->
		{
			editBook();
		});
		panel.add(editButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setToolTipText("Delete selected book");
		deleteButton.addActionListener((ActionEvent) ->
		{
			deleteBook();
		});
		panel.add(deleteButton);
		
		BookManagerFrame.this.add(panel, BorderLayout.NORTH);
		
		// Build table
		bookTableModel = new BookTableModel();
		JTable table = new JTable(bookTableModel);
		bookTable = table;
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(null);
		BookManagerFrame.this.add(new JScrollPane(table), BorderLayout.CENTER);
		
		panel.revalidate();  
		panel.repaint();
	}
	
	public void buildStandardForm()
	{
		// Create the header.
		JPanel headerPanel = new JPanel();
		
		JLabel heading = new JLabel();
		heading.setText("BestBooks Book Purchasing Client");
		headerPanel.add(heading);
		
		BookManagerFrame.this.add(headerPanel, BorderLayout.NORTH);
		
		// Create the center pane that holds the list.
		JPanel centerPanel = new JPanel();
		
		listBooks = new JList(populateJList());
		listBooks.setFixedCellWidth(420);;
		listBooks.setVisibleRowCount(13);
		listBooks.setFont( new Font("monospaced", Font.PLAIN, 12) );
		listBooks.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		centerPanel.add(listBooks);
		JScrollPane listScroller = new JScrollPane(listBooks, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		centerPanel.add(listScroller);
		populateJList();
		BookManagerFrame.this.add(centerPanel, BorderLayout.CENTER);
		
		// Create the east pane that holds the option buttons and checkbox.
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5 ,5, 0, 5);
		
		JPanel optionPanel = new JPanel(new GridBagLayout());
		
		rdoNewBook = new JRadioButton("New Book");
		rdoNewBook.setSelected(true);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		optionPanel.add(rdoNewBook, c);
		
		rdoUsedBook = new JRadioButton("Used Book");
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_START;
		optionPanel.add(rdoUsedBook, c);
		
		chkShipping = new JCheckBox("Ship to me ($2.50 shipping charge)");
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_START;
		optionPanel.add(chkShipping, c);
		
		selection.add(rdoNewBook);
		selection.add(rdoUsedBook);;
		BookManagerFrame.this.add(optionPanel, BorderLayout.EAST);
		
		
		// Create the south pane that holds the text fields and submit button.
		JPanel orderForm = new JPanel(new GridBagLayout());
		nameField = new JTextField();
		addressField = new JTextField();
		cityField = new JTextField();
		stateField = new JTextField();
		zipField = new JTextField();
		phoneField = new JTextField();
		btnSubmit = new JButton("Submit");
		
		Dimension shortField = new Dimension(250, 20);
		nameField.setPreferredSize(shortField);
		nameField.setMinimumSize(shortField);
		addressField.setPreferredSize(shortField);
		addressField.setMinimumSize(shortField);
		cityField.setPreferredSize(shortField);
		cityField.setMinimumSize(shortField);
		stateField.setPreferredSize(shortField);
		stateField.setMinimumSize(shortField);
		zipField.setPreferredSize(shortField);
		zipField.setMinimumSize(shortField);
		phoneField.setPreferredSize(shortField);
		phoneField.setMinimumSize(shortField);
			
		orderForm.add(new JLabel("Name:"),
				getConstraints(0, 0, GridBagConstraints.LINE_END, 1));
		orderForm.add(nameField,
				getConstraints(1, 0, GridBagConstraints.LINE_START, 1));
		orderForm.add(new JLabel("Address:"),
				getConstraints(0, 1, GridBagConstraints.LINE_END, 1));
		orderForm.add(addressField,
				getConstraints(1, 1, GridBagConstraints.LINE_START, 1));
		orderForm.add(new JLabel("City:"),
				getConstraints(0, 2, GridBagConstraints.LINE_END, 1));
		orderForm.add(cityField,
				getConstraints(1, 2, GridBagConstraints.LINE_START, 1));
		orderForm.add(new JLabel("State:"),
				getConstraints(0, 3, GridBagConstraints.LINE_END, 1));
		orderForm.add(stateField,
				getConstraints(1, 3, GridBagConstraints.LINE_START, 1));
		orderForm.add(new JLabel("Zipcode:"),
				getConstraints(0, 4, GridBagConstraints.LINE_END, 1));
		orderForm.add(zipField,
				getConstraints(1, 4, GridBagConstraints.LINE_START, 1));
		orderForm.add(new JLabel("Phone:"),
				getConstraints(0, 5, GridBagConstraints.LINE_END, 1));
		orderForm.add(phoneField,
				getConstraints(1, 5, GridBagConstraints.LINE_START, 1));	
		orderForm.add(btnSubmit,
				getConstraints(0, 6, GridBagConstraints.CENTER, 2));
		this.getRootPane().setDefaultButton(btnSubmit);
		btnSubmit.addActionListener((ActionEvent) -> {
			double usedBookDiscountPrice = 10.50;
			double shippingCost = 2.50;
			boolean useNewPrice = true;
			boolean calculateShipping = false;
			double price = 0;
			double total = 0;
			
//			boolean isValid = checkValidData();
//			if (isValid)
//			{
				if (rdoUsedBook.isSelected())
				{
					useNewPrice = false;
				}
	
				if (chkShipping.isSelected())
				{
					calculateShipping = true;
				}
						
				int[] index = listBooks.getSelectedIndices();
				
				calculateOrderTotal(useNewPrice, calculateShipping, index);
				
//				for (int i = 0; i < index.length; i++)
//				{
//					price = books.get(index[i]).getPrice();
//					total += price;
//				}
//				
//				JOptionPane.showMessageDialog(this, "Your order is " + total);
//			}	
		});
		
		BookManagerFrame.this.add(orderForm, BorderLayout.PAGE_END);
				
		pack();
		
//		headerPanel.revalidate();
//		headerPanel.repaint();
//		centerPanel.revalidate();
//		centerPanel.repaint();
	}
	
	public void calculateOrderTotal(boolean useNewPrice, boolean calculateShipping, int[] index)
	{
		double usedBookDiscountPrice = 10.50;
		final double salesTax = 7.5;
		double shippingCost = 0;
		String bookTitle = "";
		double bookPrice = 0;
		double orderTax = 0;
		double subTotal = 0;
		double orderTotal = 0;
		
		for (int i = 0; i < index.length; i++)
		{
			bookPrice = books.get(index[i]).getPrice();
			if (!useNewPrice)
			{
				bookPrice -= usedBookDiscountPrice;
			}

			subTotal += bookPrice;
		}
		
		orderTax = subTotal * (salesTax / 100);
		orderTotal = subTotal + orderTax;
	
		if (calculateShipping)
		{
			shippingCost = 2.5;
			orderTotal += shippingCost;
		}
		
		String  s =  StringUtil.padWithSpaces("Subtotal", 20) + SwingValidator.formatRound(subTotal) + "\n";
				s += StringUtil.padWithSpaces("Sales Tax", 20) + SwingValidator.formatRound(orderTax) + "\n";
				s += StringUtil.padWithSpaces("Shipping Total", 20) + SwingValidator.formatRound(shippingCost) + "\n";
				s += StringUtil.padWithSpaces("Order Total", 20) + SwingValidator.formatRound(orderTotal);
				
		ReceiptDialog receiptDialog = new ReceiptDialog(this, true, s);
		receiptDialog.setLocationRelativeTo(this);
		receiptDialog.setVisible(true);
		
//		JOptionPane.showMessageDialog(this, s);
	}
	
	public boolean checkValidData()
	{
		return (SwingValidator.isPresent(nameField, "Name")) &&
			   (SwingValidator.isPresent(addressField, "Address")) &&
			   (SwingValidator.isPresent(cityField, "City")) &&
			   (SwingValidator.isPresent(stateField, "State")) &&
			   (SwingValidator.isPresent(zipField, "Zipcode")) &&
			   (SwingValidator.isPresent(phoneField, "Phone"));
	}
	
	public DefaultListModel<Book> populateJList()
	{
		listModel = new DefaultListModel<Book>();
		
//		BookDB book = new BookDB();
//		Book books = new Book();
		try
		{
			books = BookDB.getAll();
//			listModel.addElement(StringUtil.padWithSpaces("Book Title", 30) + StringUtil.padWithSpaces("Book Cost", 10));
//			listModel.addElement(StringUtil.padWithSpaces("-", 30) + StringUtil.padWithSpaces("-", 10));
			for (Book b : books)
			{
				listModel.addElement(StringUtil.padWithSpaces(b.getDescription(), 30) + StringUtil.padWithSpaces(b.getPriceFormatted(), 10));
			}
			
//			return listModel;
		} catch (DBException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listModel;
	}
	
	private GridBagConstraints getConstraints(int x, int y, int anchor, int width)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 0, 5);
		c.gridx = x;
		c.gridy = y;
		c.anchor = anchor;
		c.gridwidth = width;
		
		return c;	
	}
	
	private void addNewBook()
	{
		BookForm bookForm = new BookForm(this, "Add Book", true);
		bookForm.setLocationRelativeTo(this);
		bookForm.setVisible(true);
	}
	
	private void editBook()
	{
		int selectedRow = bookTable.getSelectedRow();
		if (selectedRow == -1)
		{
			JOptionPane.showMessageDialog(this, 
					"No product is currently selected.",
					"No product selected", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			Book book = bookTableModel.getBook(selectedRow);
			BookForm bookForm = new BookForm(this, "Edit Product", true, book);
			bookForm.setLocationRelativeTo(this);
			bookForm.setVisible(true);
		}
	}
	
	private void deleteBook()
	{
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "No product is currently selected.", 
                    "No product selected", JOptionPane.ERROR_MESSAGE);
        } 
        else 
        {
            Book book = bookTableModel.getBook(selectedRow);
            int ask = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete " + 
                        book.getDescription() + " from the database?",
                    "Confirm delete", JOptionPane.YES_NO_OPTION);
            if (ask == JOptionPane.YES_OPTION) 
            {
                try 
                {                    
                    BookDB.delete(book);
                    fireDatabaseUpdatedEvent();
                } 
                catch (DBException e) {
                	
                    System.out.println(e);
                }
            }
        }
	}
	
    void fireDatabaseUpdatedEvent() {
        bookTableModel.databaseUpdated();
    }   
	
}

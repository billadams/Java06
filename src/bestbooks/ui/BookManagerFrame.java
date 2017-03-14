package bestbooks.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
import bestbooks.business.Order;
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
	private JPanel headerPanel, centerPanel, optionPanel, orderForm, buttonPanel, tablePanel;
	private JTable table;
	
	private JList listBooks;
	private DefaultListModel listModel;
	private JRadioButton rdoNewBook;
	private JRadioButton rdoUsedBook;
	ButtonGroup selection = new ButtonGroup();
	private JCheckBox chkShipping;
	private JTextField nameField, addressField, cityField, stateField, zipField, phoneField;
	private JButton btnSubmit;
	private boolean isLoggedIn = false;
	private boolean didSubmitLogin = false;
	private boolean isAdmin = false;
	
	private List<Book> books;
	LocalDate today = LocalDate.now();
	
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
        setBackground(Color.LIGHT_GRAY);
        setTitle("Best Books Inventory Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 400, 450, 300);
		setResizable(false);
		
		// Add the menubar to the form.
		this.setJMenuBar(buildMenuBar());

		setVisible(true);
	}
	
	/**
	 * Builds the menu bar at the top of the main application.
	 * 
	 * @return JMenuBar object
	 */
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
			if (!isLoggedIn)
			{
				// Get the login type and build the relevant form based on the login credentials.
				displayLoginWindow();
				if (didSubmitLogin)
				{
					BookManagerFrame.this.mnuFileLogin.setText("Logout");
					isLoggedIn = true;
				}
			}
			else
			{
				if (!isAdmin)
				{
					// When logging out, just hide the panels. Seems to be the best solution, for now....
					headerPanel.setVisible(false);
					centerPanel.setVisible(false);
					optionPanel.setVisible(false);
					orderForm.setVisible(false);
				}
				else
				{
					// If admin was logged in, log them out and hide the admin panels.
					buttonPanel.setVisible(false);
					tablePanel.setVisible(false);
					isAdmin = false;
				}
				
				BookManagerFrame.this.mnuFileLogin.setText("Login");
				didSubmitLogin = false;
				isLoggedIn = false;
			}		
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
	
	/**
	 * Gets the didSubmitLogin variable.
	 * 
	 * @return boolean
	 */
	public boolean getDidSubmitLogin()
	{
		return didSubmitLogin;
	}
	
	/**
	 * Sets the didSubmitLogin variable.
	 * 
	 * @param boolean didSubmitLogin
	 * @return void
	 */
	public void setDidSubmitiLogin(boolean didSubmitLogin)
	{
		this.didSubmitLogin = didSubmitLogin;
	}
	
	/**
	 * Creates the login window. 
	 * 
	 * @return void
	 */
	private void displayLoginWindow()
	{			
		LoginDialog loginDialog = new LoginDialog(this, "User Login", true);
		loginDialog.setLocationRelativeTo(this);
		loginDialog.setVisible(true);
	}
	
	/**
	 * Creates the Admin window.
	 * 
	 * @return void
	 */
	public void buildAdminForm()
	{	
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		
		JButton addButton = new JButton("Add");
		addButton.setToolTipText("Add book");
		addButton.addActionListener((ActionEvent) ->
		{
			addNewBook();
		});
		buttonPanel.add(addButton);
		
		JButton editButton = new JButton("Edit");
		editButton.setToolTipText("Edit selected book");
		editButton.addActionListener((ActionEvent) ->
		{
			editBook();
		});
		buttonPanel.add(editButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setToolTipText("Delete selected book");
		deleteButton.addActionListener((ActionEvent) ->
		{
			deleteBook();
		});
		buttonPanel.add(deleteButton);
		
		BookManagerFrame.this.add(buttonPanel, BorderLayout.NORTH);
		
		// Build the table for interacting with the books in the database.
		tablePanel = new JPanel();
		tablePanel.setBackground(Color.LIGHT_GRAY);
		
		bookTableModel = new BookTableModel();
		table = new JTable(bookTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(null);
		JScrollPane spTable = new JScrollPane(table);
		
		tablePanel.add(spTable);
		BookManagerFrame.this.add(tablePanel, BorderLayout.CENTER);
			
		isAdmin = true;

		buttonPanel.setVisible(true);
		tablePanel.setVisible(true);
		
		buttonPanel.revalidate();
		tablePanel.revalidate();
		
		pack();
	}
	
	/**
	 * Creates the standard user form.
	 * 
	 * @return void
	 */
	public void buildStandardForm()
	{	
		// Create the header.
		headerPanel = new JPanel();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
		String currentDateTimeFormatted = dtf.format(today);
		
		JLabel heading = new JLabel();
		heading.setText(currentDateTimeFormatted);
		headerPanel.add(heading);
		headerPanel.setBackground(Color.LIGHT_GRAY);
		
		BookManagerFrame.this.add(headerPanel, BorderLayout.NORTH);
		
		// Create the center pane that holds the list.
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.LIGHT_GRAY);
		
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
		
		optionPanel = new JPanel(new GridBagLayout());
		optionPanel.setBackground(Color.LIGHT_GRAY);
		
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
		orderForm = new JPanel(new GridBagLayout());
		orderForm.setBackground(Color.LIGHT_GRAY);
		
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
			boolean useNewPrice = true;
			boolean calculateShipping = false;
					
			if (listBooks.getSelectedIndex() == -1)
			{
				JOptionPane.showMessageDialog(this, 
						"No product is currently selected.",
						"No product selected", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				boolean isValid = checkValidData();
				if (isValid)
				{
					if (rdoUsedBook.isSelected())
					{
						useNewPrice = false;
					}
		
					if (chkShipping.isSelected())
					{
						calculateShipping = true;
					}
							
					int[] selectedItems = listBooks.getSelectedIndices();
					
					Order order = new Order(selectedItems, useNewPrice);
					order.calculateOrderTotal(useNewPrice, calculateShipping);
					
					String orderDetails = order.print().toString();
					
					// Display the order receipt.
					ReceiptDialog receiptDialog = new ReceiptDialog(this, true, orderDetails);
					receiptDialog.setLocationRelativeTo(this);
					receiptDialog.setVisible(true);
				}	
			}

		});
		
		BookManagerFrame.this.add(orderForm, BorderLayout.PAGE_END);
		
		pack();
	}
	
	/**
	 * Checks fields for the correct data.
	 * 
	 * @return boolean
	 */
	public boolean checkValidData()
	{
		return (SwingValidator.isPresent(nameField, "Name")) &&
			   (SwingValidator.isPresent(addressField, "Address")) &&
			   (SwingValidator.isPresent(cityField, "City")) &&
			   (SwingValidator.isPresent(stateField, "State")) &&
			   (SwingValidator.isPresent(zipField, "Zipcode")) &&
			   (SwingValidator.isPresent(phoneField, "Phone"));
	}
	
	/**
	 * Populates the JList in the standard user form
	 * 
	 * @return
	 */
	public DefaultListModel<Book> populateJList()
	{
		listModel = new DefaultListModel<Book>();
		
		try
		{
			books = BookDB.getAll();

			for (Book b : books)
			{
				listModel.addElement(StringUtil.padWithSpaces(b.getDescription(), 30) + StringUtil.padWithSpaces(b.getPriceFormatted(), 10));
			}
		} catch (DBException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listModel;
	}
	
	/**
	 * Helper function to more easily set up GridBagConstraints.
	 * 
	 * @param x
	 * @param y
	 * @param anchor
	 * @param width
	 * @return GridBagConstraints object
	 */
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
	
	/**
	 * Displays the addNewBook form.
	 * 
	 * @return void
	 */
	private void addNewBook()
	{
		BookForm bookForm = new BookForm(this, "Add Book", true);
		bookForm.setLocationRelativeTo(this);
		bookForm.setVisible(true);
	}
	
	/**
	 * Displays the editBook form.
	 * 
	 * @return void
	 */
	private void editBook()
	{
		int selectedRow = table.getSelectedRow();
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
	
	/**
	 * Displays the deleteBook form.
	 * 
	 * @return void
	 */
	private void deleteBook()
	{
        int selectedRow = table.getSelectedRow();
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
	
	/**
	 * Updates the table whenever a new entry is added to the book table,
	 * when a book is edited, or when a book is deleted.
	 * 
	 * @return void
	 */
    void fireDatabaseUpdatedEvent() {
        bookTableModel.databaseUpdated();
    }   
	
}

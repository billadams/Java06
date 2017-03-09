package bestbooks.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import bestbooks.business.Book; 

@SuppressWarnings("serial")
public class BookManagerFrame extends JFrame
{
	private JMenuItem mnuFileLogin;
	private JMenuItem mnuFileExit;
	private JTable bookTable;
	private BookTableModel bookTableModel;
	
	private JList listBooks;
	public DefaultListModel listModel;
	
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
		
		// Add the menubar to the form.
		this.setJMenuBar(buildMenuBar());
		

		
		
		// Build the main frames JPanel.
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
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
		JPanel panel = new JPanel();
		
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
//			deleteBook();
		});
		panel.add(deleteButton);
		
		BookManagerFrame.this.add(panel, BorderLayout.NORTH);
		
		// Build table
		bookTableModel = new BookTableModel();
		JTable table = new JTable(bookTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(null);
		BookManagerFrame.this.add(new JScrollPane(table), BorderLayout.CENTER);
		
		panel.revalidate();  
		panel.repaint();
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
	
	public void buildStandardForm()
	{
		JOptionPane.showMessageDialog(BookManagerFrame.getFrames()[0], "Start building standard form");
	}
	
    void fireDatabaseUpdatedEvent() {
        bookTableModel.databaseUpdated();
    }   
	
}

package bestbooks.ui;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder; 

@SuppressWarnings("serial")
public class BookManagerFrame extends JFrame
{
	private JPanel contentPane;
	private JMenuItem mnuFileLogin;
	private JMenuItem mnuFileExit;
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
		
		// Build the menu.
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnuFile = new JMenu("File");
		menuBar.add(mnuFile);
		
		mnuFileLogin = new JMenuItem("Login");
		mnuFileLogin.addActionListener((ActionEvent) ->
		{
			// Get the login type and build the relevant form based on the login credentials.
			getLoginType();
		});
		mnuFile.add(mnuFileLogin);
		
		mnuFileExit = new JMenuItem("Exit");
		mnuFileExit.addActionListener((ActionEvent) ->
		{
			dispose();
		});
		mnuFile.add(mnuFileExit);
		
		// Build the main frames JPanel.
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setVisible(true);
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
		listBooks = new JList(listModel);
		
//		JOptionPane.showMessageDialog(BookManagerFrame.getFrames()[0], "Start building admin form");
	}
	
	public void buildStandardForm()
	{
		JOptionPane.showMessageDialog(BookManagerFrame.getFrames()[0], "Start building standard form");
	}
	
}

//package bestbooks.ui;
//import java.awt.BorderLayout;
//import java.awt.EventQueue;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//
//import murach.ui.ProductManagerFrame;
//
//import javax.swing.JMenuBar;
//import javax.swing.JMenu;
//import javax.swing.JMenuItem;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//
//@SuppressWarnings("serial")
//public class Main extends JFrame
//{
//
//	private JPanel contentPane;
//	private JMenuItem mnuStartLogin;
//	private String username;
//	private String password;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args)
//	{
//		BookManagerFrame frame = new BookManagerFrame();
//		EventQueue.invokeLater(new Runnable()
//		{
//			public void run()
//			{
//				try
//				{
//					Main frame = new Main();
//					frame.setVisible(true);
//				} catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
//	public Main()
//	{
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(400, 400, 450, 300);
//		
//		JMenuBar menuBar = new JMenuBar();
//		setJMenuBar(menuBar);
//		
//		JMenu mnuStart = new JMenu("Start");
//		menuBar.add(mnuStart);
//		
//		mnuStartLogin = new JMenuItem("Login");
//		mnuStartLogin.addActionListener(new ActionListener() 
//		{
//			public void actionPerformed(ActionEvent e) 
//			{
////				JFrame frameLoginDialog = new JFrame("Login to continue");
//				LoginDialog loginDialog = new LoginDialog(this, "Login to continue", true);
//				loginDialog.setVisible(true);
//				
//
//			}
//		});
//		mnuStart.add(mnuStartLogin);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
//	}
//	
//	public void setUserName(String username)
//	{
//		this.username = username;
//	}
//	
//	public void setPassword(String password)
//	{
//		this.password = password;
//	}
//
//}

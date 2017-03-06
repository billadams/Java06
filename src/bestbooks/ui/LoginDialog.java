package bestbooks.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class LoginDialog extends JDialog
{
//	private JRadioButton rdoStandard;
//	private JRadioButton rdoAdmin;
//	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JLabel lblLoginAdminCredentials;
	private JLabel lblLoginStandardCredentials;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JButton btnLogin;
	private JButton btnCancel;
//	private boolean succeeded;
	
	public LoginDialog(Frame frame, String title, boolean modal)
	{
		super(frame, title, modal);
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 0, 5);
		
		c.fill = GridBagConstraints.HORIZONTAL;
	
		lblLoginAdminCredentials = new JLabel("Admin login -> admin/admin");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
//		c.anchor = GridBagConstraints.CENTER;
		panel.add(lblLoginAdminCredentials, c);
		
		lblLoginStandardCredentials = new JLabel("Standard login -> default/default");
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
//		c.anchor = GridBagConstraints.CENTER;
		panel.add(lblLoginStandardCredentials, c);
		
		lblUsername = new JLabel("Username: ");
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_END;
		c.gridwidth = 1;
		panel.add(lblUsername, c);
		
		txtUsername = new JTextField(20);
		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridwidth = 2;
		panel.add(txtUsername, c);
		
		lblPassword = new JLabel("Password: ");
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.LINE_END;
		c.gridwidth = 1;
		panel.add(lblPassword, c);
		
		txtPassword = new JTextField(20);
		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridwidth = 2;
		panel.add(txtPassword, c);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				String username = txtUsername.getText();
				String password = txtPassword.getText();
				
				if ((Login.authenticateUser(username, password, true)) && (username.equals("admin") && password.equals("admin")))
				{
					((BookManagerFrame) frame).buildAdminForm();
//					JOptionPane.showMessageDialog(frame, "Logged in as admin", "Login type", JOptionPane.INFORMATION_MESSAGE);
				}
				else if ((Login.authenticateUser(username, password, false)) && (username.equals("default") && password.equals("default")))
				{
					((BookManagerFrame) frame).buildStandardForm();
//					JOptionPane.showMessageDialog(frame, "Logged in as standard user", "Login type", JOptionPane.INFORMATION_MESSAGE);
				}
//				dispose();
				
			}
		});
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		
//		JPanel optionPanel = new JPanel();
//		optionPanel.add(rdoStandard);
//		optionPanel.add(rdoAdmin);
		
//		rdoStandard = new JRadioButton("Standard User");
//		rdoStandard.setSelected(true);;
//		buttonGroup.add(rdoStandard);
//		c.gridx = 0;
//		c.gridy = 0;
////		c.gridwidth = ;
//		optionPanel.add(rdoStandard, c);
		
//		rdoAdmin = new JRadioButton("Admin User");
//		buttonGroup.add(rdoAdmin);
//		c.gridx = 0;
//		c.gridy = 1;
////		c.gridwidth = 3;
//		optionPanel.add(rdoAdmin, c);
		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(btnCancel);
		buttonPanel.add(btnLogin);
		
//		getContentPane().add(optionPanel, BorderLayout.PAGE_START);
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
		
		pack();
		setResizable(false);
	}
}

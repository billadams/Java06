package bestbooks.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class LoginDialog extends JDialog
{
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JLabel lblLoginAdminCredentials;
	private JLabel lblLoginStandardCredentials;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JButton btnLogin;
	private JButton btnCancel;
	
	public LoginDialog(Frame frame, String title, boolean modal)
	{
		super(frame, title, modal);
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 0, 5);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		add(buildEntryPanel(c), BorderLayout.CENTER);
		add(buildButtonPanel(c, frame), BorderLayout.PAGE_END);
		
		pack();
		setResizable(false);
	}
	
	private JPanel buildEntryPanel(GridBagConstraints c)
	{
		JPanel panel = new JPanel(new GridBagLayout());
		
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
		
		return panel;
	}
	
	private JPanel buildButtonPanel(GridBagConstraints c, Frame frame)
	{
		JPanel panel = new JPanel();
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		panel.add(btnCancel);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				String username = txtUsername.getText();
				String password = txtPassword.getText();
				((BookManagerFrame) frame).setDidSubmitiLogin(true);
				
				if ((Login.authenticateUser(username, password, true)) && (username.equals("admin") && password.equals("admin")))
				{
					((BookManagerFrame) frame).buildAdminForm();
					dispose();
				}
				else if ((Login.authenticateUser(username, password, false)) && (username.equals("default") && password.equals("default")))
				{
					((BookManagerFrame) frame).buildStandardForm();
					dispose();
				}			
			}
		});
		this.getRootPane().setDefaultButton(btnLogin);
		panel.add(btnLogin);
		
		return panel;
	}
}

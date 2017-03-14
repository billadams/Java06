package bestbooks.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
//import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
//import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class ReceiptDialog extends JDialog
{
	private JButton btnOkay;
	
	public ReceiptDialog(Frame frame, boolean modal, String orderDetails)
	{
		super(frame, "Order Receipt", modal);
		
		add(buildReceiptPanel(orderDetails), BorderLayout.CENTER);
		add(buildButtonPanel(), BorderLayout.PAGE_END);

		pack();
		setResizable(false);
	}
	
	private JPanel buildReceiptPanel(String orderDetails)
	{
		JPanel panel = new JPanel();
		
		JTextArea txtDisplay = new JTextArea();
		txtDisplay.setDisabledTextColor(Color.BLACK);
		txtDisplay.setEnabled(false);
		txtDisplay.setBorder(BorderFactory.createCompoundBorder(
				txtDisplay.getBorder(), 
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		txtDisplay.setFont( new Font("monospaced", Font.PLAIN, 12) );
		txtDisplay.setSize(300, 500);
		txtDisplay.setText(orderDetails);
		panel.add(txtDisplay);
		
		return panel;
	}
	
	private JPanel buildButtonPanel()
	{
		JPanel panel = new JPanel();
		
		btnOkay = new JButton("OK");
		btnOkay.addActionListener((ActionEvent) ->
		{
			dispose();
		});
		this.getRootPane().setDefaultButton(btnOkay);
		panel.add(btnOkay);
		
		return panel;
	}
}

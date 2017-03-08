package bestbooks.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import bestbooks.business.Book;

@SuppressWarnings("serial")
public class BookForm extends JDialog
{
	private JTextField codeField;
	private JTextField descriptionField;
	private JTextField priceField;
	private JButton confirmButton;
	private JButton cancelButton;
	
	private Book book = new Book();
	
	public BookForm(Frame parent, String title, boolean modal)
	{
		super(parent, title, modal);
		initComponents();
	}
	
	public BookForm(Frame parent, String title, boolean modal, Book book)
	{
		this(parent, title, modal);
		this.book = book;
		confirmButton.setText("Save");
		codeField.setText(book.getCode());
		descriptionField.setText(book.getDescription());
		priceField.setText(Double.toString(book.getPrice()));
	}
	
	private void initComponents()
	{
		codeField = new JTextField();
		descriptionField = new JTextField();
		priceField = new JTextField();
		cancelButton = new JButton();
		confirmButton = new JButton();
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		Dimension shortField = new Dimension(100, 20);
		Dimension longField = new Dimension(300, 20);
		codeField.setPreferredSize(shortField);
		codeField.setMinimumSize(shortField);
		priceField.setPreferredSize(shortField);
		priceField.setMinimumSize(shortField);
		descriptionField.setPreferredSize(longField);
		descriptionField.setMinimumSize(shortField);
		
		cancelButton.setText("Cancel");
		cancelButton.addActionListener((ActionEvent) -> {
//			confirmButtonActionPerformed();
		});
		
		JPanel bookPanel = new JPanel();
		bookPanel.setLayout(new GridBagLayout());
		bookPanel.add(new JLabel("Product Code:"),
				getConstraints(0, 0, GridBagConstraints.LINE_END));
		bookPanel.add(codeField,
				getConstraints(1, 0, GridBagConstraints.LINE_START));
		bookPanel.add(new JLabel("Description:"),
				getConstraints(0, 1, GridBagConstraints.LINE_END));
		bookPanel.add(descriptionField,
				getConstraints(1, 1, GridBagConstraints.LINE_START));
		bookPanel.add(new JLabel("Price:"),
				getConstraints(0, 2, GridBagConstraints.LINE_END));
		bookPanel.add(priceField,
				getConstraints(1, 2, GridBagConstraints.LINE_START));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);
		
		setLayout(new BorderLayout());
		add(buttonPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		pack();
	}
	
	private GridBagConstraints getConstraints(int x, int y, int anchor)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 0, 5);
		c.gridx = x;
		c.gridy = y;
		c.anchor = anchor;
		
		return c;	
	}
}

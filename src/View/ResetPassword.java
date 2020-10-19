package View;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Controller.EmployeeHandler;
import Model.Employee;

import javax.swing.JSeparator;

public class ResetPassword extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private String IDEmployee;
	/**
	 * Launch the application.
	 * @param name 
	 */
	//menampilkan view reset password telah berhasil dan memberitahu password employee sekarang
	public ResetPassword(String id, String name) {
		this.IDEmployee = id;

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblInsertProduct = new JLabel("Password has been reset");
			lblInsertProduct.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
			lblInsertProduct.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblInsertProduct.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblInsertProduct, BorderLayout.NORTH);
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new GridLayout(2, 2, 0, 0));
			{
				JLabel lblIdEmployee = new JLabel("ID Employee:");
				panel.add(lblIdEmployee);
			}
			{
				JLabel lblid = new JLabel(id);
				panel.add(lblid);
			}
			{
				JLabel lblUsername_1 = new JLabel("Username:");
				panel.add(lblUsername_1);
			}
			{
				JLabel lblusername = new JLabel(name);
				panel.add(lblusername);
			}
		}
		{
			JSeparator separator = new JSeparator();
			contentPanel.add(separator, BorderLayout.WEST);
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.SOUTH);
			panel.setLayout(new GridLayout(2, 1, 0, 0));
			{
				JLabel lblThisIsnames = new JLabel("This is "+name+" new password. Do not forget it again!");
				lblThisIsnames.setFont(new Font("Tahoma", Font.BOLD, 11));
				panel.add(lblThisIsnames);
			}
			{
				Employee x=EmployeeHandler.resetPassword(Integer.parseInt(id));
				JLabel lblnewPassword = new JLabel(x.getPassword());
				lblnewPassword.setHorizontalAlignment(SwingConstants.CENTER);
				lblnewPassword.setFont(new Font("Tahoma", Font.PLAIN, 30));
				panel.add(lblnewPassword);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				//ketika ok ditekan berarti employee telah melihat password barunya dan
				//sekarang kembali ke menu manage employee
				JButton okButton = new JButton("Ok");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
						EmployeeHandler.viewManageEmployeeForm();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}

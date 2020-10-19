package View;
import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import Controller.EmployeeHandler;
import Model.Employee;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import com.toedter.calendar.JDateChooser;

public class InsertEmployee extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private JTextField txtUsername;
	private JSpinner txtSalary;
	private JComboBox comboBox;
	private JDateChooser dateChooser;

	//Insert employee adalah view yang digunakan untuk melakukan insert ke table employee
	//untuk melakukan insert, employee harus mengisi data berupa name, role, username, DOB dan juga salary
	//setelah itu akan mengenerate password yang akan digunakan untuk login employee tersebut.
	public InsertEmployee() {

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblInsertProduct = new JLabel("Insert Employee");
			lblInsertProduct.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
			lblInsertProduct.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblInsertProduct.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblInsertProduct, BorderLayout.NORTH);
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new GridLayout(5, 2, 0, 10));
			{
				JLabel lblName = new JLabel("Name:");
				panel.add(lblName);
			}
			{
				txtName = new JTextField();
				panel.add(txtName);
				txtName.setColumns(10);
			}
			{
				JLabel lblRole = new JLabel("Role:");
				panel.add(lblRole);
			}
			{
				 comboBox = new JComboBox();
				comboBox.setModel(new DefaultComboBoxModel(new String[] {"Manager","Storage Manager", "Human Resource Manager", "Promo Manager", "Cashier"}));
				panel.add(comboBox);
			}
			{
				JLabel lblUsername = new JLabel("Username:");
				panel.add(lblUsername);
			}
			{
				txtUsername = new JTextField();
				panel.add(txtUsername);
				txtUsername.setColumns(10);
			}
			{
				JLabel lblDOB = new JLabel("DOB:");
				panel.add(lblDOB);
			}
			{
				dateChooser = new JDateChooser();
				panel.add(dateChooser);
			}
			{
				{
					JLabel lblSalary = new JLabel("Salary:");
					panel.add(lblSalary);
				}
				{
					SpinnerModel value=new SpinnerNumberModel(10000,1,100000000,10000);
					txtSalary = new JSpinner(value);
					panel.add(txtSalary);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				//insert akan melakukan insert ke table employee sesuai dengan data yang dibutuhkan
				JButton okButton = new JButton("Insert");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {


						String role=comboBox.getSelectedItem().toString();
						int roleid=0;
						if(role.equals("Manager"))
						{
							roleid=5;
						}
						if(role.equals("Store Manager"))
						{
							roleid=3;
						}
						if(role.equals("Human Resource Manager"))
						{
							roleid=4;
						}
						if(role.equals("Promo Manager"))
						{
							roleid=6;
						}
						if(role.equals("Cashier"))
						{
							roleid=2;
						}
						try {

							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String date = sdf.format(dateChooser.getDate());
							Employee x=EmployeeHandler.addEmployee(roleid, txtName.getText(), txtUsername.getText(), Date.valueOf(date),Integer.parseInt(txtSalary.getValue().toString()));
							if(x!=null)
							{
								//insert berjalan dengan normal dan username dan juga password yang telah digenerate secara otomatis tersebut akan ditampilkan.
							JOptionPane.showMessageDialog(null, x.getUsername()+" is added to employee list with password number: "+x.getPassword()+". Do not forget this password");
							dispose();
							EmployeeHandler.viewManageEmployeeForm();
							}
							else
							{
								//Jika terdapat data yang tidak diisi atau memiliki value yang salah maka akan menampilkan error
								JOptionPane.showMessageDialog(null, "There is invalid data, please enter valid data!");
							}
						} catch (Exception e) {
							// TODO: handle exception
							//Jika terdapat data yang tidak diisi atau memiliki value yang salah maka akan menampilkan error
							JOptionPane.showMessageDialog(null, "There is invalid data, please enter valid data!");
						}

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				//cancel akan menghapus view ini dan mengembalikan ke view sebelumnya
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
			}
		}
	}

}

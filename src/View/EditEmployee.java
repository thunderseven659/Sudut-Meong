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
import javax.swing.JFormattedTextField;
import com.toedter.calendar.demo.DateChooserPanelBeanInfo;
import com.toedter.calendar.JDateChooser;

public class EditEmployee extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField name;
	private JTextField usename;
	private String IDEmployee;
	private JSpinner txtSalary;
	private JComboBox comboBox = new JComboBox();
	private JDateChooser dateChooser;

	//Edit employee adalah view yang berguna untuk melakuan edit suatu employee
	//didalamnya terdapat nama, role, username, salary, dan DOB yang bisa diubah valuenya untuk mengupdate employee
	public EditEmployee(int id, String name2, String username, Date dOB, int salary) {
		this.IDEmployee = Integer.toString(id);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblInsertProduct = new JLabel("Update Employee");
			lblInsertProduct.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
			lblInsertProduct.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblInsertProduct.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblInsertProduct, BorderLayout.NORTH);
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new GridLayout(6, 2, 0, 10));
			{
				JLabel lblIdEmployee = new JLabel("ID Employee:");
				panel.add(lblIdEmployee);
			}
			{
				JLabel lblidBarang = new JLabel(IDEmployee);
				panel.add(lblidBarang);
			}
			{
				JLabel lblNamaRole = new JLabel("Role Employee:");
				panel.add(lblNamaRole);
			}
			{
				
				comboBox.setModel(new DefaultComboBoxModel(new String[] {"Manager","Storage Manager", "Human Resource Manager", "Promo Manager", "Cashier"}));
				panel.add(comboBox);
			}
			{
				JLabel lblName = new JLabel("Name:");
				panel.add(lblName);
			}
			{
				name = new JTextField();
				panel.add(name);
				name.setColumns(10);
				name.setText(name2);
			}
			{
				JLabel lblUsername = new JLabel("Username:");
				panel.add(lblUsername);
			}
			{
				usename = new JTextField();
				usename.setText(username);
				panel.add(usename);
				usename.setColumns(10);
			}
			{
				JLabel lblDob = new JLabel("DOB:");
				panel.add(lblDob);
			}
			{
				dateChooser = new JDateChooser();
				dateChooser.setDate(dOB);
				panel.add(dateChooser);
			}
			{
				JLabel lblSalary = new JLabel("Salary:");
				panel.add(lblSalary);
			}
			{
				SpinnerModel value=new SpinnerNumberModel(salary,1,100000000,10000);
				txtSalary = new JSpinner(value);
				panel.add(txtSalary);

			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Update");
				okButton.addActionListener(new ActionListener() {
					//jika button update diklik akan mensubmit data yang sudah diisikan didalam edit view
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
			
						String username=String.valueOf(usename.getText());
						String names=String.valueOf(name.getText());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String date = sdf.format(dateChooser.getDate());
						Date DOB=Date.valueOf(date);
						int salary=Integer.parseInt(txtSalary.getValue().toString());		
						Employee x=EmployeeHandler.updateEmployee(id,roleid , names, username, DOB, salary);	
						if(x==null)
						{
							//jika terdapat data yang invalid didalam view maka akan ditampilkan error
							JOptionPane.showMessageDialog(null, "There is invalid data, please enter valid data!");
						}
						else
						{		
							//jika update sukses maka akan mengembalikan view ke manage employee form
							dispose();
							EmployeeHandler.viewManageEmployeeForm();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				//cancel akan menghapus view ini dan mengembalikan ke view sebelumnya
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
			}
		}
	}

}

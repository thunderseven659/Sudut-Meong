package View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Controller.EmployeeHandler;
import Controller.RoleHandler;
import Model.Employee;
import Model.Role;
import Model.Session;

import java.awt.event.MouseAdapter;

public class EmployeeView extends JFrame implements MouseListener{

	JPanel paneltop,panelcenter,panelbottom,panelinput;
	private JPanel panelbottom_1;
	JTable table;
	JLabel title,titleinput,id,namabarang,hargabarang;
	JTextField idinput, qty;
	JButton insertbutton;
	JMenuBar menubar;
	JMenu menu,account;
	JMenuItem logout;
	private JPanel panel;
	private JButton btnUpdate;
	private JButton btnResetPassword;
	private JButton btnFireEmployee;
	private JButton btnNewEmployee;
	private static EmployeeView employeeView;
	//getinstance adalah function yang digunakan untuk mengconstruct 1 employee view tetapi untuk selanjutnya
	//function hanya akan memanggil employee view yang tadi sudah di construct
	public static synchronized EmployeeView getInstance()
	{
		
		if(employeeView==null)
		{

			return employeeView=new EmployeeView();
		}
		else
		{
			employeeView.setVisible(true);
			return employeeView;
		}

	}

	public EmployeeView() {
		setSize(665,470);
		setDefaultCloseOperation(EXIT_ON_CLOSE);


		paneltop = new JPanel(new BorderLayout());
		panelcenter = new JPanel();
		panelcenter.setLayout(new FlowLayout());
		panelinput = new JPanel(new GridLayout(6,2));
		panelinput.setAlignmentY(TOP_ALIGNMENT);
		panelbottom = new JPanel();
		
		menubar = new JMenuBar();
		menu = new JMenu("< Back");
		menu.addMouseListener(this);
		account = new JMenu("Hello, "+Session.getEmployee().getName());
		menubar.add(menu);
		menubar.add(Box.createHorizontalGlue());
		menubar.add(account);
		paneltop.add(menubar, BorderLayout.NORTH);
		
		title = new JLabel("Manage Employee");
		title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));
		title.setFont (title.getFont ().deriveFont (20.0f));
		paneltop.add(title, BorderLayout.CENTER);
		

		table = new JTable();
		refresh();
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600,260));
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		panelcenter.add(scroll);
		
		
		
		panelbottom_1 = new JPanel(new BorderLayout());
	
		getContentPane().add(paneltop, BorderLayout.NORTH);
		getContentPane().add(panelcenter, BorderLayout.CENTER);
		getContentPane().add(panelbottom_1, BorderLayout.SOUTH);
		
		panel = new JPanel();
		panelbottom_1.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//memvalidasi jika suatu row telah dipilih dalam JTable
				if(table.getSelectedRow() != -1) {
					//jika row telah dipilih maka akan memanggil view edit employee yang digunakan untuk mengedit
					//data employee
					int row = table.getSelectedRow();				
					int id = (int)table.getValueAt(row, 0);
					String name=(String)table.getValueAt(row, 2);
					String username=(String)table.getValueAt(row, 3);
					SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
					String date=(String)table.getValueAt(row, 5);
					java.util.Date d = null;
					try {
						d = sdf.parse(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sdf.applyPattern("yyyy-MM-dd");
					String newDate=sdf.format(d);
					int salary=(int)table.getValueAt(row, 7);
					EmployeeHandler.viewEditEmployee(id, name, username, Date.valueOf(newDate), salary);

			
				}
				else {
					//error yang akan keluar jika button ditekan tanpa memilih employee yang akan di update
					JOptionPane.showMessageDialog(null, "Please select an Employee");
				}
			}
			
		});
		panel.add(btnUpdate);
		
		btnResetPassword = new JButton("Reset Password");
		btnResetPassword.addMouseListener(new MouseAdapter() {
			@Override
			//button reset password digunakan untuk mereset password employee
			public void mouseClicked(MouseEvent arg0) {
				//memvalidasi jika suatu row telah dipilih dalam JTable
				if(table.getSelectedRow() != -1) {
					//jika row telah dipilih maka akan memanggil view reset password untuk mereset password employee yang dipilih
					String name = table.getModel().getValueAt(table.getSelectedRow(), 3).toString();
					String id = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
					int confirmation = JOptionPane.showConfirmDialog(null, "Do you want to reset "+name+"'s password?");
					if(confirmation == JOptionPane.YES_OPTION) {
						EmployeeHandler.viewResetPassword(id, name);
						
					}
				}
				else {
					//error yang terjadi jika belum memilih row
					JOptionPane.showMessageDialog(null, "Please select an Employee");
				}
			}
		});
		panel.add(btnResetPassword);
		
		btnFireEmployee = new JButton("Fire Employee");
		//button fire employee adalah button untuk mengubah status seseorang employee dari active menjadi inactive
		btnFireEmployee.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//memvalidasi jika suatu row telah dipilih dalam JTable
				if(table.getSelectedRow() != -1) {
					//jika row sudah dipilih terdapat pertanyaan apakah anda yakin untuk memecat nama dengan id berikut
					String name = table.getModel().getValueAt(table.getSelectedRow(), 3).toString();
					String id = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
					String status=table.getModel().getValueAt(table.getSelectedRow(), 6).toString();
					int confirmation = JOptionPane.showConfirmDialog(null, "Do you want to fire "+name+" with ID "+id+"?");
					//jika pilihannya iya
					if(confirmation == JOptionPane.YES_OPTION) {
						//terdapat validasi jika statusnya sudah inactive maka dia tidak usah mengubah apa-apa
						if(status.equals("Inactive"))
						{
							//error yang mengatakan akun tersebut sudah inactive
							JOptionPane.showMessageDialog(null, name+" is already Inactive.");
						}
						else
						{
							//status account tersebut masih active dan akan diubah menjadi inactive
							EmployeeHandler.fireEmployee(Integer.parseInt(id));
							JOptionPane.showMessageDialog(null, name+" is set to Inactive. Do not forget to say goodbye");
							refresh();
						}
					}
				}
				else {
					//error yang terjadi jika belum memilih row
					JOptionPane.showMessageDialog(null, "Please select an employee");
				}
			}
		});
		panel.add(btnFireEmployee);
		//button new employee adalah button untuk melakukan insert employee baru
		btnNewEmployee = new JButton("New Employee");
		btnNewEmployee.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//akan ditampilkan view insert employee yang baru
				EmployeeHandler.viewInsertEmployee();

			}
		});
		panel.add(btnNewEmployee);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	//merefresh JTable dengan data yang ada di database
	public void refresh()
	{
		String[] columnname = {"ID Employee","Role","Name","Username","Password","DOB","Status","Salary"};
		DefaultTableModel model = new DefaultTableModel(columnname,0)
		{
		    public boolean isCellEditable(int row, int column)
		    {
		      return false;//This causes all cells to be not editable
		    }
		};
		Vector<Employee> data=EmployeeHandler.getAllEmployee();
		for(int i=0;i<data.size();i++)
		{
			Role role=RoleHandler.getRole(data.get(i).getRoleId());
			Vector<Object> row=new Vector<Object>();
			row.add(data.get(i).getEmployeeId());
			row.add(role.getName());
			row.add(data.get(i).getName());
			row.add(data.get(i).getUsername());
			row.add(data.get(i).getPassword());
			Date date=data.get(i).getDOB();
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
			row.add(sdf.format(date));
			row.add(data.get(i).getStatus());
			row.add(data.get(i).getSalary());
			model.addRow(row);
		}
	table.setModel(model);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == menu){
			EmployeeHandler.viewRole(Session.getEmployee());
			dispose();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

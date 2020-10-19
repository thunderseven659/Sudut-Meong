package View;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Controller.CartHandler;
import Controller.EmployeeHandler;
import Controller.ProductHandler;
import Controller.RoleHandler;
import Controller.TransactionHandler;
import Controller.VoucherHandler;
import Model.Employee;
import Model.Role;
import Model.Session;

public class RoleView extends JFrame implements MouseListener{
	JLabel hellomessage,please,name,role;
	JPanel paneltop, panelcenter, panelbottom;
	JTextField username;
	JPasswordField password;
	JButton act,act2,act3,act4,act5;
	JMenuBar menubar;
	JMenu menu;
	//pada role view akan ditampilkan button untuk ke view selanjutnya sesuai dengan role yang telah dipilih
	//disini juga ditampilkan name dan role yang dimiliki oleh employee setelah login
	public RoleView() {
		Employee employee=Session.getEmployee();
		Role x=RoleHandler.getRole(employee.getRoleId());
		setSize(600,240);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		paneltop = new JPanel();
		panelcenter = new JPanel();
		panelbottom = new JPanel();
		paneltop.setLayout(new BorderLayout());
		panelcenter.setLayout(new GridLayout(2,1));
		paneltop.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		panelcenter.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		panelbottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		
		hellomessage = new JLabel("Welcome to Sudut Meong");
		hellomessage.setHorizontalAlignment(SwingConstants.CENTER);
		hellomessage.setFont (hellomessage.getFont ().deriveFont (21.0f));
		paneltop.add(hellomessage, BorderLayout.CENTER);
		
		name = new JLabel("Name: "+employee.getName());
		name.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		panelcenter.add(name);
		menubar = new JMenuBar();
		menu = new JMenu("< Logout");
		menu.addMouseListener(this);
		menubar.add(menu);
		paneltop.add(menubar, BorderLayout.NORTH);
		
		role = new JLabel("Role: "+x.getName());
		role.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		panelcenter.add(role);
		
		act = new JButton("Shopping Cart");
		act2 = new JButton("Manage Voucher");
		act3 = new JButton("Manage Employee");
		act4 = new JButton("Manage Product");
		act5 = new JButton("Transaction Report");
		act.addMouseListener(this);
		act2.addMouseListener(this);
		act3.addMouseListener(this);
		act4.addMouseListener(this);
		act5.addMouseListener(this);
		//memasukkan button sesuai dengan role yang dimiliki oleh employee
		if(x.getName().equals("Manager"))
		{
			panelbottom.add(act3);
			panelbottom.add(act5);
		}
		else if(x.getName().equals("Cashier"))
		{
			panelbottom.add(act);
		}
		else if(x.getName().equals("StorageManagement"))
		{
			panelbottom.add(act4);
		}
		else if(x.getName().equals("HumanResource"))
		{
			panelbottom.add(act3);
		}
		else if(x.getName().equals("PromoManagement"))
		{
		
			panelbottom.add(act2);
		}

		add(paneltop, BorderLayout.NORTH);
		add(panelcenter, BorderLayout.CENTER);
		add(panelbottom, BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		
		
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == menu) {
			//Mengembalikan view ke tampilan login awal dan menghapus view ini
			EmployeeHandler.viewLogin();
			this.dispose();
		}
		else if (e.getSource() == act) {
			//memindahkan view ke add to cart form dan menghapus view ini
			CartHandler.viewAddToCartForm();
			this.dispose();
		}
		else if (e.getSource() == act2) {
			//memindahkan view ke manage voucher form dan menghapus view ini
			VoucherHandler.viewManageVoucherForm();
			this.dispose();
		}
		else if (e.getSource() == act3) {
			//memindahkan view ke manage employee form dan menghapus view ini
			EmployeeHandler.viewManageEmployeeForm();
			this.dispose();
		}
		else if (e.getSource() == act4) {
			//memindahkan view ke manage product form dan menghapus view ini
			ProductHandler.viewManageProductForm();
			this.dispose();
		}
		else if (e.getSource() == act5) {
			//memindahkan view ke view transaction report form form dan menghapus view ini
			TransactionHandler.viewTransactionReportForm();
			this.dispose();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

package View;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.security.auth.x500.X500Principal;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Controller.EmployeeHandler;
import Model.Employee;
import Model.Session;

public class LoginView extends JFrame implements MouseListener{

	private JPanel contentPane;

	JLabel hellomessage,please,usernamelabel,passwordlabel;
	JPanel paneltop, panelcenter, panelbottom,panelflow;
	JTextField username;
	JPasswordField password;
	JButton login;
	//login view adalah view pertama kali ketika membuka aplikasi
	//disini setiap employee harus memasukkan username dan password miliknya
	//untuk bisa membuka menu sesuai dengan role mereka
	public LoginView() {
		setSize(500,240);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		paneltop = new JPanel();
		panelcenter = new JPanel();
		panelbottom = new JPanel();
		panelflow = new JPanel(new FlowLayout());
		paneltop.setLayout(new BorderLayout());
		GridLayout layout = new GridLayout(2,1);
		panelcenter.setLayout(new GridLayout(2,1));
		paneltop.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		panelcenter.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		panelbottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		
		hellomessage = new JLabel("Login to Sudut Meong");
		hellomessage.setHorizontalAlignment(SwingConstants.CENTER);
		hellomessage.setFont (hellomessage.getFont ().deriveFont (21.0f));
		please = new JLabel("Please fill username and passsword to login");
		please.setHorizontalAlignment(SwingConstants.CENTER);
		paneltop.add(hellomessage, BorderLayout.NORTH);
		paneltop.add(please, BorderLayout.CENTER);
		
		usernamelabel = new JLabel("Username");
		panelflow.add(usernamelabel);
		
		username = new JTextField(15);
		panelflow.add(username);
		panelcenter.add(panelflow);
		
		panelflow = new JPanel(new FlowLayout());
		passwordlabel = new JLabel("Password");
		panelflow.add(passwordlabel);
		
		password = new JPasswordField(15);
		panelflow.add(password);
		panelcenter.add(panelflow);
		
		login = new JButton("Login");
		login.addMouseListener(this);
		login.setBorder(BorderFactory.createEmptyBorder(5, 100, 5, 100));
		panelbottom.add(login);
		
		add(paneltop, BorderLayout.NORTH);
		add(panelcenter, BorderLayout.CENTER);
		add(panelbottom, BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		addMouseListener(this);
		
		setVisible(true);
	}
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == login) {
			//jika tombol login ditekan maka akan mencari data username dan password sudah benar atau belum
			String Username, Password;
			Username=String.valueOf(username.getText());
			Password=String.valueOf(password.getText());
			Employee employee=EmployeeHandler.login(Username, Password);
			if(employee!=null)
			{
				//jika sudah benar akan masuk ke view role
				EmployeeHandler.viewRole(employee);
				this.dispose();
			}
			else
			{
				//jika terjadi error akan diberikan message berupa kesalahan username & password maupun id tersebut sudah didelete
				//atau sudah inactive
				JOptionPane.showMessageDialog(null, "Wrong Username & Password/ID has been deleted",
					      "Error!", JOptionPane.ERROR_MESSAGE);
			}
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

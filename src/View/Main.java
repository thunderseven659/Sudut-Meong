package View;

import javax.swing.JFrame;
import Controller.EmployeeHandler;
//main class atau class pertama kali yang akan dijalankan dan akan memanggil view login
public class Main extends JFrame{
	public Main() {
		EmployeeHandler.viewLogin();
	}
	public static void main(String[] args) {	
		new Main();
	}
	
}

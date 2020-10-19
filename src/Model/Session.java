package Model;

public class Session {
	
	public Session() {
		// TODO Auto-generated constructor stub
	}
	//menampung suatu session oleh employee yang login sehingga tidak harus selalu mencari employee dari database, bisa memanggil employee dari session
	public static Employee employee;
	//getter dan setter dari employee diatas
	public static Employee getEmployee() {
		return employee;
	}
	public static void setEmployee(Employee employee) {
		Session.employee = employee;
	}
	
}

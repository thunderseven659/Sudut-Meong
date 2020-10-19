package Model;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

//model dari employee berisikan employeeId, roleId, name, username, DOB, salary, status, dan password
public class Employee {

	private int employeeId;
	private int roleId;
	private String name;
	private String username;
	private Date DOB;
	private int salary;
	private String status;
	private String password;
	//getter dan setter data yang dibutuhkan
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDOB() {
		return DOB;
	}

	public void setDOB(Date dOB) {
		DOB = dOB;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Employee() {
		// TODO Auto-generated constructor stub
	}
	//melakukan query untuk mendapatkan data seluruh employee
	public Vector<Employee> getAllEmployee()
	{

 
		Vector<Employee> y=new Vector<Employee>();
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");
		ResultSet rs = con.executeQuery("SELECT * FROM Employee");
	
		try {

			while(rs.next()) {
				Employee x=new Employee();
				x.setEmployeeId(rs.getInt("employeeid"));
				x.setRoleId(rs.getInt("roleid"));
				x.setName(rs.getString("name"));
				x.setUsername(rs.getString("username"));
				x.setDOB(rs.getDate("DOB"));
				x.setSalary(rs.getInt("salary"));
				x.setStatus(rs.getString("status"));
				x.setPassword(rs.getString("password"));
				y.add(x);
	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return y;
	}
	//melakukan query untuk mendapatkan data employee berdasarkan employeeID yang diinput
	public Employee getEmployee(int EmployeeId)
	{
		
		Employee x=new Employee();
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");
		ResultSet rs = con.executeQuery("SELECT * FROM Employee");
		int found=0;
		try {
			
			while(rs.next()) {
				x.setEmployeeId(rs.getInt("employeeid"));
				x.setRoleId(rs.getInt("roleid"));
				x.setName(rs.getString("name"));
				x.setUsername(rs.getString("username"));
				x.setDOB(rs.getDate("DOB"));
				x.setSalary(rs.getInt("salary"));
				x.setStatus(rs.getString("status"));
				x.setPassword(rs.getString("password"));
				if(x.getEmployeeId()==EmployeeId)
				{

					found=1;
					break;
				}
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(found==1)
		{

			return x;	
		}
		else
		{
			return null;
		}
		
	}
	//melakukan query untuk melakukan insert di table employee
	public Employee insertEmployee(int employeeId,int roleId, String name, String username,
			Date DOB,int salary,String status,String password){
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");
		Employee x=new Employee(); 
		x.setEmployeeId(employeeId);
		x.setRoleId(roleId);
		x.setName(name);
		x.setUsername(username);
		x.setDOB(DOB);
		x.setSalary(salary);
		x.setStatus(status);
		x.setPassword(password);
		String query = "INSERT INTO employee VALUES ("+employeeId+"," + roleId + ",'"+ name + "'"
				+ ",'" + username + "','"+DOB+"',"+salary+",'"+status+"','"+password+"'"+")";
		con.executeUpdate(query);
		return x;
	}
	//melakukan query untuk melakukan update di table employee
	public Employee updateEmployee(int employeeId,int roleId, String name, String username,
			Date DOB,int salary,String status,String password) {
		Employee x= new Employee();
		x.setEmployeeId(employeeId);
		x.setRoleId(roleId);
		x.setName(name);
		x.setUsername(username);
		x.setDOB(DOB);
		x.setSalary(salary);
		x.setStatus(status);
		x.setPassword(password);
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");

			String query = "update employee set Roleid=" + roleId + ",name='"+ name + "'"
				+ ",username='" + username + "',DOB='"+DOB+"',salary="+salary+
				",status='"+status+"',password='"+password+"' where Employeeid="+employeeId;

			con.executeUpdate(query);
			return x;
	}
}

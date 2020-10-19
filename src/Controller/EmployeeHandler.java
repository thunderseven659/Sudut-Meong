package Controller;

import java.sql.Date;
import java.util.Random;
import java.util.Vector;

import Model.Employee;
import Model.Role;
import Model.Session;
import View.CartView;
import View.EditEmployee;
import View.EmployeeView;
import View.InsertEmployee;
import View.LoginView;
import View.ResetPassword;
import View.RoleView;

public class EmployeeHandler {
	private static Employee model=new Employee();

	public EmployeeHandler() {
		
	}
	//mendapatkan employee berdasarkan employeeId dari model employee
	public static Employee getEmployee(int employeeID) {
		return model.getEmployee(employeeID);
	
	}
	//mendapatkan seluruh list vector employee
	public static Vector<Employee> getAllEmployee(){
	
		Vector<Employee> x=model.getAllEmployee();
		return x;
	}
	//menambahkan employee baru ke dalam model table
	public static Employee addEmployee(int roleId,String name, String username,Date DOB, int salary)
	{

		Vector<Role> roles=RoleHandler.getAllRole();
		int counter=0;
		int counter2=0;
		Vector<Employee> y=getAllEmployee();
		long millis=System.currentTimeMillis();
		Date now=new Date(millis);
		Employee x=new Employee();
		//validasi apakah role inputan exist
		for(int s=0;s<roles.size();s++)
		{
			if(roles.get(s)==null)
			{
				break;
			}
			if(roles.get(s).getRoleID()==roleId)
			{
				counter2=1;
				break;
			}
		}
		int id=y.size()+1;
		//mencari id yang dapat digunakan sebagai penampung employee yang baru
		for(int s=0;s<y.size();s++)
		{

			if(y.get(s)==null)
			{
				id=s;
			}
			if(y.get(s).getUsername().equalsIgnoreCase(username))
			{
				counter=1;
				break;
			}
		}
		Random random=new Random();
		model.setEmployeeId(id);
		model.setRoleId(roleId);
		model.setName(name);
		model.setUsername(username);
		model.setDOB(DOB);
		model.setSalary(salary);
		model.setStatus("Active");
		int password=0;
		//mengenerate password employee
		while(password<1000)
		{
			password=random.nextInt(10000);			
		}
		model.setPassword(Integer.toString(password));
		//validasi username tidak boleh sama, name dan username tidak boleh empty 
		//validasi salary harus lebih besar dari 0 dan DOB tidak bisa dimasa depan
		if(x!=null&&salary>0&&now.getTime()>DOB.getTime()&&counter==0&&counter2==1&&!name.isEmpty()&&!username.isEmpty())
		{
	
			model.insertEmployee(id, roleId, name, username, DOB, salary,"Active", Integer.toString(password));	
			return model;
		}
		else
		{

			return null;
		}

	}
	//mengupdate employee untuk mengubah data employee untuk employeeId yang dimasukkan
	public static Employee updateEmployee(int employeeId,int roleId,String name,String username,Date DOB, int salary)
	{
		
		RoleHandler role=new RoleHandler();
		Vector<Role> roles=RoleHandler.getAllRole();

		int counter=0,counter2=0;
		Employee x=new Employee();
		x=x.getEmployee(employeeId);
		Vector<Employee> y=getAllEmployee();
		long millis=System.currentTimeMillis();
		Date now=new Date(millis);
		//validasi apakah role inputan exist
		for(int i=0;i<roles.size();i++)
		{
			if(roles.get(i)==null)
			{
				break;
			}
			if(roles.get(i).getRoleID()==roleId)
			{
				counter2=1;
				break;
			}
		}
		//validasi username tidak boleh sama, name dan username tidak boleh empty
		for(int i=0;i<y.size();i++)
		{
			if(y.get(i)==null)
			{
				break;
			}
			if(y.get(i).getName().equalsIgnoreCase(username)&&y.get(i).getEmployeeId()!=employeeId&&!name.isEmpty()&&!username.isEmpty())
			{
				counter=1;
			}
		}
		//validasi salary harus lebih besar dari 0 dan DOB tidak bisa dimasa depan dan employee harus exist untuk diupdate
		if(x!=null&&salary>0&&now.getTime()>DOB.getTime()&&counter==0&&counter2==1&&model.getEmployee(employeeId)!=null)
		{
			model.setEmployeeId(employeeId);
			model.setRoleId(roleId);
			model.setName(name);
			model.setUsername(username);
			model.setDOB(DOB);
			model.setSalary(salary);
			model.setStatus("Active");
			model.updateEmployee(employeeId, roleId, name, username, DOB, salary, x.getStatus(), x.getPassword());
			return model;
		}
		else
		{
			return null;
		}

	}
	//untuk melakukan reset password terhadap employee
	public static Employee resetPassword(int employeeId)
	{
		Random random=new Random();

		int password=0;
		//validasi minimal password adalah 4 digit dan employee harus exist
		while(password<1000)
		{
			password=random.nextInt(10000);			
		}
		Employee x=new Employee();
		x=x.getEmployee(employeeId);
		if(x!=null)
		{
	
			model=model.updateEmployee(employeeId, x.getRoleId(), 
					x.getName(), x.getUsername(), x.getDOB(),
					x.getSalary(), x.getStatus(), Integer.toString(password));
		}

		return model;
	}
	//untuk memecat employee mengganti status employee dari active menjadi inactive
	public static Employee fireEmployee(int employeeId)
	{
		Employee x=new Employee();
		x=x.getEmployee(employeeId);
		//validasi employeeId harus exist
		if(x!=null)
		{
			model.updateEmployee(employeeId, x.getRoleId(), 
					x.getName(), x.getUsername(), x.getDOB(),
					x.getSalary(), "Inactive", x.getPassword());
		}
		return model;
	}
	//untuk masuk ke setiap form sesuai tugas employee maka employee harus login dengan username dan password,
	//jika benar mereka akan diambil roleId nya sehingga bisa masuk ke view peran mereka masing-masing
	public static Employee login(String username, String password)
	{
		Vector<Employee> x=model.getAllEmployee();
		Employee y;
		//validasi username dan password harus sama dan validasi jika status inactive tidak bisa melakukan login
		for(int i=0;i<x.size();i++)
		{
			y=x.get(i);

			if(username.equalsIgnoreCase(y.getUsername())&&password.equalsIgnoreCase(y.getPassword()))
			{
				if(y.getStatus().equalsIgnoreCase("inactive"))
				{
					return null;
				}
				return y;
			}
		}
		return null;
	}
	//membuka view manage employee
	public static void viewManageEmployeeForm()
	{
		EmployeeView.getInstance();
		if(EmployeeView.getInstance()!=null)
		{
			EmployeeView.getInstance().refresh();
		}
	}
	//membuka view reset password
	public static void viewResetPassword(String id, String name)
	{
		ResetPassword resetPassword=new ResetPassword(id,name);
		resetPassword.setVisible(true);
	}
	//membuka view insert employee
	public static void viewInsertEmployee()
	{
		InsertEmployee insertEmployee=new InsertEmployee();
		insertEmployee.setVisible(true);
	}
	//membuka view edit employee
	public static void viewEditEmployee(int id, String name2, String username, Date dOB, int salary)
	{
		EditEmployee editEmployee=new EditEmployee(id,name2,username,dOB,salary);
		editEmployee.setVisible(true);
	}
	//membuka view login employee
	public static void viewLogin()
	{
		LoginView loginView=new LoginView();
		loginView.setVisible(true);
	}
	//membuka view role yaitu view dimana employee memilih menu berdasarkan role yang dimiliki
	public static void viewRole(Employee employee)
	{
		Session.setEmployee(employee);
		RoleView roleView=new RoleView();
		roleView.setVisible(true);
	}
}

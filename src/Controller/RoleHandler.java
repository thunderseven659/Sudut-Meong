package Controller;
import java.util.Vector;

import Model.Role;

public class RoleHandler {
	private static Role model=new Role();


	public RoleHandler() {
	
	}
	//mengambil data role berdasarkan roleId
	public static Role getRole(int roleID) {
		return model.getRole(roleID);
	
	}
	//mengambil seluruh data vector<Role>
	public static Vector<Role> getAllRole(){
	
		Vector<Role> x=model.getAllRole();
		return x;
	}
	

}

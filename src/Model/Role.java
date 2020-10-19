package Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
//model dari role yang berisikan roleId dan name
public  class Role{

	private int RoleID;
	private String name;
	//getter dan setter dari data diatas
	public int getRoleID() {
		return RoleID;
	}

	public void setRoleID(int roleID) {
		RoleID = roleID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role()
	{
		
	}
	//melakukan query untuk mendapatkan seluruh data role
	public Vector<Role> getAllRole()
	{

		
		Vector<Role> y=new Vector<Role>();
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");
		ResultSet rs = con.executeQuery("SELECT * FROM Role");

		try {

			while(rs.next()) {
				Role x=new Role();
				x.setName(rs.getString("name"));
				x.setRoleID(rs.getInt("roleid"));
				y.add(x);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return y;
	}
	//melakukan query untuk mendapatkan data role berdasarkan roleId
	public Role getRole(int RoleId)
	{
		
		Role x=new Role();
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");
		ResultSet rs = con.executeQuery("SELECT * FROM Role");

		int found=0;
		try {
			
			while(rs.next()) {
				
				x.setName(rs.getString("name"));
				x.setRoleID(rs.getInt("roleid"));
				if(x.getRoleID()==RoleId)
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
}

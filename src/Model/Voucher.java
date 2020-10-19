package Model;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
public class Voucher {
	//model dari voucher yang berisikan voucherId, discount, validDate, status
	private int voucherId;
	private float discount;
	private Date validDate;
	private String status;
	//getter dan setter dari data diatas
	public int getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(int voucherId) {
		this.voucherId = voucherId;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public Date getValidDate() {
		return validDate;
	}
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Voucher() {
		// TODO Auto-generated constructor stub
	}
	//melakukan query untuk mendapatkan seluruh data voucher dari table voucher
	public Vector<Voucher> getAllVoucher()
	{

		Vector<Voucher> y=new Vector<Voucher>();
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");
		ResultSet rs = con.executeQuery("SELECT * FROM Voucher");

		try {

			while(rs.next()) {
				Voucher x=new Voucher();
				x.setVoucherId(rs.getInt("voucherid"));
				x.setDiscount(rs.getFloat("discount"));
				x.setStatus(rs.getString("status"));
				x.setValidDate(rs.getDate("validDate"));
				y.add(x);
	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return y;
	}
	//melakukan query untuk mendapatkan voucher berdasarkan voucherId
	public Voucher getVoucher(int voucherId)
	{
		Voucher x=new Voucher();
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");
		ResultSet rs = con.executeQuery("SELECT * FROM Voucher");
		int found=0;
		try {
			
			while(rs.next()) {
			
				x.setVoucherId(rs.getInt("voucherid"));
				x.setDiscount(rs.getFloat("discount"));
				x.setStatus(rs.getString("status"));
				x.setValidDate(rs.getDate("validDate"));
				if(x.getVoucherId()==voucherId)
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
	//melakukan query untuk menginsert data voucher ke dalam table voucher
	public Voucher insertVoucher(int voucherId,float discount,Date validDate, String status)
	{
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");
		Voucher x=new Voucher(); 
		x.setVoucherId(voucherId);
		x.setDiscount(discount);
		x.setValidDate(validDate);
		x.setStatus(status);
		String query = "INSERT INTO voucher VALUES ("+voucherId+"," + discount + ",'"+ validDate + "'"
				+ ",'" + status +"')";
		con.executeUpdate(query);
		return x;
	}
	//melakukan query untuk mengupdate data voucher di dalam table voucher
	public Voucher 	updateVoucher(int voucherId,float discount,Date validDate, String status)
	{
		Voucher x= new Voucher();
		x.setVoucherId(voucherId);
		x.setDiscount(discount);
		x.setValidDate(validDate);
		x.setStatus(status);
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");

			String query = "update voucher set discount='"+ discount + "'"
				+ ",validDate='" + validDate + "',Status='"+status+"' where Voucherid="+voucherId;
	
			con.executeUpdate(query);
			return x;
	}
	//melakukan query untuk mendelete data voucher di dalam table voucher
	public boolean deleteVoucher(int voucherId){
		Voucher x= new Voucher();
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");

		String query = "DELETE FROM Voucher WHERE voucherId = " + voucherId;
		con.executeUpdate(query);
		return true;
	}
}

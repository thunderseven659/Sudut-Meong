package Model;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
//model dari transaction yang berisikan transactionId, purchase_datetime, voucherId, employeeId, paymentType
public class Transaction {
	private int transactionId;
	private Date purchase_datetime;
	private int voucherId;
	private int employeeId;
	private String paymentType;
	//getter dan setter dari data diatas
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public Date getPurchase_datetime() {
		return purchase_datetime;
	}

	public void setPurchase_datetime(Date purchase_datetime) {
		this.purchase_datetime = purchase_datetime;
	}

	public int getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(int voucherId) {
		this.voucherId = voucherId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Transaction() {
		// TODO Auto-generated constructor stub
	}
	//melakukan query untuk mendapatkan seluruh data dari table transaction
	public Vector<Transaction> getAllTransaction()
	{
		Vector<Transaction> y=new Vector<Transaction>();
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");
		ResultSet rs = con.executeQuery("SELECT * FROM Transaction");

		try {

			while(rs.next()) {
				Transaction x=new Transaction();
				x.setTransactionId(rs.getInt("TransactionId"));
				x.setPurchase_datetime(rs.getDate("purchase_datetime"));
				x.setVoucherId(rs.getInt("voucherId"));
				x.setEmployeeId(rs.getInt("EmployeeId"));
				x.setPaymentType(rs.getString("paymentType"));
				y.add(x);

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return y;
	}
	//melakukan query untuk melakukan insert data transaction ke table transaction
	public Transaction addTransaction(int transactionId,Date purchase_datetime,int voucherId, int employeeId, String paymentType) {
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");
		Transaction x=new Transaction(); 
		x.setTransactionId(transactionId);
		x.setPurchase_datetime(purchase_datetime);
		x.setVoucherId(voucherId);
		x.setEmployeeId(employeeId);
		x.setPaymentType(paymentType);
		String query;
		if(voucherId==0)
		{
			 query = "INSERT INTO transaction (transactionId,purchase_datetime,employeeID,paymentType) VALUES ("+transactionId+",'" + purchase_datetime +"'," + employeeId +",'"+paymentType+"')";

		}
		else
		{
			 query = "INSERT INTO transaction VALUES ("+transactionId+",'" + purchase_datetime + "',"+ voucherId 
					+ "," + employeeId +",'"+paymentType+"')";			

		}

		con.executeUpdate(query);
		return x;
	}
	//melakukan query untuk mendapatkan list transaction berdasarkan month dan year yang diinput
	public Vector<Transaction> getTransaction(int month, int year) {
		Vector<Transaction> y=getAllTransaction();
		Vector<Transaction> x=new Vector<Transaction>();

		for(int i=0;i<y.size();i++)
		{

			if(y.get(i)==null)
			{
				break;
			}
			else if(y.get(i).getPurchase_datetime().getMonth()+1==month && y.get(i).getPurchase_datetime().getYear()==year-1900)
			{
			
				x.add(y.get(i));
			}
		}
		return x;
	}


}

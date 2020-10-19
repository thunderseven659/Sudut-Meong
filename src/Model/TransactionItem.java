package Model;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
//model dari transactionItem yang berisikan transactionId, productId, quantity
public class TransactionItem {
	private int transactionId;
	private int productId;
	private int quantity;
	//getter dan setter dari data diatas
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public TransactionItem() {
		// TODO Auto-generated constructor stub
	}
	//melakukan query untuk menginsert data transactionItem ke table transactionItem
	public TransactionItem addTransactionItem(int transactionId, int productId, int quantity)
	{
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");
		TransactionItem x=new TransactionItem();
		x.setTransactionId(transactionId);
		x.setProductId(productId);
		x.setQuantity(quantity);
		String query = "INSERT INTO transactionitem VALUES ("+transactionId+"," + productId + ","+ quantity +")";
		con.executeUpdate(query);
		return x;
	}
	//melakukan query untuk mendapatkan data transactionItem berdasarkan transactionId
	public Vector<TransactionItem> getTransactionItem(int transactionId)
	{
		Vector<TransactionItem> y= new Vector<TransactionItem>(); 
		Vector<TransactionItem> item= new Vector<TransactionItem>(); 
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");
		ResultSet rs = con.executeQuery("SELECT * FROM TransactionItem");

		try {

			while(rs.next()) {
				TransactionItem x=new TransactionItem();
				x.setTransactionId(rs.getInt("TransactionId"));
				x.setProductId(rs.getInt("productId"));
				x.setQuantity(rs.getInt("quantity"));
				y.add(x);

			}
		
			for(int i=0;i<y.size();i++)
			{
				int id=y.get(i).getTransactionId();
				if(id==transactionId)
				{
					TransactionItem x=new TransactionItem();
					x.setTransactionId(y.get(i).getTransactionId());
					x.setProductId(y.get(i).getProductId());
					x.setQuantity(y.get(i).getQuantity());
					item.add(x);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return item;
	}
}

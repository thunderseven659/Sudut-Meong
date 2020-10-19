package Model;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

//model dari product yang berisikan productId, name, description, price, dan stock
public class Product {
	private int productId;
	private String name;
	private String description;
	private int Price;
	private int stock;
	//getter dan setter untuk data diatas
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return Price;
	}

	public void setPrice(int price) {
		Price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Product() {
		// TODO Auto-generated constructor stub
	}
	//melakukan query untuk mendapatkan seluruh data product 
	public Vector<Product> getAllProduct()
	{
		Vector<Product> y= new Vector<Product>();
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");
		ResultSet rs = con.executeQuery("SELECT * FROM Product");
		int counter=0;
		try {

			while(rs.next()) {
				Product x=new Product();
				x.setProductId(rs.getInt("productId"));
				x.setName(rs.getString("name"));
				x.setDescription(rs.getString("description"));
				x.setPrice(rs.getInt("price"));
				x.setStock(rs.getInt("stock"));
				y.add(x);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return y;
	}
	//melakukan query untuk mendapatkan data product berdasarkan productId
	public Product getProduct(int productId)
	{
		Product x=new Product();
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");
		ResultSet rs = con.executeQuery("SELECT * FROM Product");
		int found=0;
		try {
			
			while(rs.next()) {
				x.setProductId(rs.getInt("productId"));
				x.setName(rs.getString("name"));
				x.setDescription(rs.getString("description"));
				x.setPrice(rs.getInt("price"));
				x.setStock(rs.getInt("stock"));
				if(x.getProductId()==productId)
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
	//melakukan query untuk melakukan insert product ke table product
	public Product insertProduct(int productId,String name,String description, int price, int stock)
	{
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");
		Product x=new Product(); 
		String query = "INSERT INTO product VALUES ("+productId+",'" + name + "','"+ description + "'"
				+ "," + price +","+stock+")";
		x.setProductId(productId);
		x.setName(name);
		x.setDescription(description);
		x.setPrice(price);
		x.setStock(stock);
		con.executeUpdate(query);
		return x;
	}
	//melakukan query untuk melakukan update product ke table product
	public Product updateProduct(int productId,String name,String description, int price, int stock)
	{
		Product x= new Product();
		x.setProductId(productId);
		x.setName(name);
		x.setDescription(description);
		x.setPrice(price);
		x.setStock(stock);
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");

			String query = "update product set name='"+ name + "'"
				+ ",description='" + description + "',price="+price+",stock="+stock+" where productId="+productId;
		
			con.executeUpdate(query);
			return x;
	}
	//melakukan query untuk melakukan delete product ke table product
	public boolean  deleteProduct(int productId)
	{
		Product x= new Product();
		Connect con=Connect.getConnection();
		con.executeQuery("USE sudutmeong");

		String query = "DELETE FROM Product WHERE productId = " + productId;
		con.executeUpdate(query);
		return true;
	}
	
}

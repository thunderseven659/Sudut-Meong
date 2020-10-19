package Controller;

import java.util.Vector;

import Model.Product;
import Model.Transaction;
import Model.TransactionItem;
import View.CartView;
import View.EditProduct;
import View.InsertProduct;
import View.ProductView;

public class ProductHandler {
	private static Product model=new Product();
	public ProductHandler() {
		// TODO Auto-generated constructor stub
	}
	//mendapatkan data seluruh vector product
	public static Vector<Product> getAllProduct(){
		Vector<Product> x=model.getAllProduct();
		return x;
	}
	//mendapatkan data vector product berdasarkan productId
	public static Product getProduct(int productId) {
		Product x=model.getProduct(productId);
		return x;
	}
	//menambahkan product baru ke dalam table
	public static Product addProduct(String name, String description, int price, int stock)
	{
		//mencari id yang cocok digunakan sebagai penampung product
		Vector<Product> x=getAllProduct();
		int id=x.size()+1;
		for(int i=0;i<x.size();i++)
		{
			if(x.get(i).getProductId()!=i+1)
			{
				id=i;
			}
		}
		//validasi price harus lebih besar dari 0, stock harus lebih besar dari 0, name dan description tidak boleh kosong
		if(price>0&&stock>0&&!name.isEmpty()&&!description.isEmpty())
		{
		model.setName(name);
		model.setDescription(description);
		model.setPrice(price);
		model.setStock(stock);
		model.insertProduct(id+1, name, description, price, stock);
		return model;
		}
		else
		{
			return null;
		}
	}
	//mengupdate product berdasarkan productId yang dipilih
	public static Product updateProduct(int productId,String name, String description, int price)
	{
		model=getProduct(productId);
		//validasi price harus lebih besar dari 0, product harus exist, name dan description tidak boleh kosong
		if(price>0&&model.getProduct(productId)!=null&&!name.isEmpty()&&!description.isEmpty())
		{
			model.updateProduct(productId, name, description, price, model.getStock());
			return model;
		}
		else
		{
			return null;
		}

	}
	//mendelete product berdasarkan productId yang dipilih
	public static boolean deleteProduct(int productId) {
		if(model.getProduct(productId)!=null)
		{
			Vector<Transaction> transaction=TransactionHandler.getAllTransaction();
			//validasi jika product sudah pernah terjual maka tidak bisa di delete
			//hal ini akan menyebabkan database kehilangan integrity datanya di transactionItem
			for(int i=0;i<transaction.size();i++)
			{
				Vector<TransactionItem> transactionItem=TransactionHandler.getTransactionDetail(transaction.get(i).getTransactionId());
				for(int a=0;a<transactionItem.size();a++)
				{
					if(transactionItem.get(a).getProductId()==productId)
					{
						return false;
					}
				}
			}
			
			model.deleteProduct(productId);
			return true;
		}
		return false;
	}
	//melakukan restock quantity (menambah quantity pada suatu product)
	public static void restock(int productId, int quantity) {
		model=getProduct(productId);
		//validasi quantity untuk restock harus lebih besar dari 0 dan product must exist
		if(quantity>0&&model.getProduct(productId)!=null)
		{
			model.updateProduct(productId, model.getName(), model.getDescription(), model.getPrice(), model.getStock()+quantity);
		}
	}
	//melakukan use stock atau mengurangi stock sesuai dengan banyak quantity yang dibeli ketika setelah melakukan payment
	public static void useStock(int productId, int quantity)
	{
		model=getProduct(productId);
		//validasi quantity untuk digunakan harus lebih besar dari 0, product must exist dan 
		//stock-quantity(jumlah barang tersedia cukup untuk dijual) harus lebih besar atau sama dengan 0
		if(quantity>0&&model.getProduct(productId)!=null&&model.getStock()-quantity>=0)
		{
			model.updateProduct(productId, model.getName(), model.getDescription(), model.getPrice(), model.getStock()-quantity);
		}
	}
	//membuat view manage product form
	public static void viewManageProductForm()
	{
		ProductView.getInstance();
		if(ProductView.getInstance()!=null)
		{
			ProductView.getInstance().refresh();
		}
	}
	//membuat view edit product form
	public static void viewEditProduct(String id,String nama,String description,String harga)
	{
		EditProduct editProduct=new EditProduct(id,nama,description,harga);
		editProduct.setVisible(true);
	}
	//membuat view insert form
	public static void viewInsertProduct()
	{
		InsertProduct insertProduct=new InsertProduct();
		insertProduct.setVisible(true);
	}
}

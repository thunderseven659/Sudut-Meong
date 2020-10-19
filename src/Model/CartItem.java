package Model;

//model dari cartItem berisikan data yang dimiliki oleh sebuah cart yaitu productId dan quantity
public class CartItem {
	private int productId;
	private int quantity;
	//getter dan setter data yang dibutuhkan
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

	public CartItem() {
		// TODO Auto-generated constructor stub
	}
	
}

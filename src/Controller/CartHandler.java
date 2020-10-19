package Controller;

import java.util.Vector;

import Model.CartItem;
import Model.Product;
import View.CartView;
import View.EditCart;
import View.Payment;

public class CartHandler {
	//x sebagai tempat menampung seluruh vector cartItem 
	private static Vector<CartItem> x=new Vector<CartItem>();
	public CartHandler() {
		// TODO Auto-generated constructor stub
	}
	//mendapatkan quantity dari model berdasarkan productId
	public static int getQuantity(int productId)
	{
		int i=0;
		int found=0;
		//validasi jika product id nya ada
		for(i=0;i<x.size();i++)
		{
			
			if(x.get(i).getProductId()==productId)
			{
				found=1;
				break;
			}
		}
		//jika product ada maka akan mereturn quantity nya
		//sedangkan jika tidak ada akan mereturn 0
		if(found==1)
		{
		return x.get(i).getQuantity();
		}
		return 0;
	}
	//melakukan addToCart memvalidasi stock, menambah stock pada cart 
	public static CartItem addToCart(int productId, int quantity)
	{
		if(quantity<=0)
		{
			//validasi jika quantity 0 maka addtocart tidak bisa digunakan
			return null;
		}
		int counter=0;
		Product product=ProductHandler.getProduct(productId);
		CartItem y=new CartItem();
		y.setProductId(productId);
		y.setQuantity(quantity);
		if(quantity>product.getStock())
		{
			//validasi jika quantity yang ingin dibeli lebih besar dari product maka addtocart tidak bisa digunakan
			return null;
		}
		for(int i=0;i<x.size();i++)
		{
			if(x.get(i).getProductId()==productId)
			{
				
				if(x.get(i).getQuantity()+quantity>product.getStock())
				{
					//validasi jika quantity + jumlah yang ada di cart yang ingin dibeli lebih besar dari product maka addtocart tidak bisa digunakan
					return null;
				}
				//melakukan update stock jika stock masih cukup
				updateStock(productId,quantity+x.get(i).getQuantity());
				counter=1;	
				y.setQuantity(quantity+x.get(i).getQuantity());
				return y;
			}
		}
		//validasi jika item belom pernah ada di cart maka akan melakukan add pada cart jika sudah maka akan menambahkan stock di code yang diatas
		if(counter==0)
		{
			x.add(y);
			return y;
		}
		return null;

	}
	//melakukan update pada stock contohnya jika addToCart menambah item ex: 5 addtocart 5 -> 10 maka update stock akan mengubah cart quantity
	//sesuai dengan banyak quantity yang dimasukkan ex : 5 updatestock 10 -> 10
	public static CartItem updateStock(int productId, int quantity)
	{
		int i=0;
		int found=0;
		//validasi jika quantity<=0 maka akan mereturn null
		if(quantity<=0)
		{
			return null;
		}
		//mencari product yang akan di update 
		for(i=0;i<x.size();i++)
		{
			
			if(x.get(i).getProductId()==productId)
			{
				found=1;
				break;
			}
		}
	
		try {
			//validasi quantity yang ingin dibeli tidak melebihi stock
			Product product=ProductHandler.getProduct(productId);
			if(quantity>product.getStock())
			{

				x.get(i).setQuantity(x.get(i).getQuantity());
				return null;
			}
		} catch (Exception e) {
		
			return null;
			// TODO: handle exception
		}
		//jika product ditemukan maka akan mengeset quantity sesuai dengan inputan
		if(found==1)
		{
		x.get(i).setQuantity(quantity);
		return x.get(i);
		}
		return null;
	}
	//untuk mengosongkan cart pada saat payment berhasil maka cart akan dikosongkan
	public static Vector<CartItem> emptyCart()
	{
		x.clear();
		return x;
	}
	//untuk mengambil seluruh cart 
	public static Vector<CartItem> getCart()
	{
		return x;
	}
	//menghitung price harga yang ada didalam cart
	public static int calculatePrice()
	{
		int total=0;
		for(int i=0;i<x.size();i++)
		{
			Product product=ProductHandler.getProduct(x.get(i).getProductId());
			total=total+x.get(i).getQuantity()*product.getPrice();
		}
		return total;
	}
	//mendelete item yang sudah ada di cart
	public static CartItem delete(int productId)
	{
		int i=0;
		int found=0;

		for(i=0;i<x.size();i++)
		{
			
			if(x.get(i).getProductId()==productId)
			{
				found=1;
				break;
			}
		}

		if(found==1)
		{
			CartItem cart=x.get(i);
		x.remove(i);
		return cart;
		}
		return null;
	}
	//membuka view addtocart form
	public static void viewAddToCartForm()
	{
		CartView.getInstance();
		if(CartView.getInstance()!=null)
		{
			CartView.getInstance().refresh();
		}
	}
	//membuka view purchase form / form payment
	public static void viewPurchaseForm()
	{
		Payment payment=new Payment();
		payment.setVisible(true);
	}
	//membuka view edit cart/ untuk melakukan edit pada cart
	public static void viewEditCart(String id)
	{
		EditCart editCart=new EditCart(id);
		editCart.setVisible(true);
	}
}

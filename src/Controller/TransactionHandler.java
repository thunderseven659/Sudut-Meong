package Controller;
import java.sql.Date;
import java.util.Vector;

import Model.CartItem;
import Model.Product;
import Model.Transaction;
import Model.TransactionItem;
import Model.Voucher;
import View.TransactionDetail;
import View.TransactionReport;

public class TransactionHandler {
	private static int change;
	//change merupakan kembalian yang akan ditampilkan setelah payment
	public static int getChange() {
		return change;
	}
	public static void setChange(int change) {
		TransactionHandler.change = change;
	}
	static Transaction transaction=new Transaction();
	static TransactionItem detail=new TransactionItem();
	public TransactionHandler() {
		// TODO Auto-generated constructor stub
	}
	//mendapatkan seluruh transaction yang terjadi
	public static Vector<Transaction> getAllTransaction()
	{
		Vector<Transaction> x=transaction.getAllTransaction();
		return x;
	}
	//melakukan process transaction yaitu membuat transaction baru ke dalam table, mengecek kembalian, mengosongkan cart untuk digunakan pengguna selanjutnya
	public static Transaction processTransaction(int employeeId, String paymentType, String money)
	{
		//validasi jika payment belum dipilih maka akan mereturn null(error)
		if(paymentType==null)
		{
			return null;
		}

		Transaction y=new Transaction();
		Vector<Transaction> x=y.getAllTransaction(); 
		long millis=System.currentTimeMillis();
		Date now=new Date(millis);
		Vector<CartItem> cart=CartHandler.getCart();
		int id=x.size()+1;
		//mencari id yang bisa digunakan untuk menampung transaction
		for(int i=0;i<x.size();i++)
		{
			if(x.get(i).getTransactionId()!=i+1)
			{
				id=i+1;
			}
		}
		//validasi jika voucher tidak ditemukan dan voucher id nya bukan -1 maka akan menampilkan error
		//voucher id -1 menandakan dia tidak menggunakan voucher
		if(VoucherHandler.getVoucher(transaction.getVoucherId())==null&&transaction.getVoucherId()!=-1)
		{
			return null;
		}
		//validasi jika voucher id -1 maka melakukan transaksi tanpa menggunakan voucher
		if(transaction.getVoucherId()==-1)
		{
			try {
				int uang=Integer.parseInt(money);
				//validasi jika uang yang dibayar cukup untuk membayar seluruh barang yang ada di cart
				if(CartHandler.calculatePrice()>uang)
				{
					return null;
				}
				else
				{
					//mengganti kembalian yang akan ditampilkan setelah payment
					setChange(uang-CartHandler.calculatePrice());
				}
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
			//menambahkan transaction baru
			transaction.addTransaction(id,now,0, employeeId, paymentType);			
		}
		else
		{
				//validasi voucher telah digunakan atau sudah expired
				Voucher voucher=VoucherHandler.getVoucher(transaction.getVoucherId());
				if(voucher.getStatus().equalsIgnoreCase("used")||voucher.getValidDate().getTime()<now.getTime())
				{
					return null;
				}
				else
				{
					try {
						int uang=Integer.parseInt(money);
						//validasi jika uang yang dibayar cukup untuk membayar seluruh barang yang ada di cart
						if(CartHandler.calculatePrice()>uang)
						{
							return null;
						}
						else
						{
							//mengganti kembalian yang akan ditampilkan setelah payment
							setChange((int)(uang-CartHandler.calculatePrice()*(1-voucher.getDiscount()/100)));
						}
					} catch (Exception e) {
						// TODO: handle exception
						return null;
					}
					//menambahkan transaction baru dan mengubah id voucher dari not used menjadi used
					transaction.addTransaction(id,now,transaction.getVoucherId(), employeeId, paymentType);		
					VoucherHandler.useVoucher(transaction.getVoucherId());			
				}
		
		}
		//memasukkan transaction detail dari cart dan mengurangi stock product setiap kali dibeli/ masuk ke transaction
		for(int i=0;i<cart.size();i++)	
		{
			detail.addTransactionItem(id, cart.get(i).getProductId(), cart.get(i).getQuantity());
			ProductHandler.useStock(cart.get(i).getProductId(), cart.get(i).getQuantity());
		}
		//mengosongkan cart untuk pembelian selanjutnya
		CartHandler.emptyCart();
		return transaction;
	}
	//untuk mencari voucher yang digunakan memiliki diskon sebesar apa
	public static float findDiskon(int transactionId)
	{
		float diskon=0;
		Vector<TransactionItem> item=detail.getTransactionItem(transactionId);
		Vector<Transaction> transactions=transaction.getAllTransaction();
		int id=item.get(0).getTransactionId();
		for(int i=0;i<transactions.size();i++)
		{
			if(transactions.get(i).getTransactionId()==id)
			{
				try {
					Voucher x=VoucherHandler.getVoucher(transactions.get(i).getVoucherId());
					diskon=x.getDiscount();					
				} catch (Exception e) {
					diskon=0;
				}

			}
		}
		return diskon;
	}
	//untuk menghitung total dari transaction dengan dikurangi diskon dari transaksi yang telah terjadi
	public static int CalculateTotal(int transactionId)
	{
		float diskon=0;
		Vector<TransactionItem> item=detail.getTransactionItem(transactionId);
		Vector<Transaction> transactions=transaction.getAllTransaction();
		int id=item.get(0).getTransactionId();
		for(int i=0;i<transactions.size();i++)
		{
			if(transactions.get(i).getTransactionId()==id)
			{
				try {
					Voucher x=VoucherHandler.getVoucher(transactions.get(i).getVoucherId());
					diskon=x.getDiscount();					
				} catch (Exception e) {
					diskon=0;
				}

			}
		}
		int total=0;
		for(int i=0;i<item.size();i++)
		{
			Product product=ProductHandler.getProduct(item.get(i).getProductId());
			total=total+item.get(i).getQuantity()*product.getPrice();
		}
		total=(int)(total-total*diskon/100);
		return total;
	}
	//untuk menghitung total full tanpa dikurangi diskon dari transaksi yang telah terjadi
	public static int CalculateTotalFull(int transactionId)
	{

		Vector<TransactionItem> item=detail.getTransactionItem(transactionId);

		int total=0;
		for(int i=0;i<item.size();i++)
		{
			Product product=ProductHandler.getProduct(item.get(i).getProductId());
			total=total+item.get(i).getQuantity()*product.getPrice();
		}
		return total;
	}
	//untuk mengapply voucher kepada harga total akan mengurangi harga yang butuh dibayarkan dalam transaksi
	public static void applyVoucher(int voucherId)
	{
		if(voucherId==0)
		{
			transaction.setVoucherId(-1);
		}
		else
		{
			Voucher x=VoucherHandler.getVoucher(voucherId);
			if(x==null)
			{
				transaction.setVoucherId(0);
			}
			else
			{
				long millis=System.currentTimeMillis();
				Date now=new Date(millis);
				transaction.setVoucherId(voucherId);					
			}
		}

	
	}
	//untuk menghitung total price yang akan dibayarkan didalam payment form
	public static int calculateTotalPrice()
	{

		int x=CartHandler.calculatePrice();
		try {
			Voucher voucher=VoucherHandler.getVoucher(transaction.getVoucherId());		
			if(voucher.getStatus().equalsIgnoreCase("used"))
			{
				return -1;
			}
			long millis=System.currentTimeMillis();
			Date now=new Date(millis);
			if(now.getTime()>voucher.getValidDate().getTime())
			{
				return -2;
			}
			return (int)(x*(1-voucher.getDiscount()/100));
		} catch (Exception e) {
			// TODO: handle exception
			return x;
		}


		
	}
	//Mendapatkan seluruh vector<TransactionItem> yaitu detail dari setiap transaction
	public static Vector<TransactionItem> getTransactionDetail(int transactionId)
	{
		return detail.getTransactionItem(transactionId);
	}
	//mendapatkan vector<Transaction> yaitu transaction report yang terjadi di bulan dan tahun apa
	public static Vector<Transaction> getTransactionReport(int month,int year)
	{
		return transaction.getTransaction(month, year);
	}
	//membuka view transaction report form
	public static void viewTransactionReportForm()
	{
		TransactionReport transactionReport=new TransactionReport();
		transactionReport.setVisible(true);
	}
	//membuka view transaction detail report form
	public static void viewTransactionDetail(int idTrans, Date date, int idStaff, String name, int total)
	{
		TransactionDetail transactionDetail=new TransactionDetail(idTrans,date,idStaff,name,total);
		transactionDetail.setVisible(true);
	}
}

package Controller;
import java.sql.Date;
import java.util.Vector;

import Model.Voucher;
import View.CartView;
import View.EditVoucher;
import View.InsertVoucher;
import View.VoucherView;

public class VoucherHandler {
	private static Voucher model=new Voucher();
	public VoucherHandler() {
		// TODO Auto-generated constructor stub
	}
	//mendapatkan seluruh voucher yang ada dalam table
	public static Vector<Voucher> getAllVoucher()
	{
		Voucher test=new Voucher();
		Vector<Voucher> x=test.getAllVoucher();
		return x;
	}
	//mendapatkan voucher berdasarkan voucherId
	public static Voucher getVoucher(int voucherId)
	{
		return model.getVoucher(voucherId);
	}
	//melakukan insert voucher atau menambah voucher baru
	public static Voucher insertVoucher(float discount, Date validDate)
	{

		Vector<Voucher> x= getAllVoucher();
		int id=x.size();
		//mencari id yang dapat digunakan untuk menampung voucher
		for(int i=0;i<x.size();i++)
		{
		
			if(x.get(i).getVoucherId()!=i+1)
			{
				id=i;
			}
		}
		long millis=System.currentTimeMillis();
		Date now=new Date(millis);
		//validasi discount harus berada di antara 1 dan 100 serta valid date berada di masa depan
		if(discount>=1&&discount<=100&&now.getTime()<validDate.getTime())
		{
		model.insertVoucher(id+1, discount, validDate, "not used");
		return model;
		}
		return null;
	}
	//melakukan update voucher atau mengupdate data dari voucher yang diketahaui voucherId nya
	public static Voucher updateVoucher(int voucherId,float discount, Date validDate)
	{
		model=getVoucher(voucherId);
		long millis=System.currentTimeMillis();
		Date now=new Date(millis);
		//validasi discount harus berada di antara 1 dan 100 serta valid date berada di masa depan
		if(discount>=1&&discount<=100&&now.getTime()<validDate.getTime())
		{
		model.updateVoucher(voucherId, discount, validDate, model.getStatus());
		return model;
		}
		return null;
	}
	//mendelete voucher yang belum pernah digunakan
	public static boolean deleteVoucher(int voucherId)
	{
		try {
			//validasi voucher yang telah digunakan tidak bisa didelete
			model=model.getVoucher(voucherId);
			
				if(model.getStatus().equalsIgnoreCase("not used"))
				{
					model.deleteVoucher(voucherId);			
					return true;
				}
				else
				{
					return false;
				}
	
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		
	}
	//menggunakan voucher mengubah status voucher dari not used menjadi used
	public static void useVoucher(int voucherId)
	{
		model=getVoucher(voucherId);
		if(model.getVoucher(voucherId)!=null)
		{
			model.updateVoucher(voucherId, model.getDiscount(), model.getValidDate(), "used");
		}
	}
	//membuka view manage voucher form
	public static void viewManageVoucherForm()
	{
		VoucherView.getInstance();
		if(VoucherView.getInstance()!=null)
		{
			VoucherView.getInstance().refresh();
		}
	}
	//membuka view insert Voucher form
	public static void viewInsertVoucher()
	{
		InsertVoucher insertVoucher=new InsertVoucher();
		insertVoucher.setVisible(true);
	}
	//membuka view edit voucher form
	public static void viewEditVoucher(String id, String validDates, String discount )
	{
		EditVoucher editVoucher=new EditVoucher(id,validDates,discount);
		editVoucher.setVisible(true);
	}

}

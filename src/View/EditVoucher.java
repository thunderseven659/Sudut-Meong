package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


import Controller.VoucherHandler;
import com.toedter.calendar.JDateChooser;

//Edit voucher adalah view yang berguna untuk melakuan edit suatu voucher
//didalamnya terdapat total discount dan validdate suatu voucher yang bisa diubah valuenya untuk mengupdate voucher
public class EditVoucher extends JDialog{
	private final JPanel contentPanel = new JPanel();
	private JSpinner Discount;
	private String IDVoucher;
	private JDateChooser dateChooser;
	public EditVoucher(String id, String validDates, String discount ){
		this.IDVoucher= id;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblInsertProduct = new JLabel("Update Voucher");
			lblInsertProduct.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
			lblInsertProduct.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblInsertProduct.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblInsertProduct, BorderLayout.NORTH);
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new GridLayout(3, 2, 0, 10));
			{
				JLabel lblIdBarang = new JLabel("ID Voucher:");
				panel.add(lblIdBarang);
			}
			{
				JLabel lblidBarang = new JLabel(IDVoucher);
				panel.add(lblidBarang);
			}
			{
				JLabel lblNamaBarang = new JLabel("Discount :");
				panel.add(lblNamaBarang);
			}
			{
				SpinnerModel value=new SpinnerNumberModel(Float.parseFloat(discount),1,100,0.1f);
				Discount = new JSpinner(value);
				panel.add(Discount);
			}
			{
				JLabel lblQuantity = new JLabel("Valid Date: ");
				panel.add(lblQuantity);
			}
			{
				dateChooser = new JDateChooser();
				SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
				java.util.Date d = null;
				try {
					d = sdf.parse(validDates);
				} catch (ParseException e) {
				
				}
				sdf.applyPattern("yyyy-MM-dd");
				String newDate=sdf.format(d);
				dateChooser.setDate(Date.valueOf(newDate));
				panel.add(dateChooser);
	
			}
		
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				//button yang digunakan untuk mengupdate voucher
				JButton okButton = new JButton("Update");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String diskon = Discount.getValue().toString();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String date = sdf.format(dateChooser.getDate());
						Date d=Date.valueOf(date);
						String dates = d.toString();
						try {
							//jika sukses diupdate maka akan menghapus view ini dan mengembalikannya ke view manage voucher form
							VoucherHandler.updateVoucher(Integer.parseInt(IDVoucher),Float.parseFloat(diskon), Date.valueOf(dates));	
							JOptionPane.showMessageDialog(null, "Edit Success!");
							VoucherHandler.viewManageVoucherForm();
							dispose();	
						} catch (Exception e) {
							//error yang ditampilkan jika data yang dimasukkan invalid
							JOptionPane.showMessageDialog(null, "Please Enter Valid Data");
							// TODO: handle exception
						}



					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				//cancel akan menghapus view ini dan mengembalikan ke view sebelumnya
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
			}
		}
	}

}

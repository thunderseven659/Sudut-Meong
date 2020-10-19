package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
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

import Controller.ProductHandler;
import Controller.VoucherHandler;
import Model.Product;
import Model.Voucher;
import com.toedter.calendar.JDateChooser;

public class InsertVoucher extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JSpinner txtDiscount;
	private JDateChooser dateChooser;
	//insert voucher adalah view yang digunakan untuk menambahkan voucher baru
	//disini berisikan besar discount dan valid date untuk voucherId yang baru 
	public InsertVoucher() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblInsertProduct = new JLabel("Insert Voucher");
			lblInsertProduct.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
			lblInsertProduct.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblInsertProduct.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblInsertProduct, BorderLayout.NORTH);
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new GridLayout(2, 2, 0, 10));
			{
				JLabel lblNamaBarang = new JLabel("Discount:");
				panel.add(lblNamaBarang);
			}
			{
				SpinnerModel value=new SpinnerNumberModel(1,1,100,0.1f);
				txtDiscount = new JSpinner(value);
				panel.add(txtDiscount);
			}
			{
				JLabel lblDescription = new JLabel("Valid Date:");
				panel.add(lblDescription);
			}
			{
				dateChooser = new JDateChooser();
				panel.add(dateChooser);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Insert");
				//jika menekan insert maka akan melakukan validasi apakah voucher tersebut bisa diinsert
				//dan voucher tersebut bisa diinsert ke dalam database
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String discount=txtDiscount.getValue().toString();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String date = sdf.format(dateChooser.getDate());
						Date d=Date.valueOf(date);
						try {
							Voucher x=VoucherHandler.insertVoucher(Float.parseFloat(discount), d);	
							if(x==null)
							{
								//jika terdapat data yang salah ketika pembuatan voucher
								JOptionPane.showMessageDialog(null, "Please enter valid data!");
							}
							else
							{
								//insert sukses dan akan mengembalikan view ke manage voucher form
								JOptionPane.showMessageDialog(null, "Insert Success!");
								VoucherHandler.viewManageVoucherForm();
								dispose();	
							}
						} catch (Exception e) {
							// TODO: handle exception
							//jika terdapat data yang salah ketika pembuatan voucher
							JOptionPane.showMessageDialog(null, "Please enter valid data!");
						}

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				//cancel akan menghapus view ini dan mengembalikan ke view sebelumnya
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
			}
		}
	}

}

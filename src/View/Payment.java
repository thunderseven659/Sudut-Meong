package View;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;

import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import Controller.CartHandler;
import Controller.ProductHandler;
import Controller.TransactionHandler;
import Controller.VoucherHandler;
import Model.Product;
import Model.Session;
import Model.Transaction;
import Model.Voucher;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Payment implements DocumentListener{

	private JFrame frame;
	private JTextField textField;
	private JLabel lbltotalAfter;
	private JLabel lbltotalBefore;
	private JLabel lblDiscount;
	private JSpinner txtMoney;

	public Payment() {
		initialize();
	}

	//payment adalah view yang akan ditampilkan jika employee menekan pay button yang berada di cartview
	//payment berisikan data untuk membuat suatu transaction sebagai contohnya tipe pembayaran, harga (untuk mengecek kembalian)
	//dan voucher yang perlu jika ingin digunakan
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 313);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblPaymentConfirm = new JLabel("Payment Confirm");
		lblPaymentConfirm.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPaymentConfirm.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
		lblPaymentConfirm.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblPaymentConfirm, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnCancel = new JButton("CANCEL");
		panel_2.add(btnCancel);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			//cancel akan menghapus view ini dan mengembalikan ke view sebelumnya
			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();
			}
		});
		JButton btnPay = new JButton("PAY");

		panel_2.add(btnPay);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 10, 0, 10));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(6, 2, 0, 10));
		
		JLabel lblChooseMethod = new JLabel("Choose Method: ");
		panel.add(lblChooseMethod);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		FlowLayout fl_panel_1 = new FlowLayout(FlowLayout.CENTER, 5, 5);
		panel_1.setLayout(fl_panel_1);
		
		JRadioButton rdbtnCash = new JRadioButton("Cash");
		panel_1.add(rdbtnCash);
		
		JRadioButton rdbtnCredit = new JRadioButton("Credit");
		panel_1.add(rdbtnCredit);
		
		JLabel lblMoney=new JLabel("Money:");
		panel.add(lblMoney);
		
		SpinnerModel value=new SpinnerNumberModel(1,1,10000000,100000);
		txtMoney = new JSpinner(value);
		panel.add(txtMoney);

		
		JLabel lblCouponNumber = new JLabel("Coupon Number:");
		panel.add(lblCouponNumber);
		

		
		textField = new JTextField();
		textField.getDocument().addDocumentListener(this);
		panel.add(textField);
		textField.setColumns(10);
	
		
		JLabel lblCouponDiscount = new JLabel("Coupon Discount:");
		panel.add(lblCouponDiscount);
		
		lblDiscount = new JLabel("Discount");
		panel.add(lblDiscount);
		
		JLabel lblTotal = new JLabel("Total:");
		panel.add(lblTotal);
		lbltotalBefore = new JLabel(String.valueOf(CartHandler.calculatePrice()));
		panel.add(lbltotalBefore);
		
		JLabel lblAfterDiscount = new JLabel("Total After Discount:");
		panel.add(lblAfterDiscount);
		
		lbltotalAfter = new JLabel(String.valueOf(CartHandler.calculatePrice()));
		ButtonGroup buttonGroup=new ButtonGroup();
		buttonGroup.add(rdbtnCash);
		buttonGroup.add(rdbtnCredit);
		panel.add(lbltotalAfter);
		btnPay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					//melakukan kegiatan payment melakukan validasi terhadap data seperti paymentType, id voucher, dan pembuatan transaction
					
					String paymentType = null;
					if(rdbtnCash.isSelected())
					{
						paymentType="Cash";
					}
					if(rdbtnCredit.isSelected())
					{
						paymentType="Credit";
					}
					String id="";
					id=textField.getText().toString();
					if(id.equals(""))
					{

						TransactionHandler.applyVoucher(0);
					}
					Transaction x=TransactionHandler.processTransaction(Session.getEmployee().getEmployeeId(), paymentType,txtMoney.getValue().toString());
					if(x==null)
					{
						//terdapat juga validasi error jika terjadi error dalam pemrosesan transaction
						JOptionPane.showMessageDialog(null, "Please input correct Voucher/PaymentType/Money is not sufficient");
					}
					else
					{
						//Jika transaction sukses maka akan kembali ke tampilan cartview 
						JOptionPane.showMessageDialog(null, "Transaction Success your change is "+TransactionHandler.getChange());
						frame.dispose();
						CartView.getInstance().refresh();
					}

				} catch (Exception e) {
					//terdapat juga validasi error jika terjadi error dalam pemrosesan transaction
					JOptionPane.showMessageDialog(null, "Please input correct Voucher/PaymentType/Money is not sufficient");

				}

			}
		});
	
	}
	//melakukan set visible sesuai dengan boolean yang dimasukkan 
	public void setVisible(boolean b) {
		frame.setVisible(b);
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//mengubah voucher name dan total price setelah melakukan penginsertan pada voucherId
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(e.getDocument()== textField.getDocument())
		{
			try {
				String id="";
				id=textField.getText().toString();
				int voucherId=Integer.parseInt(id);
				Voucher x=VoucherHandler.getVoucher(voucherId);

				if(id.equals(""))
				{
					//jika voucher akan mengapply voucher 0 yang menandakan tidak ada voucher yang akan digunakan dalam transaksi
					TransactionHandler.applyVoucher(0);
				}
				else
				{
					TransactionHandler.applyVoucher(Integer.parseInt(textField.getText().toString()));					
				}
				if(TransactionHandler.calculateTotalPrice()==-1)
				{
					//jika voucher ada dan telah digunakan menampilkan error 
					lbltotalAfter.setText("Voucher has been used");
					lblDiscount.setText("-");
				}
				else if(TransactionHandler.calculateTotalPrice()==-2)
				{
					//jika voucher ada dan telah expired akan menampilkan error 
					lbltotalAfter.setText("Voucher has been expired");
					lblDiscount.setText("-");
				}
				else
				{
					//jika voucher berhasil dan tidak memiliki masalah maka harga total yang harus dibayar akan diupdate
					lbltotalAfter.setText(String.valueOf(TransactionHandler.calculateTotalPrice()));
					lblDiscount.setText(Float.toString(x.getDiscount()));					
				}

			} catch (Exception e2) {
				//jika id tidak ditemukan maka akan menset id menjadi not found
				lbltotalAfter.setText(lbltotalBefore.getText().toString());
				lblDiscount.setText("ID not Found");
			}
		}
		
	}

	@Override
	//mengubah voucher name dan total price setelah melakukan peremovean pada voucherId
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(e.getDocument()== textField.getDocument())
		{
			try {
				String id="";
				id=textField.getText().toString();
				int voucherId=Integer.parseInt(id);
				Voucher x=VoucherHandler.getVoucher(voucherId);

				if(id.equals(""))
				{
					//jika voucher akan mengapply voucher 0 yang menandakan tidak ada voucher yang akan digunakan dalam transaksi
					TransactionHandler.applyVoucher(0);
				}
				else
				{
					TransactionHandler.applyVoucher(Integer.parseInt(textField.getText().toString()));					
				}
				if(TransactionHandler.calculateTotalPrice()==-1)
				{
					//jika voucher ada dan telah digunakan menampilkan error 
					lbltotalAfter.setText("Voucher has been used");
					lblDiscount.setText("-");
				}
				else if(TransactionHandler.calculateTotalPrice()==-2)
				{
					//jika voucher ada dan telah expired akan menampilkan error 
					lbltotalAfter.setText("Voucher has been expired");
					lblDiscount.setText("-");
				}
				else
				{
					//jika voucher berhasil dan tidak memiliki masalah maka harga total yang harus dibayar akan diupdate
					lbltotalAfter.setText(String.valueOf(TransactionHandler.calculateTotalPrice()));
					lblDiscount.setText(Float.toString(x.getDiscount()));					
				}

			} catch (Exception e2) {
				//jika id tidak ditemukan maka akan menset id menjadi not found
				lbltotalAfter.setText(lbltotalBefore.getText().toString());
				lblDiscount.setText("ID not Found");
			}
		}
	}

}

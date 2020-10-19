package View;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Controller.CartHandler;
import Controller.ProductHandler;
import Controller.TransactionHandler;
import Model.CartItem;
import Model.Product;
import Model.Session;

public class EditCart extends JFrame {

	private final JPanel contentPanel = new JPanel();
	private JSpinner qty;
	private String IDBarang;


	//edit cart merupakan edit product yang ada di cart dengan id yang sudah dipilih di cart View
	public EditCart(String id) {
		this.IDBarang = id;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblInsertProduct = new JLabel("Update Cart");
			lblInsertProduct.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
			lblInsertProduct.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblInsertProduct.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblInsertProduct, BorderLayout.NORTH);
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new GridLayout(4, 2, 0, 10));
			{
				JLabel lblIdBarang = new JLabel("ID Barang:");
				panel.add(lblIdBarang);
			}
			{
				JLabel lblidBarang = new JLabel(IDBarang);
				panel.add(lblidBarang);
			}
			{

				JLabel lblNamaBarang = new JLabel("Nama Barang:");
				panel.add(lblNamaBarang);
			}
			{
				Product x=ProductHandler.getProduct(Integer.parseInt(id));
				JLabel lblnamaBarang = new JLabel(x.getName());
				panel.add(lblnamaBarang);
			}
			{
				JLabel lblHargaBarang = new JLabel("Harga Barang:");
				panel.add(lblHargaBarang);
			}
			{
				Product x=ProductHandler.getProduct(Integer.parseInt(id));
				JLabel lblhargaBarang = new JLabel(Integer.toString(x.getPrice()));
				panel.add(lblhargaBarang);
			}
			{
				JLabel lblQuantity = new JLabel("Quantity: ");
				panel.add(lblQuantity);
			}
			{
				SpinnerModel value=new SpinnerNumberModel(1,1,100000000,1);
				qty = new JSpinner(value);
				panel.add(qty);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				
				JButton okButton = new JButton("Edit");
				okButton.addActionListener(new ActionListener() {
					//jika menekan tombol edit maka akan mengubah nilai cart yang ada di tabel ex: awalnya ada 5 quantity di cart
					//lalu di edit cart quantity nya di set menjadi 1 maka quantity yang ada di table cart view menjadi 1
					public void actionPerformed(ActionEvent arg0) {
						try {
							
							
							CartItem x=CartHandler.updateStock(Integer.parseInt(id),Integer.parseInt(qty.getValue().toString()));
							if(x==null)
							{
								JOptionPane.showMessageDialog(null, "Please input valid quantity");
								
							}
							else
							{
								dispose();
								CartHandler.viewAddToCartForm();
							}
						} catch (Exception e) {
							// TODO: handle exception
							JOptionPane.showMessageDialog(null, "Please input valid quantity");
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
				cancelButton.addActionListener(new ActionListener() {
					//cancel akan menghapus view ini dan mengembalikan ke view sebelumnya
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
			}
		}
	}

}

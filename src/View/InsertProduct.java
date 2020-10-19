package View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;

import Controller.ProductHandler;
import Model.Product;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InsertProduct extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField namaBarang;
	private JTextField description;
	private JSpinner hargaBarang;
	private JSpinner qty;

	//insert product adalah view yang digunakan untuk menambahkan product
	//disini employee harus mengisi nama barang, description, harga barang dan quantity untuk menambahkan product
	public InsertProduct() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblInsertProduct = new JLabel("Insert Product");
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
				JLabel lblNamaBarang = new JLabel("Nama Barang:");
				panel.add(lblNamaBarang);
			}
			{
				namaBarang = new JTextField();
				panel.add(namaBarang);
				namaBarang.setColumns(10);
			}
			{
				JLabel lblDescription = new JLabel("Description:");
				panel.add(lblDescription);
			}
			{
				description = new JTextField();
				panel.add(description);
			}
			{
				JLabel lblHargaBarang = new JLabel("Harga Barang:");
				panel.add(lblHargaBarang);
			}
			{
				SpinnerModel value=new SpinnerNumberModel(1,1,10000000,1000);
				hargaBarang = new JSpinner(value);
				panel.add(hargaBarang);
			}
			{
				JLabel lblQuantity = new JLabel("Quantity: ");
				panel.add(lblQuantity);
			}
			{
				SpinnerModel value=new SpinnerNumberModel(1,1,10000000,1);
				qty = new JSpinner(value);
				panel.add(qty);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Insert");
				//button insert akan menginsert product sesuai dengan data yang dimasukkan dalam view
				//seperti name, description, price, dan stock(quantity)
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String name=namaBarang.getText().toString();
						String description2=description.getText().toString();
						String price2=hargaBarang.getValue().toString();
						int price=Integer.parseInt(price2);
						String stock2=qty.getValue().toString();
						int stock=Integer.parseInt(stock2);
						Product x=ProductHandler.addProduct(name, description2, price, stock);
						if(x==null)
						{
							//jika terdapat error dalam melakukan addproduct akan menampilkan error
							JOptionPane.showMessageDialog(null, "Please enter valid data!");
						}
						else
						{
							//jika proses penambahan product sukses dilaksanakan akan mengembalikan view ke manage product form
							JOptionPane.showMessageDialog(null, "Insert Success!");
							dispose();
							ProductHandler.viewManageProductForm();
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

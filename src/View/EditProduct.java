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
import Model.Product;

public class EditProduct extends JDialog {


	private final JPanel contentPanel = new JPanel();
	private JTextField namaBarang;
	private JSpinner hargaBarang;
	private JTextField txtDescription;
	private String IDBarang;


	//Edit product adalah view yang berguna untuk melakuan edit suatu product
	//didalamnya terdapat nama, description dan harga yang bisa diubah valuenya untuk mengupdate product
	public EditProduct(String id, String nama, String description, String harga) {
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
			JLabel lblInsertProduct = new JLabel("Update Product");
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
				namaBarang = new JTextField();
				namaBarang.setText(nama);
				panel.add(namaBarang);
				namaBarang.setColumns(10);
			}
			{
				JLabel lblQuantity = new JLabel("Description: ");
				panel.add(lblQuantity);
			}
			{
				txtDescription = new JTextField();
				txtDescription.setText(description);
				panel.add(txtDescription);
				txtDescription.setColumns(10);
			}
			{
				JLabel lblHargaBarang = new JLabel("Harga Barang:");
				panel.add(lblHargaBarang);
			}
			{
				SpinnerModel value=new SpinnerNumberModel(Integer.parseInt(harga),1,100000000,1000);
				hargaBarang= new JSpinner(value);
				panel.add(hargaBarang);
			}
		
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				//Button update akan melakukan update sesuai dengan data yang telah diisi/dirubah didalam view ini
				JButton okButton = new JButton("Update");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String nama = namaBarang.getText().toString();
						String description = txtDescription.getText().toString();
						String harga = hargaBarang.getValue().toString();
						int harga2=Integer.parseInt(harga);
						Product x=ProductHandler.updateProduct(Integer.parseInt(id), nama, description, harga2);
						if(x==null)
						{
							//jika terdapat data yang tidak valid maka akan menampilkan error
							JOptionPane.showMessageDialog(null, "Please enter valid data!");
						}
						else
						{
							//jika update sukses maka akan mengembalikan view ke manage product form
							JOptionPane.showMessageDialog(null, "Update Success!");
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

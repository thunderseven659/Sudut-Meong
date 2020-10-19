package View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Controller.EmployeeHandler;
import Controller.ProductHandler;
import Controller.RoleHandler;
import Model.Employee;
import Model.Product;
import Model.Role;
import Model.Session;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductView {

	private static final float TOP_ALIGNMENT = 0;
	private JFrame frame;
	private JTable table;
	static ProductView productView;

	//getinstance merupakan function yang digunakan untuk membuat view product view hanya memiliki 1 bagian saja
	//sehingga selanjutnya hanya akan memanggil view product yang lama tidak akan melakukan new terlebih dahulu
	public static synchronized ProductView getInstance()
	{
		
		if(productView==null)
		{

			return productView=new ProductView();
		}
		else
		{
			productView.setVisible(true);
			return productView;
		}

	}
	//product view adalah view tempat employee bisa mengedit,insert,dan delete product role yang bisa masuk
	//ke view ini adalah storage management
	public ProductView() {
		initialize();

	}


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setSize(700,470);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	
		JPanel paneltop = new JPanel(new BorderLayout());
		JPanel panelcenter = new JPanel();
		panelcenter.setLayout(new FlowLayout());
		JPanel panelinput = new JPanel(new GridLayout(6,2));
		panelinput.setAlignmentY(TOP_ALIGNMENT);
		JPanel panelbottom = new JPanel();
		
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("< Back");
		menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EmployeeHandler.viewRole(Session.getEmployee());
				frame.dispose();
			}
		});
		JMenu account = new JMenu("Hello, "+Session.getEmployee().getName());
		menubar.add(menu);
		menubar.add(Box.createHorizontalGlue());
		menubar.add(account);
		paneltop.add(menubar, BorderLayout.NORTH);
		
		JLabel title = new JLabel("Product List");
		title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));
		title.setFont (title.getFont ().deriveFont (20.0f));
		paneltop.add(title, BorderLayout.CENTER);
		
		table = new JTable();
		refresh();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600,260));
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		panelcenter.add(scroll);
		

		panelbottom = new JPanel(new BorderLayout());
		JPanel panelupdate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelupdate.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 55));
		JPanel panelright = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton delete = new JButton("Delete");
		//Button yang ketika ditekan akan memunculkan pop up delete
		delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//validasi jika row harus dipilih terlebih dahulu di JTable
				if(table.getSelectedRow() != -1) {
					
					int confirmation = JOptionPane.showConfirmDialog(null, "Do you want to delete this product?");
					String id = table.getModel().getValueAt(table.getSelectedRow(), 1).toString();
					//jika iya maka delete akan dilaksanakan dan merefesh tampilan di product view
					if(confirmation == JOptionPane.YES_OPTION) {
						//PERFORM DELETE
						try {
							boolean test=ProductHandler.deleteProduct(Integer.parseInt(id));
							if(test==false)
							{
								JOptionPane.showMessageDialog(null, "Item cannot be deleted because item already bought");
							}		
						} catch (Exception e) {
							// TODO: handle exception
							JOptionPane.showMessageDialog(null, "Item cannot be deleted because item already bought");
						}
					
						refresh();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please select a product");
				}
			}
		});
		
		JButton restock = new JButton("Restock");
		//restock adalah button yang digunakan untuk menambahkan quantity dari suatu product
		restock.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				//validasi jika row harus dipilih terlebih dahulu di JTable
				if(table.getSelectedRow() != -1) {
						String id = table.getModel().getValueAt(table.getSelectedRow(), 1).toString();
					    JFrame banyak=new JFrame();   
						SpinnerModel value=new SpinnerNumberModel(1,1,100000000,1);
						JSpinner stock = new JSpinner(value);   
						//cancel akan menghapus JoptionPane dan mengembalikan ke product view
						//sedangkan ok akan menambah quantity product yang ada di stock
					    int option = JOptionPane.showOptionDialog(null, stock, "Enter How many Item to Add :", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					    if (option == JOptionPane.CANCEL_OPTION)
					    {
					
					    } else if (option == JOptionPane.OK_OPTION)
					    {

					    	 try {

							    	int restock=(int) stock.getValue();
							    	if(restock>0)
							    	{
							    		ProductHandler.restock(Integer.parseInt(id), restock);
							    	}
							    	else
							    	{
										JOptionPane.showMessageDialog(null, "Number must be greater than 0!");
							    	}
								} catch (Exception e2) {

									JOptionPane.showMessageDialog(null, "Please enter valid Number!");
								}
					    }

				}
				else {
					JOptionPane.showMessageDialog(null, "Please select a product");
				}
				refresh();
			}	
		});
		panelupdate.add(restock);
		panelupdate.add(delete);
		//edit akan menjalankan view edit product untuk mengganti nama,description dan harga product
		JButton edit = new JButton("Edit");
		edit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//validasi jika row harus dipilih terlebih dahulu dari JTable
				if(table.getSelectedRow() != -1) {
					
					String id = table.getModel().getValueAt(table.getSelectedRow(), 1).toString();
					String nama = table.getModel().getValueAt(table.getSelectedRow(), 2).toString();
					String description = table.getModel().getValueAt(table.getSelectedRow(), 3).toString();
					String harga = table.getModel().getValueAt(table.getSelectedRow(), 4).toString();
					ProductHandler.viewEditProduct(id, nama, description, harga);
	
				}
				else {
					JOptionPane.showMessageDialog(null, "Please select a product");
				}
			}
		});
		panelupdate.add(edit);
		//insert adalah button untuk menambahkan product baru dan mengubah view menjadi ke view InsertProduct
		JButton paybutton = new JButton("Insert");
		paybutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				ProductHandler.viewInsertProduct();

			}
		});
		paybutton.setPreferredSize(new Dimension(585,30));
		panelright.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 55));
		panelright.add(paybutton);
		panelbottom.add(panelupdate, BorderLayout.NORTH);
		panelbottom.add(panelright, BorderLayout.CENTER);
		

		frame.getContentPane().add(paneltop, BorderLayout.NORTH);
		frame.getContentPane().add(panelcenter, BorderLayout.CENTER);
		frame.getContentPane().add(panelbottom, BorderLayout.SOUTH);
	}
	//set visible dari view ini tergantung dari boolean yang dimasukkan
	public void setVisible(boolean b) {
		frame.setVisible(b);
		
	}
	//merefresh JTable dengan data yang ada di database
	public void refresh()
	{
		String[] columnname = {"No","ID Barang","Nama Barang","Description","Harga","Qty"};
		DefaultTableModel model = new DefaultTableModel(columnname,0)
		{
		    public boolean isCellEditable(int row, int column)
		    {
		      return false;//This causes all cells to be not editable
		    }
		};
		Vector<Product> data=ProductHandler.getAllProduct();
		for(int i=0;i<data.size();i++)
		{

			Vector<Object> row=new Vector<Object>();
			row.add(i+1);
			row.add(data.get(i).getProductId());
			row.add(data.get(i).getName());
			row.add(data.get(i).getDescription());
			row.add(data.get(i).getPrice());
			row.add(data.get(i).getStock());
			model.addRow(row);
		}
		table.setModel(model);
	}
}

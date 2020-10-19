package View;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
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
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Controller.CartHandler;
import Controller.EmployeeHandler;
import Controller.ProductHandler;
import Controller.RoleHandler;
import Model.CartItem;
import Model.Employee;
import Model.Product;
import Model.Role;
import Model.Session;

public class CartView extends JFrame implements MouseListener,DocumentListener{
	//cart view adalah view yang dapat dilihat oleh employee yang memiliki role cashier
	private JPanel paneltop,panelcenter,panelbottom,panelinput,panelleft,panelright;
	private JTable table;
	private JLabel title,titleinput,id,namabarang,hargabarang;
	private JTextField idinput;
	private JSpinner qty;
	private JButton insertbutton,paybutton,delete,edit;
	private JMenuBar menubar;
	private JMenu menu,account;
	private JMenuItem logout;
	static CartView cartView;
	//getinstance adalah function yang dibuat agar cart view yang dibuat tidak bisa lebih dari 1 dan hanya akan mereturn cart view 
	//yang dibuat tadi jika function tersebut dipanggil
	public static synchronized CartView getInstance()
	{
		
		if(cartView==null)
		{

			return cartView=new CartView();
		}
		else
		{
			cartView.setVisible(true);
			return cartView;
		}

	}
	CartView(){
		setSize(1000,470);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	

		paneltop = new JPanel(new BorderLayout());
		panelcenter = new JPanel();
		panelcenter.setLayout(new FlowLayout());
		panelinput = new JPanel(new GridLayout(6,2));
		panelinput.setAlignmentY(TOP_ALIGNMENT);
		panelbottom = new JPanel();
		
		menubar = new JMenuBar();
		menu = new JMenu("< Back");
		menu.addMouseListener(this);
		account = new JMenu("Hello,  "+Session.getEmployee().getName());
		menubar.add(menu);
		menubar.add(Box.createHorizontalGlue());
		menubar.add(account);
		paneltop.add(menubar, BorderLayout.NORTH);
		
		title = new JLabel("Shopping Cart");
		title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));
		title.setFont (title.getFont ().deriveFont (20.0f));
		paneltop.add(title, BorderLayout.CENTER);
		
		  
		table = new JTable();
		refresh();
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600,260));
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		panelcenter.add(scroll);

		titleinput = new JLabel("Input Item");
		
		titleinput.setFont (titleinput.getFont ().deriveFont (15.0f));
		titleinput.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		panelinput.add(titleinput);
		titleinput.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		titleinput = new JLabel("");
		panelinput.add(titleinput);
		
		id = new JLabel("ID Barang");
		idinput = new JTextField(15);
		idinput.getDocument().addDocumentListener(this);
		
		panelinput.add(id);
		panelinput.add(idinput);

		id = new JLabel("Quantity");
		panelinput.add(id);
		SpinnerModel value=new SpinnerNumberModel(1,1,10000000,1);
		qty = new JSpinner(value);
		panelinput.add(qty);
		
		titleinput = new JLabel("Nama Barang");
		panelinput.add(titleinput);
		namabarang = new JLabel("[nama barang]");
		panelinput.add(namabarang);
		titleinput = new JLabel("Harga Barang");
		panelinput.add(titleinput);
		hargabarang = new JLabel("[harga barang]");
		panelinput.add(hargabarang);
		
		titleinput = new JLabel("");
		panelinput.add(titleinput);
		insertbutton = new JButton("Input");
		insertbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//jika mouse menekan tombol insert dan data nya valid akan menambahkan ke vector<cartlist> berupa cartItem
			
				try {
					String quantity=qty.getValue().toString();			
					String productId=idinput.getText().toString();
					CartItem x=CartHandler.addToCart(Integer.parseInt(productId), Integer.parseInt(quantity));
					if(x==null)
					{
						//error akan ditampilkan jika data yang diberikan tidak valid
						JOptionPane.showMessageDialog(null, "Please input Correct Data!/Too many item to buy!");
					}

				} catch (Exception e) {
					// TODO: handle exception
					//error akan ditampilkan jika data yang diberikan tidak valid
					JOptionPane.showMessageDialog(null, "Please Input Correct Data to Insert!");
				}
				refresh();

			}
		});
		panelinput.add(insertbutton);
		panelcenter.add(panelinput);
		
		panelbottom = new JPanel(new BorderLayout());
		panelleft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelleft.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		panelright = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		delete = new JButton("Delete");
		delete.addMouseListener(this);
		panelleft.add(delete);
		edit = new JButton("Edit");
		edit.addMouseListener(this);
		panelleft.add(edit);
		paybutton = new JButton("Pay");
		paybutton.setPreferredSize(new Dimension(380,30));
		paybutton.addMouseListener(this);
		panelright.add(paybutton);
		panelbottom.add(panelleft, BorderLayout.NORTH);
		panelbottom.add(panelright, BorderLayout.CENTER);

		add(paneltop, BorderLayout.NORTH);
		add(panelcenter, BorderLayout.CENTER);
		add(panelbottom, BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == menu) {
			//jika mouse menekan tombol menu maka akan kembali ke menu view role
			EmployeeHandler.viewRole(Session.getEmployee());
			this.dispose();
		}
		else if(e.getSource() == paybutton) {
			//jika mouse menekan tombol pay button maka akan masuk ke view payment
			//validasi jika cart size nya 0 maka tidak bisa masuk ke payment
			if(CartHandler.getCart().size()==0)
			{
				//error yang ditampilkan jika cartnya kosong
				JOptionPane.showMessageDialog(null, "Cart Empty!");
			}
			else
			{
				//menampilkan suatu payment view 
				Payment payment = new Payment();
				payment.setVisible(true);	
			}
		}
		else if(e.getSource() == edit) {
			//jika mouse menekan tombol edit button maka akan masuk ke view edit cart
			//jika row tidak dipilih akan menampilkan error untuk membuat employee memilih product di dalam cart yang akan di update
			if(table.getSelectedRow() != -1) {
				//validasi jika yang diupdate merupakan total maka menampilkan error total tidak bisa di edit
				String id = table.getModel().getValueAt(table.getSelectedRow(), 1).toString();
				if(id.equals("yang"))
				{
					//error yang menampilkan total tidak bisa diedit
					JOptionPane.showMessageDialog(null, "Total cannot be edited");
				}
				else
				{
					//jika sukses maka akan menampilkan view edit cart dengan id sesuai dengan row yang dipilih
					EditCart editcart = new EditCart(id);
					editcart.setVisible(true);	
				}
		
			}
				else {
					//error yang ditampilkan jika tidak memilih row dalam JTable
					JOptionPane.showMessageDialog(null, "Please select a product in the cart");
				}
		}
		else if(e.getSource() == delete) {
			//jika mouse menekan tombol delete butotn maka akan mendelete bagian dari cart.
			//jika row tidak dipilih akan menampilkan error untuk membuat employee memilih product di dalam cart yang akan di delete
			if(table.getSelectedRow() != -1) {
				int confirmation = JOptionPane.showConfirmDialog(null, "Do you want to delete this product?");
				String id = table.getModel().getValueAt(table.getSelectedRow(), 1).toString();
				if(confirmation == JOptionPane.YES_OPTION) {
					//validasi jika yang didelete merupakan total maka menampilkan error total tidak bisa di delete
					if(id.equals("yang"))
					{
						//error yang menampilkan total tidak bisa di delete
						JOptionPane.showMessageDialog(null, "Total cannot be deleted from the Table");
					}
					else
					{
						//jika data product valid maka akan melakukan proses delete cartItem yang ada didalam cart
					CartHandler.delete(Integer.parseInt(id));
					JOptionPane.showMessageDialog(null, "Product Has been deleted from Cart!");
					refresh();
					}
				}
			}
			else {
				//error yang ditampilkan jika tidak memilih row dalam JTable
				JOptionPane.showMessageDialog(null, "Please select a product in the cart");
			}
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override

	//jika memasukkan bagian dari productId dan productId terdetect maka akan menampilkan nama product dan harga product
	//sebaliknya jika tidak ditemukan maka akan berstatus not found
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub

		if(e.getDocument()== idinput.getDocument())
		{
			try {
				String id=idinput.getText().toString();
				int productId=Integer.parseInt(id);
				Product x=ProductHandler.getProduct(productId);
				namabarang.setText(x.getName());
				hargabarang.setText(Integer.toString(x.getPrice()));
			} catch (Exception e2) {
				// TODO: handle exception
				namabarang.setText("Not Found");
				hargabarang.setText("Not Found");
			}
		}
		
	}
	//jika meremove bagian dari productId dan productId terdetect maka akan menampilkan nama product dan harga product
	//sebaliknya jika tidak ditemukan maka akan berstatus not found
	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub

		if(e.getDocument()== idinput.getDocument())
		{
			try {
				String id=idinput.getText().toString();
				int productId=Integer.parseInt(id);
				Product x=ProductHandler.getProduct(productId);
				namabarang.setText(x.getName());
				hargabarang.setText(Integer.toString(x.getPrice()));
			} catch (Exception e2) {
				// TODO: handle exception
				namabarang.setText("Not Found");
				hargabarang.setText("Not Found");
			}
		}

		
	}
	//merefresh isi dari tabel yang ada di JTable
	public void refresh()
	{
		String[] columnname = {"No","ID Barang","Nama Barang","Harga","Qty","Jumlah"};
		Vector<CartItem> data=CartHandler.getCart();
		DefaultTableModel model = new DefaultTableModel(columnname,0) 
		{
		    public boolean isCellEditable(int row, int column)
		    {
		      return false;//This causes all cells to be not editable
		    }
		  };
		for(int i=0;i<data.size();i++)
		{

			Vector<Object> row=new Vector<Object>();
			Product x=ProductHandler.getProduct(data.get(i).getProductId());
			row.add(i+1);
			row.add(data.get(i).getProductId());
			row.add(x.getName());
			row.add(x.getPrice());
			row.add(data.get(i).getQuantity());
			row.add(x.getPrice()*data.get(i).getQuantity());
			model.addRow(row);
			if(i==data.size()-1)
			{
				Vector<Object> total=new Vector<Object>();
				total.add("total");
				total.add("yang");
				total.add("harus");
				total.add("dibayarkan");
				total.add("adalah");
				total.add(Integer.toString(CartHandler.calculatePrice()));
				model.addRow(total);
			}
			
		
		}
		if(data.size()==0)
		{
			Vector<Object> total=new Vector<Object>();
			total.add("total");
			total.add("yang");
			total.add("harus");
			total.add("dibayarkan");
			total.add("adalah");
			total.add(Integer.toString(CartHandler.calculatePrice()));
			model.addRow(total);
		}
		table.setModel(model);
	}
}

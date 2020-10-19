package View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Controller.ProductHandler;
import Controller.TransactionHandler;
import Model.Product;
import Model.TransactionItem;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

public class TransactionDetail extends JFrame {

	private JPanel contentPane;
	private JTable table;

	//pada transaction detail akan ditampilkan detail transaction seperti jenis dan harga masing-masing produk yang dibeli harga total,
	//discount, harga discount, cashier yang waktu itu bekerja
	public TransactionDetail(int idTrans, Date date, int idStaff, String name, int total) {

		setBounds(100, 100, 450, 300);
		setSize(660,530);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		String[] columnname = {"No","ID Barang","Nama Barang","Harga","Qty"};
		JPanel paneltop = new JPanel(new BorderLayout());
		JPanel panelcenter = new JPanel();
		panelcenter.setLayout(new FlowLayout());
		JPanel panelinput = new JPanel(new GridLayout(6,2));
		panelinput.setAlignmentY(TOP_ALIGNMENT);
		JPanel panelbottom = new JPanel();
		
		
		JLabel title = new JLabel("Transaction ID-"+idTrans);
		title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));
		title.setFont (title.getFont ().deriveFont (20.0f));
		paneltop.add(title, BorderLayout.CENTER);
		
		table = new JTable(){
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return false;
			}
		};
			DefaultTableModel model = new DefaultTableModel(columnname,0)		  {
			    public boolean isCellEditable(int row, int column)
			    {
			      return false;//This causes all cells to be not editable
			    }
			  };
			  //mencari transactionItem dengan id sesuai dengan transactionId
			Vector<TransactionItem> data=TransactionHandler.getTransactionDetail(idTrans);
			//membuat JTable untuk menampilkan list detail transaksi item yang telah dibeli
			for(int i=0;i<data.size();i++)
			{

				Vector<Object> row=new Vector<Object>();
				Product product=ProductHandler.getProduct(data.get(i).getProductId());
				row.add(i+1);
				row.add(data.get(i).getProductId());
				row.add(product.getName());
				row.add(product.getPrice());
				row.add(data.get(i).getQuantity());
				model.addRow(row);
			}
			
			table.setModel(model);
		JTable table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600,260));
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		panelcenter.add(scroll);
		

		panelbottom = new JPanel(new BorderLayout());
		

		getContentPane().add(paneltop, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));
		flowLayout.setAlignment(FlowLayout.LEFT);
		paneltop.add(panel, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(3, 2, 0, 0));
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
		JLabel lblJune = new JLabel("Transaction Date : "+sdf.format(date)+"");
		lblJune.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(lblJune);
		
		JLabel label = new JLabel("");
		panel_1.add(label);
		
		JLabel lblStaffId = new JLabel("Staff ID:");
		panel_1.add(lblStaffId);
		
		JLabel label_1 = new JLabel(idStaff+"");
		panel_1.add(label_1);
		
		JLabel lblStaffName = new JLabel("Staff Name: ");
		panel_1.add(lblStaffName);
		
		JLabel lblJohnDoe = new JLabel(name);
		panel_1.add(lblJohnDoe);
		
		JSeparator separator = new JSeparator();
		panel.add(separator);
		
		JLabel lblTotalRp = new JLabel();
		lblTotalRp.setPreferredSize(new Dimension(600, 100));
		lblTotalRp.setText("Total: "+TransactionHandler.CalculateTotalFull(idTrans)+"      Discount: "+TransactionHandler.findDiskon(idTrans)+" %" +" Total After Discount: "+total);
		lblTotalRp.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalRp.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel.add(lblTotalRp);
		getContentPane().add(panelcenter, BorderLayout.CENTER);
		getContentPane().add(panelbottom, BorderLayout.SOUTH);
	}

}

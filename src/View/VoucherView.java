package View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import Controller.EmployeeHandler;
import Controller.ProductHandler;
import Controller.VoucherHandler;
import Model.Product;
import Model.Session;
import Model.Voucher;


public class VoucherView {
	
	
	private static final float TOP_ALIGNMENT = 0;
	private JFrame frame;
	private JTable table;
	static VoucherView voucherView;
	public VoucherView() {
		initialize();

	}
	private void initialize() {
		// TODO Auto-generated method stub
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setSize(700,470);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
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
			//mengembalikan tampilan ke view sebelumnya yaitu view role
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
		
		JLabel title = new JLabel("Voucher List");
		title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));
		title.setFont (title.getFont ().deriveFont (20.0f));
		paneltop.add(title, BorderLayout.CENTER);
		table = new JTable();
		refresh();
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600,260));
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		panelcenter.add(scroll);
		

		panelbottom = new JPanel(new BorderLayout());
		JPanel panelupdate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelupdate.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 55));
		JPanel panelright = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton delete = new JButton("Delete");
		delete.addMouseListener(new MouseAdapter() {
			//mendelete voucher yang sudah dipilih
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//validasi jika voucher sudah harus dipilih dari table
				if(table.getSelectedRow() != -1) {
					int confirmation = JOptionPane.showConfirmDialog(null, "Do you want to delete this voucher?");
					String id = table.getModel().getValueAt(table.getSelectedRow(), 1).toString();
					if(confirmation == JOptionPane.YES_OPTION) {
						//PERFORM DELETE
						boolean done=VoucherHandler.deleteVoucher(Integer.parseInt(id));
						//jika voucher belum digunakan maka delete akan berhasil tetapi jika digunakan akan memanggil error
						if(done==true)
						{
							JOptionPane.showMessageDialog(null, "Delete Success");
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Voucher Already Used");
						}
						refresh();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please select a voucher");
				}
			}
		});
		panelupdate.add(delete);

		JButton edit = new JButton("Edit");
		edit.addMouseListener(new MouseAdapter() {
			//memanggil view edit Voucher
			@Override
			public void mouseClicked(MouseEvent e) {
				//validasi jika voucher sudah harus dipilih dari JTable
				if(table.getSelectedRow() != -1) {

					String id = table.getModel().getValueAt(table.getSelectedRow(), 1).toString();
					String Discount = table.getModel().getValueAt(table.getSelectedRow(), 2).toString();

					SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
					String date=(String)table.getValueAt(table.getSelectedRow(), 3);
					VoucherHandler.viewEditVoucher(id, date, Discount);

				}
				else {
					JOptionPane.showMessageDialog(null, "Please select a voucher");
				}
			}
		});
		panelupdate.add(edit);
		JButton paybutton = new JButton("Insert");
		paybutton.addMouseListener(new MouseAdapter() {
			//memanggil view insertVoucher
			@Override
			public void mouseClicked(MouseEvent arg0) {
				VoucherHandler.viewInsertVoucher();

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
	//getInstance hanya akan membuat 1 tampilan voucher view dan jika sudah ada tampilan voucher view maka tampilan hanya akan dipanggil ulang
	public static synchronized VoucherView getInstance()
	{
		
		if(voucherView==null)
		{
		
			return voucherView=new VoucherView();
		}
		else
		{
			voucherView.setVisible(true);
			return voucherView;
		}

	}
	//merefresh tabel voucher yang ditampilkan
	public void refresh()
	{

		String[] columnname = {"No","VoucherId","Discount","Valid Date","Status"};
		DefaultTableModel model = new DefaultTableModel(columnname,0)
		  {
		    public boolean isCellEditable(int row, int column)
		    {
		      return false;//This causes all cells to be not editable
		    }
		  };
		  Vector<Voucher> data=VoucherHandler.getAllVoucher();
			for(int i=0;i<data.size();i++)
			{

				Vector<Object> row=new Vector<Object>();
				row.add(i+1);
				row.add(data.get(i).getVoucherId());
				row.add(data.get(i).getDiscount());
				Date date=data.get(i).getValidDate();
				SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
				row.add(sdf.format(date));
				row.add(data.get(i).getStatus());
				model.addRow(row);
			}
			table.setModel(model);
	}
	//set visible frame sesuai dengna boolean yang diinput
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		frame.setVisible(b);
		
	}

	
}

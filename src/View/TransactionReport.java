package View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Controller.EmployeeHandler;
import Controller.TransactionHandler;
import Model.Employee;
import Model.Session;
import Model.Transaction;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class TransactionReport extends JFrame {

	private JPanel contentPane;
	JTable table;

	public TransactionReport() {
		setBounds(100, 100, 450, 300);
		setSize(700,470);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String[] columnname = {"ID Transaction","Purchase Date","ID Employee","Employee Name","Total","Payment Method"};



		JPanel paneltop = new JPanel(new BorderLayout());
		JPanel panelcenter = new JPanel();
		panelcenter.setLayout(new FlowLayout());
		JPanel panelinput = new JPanel(new GridLayout(6,2));
		panelinput.setAlignmentY(TOP_ALIGNMENT);
		JPanel panelbottom = new JPanel();
		
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("< Back");
		//mengembalikan ke view sebelumnya yaitu view role dan mendispose view ini
		menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EmployeeHandler.viewRole(Session.getEmployee());
				dispose();
			}
		});
		JMenu account = new JMenu("Hello, "+Session.getEmployee().getName());
		menubar.add(menu);
		menubar.add(Box.createHorizontalGlue());
		menubar.add(account);
		paneltop.add(menubar, BorderLayout.NORTH);
		
		JLabel title = new JLabel("Transaction Report");
		title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));
		title.setFont (title.getFont ().deriveFont (20.0f));
		paneltop.add(title, BorderLayout.CENTER);
		
		TableModel model = new DefaultTableModel(null, columnname)
		  {
		    public boolean isCellEditable(int row, int column)
		    {
		      return false;//This causes all cells to be not editable
		    }
		  };
		JTable table = new JTable(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//mengeklik tabel akan menampilkan transaction detail
				if(table.getSelectedRow() != -1) {
					int confirmation = JOptionPane.showConfirmDialog(null, "Do you want to see this transaction?");
					String id = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
					//pertanyaan apakah employee ingin melihat detail transaksi
					if(confirmation == JOptionPane.YES_OPTION) {
						int row = table.getSelectedRow();
						int idTrans=(int)table.getValueAt(row, 0);
						int idStaff = (int)table.getValueAt(row, 2);
						String name=(String)table.getValueAt(row, 3);
						SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
						String date=(String)table.getValueAt(row, 1);
						java.util.Date d = null;
						try {
							d = sdf.parse(date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						sdf.applyPattern("yyyy-MM-dd");
						String newDate=sdf.format(d);
						int total=(int)table.getValueAt(row, 4);
						TransactionHandler.viewTransactionDetail(idTrans, Date.valueOf(newDate), idStaff, name, total);
					}
				}
			}
		});
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600,260));
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		panelcenter.add(scroll);
		

		panelbottom = new JPanel(new BorderLayout());
		

		getContentPane().add(paneltop, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		paneltop.add(panel, BorderLayout.SOUTH);
		
		JLabel lblMonth = new JLabel("Month: ");
		panel.add(lblMonth);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}));
		panel.add(comboBox);
		
		JLabel lblYear = new JLabel("Year: ");
		panel.add(lblYear);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "1999", "1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985", "1984", "1983", "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971", "1970", "1969", "1968", "1967", "1966", "1965", "1964", "1963", "1962", "1961", "1960", "1959", "1958", "1957", "1956", "1955", "1954", "1953", "1952", "1951", "1950", "1949", "1948", "1947", "1946", "1945", "1944", "1943", "1942", "1941", "1940", "1939", "1938", "1937", "1936", "1935", "1934", "1933", "1932", "1931", "1930", "1929", "1928", "1927", "1926", "1925", "1924", "1923", "1922", "1921", "1920", "1919", "1918", "1917", "1916", "1915", "1914", "1913", "1912", "1911", "1910", "1909", "1908", "1907", "1906", "1905", "1904", "1903", "1902", "1901", "1900"}));
		panel.add(comboBox_1);
		
		JButton btnFetch = new JButton("Fetch");
		panel.add(btnFetch);
		btnFetch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//fetch akan mencarikan transaksi sesuai dengan tahun dan bulan yang dipilih
				String month=comboBox.getSelectedItem().toString();
				String year=comboBox_1.getSelectedItem().toString();
				DefaultTableModel model = new DefaultTableModel(columnname,0)
				{
				    public boolean isCellEditable(int row, int column)
				    {
				      return false;//This causes all cells to be not editable
				    }
				};
				int month2=0;
				if(month.equals("January"))
				{
					month2=1;
				}
				else if(month.equals("February"))
				{
					month2=2;
				}
				else if(month.equals("March"))
				{
					month2=3;
				}
				else if(month.equals("April"))
				{
					month2=4;
				}
				else if(month.equals("May"))
				{
					month2=5;
				}
				else if(month.equals("June"))
				{
					month2=6;
				}
				else if(month.equals("July"))
				{
					month2=7;
				}
				else if(month.equals("August"))
				{
					month2=8;
				}
				else if(month.equals("September"))
				{
					month2=9;
				}
				else if(month.equals("October"))
				{
					month2=10;
				}
				else if(month.equals("November"))
				{
					month2=11;
				}
				else if(month.equals("December"))
				{
					month2=12;
				}
				//membuat JTable transaction list berdasarkan tahun dan bulan yang dipilih
				Vector<Transaction> data=TransactionHandler.getTransactionReport(month2, Integer.parseInt(year));
				for(int i=0;i<data.size();i++)
				{

					Employee employee=EmployeeHandler.getEmployee(data.get(i).getEmployeeId());
					TransactionHandler.CalculateTotal(data.get(i).getTransactionId());
					Vector<Object> row=new Vector<Object>();
					row.add(data.get(i).getTransactionId());

					Date date=data.get(i).getPurchase_datetime();
					SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
					row.add(sdf.format(date));
					row.add(data.get(i).getEmployeeId());
					row.add(employee.getName());
					row.add(TransactionHandler.CalculateTotal(data.get(i).getTransactionId()));
					row.add(data.get(i).getPaymentType());
					model.addRow(row);
				}
			table.setModel(model);
			}
		});
		getContentPane().add(panelcenter, BorderLayout.CENTER);
		getContentPane().add(panelbottom, BorderLayout.SOUTH);
	}

}

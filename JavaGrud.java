
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import net.proteanit.sql.DbUtils;
import java.sql.*;

import javax.swing.border.LineBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class JavaGrud {

	private JFrame frame;
	private JTextField bookname;
	private JTextField edtion;
	private JTextField price;
	private JTextField booksearch;
	private JLabel bookid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaGrud window = new JavaGrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaGrud() {
		initialize();
		connectTest();
		table_load();
	}
   /**
    *  Create Connection SQL
    */
	 Connection con ;
	 PreparedStatement pstmt;
	 ResultSet rs ;
	 private JTable table_1;
	 
	public void connectTest() {
	 try {
	 //Class.forName("com.mysql.cj.jdbc.Driver");
	  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buch", "root", "");
	 System.out.println("Verbindung wurde herstellt");
	 }catch(SQLException ex) {
	 System.err.println("Verbindung konnte nicht herstellt werden");
	 System.err.println(ex);
	}
	}
	 /**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Book Shop");
		frame.getContentPane().setBackground(SystemColor.control);
		frame.setBounds(0, 0, 550, 330);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registrierung", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(2, 38, 285, 141);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Buch Name");
		lblNewLabel.setBounds(10, 36, 74, 21);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Edtion");
		lblNewLabel_1.setBounds(10, 70, 46, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Preis");
		lblNewLabel_3.setBounds(10, 106, 46, 14);
		panel.add(lblNewLabel_3);
		
		bookname = new JTextField();
		bookname.setBounds(94, 36, 181, 20);
		panel.add(bookname);
		bookname.setColumns(10);
		
		edtion = new JTextField();
		edtion.setBounds(94, 67, 181, 20);
		panel.add(edtion);
		edtion.setColumns(10);
		
		price = new JTextField();
		price.setBounds(94, 103, 181, 20);
		panel.add(price);
		price.setColumns(10);
		
		
		
		
		JLabel lblNewLabel_2 = new JLabel("Buchhandlung");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(171, 0, 114, 40);
		frame.getContentPane().add(lblNewLabel_2);
		
		JPanel search = new JPanel();
		search.setBorder(new TitledBorder(null, "Suchen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		search.setBounds(2, 230, 287, 50);
		frame.getContentPane().add(search);
		search.setLayout(null);
		
		JLabel bookid = new JLabel("Buch ID");
		bookid.setBounds(10, 20, 50, 20);
		search.add(bookid);
		
		booksearch = new JTextField();
		booksearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			
			String id =	booksearch.getText();
			try {
			pstmt = con.prepareStatement("select name,edtion,preis from shop where id = ?");
			pstmt.setString(1,id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()== true) {
			  String name =  rs.getString(1);
			  String edtion1 =  rs.getString(2);
			  String price1 =  rs.getString(3);
			  
			  bookname.setText(name);
			  edtion.setText(edtion1);
			  price.setText(price1);
			  
			}else {
				  bookname.setText("");
				  edtion.setText("");
				  price.setText("");
				
			}}catch(SQLException ex) {
				ex.printStackTrace();
			}
			}});
		booksearch.setColumns(10);
		booksearch.setBounds(96, 20, 181, 20);
		search.add(booksearch);
		
		
		JButton exit = new JButton("Beenden");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exit.setBounds(97, 190, 88, 33);
		frame.getContentPane().add(exit);
		

		
		JButton clear = new JButton("Leeren");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookname.setText("");
				edtion.setText("");
				price.setText("");
			}
		});
		clear.setBounds(186, 190, 98, 33);
		frame.getContentPane().add(clear);
		
		JButton update = new JButton("Update");
	
		
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sbookname,sedtion,sprice,sbookid;
				sbookname = bookname.getText();
				sedtion = edtion.getText();
				sprice = price.getText();
				sbookid = booksearch.getText();
				
				try {
						pstmt = con.prepareStatement("Update shop set name =? , edtion=? , preis=? where id =?");
						pstmt.setString(1, sbookname);
						pstmt.setString(2, sedtion);
						pstmt.setString(3, sprice);
						pstmt.setString(4, sbookid);
					;
						pstmt.executeUpdate();
						//table_load();
						JOptionPane.showMessageDialog(null, "Es wurde aktulisiert");
						bookname.setText("");
						edtion.setText("");
						price.setText("");
						bookname.requestFocus();
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			     } });
		
		update.setBounds(320, 234, 89, 40);
		frame.getContentPane().add(update);
		
		
		JButton delet = new JButton("L\u00F6schen");
		delet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sbookid;
				sbookid = booksearch.getText();
				try {
						pstmt = con.prepareStatement("delete from shop where id=?");
						pstmt.setString(1, sbookid);
						pstmt.executeUpdate();
						
						table_load();
						JOptionPane.showMessageDialog(null, "Es wurde gelöscht");
						bookname.setText("");
						edtion.setText("");
						price.setText("");
						bookname.requestFocus();
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			     } });
		delet.setBounds(435, 234, 89, 40);
		frame.getContentPane().add(delet);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(315, 40, 200, 180);
		frame.getContentPane().add(scrollPane);
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		
		JButton save = new JButton("Speichern");
		save.setBounds(2, 190, 93, 33);
		frame.getContentPane().add(save);
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				String sbookname,sedtion,sprice;
				sbookname = bookname.getText();
				sedtion = edtion.getText();
				sprice = price.getText();
				
				try {
					 if( sbookname.length()!=0 && sedtion.length()!=0 && sprice.length()!=0) {
						pstmt = con.prepareStatement("insert into shop(name , edtion , preis)values(?,?,?)");
						pstmt.setString(1, sbookname);
						pstmt.setString(2, sedtion);
						pstmt.setString(3, sprice);
						pstmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "Es wurde gespeichert");
				table_load();
						bookname.setText("");
						edtion.setText("");
						price.setText("");
						bookname.requestFocus();
				}else{
					 JOptionPane.showMessageDialog(null, "Bitte F\u00fchlen Alle Fields aus");	 
					 } 
					 }
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			     } });
		
		
	}
	
	


         public void table_load(){
        	  
        	  try {
        	  pstmt= con.prepareStatement("select * from shop");
        	  rs= pstmt.executeQuery();
        	  table_1 .setModel(DbUtils.resultSetToTableModel(rs));
        	  }catch  (SQLException es){
        		  
        		  es.printStackTrace();
        	  }
            }
            
}




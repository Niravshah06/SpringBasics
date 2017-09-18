import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Frame1 extends JFrame {

	private JPanel contentPane;
	private JTextField txtPleaseSelectA;
	private JTextField txtLoadingpleaseWait;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 frame = new Frame1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Ping Tool For Machine Status");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnWelcomeToPing = new JTextPane();
		txtpnWelcomeToPing.setBounds(110, 11, 219, 30);
		txtpnWelcomeToPing.setFont(new Font("Script MT Bold", Font.BOLD, 20));
		txtpnWelcomeToPing.setEditable(false);
		txtpnWelcomeToPing.setText("Welcome to Ping Tool!");
		txtpnWelcomeToPing.setBackground(null);
		contentPane.add(txtpnWelcomeToPing);
		
		txtPleaseSelectA = new JTextField();
		txtPleaseSelectA.setFont(new Font("Times New Roman", Font.BOLD, 12));
		txtPleaseSelectA.setEditable(false);
		txtPleaseSelectA.setText("Please select a .CSV file to Ping");
		txtPleaseSelectA.setBounds(135, 111, 171, 20);
		txtPleaseSelectA.setBorder(null);
		contentPane.add(txtPleaseSelectA);
		txtPleaseSelectA.setColumns(10);
		
		txtLoadingpleaseWait = new JTextField();
		txtLoadingpleaseWait.setVisible(false);
		txtLoadingpleaseWait.setHorizontalAlignment(SwingConstants.CENTER);
		txtLoadingpleaseWait.setText("In progress...");
		txtLoadingpleaseWait.setBounds(156, 216, 133, 20);
		txtLoadingpleaseWait.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtLoadingpleaseWait.setEditable(false);
		txtLoadingpleaseWait.setBorder(null);
		txtLoadingpleaseWait.setEnabled(false);
		contentPane.add(txtLoadingpleaseWait);
		txtLoadingpleaseWait.setColumns(10);
		
		
		JButton btnSelect = new JButton("Select");
		btnSelect.setBounds(173, 165, 89, 23);
		contentPane.add(btnSelect);
		
		
		
		JTextPane txtpnCreatedBySauravshroff = new JTextPane();
		txtpnCreatedBySauravshroff.setText("Developed by: Nirav Shah");
		txtpnCreatedBySauravshroff.setBounds(145, 42, 161, 20);
		contentPane.add(txtpnCreatedBySauravshroff);
		txtpnCreatedBySauravshroff.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtpnCreatedBySauravshroff.setEditable(false);
		txtpnCreatedBySauravshroff.setBackground(null);
		txtpnCreatedBySauravshroff.setBorder(null);
		
		
		btnSelect.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent arg0) {
				
				final JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(fc);

				
				    // Open an input stream
				    try {
						
						File file=fc.getSelectedFile();
						String csvFilePath=file.getAbsolutePath();
						
						if(csvFilePath.endsWith(".csv")){
							btnSelect.setEnabled(false);
							txtLoadingpleaseWait.setVisible(true);
							BufferedReader br = null;
							BufferedWriter writer=null;
							String line = "";
							String csvSplitBy = ",";
							
							try {
								int pingTime=3000;
								br = new BufferedReader(new FileReader(csvFilePath));
								
								int posOfDot=csvFilePath.lastIndexOf(".");
								String filePath=csvFilePath.substring(0, posOfDot);
								
								
								int j=1;
								String newFilePath=filePath.concat("_"+j).concat(".csv");
								int posOfSlash=newFilePath.lastIndexOf("\\");
								String newFileName=newFilePath.substring(posOfSlash+1);
								j++;
								String status="Alive";
								File newFile=new File(newFilePath);
								if(!newFile.exists()){
									newFile.createNewFile();
								}
								writer=new BufferedWriter(new FileWriter(newFile));
								
								while ((line = br.readLine()) != null) {
									// use comma as separator
									String[] machine = line.split(csvSplitBy);
									if(machine.length==2){
										try {
						       		       
									       InetAddress address = InetAddress.getByName(machine[1]);
									       if(address.isReachable(pingTime)){
									    	   status="Alive";
									       }
									       else
									       {
									    	   status="Dead";
									       }
									     }
									     catch (UnknownHostException e) {
									       e.printStackTrace();
									     }
									     catch (IOException e) {
									       e.printStackTrace();
									     }
									writer.write(machine[0]+","+machine[1]+",,"+status);
									writer.newLine();
									}
									else if(machine.length==3){
										try {
							       		       
										       InetAddress proxyAddress = InetAddress.getByName(machine[2]);
										       if(proxyAddress.isReachable(pingTime)){
										    	   InetAddress ipAddress = InetAddress.getByName(machine[1]);
										    	   if(ipAddress.isReachable(pingTime)){
										    	   status="Alive";
										    	   }
										    	   else
										    	   {
										    		   status="Dead";
										    	   }
										       }
										       else
										       {
										    	   status="Dead";
										       }
										     }
										     catch (UnknownHostException e) {
										       e.printStackTrace();
										     }
										     catch (IOException e) {
										       e.printStackTrace();
										     }
										writer.write(machine[0]+","+machine[1]+","+machine[2]+","+status);
										writer.newLine();
									}
									
								}
								
								writer.close();
								txtLoadingpleaseWait.setVisible(false);
								JOptionPane.showMessageDialog(fc, "Success!! File created with name "+newFileName);
								btnSelect.setEnabled(true);
								
								
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
								
								if (br != null) {
									try {
										br.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}

						}
						else{
							JOptionPane.showMessageDialog(fc, "File format not supported");
						}
					} 
				    catch (NullPointerException e) {
						// TODO: handle exception
				    	fc.remove(fc);
					}
			}
		});
		
		
	}
}

package ui;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.UserDAO;
import model.User;
import util.SessionManager;

public class LoginScreen extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	JLabel l1_userName;
	JLabel l2_password; 
	JLabel l3_failMessage; 
	JLabel imageLabel;
	JTextField t1_userName;
	JPasswordField t2_password;
	JButton loginButton, registerButton;
	boolean loggedIn = false;
	
	public LoginScreen() {
		setTitle("Poker Professor");
		setSize(700,800);
		setResizable(false);
		setLayout(null);
	
		// Load the image
		ImageIcon originaIcon = new ImageIcon("LoginPic.jpg");
		Image originalImage = originaIcon.getImage();
		Image resizedImage = originalImage.getScaledInstance(700, 550, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        imageLabel = new JLabel(resizedIcon);
		
		l1_userName = new JLabel("User Name");
		t1_userName = new JTextField(30);
		
		l2_password = new JLabel("Password");
		t2_password = new JPasswordField(30);
		
		loginButton = new JButton("Login");
		registerButton = new JButton("Register");
		
		this.add(imageLabel);
		
		this.add(l1_userName);
		this.add(t1_userName);
		
		this.add(l2_password);
		this.add(t2_password);
	
		this.add(loginButton);
		this.add(registerButton);
		
		imageLabel.setBounds(0,0, 700,550);
		
		l1_userName.setBounds(125,580,150,30);
		l1_userName.setFont(new Font("Times new Roman", Font.PLAIN, 20));
		t1_userName.setBounds(275,580,250,30);
		
		l2_password.setBounds(125,630,150,30);
		l2_password.setFont(new Font("Times new Roman", Font.PLAIN, 20));
		t2_password.setBounds(275,630,250,30);
		

		loginButton.setBounds(125,680,100,30);
		loginButton.setFont(new Font("Times new Roman", Font.PLAIN, 20));
		registerButton.setBounds(425,680,100,30);
		registerButton.setFont(new Font("Times new Roman", Font.PLAIN, 20));
	
		
		loginButton.addActionListener(this);
		registerButton.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			String username = t1_userName.getText();
	        String password = new String(t2_password.getPassword());
	        UserDAO userDao = new UserDAO();
	        User user = userDao.findByUsername(username);
	        
	        if (user != null && user.getPassword().equals(password)) {
	        	SessionManager.setUserDetails(user.getUserID(), user.getUserName(), user.getEmail());
	        	JOptionPane.showMessageDialog(this, "Login Successful!");
	        	this.dispose();
				new HomeScreen();
	        }else {
	        	JOptionPane.showMessageDialog(this, "Wrong User Name or Password");
	            //l3_failMessage.setText("Wrong User Name Or Password");
	            t1_userName.setText(""); 
	            t2_password.setText("");
	        }
		}else if (e.getSource() == registerButton) {
			this.dispose();
			new RegisterScreen();		
		}	
	}
	
//	public static void main(String[] args) {
//		LoginScreen login = new LoginScreen();
//	}	
}

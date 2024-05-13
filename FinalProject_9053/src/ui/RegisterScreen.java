package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import dao.UserDAO;
import model.User;

// In this screen user should input userName, email, password
// after input, press a button ("create")
// output a message ("success created")
// back to the login page. 
public class RegisterScreen extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	JLabel l1_userName,l2_password,l3_emailAddress; 
	JTextField t1_userName,t2_password,t3_emailAddress; 
	JButton saveButton, resetButton; 
	
	public RegisterScreen() {
		setTitle("Poker Professor - Sign Up"); 
		setSize(500,500);
		setResizable(false);
		setLayout(null);
		l1_userName = new JLabel("User Name :");
		l2_password = new JLabel("Password : ");
		l3_emailAddress = new JLabel("Email Address :");
		
		t1_userName = new JTextField(20);
		t2_password = new JTextField(20);
		t3_emailAddress = new JTextField(30);
		
		saveButton = new JButton("Create");
        resetButton = new JButton("Reset");

        l1_userName.setBounds(50, 100, 120, 25);
        t1_userName.setBounds(180, 100, 250, 25);

        l2_password.setBounds(50, 150, 120, 25);
        t2_password.setBounds(180, 150, 250, 25);

        l3_emailAddress.setBounds(50, 200, 120, 25);
        t3_emailAddress.setBounds(180, 200, 250, 25);

        saveButton.setBounds(50, 250, 100, 25);
        resetButton.setBounds(180, 250, 100, 25);

        add(l1_userName);
        add(t1_userName);
        add(l2_password);
        add(t2_password);
        add(l3_emailAddress);
        add(t3_emailAddress);
        add(saveButton);
        add(resetButton);

        saveButton.addActionListener(this);
        resetButton.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
		
		
	}

	@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            String userName = t1_userName.getText();
            String password = new String(t2_password.getText());
            String emailAddress = t3_emailAddress.getText();
         
            // Create a new User object
            User newUser = new User(0, userName, password, emailAddress);
            UserDAO userDao = new UserDAO();
            int userId = userDao.registerUser(newUser);
            
            if (userId > 0) {
                JOptionPane.showMessageDialog(this, "User successfully created!");    
                // Redirect to the login screen or close the current window
                this.dispose();
                new LoginScreen();
            }else {
            	JOptionPane.showMessageDialog(this, "Failed to create user. Please try again.");
            }
        } else if (e.getSource() == resetButton) {
            // Clear all text fields
            t1_userName.setText("");
            t2_password.setText("");
            t3_emailAddress.setText("");
        }
    }

//    public static void main(String[] args) {
//        new RegisterScreen();
//    }
	
}

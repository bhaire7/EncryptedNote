package ui;

import database.LoginDB;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private Mainframe mainFrame; // reference to the main frame

    public LoginPanel(Mainframe mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(null);

        JLabel Welcome = new JLabel("Welcome Back!", SwingConstants.CENTER);
        Welcome.setBounds(0,150,586,50);
        Welcome.setForeground(new Color(0x00BFA5));
        Welcome.setFont(new Font("Montserrat", Font.BOLD, 30));
        add(Welcome);

        JLabel Email = new JLabel("Email", SwingConstants.LEFT);
        Email.setBounds(130,250,586,50);
        Email.setFont(new Font("Arial", Font.PLAIN, 15));
        add(Email);

        JTextField emailtx = new JTextField();
        emailtx.setBounds(130,300,400,35);
        add(emailtx);

        JLabel Pass = new JLabel("Password", SwingConstants.LEFT);
        Pass.setBounds(130,360,586,50);
        Pass.setFont(new Font("Arial", Font.PLAIN, 15));
        add(Pass);

        JPasswordField Passf = new JPasswordField();
        Passf.setBounds(130,410,400,35);
        add(Passf);

        JButton Signin = new JButton("Sign In");
        Signin.setBounds(130,500,140,30);
        Signin.setBorderPainted(false);
        Signin.setFocusPainted(false);
        Signin.setBackground(new Color(0x00C9A7));
        Signin.setForeground(Color.white);
        add(Signin);

        Signin.addActionListener(e -> {
            String email = emailtx.getText();
            String password = new String(Passf.getPassword());

            if (LoginDB.authenticate(email, password)) {
                SwingUtilities.getWindowAncestor(this).dispose();
                new LoginSucessfull();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Not able to log in.\nCheck your details and try again!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton Reg = new JButton("Register Now!");
        Reg.setBounds(310,500,140,30);
        Reg.setBackground(new Color(0x2196F3));
        Reg.setForeground(Color.white);
        Reg.setBorderPainted(false);
        Reg.setFocusPainted(false);
        add(Reg);

        // Switch to Register Panel
        Reg.addActionListener(e -> mainFrame.showPanel("register"));
    }
}

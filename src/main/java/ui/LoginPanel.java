package ui;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {



    public LoginPanel() {


        setLayout(null);


        JLabel Welcome = new JLabel("Welcome Back!",SwingConstants.CENTER);
        Welcome.setBounds(0,150,586,50);
        Welcome.setForeground(new Color(0x00BFA5));
        Welcome.setFont(new Font("Montserrat", Font.BOLD, 30));
        add(Welcome);

        JLabel Email = new JLabel("Email",SwingConstants.LEFT);
        Email.setBounds(130,250,586,50);
        Email.setFont(new Font("Arial", Font.PLAIN, 15));
        add(Email);

        JTextField emailtx = new JTextField();
        emailtx.setBounds(130,300,400,35);
        add(emailtx);

        JLabel Pass = new JLabel("Password",SwingConstants.LEFT);
        Pass.setBounds(130,360,586,50);
        Pass.setFont(new Font("Arial", Font.PLAIN, 15));
        add(Pass);

        JPasswordField Passf = new JPasswordField();
        Passf.setBounds(130,410,400,35);
        add(Passf);

        JButton Signin = new JButton("Sign In");
        Signin.setBounds(130,500,140,30);
        Signin.addActionListener(e -> {
            String email = emailtx.getText();
            String password = new String (Passf.getPassword());
            System.out.println("Email:"+ email + "\nPassword:"+ password );

        });
        Signin.setBorderPainted(false);
        Signin.setFocusPainted(false);
        Signin.setBackground(new Color(0x00C9A7));
        Signin.setForeground(Color.white);
        add(Signin);

        JButton Reg = new JButton("Register Now!");
        Reg.setBounds(310,500,140,30);
        Reg.setBackground(new Color(0x2196F3));
        Reg.setForeground(Color.white);
        Reg.setBorderPainted(false);
        Reg.setFocusPainted(false);
        add(Reg);



    }




//    @Override
//    protected void paintComponent(Graphics g){
//        super.paintComponent(g);
//        Graphics2D gd = (Graphics2D) g;
//        int  width = getWidth();
//        int height = getHeight();
//        System.out.println(width);
//
//        Color col1 = new Color(255, 255, 255);
//        Color col2 = new Color(65, 65, 65);
//
//        GradientPaint gp = new GradientPaint(0,0,col1,0,height,col2);
//        gd.setPaint(gp);
//        gd.fillRect(0,0,width,height);
//    }
}

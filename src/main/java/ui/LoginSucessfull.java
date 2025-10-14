package ui;

import javax.swing.*;
import java.awt.*;

public class LoginSucessfull extends JFrame {

    public LoginSucessfull() {
        setTitle("Login Successful");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null); // center the window

        JLabel success = new JLabel("Successfully Logged In!", SwingConstants.CENTER);
        success.setFont(new Font("Arial", Font.BOLD, 32));
        success.setForeground(new Color(0x00BFA5));

        add(success, BorderLayout.CENTER);

        setVisible(true);
    }

    // Optional: allow direct running for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginSucessfull::new);
    }
}

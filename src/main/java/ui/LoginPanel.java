package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import database.AdminDB;
import database.LoginDB;

public class LoginPanel extends JPanel {

    private Mainframe mainFrame;

    public LoginPanel(Mainframe mainFrame) {
        this.mainFrame = mainFrame;
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel welcomeLabel = new JLabel("Welcome Back!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Montserrat", Font.BOLD, 36));
        welcomeLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(welcomeLabel, gbc);

        // Email Field
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        add(new JLabel("Email"), gbc);

        gbc.gridy = 2;
        JTextField emailField = new JTextField(20);
        add(emailField, gbc);

        // Password Field
        gbc.gridy = 3;
        add(new JLabel("Password"), gbc);

        gbc.gridy = 4;
        JPasswordField passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        // Sign In Button
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton signInButton = createStyledButton("Sign In", new Color(0x00BFA5));
        add(signInButton, gbc);

        // Register Button
        gbc.gridy = 6;
        JButton registerButton = createStyledButton("Register Now!", new Color(0x2196F3));
        add(registerButton, gbc);

        // Admin Button
        gbc.gridy = 7;
        JButton adminButton = createStyledButton("Admin", new Color(0xF39C12));
        add(adminButton, gbc);

        // Action Listeners
        signInButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (LoginDB.authenticate(email, password)) {
                SwingUtilities.getWindowAncestor(this).dispose();
                new LoginSucessfull();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> mainFrame.showPanel("register"));

        adminButton.addActionListener(e -> {
            String adminCode = JOptionPane.showInputDialog(this, "Enter Admin Code:", "Admin Login", JOptionPane.PLAIN_MESSAGE);
            if (adminCode == null) return;

            String adminUsername = JOptionPane.showInputDialog(this, "Enter Admin Username:", "Admin Login", JOptionPane.PLAIN_MESSAGE);
            if (adminUsername == null) return;

            JPasswordField adminPasswordField = new JPasswordField();
            int option = JOptionPane.showConfirmDialog(this, adminPasswordField, "Enter Admin Password:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option != JOptionPane.OK_OPTION) return;

            String adminPassword = new String(adminPasswordField.getPassword());

            if (AdminDB.authenticate(adminCode, adminUsername, adminPassword)) {
                mainFrame.showPanel("admin");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Admin Credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Montserrat", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        Color color1 = new Color(0xFFFFFF);
        Color color2 = new Color(0xE0E0E0);
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
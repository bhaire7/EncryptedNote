package ui.LogInRegisterUI;

import java.awt.Color;
import java.awt.Dimension;
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

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class RegisterPanel extends JPanel {
    private Mainframe mainFrame;
    private JTextField userField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public RegisterPanel(Mainframe mainFrame) {
        this.mainFrame = mainFrame;
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Create Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 36));
        titleLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Username Field
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        add(new JLabel("Username"), gbc);

        gbc.gridy = 2;
        userField = new JTextField(20);
        userField.setPreferredSize(new Dimension(200, 30));
        add(userField, gbc);

        // Email Field
        gbc.gridy = 3;
        add(new JLabel("Email"), gbc);

        gbc.gridy = 4;
        emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(200, 30));
        add(emailField, gbc);

        // Password Field
        gbc.gridy = 5;
        add(new JLabel("Password"), gbc);

        gbc.gridy = 6;
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(200, 30));
        add(passwordField, gbc);

        // Sign Up Button
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        JButton signUpButton = createStyledButton("Sign Up", new Color(0x00BFA5));
        add(signUpButton, gbc);

        // Back to Login Button
        gbc.gridy = 8;
        JButton backToLoginButton = createStyledButton("Back to Login", new Color(0x2196F3));
        add(backToLoginButton, gbc);

        // Action Listeners
        signUpButton.addActionListener(e -> {
            String username = userField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (username.length() < 4) {
                JOptionPane.showMessageDialog(this, "Username must be at least 4 characters long", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password.length() < 5) {
                JOptionPane.showMessageDialog(this, "Password must be at least 5 characters long", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid Email Format", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String uri = "mongodb://localhost:27017";
            try (MongoClient mongoClient = MongoClients.create(uri)) {
                MongoDatabase db = mongoClient.getDatabase("LoginInfo");
                MongoCollection<Document> collection = db.getCollection("Logindetail");
                Document doc = new Document("user_name", username).append("email", email).append("password", password);
                collection.insertOne(doc);
                JOptionPane.showMessageDialog(this, "Registration successful!");
                clearFields();
                mainFrame.showPanel("login");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Registration failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backToLoginButton.addActionListener(e -> {
            clearFields();
            mainFrame.showPanel("login");
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

    private void clearFields() {
        userField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
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
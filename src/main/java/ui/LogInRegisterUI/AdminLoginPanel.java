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

public class AdminLoginPanel extends JPanel {
    private Mainframe mainFrame;
    private JTextField userField;
    private JPasswordField passwordField;

    public AdminLoginPanel(Mainframe mainFrame) {
        this.mainFrame = mainFrame;
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Admin Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 36));
        titleLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Username Field
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("Admin Username:"), gbc);
        gbc.gridy = 2;
        userField = new JTextField("admin", 20);
        userField.setPreferredSize(new Dimension(200, 30));
        userField.setEditable(false); // Admin username is fixed
        add(userField, gbc);

        // Password Field
        gbc.gridy = 3;
        add(new JLabel("Admin Password:"), gbc);
        gbc.gridy = 4;
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(200, 30));
        add(passwordField, gbc);

        // Login Button
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton loginButton = createStyledButton("Login", new Color(0x00BFA5));
        add(loginButton, gbc);

        // Back Button
        gbc.gridy = 6;
        JButton backButton = createStyledButton("Back to Main Login", new Color(0x757575));
        add(backButton, gbc);

        // Action Listeners
        loginButton.addActionListener(e -> handleAdminLogin());
        backButton.addActionListener(e -> mainFrame.showPanel("login"));
    }

    public void clearPasswordField() {
        passwordField.setText("");
    }

    private void handleAdminLogin() {
        String username = userField.getText();
        String password = new String(passwordField.getPassword());

        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("adminDb");
            MongoCollection<Document> collection = db.getCollection("admins");

            // Step 1: Find the admin user by username
            Document adminUser = collection.find(new Document("username", "admin")).first();

            // Step 2: If user exists, check the password
            if (adminUser != null) {
                String storedPassword = adminUser.getString("password");
                if (storedPassword.equals(password)) {
                    // Success: Password matches
                    mainFrame.showPanel(new AdminPanel(mainFrame));
                } else {
                    // Failure: Password does not match
                    JOptionPane.showMessageDialog(this, "Invalid admin credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Admin user not found in the correct database
                JOptionPane.showMessageDialog(this, "Admin user not found in database.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
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
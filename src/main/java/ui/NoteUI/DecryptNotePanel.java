package ui.NoteUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.bson.Document;

import database.NoteDB;
import utils.CryptoUtils;

public class DecryptNotePanel extends JPanel {

    private final String username;
    private final JTextField titleField;
    private final JTextArea contentArea;
    private final JPasswordField passwordField;
    private final JButton decodeButton;
    private final JButton encryptButton;
    private String currentTitle;
    private boolean isNewNote = false;
    private Runnable onNoteSaved;

    public DecryptNotePanel(String username) {
        this.username = username;
        setOpaque(false);
        setLayout(new BorderLayout(10, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Title Panel ---
        JPanel titlePanel = new JPanel(new BorderLayout(10, 0));
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 16));
        titleLabel.setForeground(new Color(50, 50, 90));
        
        titleField = new JTextField();
        titleField.setFont(new Font("Lato", Font.PLAIN, 16));
        titleField.setOpaque(false);
        titleField.setEditable(false); // Not editable by default
        titleField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(120, 150, 220)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        titlePanel.add(titleLabel, BorderLayout.WEST);
        titlePanel.add(titleField, BorderLayout.CENTER);

        // --- Note Content Area ---
        JPanel contentPanel = new JPanel(new BorderLayout(0, 5));
        contentPanel.setOpaque(false);
        JLabel contentLabel = new JLabel("Note Content:");
        contentLabel.setFont(new Font("Montserrat", Font.BOLD, 16));
        contentLabel.setForeground(new Color(50, 50, 90));
        
        contentArea = new JTextArea();
        contentArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        contentScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 220)));
        contentPanel.add(contentLabel, BorderLayout.NORTH);
        contentPanel.add(contentScrollPane, BorderLayout.CENTER);

        // --- Bottom Decryption Panel ---
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Montserrat", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(50, 50, 90));
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Lato", Font.PLAIN, 14));
        passwordField.setOpaque(false);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(120, 150, 220)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        decodeButton = createStyledButton("Decrypt", new Color(0x00BFA5));
        decodeButton.addActionListener(e -> handleDecrypt());
        encryptButton = createStyledButton("Encrypt", new Color(0x42A5F5));
        encryptButton.addActionListener(e -> handleEncryptAndSave());

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_END;
        bottomPanel.add(passwordLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.LINE_START;
        bottomPanel.add(passwordField, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.LINE_END; gbc.fill = GridBagConstraints.NONE;
        bottomPanel.add(decodeButton, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_START;
        bottomPanel.add(encryptButton, gbc);

        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void setOnNoteSaved(Runnable onNoteSaved) {
        this.onNoteSaved = onNoteSaved;
    }

    public void displayNote(String title) {
        this.currentTitle = title;
        this.isNewNote = false;
        
        Document note = NoteDB.getNote(username, title);
        if (note != null) {
            titleField.setText(note.getString("title"));
            contentArea.setText(note.getString("content")); // This will be encrypted text
            passwordField.setText(""); // Clear password field
        }
        titleField.setEditable(false);
        contentArea.setEditable(false);
        decodeButton.setVisible(true);
        encryptButton.setVisible(false);
    }

    public void prepareForNewNote() {
        this.currentTitle = null;
        this.isNewNote = true;
        
        titleField.setText("");
        contentArea.setText("");
        passwordField.setText("");
        
        titleField.setEditable(true);
        contentArea.setEditable(true);
        decodeButton.setVisible(false);
        encryptButton.setVisible(true);
        titleField.requestFocusInWindow();
    }

    private void handleEncryptAndSave() {
        String title = titleField.getText().trim();
        String content = contentArea.getText();
        String password = new String(passwordField.getPassword());

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Encrypt the content before saving
        String encryptedContent = CryptoUtils.encrypt(content, password);

        if (isNewNote) {
            NoteDB.createNote(username, title, encryptedContent, password); // Store encrypted content and password
            JOptionPane.showMessageDialog(this, "Note created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Transition from "new note" state to "editing existing note" state
            this.currentTitle = title;
            this.isNewNote = false;
            titleField.setEditable(false);

        } else {
            NoteDB.updateNote(username, currentTitle, encryptedContent, password); // Store encrypted content and password
            JOptionPane.showMessageDialog(this, "Note saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        // Notify listener to refresh the notes list in both cases
        if (onNoteSaved != null) {
            onNoteSaved.run();
        }

        // Update UI to show encrypted state
        contentArea.setText(encryptedContent);
        contentArea.setEditable(false);
        encryptButton.setVisible(false);
        decodeButton.setVisible(true);
        passwordField.setText(""); // Clear password after encryption
    }

    private void handleDecrypt() {
        String encryptedContent = contentArea.getText();
        String password = new String(passwordField.getPassword());

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a password to decrypt.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (encryptedContent.isEmpty()) {
            JOptionPane.showMessageDialog(this, "There is no content to decrypt.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            String decryptedContent = CryptoUtils.decrypt(encryptedContent, password);
            if (decryptedContent != null) {
                contentArea.setText(decryptedContent);
                contentArea.setEditable(true);
                encryptButton.setVisible(true);
                decodeButton.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Wrong password or corrupted data.", "Decryption Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred during decryption.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Montserrat", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
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
        Color color1 = new Color(230, 240, 255);
        Color color2 = new Color(245, 245, 255);
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);}
    

    public void clearPanel() {
        currentTitle = null;
        isNewNote = false;
        titleField.setText("");
        contentArea.setText("");
        passwordField.setText("");
        titleField.setEditable(false);
        decodeButton.setVisible(true);
    }
}
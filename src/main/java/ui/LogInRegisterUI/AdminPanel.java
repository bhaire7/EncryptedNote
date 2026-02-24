package ui.LogInRegisterUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.bson.Document;

import database.NoteDB;
import database.UserDB;

public class AdminPanel extends JPanel {
    private Mainframe mainFrame;

    private DefaultListModel<String> listModel;
    private JList<String> userList;
    private DefaultListModel<String> noteListModel;
    private JList<String> noteTitlesList;
    private JTextArea noteContentArea;
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField passwordField;

    private List<Document> users;
    private Document selectedUser;

    public AdminPanel(Mainframe mainFrame) {
        this.mainFrame = mainFrame;
        setOpaque(false);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Header ---
        JLabel headerLabel = new JLabel("Admin Dashboard");
        headerLabel.setFont(new Font("Montserrat", Font.BOLD, 28));
        headerLabel.setForeground(new Color(50, 50, 90));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        // --- Main Split Pane ---
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(250);
        splitPane.setOpaque(false);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        add(splitPane, BorderLayout.CENTER);

        // --- Left Panel (User List) ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        listModel = new DefaultListModel<>();
        userList = new JList<>(listModel);
        userList.setCellRenderer(new UserListCellRenderer());
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setOpaque(false);
        JScrollPane listScrollPane = new JScrollPane(userList);
        listScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 230)));
        listScrollPane.getViewport().setOpaque(false);
        leftPanel.add(listScrollPane, BorderLayout.CENTER);
        splitPane.setLeftComponent(leftPanel);

        // --- Right Panel (User Details & Notes) ---
        JPanel rightPanel = new JPanel(new BorderLayout(10, 20)); // Increased gap
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        splitPane.setRightComponent(rightPanel);

        // Panel to hold both form and notes
        JPanel detailsAndNotesPanel = new JPanel(new BorderLayout(10, 15));
        detailsAndNotesPanel.setOpaque(false);
        rightPanel.add(detailsAndNotesPanel, BorderLayout.CENTER);

        // Form for details
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 220)),
            "User Information",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Montserrat", Font.BOLD, 16),
            new Color(50, 50, 90)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        usernameField = createStyledTextField();
        usernameField.setEditable(false);
        emailField = createStyledTextField();
        passwordField = createStyledTextField();

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(createFormLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; formPanel.add(usernameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(createFormLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(createFormLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(passwordField, gbc);
        detailsAndNotesPanel.add(formPanel, BorderLayout.NORTH);

        // --- Notes Panel ---
        JPanel notesPanel = new JPanel(new BorderLayout());
        notesPanel.setOpaque(false);
        notesPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 220)),
            "User's Notes",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Montserrat", Font.BOLD, 16),
            new Color(50, 50, 90)
        ));

        noteListModel = new DefaultListModel<>();
        noteTitlesList = new JList<>(noteListModel);
        noteTitlesList.setFont(new Font("Lato", Font.PLAIN, 14));
        noteTitlesList.setCellRenderer(new NoteTitleCellRenderer());
        noteTitlesList.setOpaque(false);
        JScrollPane notesScrollPane = new JScrollPane(noteTitlesList);
        notesScrollPane.getViewport().setOpaque(false);
        notesScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        noteContentArea = new JTextArea();
        noteContentArea.setOpaque(false);
        noteContentArea.setEditable(false);
        noteContentArea.setFont(new Font("Lato", Font.PLAIN, 14));
        noteContentArea.setLineWrap(true);
        noteContentArea.setWrapStyleWord(true);
        noteContentArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JScrollPane contentScrollPane = new JScrollPane(noteContentArea);
        contentScrollPane.getViewport().setOpaque(false);
        contentScrollPane.setBorder(BorderFactory.createEmptyBorder());

        JSplitPane notesSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, notesScrollPane, contentScrollPane);
        notesSplitPane.setDividerLocation(150);
        notesSplitPane.setOpaque(false);
        notesSplitPane.setBorder(BorderFactory.createEmptyBorder());

        notesPanel.add(notesSplitPane, BorderLayout.CENTER);
        detailsAndNotesPanel.add(notesPanel, BorderLayout.CENTER);


        // Button panel for actions
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setOpaque(false);
        JButton saveButton = createStyledButton("Save Changes", new Color(0x42A5F5));
        JButton deleteUserButton = createStyledButton("Delete User", new Color(0xEF5350));
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteUserButton);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setOpaque(false);
        JButton backButton = createStyledButton("Back to Login", new Color(0x757575));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Load data and set listeners ---
        try {
            loadUsers();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Failed to connect to the database. Please ensure MongoDB is running.",
                "Database Connection Error", JOptionPane.ERROR_MESSAGE);
            javax.swing.SwingUtilities.invokeLater(() -> mainFrame.showPanel("login"));
            return;
        }

        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = userList.getSelectedIndex();
                if (selectedIndex != -1) {
                    selectedUser = users.get(selectedIndex);
                    displayUserDetails(selectedUser);
                    displayUserNotes(selectedUser.getString("user_name"));
                } else {
                    clearSelection();
                }
            }
        });

        noteTitlesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedTitle = noteTitlesList.getSelectedValue();
                if (selectedUser != null && selectedTitle != null && !selectedTitle.equals("No notes found for this user.")) {
                    displayNoteContent(selectedUser.getString("user_name"), selectedTitle);
                } else {
                    noteContentArea.setText("");
                }
            }
        });

        saveButton.addActionListener(e -> saveUserChanges());
        deleteUserButton.addActionListener(e -> deleteUser());
        backButton.addActionListener(e -> mainFrame.showPanel("login"));
    }

    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Montserrat", Font.BOLD, 14));
        label.setForeground(new Color(50, 50, 90));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Lato", Font.PLAIN, 14));
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(120, 150, 220)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField pwField = new JPasswordField(20);
        pwField.setFont(new Font("Lato", Font.PLAIN, 14));
        pwField.setOpaque(false);
        pwField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(120, 150, 220)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return pwField;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Montserrat", Font.BOLD, 12));
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
        Color color1 = new Color(245, 245, 255);
        Color color2 = new Color(230, 240, 255);
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }

    private void loadUsers() {
        listModel.clear();
        users = UserDB.getAllUsers();
        for (Document user : users) {
            listModel.addElement(user.getString("user_name"));
        }
    }

    private void displayUserDetails(Document user) {
        usernameField.setText(user.getString("user_name"));
        emailField.setText(user.getString("email"));
        passwordField.setText(user.getString("password"));
    }

    private void displayUserNotes(String username) {
        noteListModel.clear();
        if (username != null && !username.isEmpty()) {
            List<String> titles = NoteDB.getNoteTitles(username);
            if (titles.isEmpty()) {
                noteListModel.addElement("No notes found for this user.");
            } else {
                for (String title : titles) {
                    noteListModel.addElement(title);
                }
            }
        }
        clearNoteSelection();
    }

    private void displayNoteContent(String username, String title) {
        Document note = NoteDB.getNoteByTitle(username, title);
        if (note != null) {
            String content = note.getString("content");
            String password = note.getString("password");
            if (password != null && !password.isEmpty()) {
                noteContentArea.setText("This note is password protected.");
            } else {
                noteContentArea.setText(content);
            }
        } else {
            noteContentArea.setText("");
        }
    }

    private void saveUserChanges() {
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "No user selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String originalEmail = selectedUser.getString("email");
        String newEmail = emailField.getText();
        String newPassword = passwordField.getText();

        if (newEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email field must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // The updateUser method expects individual strings, not a Document
        UserDB.updateUser(originalEmail, selectedUser.getString("user_name"), newEmail, newPassword);
        
        JOptionPane.showMessageDialog(this, "User details updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        
        // Refresh the user list and re-select the user
        loadUsers();
        
        // Find the new index of the user and re-select them
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getString("email").equals(newEmail)) {
                userList.setSelectedIndex(i);
                break;
            }
        }
    }

    private void deleteUser() {
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "No user selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this user and all their notes?\nThis action cannot be undone.",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            String username = selectedUser.getString("user_name");
            String email = selectedUser.getString("email");

            UserDB.deleteUser(email);
            NoteDB.dropUserDatabase(username);

            JOptionPane.showMessageDialog(this, "User '" + username + "' has been deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadUsers();
            clearSelection();
        }
    }

    private void clearSelection() {
        userList.clearSelection();
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        selectedUser = null;
        noteListModel.clear();
        clearNoteSelection();
    }

    private void clearNoteSelection() {
        noteTitlesList.clearSelection();
        noteContentArea.setText("");
    }

    // Custom cell renderer for the JList
    private static class UserListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setFont(new Font("Lato", Font.PLAIN, 16));
            label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            label.setOpaque(true);

            if (isSelected) {
                label.setBackground(new Color(120, 150, 220));
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(index % 2 == 0 ? new Color(250, 250, 255) : new Color(245, 245, 250));
                label.setForeground(new Color(70, 70, 110));
            }
            return label;
        }
    }

    // Custom cell renderer for the Note Titles JList
    private static class NoteTitleCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setFont(new Font("Lato", Font.ITALIC, 14));
            label.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            label.setOpaque(true);

            // A simple, clean look for the notes list
            if (isSelected) {
                label.setBackground(new Color(200, 210, 230));
                label.setForeground(new Color(50, 50, 90));
            } else {
                label.setBackground(Color.WHITE);
                label.setForeground(new Color(80, 80, 120));
            }
            return label;
        }
    }
}
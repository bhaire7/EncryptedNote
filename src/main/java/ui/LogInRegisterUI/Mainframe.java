package ui.LogInRegisterUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.NoteUI.HomePage;

public class Mainframe extends JFrame {

    private final JPanel detailsPanel;
    private final JPanel centerPanel;
    private final CardLayout cardLayout;

    private final LoginPanel loginPanel;
    private final RegisterPanel registerPanel;
    private final AdminPanel adminPanel;

    public Mainframe() {
        setTitle("Login Page");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Left details panel
        detailsPanel = new DetailPanel();


        // Center panel with CardLayout
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);

        // Pass this Mainframe reference into LoginPanel
        loginPanel = new LoginPanel(this);
        registerPanel = new RegisterPanel(this);
        adminPanel = new AdminPanel(this);

        centerPanel.add(loginPanel, "login");
        centerPanel.add(registerPanel, "register");
        centerPanel.add(adminPanel, "admin");

        add(detailsPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);

        cardLayout.show(centerPanel, "login"); // show login first
        setVisible(true);
    }

    // Method to switch cards
    public void showPanel(String name) {
        cardLayout.show(centerPanel, name);
    }

    public void showHomePage(String username, String email) {
        dispose(); // Close the login frame
        new HomePage(username, email);
    }


}
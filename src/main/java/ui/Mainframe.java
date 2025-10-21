package ui;

import javax.swing.*;
import java.awt.*;

public class Mainframe extends JFrame {

    private JPanel detailsPanel;
    private JPanel centerPanel;
    private CardLayout cardLayout;

    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;

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

        centerPanel.add(loginPanel, "login");
        centerPanel.add(registerPanel, "register");

        add(detailsPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);

        cardLayout.show(centerPanel, "login"); // show login first
        setVisible(true);
    }

    // Method to switch cards
    public void showPanel(String name) {
        cardLayout.show(centerPanel, name);
    }


}

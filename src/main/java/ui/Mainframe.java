package ui;

import javax.swing.*;
import java.awt.*;

public class Mainframe extends JFrame {

    public Mainframe() {
        setTitle("Login Page");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Create panel objects
        DetailPanel detailsPanel = new DetailPanel();
        LoginPanel loginPanel = new LoginPanel();

        // Add panels to frame
        add(detailsPanel, BorderLayout.WEST);
        add(loginPanel, BorderLayout.CENTER);

        setVisible(true);
    }


}


package ui;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {
    private Mainframe mainFrame;

    public AdminPanel(Mainframe mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Admin Panel", SwingConstants.CENTER);
        title.setFont(new Font("Montserrat", Font.BOLD, 30));
        add(title, BorderLayout.NORTH);

        JButton backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> mainFrame.showPanel("login"));
        add(backButton, BorderLayout.SOUTH);
    }
}
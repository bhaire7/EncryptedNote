package ui;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class HomePage extends JFrame {

    private JSplitPane splitPane;
    private TitlePanel TitlePanel;
    private DecryptNotePanel rightPanel;

    public HomePage(String username) {
        setTitle("Encrypted Note Keeper");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Left Panel (for notes list)
        TitlePanel = new TitlePanel(username);

        // Right Panel (for note content)
        rightPanel = new DecryptNotePanel();

        // Split Pane to divide the two panels
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TitlePanel, rightPanel);
        splitPane.setDividerLocation(350);
        splitPane.setEnabled(false); // Disables user from resizing the divider

        add(splitPane);
        setVisible(true);
    }
}
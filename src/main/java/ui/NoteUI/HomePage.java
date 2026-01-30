package ui.NoteUI;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class HomePage extends JFrame {

    private final JSplitPane splitPane;
    private final TitlePanel titlePanel;
    private final DecryptNotePanel decryptNotePanel;

    public HomePage(String username) {
        setTitle("Encrypted Note Keeper");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Left Panel (for notes list)
        titlePanel = new TitlePanel(username);

        // Right Panel (for note content)
        decryptNotePanel = new DecryptNotePanel();

        // Split Pane to divide the two panels
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, titlePanel, decryptNotePanel);
        splitPane.setDividerLocation(350);
        splitPane.setEnabled(false); // Disables user from resizing the divider

        add(splitPane);
        setVisible(true);
    }
}
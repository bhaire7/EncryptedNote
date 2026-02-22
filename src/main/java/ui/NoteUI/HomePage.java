package ui.NoteUI;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class HomePage extends JFrame {

    private final JSplitPane splitPane;
    private final TitlePanel titlePanel;
    private final DecryptNotePanel decryptNotePanel;

    public HomePage(String username, String userEmail) {
        setTitle("Encrypted Note Keeper");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Left Panel (for notes list)
        titlePanel = new TitlePanel(username, userEmail);

        // Right Panel (for note content)
        decryptNotePanel = new DecryptNotePanel(username);

        // Set up the listeners
        titlePanel.setNoteSelectionListener(title -> decryptNotePanel.displayNote(title));
        titlePanel.setNewNoteListener(() -> decryptNotePanel.prepareForNewNote());
        titlePanel.setOnNoteDeleted(() -> decryptNotePanel.clearPanel());
        decryptNotePanel.setOnNoteSaved(() -> titlePanel.refreshNotesList());

        // Split Pane to divide the two panels
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, titlePanel, decryptNotePanel);
        splitPane.setDividerLocation(350);
        splitPane.setEnabled(false); // Disables user from resizing the divider

        add(splitPane);
        setVisible(true);
    }
}
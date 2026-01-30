package ui.NoteUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class TitlePanel extends JPanel {

    public TitlePanel(String username) {
        setOpaque(false);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Header Panel ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(50, 50, 90));
        JButton logoutButton = createStyledButton("Logout", new Color(0xEF5350));
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);
        headerPanel.add(logoutButton, BorderLayout.EAST);

        // --- Notes Section ---
        JPanel notesSectionPanel = new JPanel(new BorderLayout());
        notesSectionPanel.setOpaque(false);
        JLabel yourNotesLabel = new JLabel("Your Notes");
        yourNotesLabel.setFont(new Font("Montserrat", Font.BOLD, 24));
        yourNotesLabel.setForeground(new Color(50, 50, 90));
        yourNotesLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 0));
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        // TODO: Populate this list from the database
        listModel.addElement("My Social Media Pass");
        listModel.addElement("My Private Diary");
        listModel.addElement("Project Idea");
        
        JList<String> notesList = new JList<>(listModel);
        notesList.setCellRenderer(new NoteListCellRenderer());
        notesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        notesList.setOpaque(false);
        JScrollPane listScrollPane = new JScrollPane(notesList);
        listScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 230)));
        listScrollPane.getViewport().setOpaque(false);

        notesSectionPanel.add(yourNotesLabel, BorderLayout.NORTH);
        notesSectionPanel.add(listScrollPane, BorderLayout.CENTER);

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        JButton newNoteButton = createStyledButton("New Note", new Color(0x00BFA5));
        JButton deleteButton = createStyledButton("Delete", new Color(0x757575));
        buttonPanel.add(newNoteButton);
        buttonPanel.add(deleteButton);

        add(headerPanel, BorderLayout.NORTH);
        add(notesSectionPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Montserrat", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
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

    // Custom cell renderer for the JList
    private static class NoteListCellRenderer extends DefaultListCellRenderer {
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
}
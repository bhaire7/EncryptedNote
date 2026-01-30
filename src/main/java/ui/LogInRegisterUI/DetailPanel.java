package ui.LogInRegisterUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DetailPanel extends JPanel {

    public DetailPanel() {
        setPreferredSize(new Dimension(400, 700));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBorder(BorderFactory.createEmptyBorder(50, 40, 50, 40));

        add(Box.createVerticalStrut(20));

        // Custom Logo
        add(new SecureNoteIcon());

        add(Box.createVerticalStrut(30));

        // Main Heading
        JLabel mainHeading = createLabel("<html><div style='text-align:center;'>Your Secure<br>Digital Notebook</div></html>",
                new Font("Montserrat", Font.BOLD, 32), new Color(50, 50, 90));
        add(mainHeading);

        add(Box.createVerticalStrut(15));

        // Tagline
        JLabel tagline = createLabel("Privacy, Perfected.",
                new Font("Lato", Font.ITALIC, 18), new Color(80, 80, 120));
        add(tagline);

        add(Box.createVerticalStrut(50));

        // Feature Highlights
        add(createFeaturePanel("End-to-End Encryption", "Your notes are for your eyes only. We use state-of-the-art encryption to keep your data safe."));
        add(Box.createVerticalStrut(30));
        add(createFeaturePanel("Intuitive & Simple", "A clean, beautiful interface designed to help you focus on what matters most: your thoughts."));
        add(Box.createVerticalStrut(30));
        add(createFeaturePanel("Organize with Ease", "Effortlessly categorize, search, and manage your notes, so you can find what you need in seconds."));

        add(Box.createVerticalGlue()); // Pushes content to the top
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JPanel createFeaturePanel(String title, String description) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = createLabel(title, new Font("Montserrat", Font.BOLD, 16), new Color(60, 60, 100));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = createLabel("<html><div style='text-align:center; width:250px;'>" + description + "</div></html>",
                new Font("Lato", Font.PLAIN, 14), new Color(100, 100, 140));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(descLabel);

        return panel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        Color color1 = new Color(220, 237, 255); // Light Sky Blue
        Color color2 = new Color(240, 240, 255); // Lavender White
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }

    // A simple custom component to draw a logo
    private static class SecureNoteIcon extends JComponent {
        SecureNoteIcon() {
            setPreferredSize(new Dimension(80, 80));
            setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Outer shape (document)
            g2d.setColor(new Color(120, 150, 220));
            g2d.fillRoundRect(10, 10, 60, 70, 15, 15);

            // Inner shape (lines of text)
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine(25, 30, 55, 30);
            g2d.drawLine(25, 40, 55, 40);
            g2d.drawLine(25, 50, 45, 50);

            // Lock icon
            g2d.setColor(new Color(255, 215, 0)); // Gold
            g2d.fillOval(45, 55, 20, 20); // Lock body
            g2d.setColor(new Color(50, 50, 90));
            g2d.setStroke(new BasicStroke(2));
            g2d.drawArc(50, 48, 10, 12, 0, 180); // Shackle
            g2d.drawLine(50, 54, 50, 65);
            g2d.drawLine(60, 54, 60, 65);

            g2d.dispose();
        }
    }
}
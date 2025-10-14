package ui;

import javax.swing.*;
        import java.awt.*;

public class DetailPanel extends JPanel {

    public DetailPanel() {
        setPreferredSize(new Dimension(400, 700)); // left panel width
        setLayout(null); // custom placement

        // Logo
        JLabel logo = new JLabel("Logo");
        logo.setBounds(20, 20, 100, 30);
        logo.setForeground(Color.white);
        add(logo);

        // Heading
        JLabel SHeading = new JLabel("Encrypted Note Keeping Software");
        SHeading.setForeground(Color.WHITE);
        SHeading.setBounds(20, 70, 400, 30);
        SHeading.setFont(new Font("Poppins", Font.BOLD, 18));
        add(SHeading);

        // Description
        JLabel SDec = new JLabel();
        SDec.setText("<html><div style='text-align:left;'>"
                + "<p>This application is a robust and secure solution designed for individuals who need to store and manage personal or confidential notes with complete peace of mind. Whether you're jotting down sensitive ideas, private reminders, or important records, your data remains protected at all times.</p>"
                + "<p>Using advanced encryption algorithms, the software ensures that your notes are shielded from unauthorized access, making it ideal for professionals, students, and anyone who values privacy. Even if your device is compromised, your notes remain inaccessible without proper authentication.</p>"
                + "<p>Its intuitive and user-friendly interface allows you to effortlessly create, edit, organize, and retrieve notes whenever you need them. Features like search functionality, categorization, and auto-save make note management seamless and efficient.</p>"
                + "<p>Whether you're working offline or online, the application adapts to your workflow, ensuring your notes are always available and secure. Experience the perfect blend of simplicity, security, and reliability with Encrypted Note Keeping Software.</p>"
                + "</div></html>");
        SDec.setFont(new Font("Poppins", Font.BOLD, 15));
        SDec.setBounds(20, 20, 360, 700);
        SDec.setForeground(new Color(0xEAFDFD));
        add(SDec);

        // Image logo (optional)
//        ImageIcon originalIcon = new ImageIcon("Projects/LoginPage/src/enk.png");
//        Image scaledImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
//        ImageIcon dlogo = new ImageIcon(scaledImage);
//
//        JLabel logoLabel = new JLabel(dlogo);
//        logoLabel.setBounds(40, 340, 200, 200);
//        add(logoLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Use Graphics2D for gradient
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Create a gradient from top-left to bottom-right
        Color color1 = new Color(0x00E4D0); // light blue
        Color color2 = new Color(0x5983E8); // soft teal-blue

        GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color1);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);
    }
}

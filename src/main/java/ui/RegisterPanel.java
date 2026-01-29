package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class RegisterPanel extends JPanel {
    private Mainframe mainFrame;
    private JTextField UserField;
    private JTextField emailtx;
    private JPasswordField Passf;

    public RegisterPanel(Mainframe mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(null);


        JLabel title = new JLabel("Register!!", SwingConstants.CENTER);
        title.setBounds(0,50,586,50);
        title.setForeground(new Color(0x00BFA5));
        title.setFont(new Font("Montserrat", Font.BOLD, 30));
        add(title);

        JLabel UserName = new JLabel("User Name");
        UserName.setBounds(130,140,586,50);
        UserName.setFont(new Font("Arial", Font.PLAIN, 15));
        add(UserName);

        UserField = new JTextField();
        UserField.setBounds(130,190,400,35);
        add(UserField);


        JLabel Email = new JLabel("Email", SwingConstants.LEFT);
        Email.setBounds(130,250,586,50);
        Email.setFont(new Font("Arial", Font.PLAIN, 15));
        add(Email);

        emailtx = new JTextField();
        emailtx.setBounds(130,300,400,35);
        add(emailtx);

        JLabel Pass = new JLabel("Password", SwingConstants.LEFT);
        Pass.setBounds(130,360,586,50);
        Pass.setFont(new Font("Arial", Font.PLAIN, 15));
        add(Pass);

        Passf = new JPasswordField();
        Passf.setBounds(130,410,400,35);
        add(Passf);

        JButton Signin = new JButton("Sign In");
        Signin.setBounds(130,500,140,30);
        Signin.setBorderPainted(false);
        Signin.setFocusPainted(false);
        Signin.setBackground(new Color(0x00C9A7));
        Signin.setForeground(Color.white);
        Signin.addActionListener(e->{
            String email = emailtx.getText();
            String username = UserField.getText();
            String password = new String(Passf.getPassword());

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled out", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (username.length() < 4) {
                JOptionPane.showMessageDialog(null, "Username must be at least 4 characters long", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.length() < 5) {
                JOptionPane.showMessageDialog(null, "Password must be at least 5 characters long", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Invalid Email Format", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String uri = "mongodb://localhost:27017";
            try(MongoClient mongoClient = MongoClients.create(uri)){
                MongoDatabase db = mongoClient.getDatabase("LoginInfo");
                MongoCollection<Document> collection = db.getCollection("Logindetail");
                Document dos = new Document("user_name", username).append("email", email).append("password", password);
                collection.insertOne(dos);
                JOptionPane.showMessageDialog(null,"Your User Name, Email and Password is added");

                clearFields();
                mainFrame.showPanel("login");



            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }

                }
        );
        add(Signin);


        JButton backToLogin = new JButton("Back to Login");
        backToLogin.setBounds(310,500,140,30);
        backToLogin.setBackground(new Color(0x2196F3));
        backToLogin.setForeground(Color.white);
        backToLogin.setBorderPainted(false);
        backToLogin.setFocusPainted(false);
        backToLogin.addActionListener(e -> {
            clearFields();
            mainFrame.showPanel("login");
        });
        add(backToLogin);
    }

    private void clearFields() {
        UserField.setText("");
        emailtx.setText("");
        Passf.setText("");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
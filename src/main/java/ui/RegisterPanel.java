package ui;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    private Mainframe mainFrame;

    public RegisterPanel(Mainframe mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(null);


        JLabel title = new JLabel("Register Panel", SwingConstants.CENTER);
        title.setBounds(0,150,586,50);
        title.setForeground(new Color(0x00BFA5));
        title.setFont(new Font("Montserrat", Font.BOLD, 30));
        add(title);

        JLabel Email = new JLabel("Email", SwingConstants.LEFT);
        Email.setBounds(130,250,586,50);
        Email.setFont(new Font("Arial", Font.PLAIN, 15));
        add(Email);

        JTextField emailtx = new JTextField();
        emailtx.setBounds(130,300,400,35);
        add(emailtx);

        JLabel Pass = new JLabel("Password", SwingConstants.LEFT);
        Pass.setBounds(130,360,586,50);
        Pass.setFont(new Font("Arial", Font.PLAIN, 15));
        add(Pass);

        JPasswordField Passf = new JPasswordField();
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
            String password = new String(Passf.getPassword());
            String uri = "mongodb://localhost:27017";
            try(MongoClient mongoClient = MongoClients.create(uri)){
                MongoDatabase db = mongoClient.getDatabase("LoginInfo");
                MongoCollection<Document> collection = db.getCollection("Logindetail");
                Document dos = new Document("email", email).append("password", password);
                collection.insertOne(dos);
                JOptionPane.showMessageDialog(null,"Your Email and Password is added");

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
        backToLogin.addActionListener(e -> mainFrame.showPanel("login"));
        add(backToLogin);
    }
}

package restaurantsystem.component.auth;

import javax.swing.*;
import java.awt.*;
import restaurantsystem.MainMenu;
import javax.swing.border.TitledBorder;
import static restaurantsystem.model.DatabaseManager.*;

public class Login extends JFrame {

    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public Login() {
        initComponents();
        setDefaultUserName();
    }

    private void setDefaultUserName() {
        this.userNameField.setText("tony");
        this.passwordField.setText("marvel");
    }

    private void initComponents() {
        // Panel setup
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(new Color(85, 85, 255));
        loginPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 128), 2, true),
                "Restaurant Management 🍽️",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                Color.WHITE));

        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username Label and Field
        JLabel userNameLabel = new JLabel("User Name: ");
        userNameLabel.setForeground(Color.WHITE);
        userNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(userNameLabel, gbc);

        userNameField = new JTextField(15);
        userNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        loginPanel.add(userNameField, gbc);

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        // Login Button
        loginButton = new JButton("Log In");
        loginButton.setBackground(new Color(0, 153, 76));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(this::loginButtonActionPerformed);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        // Main Frame Setup
        setTitle("Restaurant System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        add(loginPanel);
    }

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String name = userNameField.getText();
        String pass = new String(passwordField.getPassword()); // Retrieve password safely
        String role = getUserRole(name, pass);

        if (role != null) {
            JOptionPane.showMessageDialog(this, "Access granted");
            this.dispose();
            new MainMenu().setVisible(true);
        } else {
            userNameField.setText("");
            passwordField.setText("");
            JOptionPane.showMessageDialog(this, "Access Denied");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}

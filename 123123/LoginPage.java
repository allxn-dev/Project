import java.awt.*;
import javax.swing.*;

public class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel formPanel;
    private JPanel wrapperPanel;

    public LoginPage() {
        setTitle("Login Page");
        setSize(420, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(new Color(245, 245, 245));

        formPanel = new JPanel(null);
        formPanel.setPreferredSize(new Dimension(360, 340));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel title = new JLabel("Login");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(140, 10, 100, 30);
        formPanel.add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 70, 100, 25);
        formPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(30, 95, 300, 35);
        formPanel.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 140, 100, 25);
        formPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(30, 165, 260, 35);
        formPanel.add(passwordField);

        JButton toggleBtn = new JButton("👁");
        toggleBtn.setBounds(295, 165, 35, 35);
        toggleBtn.setFocusPainted(false);
        formPanel.add(toggleBtn);

        toggleBtn.addActionListener(e -> {
            if (passwordField.getEchoChar() == '\u2022') {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('\u2022');
            }
        });

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(30, 220, 300, 40);
        loginBtn.setBackground(new Color(76, 175, 80));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        formPanel.add(loginBtn);

        loginBtn.addActionListener(e -> login());

        JButton createAccountBtn = new JButton("Create Account");
        createAccountBtn.setBounds(30, 270, 300, 40);
        createAccountBtn.setBackground(new Color(33, 150, 243));
        createAccountBtn.setForeground(Color.WHITE);
        createAccountBtn.setFocusPainted(false);
        formPanel.add(createAccountBtn);

        createAccountBtn.addActionListener(e -> showSignUpDialog());

        wrapperPanel.add(formPanel);
        add(wrapperPanel);
        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("admin") && password.equals("admin123")) {
            JOptionPane.showMessageDialog(this, "Login successful!");

            new DashboardFrame(); // open dashboard
            dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
    }

    private void showSignUpDialog() {
        JDialog signUpDialog = new JDialog(this, "Create Account", true);
        signUpDialog.setSize(350, 230);
        signUpDialog.setLayout(null);
        signUpDialog.setLocationRelativeTo(this);

        JLabel gmailLabel = new JLabel("Gmail:");
        gmailLabel.setBounds(20, 20, 100, 25);
        signUpDialog.add(gmailLabel);

        JTextField gmailField = new JTextField();
        gmailField.setBounds(20, 45, 300, 30);
        signUpDialog.add(gmailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 90, 100, 25);
        signUpDialog.add(passwordLabel);

        JPasswordField signUpPasswordField = new JPasswordField();
        signUpPasswordField.setBounds(20, 115, 300, 30);
        signUpDialog.add(signUpPasswordField);

        JButton signUpBtn = new JButton("Sign Up");
        signUpBtn.setBounds(20, 155, 300, 30);
        signUpBtn.setBackground(new Color(76, 175, 80));
        signUpBtn.setForeground(Color.WHITE);
        signUpBtn.setFocusPainted(false);
        signUpDialog.add(signUpBtn);

        signUpBtn.addActionListener(e -> {
            String gmail = gmailField.getText().trim();
            String password = new String(signUpPasswordField.getPassword());

            if (gmail.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(signUpDialog, "All fields are required!");
                return;
            }

            if (!gmail.endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(signUpDialog, "Gmail must end with @gmail.com!");
                return;
            }

            JOptionPane.showMessageDialog(signUpDialog, "Account created successfully!");
            signUpDialog.dispose();
        });

        signUpDialog.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
package view;

import authenticator.MapAuthenticator;

import javax.swing.*;
import java.awt.*;

public class ConnexionFrame extends JDialog {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private final MapAuthenticator authenticator;
    private String loggedInUser;

    public ConnexionFrame(JFrame parent) {
        super(parent, "Connexion", true);
        authenticator = new MapAuthenticator();
        loggedInUser = null;

        initializeDialog();
        setLocationRelativeTo(parent);
    }

    private void initializeDialog() {
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel usernameLabel = new JLabel("Nom d'utilisateur:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordField = new JPasswordField();

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton loginButton = new JButton("Connexion");
        JButton cancelButton = new JButton("Annuler");

        loginButton.addActionListener(e -> handleLogin());
        cancelButton.addActionListener(e -> dispose());

        // Permettre la validation en appuyant sur Entrée
        getRootPane().setDefaultButton(loginButton);

        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        try {
            if (authenticator.authenticate(username, password)) {
                JOptionPane.showMessageDialog(this,
                        "Connexion réussie !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);

                // Stocker le nom de l'utilisateur connecté
                loggedInUser = username;
                dispose(); // Fermer la fenêtre
            } else {
                JOptionPane.showMessageDialog(this,
                        "Nom d'utilisateur ou mot de passe invalide !",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);

                // Effacer le champ mot de passe après une tentative échouée
                passwordField.setText("");
                passwordField.requestFocus();
            }
        } finally {
            // Effacer le mot de passe de la mémoire
            java.util.Arrays.fill(passwordChars, '0');
        }
    }

    // Retourne le nom d'utilisateur connecté
    public String getLoggedInUser() {
        return loggedInUser;
    }
}
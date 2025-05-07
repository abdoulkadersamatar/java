package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class JoueurDialog extends JDialog {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField dateNaissanceField;
    private JTextField tailleField;
    private JTextField poidsField;
    private JTextField posteField;
    private JTextField numeroField;
    private JTextField anneeRejointField;
    private boolean confirmed = false;

    public JoueurDialog(JFrame parent, String[] joueurInfo) {
        super(parent, "Joueur", true);
        initializeDialog();

        // Si des données existent, les préremplir
        if (joueurInfo != null) {
            nomField.setText(joueurInfo[0]);
            prenomField.setText(joueurInfo[1]);
            dateNaissanceField.setText(joueurInfo[2]);
            tailleField.setText(joueurInfo[3]);
            poidsField.setText(joueurInfo[4]);
            posteField.setText(joueurInfo[5]);
            numeroField.setText(joueurInfo[6]);
            anneeRejointField.setText(joueurInfo[7]);
        }
    }

    private void initializeDialog() {
        setLayout(new BorderLayout());
        setSize(500, 400);
        setLocationRelativeTo(getParent());

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Nom :"));
        nomField = new JTextField();
        formPanel.add(nomField);

        formPanel.add(new JLabel("Prénom :"));
        prenomField = new JTextField();
        formPanel.add(prenomField);

        formPanel.add(new JLabel("Date de naissance :"));
        dateNaissanceField = new JTextField();
        formPanel.add(dateNaissanceField);

        formPanel.add(new JLabel("Taille :"));
        tailleField = new JTextField();
        formPanel.add(tailleField);

        formPanel.add(new JLabel("Poids :"));
        poidsField = new JTextField();
        formPanel.add(poidsField);

        formPanel.add(new JLabel("Poste :"));
        posteField = new JTextField();
        formPanel.add(posteField);

        formPanel.add(new JLabel("Numéro :"));
        numeroField = new JTextField();
        formPanel.add(numeroField);

        formPanel.add(new JLabel("Année de rejoint :"));
        anneeRejointField = new JTextField();
        formPanel.add(anneeRejointField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Annuler");

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        okButton.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });
    }

    public String[] getJoueurData() {
        if (confirmed) {
            try {
                String nom = nomField.getText().trim();
                String prenom = prenomField.getText().trim();
                String dateNaissance = dateNaissanceField.getText().trim();
                int taille = Integer.parseInt(tailleField.getText().trim());
                int poids = Integer.parseInt(poidsField.getText().trim());
                String poste = posteField.getText().trim();
                int numero = Integer.parseInt(numeroField.getText().trim());
                int anneeRejoint = Integer.parseInt(anneeRejointField.getText().trim());

                // Vérifications
                if (nom.length() < 3) {
                    throw new IllegalArgumentException("Le nom doit contenir au moins 3 caractères.");
                }
                if (prenom.length() < 3) {
                    throw new IllegalArgumentException("Le prénom doit contenir au moins 3 caractères.");
                }
                if (poste.length() < 3) {
                    throw new IllegalArgumentException("Le poste doit contenir au moins 3 caractères.");
                }
                if (taille < 150 || taille > 250) {
                    throw new IllegalArgumentException("La taille doit être comprise entre 150 cm et 250 cm.");
                }
                if (poids < 50 || poids > 150) {
                    throw new IllegalArgumentException("Le poids doit être compris entre 50 kg et 150 kg.");
                }

                // Vérification de la date de naissance
                LocalDate naissanceDate;
                try {
                    naissanceDate = LocalDate.parse(dateNaissance, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (DateTimeParseException ex) {
                    throw new IllegalArgumentException("La date de naissance doit être au format yyyy-MM-dd.");
                }
                if (naissanceDate.isAfter(LocalDate.now())) {
                    throw new IllegalArgumentException("La date de naissance ne peut pas être dans le futur.");
                }
                if (naissanceDate.getYear() < 1960) {
                    throw new IllegalArgumentException("L'année de naissance ne peut pas être avant 1800.");
                }

                // Vérification de l'année de rejoint
                if (anneeRejoint < naissanceDate.getYear() + 18) {
                    throw new IllegalArgumentException("L'année de rejoint doit être au moins 18 ans après la date de naissance.");
                }

                return new String[]{
                        nom, prenom, dateNaissance,
                        String.valueOf(taille), String.valueOf(poids),
                        poste, String.valueOf(numero), String.valueOf(anneeRejoint)
                };
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Les champs numériques doivent contenir des nombres valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return null;
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        return null;
    }
}
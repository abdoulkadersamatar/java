package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        // Si des données existent, les pré-remplir
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

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = true;
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                dispose();
            }
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

                if (nom.isEmpty() || prenom.isEmpty() || dateNaissance.isEmpty() || poste.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Tous les champs doivent être remplis.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return null;
                }

                return new String[]{
                        nom, prenom, dateNaissance,
                        String.valueOf(taille), String.valueOf(poids),
                        poste, String.valueOf(numero), String.valueOf(anneeRejoint)
                };
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Les champs numériques doivent contenir des nombres valides.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
                return null;
            }
        }

        return null;
    }
}
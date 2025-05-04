package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EquipeDialog extends JDialog {
    private JTextField nomField;
    private JTextField paysField;
    private JTextField villeField;
    private boolean confirmed = false;

    public EquipeDialog(JFrame parent, String[] existingData) {
        super(parent, "Équipe", true);
        initializeDialog();

        // Si des données existent, les préremplir
        if (existingData != null) {
            nomField.setText(existingData[0]);
            paysField.setText(existingData[1]);
            villeField.setText(existingData[2]);
        }
    }

    private void initializeDialog() {
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(getParent());

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Nom de l'équipe :"));
        nomField = new JTextField();
        formPanel.add(nomField);

        formPanel.add(new JLabel("Pays :"));
        paysField = new JTextField();
        formPanel.add(paysField);

        formPanel.add(new JLabel("Ville :"));
        villeField = new JTextField();
        formPanel.add(villeField);

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

    public String[] getEquipeData() {
        if (confirmed) {
            String nom = nomField.getText().trim();
            String pays = paysField.getText().trim();
            String ville = villeField.getText().trim();

            if (nom.isEmpty() || pays.isEmpty() || ville.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Tous les champs doivent être remplis.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
                return null;
            }

            return new String[]{nom, pays, ville};
        }

        return null;
    }
}
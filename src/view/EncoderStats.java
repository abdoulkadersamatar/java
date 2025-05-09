package view;

import model.Joueur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EncoderStats extends JFrame {
    private static final Color GREEN_DARK = new Color(0x00471B);
    private static final Color GREEN_LIGHT = new Color(0x007A33);
    private static final Color WHITE = Color.WHITE;

    private static final String[] COLUMNS = {"Joueur", "1PT", "2PTS", "3PTS", "fautes", "rebonds", "assists", "contres", "Total"};

    private JTable table;
    private List<Joueur> joueurs;

    public EncoderStats(String equipeNom, List<Joueur> joueurs) {
        this.joueurs = joueurs; // Stocker la liste des joueurs
        setTitle(equipeNom);
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(GREEN_DARK);

        // === En-tête ===
        JLabel titleLabel = new JLabel("Équipe : " + equipeNom, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // === Tableau ===
        DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0);
        for (Joueur joueur : joueurs) {
            String joueurInfo = joueur.getNom() + " (N°" + joueur.getNumero() + ")";
            tableModel.addRow(new Object[]{joueurInfo, 0, 0, 0, 0, 0, 0, 0, 0});
        }

        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0; // Seules les colonnes des stats sont éditables
            }
        };
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setForeground(GREEN_DARK);
        table.setGridColor(GREEN_LIGHT);
        table.setSelectionBackground(GREEN_LIGHT);
        table.setSelectionForeground(WHITE);
        table.setBackground(WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // === Boutons de contrôle ===
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(GREEN_DARK);

        JButton plusBtn = new JButton("+");
        JButton minusBtn = new JButton("-");
        JButton statsBtn = new JButton("Voir les stats finales");

        styleButton(plusBtn);
        styleButton(minusBtn);
        styleButton(statsBtn);

        controlPanel.add(plusBtn);
        controlPanel.add(minusBtn);
        controlPanel.add(statsBtn);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        // === Gestion des événements ===
        plusBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();
            if (selectedRow >= 0 && selectedColumn > 0 && selectedColumn < COLUMNS.length - 1) { // Vérifie que la colonne est valide
                Joueur joueur = joueurs.get(selectedRow);
                String typeStat = COLUMNS[selectedColumn]; // Récupère le type de statistique à partir de l'en-tête de colonne
                joueur.getStatistique().incrementer(typeStat); // Incrémente la statistique correspondante
                int updatedValue = joueur.getStatistique().getStat(typeStat); // Récupère la nouvelle valeur
                table.setValueAt(updatedValue, selectedRow, selectedColumn); // Met à jour la cellule correspondante

                // Met à jour le total des points
                int totalPoints = joueur.getStatistique().getTotalPoints();
                table.setValueAt(totalPoints, selectedRow, COLUMNS.length - 1); // Colonne "Total"
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une cellule valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        minusBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();
            if (selectedRow >= 0 && selectedColumn > 0 && selectedColumn < COLUMNS.length - 1) { // Vérifie que la colonne est valide
                Joueur joueur = joueurs.get(selectedRow);
                String typeStat = COLUMNS[selectedColumn]; // Récupère le type de statistique à partir de l'en-tête de colonne
                joueur.getStatistique().decrementer(typeStat); // Décrémente la statistique correspondante
                int updatedValue = joueur.getStatistique().getStat(typeStat); // Récupère la nouvelle valeur
                table.setValueAt(updatedValue, selectedRow, selectedColumn); // Met à jour la cellule correspondante

                // Met à jour le total des points
                int totalPoints = joueur.getStatistique().getTotalPoints();
                table.setValueAt(totalPoints, selectedRow, COLUMNS.length - 1); // Colonne "Total"
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une cellule valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        statsBtn.addActionListener(e -> {
            for (int i = 0; i < joueurs.size(); i++) {
                Joueur joueur = joueurs.get(i);
                System.out.println("Statistiques de " + joueur.getNom() + ": " + joueur.getStatistique());
            }
            JOptionPane.showMessageDialog(this, "Statistiques sauvegardées avec succès !", "Information", JOptionPane.INFORMATION_MESSAGE);
        });

        add(mainPanel);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(GREEN_LIGHT);
        button.setForeground(WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
}
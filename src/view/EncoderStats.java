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

    public JTable table; // Champ public
    public JButton plusBtn; // Champ public
    public JButton minusBtn; // Champ public
    public JButton statsBtn; // Champ public
    public List<Joueur> joueurs; // Champ public

    public EncoderStats(String equipeNom, List<Joueur> joueurs) {
        this.joueurs = joueurs; // Stocker la liste des joueurs
        setTitle(equipeNom);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        plusBtn = new JButton("+");
        minusBtn = new JButton("-");
        statsBtn = new JButton("Voir les stats finales");

        styleButton(plusBtn);
        styleButton(minusBtn);
        styleButton(statsBtn);

        controlPanel.add(plusBtn);
        controlPanel.add(minusBtn);
        controlPanel.add(statsBtn);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);

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
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EncoderStats extends JFrame {
    private static final Color GREEN_DARK = new Color(0x00471B);
    private static final Color GREEN_LIGHT = new Color(0x007A33);
    private static final Color WHITE = Color.WHITE;

    private static final int NUM_PLAYERS = 12;
    private static final String[] COLUMNS = {
            "Joueur", "REBOND", "BLOCS", "3pts", "2pts", "fautes", "assists", "contres", "1pts", "totalpoints"
    };

    public EncoderStats() {
        setTitle("ÉQUIPE A");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(GREEN_DARK);

        // === En-tête ===
        JLabel titleLabel = new JLabel("ÉQUIPE A", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // === Tableau ===
        String[][] data = new String[NUM_PLAYERS][COLUMNS.length];
        for (int i = 0; i < NUM_PLAYERS; i++) {
            data[i][0] = "Joueur " + (i + 1); // colonne joueur
            for (int j = 1; j < COLUMNS.length; j++) {
                data[i][j] = "-"; // colonnes vides
            }
        }

        JTable table = new JTable(new DefaultTableModel(data, COLUMNS)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EncoderStats::new);
    }
}

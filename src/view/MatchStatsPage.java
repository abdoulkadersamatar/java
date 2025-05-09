package view;

import model.Joueur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MatchStatsPage extends JFrame {

    private static final String[] COLUMNS = {
            "Joueur", "3pts", "2pts", "1pt", "Total Pts", "Rebonds", "Assists", "Fautes", "Blocs", "Contres"
    };

    private static final Object[][] SAMPLE_DATA = {
            {"Joueur 1", 2, 4, 3, 17, 5, 2, 1, 1, 0},
            {"Joueur 2", 1, 3, 2, 11, 4, 3, 2, 0, 1},
            {"Joueur 3", 3, 2, 5, 16, 6, 8, 0, 0, 2},
            {"Joueur 1", 2, 4, 3, 17, 5, 2, 1, 1, 0},
            {"Joueur 2", 1, 3, 2, 11, 4, 3, 2, 0, 1},
            {"Joueur 3", 3, 2, 5, 16, 6, 8, 0, 0, 2},
            {"Joueur 1", 2, 4, 3, 17, 5, 2, 1, 1, 0},
            {"Joueur 2", 1, 3, 2, 11, 4, 3, 2, 0, 1},
            {"Joueur 3", 3, 2, 5, 16, 6, 8, 0, 0, 2},
            {"Joueur 1", 2, 4, 3, 17, 5, 2, 1, 1, 0},
            {"Joueur 2", 1, 3, 2, 11, 4, 3, 2, 0, 1},
            {"Joueur 3", 3, 2, 5, 16, 6, 8, 0, 0, 2},

    };

    public MatchStatsPage() {
        setTitle("Résumé du match - ÉQUIPE A");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 71, 27)); // vert Bucks

        JLabel titleLabel = new JLabel("Résumé des statistiques du match", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JTable table = new JTable(new DefaultTableModel(SAMPLE_DATA, COLUMNS));
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.setGridColor(Color.LIGHT_GRAY);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 300));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //Stats Totales
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2, 3, 10, 10));
        statsPanel.setBackground(new Color(255, 255, 255));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        statsPanel.add(createStatCard("Total points", "84"));
        statsPanel.add(createStatCard("Total fautes", "15"));
        statsPanel.add(createStatCard("Total rebonds", "39"));
        statsPanel.add(createStatCard("% 3pts", "38%"));
        statsPanel.add(createStatCard("% 2pts", "54%"));
        statsPanel.add(createStatCard("% lancers-francs", "72%"));

        JPanel statsContainer = new JPanel(new BorderLayout());
        statsContainer.add(statsPanel, BorderLayout.CENTER);

        mainPanel.add(statsContainer, BorderLayout.SOUTH);

        add(mainPanel);
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);
    }

    public MatchStatsPage(String nom, List<Joueur> joueurs) {
    }

    private JPanel createStatCard(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 102, 51));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MatchStatsPage::new);
    }
}

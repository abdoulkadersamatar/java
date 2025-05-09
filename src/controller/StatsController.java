package controller;

import model.Equipe;
import model.Joueur;
import view.EncoderStats;
import view.MainFrame;
import view.MatchStatsPage;

import javax.swing.*;

public class StatsController {
    private final MainFrame mainFrame;

    public StatsController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void handleOpenEncoderStats() {
        int selectedEquipeIndex = mainFrame.getSelectedEquipeIndex();
        if (selectedEquipeIndex < 0) {
            mainFrame.showMessageDialog("Veuillez sélectionner une équipe avant de continuer.", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Equipe equipe = mainFrame.getEquipeController().getEquipeByIndex(selectedEquipeIndex);
        if (equipe != null) {
            EncoderStats encoderStats = new EncoderStats(equipe.getNom(), equipe.getJoueurs());

            encoderStats.plusBtn.addActionListener(e -> {
                JTable table = encoderStats.table;
                int selectedRow = table.getSelectedRow();
                int selectedColumn = table.getSelectedColumn();
                if (selectedRow >= 0 && selectedColumn > 0 && selectedColumn < table.getColumnCount() - 1) {
                    Joueur joueur = encoderStats.joueurs.get(selectedRow);
                    String typeStat = table.getColumnName(selectedColumn);
                    joueur.getStatistique().incrementer(typeStat);
                    table.setValueAt(joueur.getStatistique().getStat(typeStat), selectedRow, selectedColumn);
                    table.setValueAt(joueur.getStatistique().getTotalPoints(), selectedRow, table.getColumnCount() - 1);
                } else {
                    JOptionPane.showMessageDialog(encoderStats, "Veuillez sélectionner une cellule valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            encoderStats.minusBtn.addActionListener(e -> {
                JTable table = encoderStats.table;
                int selectedRow = table.getSelectedRow();
                int selectedColumn = table.getSelectedColumn();
                if (selectedRow >= 0 && selectedColumn > 0 && selectedColumn < table.getColumnCount() - 1) {
                    Joueur joueur = encoderStats.joueurs.get(selectedRow);
                    String typeStat = table.getColumnName(selectedColumn);
                    joueur.getStatistique().decrementer(typeStat);
                    table.setValueAt(joueur.getStatistique().getStat(typeStat), selectedRow, selectedColumn);
                    table.setValueAt(joueur.getStatistique().getTotalPoints(), selectedRow, table.getColumnCount() - 1);
                } else {
                    JOptionPane.showMessageDialog(encoderStats, "Veuillez sélectionner une cellule valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            encoderStats.statsBtn.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> {
                    new MatchStatsPage(equipe.getNom(), equipe.getJoueurs());
                });
            });
        } else {
            mainFrame.showMessageDialog("Équipe introuvable.", JOptionPane.ERROR_MESSAGE);
        }
    }
}
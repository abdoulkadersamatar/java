package controller;

import model.Equipe;
import model.dao.IStatistique;
import view.EncoderStats;
import view.MainFrame;

import javax.swing.*;

public class StatsController  {
    private final MainFrame mainFrame;//definir la reference de la fenetre principale

    public StatsController(MainFrame mainFrame) {//constructeur de la classe
        this.mainFrame = mainFrame;
    }

    //methode pour gerer l'ouverture de la fenetre d'encodage des stat
    public void handleOpenEncoderStats() {
        int selectedEquipeIndex = mainFrame.getSelectedEquipeIndex();
        if (selectedEquipeIndex < 0) {
            mainFrame.showMessageDialog("Veuillez sélectionner une équipe avant de continuer.", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Equipe equipe = mainFrame.getEquipeController().getEquipeByIndex(selectedEquipeIndex);
        if (equipe != null) {
            new EncoderStats(equipe.getNom(), equipe.getJoueurs());
        } else {
            mainFrame.showMessageDialog("Équipe introuvable.", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Logique métier intégrée
    public void incrementerStat(IStatistique stats, String type) {
        stats.incrementer(type);
    }

    public void decrementerStat(IStatistique stats, String type) {
        stats.decrementer(type);
    }

    public int calculerTotalPoints(IStatistique stats) {
        return stats.getTotalPoints();
    }

}
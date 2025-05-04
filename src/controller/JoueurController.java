package controller;

import model.Joueur;
import model.Equipe;
import view.MainFrame;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class JoueurController {
    private final DefaultTableModel joueurTableModel;
    private final MainFrame mainFrame;

    public JoueurController(DefaultTableModel joueurTableModel, MainFrame mainFrame) {
        this.joueurTableModel = joueurTableModel;
        this.mainFrame = mainFrame;
    }

    public void addJoueur() {
        int selectedEquipeIndex = mainFrame.getSelectedEquipeIndex();
        if (selectedEquipeIndex < 0) {
            mainFrame.showMessageDialog("Veuillez sélectionner une équipe avant d'ajouter un joueur.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Equipe equipe = mainFrame.getEquipeController().getEquipeByIndex(selectedEquipeIndex);
        if (equipe == null) {
            mainFrame.showMessageDialog("Équipe introuvable.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] joueurData = mainFrame.openJoueurDialog(null);
        if (joueurData != null) {
            Joueur joueur = new Joueur(
                    joueurData[0],
                    joueurData[1],
                    joueurData[2],
                    Integer.parseInt(joueurData[3]),
                    Integer.parseInt(joueurData[4]),
                    Integer.parseInt(joueurData[6]),
                    joueurData[5],
                    Integer.parseInt(joueurData[7])
            );
            joueur.setId(equipe.generateJoueurId());
            equipe.ajouterJoueur(joueur);

            joueurTableModel.addRow(new Object[]{
                    joueur.getId(),
                    joueur.getNom(),
                    joueur.getPrenom(),
                    joueur.getDateNaissance(),
                    joueur.getTaille(),
                    joueur.getPoids(),
                    joueur.getPoste(),
                    joueur.getNumero(),
                    joueur.getAnneeRejoint()
            });
            mainFrame.showMessageDialog("Joueur ajouté avec succès !", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void editJoueur(int selectedRow) {
        if (selectedRow < 0) {
        mainFrame.showMessageDialog("Veuillez sélectionner un joueur à modifier.", JOptionPane.ERROR_MESSAGE);
        return;
        }

        int selectedEquipeIndex = mainFrame.getSelectedEquipeIndex();
        if (selectedEquipeIndex < 0) {
            mainFrame.showMessageDialog("Veuillez sélectionner une équipe.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Equipe equipe = mainFrame.getEquipeController().getEquipeByIndex(selectedEquipeIndex);
        if (equipe == null) {
            mainFrame.showMessageDialog("Équipe introuvable.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int joueurId = (int) joueurTableModel.getValueAt(selectedRow, 0);
        Joueur joueur = equipe.getJoueurs().stream()
                .filter(j -> j.getId() == joueurId)
                .findFirst()
                .orElse(null);

        if (joueur == null) {
            mainFrame.showMessageDialog("Joueur introuvable.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] joueurData = {
                joueur.getNom(),
                joueur.getPrenom(),
                joueur.getDateNaissance(),
                String.valueOf(joueur.getTaille()),
                String.valueOf(joueur.getPoids()),
                joueur.getPoste(),
                String.valueOf(joueur.getNumero()),
                String.valueOf(joueur.getAnneeRejoint())
        };

        String[] updatedJoueurData = mainFrame.openJoueurDialog(joueurData);
        if (updatedJoueurData != null) {
            try {
                joueur.setNom(updatedJoueurData[0]);
                joueur.setPrenom(updatedJoueurData[1]);
                joueur.setDateNaissance(updatedJoueurData[2]);
                joueur.setTaille(Integer.parseInt(updatedJoueurData[3]));
                joueur.setPoids(Integer.parseInt(updatedJoueurData[4]));
                joueur.setPoste(updatedJoueurData[5]);
                joueur.setNumero(Integer.parseInt(updatedJoueurData[6]));
                joueur.setAnneeRejoint(Integer.parseInt(updatedJoueurData[7]));

                joueurTableModel.setValueAt(updatedJoueurData[0], selectedRow, 1);
                joueurTableModel.setValueAt(updatedJoueurData[1], selectedRow, 2);
                joueurTableModel.setValueAt(updatedJoueurData[2], selectedRow, 3);
                joueurTableModel.setValueAt(updatedJoueurData[3], selectedRow, 4);
                joueurTableModel.setValueAt(updatedJoueurData[4], selectedRow, 5);
                joueurTableModel.setValueAt(updatedJoueurData[5], selectedRow, 6);
                joueurTableModel.setValueAt(updatedJoueurData[6], selectedRow, 7);
                joueurTableModel.setValueAt(updatedJoueurData[7], selectedRow, 8);

                mainFrame.showMessageDialog("Modification réussie !", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                mainFrame.showMessageDialog("Erreur dans les données numériques.", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void deleteJoueur(int selectedRow) {
        if (selectedRow < 0) {
            mainFrame.showMessageDialog("Veuillez sélectionner un joueur à supprimer.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedEquipeIndex = mainFrame.getSelectedEquipeIndex();
        if (selectedEquipeIndex < 0) {
            mainFrame.showMessageDialog("Veuillez sélectionner une équipe.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Equipe equipe = mainFrame.getEquipeController().getEquipeByIndex(selectedEquipeIndex);
        if (equipe == null) {
            mainFrame.showMessageDialog("Équipe introuvable.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int joueurId = (int) joueurTableModel.getValueAt(selectedRow, 0);
        Joueur joueur = null;
        for (Joueur j : equipe.getJoueurs()) {
            if (j.getId() == joueurId) {
                joueur = j;
                break;
            }
        }

        if (joueur != null && mainFrame.showConfirmationDialog("Êtes-vous sûr de vouloir supprimer le joueur " + joueur.getNom() + " ?")) {
            equipe.supprimerJoueur(joueur);
            joueurTableModel.removeRow(selectedRow);
            mainFrame.showMessageDialog("Joueur supprimé avec succès !", JOptionPane.INFORMATION_MESSAGE);
            loadJoueursByEquipe(equipe.getId());
        } else {
            mainFrame.showMessageDialog("Joueur introuvable.", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadJoueursByEquipe(int equipeId) {
        joueurTableModel.setRowCount(0);
        Equipe equipe = mainFrame.getEquipeController().getEquipeById(equipeId);

        if (equipe != null) {
            int position = 1;
            for (Joueur joueur : equipe.getJoueurs()) {
                joueurTableModel.addRow(new Object[]{
                        position++,
                        joueur.getNom(),
                        joueur.getPrenom(),
                        joueur.getDateNaissance(),
                        joueur.getTaille(),
                        joueur.getPoids(),
                        joueur.getPoste(),
                        joueur.getNumero(),
                        joueur.getAnneeRejoint()
                });
            }
        }
    }
}
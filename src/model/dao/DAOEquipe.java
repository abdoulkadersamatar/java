package model.dao;

import model.Equipe;
import util.IdGenerator;

import java.util.ArrayList;

public class DAOEquipe {
    private final ArrayList<Equipe> equipes;
    private final IdGenerator idGenerator; // Utilisation de l'IdGenerator

    public DAOEquipe() {
        equipes = new ArrayList<>();
        idGenerator = new IdGenerator(); // Initialisation de l'IdGenerator
    }

    // Ajouter une équipe
    public int addEquipe(String nom, String pays, String ville) {
        int id = idGenerator.generateId(); // Générer un ID unique
        Equipe equipe = new Equipe(id, nom, pays, ville);
        equipes.add(equipe);
        return id;
    }

    // Mettre à jour une équipe existante
    public boolean updateEquipe(Equipe equipe) {
        for (int i = 0; i < equipes.size(); i++) {
            if (equipes.get(i).getId() == equipe.getId()) {
                equipes.set(i, equipe);
                return true;
            }
        }
        return false;
    }

    // Supprimer une équipe par instance
    public boolean deleteEquipe(Equipe equipe) {
        return equipes.remove(equipe);
    }

    // Récupérer une équipe par ID
    public Equipe getEquipeById(int id) {
        for (Equipe equipe : equipes) {
            if (equipe.getId() == id) {
                return equipe;
            }
        }
        return null;
    }

    // Récupérer la liste des équipes
    public ArrayList<Equipe> getEquipes() {
        return new ArrayList<>(equipes);
    }

    @Override
    public String toString() {
        return "DAOEquipe{ equipes=" + equipes + " }";
    }
}
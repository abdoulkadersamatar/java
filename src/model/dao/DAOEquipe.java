// Fichier : src/model/dao/DAOEquipe.java
package model.dao;

import model.Equipe;
import util.IdGenerator;
import java.util.ArrayList;
import java.util.List;

public class DAOEquipe implements IDAOEquipe {
    private final ArrayList<Equipe> equipes = new ArrayList<>();
    private final IdGenerator idGenerator = new IdGenerator();

    @Override
    public int addEquipe(String nom, String pays, String ville) {
        int id = idGenerator.generateId();
        Equipe equipe = new Equipe(id, nom, pays, ville);
        equipes.add(equipe);
        return id;
    }

    @Override
    public boolean updateEquipe(Equipe equipe) {
        for (int i = 0; i < equipes.size(); i++) {
            if (equipes.get(i).getId() == equipe.getId()) {
                equipes.set(i, equipe);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteEquipe(Equipe equipe) {
        return equipes.remove(equipe);
    }

    @Override
    public Equipe getEquipeById(int id) {
        return equipes.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Equipe> getEquipes() {
        return new ArrayList<>(equipes);
    }
}
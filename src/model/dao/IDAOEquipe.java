package model.dao;

import model.Equipe;
import java.util.List;

public interface IDAOEquipe {
    int addEquipe(String nom, String pays, String ville);
    boolean updateEquipe(Equipe equipe);
    boolean deleteEquipe(Equipe equipe);
    Equipe getEquipeById(int id);
    List<Equipe> getEquipes();
}
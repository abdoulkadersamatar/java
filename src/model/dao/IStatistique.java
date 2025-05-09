package model.dao;

import model.Joueur;

import java.util.List;
import java.util.Map;

public interface IStatistique {
    void incrementer(String typeStat);
    void decrementer(String typeStat);
    int getStat(String typeStat);
    int getTotalPoints();
    void reinitialiser();

}
package model.dao;

import java.util.Map;

public interface IStatistique {
    void incrementer(String typeStat);
    void decrementer(String typeStat);
    int getStat(String typeStat);
    int getTotalPoints();
    void reinitialiser();

}
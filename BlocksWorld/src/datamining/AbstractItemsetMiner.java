package datamining;

import java.util.*;
import modelling.*;

public abstract class AbstractItemsetMiner implements ItemsetMiner {

    private BooleanDatabase base; // instance de BooleanDataBase
    // Constante qui un comparateur basé sur le nom
    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName()
            .compareTo(var2.getName());

    // constructeur
    public AbstractItemsetMiner(BooleanDatabase base) {
        this.base = base;
    }

    // redifenition de la méthode getDatabase pour acceder a notre instance de
    // BooleanDatabase
    @Override
    public BooleanDatabase getDatabase() {
        return this.base;
    }

    // methode pour calculer la frequence d'un ensemble de BooleanVariables
    public float frequency(Set<BooleanVariable> items) {

        // initialisation du compteur
        int itemsetCount = 0;
        // pour chaque transaction dans notre ensemble de transactions de la base
        for (Set<BooleanVariable> transaction : base.getTransactions()) {
            if (transaction.containsAll(items)) {// si une transaction contient tous les objects de l'ensemble donné en
                                                 // argument
                itemsetCount++; // on incremente le compteur
            }
        }

        // technique pour calculer une frequence
        float totalTransactions = base.getTransactions().size();
        if (totalTransactions > 0) {
            return (float) itemsetCount / totalTransactions;
        } else {
            return 0.0f;
        }
    }
}
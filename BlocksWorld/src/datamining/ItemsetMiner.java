package datamining;

/**
 * ItemsetMiner
 */

import java.util.*;

public interface ItemsetMiner {

    public BooleanDatabase getDatabase(); // methode retournant une BooleanDatabase

    public Set<Itemset> extract(float frequence); // methode retournant un ensemble d'Itemset ayant au moins la
                                                  // frequence donnée en argument
}
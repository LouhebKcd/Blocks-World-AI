package datamining;

/**
 * Itemset
 */

import java.util.*;
import modelling.*;

public class Itemset {

    private Set<BooleanVariable> items;// ensemble de BooleanVariable
    private float frequency; // frequence

    // constructeur
    public Itemset(Set<BooleanVariable> items, float frequence) {
        this.items = items;
        this.frequency = frequence;
    }

    // Accesseur
    public Set<BooleanVariable> getItems() {
        return items;
    }

    // Accesseur
    public float getFrequency() {
        return frequency;
    }

    // redefinition de la methode toString
    @Override
    public String toString() {
        return this.items.toString();
    }
}
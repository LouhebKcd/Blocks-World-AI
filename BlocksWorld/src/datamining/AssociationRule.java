package datamining;

/**
 * AssociationRule
 */

import java.util.*;
import modelling.*;

public class AssociationRule {

    private Set<BooleanVariable> premisse, conclusion; // ensembles de BooleanVariable
    private float frequence, confiance; // frequence et confiance

    // constructeur
    public AssociationRule(Set<BooleanVariable> premisse, Set<BooleanVariable> conclusion, float frequence,
            float confiance) {
        this.premisse = premisse;
        this.conclusion = conclusion;
        this.frequence = frequence;
        this.confiance = confiance;
    }

    // Accesseurs et Seteurs
    public Set<BooleanVariable> getPremise() {
        return premisse;
    }

    public Set<BooleanVariable> getConclusion() {
        return conclusion;
    }

    public float getFrequency() {
        return frequence;
    }
    public float getConfidence() {
        return confiance;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "["+premisse.toString()+" , "+conclusion.toString()+" , "+ this.frequence+" , "+ this.confiance+"]";
    }
}
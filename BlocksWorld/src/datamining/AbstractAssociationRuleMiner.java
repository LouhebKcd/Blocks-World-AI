package datamining;

/**
 * AbstractAssociationRuleMiner
 */
import java.util.*;
import modelling.*;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {

    private BooleanDatabase base;// instance de BooleanDatabase

    public AbstractAssociationRuleMiner(BooleanDatabase base) {
        this.base = base;
    }

    @Override
    public BooleanDatabase getDatabase() {
        return this.base;
    }

    // methode statique pour calculer la frequence d'un ensemble d'elements en
    // fonction des Itemsets donnés
    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets) {

        for (Itemset itemset : itemsets) {
            if (items.equals(itemset.getItems())) {
                return itemset.getFrequency();
            }
        }
        throw new IllegalArgumentException("l'item n'existe pas");
    }

    // Méthode statique pour calculer la confiance d'une règle d'association en
    // fonction des Itemsets donnés
    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion,
            Set<Itemset> itemsets) {
        float premiseFrequence = frequency(premise, itemsets);
        Set<BooleanVariable> union = new HashSet<>(premise);
        union.addAll(conclusion);
        float frequencePremiseEtConclusion = frequency(union, itemsets);

        if (premiseFrequence > 0) {

            return frequencePremiseEtConclusion / premiseFrequence;
        } else {
            throw new IllegalArgumentException("La prémisse n'a pas de fréquence non nulle.");
        }
    }

}
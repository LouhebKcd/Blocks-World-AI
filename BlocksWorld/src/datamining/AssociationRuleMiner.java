package datamining;

/**
 * AssociationRuleMiner
 */
import java.util.*;


public interface AssociationRuleMiner {

    public BooleanDatabase getDatabase(); // methode retournant une instance de BooleanDatabase

    public Set<AssociationRule> extract(float frequence, float confiance); // methode pour extraire un ensemble de
                                                                           // regles d'association en fonction d'une
                                                                           // frequence et d'une confiance données
}
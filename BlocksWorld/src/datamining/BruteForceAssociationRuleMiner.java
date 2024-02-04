package datamining;

import java.util.*;
import modelling.*;

/**
 * BruteForceAssociationRuleMiner
 */
public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

    // constructeur
    public BruteForceAssociationRuleMiner(BooleanDatabase base) {
        super(base);
    }

    // methode qui génère tous les ensembles de premisse candidats pour les regles
    // d'association a partir d'un ensemble donné
        public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        Set<Set<BooleanVariable>> candidates = new HashSet<>(); // Initialise un ensemble de candidats vides
        List<BooleanVariable> itemList = new ArrayList<>(items); // Convertit l'ensemble en liste
        int n = itemList.size(); // Récupère la taille de la liste

        // Boucle pour générer tous les sous-ensembles possibles
        for (int i = 1; i < (int) Math.pow(2, n); i++) {
            Set<BooleanVariable> candidate = new HashSet<>(); // Initialise un nouvel ensemble de candidats
            int subsetIndex = 0; // Initialise l'indice du sous-ensemble à 0
            int temp = i; // Stocke la valeur actuelle de i dans une variable temporaire

            // Boucle pour générer les sous-ensembles en binaire
            while (temp > 0) {
                if (temp % 2 == 1) {
                    candidate.add(itemList.get(subsetIndex)); // Ajoute l'élément correspondant à l'indice au sous-ensemble candidat
                }
                temp /= 2; // Divise temp par 2
                subsetIndex++; // Incrémente l'indice du sous-ensemble
            }

            // Vérifie si le candidat n'est ni vide ni égal à l'ensemble initial, puis l'ajoute à l'ensemble de candidats
            if (!candidate.isEmpty() && !candidate.equals(items)) {
                candidates.add(candidate);
            }
        }
        candidates.remove(items); // Supprime l'ensemble initial des candidats
        return candidates; // Renvoie l'ensemble de candidats générés
    }

    // Méthode pour obtenir la base de données à partir de la classe parent
    @Override
    public BooleanDatabase getDatabase() {
        return super.getDatabase(); // Renvoie la base de données de la classe parent
    }

    // Méthode pour extraire les règles d'association en utilisant la méthode de force brute
    @Override
    public Set<AssociationRule> extract(float frequence, float confiance) {
        Set<AssociationRule> associationRules = new HashSet<>(); // Initialise un ensemble de règles d'association
        Apriori ap = new Apriori(getDatabase()); // Initialise un objet Apriori avec la base de données actuelle
        Set<Itemset> frequentItemsets = ap.extract(frequence); // Extrait les ensembles fréquents à partir de l'objet Apriori

        // Boucle pour traiter chaque ensemble fréquent
        for (Itemset itemset : frequentItemsets) {
            Set<BooleanVariable> items = itemset.getItems(); // Récupère les items de l'ensemble actuel
            Set<Set<BooleanVariable>> premises = allCandidatePremises(items); // Récupère tous les ensembles de prémisse candidats

            // Boucle pour traiter chaque prémisse
            for (Set<BooleanVariable> premise : premises) {
                Set<BooleanVariable> consequence = new HashSet<>(items); // Initialise un ensemble conséquent
                consequence.removeAll(premise); // Supprime la prémisse de l'ensemble conséquent

                float premiseFrequency = frequency(premise, frequentItemsets); // Calcule la fréquence de la prémisse
                float confidenceValue = itemset.getFrequency() / premiseFrequency; // Calcule la valeur de confiance

                // Vérifie si la valeur de confiance est supérieure ou égale au seuil de confiance spécifié
                if (confidenceValue >= confiance) {
                    AssociationRule rule = new AssociationRule(premise, consequence, itemset.getFrequency(), confidenceValue); // Crée une nouvelle règle d'association
                    associationRules.add(rule); // Ajoute la règle d'association à l'ensemble
                }
            }
        }

        return associationRules; // Renvoie l'ensemble de règles d'association extraites
    }

}

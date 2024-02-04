package datamining;

import java.util.*;
import modelling.*;

public class Apriori extends AbstractItemsetMiner {

    // Le constructeur de la classe Apriori prenant une base de données booléenne en
    // paramètre
    public Apriori(BooleanDatabase base) {
        super(base);
    }

    // methode retournant des singletons frequents en tenant compte d'une frequence
    // minimale donnée
    public Set<Itemset> frequentSingletons(float minFrequence) {
        // ensemble pour stocker les singletons
        Set<Itemset> frequentSingletons = new HashSet<>();

        // pour chaque BooleanVariable de l'ensemble d'items de notre base
        for (BooleanVariable variable : getDatabase().getItems()) {
            // Création d'un ensemble contenant une seule variable
            Set<BooleanVariable> item = new HashSet<>();
            item.add(variable);
            // Calcul de la fréquence de cet ensemble
            float itemsetFrequence = frequency(item);
            if (itemsetFrequence >= minFrequence) {// verification si la frequence calculé et superieur ou egale a la
                                                   // frequence donnée
                frequentSingletons.add(new Itemset(item, itemsetFrequence));
            }

        }
        return frequentSingletons;
    }

    // methode statique pour combiner deux ensembles de BooleanVariable
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> ensemble1,
            SortedSet<BooleanVariable> ensemble2) {
        // condition a verifier avant de combiner les deux listes
        //les deux ensembles aient la même taille k
        //les deux ensembles aient les mêmes k − 1 premiers items
        //les deux ensembles aient des differents dernier element
        if (!ensemble1.isEmpty() && !ensemble2.isEmpty() &&
                ensemble1.size() == ensemble2.size() &&
                ensemble1.headSet(ensemble1.last()).equals(ensemble2.headSet(ensemble2.last())) &&
                !ensemble1.last().equals(ensemble2.last())) {

            SortedSet<BooleanVariable> combinedSet = new TreeSet<>(ensemble1);
            combinedSet.add(ensemble2.last());
            return combinedSet;
        } else {

            return null;
        }
    }

    // Méthode statique pour vérifier si tous les sous-ensembles d'un ensemble donné
    // sont fréquents
    public static boolean allSubsetsFrequent(Set<BooleanVariable> items,Collection<SortedSet<BooleanVariable>> frequentSets) {
        
        // Parcourt chaque élément de l'ensemble donné
        for (BooleanVariable item : items) {

            // Crée un sous-ensemble en enlevant un élément à la fois
            Set<BooleanVariable> subset = new HashSet<>(items);
            subset.remove(item);

            SortedSet<BooleanVariable> sortedSubset = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            sortedSubset.addAll(subset);

            boolean found = false;
            for (SortedSet<BooleanVariable> frequentSet : frequentSets) {
                //Condition qui vérifie si le sous-ensemble trié est égal à un ensemble fréquent
                if (sortedSubset.equals(frequentSet)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<Itemset> extract(float minFrequency) {
        // Crée un ensemble pour stocker les ensembles fréquents
        Set<Itemset> frequentItemsets = new HashSet<>();

        // Étape 1 : Génération des singletons
        Set<Itemset> frequentSingletons = frequentSingletons(minFrequency);
        List<SortedSet<BooleanVariable>> ensembleItemsetFrequent = new ArrayList<>();

        
        Set<Itemset> candidateItemsets = new HashSet<>();
        candidateItemsets.addAll(frequentSingletons);

        // Ajoute les singletons fréquents à notre ensemble de resultas
        frequentItemsets.addAll(frequentSingletons);

        for (Itemset itemset : candidateItemsets) {
            SortedSet<BooleanVariable> e = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            e.addAll(itemset.getItems());
            ensembleItemsetFrequent.add(e);
        }

        //Liste temporaire pour stocker les résultats et éviter de modifier 
        //directement ensembleItemsetFrequent
        
        List<SortedSet<BooleanVariable>> temp = new ArrayList<>(ensembleItemsetFrequent);

        while (!candidateItemsets.isEmpty()) {
            Set<Itemset> newCandidateItemsets = new HashSet<>();

            //Étape 2 : Génération des ensembles de taille k+1 en utilisant la méthode combine
            for (Itemset itemset1 : candidateItemsets) {

                for (SortedSet<BooleanVariable> itemset2 : temp) {
                    // Crée des ensembles triés pour les deux ensembles en cours
                    SortedSet<BooleanVariable> l1 = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
                    l1.addAll(itemset1.getItems());
                    SortedSet<BooleanVariable> l2 = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
                    l2.addAll(itemset2);
                    
                    // Combiner les deux ensembles pour former un nouvel ensemble
                    SortedSet<BooleanVariable> combinedSet = combine(l1, l2);

                    if (combinedSet != null) {
                        
                        //Vérifie si tous les sous-ensembles de l'ensemble candidat sont fréquents
                        if (allSubsetsFrequent(combinedSet, ensembleItemsetFrequent)) {
                            
                            // calcul de la frequence de l'ensemble
                            float itemsetFrequency = frequency(combinedSet);
                            
                            // Condition pour verifier si la fréquence de l'ensemble candidat dépasse le seuil minimum
                            // et s'il n'a pas encore été ajouté à la liste des ensembles fréquents
                            if (itemsetFrequency >= minFrequency && !ensembleItemsetFrequent.contains(combinedSet)) {

                                // ajout du nouvel item
                                Itemset newItemset = new Itemset(combinedSet, itemsetFrequency);
                                frequentItemsets.add(newItemset);
                                newCandidateItemsets.add(newItemset);
                                ensembleItemsetFrequent.add(combinedSet);
                            }
                        }
                    }
                }
            }

            // Mise à jour la liste des item candidats pour la prochaine itération
            candidateItemsets = newCandidateItemsets;

            //l'ajout des nouvelles valeures de la liste ensembleItemsetFrequent a notre liste tomporaire
            temp.addAll(ensembleItemsetFrequent);
        }
        // Retourne l'ensemble final des itemset fréquents
        return frequentItemsets;
    }

}

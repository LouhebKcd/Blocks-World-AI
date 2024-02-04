package blocksworld;

import java.util.Map;
import modelling.*;
import planning.*;

/**
 * Implémentation d'une heuristique pour la planification de monde des blocs,
 * qui consiste a calculer dans chaque etape le nombre de blocs mal placée
 */
public class BlocksHeuristic implements Heuristic {

    // But de la planification représenté par une map de variables et de leurs valeurs associées
    protected Map<Variable, Object> goal;

    
    //Constructeur de la classe BlocksHeuristic.
     
    public BlocksHeuristic(Map<Variable, Object> goal) {
        this.goal = goal;
    }

    /*
     * fonction qui prend en argument un etat actuel 
     * et retourne le nombre de blocs mal placés
     */
    @Override
    public float estimate(Map<Variable, Object> etat) {
        int nbBlockMalPlace = 0;

        // Parcours des variables dans l'état actuel
        for (Variable variable : etat.keySet()) {
            Object valeurCourante = etat.get(variable);
            Object valeurDansBut = goal.get(variable);

            // Vérification si la valeur courante diffère de la valeur dans le but
            if (valeurCourante != null && valeurDansBut != null && !valeurCourante.equals(valeurDansBut)) {
                nbBlockMalPlace++;
            }
        }

        // Retourne le nombre de blocs mal placés comme estimation du coût heuristique
        return nbBlockMalPlace;
    }
}


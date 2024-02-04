package blocksworld;

import java.util.Map;
import modelling.*;
import planning.*;

/**
 * Implémentation d'une heuristique pour la planification dans le monde des blocs,
 * qui estime la distance totale que chaque bloc doit parcourir pour atteindre sa position cible.
 */
public class BlocksTotalDistanceHeuristic implements Heuristic {

    //etat final
    private Map<Variable, Object> goal;

    //Constructeur
    public BlocksTotalDistanceHeuristic(Map<Variable, Object> goal) {
        this.goal = goal;
    }

    //Fonction prenant l'état actuel en argument et retournant une estimation 
    //de la distance totale que chaque bloc doit parcourir pour arriver a l'etat final
    @Override
    public float estimate(Map<Variable, Object> state) {
        // Variable pour stocker la distance totale
        float totalDistance = 0;
        
        // Parcours des variables dans l'état actuel
        for (Variable variable : state.keySet()) {
            
            // Récupération de la position actuelle et de la position cible pour la variable
            Object positionCourante = state.get(variable);
            Object positionFinal = goal.get(variable);

            if (positionCourante != null && positionFinal != null && positionCourante instanceof Integer && positionFinal instanceof Integer) {
                
                // Ajout de la différence absolue entre currentPos et goalPos à la distance totale
                totalDistance += Math.abs((Integer) positionFinal - (Integer) positionFinal);
            }
        }
        //retourner la distance
        return totalDistance;
    }
}


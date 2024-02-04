package cp;

import java.util.*;
import modelling.*;

public class HeuristicMACSolver extends AbstractSolver {

    private VariableHeuristic variableHeuristic; 
    private ValueHeuristic valueHeuristic;

	//constructeur
    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic) {
        super(variables, constraints);
        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;
    }


    // methode MAC pour l'algorithme MAC (Consistency Maintenance) prenant une instantiation partielle, un ensemble de variables et un ensemble de domaines.
    public Map<Variable, Object> MAC(Map<Variable, Object> instantiationP, Set<Variable> ensembleVariables, Map<Variable, Set<Object>> ED) {
        ArcConsistency arcConsistency = new ArcConsistency(constraints);

        // verifie si l'ensemble de variables est vide, retourne l'instantiation actuelle.
        if (ensembleVariables.isEmpty()) {
            return instantiationP;
        } else {
            // verifie si la contrainte ArcConsistency de niveau 1 echoue, retourne null.
            if (!arcConsistency.ac1(ED)) {
                return null;
            }

            // selectionne la meilleure variable en fonction de l'heuristique de variable.
            Variable xi = variableHeuristic.best(ensembleVariables, ED);
            ensembleVariables.remove(xi);
            
            // parcourt les valeurs ordonnees de la variable selectionnee en fonction de l'heuristique de valeur.
            for (Object vi : valueHeuristic.ordering(xi, ED.get(xi))) {
                Map<Variable, Object> N = new HashMap<>(instantiationP);
                N.put(xi, vi);

                // verifie si l'instantiation est coherente.
                if (isConsistent(N)) {
                    Map<Variable, Object> result = MAC(N, ensembleVariables, ED);
                    if (result != null) {
                        return result;
                    }
                }
            }

         
            ensembleVariables.add(xi);
            return null;
        }
    }

    // Implemente la methode abstraite solve de la classe parente pour resoudre le probleme.
    @Override
    public Map<Variable, Object> solve() {
        // Initialise la carte d'ensembles de domaines (ED) pour chaque variable.
        Map<Variable, Set<Object>> ED = new HashMap<>();
        for (Variable variable : variables) {
            ED.put(variable, new HashSet<>(variable.getDomain()));
        }
        // Appelle la methode MAC avec des ensembles de variables et de domaines initialises.
        return MAC(new HashMap<>(), new HashSet<>(variables), ED);
    }

}

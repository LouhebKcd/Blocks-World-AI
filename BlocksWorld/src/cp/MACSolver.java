package cp;

import java.util.*;
import modelling.*;

//class MACSolver heritant de la class abstraite AbstractSolver
public class MACSolver extends AbstractSolver {

	//constructeur prenant en argument un ensemble de variables et un ensembles de contraintes
    public MACSolver(Set<Variable> variables , Set<Constraint> constraint){
        super(variables,constraint);
    }

	//methode auxilliare pour appliquer l'algorithme MAC
    public Map<Variable, Object> MAC(Map<Variable, Object> instantiationP, Set<Variable> ensembleVariables, Map<Variable, Set<Object>> ED) {
    
    	// creation d'une instance de la class ArcConsistency
        ArcConsistency arcConsistency = new ArcConsistency(constraints);

	//si l'ensemble des variables est vide, retourner l'instanciation partielle
        if (ensembleVariables.isEmpty()) {
            return instantiationP;
        } 
        //sinon appliquer l'algorithme ac1
        else {
            if (!arcConsistency.ac1(ED)) {
                return null;//retourne null si au moins un domaine est vide
            }
		
	    //prends une variable et la retire de l'ensemble
            Variable xi = ensembleVariables.iterator().next();
            ensembleVariables.remove(xi);
		
	    //pour chaque valuer possible pour la variable prise
            for (Object vi : ED.get(xi)) {
            //nouvelle instanciation
                Map<Variable, Object> N = new HashMap<>(instantiationP);
                N.put(xi, vi);
		//si elle est coherente
                if (isConsistent(N)) {
                    Map<Variable, Object> result = MAC(N, ensembleVariables, ED);
                    //retourne le resultat s'il nest pas null
                    if (result != null) {
                        return result;
                    }
                }
            }
		//remettre la variable dans l'ensemble si aucune solution n'a ete trouvée
            ensembleVariables.add(xi);
            return null;
        }
    }
	
	//methode de resolution principale utilisant la methode MAC
    @Override
    public Map<Variable, Object> solve() {
    //initialisation de l'ensemble de domaine
        Map<Variable, Set<Object>> ED = new HashMap<>();
        for (Variable variable : variables) {
            ED.put(variable, new HashSet<>(variable.getDomain()));
        }
        //appel de la methode MAC pour appliquer l'algorithme MAC
        return MAC(new HashMap<>(), new HashSet<>(variables), ED);
    }
}

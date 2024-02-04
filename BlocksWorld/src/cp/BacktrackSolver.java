package cp;

import java.util.*;
import modelling.*;

//class BacktrackSolver heritant de la class abstraite AbstractSolver
public class BacktrackSolver extends AbstractSolver{

	//constructeur prenant en argument un ensemble de variables et un ensemble de contraintes
    public BacktrackSolver(Set<Variable> variables , Set<Constraint> constraint){
        super(variables,constraint);
    }

	//methode auxiliaure implementant l'algorithme du backtracking
    public Map<Variable, Object> solveAux(Map<Variable, Object> solutionP , Set<Variable> ensembleVariables) {
    	
    	//si l'ensemble de variables est vide 
        if (ensembleVariables.isEmpty()) {
            return solutionP; //retourner les solutions partielles
        }
	
	//retirer une variable de notre ensemble
        Variable xi = ensembleVariables.iterator().next();
        ensembleVariables.remove(xi);

        for (Object vi : xi.getDomain()) {
        //création d'une nouvelle solution avec la variable ayant la valeur courante au resultat précédent
            Map<Variable, Object> nouvelleSol = new HashMap<>(solutionP);
            nouvelleSol.put(xi, vi);
            
            //verifie si cette nouvelle solution est coherente
            if (isConsistent(nouvelleSol)) {
            
            //appel recursif pour les autres valeurs
                Map<Variable, Object> result = solveAux(nouvelleSol,ensembleVariables);
                
                //si le resultat est valide
                if (result != null) {
                    return result;//alors le retourner
                }
            }
        }
        //remettre la variable dans notre ensemble de variables si aucune solution n'a ete trouvé
        ensembleVariables.add(xi);
        return null;
    }
	
	//methode principale appelant la methode auxiliaire solveAux
    @Override
    public Map<Variable, Object> solve() {
        
        Map<Variable, Object> solutionPartielle = new HashMap<>();
        Set<Variable> ensembleVariables = new HashSet<>(this.variables);

        return solveAux(solutionPartielle, ensembleVariables);
    }
}

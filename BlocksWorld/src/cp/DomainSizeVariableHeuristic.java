package cp;

import java.util.*;
import modelling.*;

//class DomainSizeVariableHeuristic implementant l'interface VariableHeuristic
public class DomainSizeVariableHeuristic implements VariableHeuristic {
    private boolean preferePlusGrandDomaine; //booleen pour dire si domaine est preferé ou pas	

    //constructeur	
    public DomainSizeVariableHeuristic(boolean preferePlusGrandDomaine) {
        this.preferePlusGrandDomaine = preferePlusGrandDomaine;
    }

    //methode pour trouver la meilleur variable en fonction de la taille de son domaine
    @Override
    public Variable best(Set<Variable> ensembleVariables, Map<Variable, Set<Object>> ensembleDomain) {
        Variable meilleureVariable = null;//variable pour stocker la meilleure variables dedans
        int meilleureTaille = preferePlusGrandDomaine ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        //pour chaque variable de notre ensemble de variables , trouver la meilleure en fonction de la taille du domaine
        for (Variable variable : ensembleVariables) {
            int tailleDomaine = ensembleDomain.get(variable).size();
            if ((preferePlusGrandDomaine && tailleDomaine > meilleureTaille) ||(!preferePlusGrandDomaine && tailleDomaine < meilleureTaille)) {
                meilleureVariable = variable;
                meilleureTaille = tailleDomaine;
            }
        }
	//renvoie la meilleure variable
        return meilleureVariable;
    }
}

package cp;

import java.util.*;
import modelling.*;

//class NbConstraintsVariableHeuristic implementant l'interface VariableHeuristic
public class NbConstraintsVariableHeuristic implements VariableHeuristic {
    private Set<Constraint> constraints; // ensyemble de contraintes
    private boolean preferePlusDeContraintes; // indicateur de preference
	
	// constructeur
    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean preferePlusDeContraintes) {
        this.constraints = constraints;
        this.preferePlusDeContraintes = preferePlusDeContraintes;
    }
	
	//redefinition de la methode pour trouver la meilleure variable
    @Override
    public Variable best(Set<Variable> ensembleVariables, Map<Variable, Set<Object>> ensembleDomain) {
    	//variable pour stocker la meilleure Variable
        Variable meilleureVariable = null;
        //initialisation du compteur
        int meilleurCompte = preferePlusDeContraintes ? Integer.MIN_VALUE : Integer.MAX_VALUE;
	
	//pour chaque variable de notre ensemble de variables
        for (Variable variable : ensembleVariables) {
        //nombre de contraintes associes a la variable
            int compteContraintes = getCompteContraintes(variable);
            
            // Vérifie si le nombre de contraintes actuel est meilleur que le meilleur compte en fonction de la préférence
            if ((preferePlusDeContraintes && compteContraintes > meilleurCompte) ||(!preferePlusDeContraintes && compteContraintes < meilleurCompte)) {
                meilleureVariable = variable; // mets a jour la meilleure variable
                meilleurCompte = compteContraintes; // mets a jour le meilleur compte
            } 
        }

        return meilleureVariable; // retourne la meilleure variable trouvée
    }
	
	//methode qui compte le nombre de contraintes associées a une variables
    public int getCompteContraintes(Variable variable) {
        int compte = 0; // initialisation du compteur
        
        //pour chaque contrainte de notre ensemble de contraintes
        for (Constraint contrainte : constraints) {
            
            // Vérifie si la contrainte implique la variable en cours
            if (contrainte.getScope().contains(variable)) {
                compte++; // on incremente notre compteur
            }
        }
        return compte; //retourne le nombre total de contraintes
    }
}

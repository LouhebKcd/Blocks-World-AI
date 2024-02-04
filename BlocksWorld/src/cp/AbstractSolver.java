package cp;


import java.util.*;
import modelling.*;

//class abstraite AbstractSolver implementant l'interface Solver
public abstract class AbstractSolver implements Solver{

    protected Set<Variable> variables; //ensemble de variables
    protected Set<Constraint> constraints; // ensemble de contraintes
    
    //constructeur
    public AbstractSolver(Set<Variable> variables , Set<Constraint> constraint){
        this.variables = variables;
        this.constraints = constraint;
    }

	//méthode retournant un boolean , vrai si l'affectation passé en argument satisfait toutes les contraintes , faux sinon
    public boolean isConsistent(Map<Variable, Object> affectationP) {
    
        for (Constraint cons : constraints) {//pour chaque contrainte
        
       //verifier si toutes les variables de la contraintes sont dans l'affectation
            if (cons.getScope().stream().allMatch(affectationP::containsKey)) { 
            
            // verifie si la contrainte est satisfaite
                if (!cons.isSatisfiedBy(affectationP)) { 
                
                    return false; // retourne false si elle n'est pas satisfaite
                }
            }
        }
        return true; // retourne true sinon
    }
}


package modelling;

import java.util.*;

public interface Constraint {
	
    //La méthode getScope() retourne l'ensemble des variables sur lesquelles porte la contrainte.
    public Set<Variable> getScope();
    
    //La méthode isSatisfiedBy(Map<Variable, Object>  instanciation) retourne un boolean selon si la contrainte est satisfaite ou pas par l'instanciation donnée.
    public boolean isSatisfiedBy(Map<Variable, Object>  instanciation);

}

package planning;

import modelling.Variable;
import java.util.*;

public interface Action {
    public boolean isApplicable(Map<Variable,Object> etat); //retourne un boolean verifiant si l'effet est applicable sur l'etat
    
    public Map<Variable, Object> successor(Map<Variable, Object> etat); // applique l'effet sur l'etat et retournant un nouvel etat
    
    public int getCost(); // retorune un entier (cout de l'action)
    
} 

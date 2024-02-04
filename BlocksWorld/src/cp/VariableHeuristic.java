package cp;

import java.util.*;
import modelling.*;

//interface VariableHeuristic
public interface VariableHeuristic {

   // methode prenant en argument un ensemble de variable et domaines ,retournant la meilleure Variable 
    public Variable best(Set<Variable> ensembleVariables , Map<Variable, Set<Object>> ensembleDomain);
}

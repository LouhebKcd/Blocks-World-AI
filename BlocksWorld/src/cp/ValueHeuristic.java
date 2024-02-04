package cp;

import java.util.*;
import modelling.*;

//interface ValueHeuristic
public interface ValueHeuristic {
	
	//methode prenant en argument une variable et son domaine , et retournant une liste contennant les valeurs du domaine ordonnée selon l'heuristique (meilleur en premier)
    public List<Object> ordering(Variable v, Set<Object> domain);
}

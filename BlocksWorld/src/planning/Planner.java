package planning;

import java.util.List;
import java.util.Set;
import java.util.*;
import modelling.*;
public interface Planner {

    public List<Action> plan(); // retourne un plan d'action à faire du parcours
    public Map<Variable, Object> getInitialState(); // retourne l'etat initial 
    public void activateNodeCount(boolean activate); // active les sondes 
    public int getNodeCount(); // retourne le nombre de noeud exploré durant un parcours
    public Set<Action> getActions();// retourne la liste de nos actions
    public Goal getGoal(); //retourne notre but
}

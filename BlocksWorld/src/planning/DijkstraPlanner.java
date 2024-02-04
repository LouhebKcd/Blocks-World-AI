package planning;

import modelling.*;
import java.util.*;

//la class DijkstraPlanner implemente l'interface Planner
public class DijkstraPlanner implements Planner {
    private Map<Variable, Object> initialState; //etat initial 
    private Set<Action> actions; // liste de nos actions 
    private Goal goal; // notre but
    private int NodeCount; // compteur pour le nombre de sommets vistés
	
	//constructeur
    public DijkstraPlanner(Map<Variable, Object> initialState ,Set<Action> actions ,Goal goal ){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }
    
    //cette methode prend un ensemble de paires (etat,distance) comme argument et renvoie l'etat avec la plus petite distance
    public Map<Variable, Object> argmin(Set<Map<Variable, Object>> open , Map<Map<Variable, Object>,Float> distance) {
        
        if (open.isEmpty()) {
            return null;
        }else{
            float minDistance = Float.POSITIVE_INFINITY;
            Map<Variable, Object> element = new HashMap<>();
            for(Map<Variable, Object> state : open){
                if (distance.get(state) < minDistance) {
                    element = state;
                    minDistance = distance.get(state);
                }
            }
            return element;
        }
        
    }

	//methode pour reconstruire le plan pour atteindre le but
    public List<Action> getDijkstraPlan(Map<Map<Variable, Object>,Map<Variable, Object>>father,Map<Map<Variable, Object>,Action> plan,Set<Map<Variable, Object>> goals,Map<Map<Variable, Object>,Float> distance){
        Queue<Action> BFSPlan = new LinkedList<>();
        Map<Variable, Object> goall = argmin(goals , distance);
        while (father.get(goall)!= null) {
            BFSPlan.add(plan.get(goall));
            goall = father.get(goall);
        }
        return new ArrayList<>(reverseQueue(BFSPlan));
        
    }
	
	//methode pour renverser l'ordre des elements d'une file
    public Queue<Action> reverseQueue(Queue<Action> queue) {
        LinkedList<Action> reversedQueue = new LinkedList<>();
        while (!queue.isEmpty()) {
            reversedQueue.addFirst(queue.poll());
        }
        return reversedQueue;
    }
    
    //implementation de l'algorithme de Dijkstra qui permet de retourner le plan ideale pour atteindre notre si celui-ci existe
    @Override
    public List<Action> plan() {

	//initialisation des structures
	
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        father.put(this.initialState, null);

        Map<Map<Variable, Object>, Action> plan = new HashMap<>();

        Map<Map<Variable, Object>,Float> distance = new HashMap<>();
        distance.put(this.initialState,Float.valueOf(0));

        Set<Map<Variable, Object>> open = new HashSet<>();
        open.add(this.initialState);

        Set<Map<Variable, Object>> goals = new HashSet<>();
        Map<Variable, Object> next;

        Map<Variable, Object> instantiation = new HashMap<>(this.initialState);
	
	//algorithme de Dijkstra 
        while (!open.isEmpty()) {
            NodeCount++;
            instantiation = argmin(open,distance);
            open.remove(instantiation);
            if (this.goal.isSatisfiedBy(instantiation)) {
                goals.add(instantiation);
            }
            for (Action action : this.actions) {
                
                if (action.isApplicable(instantiation)) {
                    next = action.successor(instantiation); 
                    if (!distance.containsKey(next)) {
                        distance.put(next,Float.POSITIVE_INFINITY);

                    }
                    if (distance.get(next) > (distance.get(instantiation)+action.getCost()) ) {
                        distance.put(next,(distance.get(instantiation)+action.getCost()));
                        father.put(next,instantiation);
                        plan.put(next,action);
                        open.add(next);
                    }
                }
            }
        }
	//si aucun but n'est atteint renvoyer null sinon le plan trouvé
        if (goals.isEmpty()) {
            return null;
        }else{
            return getDijkstraPlan(father,plan,goals,distance);
        }
    }

	//methode pour obtenir le nombre de noeuds visités
    @Override
    public int getNodeCount() {
        return NodeCount;
    }
	
	//methode pour activer le compterus de noeuds visités
    @Override
    public void activateNodeCount(boolean activate) {
     if (activate) {
        NodeCount = 0;
     } else {
        NodeCount = -1;
     }
      
      //methode retournant l'etat initial
    }
    @Override
    public Map<Variable, Object> getInitialState() {
        return initialState;
    }

	//methode retournant l'ensemble de nos actions
    @Override
    public Set<Action> getActions() {
        return actions;
    }
	//methode retournant notre but
    @Override
    public Goal getGoal() {
        return goal;
    }


}

package planning;

import modelling.*;
import java.util.*;;


public class AStarPlanner implements Planner {

    private Map<Variable, Object> initialState;//etat initial
    private Set<Action> actions; // ensemble de nos actions
    private Goal goal; // notre but
    private Heuristic heuristic; // heuristique utilisé
    private int NodeCount;//compteur de noeuds


	//constructeur
    public AStarPlanner(Map<Variable, Object> initialState ,Set<Action> actions ,Goal goal , Heuristic heuristic){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.heuristic = heuristic;
    }
	
	//methode pour renverser l'ordre des elements de la file
    public Queue<Action> reverseQueue(Queue<Action> queue) {
        LinkedList<Action> reversedQueue = new LinkedList<>();
        while (!queue.isEmpty()) {
            reversedQueue.addFirst(queue.poll());
        }
        return reversedQueue;
    }
	
	//methode retournant l'etat avec la valeur (cout+heuristique) la plus petite
    public Map<Variable, Object> argmin(Set<Map<Variable, Object>> open , Map<Map<Variable, Object>,Float> value) {
        
        if (open.isEmpty()) {
            return null;
        }else{
            float minDistance = Float.POSITIVE_INFINITY;
            Map<Variable, Object> element = new HashMap<>();
            for(Map<Variable, Object> state : open){
                if (value.get(state) < minDistance) {
                    element = state;
                    minDistance = value.get(state);
                }
            }
            return element;
        }
        
    }

	//methode retournant le plan utilisant la recherche en largeur
    public List<Action> getBfsPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Map<Variable, Object> goal) {
        Queue<Action> BFSPlan = new LinkedList<>();

        while (father.get(goal) != null) {
            BFSPlan.add(plan.get(goal));
            goal = father.get(goal);
        }
        List<Action> list = new ArrayList<>(reverseQueue(BFSPlan));
        return list;
    }

    @Override
    public List<Action> plan() {
    	//initialisation des structures
    	
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        father.put(this.initialState, null);

        Set<Map<Variable, Object>> open = new HashSet<>();
        open.add(this.initialState);

        Map<Map<Variable, Object>,Float> distance = new HashMap<>();
        distance.put(this.initialState,Float.valueOf(0));
        
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        
        Map<Map<Variable, Object>,Float> value = new HashMap<>();
        value.put(this.initialState,this.heuristic.estimate(this.initialState));

        Map<Variable, Object> instantiation = new HashMap<>(this.initialState);
        
        Map<Variable, Object> next;
        
        while (!open.isEmpty()) {
            NodeCount++;
            instantiation = argmin(open,value);
            //verifie si le but est satisfait ou pas
            if (this.goal.isSatisfiedBy(instantiation)) {
                return getBfsPlan(father,plan,instantiation);
            }else{
                open.remove(instantiation);//retire l'etat actuel de l'ensemble des ouverts
                for (Action action : this.actions) {
                    if (action.isApplicable(instantiation)) {
                        next = action.successor(instantiation);//applique l'effet si celui-ci est applicable
                        if (!distance.containsKey(next)) {
                            distance.put(next,Float.POSITIVE_INFINITY);
    
                        }
                        if (distance.get(next) > (distance.get(instantiation)+action.getCost()) ) {			
                        //mise a jour de la distance et la valeur de l'etat prochain
                            distance.put(next,(distance.get(instantiation)+action.getCost()));
                            value.put(next,(distance.get(next)+this.heuristic.estimate(next)));
                            //met a jour le father, l'action et ajoute l'etat suivant
                            father.put(next,instantiation);
                            plan.put(next,action);
                            open.add(next);
                        }
                    }
                }
            }
        }
        return null;//retourne null si l'objectif n'est pas atteint

    }

//retourne le nombre de noeuds explorés
    @Override
    public int getNodeCount() {
        return NodeCount;
    }
//active le compteur de noeuds visités
    @Override
    public void activateNodeCount(boolean activate) {
     if (activate) {
        NodeCount = 0;
     } else {
        NodeCount = -1;
     }
      
    }

//retourne l'etat initial
    @Override
    public Map<Variable, Object> getInitialState() {
        return initialState;
    }

//retourne l'ensemble de nos actions
    @Override
    public Set<Action> getActions() {
        return actions;
    }

//retourne notre but
    @Override
    public Goal getGoal() {
        return goal;
    }


    
}

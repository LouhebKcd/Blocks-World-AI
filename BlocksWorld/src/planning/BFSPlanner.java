package planning;


import java.util.*;
import modelling.*;


public class BFSPlanner implements Planner {

    private Map<Variable, Object> initialState; //Etat initial du probleme
    private Set<Action> actions;// ensemble d'actions possibles
    private Goal goal; // notre but
    private int NodeCount; // compteur du nombre de noeuds explorés

    public BFSPlanner(Map<Variable, Object> initialState ,Set<Action> actions ,Goal goal ){
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

	//methode qui permet d'inverser l'ordre des actions dans la file
    public Queue<Action> reverseQueue(Queue<Action> queue) {
        LinkedList<Action> reversedQueue = new LinkedList<>();
        while (!queue.isEmpty()) {
            reversedQueue.addFirst(queue.poll());
        }
        return reversedQueue;
    }
	
	//methode retournant le plan utilisant les informations des noeuds peres
	    public List<Action> getBfsPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Map<Variable, Object> goal) {
        Queue<Action> BFSPlan = new LinkedList<>();

        while (father.get(goal) != null) {
            BFSPlan.add(plan.get(goal));
            goal = father.get(goal);
        }
        List<Action> list = new ArrayList<>(reverseQueue(BFSPlan));
        return list;
    }
	
	//methode principale pour atteindre le but et le plan d'actions correspondant
    @Override
    public List<Action> plan() {

        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        father.put(this.initialState, null);

        Map<Map<Variable, Object>, Action> plan = new HashMap<>();

        Set<Map<Variable, Object>> closed = new HashSet<>();
        closed.add(this.initialState);

        Queue<Map<Variable, Object>> open = new LinkedList<>();
        open.add(this.initialState);

        Map<Variable, Object> instantiation = new HashMap<>(this.initialState);
	
	//verifie si l'etat initial satisfait deja le but , si c'est le cas retourner un plan vide
        if (goal.isSatisfiedBy(instantiation)) {
            return new ArrayList<Action>();
        }else{
        //sinon on lance la recherche de plan
            while (!open.isEmpty()) {
                NodeCount++; // incrementer notre compteur
                instantiation = open.poll();//defiler un etat
                closed.add(instantiation);// et le marqué comme visté
                
                //pour chaque action de notre ensempble d'actions
                for (Action action : this.actions) {
                    if (action.isApplicable(instantiation)) {
                        
                    //initialisation d'un etat suivant
                        Map<Variable, Object> next = action.successor(instantiation);
                        //verifier si celui-ci n'est pas deja visité
                        if (!closed.contains(next) && !open.contains(next) ) {
                            father.put(next,instantiation); //ajouter l'etat suivant dans notre map comme noeud pere
                            plan.put(next,action); //ajouter l'action dans notre map plan
                            //si notre but est satisfait par l'etat initial on retourne le plan trouvé
                            if (goal.isSatisfiedBy(next)) {
                                return getBfsPlan(father,plan,next);
                            }else{
                            //sinon enfiler l'etat suivant
                                open.add(next);
                            }
                        }
                    }
                }
            }
            return null; // retourne rien si aucune solution n'est trouvée
            
        }

        
    }

// retourne le nombre de noeud visités
    @Override
    public int getNodeCount() {
        return NodeCount;
    }

// methode qui visite la sonde
    @Override
    public void activateNodeCount(boolean activate) {
     if (activate) {
        NodeCount = 0;
     } else {
        NodeCount = -1;
     }
      
    }
       
// retourne l'eat initial

    @Override
    public Map<Variable, Object> getInitialState() {
        return initialState;
    }

// retourne un ensemble d'actions
    @Override
    public Set<Action> getActions() {
        return actions;
    }

// retorune notre but
    @Override
    public Goal getGoal() {
        return goal;
    }
}

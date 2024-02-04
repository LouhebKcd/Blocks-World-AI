package planning;

import java.util.*;
import modelling.*;

public class DFSPlanner implements Planner {

  private Map<Variable, Object> initialState; //Etat initial du probleme
  private Set<Action> actions; // ensemble d'actions possibles
  private Goal goal; // objectif ou but
  private int NodeCount; // compteur de noeuds explorés
  
  
  //constructeur
  public DFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
    this.initialState = initialState;
    this.actions = actions;
    this.goal = goal;
  }
  
  //Methode recursive pour la recherche DFS
  public List<Action> planDFS(Map<Variable, Object> instantiation, Stack<Action> plan, Set<Map<Variable, Object>> closed) {
  //si le but est satisfait par l'etat initial, on retourne le plan
  NodeCount++;
  if (goal.isSatisfiedBy(instantiation)) {
      return new ArrayList<Action>(plan);
      
    } else {
    // pour chaque action de notre ensemble d'actions
      for (Action action : actions) {
      //verifie si celle-ci est applicable
        if (action.isApplicable(instantiation)) {
          
        //creer la prochaine instantiation et appliquer l'action
          Map<Variable, Object> next = action.successor(instantiation);
          //verifier si cette nouvelle instanciation n'est pas explorrée
          if (!closed.contains(next)) {
          //empiler l'action dans plan et marqué l'instanciation comme explorée
            plan.push(action);
            closed.add(next);

            List<Action> subplan = planDFS(next, plan, closed);
	  // si il y'a un sous-plan valide le retourner
            if (subplan != null) {
              return subplan;
            } else {
            //sinon depiler nos actions
              plan.pop();
            }
          }
        }
      }
      //retourne null s'il n'y a pas de plan ou de solution
      return null;
    }
  }
	
	
	//methode principale 
  @Override
  public List<Action> plan() {

    Stack<Action> planStack = new Stack<>();
    Set<Map<Variable, Object>> closed = new HashSet<>();

    closed.add(initialState);
    List<Action> resultPlan = planDFS(initialState, planStack, closed);

    return resultPlan;
  }
	//methode retourne le nombre de noeudzs explorés
  @Override
    public int getNodeCount() {
        return NodeCount;
    }
	//methode pour activer le compteur de noeuds explorés
    @Override
    public void activateNodeCount(boolean activate) {
     if (activate) {
        NodeCount = 0;
     } else {
        NodeCount = -1;
     }
      
    }

//methode retournant l'etat initial
  @Override
  public Map<Variable, Object> getInitialState() {
    return initialState;
  }
//methode retournant l'ensemple de nos actions
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

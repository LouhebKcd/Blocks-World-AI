package blocksworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;


import modelling.*;
import planning.*;
import bwmodel.*;
import bwui.*;

/**
 * Demo
 */

public class DemoPlanning {

    //Crée un état du monde pour la visualisation graphique.
    public static BWState<Integer> makestate(int n ,BlocksWorld bw ,Map<Variable , Object> monde){
        
        // Initialisation du constructeur d'état pour un monde de blocs avec n blocs
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(n);

        // Parcours de chaque bloc dans le monde
        for (int b = 0; b < n; b++) {
            Variable onb = null;

            // Recherche de la variable "onb" correspondant au bloc actuel
            for (Variable var : bw.getVariableOn()) {
                if (Integer.parseInt(var.getName().substring(2)) == b) {
                    onb = var;
                    break;
                }
            }
            // Si la variable "onb" est trouvée,on configure la position du bloc dans l'état
            if (onb != null) {
                int under = (int) monde.get(onb);
                if (under >= 0) {
                    builder.setOn(b, under);
                }
            }
        }
        // Création de l'état du monde à partir d'ine instance de BWStateBuilder
        BWState<Integer> state = builder.getState();

        //retourner l'etat
        return state;
    } 

    public static void main(String[] args) {

        int numBlocks = 3;
        int numPiles = 2;
        BlocksWorld bw  = new BlocksWorld(numBlocks, numPiles);
        BlocksAction ac = new BlocksAction(numBlocks,numPiles,bw);
        Set<Action> actions = new HashSet<>();
        actions = ac.getEnsembleDesActions();
        
        Map<Variable, Object> goal , monde = new HashMap<>();
        goal = new HashMap<>();
        for (Variable variable : bw.getVariables()) {
            if (variable.getName().startsWith("on")) {
                // Attribuez des valeurs aux variables "on"
                if (variable.getName().equals("on0")) {
                    monde.put(variable, -1);
                } else if (variable.getName().equals("on1")) {
                    monde.put(variable, 0);
                }else{
                    monde.put(variable, 1);
                }
            } else if (variable.getName().startsWith("fixed")) {
                // Attribuez des valeurs aux variables fixed
                if (variable.getName().equals("fixed2")) {
                    monde.put(variable, false);
                }else{
                    monde.put(variable, true);
                }
                
            } else if (variable.getName().startsWith("free")) {
                // Attribuez des valeurs aux variables "free"
                if (variable.getName().equals("free2")) {
                    monde.put(variable, true);
                } else {
                    monde.put(variable, false);
                }
            }
        }
        
        for (Variable variable : bw.getVariables()) {
            if (variable.getName().startsWith("on")) {
                // Attribuez des valeurs aux variables "on"
                if (variable.getName().equals("on0")) {
                    goal.put(variable, 1);
                } else if (variable.getName().equals("on1")) {
                    goal.put(variable, 2);
                }else{
                    goal.put(variable, -2);
                }
            } else if (variable.getName().startsWith("fixed")) {
                // Attribuez des valeurs aux variables "fixed"
                if (variable.getName().equals("fixed0")) {
                    goal.put(variable, false);
                }else if (variable.getName().equals("fixed1")) {
                    goal.put(variable, true);
                }else{
                    goal.put(variable, true);
                }
                
            } else if (variable.getName().startsWith("free")) {
                // Attribuez des valeurs aux variables "free"
                if (variable.getName().equals("free1")) {
                    goal.put(variable, true);
                } else  if (variable.getName().equals("free2")) {
                    goal.put(variable, false);
                }
            }
        }
        
        Goal but = new BasicGoal(goal);

        //resulution de notre probleme en utilisant l'algorithme DFS
        long startTimeDFS = System.currentTimeMillis();
        DFSPlanner dfsPlanner = new DFSPlanner(monde,actions, but);
        List<Action> dfsPlannerPlan = dfsPlanner.plan();
        long endTimeDFS = System.currentTimeMillis();

        List<Action> resultatDfs = new ArrayList<>();
        resultatDfs.addAll(dfsPlannerPlan);
        for (Action action : resultatDfs) {
            System.out.println(action);
        }
        System.out.println("le nombre de neuds explorés par l'algorithme DFS : "+dfsPlanner.getNodeCount());
        System.out.println("Temps d'exécution de l'algorithme DFS : " + (endTimeDFS - startTimeDFS) + " millisecondes");

        //resulution de notre probleme en utilisant l'algorithme BFS
        long startTimeBFS = System.currentTimeMillis();
        BFSPlanner bfsPlanner = new BFSPlanner(monde,actions, but);
        List<Action> bfsPlannerPlan = bfsPlanner.plan();
        long endTimeBFS = System.currentTimeMillis();

        List<Action> resultatBfs = new ArrayList<>();
        resultatBfs.addAll(bfsPlannerPlan);
        for (Action action : resultatBfs) {
            System.out.println(action);
        }
        System.out.println("le nombre de neuds explorés par l'algorithme BFS : "+bfsPlanner.getNodeCount());
        System.out.println("Temps d'exécution de l'algorithme BFS : " + (endTimeBFS - startTimeBFS) + " millisecondes");
        
        //resulution de notre probleme en utilisant l'algorithme de Dijkstra
        long startTimeDijkstra = System.currentTimeMillis();
        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(monde,actions, but);
        List<Action> dijkstraPlan = dijkstraPlanner.plan();
        long endTimeDijkstra = System.currentTimeMillis();

        List<Action> resultatDijkstra = new ArrayList<>();
        resultatDijkstra.addAll(dijkstraPlan);
        for (Action action : resultatDijkstra) {
            System.out.println(action);
        }
        System.out.println("le nombre de neuds explorés par l'algorithme dijksra : "+dijkstraPlanner.getNodeCount());
        System.out.println("Temps d'exécution de l'algorithme Dijkstra : " + (endTimeDijkstra - startTimeDijkstra) + " millisecondes");

        //resulution de notre probleme en utilisant l'algorithme de AStar
        BlocksHeuristic blocksHeuristic = new BlocksHeuristic(goal);

        //BlocksTotalDistanceHeuristic distanceHeuristic = new BlocksTotalDistanceHeuristic(goal);
        //pour utiliser cette heuristique on peut passer distanceHeuristic a AStarPlanner 
        //comme argument a la place de blocksHeuristic

        long startTimeAStar = System.currentTimeMillis();
        AStarPlanner aStarPlanner = new AStarPlanner(monde, actions, but,blocksHeuristic);
        List<Action> aStarPlannerPlan = aStarPlanner.plan();
        long endTimeAStar = System.currentTimeMillis();

        List<Action> resultatAStar = new ArrayList<>();
        resultatAStar.addAll(aStarPlannerPlan);
        for (Action action : resultatAStar) {
            System.out.println(action);
        }
        System.out.println("le nombre de neuds explorés par l'algorithme AStar : "+aStarPlanner.getNodeCount());
        System.out.println("Temps d'exécution de l'algorithme AStar : " + (endTimeAStar - startTimeAStar) + " millisecondes");
        
        
        // Visualisation graphique du plan trouvé
        // Pour visualiser les résultats des différents algorithmes, cette section utilise une interface graphique.
        // Une instance de la classe BWIntegerGUI est créée avec un paramètre représentant le nombre de bloc d'un monde
        BWIntegerGUI gui = new BWIntegerGUI(numBlocks);

        // Création d'une nouvelle fenêtre pour afficher la visualisation du plan
        JFrame frame = new JFrame("Plan Visualization");

        // Création d'un état initial pour la visualisation en utilisant les paramètres de la classe DemoPlanning
        BWState<Integer> state = DemoPlanning.makestate(numBlocks, bw, monde);

        // Création du composant graphique à partir de l'état initial
        BWComponent<Integer> component = gui.getComponent(state);

        // Ajout du composant à la fenêtre
        frame.add(component);

        // Configuration de la taille de la fenêtre
        frame.setSize(600, 400);

        // Positionnement de la fenêtre au centre de l'écran
        frame.setLocationRelativeTo(frame);

        // Définition de l'opération par défaut lors de la fermeture de la fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Rend la fenêtre visible
        frame.setVisible(true);

        // Itération à travers les actions du plan résultant de l'algorithme A*
        //on peut remplacer "resultatAstar" par le resultat d'un autre planificateur et le visualiser
        for (Action a : resultatBfs) {
            try {
                // Pause d'une seconde entre chaque action pour visualiser progressivement le plan
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            monde = a.successor(monde);

            // Met à jour le composant graphique avec le nouvel état du monde
            component.setState(DemoPlanning.makestate(3, bw, monde));
        }

    }
}


package blocksworld;

import java.util.Map;
import java.util.Set;
import java.util.Random;
import modelling.*;
import cp.*;
import bwmodel.*;
import bwui.*;
import javax.swing.JFrame;
//class executable instancaitn un monde avec des contraintes regulieres

public class DemoCroissanteReguliere {

    public static void main(String[] args) {
    	
        int numBlocks = 5;//nombre de blocks
        int numPiles = 2; //nombre de piles
        
        //instanciation d'un monde de blocs
        BlocksWorld bw = new BlocksWorld(numBlocks, numPiles);
        
        // Création et recuperation des contraintes du monde des blocs
        BlocksWorldConstraints blocksWorldConstraints = new BlocksWorldConstraints(numBlocks, numPiles,bw);
        Set<Constraint> constraints = blocksWorldConstraints.getSetConstraint();
        
        // Obtention de l'ensemble de variables
        Set<Variable> variables = bw.getVariables();
        
        
        //Ajout des contraintes regulieres
        BlockWorldRegular blockWorldRegular = new BlockWorldRegular(bw);
        constraints.addAll(blockWorldRegular.getContrainteReguliere());
        
       //Ajout des contraintes d'un monde croissant
        Croissante blockWorldCroissante = new Croissante(bw);
        constraints.addAll(blockWorldCroissante.getContrainteCroissante());
        
        //generation d'un nombre aleatoire pour valueHeuristic
        Random random = new Random();
        
        //creeation d'un valeur heuristique
        ValueHeuristic valueHeuristic = new RandomValueHeuristic(random); 
        
        //creation de deux varibales heuriqtiques ,une avec favorisant ou pas la taille du domaine de variable
        //l'autre favorisant ou pas le nombre de contraintes 
        
        VariableHeuristic variableHeuristic1 = new NbConstraintsVariableHeuristic(constraints,false);
        VariableHeuristic variableHeuristic2 = new DomainSizeVariableHeuristic(false);


        // Création des solveurs
        Solver solver1 = new BacktrackSolver(variables, constraints);
        Solver solver2 = new MACSolver(variables, constraints);
        Solver solver3 = new HeuristicMACSolver(variables, constraints,variableHeuristic1,valueHeuristic);
        Solver solver4 = new HeuristicMACSolver(variables, constraints,variableHeuristic2,valueHeuristic);


        // Résolution du problème et mesure du temps de calcul
        long startTime1 = System.currentTimeMillis();
        Map<Variable, Object> solution1 = solver1.solve();
        long endTime1 = System.currentTimeMillis();

        long startTime2 = System.currentTimeMillis();
        Map<Variable, Object> solution2 = solver2.solve();
        long endTime2 = System.currentTimeMillis();

        long startTime3 = System.currentTimeMillis();
        Map<Variable, Object> solution3 = solver3.solve();
        long endTime3 = System.currentTimeMillis();

        long startTime4 = System.currentTimeMillis();
        Map<Variable, Object> solution4 = solver4.solve();
        long endTime4 = System.currentTimeMillis();


        // Affichage de la solution pour backtarackSolver
        System.out.println("Configuration Réguliere & Croissante: \n");

        if (solution1 != null) {
            System.out.println("Solution trouvée BackTrackSolver: ");
            for (Map.Entry<Variable, Object> entry : solution1.entrySet()) {
                System.out.println(entry.getKey().getName() + " : " + entry.getValue());
            }
            System.out.println("\n\n");
        } else {
            System.out.println("Aucune solution trouvée pour BackTrackSolver. \n\n");
        }
        
        
        // Affichage de la solution pour MacSolver

        if (solution2 != null) {
            System.out.println("Solution trouvée MACSolver: ");
            for (Map.Entry<Variable, Object> entry : solution2.entrySet()) {
                System.out.println(entry.getKey().getName() + " : " + entry.getValue());
            }
            System.out.println("\n\n");

        } else {
            System.out.println("Aucune solution trouvée pour MACSolver \n\n.");
        }
        
        // Affichage de la solution pour MACSolver utilisant nbConstraintsVariableHeuristic 

        if (solution3 != null) {
            System.out.println("Solution trouvée HeuristicMACSolver avec nbConstraintsVariableHeuristic:  ");
            for (Map.Entry<Variable, Object> entry : solution3.entrySet()) {
                System.out.println(entry.getKey().getName() + " : " + entry.getValue());
            }
            System.out.println("\n\n");

        } else {
            System.out.println("Aucune solution trouvée pour HeuristicMACSolver avec nbConstraintsVariableHeuristic\n\n.");
        }
        
        // Affichage de la solution pour MACSolver utilisant DomainSizeVariableHeuristique 

        if (solution4 != null) {
            System.out.println("Solution trouvée HeuristicMACSolver avec domaineSizeVariableHeuristic: ");
            for (Map.Entry<Variable, Object> entry : solution4.entrySet()) {
                System.out.println(entry.getKey().getName() + " : " + entry.getValue());
            }
            System.out.println("\n\n");

        } else {
            System.out.println("Aucune solution trouvée pour HeuristicMACSolver avec domaineSizeVariableHeuristic\n\n.");
        }

        // Affichage du temps de calcul des solvers
        System.out.println("Temps de calcul BackTrackSolver: " + (endTime1 - startTime1) + " millisecondes.");
        System.out.println("Temps de calcul MACSolver: " + (endTime2 - startTime2) + " millisecondes.");
        System.out.println("Temps de calcul HeuristicMACSolver avec NbConstraintsVariableHeuristic: " + (endTime3 - startTime3) + " millisecondes.");
        System.out.println("Temps de calcul HeuristicMACSolver avec DomainSizeVariableHeuristic: " + (endTime4 - startTime4) + " millisecondes.");

        BWIntegerGUI gui = new BWIntegerGUI(numBlocks);
        JFrame frame = new JFrame("Plan Visualization");
        BWState<Integer> state = DemoPlanning.makestate(numBlocks ,bw,solution3);
        BWComponent<Integer> component = gui.getComponent(state);
        frame.add(component);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

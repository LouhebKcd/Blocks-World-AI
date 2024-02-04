package blocksworld;

import java.util.*;
import modelling.Variable;
import planning.*;

/**
 * BlocksAction
 */
public class BlocksAction {

    private int numBlocks; // Nombre de blocs
    private int numPiles; // Nombre de piles
    private Set<Action> ensembleDesActions;//ensemble de toutes les actions possible
    
    public BlocksAction(int numBlocks , int numPiles ,BlocksWorld bw ){
        this.numBlocks = numBlocks;
        this.numPiles = numPiles;
        this.ensembleDesActions = new HashSet<>();

        // Trois boucles pour parcourir nos différentes variables et créer toutes les actions possibles
        for (Variable variable1 : bw.getVariableOn()) {
            for (Variable variable2 : bw.getVariables()) {
                for (Variable variable3 : bw.getVariables()) {
                    
                    // action 1 : déplacer un bloc b du dessus d’un bloc b′ vers le dessus d’un bloc b′′
                    if (variable2.getName().startsWith("on") 
                    && variable3.getName().startsWith("on")
                    && !variable1.equals(variable2) && !variable2.equals(variable3)
                    && !variable3.equals(variable1)) {
                        
                        //initialisation de la map des precondition
                        Map<Variable,Object> precondition = new HashMap<>();
                        //verification si variable1 est sur variable2 (onb égal à b')
                        precondition.put(variable1,Integer.parseInt(variable2.getName().substring(2)));
                        
                        //initialisation de la map des effets
                        Map<Variable,Object> effet = new HashMap<>();
                        effet.put(variable1, Integer.parseInt(variable3.getName().substring(2)));

                        //boucle for qui parcours l'ensemble des variables fixed pour 
                        //recuperer la variable fixed liee a notre bloc actuel
                        for (Variable var : bw.getVariableFixed()) {
                            if (var.getName().equals("fixed"+variable1.getName().substring(2))) {
                                precondition.put(var, false);
                            }
                            if (var.getName().equals("fixed"+variable3.getName().substring(2))) {
                               precondition.put(var, false);
                               effet.put(var, true);
                            }

                            if (var.getName().equals("fixed"+variable2.getName().substring(2))) {
                                
                                effet.put(var, false);
                            }
                        }

                        //creation de l'action en utilisant ces precondition et ces effets
                        BasicAction action1 = new BasicAction(precondition, effet, 1);
                        //ajout de l'action dans l'ensemble des actions
                        ensembleDesActions.add(action1);

                    }
                    
                    //Action 2 : déplacer un bloc b du dessus d’un bloc b′ vers une pile vide p
                    if (variable2.getName().startsWith("on") 
                    && variable3.getName().startsWith("free")
                    && !variable1.equals(variable2) && !variable2.equals(variable3)
                    && !variable3.equals(variable1)) {

                        //initialisation de la map des precondition
                        Map<Variable,Object> precondition = new HashMap<>();
                        precondition.put(variable1,Integer.parseInt(variable2.getName().substring(2)));
                        precondition.put(variable3, true);
                        
                        //initialisation de la map des effets
                        Map<Variable,Object> effet = new HashMap<>();
                        effet.put(variable1, -Integer.parseInt(variable3.getName().substring(4)));
                        effet.put(variable3,false );

                        //boucle for qui parcours l'ensemble des variables fixed pour 
                        //recuperer la variable fixed liee a notre bloc actuel
                        for (Variable var : bw.getVariableFixed()) {
                            if (var.getName().equals("fixed"+variable1.getName().substring(2))) {
                                precondition.put(var, false);
                            }
                            if (var.getName().equals("fixed"+variable2.getName().substring(2))) {
                                effet.put(var,false );
                            }
                        }
                        
                        //creation de l'action en utilisant ces precondition et ces effets
                        BasicAction action2 = new BasicAction(precondition, effet, 1);
                        //ajout de l'action dans l'ensemble des actions
                        ensembleDesActions.add(action2);
                        
                    }

                    //action 3 : déplacer un bloc b du dessous d’une pile p vers le dessus d’un bloc b′
                    if (variable2.getName().startsWith("on")
                        && variable3.getName().startsWith("free")
                        && !variable1.equals(variable2) && !variable2.equals(variable3)
                        && !variable3.equals(variable1)){
                        
                        Map<Variable,Object> precondition = new HashMap<>();
                        precondition.put(variable1, -Integer.parseInt(variable3.getName().substring(4)));
                        
                        Map<Variable,Object> effet = new HashMap<>();
                        effet.put(variable1,Integer.parseInt(variable2.getName().substring(2)));
                        effet.put(variable3, true);

                        //boucle for qui parcours l'ensemble des variables fixed pour 
                        //recuperer la variable fixed liee a notre bloc actuel
                        for (Variable var : bw.getVariableFixed()) {
                            if (var.getName().equals("fixed"+variable1.getName().substring(2))) {
                                precondition.put(var, false);
                            }
                            if (var.getName().equals("fixed"+variable2.getName().substring(2))) {
                                precondition.put(var, false);
                                effet.put(var, true);
                            }
                        }
                        
                        //creation de l'action en utilisant ces precondition et ces effets
                        BasicAction action3 = new BasicAction(precondition, effet, 1);
                        //ajout de l'action
                        ensembleDesActions.add(action3);

                    }

                    //action 4 : déplacer un bloc b du dessous d’une pile p vers une pile vide p′
                    if (variable2.getName().startsWith("free")
                        && variable3.getName().startsWith("free")
                        && !variable1.equals(variable2) && !variable2.equals(variable3)
                        && !variable3.equals(variable1)) {
                        
                        // initialisation de la map des effets
                        Map<Variable,Object> precondition = new HashMap<>();
                        precondition.put(variable1, -Integer.parseInt(variable2.getName().substring(4)));
                        
                        //boucle for qui parcours l'ensemble des variables fixed pour 
                        //recuperer la variable fixed liee a notre bloc actuel
                        for (Variable var : bw.getVariableFixed()) {
                            if (var.getName().equals("fixed"+variable1.getName().substring(2))) {
                                precondition.put(var, false);
                            }
                        }
                        precondition.put(variable3,true );
                        
                        //initialisation de la map des effets
                        Map<Variable,Object> effet = new HashMap<>();
                        effet.put(variable1, -Integer.parseInt(variable3.getName().substring(4)));
                        effet.put(variable2, true);
                        effet.put(variable3, false);

                        //creation de l'action en utilisant ces precondition et ces effets
                        BasicAction action4 = new BasicAction(precondition, effet, 1);
                        ensembleDesActions.add(action4);
                    }
                }
            }
        }

    }

    // Les getters de la class 
    public Set<Action> getEnsembleDesActions() {
        return ensembleDesActions;
    }

    public int getNumBlocks() {
        return numBlocks;
    }

    public int getNumPiles() {
        return numPiles;
    }
}
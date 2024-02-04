package blocksworld;

import java.util.*;
import modelling.*;
public class BlocksWorldConstraints {
    private int numBlocks; // Nombre de blocs
    private int numPiles; // Nombre de piles
    private Set<Constraint> setConstraint; //ensemble de contraintes

    public BlocksWorldConstraints(int numBlocks, int numPiles , BlocksWorld bw) {
        this.numBlocks = numBlocks;
        this.numPiles = numPiles;
        
        //Initialisation de l'ensemble des contraintes
        this.setConstraint = new HashSet<>();

        // Trois boucles pour parcourir nos différentes variables et créer toutes les contraintes possibles
        for (Variable variable1 : bw.getVariableOn()) {
            for (Variable variable2 : bw.getVariables()) {
                if (variable2.getName().startsWith("on") && !variable1.equals(variable2)) {
                    
                    //creation de la contrainte de difference entre la variable1 et variable2
                    DifferenceConstraint c1 = new DifferenceConstraint(variable1, variable2);
                        
                        // Cette partie du code permet de vérifier si une contrainte entre 
                        // deux variables existe déjà pour éviter de l'ajouter une seconde fois
                        boolean contient = false;
                        for (Constraint constraint : setConstraint) {
                            Set<Variable>  scop = constraint.getScope();
                            ArrayList<Variable> vs = new ArrayList<>(scop);
                            if (vs.contains(variable1) && vs.contains(variable2)) {
                                contient = true;
                            }
                        }
                        if (!contient) {
                            this.setConstraint.add(c1);
                        }

                        // Création de la contrainte d'implication : si la variable onb a la valeur b′,
                        // alors la variable fixedb′ doit avoir la valeur true
                        for (Variable var : bw.getVariableFixed()){
                            if (var.getName().equals("fixed"+variable2.getName().substring(2))) {
                                Implication imp = new Implication(variable1, Set.of(Integer.parseInt(variable2.getName().substring(2))), var, Set.of(true));
                                this.setConstraint.add(imp);
                            }
                        }

                }
                
                // Création de la contrainte d'implication : si la variable onb a la valeur −(p + 1),
                // alors la variable freep doit avoir la valeur false
                if (variable2.getName().startsWith("free")) {
                    Implication imp = new Implication(variable1, Set.of(-(Integer.parseInt(variable2.getName().substring(4)))),variable2 , Set.of(false));
                    this.setConstraint.add(imp);
                    
                }
            }
        }
        
    }

    // Les getters de la class
    public Set<Constraint> getSetConstraint() {
        return setConstraint;
    }

    public int getNumBlocks() {
        return numBlocks;
    }

    public int getNumPiles() {
        return numPiles;
    }

    
}

package blocksworld;

import java.util.*;
import modelling.*;
public class BlocksWorld {
    private int numBlocks; // Nombre de blocs
    private int numPiles; // Nombre de piles
    private Set<Variable> variables ,variableOn ; //les ensembles des variables
    private Set<BooleanVariable> variableFixed , variableFree;
    // Constructeur de la class 
    public BlocksWorld(int numBlocks, int numPiles) {
        this.numBlocks = numBlocks;
        this.numPiles = numPiles;
        
        //Initialisation des ensembles
        this.variables = new HashSet<>();
        this.variableFixed = new HashSet<>();
        this.variableOn = new HashSet<>();
        this.variableFree = new HashSet<>();

        // Création des variables onb pour chaque bloc
        for (int i = 0; i < numBlocks; i++) {
            Set<Object> domaineOnb = new TreeSet<>();
            
            // Ajouter les entiers négatifs pour représenter les piles
            for (int j = -numPiles; j < 0; j++) {
                domaineOnb.add(j);
            }
            
            // Ajouter les entiers positifs ou nuls pour représenter les blocs
            for (int k = 0; k < numBlocks; k++) {
                if (k != i) {
                    domaineOnb.add(k);
                }
            }
            
            //Création et ajout de la variable dans notre ensemble de variables
            Variable onb = new Variable("on" + i, domaineOnb);
            this.variables.add(onb);
            this.variableOn.add(onb);
        }

        // Création des variables fixedb pour chaque bloc
        for (int i = 0; i < numBlocks; i++) {
            BooleanVariable fixedb = new BooleanVariable("fixed" + i);
            this.variables.add(fixedb);
            this.variableFixed.add(fixedb);
        }

        // Création des variables freep pour chaque pile
        for (int i = 1; i <= numPiles; i++) {
            BooleanVariable freep = new BooleanVariable("free" + i);
            this.variables.add(freep);
            this.variableFree.add(freep);
           
        }
    }

    // Les getters de la class 
    public Set<Variable> getVariables() {
        return variables;
    }

    public int getNumBlocks() {
        return numBlocks;
    }

    public int getNumPiles() {
        return numPiles;
    }

    public Set<BooleanVariable> getVariableFixed() {
        return variableFixed;
    }

    public Set<Variable> getVariableOn() {
        return variableOn;
    }

    public Set<BooleanVariable> getVariableFree() {
        return variableFree;
    }

}
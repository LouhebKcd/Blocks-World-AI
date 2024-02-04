package blocksworld;

import java.util.*;
import modelling.*;

public class DemoModelling {
    public static void main(String[] args) {

        //Instance du BlocksWorld pour recuperer l'ensemble des variables
        BlocksWorld bw = new BlocksWorld(5, 2);

        //Creation des deux configurations
        Map<Variable, Object> monde = new HashMap<>();
        for (Variable variable : bw.getVariableOn()) {
            switch (variable.getName()) {
                case "on0":monde.put(variable, 1);
                case "on1":monde.put(variable, -1);
                case "on2":monde.put(variable, 3);
                case "on3":monde.put(variable, 4);
                case "on4":monde.put(variable, -2);
            }
        }
        for (Variable variable : bw.getVariableFixed()) {
            switch (variable.getName()) {
                case "fixed0":monde.put(variable, false);
                case "fixed1":monde.put(variable, true);
                case "fixed2":monde.put(variable, false);
                case "fixed3":monde.put(variable, true);
                case "fixed4":monde.put(variable, true);
            }
        }
        for (Variable variable : bw.getVariableFree()) {
            switch (variable.getName()) {
                case "free1":monde.put(variable, false);
                case "free2":monde.put(variable, false);
            }
        }

        Map<Variable, Object> monde2 = new HashMap<>();
        for (Variable variable : bw.getVariableOn()) {
            switch (variable.getName()) {
                case "on0":monde2.put(variable, -1);
                case "on1":monde2.put(variable, -2);
                case "on2":monde2.put(variable, 0);
                case "on3":monde2.put(variable, 2);
                case "on4":monde2.put(variable, 1);
            }
        }
        for (Variable variable : bw.getVariableFixed()) {
            switch (variable.getName()) {
                case "fixed0":monde2.put(variable, true);
                case "fixed1":monde2.put(variable, true);
                case "fixed2":monde2.put(variable, true);
                case "fixed3":monde2.put(variable, false);
                case "fixed4":monde2.put(variable, false);
            }
        }
        for (Variable variable : bw.getVariableFree()) {
            switch (variable.getName()) {
                case "free1":monde2.put(variable, false);
                case "free2":monde2.put(variable, false);
            }
        }
        

        // Variable pour garder une trace de la satisfaction des contraintes
        boolean toutesContraintesReguliere2 = true ,toutesContraintesReguliere = true; 

        BlockWorldRegular bwl = new BlockWorldRegular(bw);
        for (Constraint constraint : bwl.getContrainteReguliere()) {
            // Vérifier si la contrainte est satisfaite
            if (!constraint.isSatisfiedBy(monde)) {
                toutesContraintesReguliere = false;
            }
            if (!constraint.isSatisfiedBy(monde2)) {
                toutesContraintesReguliere2 = false;
            }
        }
        // Affichage d'un message selon la satisfaction des contraintes
        if (toutesContraintesReguliere) {
            System.out.println("La premiere configuration satisfait toutes les contraintes d'une configuration régulière.");
        } else {
            System.out.println("Le premiere configuration ne satisfait pas toutes les contraintes d'une configuration régulière.");
        }

        if (toutesContraintesReguliere2) {
            System.out.println("La deuxieme configuration satisfait toutes les contraintes d'une configuration régulière.");
        } else {
            System.out.println("Le deuxieme configuration ne satisfait pas toutes les contraintes d'une configuration régulière.");
        }
        
        // Variable pour garder une trace de la satisfaction des contraintes croissante
        boolean toutesContraintesCroissante2 =true , toutesContraintesCroissante = true;

        Croissante croissante = new Croissante(bw);
        for (Constraint constraint : croissante.getContrainteCroissante()) {
            // Vérifier si la contrainte croissante est satisfaite
            if (!constraint.isSatisfiedBy(monde)) {
                toutesContraintesCroissante = false;
            }
            if (!constraint.isSatisfiedBy(monde2)) {
                toutesContraintesCroissante2 = false;
            }
        }

        // Affichage d'un message selon la satisfaction des contraintes croissante
        if (toutesContraintesCroissante) {
            System.out.println("La premiere configuration satisfait toutes les contraintes croissantes.");
        } else {
            System.out.println("La premiere configuration ne satisfait pas toutes les contraintes croissantes.");
        }

        if (toutesContraintesCroissante2) {
            System.out.println("La deuxieme configuration satisfait toutes les contraintes croissantes.");
        } else {
            System.out.println("La deuxieme configuration ne satisfait pas toutes les contraintes croissantes.");
        }

    }
    
}
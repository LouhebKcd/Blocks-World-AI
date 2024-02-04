package modelling;

import java.util.*;

public class Demo{
    public static void main(String[] args){
        
        //creation des domaines pour nos variables
        Set<Object> d1 = new HashSet<>(Arrays.asList("Vert","Rouge","Jaune","Bleu"));
        Set<Object> d2 = new HashSet<>(Arrays.asList("Titi","Tata","Toto","Rouge"));

        //creation de nos variables
        Variable v1 = new Variable("Couleurs",d1);
        Variable v2 = new Variable("Personnes",d2);
        
        //initialisation des contraintes
        Constraint c1 = new Implication(v1,d1, v2,d2);
        Constraint c2 = new DifferenceConstraint(v1, v2);
        Constraint c3 = new UnaryConstraint(v1,d1);

        //instanciation
        Map<Variable,Object> instanciation = new HashMap<>();
        instanciation.put(v1,"Rouge");
        instanciation.put(v2,"Tato");
        
        //affichage du resultat (contrainte satisfaite ou pas)
        String chaine1 , chaine2 , chaine3;
        chaine1 = c1.isSatisfiedBy(instanciation) ? c1+"\nest satisfaite ! \n\n" : c1 +"\nn'est pas satisfaite !\n\n";
        chaine2 = c2.isSatisfiedBy(instanciation) ? c2+"\nest satisfaite ! \n\n" : c2 +"\nn'est pas satisfaite !\n\n";
        chaine3 = c3.isSatisfiedBy(instanciation) ? c3+"\nest satisfaite ! \n\n" : c3 +"\nn'est pas satisfaite !\n\n";
        System.out.println(chaine1+chaine2+chaine3);
        

        
        

    }
}

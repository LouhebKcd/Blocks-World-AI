package modelling;

import java.util.*;

//la classe Implication implemente l'interface Constraint.
public class Implication implements Constraint{

    private Variable var1 , var2; // Variables
    private Set<Object> domaine , domaine2; //Sous-ensembles des domaines des deux variables

    //constructeur
    public Implication(Variable v1 , Set<Object> domaine , Variable v2 , Set<Object> domaine2){
        this.var1 = v1;
        this.var2 = v2;
        this.domaine = domaine;
        this.domaine2 = domaine2;
    }

    //Implementation de la méthode getScope() retournant l'ensemble des variables sur lesquelles porte la contrainte
    @Override
    public Set<Variable> getScope(){
        return new HashSet<Variable>(Arrays.asList(this.var1,this.var2));
    }
    
    //Implémentation de la méthode isSatisfiedBy retournant un bollean selon si la contrainte d'implication est satisfaite ou pas
    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> instanciation) {
        if (!instanciation.containsKey(this.var1) || !instanciation.containsKey(this.var2)) {
            throw new IllegalArgumentException("Il y a des variables qui ne sont pas dans l'instanciation");
        }

        return (!this.domaine.contains(instanciation.get(this.var1))) || this.domaine2.contains(instanciation.get(this.var2));
    }

    //La méthode toString(bool) affichera si la contrainte est satisfaite ou pas
    @Override
    public String toString() {
        return "La contrainte Implication avec : \n Variable 1 : " + this.var1 + " domaine : " + this.domaine + "\n" + " Variable 2 : " + this.var2 + " domaine : " + this.domaine2;
    }
}

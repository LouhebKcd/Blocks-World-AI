package modelling;


import java.util.*;

//la classe Implication implemente l'interface Constraint.
public class DifferenceConstraint implements Constraint {

    private Variable var1 , var2; //Variables

	//constructeur
    public DifferenceConstraint(Variable v1 , Variable v2){
        this.var1 = v1;
        this.var2 = v2;
    }
    
    //Implementation de la méthode getScope() retournant l'ensemble des variables sur lesquelles porte la contrainte
    @Override
    public Set<Variable> getScope(){
        return new HashSet<Variable>(Arrays.asList(this.var1,this.var2));
    }

    //Implémentation de la méthode isSatisfiedBy retournant un bollean selon si la contrainte de differnce des deux variables est satisfaite ou pas
    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> instanciation) {
        if (!instanciation.containsKey(this.var1) || !instanciation.containsKey(this.var2)) {
            throw new IllegalArgumentException("Il y a des variables qui ne sont pas dans l'instanciation");
        }

        return !instanciation.get(var1).equals(instanciation.get(var2));
    }

    //La méthode toString(bool) affichera si la contrainte est satisfaite ou pas
    @Override
    public String toString() {
        return "La contrainte de différence de deux variables avec : \n Variable 1 : " + this.var1 + "\n Variable 2 : " + this.var2;  
    }

}

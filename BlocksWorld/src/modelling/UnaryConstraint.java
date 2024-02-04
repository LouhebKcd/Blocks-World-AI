package modelling;

import java.util.*;

//la classe Implication implemente l'interface Constraint.
public class UnaryConstraint implements Constraint{

    private Variable var; //Variable
    private Set<Object> domaine;  //Sous-ensembles du domaine de la variable
    
	//constructeur
    public UnaryConstraint(Variable var , Set<Object> domaine){
        this.var = var;
        this.domaine = domaine;
    }
	
	//Implementation de la méthode getScope() retournant l'ensemble des variables sur lesquelles porte la contrainte
    @Override
    public Set<Variable> getScope(){
        return new HashSet<Variable>(Arrays.asList(this.var));
    }
	
    	//Implémentation de la méthode isSatisfiedBy retournant un boolean selon si la contrainte de la forme var ∈ domaine est satisfaite ou pas
    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> instanciation) {
        if (!instanciation.containsKey(this.var)) {
            throw new IllegalArgumentException("Il y a des variables qui ne sont pas dans l'instanciation");
        }

        return this.domaine.contains(instanciation.get(this.var));
    }

    //La méthode toString(bool) affichera si la contrainte est satisfaite ou pas
    @Override
    public String toString() {
	return "La contrainte Appartenance variable avec : \n Variable 1 : " + this.var + "\n domaine : " + this.domaine;
    }


}

package modelling;

import java.util.*;

public class Variable {

    private String nom; // nom de la variable
    private Set<Object> domaine; // domaine : ensemble de valeurs que la variable peut prendre
	
	
	//constructeur
    public Variable(String nom ,Set<Object> domaine ){
        this.nom = nom;
        this.domaine = domaine;
    }
	
	//redéfinition de la méthode equals(Object) pour comparer deux noms de variables.
    @Override
    public boolean equals(Object objet) {
        if (objet instanceof Variable) {
            return ((Variable) objet).nom.equals(this.nom);
        } 
        return false;
    }
	
	//redéfinition de la méthode hashCode() retournant le code du nom.
    @Override
    public int hashCode() {
        return this.nom.hashCode();
    }
	
	//accesseurs
    public String getName() {
        return nom;
    }

    public Set<Object> getDomain() {
        return domaine;
    }
	
	//redéfinition de la méthode toString() pour afficher les noms de vaariables.
    @Override
    public String toString() {
        return getName();
    }

}

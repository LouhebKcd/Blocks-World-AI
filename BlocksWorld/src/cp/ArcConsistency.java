package cp;

import java.util.*;
import modelling.*;

public class ArcConsistency {

    private Set<Constraint> listContraints; //ensemble de contraintes
	
	//constructeur
    public ArcConsistency(Set<Constraint> listContraint){
        this.listContraints = new HashSet<>();
        
        //parcourir chaque contrainte de notre ensemble de contrainte
        for (Constraint element : listContraint) {
            Set<Variable> scop = element.getScope();
            if (scop.size() == 1) {//verifie si elle est unaire et l'ajouter
                listContraints.add(element);
            } else if(scop.size() == 2) {//ou binaire et l'ajouter
                listContraints.add(element);
            }else{
            //lancer une exception si une contrainte n'est ni unaire ni binaire
                throw new IllegalArgumentException("Contraint Ni unaire ni binaire");
            }
        }
    }


    //methode retorunant un boolean pour appliquer la contrainte de cohenrence sur les noeuds
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> ensembleDomaine){
	
	//pour chaque couple : variable et ses valeurs possibles
        for (Map.Entry<Variable, Set<Object>> coupleCleValeur : ensembleDomaine.entrySet()) {
	
	    //pour chaque valeur possible
            for (Object valeur : new HashSet<>(coupleCleValeur.getValue())) {
		//pour chaque contrainte de notre ensemble de contraintes
                for (Constraint constraint : this.listContraints) {
			
                    Set<Variable> scop = constraint.getScope();
                    //verifie si la contrainte est unaire et si elle concerne la variable actuelle
                    if (scop.size()==1 && scop.contains(coupleCleValeur.getKey())) {

			//nouvelle instanciation avec la valeur 
                        Map<Variable,Object> instantiation = new HashMap<>();
                        instantiation.put(coupleCleValeur.getKey(),valeur);
			
			//verifie si la contrainte est satisfaite par la valeur sinon la suprimer de l'ensemble des domaines
                        if (!constraint.isSatisfiedBy(instantiation)) {
                            coupleCleValeur.getValue().remove(valeur);
                            
                        }
                    }
                }
            }
        }
	
	//verifie si toutes les valeurs de domaines sont supprimer apres avoir appliquer la contrainte 
        for (Map.Entry<Variable, Set<Object>> coupleCleValeur : ensembleDomaine.entrySet()) {
            if (coupleCleValeur.getValue().isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    

	//revise des domaines de variables en fonction des contraintees entre edux variables
    public boolean revise(Variable v1 , Set<Object> domaine1 , Variable v2 , Set<Object> domaine2){
        
        boolean del = false; // valeur pour dire si une valeur est supprimer ou pas
        
        //pour chaque valeur possible de la premier variable
        for (Object valeur1 : new HashSet<>(domaine1)) {
            
            boolean viable = false;
            //pour chaque valeur possible de la deuxieme variable
            for (Object valeur2 : new HashSet<>(domaine2)) {
                boolean toutSatisfait = true;
                //parcourt de chaque contrainte
                for (Constraint constraint : this.listContraints) {
                    Set<Variable> scop = constraint.getScope();
                    
                    //si la contrainte est binaire alors elle concerne les deux variables
                    if (scop.size() == 2 && scop.contains(v1) && scop.contains(v2)) {
                    
                    //instanciation avec les valeurs actuelles
                        Map<Variable,Object> n = new HashMap<>();
                        n.put(v1,valeur1);
                        n.put(v2,valeur2);
                        
                        //verifie si l'instantiation ne satisfait pas la contrainte
                        if(!constraint.isSatisfiedBy(n)){
                            toutSatisfait = false;
                            break;
                        }
                    }
                }
                                // Vérifie si au moins une valeur de la deuxième variable satisfait les contraintes
                if (toutSatisfait) {
                    viable = true;
                    break;
                }
            }
                        // si aucune valeur de la deuxième variable ne satisfait les contraintes, supprime la valeur de la première variable
            if (!viable) {
               domaine1.remove(valeur1);
                del = true;  //indique qu'une valeur a ete supprimée
            }
        }
        return del; // retorune true si au moins une valeur a ete supprimée , false sinon
    }

	
	//méthode implementant l'algorithme ac1
    public boolean ac1(Map<Variable, Set<Object>> ensembleDomaine) {
        boolean change; // variable pour connaitre les changement
        
        //si l'ensemble de domaine est vide en appliquant la contrainte de coherence
        if (!enforceNodeConsistency(ensembleDomaine)) {
            return false;
        }
		
	//boucle pour applqiuer l'algorithme
        do {
            change = false;
	
	    //pour chaque variable et son ensemble de domaine
            for (Map.Entry<Variable, Set<Object>> coupleCleValeur : ensembleDomaine.entrySet()) {
               
                Variable variablei = coupleCleValeur.getKey();
                Set<Object> domaine = new HashSet<>(coupleCleValeur.getValue());
                Set<Variable>  tempVariable = new HashSet<>();
		
		//pour chaque autre variable et son ensemble de domaines (faire toutes les combinaisons possibles de variables)
                for (Map.Entry<Variable, Set<Object>> autreCouple : ensembleDomaine.entrySet()) {
        		
        	    //si les variables actuelles ne sont pas les memes 
                    if (!variablei.equals(autreCouple.getKey())) {
                        //revise des domaines des variables et marque le changement si necessaire
                        if (revise(variablei, domaine,autreCouple.getKey(), autreCouple.getValue())) {
                            change = true;
                            tempVariable.add(variablei);
                        }
                    }

                }
		
		//mets a jour l'ensemble de domaines pour les variable ayant eu un changement
                for (Variable variable : tempVariable) {
                    ensembleDomaine.get(variable).retainAll(domaine);
                }

            }
        } while (change);//tant qu'il y a un changement
	
	//verifie si un esemble de domaine est devenu vide apres avoir appliquer l'algorithme
        for (Map.Entry<Variable, Set<Object>> coupleCleValeur : ensembleDomaine.entrySet()) {
            if (coupleCleValeur.getValue().isEmpty()) {
                return false;
            }
        }
        return true;
    }
	
	//methode retournant l'ensemble de nos contraintes
    public Set<Constraint> getListContraints() {
        return listContraints;
    }
    
}

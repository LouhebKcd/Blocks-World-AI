package cp;

import java.util.*;
import modelling.*;

//class RandomValueHeuristic implementant ValueHeuristic
public class RandomValueHeuristic implements ValueHeuristic {

    private Random generateurAleatoire; // generatuer aleatoire 
	
	//constructeur
    public RandomValueHeuristic(Random generateurAleatoire) {
        this.generateurAleatoire = generateurAleatoire;
    }
    
    //methode pour ordonner les valeurs de façon aléatoire pour une variable passée en argument
    @Override
    public List<Object> ordering(Variable variable, Set<Object> domaine) {
        List<Object> valeurs = new ArrayList<>(domaine); //création d'une liste
        Collections.shuffle(valeurs, generateurAleatoire); //melange aleatoirement la liste de valeurs
        return valeurs; // retourne la liste des valeurs mélangées
    }
}

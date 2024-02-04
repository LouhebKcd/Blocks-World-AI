package modelling;

import java.util.*;

//Cette class hérite de la classe Variable

public class BooleanVariable extends Variable{

	//constructeur prend un nom en argument et initialise le domaine à l'ensemble {true,false}
    public BooleanVariable(String nom){
        super(nom,new HashSet<Object>(Arrays.asList(true,false)));
    }
}

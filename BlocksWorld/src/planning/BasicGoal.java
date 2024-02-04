package planning;

import java.util.Map;
import modelling.*;

//la class BasicGoal implemente l'interface Goal
public class BasicGoal implements Goal{

    private Map<Variable, Object> instanciation; //instanciation de variable

    //constructeur
    public BasicGoal(Map<Variable, Object> inst){
        this.instanciation = inst;
    }
    
    //retourne true si notre but est satisfait par etat ou false sinon
    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> etat) {
        return etat.entrySet().containsAll(instanciation.entrySet());
    }
}
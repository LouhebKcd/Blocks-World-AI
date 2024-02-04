package planning;

import java.util.HashMap;
import java.util.Map;


import modelling.*;
//la class BasicAction implemente l'interface Action
public class BasicAction implements Action {

    private Map<Variable, Object> precondition,effet; //precondition et effet
    private int cout; //cout de l'action
	
	//constructeur
    public BasicAction(Map<Variable, Object> precond ,Map<Variable, Object> effet , int cout){
        this.precondition = precond;
        this.effet = effet;
        this.cout = cout;
    }
    
    //la methode isApplicable retourne un boolean verifiant si l'effet est applicable sur l'etat
    @Override
    public boolean isApplicable(Map<Variable, Object> etat) {
        
        return etat.entrySet().containsAll(this.precondition.entrySet());

    }

    //la methode seccessor applique l'effet sur l'etat et retournant un nouvel etat
    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> etat) {
        if (etat.isEmpty()) { //si l'etat est vide on retourne directement l'effet
            return this.effet;
        }

        if (isApplicable(etat)) { //si l'effet est applicable sur l'etat
            Map<Variable, Object> nvEtat = new HashMap<Variable, Object>(); //on cree un nouvel etat
            
            //on recupere les cles et valeurs dans l'ancien etat et on modifie celles qui sont concernées par l'effet puis on les ajoute a chaque fois dans le nouvel etat
            for (Map.Entry<Variable, Object> couple : etat.entrySet()) {
                if (effet.containsKey(couple.getKey())) {
                    nvEtat.put(couple.getKey(),effet.get(couple.getKey()));
                }else{
                    nvEtat.put(couple.getKey(),couple.getValue());
                }
            }
            
            for (Map.Entry<Variable, Object> couple : effet.entrySet()) {
                if (!nvEtat.containsKey(couple.getKey())) {
                    nvEtat.put(couple.getKey(), effet.get(couple.getKey()));
                }
            }
            return nvEtat; //on retourne le nouvel etat
        }
        return null;
    }

	//retorune un entier (cout de l'action)
    @Override
    public int getCost() {
        return cout;
    }

    @Override
    public String toString() {
        StringBuilder sd = new StringBuilder();
        for ( Map.Entry<Variable, Object> couple : precondition.entrySet()) {
            sd.append(couple.getKey().toString()+"\t"+couple.getValue()+"\t");
        }
        sd.append("\n");
        for ( Map.Entry<Variable, Object> couple : effet.entrySet()) {
            sd.append(couple.getKey().toString()+"\t"+couple.getValue()+"\t");
        }
        sd.append("\n");
        return sd.toString();
    }
}

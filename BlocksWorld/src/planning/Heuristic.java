package planning;

import java.util.Map;
import modelling.*;


public interface Heuristic {

    public float estimate(Map<Variable, Object> etat);
}
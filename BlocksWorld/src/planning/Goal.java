package planning;

import modelling.*;
import java.util.Map;

public interface Goal {

    public boolean isSatisfiedBy(Map<Variable, Object> etat); //retourne un boolean et verifie si notre etat satisfait notre but

}

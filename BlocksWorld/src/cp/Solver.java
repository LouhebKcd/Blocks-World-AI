package cp;

import java.util.*;
import modelling.*;

//interface solver
public interface Solver {
	
	//méthode retournant une solution si elle existe , null sinon
    public Map<Variable, Object> solve();
}

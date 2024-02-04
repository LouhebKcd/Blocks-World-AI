package blocksworld;

import java.util.HashSet;
import java.util.Set;
import modelling.*;

public class BlockWorldRegular {
    
    private  Set<Constraint> contrainteReguliere; // Ensemble des contraintes régulières

    public BlockWorldRegular(BlocksWorld bw){
        contrainteReguliere = new HashSet<>();

        // Création des contraintes
        for (Variable variable1 : bw.getVariableOn()) {
            for (Variable variable2 : bw.getVariableOn()) {

                if (!variable1.equals(variable2)) {
                    // Calcul de l'écart entre les deux blocs
                    int ecart = Integer.parseInt(variable1.getName().substring(2))-Integer.parseInt(variable2.getName().substring(2));

                 for (Variable variable3 : bw.getVariableOn()) {
                    if (!variable1.equals(variable3) && !variable2.equals(variable3)) {
                        
                        // Calcul de l'écart entre les deux prochains blocs
                        int ecart2 = Integer.parseInt(variable2.getName().substring(2))-Integer.parseInt(variable3.getName().substring(2));
                        
                        // Vérification si on a le même écart
                        if (ecart == ecart2) {
                            
                            // Ajout des différentes piles dans notre domaine
                            Set<Object> domain= new HashSet<>();
                            for (Variable var : bw.getVariableFree()) {   
                                domain.add(-Integer.parseInt(var.getName().substring(4)));
                            }
                            domain.add(Integer.parseInt(variable3.getName().substring(2)));
                            
                            // Création de notre implication qui nous permet de respecter la régularité du monde
                            Implication imp = new Implication(variable1, Set.of(Integer.parseInt(variable2.getName().substring(2))), variable2, domain);
                            contrainteReguliere.add(imp);
                        }
                    }
                    
                }
                }
            }
        }


    }

    // Méthode pour récupérer les contraintes régulières
    public Set<Constraint> getContrainteReguliere() {
        return contrainteReguliere;
    }

}

package blocksworld;

import java.util.HashSet;
import java.util.Set;
import modelling.*;
public class Croissante {
    
    private Set<Constraint> contrainteCroissante; // Ensemble des contraintes de croissante


    public Croissante(BlocksWorld bw){
        contrainteCroissante = new HashSet<>();

        // Parcours des variables on de notre monde
        for (Variable variable1 : bw.getVariableOn()) {
            
            // Récupération de la valeur de la variable courante
            int valeurVariable = Integer.parseInt(variable1.getName().substring(2));
            Set<Object> domaine = new HashSet<>();
            
            // Parcours du domaine de la variable
            for (Object valeur : variable1.getDomain()) {
                
                // Si la valeur est inférieure à la valeur de la variable courante, l'ajouter au domaine
                if ((int) valeur < valeurVariable ) {
                    domaine.add(valeur);
                }
            }

            // Création d'une contrainte unaire avec le sous-domaine pour assurer la croissance du monde
            UnaryConstraint uc = new UnaryConstraint(variable1, domaine);
            contrainteCroissante.add(uc);
            
        }
        
    }

    // Méthode pour récupérer les contraintes croissantes
    public Set<Constraint> getContrainteCroissante() {
        return contrainteCroissante;
    }
}

package blocksworld;

import java.util.List;
import java.util.Random;
import java.util.Set;


import bwgeneratordemo.Demo;
import modelling.BooleanVariable;
import datamining.*;


//classe Executable
public class DemoDataMining {

    public static void main(String[] args) {
        
        int nbInstances = 10000; //nombre d'instance 
        float minFrequency = 2/3; //frequence Minimale
        float confiance = 95/100; // confaince
        
        //instanciation d'un monde des blocks
        BlocksWorld bw = new BlocksWorld(2,1);
        
        //instanciation pour recuperer les boolean variables
        BooleanBlocksWorld booleanBlocksWorld = new BooleanBlocksWorld(2, 1, bw);
        
        //creation d'une booleanDatabase avec les variables des booleanBlocksWorld
        BooleanDatabase booleanDatabase = new BooleanDatabase(booleanBlocksWorld.getVariables());
        
        //affichage de nos BooleanVariables
        for (BooleanVariable var : booleanBlocksWorld.getVariables()) {
            System.out.println(var + "\n");
        }
        
        //pour nbInstances fois faire
        for (int i = 0; i < nbInstances; i++) {
            
            //genrer un nombre aleatoire
            Random random = new Random();
            
            //generation d'un etat de type liste de liste d'integer
            List<List<Integer>> state = Demo.getState(random);
            
            //initialisation du nombre dans notre etat
            int nbBlocks = 0;
            
            for(List<Integer> pile : state) {
                nbBlocks += pile.size();
            }
            
            //instanciation du blocks world avec le nombre de blocks et de piles dans l'etat courant genere
            BlocksWorld blocksWorld = new BlocksWorld(nbBlocks, state.size());
            
            //nouvelle instance ayant comme argument le nomb rede blocks , piles de l'etat et blocksWorld genree avec ce dernier
            BooleanBlocksWorld instance = new BooleanBlocksWorld(nbBlocks, state.size(), blocksWorld);
            
            //Ajout des variables de l'instance dans notre base
            booleanDatabase.add(instance.getVariables());
        }
        
        //Instanciation de la class Apriori sur notre base
        Apriori apriori = new Apriori(booleanDatabase);
        
        //extraction des items les plus frequents
        Set<Itemset> itemsetsFrequents = apriori.extract(minFrequency);
        
        //affichage des items les plus frequents
        System.out.println("Motifs fréquents Apriori: \n");
        for (Itemset itemset : itemsetsFrequents) {
            System.out.println(itemset);
        }
        
        System.out.println("\n\n\n");
        
        //Implementation de BruteForceAssociationRuleMiner
        BruteForceAssociationRuleMiner brute = new BruteForceAssociationRuleMiner(booleanDatabase);
        
        //Extraction des AssociationRule
        Set<AssociationRule> setOfAssociationRule = brute.extract(minFrequency, confiance);
               
        //Affichage des AssociationRule
        System.out.println("Association Rules : \n");
        for(AssociationRule rule : setOfAssociationRule) {
            System.out.println(rule);
        }
        
        /*//Affichage des transactions
        for (Set<BooleanVariable> transaction : booleanDatabase.getTransactions()) {
            System.out.println("Transaction : " + transaction);
        }*/
    }
}
package blocksworld;
import java.util.HashSet;
import java.util.Set;

import modelling.*;

public class BooleanBlocksWorld {
    private int numBlocks , numPiles;
    private Set<BooleanVariable> variables;

    public BooleanBlocksWorld(int numBlocks , int numPiles , BlocksWorld bw){
        this.numBlocks = numBlocks;
        this.numPiles = numPiles;
        this.variables = new HashSet<>();

        for (Variable var1 : bw.getVariableOn() ) {
            for(Variable var2 : bw.getVariableOn()){
                if(!var1.equals(var2) ){
                    BooleanVariable onbbprime = new BooleanVariable("on"+Integer.parseInt(var1.getName().substring(2))+","+Integer.parseInt(var2.getName().substring(2)));
                    this.variables.add(onbbprime);
                    
                }

            }
                            
            for(Variable var3 : bw.getVariableFree()){
                BooleanVariable ontablebbprime = new BooleanVariable("on-table"+Integer.parseInt(var1.getName().substring(2))+","+Integer.parseInt(var3.getName().substring(4)));
                this.variables.add(ontablebbprime);
            }
        }

        for(BooleanVariable var4 : bw.getVariableFree()){
            this.variables.add(var4);
        }

        for(BooleanVariable var5 : bw.getVariableFixed()){
            this.variables.add(var5);
        }
    }

    public Set<BooleanVariable> getVariables(){
        return this.variables;
    }

    public int getNumBlocks() {
        return numBlocks;
    }

    public int getNumPiles() {
        return numPiles;
    }
    
}


   


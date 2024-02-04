package datamining;

/**
 * BooleanDatabase
 */
import java.util.*;
import modelling.*;

public class BooleanDatabase {
    private Set<BooleanVariable> items; // ensemble de nos BooleanVariable
    private List<Set<BooleanVariable>> transactions; // liste d'ensemble de booleanVariables

    // constructeur
    public BooleanDatabase(Set<BooleanVariable> ensembleItems) {
        this.items = ensembleItems;
        transactions = new ArrayList<>();
    }

    // methode pour ajouter une transaction a notre liste de transactions
    public void add(Set<BooleanVariable> transaction) {
        this.transactions.add(transaction);
    }

    // Accesseur
    public Set<BooleanVariable> getItems() {
        return items;
    }

    // Accesseur
    public List<Set<BooleanVariable>> getTransactions() {
        return transactions;
    }

}
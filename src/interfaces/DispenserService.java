package interfaces;

import classes.Coin;
import classes.Product;
import exceptions.NotEnoughChangeException;
import exceptions.NotEnoughMoneyException;
import java.util.Collection;

/**
 * The DispenserService interface describes the functionality that a 
 * service should implement.
 * @author Kenneth
 */
public interface DispenserService {
    
    /**
     * This method should be called when there are no limits when returning 
     * change to a customer.
     * @param pence Amount provided by the customer.
     * @param p Product selected by the customer.
     * @return Collection of coins representing the exact amount as difference
     * between the pence provided by the user and the price of the product
     * selected by the user.
     * @throws exceptions.NotEnoughMoneyException Exception thrown if the user
     * has not provided enough money.
     */
    public Collection<Coin> getOptimalChangeFor(int pence, Product p) 
            throws NotEnoughMoneyException;
    
    /**
     * This method should be called when there are limits when returning 
     * change to a customer.
     * @param pence Amount provided by the customer.
     * @param p Product selected by the customer.
     * @return Collection of coins representing the exact amount as difference
     * between the pence provided by the user and the price of the product
     * selected by the user.
     * @throws exceptions.NotEnoughChangeException Exception thrown if there is
     * not enough change in the machine.
     * @throws exceptions.NotEnoughMoneyException Exception thrown if the user
     * has not provided enough money.
     */
    public Collection<Coin> getChangeFor(int pence, Product p) 
            throws NotEnoughChangeException, NotEnoughMoneyException;
    
}

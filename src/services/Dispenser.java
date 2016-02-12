package services;

import classes.Coin;
import classes.Product;
import exceptions.NotEnoughChangeException;
import exceptions.NotEnoughMoneyException;
import interfaces.DispenserService;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * The dispenser class is the service that handles operations.
 *
 * @author Kenneth
 */
public class Dispenser implements DispenserService {

    private Properties properties;
    private Map<Coin, Integer> availableChangeMachine;
    private Collection<Coin> changeInProgress;

    /**
     * The dispenser class acts as a conduit between the machine and the service.
     * This class must implement the DispenserService interface.
     */
    public Dispenser() {
        this.properties = new Properties();
        this.availableChangeMachine = new HashMap();
        this.changeInProgress = new ArrayList();
    }

    @Override
    public Collection<Coin> getOptimalChangeFor(int pence, Product p)
            throws NotEnoughMoneyException {

        changeInProgress.clear();
        int difference = pence - p.getProductPrice();

        if (enoughMoneyProvided(pence, p)) {

            for (Coin coin : Coin.values()) {
                if (isCoinTypeNeeded(coin, difference - getCurrentChange())) {
                    supplyThisCoinType(coin, difference - getCurrentChange(), false);
                }
            }
        } else {
            throw new NotEnoughMoneyException("You have not provided enough money.\n"
                    + "Remeber that 10 pounds need to be entered as 1000 (one thousand pennies), for example.");

        }
        return changeInProgress;
    }

    @Override
    public Collection<Coin> getChangeFor(int pence, Product p)
            throws NotEnoughChangeException, NotEnoughMoneyException {

        changeInProgress.clear();
        int difference = pence - p.getProductPrice();

        loadConfig();

        if (enoughMoneyProvided(pence, p)) {
            if (enoughChange(difference)) {
                for (Coin coin : Coin.values()) {
                    if (isCoinTypeNeeded(coin, difference - getCurrentChange())) {
                        supplyThisCoinType(coin, difference - getCurrentChange(), true);
                    }
                }
                updateConfig();
            } else {
                throw new NotEnoughChangeException("The machine has run out of change.");
            }
        } else {
            throw new NotEnoughMoneyException("You have not provided enough money.");
        }

        return changeInProgress;
    }

    /**
     * This method initialises the properties files with a specific amount of
     * coins of each type indicating the available change.
     */
    public void initConfig() {

        properties.setProperty("100", "11");
        properties.setProperty("50", "24");
        properties.setProperty("20", "0");
        properties.setProperty("10", "99");
        properties.setProperty("5", "200");
        properties.setProperty("2", "11");
        properties.setProperty("1", "23");

        FileWriter writer = null;

        try {
            writer = new FileWriter("coin-inventory.properties");
            properties.store(writer, "Author: KP");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Updates the configuration file with the latest values after a purchase.
     */
    private void updateConfig() {

        availableChangeMachine.entrySet().stream().forEach((newAvailableChange) -> {
            properties.setProperty(Integer.toString(newAvailableChange.getKey().getValueAsInt()),
                    newAvailableChange.getValue().toString());
        });

        FileWriter writer = null;

        try {
            writer = new FileWriter("coin-inventory.properties");
            properties.store(writer, "Author: KP");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Loads the configuration file into a Map for later use in
     * the application.
     */
    public void loadConfig() {
        try (FileReader reader = new FileReader("coin-inventory.properties")) {
            availableChangeMachine.clear();
            properties.load(reader);
            properties.entrySet().stream().forEach((e) -> {
                switch (e.getKey().toString()) {
                    case "1":
                        availableChangeMachine.put(Coin.ONE_PENNY, Integer.parseInt(e.getValue().toString()));
                        break;
                    case "2":
                        availableChangeMachine.put(Coin.TWO_PENCE, Integer.parseInt(e.getValue().toString()));
                        break;
                    case "5":
                        availableChangeMachine.put(Coin.FIVE_PENCE, Integer.parseInt(e.getValue().toString()));
                        break;
                    case "10":
                        availableChangeMachine.put(Coin.TEN_PENCE, Integer.parseInt(e.getValue().toString()));
                        break;
                    case "20":
                        availableChangeMachine.put(Coin.TWENTY_PENCE, Integer.parseInt(e.getValue().toString()));
                        break;
                    case "50":
                        availableChangeMachine.put(Coin.FIFTY_PENCE, Integer.parseInt(e.getValue().toString()));
                        break;
                    case "100":
                        availableChangeMachine.put(Coin.ONE_POUND, Integer.parseInt(e.getValue().toString()));
                        break;
                }
            });
        } catch (Exception e) {
            System.out.println("There was a problem loading the configuration file.");
        }
    }

    /**
     * Checks if the user has provided enough money to purchase a product.
     * @param pence Money provided by the user.
     * @param p The Product selected by the user.
     * @return True if there is enough money. False otherwise.
     */
    private boolean enoughMoneyProvided(int pence, Product p) {
        return pence - p.getProductPrice() >= 0;
    }

    /**
     * Checks if the current coin in the Map<Coin,Integer> needs to be added
     * to the changeInProgress.
     * @param coin Current coin in the Map<Coin,Integer> 
     * @param difference Amount between the money provided and the product price.
     * @return True if the current coin fits the criteria for the return change.
     */
    private boolean isCoinTypeNeeded(Coin coin, int difference) {
        return difference / coin.getValueAsInt() > 0;
    }

    /**
     * Distributes X amount of a coin type.
     * @param coin Current coin in the Map<Coin,Integer> 
     * @param difference Amount between the money provided and the product price.
     * @param changeLimits Boolean indicating whether the machine has unlimited change or not.
     */
    private void supplyThisCoinType(Coin coin, int difference, boolean changeLimits) {
        int cap = getCap(difference, coin, changeLimits);
        for (int coinsAdded = 0; coinsAdded < cap; coinsAdded++) {
            changeInProgress.add(coin);
            if (changeLimits) {
                availableChangeMachine.put(coin, availableChangeMachine.get(coin) - 1);
            }
        }
    }

    /**
     * Checks if there is enough change in the machine to return to the customer.
     * @param difference Amount between the money provided and the product price.
     * @return Boolean indicating whether there is enough change or not.
     */
    private boolean enoughChange(int difference) {
        return getTotalChangeInMachine() - difference >= 0;
    }

    /**
     * Returns the total amount in pence of what's left in the machine.
     * @return an integer indicating the totalChange left in the machine.
     */
    public int getTotalChangeInMachine() {

        int totalChange = 0;

        totalChange = availableChangeMachine.entrySet().stream().map((ac)
                -> ac.getKey().getValueAsInt() * ac.getValue()).reduce(totalChange, Integer::sum);

        return totalChange;
    }

    /**
     * Returns the total amount in pence of what is currently being returned to the customer.
     * @return an integer indicating the totalChange being returned to the customer.
     */
    private int getCurrentChange() {

        int totalChange = 0;

        totalChange = changeInProgress.stream().map((c)
                -> c.getValueAsInt()).reduce(totalChange, Integer::sum);

        return totalChange;
    }

    /**
     * Returns an integer indicating the maximum number of coins available of
     * a specific type.
     * @param difference Amount between the money provided and the product price.
     * @param coin Current coin in the Map<Coin,Integer> 
     * @param changeLimits Indicated whether there is an unlimited supply of change
     * in the machine or not.
     * @return Integer indicating the maximum amount of coins available for distribution.
     */
    private int getCap(int difference, Coin coin, boolean changeLimits) {

        int capExact = difference / coin.getValueAsInt();

        if (changeLimits) {
            int capMachine = availableChangeMachine.get(coin);
            if (capExact >= capMachine) {
                return capMachine;
            }
        }

        return capExact;
    }

    /**
     * Returns a Map containing the available change in the machine.
     * @return Map with config info
     */
    public Map<Coin, Integer> getAvailableChangeMachine() {
        return availableChangeMachine;
    }

}

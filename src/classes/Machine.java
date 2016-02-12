package classes;

import java.util.ArrayList;
import java.util.List;
import services.Dispenser;

/**
 * This class runs with the physical vending machine.
 * Its purpose is to generate test data and retrieve the menu and dispenser service.
 * @author Kenneth
 */
public class Machine {

    private Dispenser dispenser;
    private List<Product> menu;

    /**
     * The constructor requires a <code>Dispenser</code> service to operate. 
     * This class will initalise the configuration file and products for sale.
     * @param dispenser efefz
     */
    public Machine(Dispenser dispenser) {
        this.dispenser = dispenser;
        this.dispenser.initConfig();
        this.menu = new ArrayList();
        generateTestProducts();
    }

    /**
     * Generates a list of products for testing purposes.
     */
    private void generateTestProducts() {

        menu.add(new Product(0, "Roasted Chicken", 1010));
        menu.add(new Product(1, "Spaghetti carbonara", 800));
        menu.add(new Product(2, "Steak tartare", 1125));
        menu.add(new Product(3, "Baked salmon", 1401));
        menu.add(new Product(4, "Veggie pizza", 1299));
        menu.add(new Product(5, "Paella", 1653));
        menu.add(new Product(6, "Onion soup", 844));
        menu.add(new Product(7, "Caesar salad", 1110));
        menu.add(new Product(8, "XXL Burger", 1093));
        menu.add(new Product(9, "BBQ Ribs", 1367));
    }

    /**
     * Returns a List of Products
     * @return List of Product
     */
    public List<Product> getMenu() {
        return menu;
    }

    /**
     * Returns a Dispenser
     * @return Dispenser
     */
    public Dispenser getDispenser() {
        return dispenser;
    }
}

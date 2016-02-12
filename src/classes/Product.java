
package classes;

/**
 * This class represents an item sold by the vending machine.
 * I created this for testing purposes only in order to have a reference
 * when calculating the change to be returned to the user.
 * @author Kenneth
 */
public class Product {
    
    private int productID;
    private String productName;
    private int productPrice;

    public Product() {
    }

    /**
     * The constructor for the Product class accepts 3 parameters.
     * The list of products for testing are initialized in the class Machine.
     * Feel free to add any products you wish in that file.
     * @param productID ID of the product. Make sure it continues the trend.
     * @param productName Name of the product. This is a meals machine so have fun.
     * @param productPrice Product price in pennies! 12.40 is 1240.
     */
    public Product(int productID, String productName, int productPrice) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    /**
     * Returns the product ID
     * @return productID
     */
    public int getProductID() {
        return productID;
    }

    /**
     * Returns the product name
     * @return productName
     */
    public String getProductName() {
        return productName;
    }

     /**
     * Returns the product price
     * @return productPrice
     */
    public int getProductPrice() {
        return productPrice;
    }
}

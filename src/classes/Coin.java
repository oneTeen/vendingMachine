
package classes;

/**
 * Enumeration of Coins. These are the currently available coins as currency
 * in the UK. Coin has a name and a value that can be retrieved using the 
 * methods below.
 * @author Kenneth
 */
public enum Coin{

    /**
     * Currency ONE POUND of value 100
     */
    ONE_POUND("One pound", 100),

    /**
     * Currency FIFTY PENCE of value 50
     */
    FIFTY_PENCE("Fifty pence", 50),

    /**
     * Currency TWENTY PENCE of value 20
     */
    TWENTY_PENCE("Twenty pence", 20), 

    /**
     * Currency TEN PENCE of value 10
     */
    TEN_PENCE("Ten pence", 10), 

    /**
     * Currency FIVE PENCE of value 5
     */
    FIVE_PENCE("Five pence", 5),  

    /**
     * Currency TWO PENCE of value 2
     */
    TWO_PENCE("Two pence", 2),  

    /**
     * Currency ONE PENNY  of value 1
     */
    ONE_PENNY("One penny", 1);  

    private final String nameAsString;
    private final int valueAsInt;
    
    private Coin(String nameAsString, int valueAsInt){
        this.nameAsString = nameAsString;
        this.valueAsInt = valueAsInt;
    }

     /**
     * Returns a string with the name and value of the coin
     * @return Name and value of the coin
     */
    @Override
    public String toString() {
        return nameAsString + "(" + valueAsInt + ")";
    }

    /**
     * Returns the name of the coin
     * @return Name of the coin
     */
    public String getNameAsString() {
        return nameAsString;
    }

    /**
     * Returns the value of the coin
     * @return Value of the coin
     */
    public int getValueAsInt() {
        return valueAsInt;
    }
}

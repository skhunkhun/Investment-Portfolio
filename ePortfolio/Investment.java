/**
 * The investment class is the parent of the Stock and MutualFund class. It creates a new investment object
 * that contains the symbol, name and price.
 */

package ePortfolio;

/**
 * The investment class is the parent of the Stock and MutualFund class. It creates a new investment object
 * that contains the symbol, name and price.
 */

public abstract class Investment {

    protected String symbol;
    protected String name;
    protected int quantity;
    protected double price;
    protected double bookValue;
    /**
     * Method to set values of investment object.
     * @param symbol
     * @param name
     * @param quantity
     * @param price
     */
    public Investment(String symbol, String name, int quantity, double price) throws Exception{

        if(isValid(quantity, price)){

            this.symbol = symbol;
            this.name = name;
            this.quantity = quantity;
            this.price = price;

        } else{

            throw new Exception("Price and quantity must be greater than 0");
        }

    }

    /**
     * copy contructor for Investment
     * @param originalObject
     */
    public Investment(Investment originalObject) {
        symbol = originalObject.symbol;
        name = originalObject.name;
        quantity = originalObject.quantity;
        price = originalObject.price;
        
    }

    /**
     * method to check validity of quantity and price inputs
     * @param quantity
     * @param price
     * @return
     */
    public boolean isValid(int quantity, double price){

        return quantity >= 0 && price >= 0;

    }

    /**
     * method to get the book value
     * @return
     */
    public abstract double bookValue();

    /**
     * method to set the book value
     * @param bookValue
     */
    public abstract void setBookValue(double bookValue);
    /**
     * Method to get a investment symbol. 
     * @return String. This returns the String symbol.
     */
    public abstract String symbol();
    /**
     * Method to set a symbol.
     * @param symbol
     */
    public abstract void setSymbol(String symbol);
    /**
     * Method to get a investment name.
     * @return String. This returns the String name.
     */
    public abstract String name();
    /**
     * Method to set a investment name.
     * @param name
     */
    public abstract void setName(String name);
    /**
     * Method to get a investment quantity.
     * @return int. This return the quantity of a investment.
     */
    public abstract int quantity();
    /**
     * Method to set a investment quantity.
     * @param quantity
     */
    public abstract void setQuantity(int quantity);
    /**
     * Method to set a investment price.
     * @param price
     */
    public abstract void setPrice(double price);
    /**
     * Method to get a investment price.
     * @return double. This returns the price of a investment.
     */
    public abstract double price();
    
    /**
     * Method to return the string of an Investment object
     */
    public String toString() {

        return "Symbol: " + symbol + "\n" +
                "Name: " + name + "\n" +
                "Quantity: " + quantity + " shares" + "\n" +
                "Price: $" + price + "\n";
    }

     /**
     * Method to see if an Investment object matches another.
     * @param other
     * @return boolean.
     */
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (getClass() != other.getClass()) {
            return false;
        } else {
            Investment otherInvestment = (Investment)other;
            return symbol.equals(otherInvestment.symbol)
                && name.equals(otherInvestment.name)
                && quantity == otherInvestment.quantity
                && price == otherInvestment.price;    
        }
    }
}

/**
 * The MutualFund class is used to set the values of each mutualfund object 
 * in the investments ArrayList. One mutualfund object contains the symbol, name, price,
 * bookvalue, and gain
 */

package ePortfolio;

/**
 * The MutualFund class is used to set the values of each mutualfund object 
 * in the ArrayList. One stock object contains the symbol, name, price,
 * bookvalue, and gain
 */

public class MutualFund extends Investment{

    public static final double REDFEE = 45;
    private double bookValue;

    /**
     * Method to set values of mutualfund object.
     * @param symbol
     * @param name
     * @param quantity
     * @param price
     * @param bookValue
     */
    public MutualFund(String symbol, String name, int quantity, double price, double bookValue) throws Exception{
        super(symbol,name,quantity,price);
        this.bookValue = bookValue;
    }

    /**
     * copy constructor for MutualFund
     * @param originalObject
     * @throws Exception
     */
    public MutualFund(MutualFund originalObject) throws Exception {
        this(originalObject.symbol(), originalObject.name(), originalObject.quantity(), originalObject.price(), originalObject.bookValue());

    }

    /**
     * method to set a symbol
     */
    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    /**
     * method to get a symbol
     */
    public String symbol(){

        return symbol;
    }

    /**
     * method to get a name
     */
    public String name(){
        return name;
    }

    /**
     * method to set a name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * method to get a quantity
     */
    public int quantity(){
        return quantity;
    }

    /**
     * method to set a quantity
     */
    public void setQuantity(int quantity) {
        if(quantity >= 0){

            this.quantity = quantity;
        }
        
    }

    /**
     * method to set a price
     */
    public void setPrice(double price){
        if(price >= 0){

            this.price = price;
        } 
        
    }

    /**
     * method to return a price
     */
    public double price(){
        return price;
    }
    /**
     * Method to set a mutual book value.
     * @param bookValue
     */
    public void setBookValue(double bookValue){
        this.bookValue = bookValue;
    }

     /**
     * Method to get a mutualfund book value.
     * @return double. This returns the book value.
     */
    public double bookValue(){
        return bookValue;
    }

    /**
     * Method to calculate the book value of a mutualfund.
     * @param quantity
     * @param price
     * @return double. This returns the calculated book value.
     */
    public static double calculateBookValue(int quantity, double price){
        return quantity * price; 
    }

    /**
     * Method to return the string contents of a mutualfund.
     */
    public String toString() {

        return   super.toString() +
                "Book Value $" + bookValue + "\n";
    }

     /**
     * Method to see if a mutualfund object matches another.
     * @param other
     * @return boolean.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (getClass() != other.getClass()) {
            return false;
        } else {
            MutualFund otherInvestment = (MutualFund) other;
            return super.equals(otherInvestment)
                    && bookValue == otherInvestment.bookValue;  
        }
    }
}

/**
 * The Stock class is used to set the values of each stock object 
 * in the investments ArrayList. One stock object contains the symbol, name, price,
 * bookvalue.
 */

package ePortfolio;

/**
 * The Stock class is used to set the values of each stock object 
 * in the ArrayList. One stock object contains the symbol, name, price,
 * bookvalue, and gain
 */

public class Stock extends Investment{

    public static final double COMMISSION = 9.99;
    private double bookValue;

    /**
     * Stock contructor to create a stock object
     * @param symbol
     * @param name
     * @param quantity
     * @param price
     * @param bookValue
     * @throws Exception
     */
    public Stock(String symbol, String name, int quantity, double price, double bookValue) throws Exception{
        
        super(symbol, name, quantity, price);
        this.bookValue = bookValue;
      
    }

    /**
     * copy contructor of Stock
     * @param originalObject
     * @throws Exception
     */
    public Stock(Stock originalObject) throws Exception {
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
     * method to return a name
     */
    public String name(){
        return name;
    }

    /**
     * method to get a name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * method tp get a quantity
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
     * method to get a price
     */
    public double price(){
        return price;
    }
    

    /**
     * Method to calculate the book value of a stock.
     * @param quantity
     * @param price
     * @return double. This returns the calculated book value.
     */
    public static double calculateBookValue(int quantity, double price){
        return quantity * price + COMMISSION; 
    }

    /**
     * Method to set the book value of a stock
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
     * Method to return the string contents of a stock.
     */
    @Override
    public String toString() {

        return  super.toString() +
                "Book Value: $" + bookValue + "\n";
    }
    /**
     * Method to see if a stock object matches another.
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
             Stock otherInvestment = (Stock) other;
             return super.equals(otherInvestment)
                     && bookValue == otherInvestment.bookValue;  
         }
     }
    
}

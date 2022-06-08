/** 
 * The Portfolio class maintains the ArrayList investments.
 * It maintains the array list by keeping track of buying, selling, updating,
 * computing the total gain for investments, and searching the list given certain
 * parameters.
*/

package ePortfolio;

import java.util.Scanner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

/** 
 * The Portfolio class maintains the ArrayList investments.
 * It maintains the array list by keeping track of buying, selling, updating,
 * computing the total gain for investments, and searching the list given certain
 * parameters.
*/
public class Portfolio{

    public static ArrayList<Investment> investments = new ArrayList<Investment>();                                   //keep track of investments
    private String stockSymbol, stockName, mutualFundSymbol, mutualFundName;
    private int stockQuantity, mutualFundQuantity;
    private double stockPrice, stockBookValue, mutualFundPrice, mutualFundBookValue, gain;
    boolean match = true;

    /**
     * Method to add a new investment to the list.
     * @param info
     */

    public void stockBuy(String symbol, String name, int quantity, double price, JTextArea message){
        ArrayList<Investment> sameInvestments = new ArrayList<Investment>(); //ArrayList to keep track of the duplicate investment
        stockSymbol = symbol;
        stockName = name;
        stockQuantity = quantity;
        stockPrice = price;
        stockBookValue = Stock.calculateBookValue(stockQuantity, stockPrice);

        try{

           Stock stock =  new Stock(stockSymbol, stockName, stockQuantity, stockPrice, stockBookValue);
           investments.add(stock); //add the investment to the list
           message.append("\nThe bookvalue for " + stockSymbol + " is " + stockBookValue + "\n");

        } catch(Exception e){

            message.setText(e.toString());
        }
 
        investments.removeAll(sameInvestments);

    }

    /**
     * method to check a duplicate investment
     * @param symbol
     * @return
     */
    public int checkDuplicate(String symbol){

        for (Investment n: investments){

            if(n.symbol().equals(symbol)){ //if investment that the user entered matches any investment in the list, then set new values for the investment.
                
                return investments.indexOf(n);
            } 
        }

        return -1;

    }

    /**
     * method to change a duplicate investments price and quantity
     * @param message
     * @param symb
     * @param quant
     * @param pri
     * @param index
     */
    public void changeQuantAndPrice(JTextArea message, JTextField symb, JTextField quant, JTextField pri, int index){

        Investment in = investments.get(index);

        try{

            Integer.parseInt(quant.getText());
            Double.parseDouble(pri.getText());

        } catch(Exception e5){

            message.setText("Enter valid price and quantity values");
            return;
        }

        try{

            if(Integer.parseInt(quant.getText()) <= 0 ||  Double.parseDouble(pri.getText()) <= 0){

                throw new Exception(" Price and quantity must be greater than 0");
            }

            } catch(Exception e6){

                message.setText(e6.toString());
                return;
            }

        int quantity = Integer.parseInt(quant.getText());
        double price = Double.parseDouble(pri.getText());

        in.setQuantity(quantity + in.quantity());
        in.setPrice(price);

        if(in instanceof Stock){

            in.setBookValue(in.bookValue() + Stock.calculateBookValue(quantity, price));

        } else if(in instanceof MutualFund){

            in.setBookValue(in.bookValue() + MutualFund.calculateBookValue(quantity, price));
        }

        message.setText(in.symbol() + " is already in your portfolio. The new price is $" + in.price() + " and the new quantity is " + in.quantity() + ".");

    }

    /**
     * Method to add a new mutualfund to the list.
     * @param mutualFunds
     * @param info
     */

    public void mutualfundBuy(String symbol, String name, int quantity, double price, JTextArea message){
        ArrayList<Investment> sameMutualFund = new ArrayList<Investment>(); //ArrayList to keep track of the duplicate mutualfunds

        mutualFundSymbol = symbol;
        mutualFundName = name;
        mutualFundQuantity = quantity;
        mutualFundPrice = price;
        mutualFundBookValue = mutualFundQuantity * mutualFundPrice;

        try{

            MutualFund mutualfund =  new MutualFund(mutualFundSymbol, mutualFundName, mutualFundQuantity, mutualFundPrice, mutualFundBookValue); //add mutualfund to list
            investments.add(mutualfund); //add the investment to the list
            message.setText("\nThe bookvalue for " + mutualFundSymbol + " is " + mutualFundBookValue + "\n");
 
         } catch(Exception e){
 
             message.setText(e.toString());
         }

        investments.removeAll(sameMutualFund);
    
    }

    /**
     * Method to sell an investment in each of the lists.
     * @param sellInfo
     */
    public void sell(String sym, String quan, String pri, JTextArea message){
        String symbol = sym;
        double price = Double.parseDouble(pri);
        int quantity = Integer.parseInt(quan);
        ArrayList<Investment> sameInvestments = new ArrayList<Investment>(); //keep track of investments to be deleted from portfolio
        match = false;

        for(Investment n : investments){{ //loop through the list of investments until the symbol the user entered matches a symbol in the investment list.
            double bookValue = n.bookValue();
            if(n.symbol().equals(symbol)){
                match = true;
                if(n.quantity() > quantity && n.quantity() > 0 ){ //if the quantity entered is less than the current investment quantity and greater than 0, then calculate the gain and set new quantity and book values
                    if(n instanceof Stock){
                        int newQuantity = n.quantity() - quantity;
                        double quantDiv = (double)newQuantity / n.quantity();
                        bookValue = bookValue * (quantDiv);
                        double payment = price * newQuantity - Stock.COMMISSION;
                        double gain = Portfolio.calculateGain(payment, bookValue);
                        setGain(gain);
                        n.setQuantity(newQuantity);
                        n.setBookValue(bookValue);
                        message.setText("Success! " + symbol + " was sold.");
                        break;
                    } else if(n instanceof MutualFund){
                        int newQuantity = n.quantity() - quantity;
                        double quantDiv = (double)newQuantity / n.quantity();
                        bookValue = bookValue * (quantDiv);
                        double payment = price * newQuantity - MutualFund.REDFEE;
                        double gain = Portfolio.calculateGain(payment, bookValue);
                        setGain(gain);
                        n.setQuantity(newQuantity);
                        n.setBookValue(bookValue);
                        message.setText("Success! " + symbol + " was sold.");
                        break;
                    }
                } else if(n.quantity() == quantity){ //if quantity entered matches the quantity of the investment, then calculate the gain and add investment to samestock list to be deleted
                    if(n instanceof Stock){
                        double payment = price * quantity - Stock.COMMISSION;
                        double gain = Portfolio.calculateGain(payment, bookValue);
                        setGain(gain);
                        sameInvestments.add(n);
                        message.setText("Success! " + symbol + " was sold.");
                        break;
                    } else if(n instanceof MutualFund){
                        double payment = price * quantity - MutualFund.REDFEE;
                        double gain = Portfolio.calculateGain(payment, bookValue);
                        setGain(gain);
                        sameInvestments.add(n);
                        message.setText("Success! " + symbol + " was sold.");
                        break;
                    }
                } else {
                    message.setText("\nNot enough quantity in your investment portfolio.\n"); //if quantity entered is greater than the quantity for the investment then print error message
                    break;
                }
            } else {
                match = false;
            }
            }
        }

        if(match == false){
            message.setText("\nNo investment with symbol of " + symbol + "\n");
        }

        investments.removeAll(sameInvestments); //remove the investment

    }

    
    /**
     * method to update the prices in the investments
     * @param message
     * @param symbol
     * @param price
     * @param index
     */
    public void updateInvestmentsPrice(JTextArea message, JTextField symbol, JTextField price, int index){
        
        try{

            Double.parseDouble(price.getText().trim());

        } catch(Exception ex){

            message.setText("Enter correct price value");
            return;
        }

        double pri = Double.parseDouble(price.getText());

        try{

            if(pri <= 0){

                throw new Exception("Price can only be greater than 0");
            }
        } catch(Exception ee){

            message.setText(ee.toString());
            return;

        }

        Investment inv = investments.get(index);
        inv.setPrice(pri);

        message.setText(inv.toString());
    }

    /**
     * method to display the investment in the proper text fields
     * @param message
     * @param symbol
     * @param name
     * @param price
     * @param index
     */
    public void displayInvestment(JTextArea message, JTextField symbol, JTextField name, JTextField price, int index){

        if(investments.size() == 0){
            message.setText("no investments in portfolio");
            return;
        }

        Investment inv = investments.get(index);
        symbol.setText(inv.symbol());
        name.setText(inv.name());
        price.setText("" + inv.price());


    }

    /**
     * method to get the size of the investments list
     * @return
     */
    public int getListSize(){

        return investments.size();
    }

    /**
     * Method to print all investments in the list.
     */
    public void printStocks(){
        System.out.println("List of investments:");

        if(investments.size() != 0){ //print all of the investments
            for( int i = 0; i < investments.size(); i++){
                System.out.println(investments.get(i));
            }
        } else{
            System.out.println("\nNo investments in portfolio\n");
        }

    }

    
    /**
     * Method to calculate the potential gain for each investment in the list.
     */
    public void potentialGain(JTextArea message, JTextField text1){

        double gain = 0;

        message.append("\nThe potential gain for each investment:\n\n");
        for(Investment n : investments){ //loop through investments

            if(n instanceof Stock){ //if the investment is an instance of the stock class, then calculate accordingly
                int tempQuantity = n.quantity();
                double price = n.price();
                double potentialGain = (tempQuantity * price - Stock.COMMISSION) - n.bookValue();
                message.append("The potential gain for the stock " + n.symbol() + " is " + potentialGain + "\n");
                gain += potentialGain;

            } else if(n instanceof MutualFund){ //if the investment in an instance of the mutualfund class, then calculate gain accordingly
                int tempQuantity = n.quantity();
                double price = n.price();
                double potentialGain = (tempQuantity * price - MutualFund.REDFEE) - n.bookValue();
                message.append("The potential gain for the mutualfund " + n.symbol() + " is " + potentialGain + "\n");
                gain += potentialGain;
            }   
        }

        text1.setText(String.valueOf(gain));

    }

    /**
     * Static method to calculate the gain for an investment.
     * @param payment
     * @param bookValue
     * @return
     */
    public static double calculateGain(double payment, double bookValue){
        return payment - bookValue;
    }

    /**
     * Method to set the gain in the portfolio.
     * @param gain
     */
    public void setGain(double gain){
        this.gain = this.gain + gain;
    }

    /**
     * Method to get the current gain in the portfolio.
     * @return
     */
    public double getGain(){
        return this.gain;
    }

    /**
     * Method to print the total gain and potential gain in portfolio.
     */
    public void printGain(JTextField text){
        
        //text.append("\nThe actual gain for each investment:");
        text.setText("\nThe total gain for all investments is " + gain + "\n");

    }

    /**
     * Method to read a file and input the file contents into the array list of investments.
     * @param file
     */
    public void readFile(String file){

        try{
            Scanner input = new Scanner(new File(file));
            String line;
            String [] lineSplit;
            String symbol = "";
            String name = "";
            int quantity = 0;
            double price = 0;
            double bookvalue = 0;

            while(input.hasNextLine()){ //loop through file until empty
                line = input.nextLine();
                lineSplit = line.split("\""); //split each line where quotations are present
                if(!line.isBlank()){ //if the line is not blank, get the values of symbol, name, quantity, price and bookvalue
                    String type = lineSplit[1];
                    for(int i = 0; i < 5; i++){
                        line = input.nextLine();
                        lineSplit = line.split("\"");
                        if(i == 0){
                            symbol = lineSplit[1];
                        } else if (i == 1){
                            name = lineSplit[1];
                        } else if (i == 2){
                            quantity = Integer.parseInt(lineSplit[1]);
                        } else if(i == 3){
                            price = Double.parseDouble(lineSplit[1]);
                        } else if(i == 4){
                            bookvalue = Double.parseDouble(lineSplit[1]);
                        }
                    }

                    if(type.equalsIgnoreCase("stock")){ //if the type of the investment is stock, then create a new stock object and add it to the array list

                        try{

                            Stock stock =  new Stock(symbol, name, quantity, price, bookvalue);
                            investments.add(stock); //add the investment to the list
                 
                         } catch(Exception e){
                 
                            System.out.println(e.toString());
                         }
                       
                    } else{
                        try{

                            MutualFund mutualfund =  new MutualFund(symbol, name, quantity, price, bookvalue); //add mutualfund to list
                            investments.add(mutualfund); //add the investment to the list
                 
                         } catch(Exception e){
                 
                            System.out.println(e.toString());
                         }
                    }

                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("\nError opening the file " + file + "\n");
        }
    }

    /**
     * Method to write the contents of the array list into a specified file.
     * @param fileWriter
     */
    public void writeFile(PrintWriter fileWriter){
        for(Investment in : investments){
            if(in instanceof Stock){
                fileWriter.println("type = \"stock\"" + "\n" +
                                    "symbol = \"" + in.symbol() + "\"" + "\n" +
                                    "name = \"" + in.name() + "\"" + "\n" + 
                                    "quantity = \"" + in.quantity() + "\"" + "\n" + 
                                    "price = \"" + in.price() + "\"" + "\n" + 
                                    "bookvalue = \"" + in.bookValue() + "\"" + "\n");
            } else if(in instanceof MutualFund){
                fileWriter.println("type = \"mutualfund\"" + "\n" +
                                    "symbol = \"" + in.symbol() + "\"" + "\n" +
                                    "name = \"" + in.name() + "\"" + "\n" + 
                                    "quantity = \"" + in.quantity() + "\"" + "\n" + 
                                    "price = \"" + in.price() + "\"" + "\n" + 
                                    "bookvalue = \"" + in.bookValue() + "\"" + "\n");

            }
        }
        fileWriter.close();
    }

    /**
     * Method to return a list of integers that represent the index that the given word appears on in the list of investments.
     * @param word
     * @return
     */
    private ArrayList<Integer> findName(String word){

        ArrayList<Integer> index = new ArrayList<Integer>(); //list of integers

        for(Investment n : investments){ //loop through the list of investments and split the investment name into multiple words
            String wordsInList = n.name();
            String [] wordIinList = wordsInList.split(" ");

            for(int i = 0; i < wordIinList.length; i++){ //store the index of that word in the Integer array list
                if(wordIinList[i].equalsIgnoreCase(word)){
                    int nIndex = investments.indexOf(n);
                    index.add(nIndex);
                }
            }

        }
       
        return index; //return the array list
    }

    /**
     * method to return a hashmap of the key (word) to the index that it appears on in the investment list.
     * @return
     */
    private HashMap<String, ArrayList<Integer>> findName(){

        HashMap<String, ArrayList<Integer>> newHashmap = new HashMap<>();

        for(Investment n : investments){
            String words = n.name();
            String [] wordSplit = words.split(" ");
            for(int i = 0; i < wordSplit.length; i++){
                ArrayList<Integer> index = findName(wordSplit[i]);
                newHashmap.putIfAbsent(wordSplit[i].toLowerCase(), index);
            }
        }
        return newHashmap;
    }

    /**
     * method to get the intersection of the keywords in the hashmap
     * @param keywords
     * @param hash
     * @return
     */
    private static ArrayList<Integer> getIntersection (String keywords, HashMap <String, ArrayList<Integer>> hash){

        ArrayList <Integer> intersection = new ArrayList <Integer>(); 
        ArrayList <ArrayList<Integer>> listofIndexs = new ArrayList <ArrayList<Integer>>();
        ArrayList <Integer> empty = new ArrayList <Integer>();

        String [] splitKeywords = keywords.split("[ ]+");

        for(String keyword : splitKeywords){ //loop throgh the given keywords

            if(hash.containsKey(keyword.toLowerCase())){ //if the keyword is in the hash table, add the index to the list of index array list
                listofIndexs.add(hash.get(keyword.toLowerCase()));
            } else{
                listofIndexs.add(empty); //if the hash does not contain any of the keywords, add the empty list
            }
        }

        intersection.addAll(listofIndexs.get(0)); //add an initial index to the to the intersection

        for(int i = 1; i < listofIndexs.size(); i++){ //loop through the list of indexs and retain all of the same indicies

            intersection.retainAll(listofIndexs.get(i));
        }

        return intersection; //return the array list
    }

    /**
     * search for keywords in the list
     * @param keywords
     * @return
     */
    public boolean keywordSearch(String keywords, JTextArea memoDisplay){

        HashMap<String, ArrayList<Integer>> hash = findName();
        ArrayList <Integer> intersection = getIntersection(keywords, hash); //create the intersection list given the specified keywords and the hash map that maps all the indicies to the keywords

        if(intersection.isEmpty()){ //if the intersection list is empty, no keywords matched, so return false
            return false;
        }

        for(int index : intersection){ //if the intersection is not empty, loop through the intersection and print out the indicies in the investments list that contain the keywords

            memoDisplay.append("\nInvestment Found:\n" + investments.get(index));
        }

        return true; //return true to indicate a successful match
    }

    /**
     * method to find a given symbol in the list of investments
     * @param symbol
     * @return
     */
    public boolean symbolSearch(String symbol, JTextArea message){

        for(Investment n : investments){ 
            if(n.symbol().equals(symbol)){

                message.append("\nInvestment Found:\n" + n.toString()); 
            }
        }

        return true;
    }

    /**
     * method to find an investment given the price or price range.
     * @param price
     * @return
     */
    public boolean priceSearch(String price, JTextArea message){

        String [] priceSplit = price.split("-"); //split the price where '-' is

        for(Investment n : investments){ //loop through the investments
            int lastIndex = price.lastIndexOf("-"); //get the last index of '-' if the user enter "xxx-"

            if(price.charAt(0) == '-'){ //if '-' is in the first index, then search for the price that is less than or equal to the given price
                String newPrice = price.substring(1);
                if(Double.parseDouble(newPrice) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                } else {

                    if(n.price() <= Double.parseDouble(newPrice)){
                        message.append("\nInvestment Found:\n" + n.toString());
                    }
                }
                

            } else if(priceSplit.length == 2){ //if the user enters a price range, then search for investments within that range

                if(Double.parseDouble(priceSplit[0]) > Double.parseDouble(priceSplit[1])){

                    message.setText("low price must be lower than high price");
                    return false;
                }

                if(Double.parseDouble(priceSplit[0]) <= 0 || Double.parseDouble(priceSplit[1]) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                }

                if(n.price() >= Double.parseDouble(priceSplit[0]) && n.price() <= Double.parseDouble(priceSplit[1])){
                    message.append("\nInvestment Found:\n" + n.toString());
                }

            } else if(price.length() <= lastIndex + 1){ //if '-' is at the last index of the price, then find the investments greater than or equal to the given price
                String newPrice = price.substring(0, lastIndex);
                
                if(Double.parseDouble(newPrice) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                }

                if(n.price() >= Double.parseDouble(newPrice)){
                    message.append("\nInvestment Found:\n" + n.toString());
                }
            } else if(n.price() == Double.parseDouble(price)){ //if the user enters an exact price, then print the investments with that exact price

                if(Double.parseDouble(price) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                }

                message.append("\nInvestment Found:\n" + n.toString());
            }

        }

        return true;
    }

    /**
     * method to find the investments given the specified symbol and price
     * @param symbol
     * @param price
     * @return
     */
    public boolean symbolAndPrice(String symbol, String price, JTextArea message){

        //same logic as above, but seeing if both the symbol and price match to print the investments

        String [] priceSplit = price.split("-");

        for(Investment n : investments){

            int lastIndex = price.lastIndexOf("-");

            if(n.symbol().equals(symbol) && price.charAt(0) == '-'){
                String newPrice = price.substring(1);

                if(Double.parseDouble(newPrice) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                }

                if(n.price() <= Double.parseDouble(newPrice)){
                    message.append("\nInvestment Found:\n" + n.toString());
                }

            } else if(n.symbol().equals(symbol) && priceSplit.length == 2){

                if(Double.parseDouble(priceSplit[0]) > Double.parseDouble(priceSplit[1])){

                    message.setText("low price must be lower than high price");
                    return false;
                }

                if(Double.parseDouble(priceSplit[0]) <= 0 || Double.parseDouble(priceSplit[1]) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                }

                if(n.price() >= Double.parseDouble(priceSplit[0]) && n.price() <= Double.parseDouble(priceSplit[1])){
                    message.append("\nInvestment Found:\n" + n.toString());
                }

            } else if(n.symbol().equals(symbol) && price.length() <= lastIndex + 1){
                String newPrice = price.substring(0, lastIndex);

                if(Double.parseDouble(newPrice) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                }

                if(n.price() >= Double.parseDouble(newPrice)){
                    message.append("\nInvestment Found:\n" + n.toString());
                }

            } else if(n.symbol().equals(symbol) && n.price() == Double.parseDouble(price)){

                if(Double.parseDouble(price) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                }

                message.append("\nInvestment Found:\n" + n.toString());
            }
        }

        return true;
    }

    /**
     * method to find all the investments given a symbol and keywords.
     * @param symbol
     * @param keywords
     * @return
     */
    public boolean symbolAndKeywords (String symbol, String keywords, JTextArea message){

        //same logic as the 'keywordSearch' and the 'symbolSearch'

        HashMap<String, ArrayList<Integer>> hash = findName();
        ArrayList <Integer> intersection = getIntersection(keywords, hash);

        if(intersection.isEmpty()){
            return false;
        }

        for(Investment n : investments){

            if(n.symbol().equals(symbol)){

                int indexOf = investments.indexOf(n); //get the index of the investment that the symbol that matches
                if(intersection.contains(indexOf)){ //if the intersection list contains that index, then print the investment
                    message.append("\nInvestment Found:\n" + n.toString());
                }
                
            } 

        }

        return true;
    }

    /**
     * method to find all of the investments given price range and keywords
     * @param keywords
     * @param price
     * @return
     */
    public boolean keywordsAndPrice(String keywords, String price, JTextArea message){

        //same logic as the 'keywordSearch' method and the 'priceSearch' method

        HashMap<String, ArrayList<Integer>> hash = findName();
        ArrayList <Integer> intersection = getIntersection(keywords, hash);

        if(intersection.isEmpty()){
            return false;
        }

        String [] priceSplit = price.split("-");

        for(Investment n : investments){

            int lastIndex = price.lastIndexOf("-");

            if(price.charAt(0) == '-'){
                String newPrice = price.substring(1);

                if(Double.parseDouble(newPrice) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                }

                if(n.price() <= Double.parseDouble(newPrice)){
                    int indexOf = investments.indexOf(n);
                    if(intersection.contains(indexOf)){
                        message.append("\nInvestment Found:\n" + n.toString());
                    }
                }

            } else if(priceSplit.length == 2){
                
                if(Double.parseDouble(priceSplit[0]) > Double.parseDouble(priceSplit[1])){

                    message.setText("low price must be lower than high price");
                    return false;
                }

                if(Double.parseDouble(priceSplit[0]) <= 0 || Double.parseDouble(priceSplit[1]) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                }

                if(n.price() >= Double.parseDouble(priceSplit[0]) && n.price() <= Double.parseDouble(priceSplit[1])){
                    int indexOf = investments.indexOf(n);
                    if(intersection.contains(indexOf)){
                        message.append("\nInvestment Found:\n" + n.toString());
                    }
                }

            } else if(price.length() <= lastIndex + 1){
                String newPrice = price.substring(0, lastIndex);

                if(Double.parseDouble(newPrice) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                }

                if(n.price() >= Double.parseDouble(newPrice)){
                    int indexOf = investments.indexOf(n);
                    if(intersection.contains(indexOf)){
                        message.append("\nInvestment Found:\n" + n.toString());
                    }
                }

            } else if(n.price() == Double.parseDouble(price)){

                if(Double.parseDouble(price) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                }

                int indexOf = investments.indexOf(n);
                    if(intersection.contains(indexOf)){
                        message.append("\nInvestment Found:\n" + n.toString());
                    }
            }
 
        } 

        return true;
    }

    /**
     * method to find an investment given the symbol, a price, and keywords
     * @param symbol
     * @param keywords
     * @param price
     * @return
     */
    public boolean searchAll(String symbol, String keywords, String price, JTextArea message){

        //same logic as 'keywordSearch', 'priceSearch' and 'symbolSearch'

        HashMap<String, ArrayList<Integer>> hash = findName();
        ArrayList <Integer> intersection = getIntersection(keywords, hash);

        if(intersection.isEmpty()){
            return false;
        }

        String [] priceSplit = price.split("-");

        for(Investment n : investments){

            int lastIndex = price.lastIndexOf("-");

            if(n.symbol().equals(symbol) && price.charAt(0) == '-'){
                String newPrice = price.substring(1);

                if(Double.parseDouble(newPrice) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                }

                if(n.price() <= Double.parseDouble(newPrice)){
                    int indexOf = investments.indexOf(n);
                    if(intersection.contains(indexOf)){
                        message.append("\nInvestment Found:\n" + n.toString());
                    }
                }

            } else if(n.symbol().equals(symbol) && priceSplit.length == 2){

                if(Double.parseDouble(priceSplit[0]) > Double.parseDouble(priceSplit[1])){

                    message.setText("low price must be lower than high price");
                    return false;
                }

                if(Double.parseDouble(priceSplit[0]) <= 0 || Double.parseDouble(priceSplit[1]) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                }

                if(n.price() >= Double.parseDouble(priceSplit[0]) && n.price() <= Double.parseDouble(priceSplit[1])){
                    int indexOf = investments.indexOf(n);
                    if(intersection.contains(indexOf)){
                        message.append("\nInvestment Found:\n" + n.toString());
                    }
                }

            } else if(n.symbol().equals(symbol) && price.length() <= lastIndex + 1){
                String newPrice = price.substring(0, lastIndex);

                if(Double.parseDouble(newPrice) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                }

                if(n.price() >= Double.parseDouble(newPrice)){
                    int indexOf = investments.indexOf(n);
                    if(intersection.contains(indexOf)){
                        message.append("\nInvestment Found:\n" + n.toString());
                    }
                }

            } else if(n.symbol().equals(symbol) && n.price() == Double.parseDouble(price)){

                if(Double.parseDouble(price) <= 0){

                    message.setText("Cannot enter values that are negative or 0 for price");
                    return false;
                }

                int indexOf = investments.indexOf(n);
                if(intersection.contains(indexOf)){
                    message.append("\nInvestment Found:\n" + n.toString());
                }
            }
 
        } 

        return true;
    }

    /**
     * method to print the list of investments, given the user leaves all of the fields blank
     */
    public void emptySearch(JTextArea message){

        for(Investment n : investments){
            message.append("Found:\n" + n.toString() + "\n");
        }

    }

}

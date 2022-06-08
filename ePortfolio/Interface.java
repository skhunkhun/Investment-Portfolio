/** 
 * The Interface class maintains the Gui for the portfolio.
 * It has an opening menu, the buy interface, the sell interface, the update investements interface,
 * the gain interface, the search interface and a quit option.
*/

package ePortfolio;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

/** 
 * The Interface class maintains the Gui for the portfolio.
 * It has an opening menu, the buy interface, the sell interface, the update investements interface,
 * the gain interface, the search interface and a quit option.
*/
public class Interface extends JFrame{
    
    public static final int WIDTH = 1000; //set width of frame
    public static final int HEIGHT = 800; //set height of frame

    public static final int LINES = 10;
    public static final int CHAR_PER_LINE = 50;

    public int displayIdx;
    public int prevDisplayIdx;

    Portfolio portfolio = new Portfolio();
    private JPanel openLayout;
    private JPanel buyPanel;
    private JPanel sellPanel;
    private JPanel updatePanel;
    private JPanel gainPanel;
    private JPanel searchPanel;

    JTextField updateSymbol;
    JTextField updateName;
    JTextField updatePrice;
    JTextArea updateDisplay;

    private class StartListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            openLayout.setVisible(true);
            buyPanel.setVisible(false);
            sellPanel.setVisible(false);
            updatePanel.setVisible(false);
            gainPanel.setVisible(false);
            searchPanel.setVisible(false);

        }
    } //End of BuyListener inner class

    private class BuyListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            openLayout.setVisible(false);
            buyPanel.setVisible(true);
            sellPanel.setVisible(false);
            updatePanel.setVisible(false);
            gainPanel.setVisible(false);
            searchPanel.setVisible(false);

        }
    } //End of BuyListener inner class

    private class SellListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            openLayout.setVisible(false);
            buyPanel.setVisible(false);
            sellPanel.setVisible(true);
            updatePanel.setVisible(false);
            gainPanel.setVisible(false);
            searchPanel.setVisible(false);
        }
    } //End of SellListener inner class

    private class UpdateListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            displayIdx = 0;
            prevDisplayIdx = 0;
            updateSymbol.setText("");
            updatePrice.setText("");
            updateName.setText("");
            updateDisplay.setText("");
            openLayout.setVisible(false);
            buyPanel.setVisible(false);
            sellPanel.setVisible(false);
            updatePanel.setVisible(true);
            gainPanel.setVisible(false);
            searchPanel.setVisible(false);
        }
    } //End of UpdateListener inner class

    private class GainListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            openLayout.setVisible(false);
            buyPanel.setVisible(false);
            sellPanel.setVisible(false);
            updatePanel.setVisible(false);
            gainPanel.setVisible(true);
            searchPanel.setVisible(false);
        }
    } //End of GainListener inner class

    private class SearchListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            openLayout.setVisible(false);
            buyPanel.setVisible(false);
            sellPanel.setVisible(false);
            updatePanel.setVisible(false);
            gainPanel.setVisible(false);
            searchPanel.setVisible(true);
        }
    } //End of SearchListener inner class

    private class QuitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    } //End of QuitListener inner class

    public class nextListener implements ActionListener { //listener for next button in update investments
    
        public void actionPerformed(ActionEvent e) {

            if(prevDisplayIdx == 0){
                
                portfolio.displayInvestment(updateDisplay, updateSymbol, updateName, updatePrice, displayIdx);
                prevDisplayIdx++;

            } else{

                if(displayIdx + 1 < portfolio.getListSize()){

                    displayIdx++;

                    portfolio.displayInvestment(updateDisplay, updateSymbol, updateName, updatePrice, displayIdx);
    
                } 


            }
        

        }
    }

    public class prevListener implements ActionListener { //listener for previous button in update investments
    
        public void actionPerformed(ActionEvent e) {

            if(displayIdx - 1 >= 0){

                displayIdx--;

            } 

            portfolio.displayInvestment(updateDisplay, updateSymbol, updateName, updatePrice, displayIdx);

        }

    }

    public class saveListener implements ActionListener { //save listener for button in update investments
    
        public void actionPerformed(ActionEvent e) {

           portfolio.updateInvestmentsPrice(updateDisplay, updateSymbol, updatePrice, displayIdx);

        }

    }


/** 
 * Main for the gui interface
 * 
*/
    public static void main(String[] args) {

        Interface start = new Interface( );
        start.setVisible(true);
    }

    /**
     * the interface for the investments gui
     */
    public Interface( ) {

        setTitle("ePortfolio"); 
        setFont(new Font("Serif", Font.BOLD, 30));
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setResizable(false);

        //START MENU INTERFACE
        openLayout = new JPanel();
        openInterface(openLayout);

        //BUY INTERFACE
        buyPanel = new JPanel();
        buyInterFace(buyPanel);

        //SELL INTERFACE
        sellPanel = new JPanel( );
        sellInterface(sellPanel);

        //UPDATE INTERFACE
        updatePanel = new JPanel( );
        updateInterface(updatePanel);
        

        //GAIN INTERFACE
        gainPanel = new JPanel( );
        gainInterface(gainPanel);

        //SEARCH INTERFACE
        searchPanel = new JPanel( );
        searchInterface(searchPanel);

        //COMMANDS MENU
        JMenuBar bar =  menuCommands();
        setJMenuBar(bar);
    }

    /**
     * method for the opening interface
     * @param openLayout
     */
    private void openInterface(JPanel openLayout){ 

        openLayout.setLayout(new GridLayout(2,1)); //make a new grid layout
        JLabel openLabel1 = new JLabel("Welcome to ePortfolio.");
        openLabel1.setFont(new Font("Serif", Font.PLAIN, 30));
        openLayout.add(openLabel1);

        String text = "Choose a command from the 'Commands' menu to buy or sell an investment, update prices for all investments, get gain for the portfolio, search for relevant investments, or quit the program.";
        JTextArea openLabel2 = new JTextArea(4,40);
        openLabel2.setFont(new Font("Serif", Font.PLAIN, 30));
        openLabel2.setText(text);
        openLabel2.setWrapStyleWord(true);
        openLabel2.setLineWrap(true);
        openLabel2.setOpaque(false);
        openLabel2.setEditable(false);
        openLabel2.setFocusable(false);
        openLabel2.setBackground(UIManager.getColor("Label.background"));
        openLabel2.setBorder(UIManager.getBorder("Label.border"));

        openLayout.add(openLabel2);
        add(openLayout);

    }

    /**
     * method for the buy interface
     * @param buyPanel
     */
    private void buyInterFace(JPanel buyPanel){

        //set panel to false initially
        buyPanel.setVisible(false);
        buyPanel.setLayout(new BorderLayout());

        //North Panel ** make the title
        JLabel headerLabel = new JLabel("Buying an Investment", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        buyPanel.add(headerLabel, BorderLayout.NORTH);
        add(buyPanel);

        //West Panel ** make new labels 
        JPanel panelWest = new JPanel();
        panelWest.setLayout(new GridLayout(5,1));
        JLabel label2 = new JLabel("Type");
        label2.setFont(new Font("Serif", Font.PLAIN, 20));
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        panelWest.add(label2);

        JLabel label3 = new JLabel("Symbol");
        label3.setFont(new Font("Serif", Font.PLAIN, 20));
        label3.setHorizontalAlignment(SwingConstants.CENTER);
        panelWest.add(label3);

        JLabel label4 = new JLabel("Name");
        label4.setFont(new Font("Serif", Font.PLAIN, 20));
        label4.setHorizontalAlignment(SwingConstants.CENTER);
        panelWest.add(label4);

        JLabel label5 = new JLabel("Quantity");
        label5.setFont(new Font("Serif", Font.PLAIN, 20));
        label5.setHorizontalAlignment(SwingConstants.CENTER);
        panelWest.add(label5);

        JLabel label6 = new JLabel("Price");
        label6.setFont(new Font("Serif", Font.PLAIN, 20));
        label6.setHorizontalAlignment(SwingConstants.CENTER);
        panelWest.add(label6);
        panelWest.setPreferredSize(new Dimension(200,100));

        buyPanel.add(panelWest, BorderLayout.WEST);

        //South Panel ** add the message box
        JPanel panelSouth = new JPanel();
        panelSouth.setPreferredSize(new Dimension(200,300));
        JLabel southLabel = new JLabel("Messages");
        southLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        panelSouth.add(southLabel);

        JTextArea memoDisplay = new JTextArea(LINES, CHAR_PER_LINE);
        memoDisplay.setBackground(Color.WHITE);
        memoDisplay.setFont(new Font("Serif", Font.PLAIN, 20));

        JScrollPane scrolledText = new JScrollPane(memoDisplay);
        scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelSouth.add(scrolledText);

        buyPanel.add(panelSouth, BorderLayout.SOUTH);

        //Center Panel ** add the textfield boxes, for user input and displaying important information
        JPanel panelCenter = new JPanel();
        panelCenter.setPreferredSize(new Dimension(200,200));
        panelCenter.setLayout(new GridLayout(5,1));
        String[] investments = { "Stock", "MutualFund" };
        JComboBox<String> invest = new JComboBox<>(investments);
        invest.setSelectedIndex(0);
        invest.setFont(new Font("Serif", Font.PLAIN, 25));

        JTextField symbol = new JTextField();
        symbol.setFont(new Font("Serif", Font.PLAIN, 20));
        JTextField name = new JTextField();
        name.setFont(new Font("Serif", Font.PLAIN, 20));
        JTextField quantity = new JTextField();
        quantity.setFont(new Font("Serif", Font.PLAIN, 20));
        JTextField price = new JTextField();
        price.setFont(new Font("Serif", Font.PLAIN, 20));

        panelCenter.add(invest);
        panelCenter.add(symbol);
        panelCenter.add(name);
        panelCenter.add(quantity);
        panelCenter.add(price);

        buyPanel.add(panelCenter, BorderLayout.CENTER);

        //East Panel ** add the buttons the user can click
        JPanel panelEast = new JPanel();
        panelEast.setLayout(new GridLayout(2,1,0,200));
        panelEast.setPreferredSize(new Dimension(150,20));

        JButton reset = new JButton();
        reset.setFocusable(false);
        reset.setText("Reset");
        reset.setFont(new Font("Serif", Font.BOLD, 25));
        reset.addActionListener(new ActionListener() { //set all of the text fields to blank

            public void actionPerformed(ActionEvent e) {
                invest.setSelectedIndex(0);
                symbol.setText("");
                name.setText("");
                quantity.setText("");
                price.setText("");
                memoDisplay.setText("");
            }
        });

        panelEast.add(reset);

        JButton buy = new JButton();
        buy.setText("Buy");
        buy.setFocusable(false);
        buy.setFont(new Font("Serif", Font.BOLD, 25));
        buy.addActionListener(new ActionListener() { //when button is pressed, add new investment to list or set new price and quantity to existing investments

            public void actionPerformed(ActionEvent e) {
                
                try{
                    if(symbol.getText().isEmpty() || name.getText().isEmpty() || quantity.getText().isEmpty() || price.getText().isEmpty()){

                        throw new Exception("Please enter all fields");
                    }

                } catch(Exception e2){

                    memoDisplay.setText(e2.toString());
                    return;

                }

                try{

                    Integer.parseInt(quantity.getText().trim());
                    Double.parseDouble(price.getText().trim());

                } catch(Exception ex){

                    memoDisplay.setText("Enter correct number values");
                    return;
                }

                String newSymbol = symbol.getText().trim();



                String newName = name.getText().trim();
                int newQuantity = Integer.parseInt(quantity.getText().trim());
                double newPrice = Double.parseDouble(price.getText().trim());

                if(invest.getSelectedIndex() == 0){

                    if(portfolio.checkDuplicate(newSymbol) != -1){

                        int index = portfolio.checkDuplicate(newSymbol);
                        portfolio.changeQuantAndPrice(memoDisplay, symbol, quantity, price, index);

                    } else{

                        portfolio.stockBuy(newSymbol, newName, newQuantity, newPrice, memoDisplay);
                    }

                } else{

                    if(portfolio.checkDuplicate(newSymbol) != -1){

                        int index = portfolio.checkDuplicate(newSymbol);
                        portfolio.changeQuantAndPrice(memoDisplay, symbol, quantity, price, index);

                    } else{

                        portfolio.mutualfundBuy(newSymbol, newName, newQuantity, newPrice, memoDisplay);

                    }

                }
                
            }
        });

        panelEast.add(buy);
        buyPanel.add(panelEast, BorderLayout.EAST);

        // add buy panel to frame
        add(buyPanel);

    }


    /**
     * method for the sell interface. Same layout as buyPanel
     * @param sellPanel
     */
    private void sellInterface(JPanel sellPanel){

        //set visibility to false
        sellPanel.setVisible(false);
        sellPanel.setLayout(new BorderLayout());

        //North Panel ** 
        JLabel headerLabel = new JLabel("Selling an investment", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        sellPanel.add(headerLabel, BorderLayout.NORTH);
        add(sellPanel);

        //West Panel **
        JPanel panelWest = new JPanel();
        panelWest.setLayout(new GridLayout(3,1));
        JLabel label2 = new JLabel("Symbol");
        label2.setFont(new Font("Serif", Font.PLAIN, 20));
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        panelWest.add(label2);

        JLabel label3 = new JLabel("Quantity");
        label3.setFont(new Font("Serif", Font.PLAIN, 20));
        label3.setHorizontalAlignment(SwingConstants.CENTER);
        panelWest.add(label3);

        JLabel label4 = new JLabel("Price");
        label4.setFont(new Font("Serif", Font.PLAIN, 20));
        label4.setHorizontalAlignment(SwingConstants.CENTER);
        panelWest.add(label4);

        panelWest.setPreferredSize(new Dimension(200,100));

        sellPanel.add(panelWest, BorderLayout.WEST);

        //South Panel **
        JPanel panelSouth = new JPanel();
        panelSouth.setPreferredSize(new Dimension(200,300));
        JLabel southLabel = new JLabel("Messages");
        southLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        panelSouth.add(southLabel);

        JTextArea memoDisplay = new JTextArea(LINES, CHAR_PER_LINE);
        memoDisplay.setBackground(Color.WHITE);
        memoDisplay.setFont(new Font("Serif", Font.PLAIN, 20));

        JScrollPane scrolledText = new JScrollPane(memoDisplay);
        scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelSouth.add(scrolledText);

        sellPanel.add(panelSouth, BorderLayout.SOUTH);

        //Center Panel **
        JPanel panelCenter = new JPanel();
        panelCenter.setPreferredSize(new Dimension(200,200));
        panelCenter.setLayout(new GridLayout(3,1,0, 20));

        JTextField symbol = new JTextField();
        symbol.setFont(new Font("Serif", Font.PLAIN, 20));
        JTextField name = new JTextField();
        name.setFont(new Font("Serif", Font.PLAIN, 20));
        JTextField lowPrice = new JTextField();
        lowPrice.setFont(new Font("Serif", Font.PLAIN, 20));

        panelCenter.add(symbol);
        panelCenter.add(name);
        panelCenter.add(lowPrice);

        sellPanel.add(panelCenter, BorderLayout.CENTER);

        //East Panel **
        JPanel panelEast = new JPanel();
        panelEast.setLayout(new GridLayout(2,1,0,200));
        panelEast.setPreferredSize(new Dimension(150,20));

        JButton reset = new JButton();
        reset.setFocusable(false);
        reset.setText("Reset");
        reset.setFont(new Font("Serif", Font.BOLD, 25));
        reset.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                symbol.setText("");
                name.setText("");
                lowPrice.setText("");
                memoDisplay.setText("");
            }
        });

        panelEast.add(reset);

        JButton buy = new JButton();
        buy.setText("Sell");
        buy.setFocusable(false);
        buy.setFont(new Font("Serif", Font.BOLD, 25));
        buy.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try{
                    if(symbol.getText().isEmpty() || name.getText().isEmpty() || lowPrice.getText().isEmpty()){

                        throw new Exception("Please enter all fields");

                    }

                } catch(Exception e2){

                    memoDisplay.setText(e2.toString());
                    return;

                }

                try{
                    Integer.parseInt(name.getText());
                    Double.parseDouble(lowPrice.getText().trim());

                } catch(Exception ex){

                    memoDisplay.setText("Please enter correct number values");
                    return;
                }

                try{

                    if(Integer.parseInt(name.getText()) <= 0 || Double.parseDouble(lowPrice.getText().trim()) <= 0){

                        throw new Exception("Please enter integer and price values that are greater than 0");
                    }

                } catch(Exception e3){

                    memoDisplay.setText(e3.toString());
                    return;
                }

                portfolio.sell(symbol.getText(), name.getText(), lowPrice.getText(), memoDisplay);
                
            }
        });

        panelEast.add(buy);
        sellPanel.add(panelEast, BorderLayout.EAST);

        // add buy panel to frame
        add(sellPanel);

    }

    /**
     * method for the update interface. Same layout as buyPanel
     * @param updatePanel
     */
    private void updateInterface(JPanel updatePanel){

        updatePanel.setVisible(false);
        updatePanel.setLayout(new BorderLayout());

        //North Panel **
        JLabel headerLabel = new JLabel("Updating investments", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        headerLabel.setPreferredSize(new Dimension(100, 100));
        updatePanel.add(headerLabel, BorderLayout.NORTH);
        add(updatePanel);

        //West Panel **
        JPanel panelWest = new JPanel();
        panelWest.setLayout(new GridLayout(3,1));
        JLabel label2 = new JLabel("Symbol");
        label2.setFont(new Font("Serif", Font.PLAIN, 20));
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        panelWest.add(label2);

        JLabel label3 = new JLabel("Name");
        label3.setFont(new Font("Serif", Font.PLAIN, 20));
        label3.setHorizontalAlignment(SwingConstants.CENTER);
        panelWest.add(label3);

        JLabel label4 = new JLabel("Price");
        label4.setFont(new Font("Serif", Font.PLAIN, 20));
        label4.setHorizontalAlignment(SwingConstants.CENTER);
        panelWest.add(label4);

        panelWest.setPreferredSize(new Dimension(200,100));

        updatePanel.add(panelWest, BorderLayout.WEST);

        //South Panel **
        JPanel panelSouth = new JPanel();
        panelSouth.setPreferredSize(new Dimension(200,400));
        JLabel southLabel = new JLabel("Messages");
        southLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        panelSouth.add(southLabel);

        updateDisplay = new JTextArea(14, CHAR_PER_LINE);
        updateDisplay.setBackground(Color.WHITE);
        updateDisplay.setFont(new Font("Serif", Font.PLAIN, 20));

        JScrollPane scrolledText = new JScrollPane(updateDisplay);
        scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelSouth.add(scrolledText);

        updatePanel.add(panelSouth, BorderLayout.SOUTH);

        //Center Panel **
        JPanel panelCenter = new JPanel();
        panelCenter.setPreferredSize(new Dimension(200,200));
        panelCenter.setLayout(new GridLayout(3,1,0, 20));

        updateSymbol = new JTextField();
        updateSymbol.setFont(new Font("Serif", Font.PLAIN, 20));
        updateSymbol.setEditable(false);
        updateSymbol.setBackground(Color.WHITE);
        updateName = new JTextField();
        updateName.setFont(new Font("Serif", Font.PLAIN, 20));
        updateName.setEditable(false);
        updateName.setBackground(Color.white);
        updatePrice = new JTextField();
        updatePrice.setFont(new Font("Serif", Font.PLAIN, 20));

        panelCenter.add(updateSymbol);
        panelCenter.add(updateName);
        panelCenter.add(updatePrice);

        updatePanel.add(panelCenter, BorderLayout.CENTER);

        //East Panel **
        JPanel panelEast = new JPanel();
        panelEast.setLayout(new GridLayout(3,1,0,30));
        panelEast.setPreferredSize(new Dimension(150,20));

        JButton prev = new JButton();
        prev.setFocusable(false);
        prev.setText("Prev");
        prev.setFont(new Font("Serif", Font.BOLD, 25));

        prev.addActionListener(new prevListener());

        panelEast.add(prev);

        JButton next = new JButton();
        next.setFocusable(false);
        next.setText("Next");
        next.setFont(new Font("Serif", Font.BOLD, 25));

        next.addActionListener(new nextListener());

        panelEast.add(next);

        JButton buy = new JButton();
        buy.setText("Save");
        buy.setFocusable(false);
        buy.setFont(new Font("Serif", Font.BOLD, 25));

        buy.addActionListener(new saveListener());

        panelEast.add(buy);
        updatePanel.add(panelEast, BorderLayout.EAST);

        // add buy panel to frame
        add(updatePanel);



    }

    /**
     * method for gain interface. Same layout as buyPanel
     * @param gainPanel
     */
    private void gainInterface(JPanel gainPanel){

        gainPanel.setVisible(false);
        gainPanel.setLayout(new BorderLayout());

        //North Panel **
        JLabel headerLabel = new JLabel("Getting total gain", SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension (50, 50));
        headerLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        gainPanel.add(headerLabel, BorderLayout.NORTH);
        add(gainPanel);

        //West Panel **
        JPanel panelWest = new JPanel();
        panelWest.setLayout(new GridLayout(3,1, 0,10));
        JLabel label2 = new JLabel("Total gain");
        label2.setFont(new Font("Serif", Font.PLAIN, 25));
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        panelWest.add(label2);

        panelWest.setPreferredSize(new Dimension(200,100));

        gainPanel.add(panelWest, BorderLayout.WEST);

        //South Panel **
        JPanel panelSouth = new JPanel();
        panelSouth.setPreferredSize(new Dimension(50,500));
        JLabel southLabel = new JLabel("Individual gains");
        southLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        panelSouth.add(southLabel);

        JTextArea memoDisplay = new JTextArea(17, CHAR_PER_LINE);
        memoDisplay.setBackground(Color.WHITE);
        memoDisplay.setFont(new Font("Serif", Font.PLAIN, 20));

        JScrollPane scrolledText = new JScrollPane(memoDisplay);
        scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelSouth.add(scrolledText);

        gainPanel.add(panelSouth, BorderLayout.SOUTH);

        //Center Panel **
        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new GridLayout(3,1,0, 20));
        JTextField symbol = new JTextField();
        symbol.setBackground(Color.WHITE);
        symbol.setFont(new Font("Serif", Font.PLAIN, 20));
        symbol.setEditable(false);

        panelCenter.add(symbol);

        gainPanel.add(panelCenter, BorderLayout.CENTER);

        //East Panel**
        JPanel panelEast = new JPanel();
        panelEast.setLayout(new GridLayout(2,1,0,50));
        panelEast.setPreferredSize(new Dimension(200,0));

        JButton reset = new JButton();
        reset.setFocusable(false);
        reset.setText("Reset");
        reset.setFont(new Font("Serif", Font.BOLD, 25));
        reset.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                symbol.setText("");
                memoDisplay.setText("");
            }
        });

        panelEast.add(reset);

        JButton buy = new JButton();
        buy.setText("Get gain");
        buy.setFocusable(false);
        buy.setFont(new Font("Serif", Font.BOLD, 25));
        buy.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                portfolio.potentialGain(memoDisplay, symbol);
                
            }
        });

        panelEast.add(buy);

        gainPanel.add(panelEast, BorderLayout.EAST);

        // add buy panel to frame
        add(gainPanel);


    }

    /**
     * method for search panel. Same layout as buyPanel
     * @param searchPanel
     */
    private void searchInterface(JPanel searchPanel){

        searchPanel.setVisible(false);
        searchPanel.setLayout(new BorderLayout());

        //North Panel **
        JLabel headerLabel = new JLabel("Searching investments", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        searchPanel.add(headerLabel, BorderLayout.NORTH);
        add(searchPanel);

        //West Panel **
        JPanel panelWest = new JPanel();
        panelWest.setLayout(new GridLayout(3,1));
        JLabel label2 = new JLabel("Symbol");
        label2.setFont(new Font("Serif", Font.PLAIN, 20));
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        panelWest.add(label2);

        JLabel label3 = new JLabel("Keywords");
        label3.setFont(new Font("Serif", Font.PLAIN, 20));
        label3.setHorizontalAlignment(SwingConstants.CENTER);
        panelWest.add(label3);

        JLabel label4 = new JLabel("Price");
        label4.setFont(new Font("Serif", Font.PLAIN, 20));
        label4.setHorizontalAlignment(SwingConstants.CENTER);
        panelWest.add(label4);

        panelWest.setPreferredSize(new Dimension(200,100));

        searchPanel.add(panelWest, BorderLayout.WEST);

        //South Panel **
        JPanel panelSouth = new JPanel();
        panelSouth.setPreferredSize(new Dimension(200,300));
        JLabel southLabel = new JLabel("Search results");
        southLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        panelSouth.add(southLabel);

        JTextArea memoDisplay = new JTextArea(LINES, CHAR_PER_LINE);
        memoDisplay.setBackground(Color.WHITE);
        memoDisplay.setFont(new Font("Serif", Font.PLAIN, 20));

        JScrollPane scrolledText = new JScrollPane(memoDisplay);
        scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelSouth.add(scrolledText);

        searchPanel.add(panelSouth, BorderLayout.SOUTH);

        //Center Panel **
        JPanel panelCenter = new JPanel();
        panelCenter.setPreferredSize(new Dimension(200,200));
        panelCenter.setLayout(new GridLayout(3,1));

        JTextField symbol = new JTextField();
        symbol.setFont(new Font("Serif", Font.PLAIN, 20));
        JTextField name = new JTextField();
        name.setFont(new Font("Serif", Font.PLAIN, 20));
        JTextField lowPrice = new JTextField();
        lowPrice.setFont(new Font("Serif", Font.PLAIN, 20));

        panelCenter.add(symbol);
        panelCenter.add(name);
        panelCenter.add(lowPrice);

        searchPanel.add(panelCenter, BorderLayout.CENTER);

        //East Panel **
        JPanel panelEast = new JPanel();
        panelEast.setLayout(new GridLayout(2,1,0,200));
        panelEast.setPreferredSize(new Dimension(150,20));

        JButton reset = new JButton();
        reset.setFocusable(false);
        reset.setText("Reset");
        reset.setFont(new Font("Serif", Font.BOLD, 25));
        reset.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                symbol.setText("");
                name.setText("");
                lowPrice.setText("");
                //highPrice.setText("");
                memoDisplay.setText("");
            }
        });

        panelEast.add(reset);

        JButton buy = new JButton();
        buy.setText("Search");
        buy.setFocusable(false);
        buy.setFont(new Font("Serif", Font.BOLD, 25));
        buy.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                
                if(symbol.getText().isBlank() && name.getText().isBlank() && lowPrice.getText().isBlank()){

                    portfolio.emptySearch(memoDisplay);           

                } else if(!symbol.getText().isBlank() && name.getText().isBlank() && lowPrice.getText().isBlank()){

                    portfolio.symbolSearch(symbol.getText(), memoDisplay);

                } else if(symbol.getText().isBlank() && !name.getText().isBlank() && lowPrice.getText().isBlank()){

                    portfolio.keywordSearch(name.getText(), memoDisplay);

                } else if(!symbol.getText().isBlank() && !name.getText().isBlank() && lowPrice.getText().isBlank()){

                    portfolio.symbolAndKeywords(symbol.getText(), name.getText(), memoDisplay);

                } else if(symbol.getText().isBlank() && name.getText().isBlank() && !lowPrice.getText().isBlank()){

                    portfolio.priceSearch(lowPrice.getText(), memoDisplay);

                } else if(!symbol.getText().isBlank() && name.getText().isBlank() && !lowPrice.getText().isBlank()){

                    portfolio.symbolAndPrice(symbol.getText(), lowPrice.getText(), memoDisplay);

                } else if(symbol.getText().isBlank() && !name.getText().isBlank() && !lowPrice.getText().isBlank()){

                    portfolio.keywordsAndPrice(name.getText(), lowPrice.getText(), memoDisplay);

                } else if(!symbol.getText().isBlank() && !name.getText().isBlank() && !lowPrice.getText().isBlank()){

                    portfolio.searchAll(symbol.getText(), name.getText(), lowPrice.getText(), memoDisplay);
                }
            }
        });

        panelEast.add(buy);
        searchPanel.add(panelEast, BorderLayout.EAST);

        // add buy panel to frame
        add(searchPanel);


    }

    /**
     * method for the menu commands.
     * @return
     */
    private JMenuBar menuCommands(){

        JMenu commandsMenu = new JMenu("Commands");
        commandsMenu.setFont(new Font("Serif", Font.BOLD, 35));

        JMenuItem startChoice = new JMenuItem("Start Menu");
        startChoice.setFont(new Font("Serif", Font.PLAIN, 25));
        startChoice.addActionListener(new StartListener( ));
        commandsMenu.add(startChoice);

        JMenuItem buyChoice = new JMenuItem("Buy");
        buyChoice.setFont(new Font("Serif", Font.PLAIN, 25));
        buyChoice.addActionListener(new BuyListener( ));
        commandsMenu.add(buyChoice);

        JMenuItem sellChoice = new JMenuItem("Sell");
        sellChoice.setFont(new Font("Serif", Font.PLAIN, 25));
        sellChoice.addActionListener(new SellListener( ));
        commandsMenu.add(sellChoice);

        JMenuItem updateChoice = new JMenuItem("Update");
        updateChoice.setFont(new Font("Serif", Font.PLAIN, 25));
        updateChoice.addActionListener(new UpdateListener( ));
        commandsMenu.add(updateChoice);

        JMenuItem gainChoice = new JMenuItem("Gain");
        gainChoice.setFont(new Font("Serif", Font.PLAIN, 25));
        gainChoice.addActionListener(new GainListener( ));
        commandsMenu.add(gainChoice);

        JMenuItem searchChoice = new JMenuItem("Search");
        searchChoice.setFont(new Font("Serif", Font.PLAIN, 25));
        searchChoice.addActionListener(new SearchListener( ));
        commandsMenu.add(searchChoice);

        JMenuItem quitChoice = new JMenuItem("Quit");
        quitChoice.setFont(new Font("Serif", Font.PLAIN, 25));
        quitChoice.addActionListener(new QuitListener( ));
        commandsMenu.add(quitChoice);
        
        JMenuBar bar = new JMenuBar( );
        bar.add(commandsMenu);

        return bar;

    }

    
}

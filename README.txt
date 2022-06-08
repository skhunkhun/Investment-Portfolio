GENERAL PROBLEM:

    Trying to create a sustainable and robust graphical user interface for and ePortfolio of investments.

    **Designed for powershell interface.

TESTPLAN:

    //Compiling
        To compile the program, print 'javac ePortfolio/*.java' in the command line and make sure the package 'ePortfolio' is in the directory.
        After program is compiled, start the program by entering 'java ePortfolio.Interface' in the command line.

        *There is no file input for this Interface as it is not a requirement

    //Menu navigation

        When first loading up the program, you will see a menu with text that welcomes you to the ePortfolio and prompts you to click on the commands menu.

        When clicking the commands menu, you should now see a menu with multiple prompts such as 'start menu' 'buy', 'sell', 'update', 'getgain', 'search', and 'quit'.
        To test if the program is robust, click 'quit' and the program should close.

    //'buy' prompt

        Selecting the buy prompt shows an interface with type, symbol, name, quantity and price. It has a textbox at the bottom and 2 buttons on the east of the panel with 'reset
        and 'buy'
        
        If the user clicks buy without entering any fields, a error message will be displayed. If the user enter any negative or 0 quantity or prices, an error message will be displayed.

        Entering all the fields correctly will prompt a message stating the bookvalue of the newly entered message. The user has to enter 'reset' to clear the fields again.

        If the user tries to enter an investment that was already in the portfolio, the quantity and price that is in the field will be added to the existing investment and the user will be
        prompted with a message.

        **One limitation of the program is that it expects the user to know that it is adding the new quantity and price if there is a duplicate investment.

        **SAMPLE BUY

        Now enter "buy", press "stock", and then enter "AAPL,Apple Inc.,100,140.10". You can add another investment of your choice after the AAPL investment. Reset the text area by
        selecting 'reset' and enter 'AAPL' again with any name(the name will not be saved since it is a duplicate) and enter a desired new price and quantity to add to you existing investment.
        After selecting 'buy', a message should appear that tells the user that the AAPL is already in the portfolio and that the new price and quantity has been updated.


    //'sell' prompt
        Now enter "sell" 
        
        Selecting the sell menu shows an interface with symbol, quantity and price. It has a textbox at the bottom and 2 buttons on the east of the panel with 'reset
        and 'sell'

        If the user clicks buy without entering any fields, a error message will be displayed. If the user enter any negative or 0 quantity or prices, an error message will be displayed.
        If there are no investments with the symbol entered, an error message will be displayed.

        Entering correct values with an investment present will prompt a success message in the message box to indicate that the investment was sold.

        **SAMPLE SELL
        Enter "GME,100,100" and user should see an error message claiming that there are no investments with 'GME'. Enter "sell" and enter "AAPL,140,600". The program should print
        'Not enough quantity in your stocks portfolio'.
        Now enter "sell" and enter "AAPL,142.23,200". Now enter 'getgain' and the gain should be about 9629.016. Enter "print" and the new bookvalue and quantity for AAPL
        should be shown. Enter "sell" and enter "SSETX,42.21,150". Select "getgain" and the total gain should be about 6269.016. 
        Enter "sell" and enter "AAPL,142.23,300". Enter "print" and there should be no stocks in the portfolio. Enter "getgain" and the total gain should be updated.
        Enter "buy", then enter "stock", then enter "AAPL,Apple Inc.,500,110.08" to get AAPL back in the portfolio.

    //'Gain' prompt

        Selecting the gain menu shows an interface with total gain. It has a textbox at the bottom that says 'individual gains' and 2 buttons on the east of the panel with 'reset'
        and 'get gain'.

        selecting get gain will print the total gain in the text field and the individual gain for all of the investments in the bottom textbox.


    //'update' prompt

        Selecting the update menu shows an interface with symbol, name and price. The symbol and name field are non-editiable. It has a textbox at the bottom and 3 buttons on the east of the panel with 'prev', 'next'
        and 'save'.

        If the user clicks 'next', the symbol, name, and price of the investment will be displayed. It will go to the first investment and if the user keeps clicking next, it will go until the end of the list. 
        If the user clicks 'prev', the it will show theh previous item in the list until the list is at the beggining.

        The user is prompted to enter  a new price for a selected investment, and if a valid input is selected, then the message box will show the updated investment and all of its attributes.

        If the user enters a negative value or 0 for the price or quantity, then an error message will print. If the user tries to enter a string, an error will be displayed.
        If the user enter a greater quantity than available, then an error message will be displayed.

        
        **SAMPLE UPDATE
        Click next, enter a new price and click save. The new investment with the new price will be displayed. Click next a couple more times, and then click prev, and enter a valid price, press save and the
        investment will be shown.

    //'search' prompt

        **LIMITATION: Search is formatted the same way as in the previous assignments. More specifically "-100", "100", "100-", and "100-200" are the valid inputs.

        **SAMPLE SEARCH
        Enter "search" and you will be prompted to enter a symbol, a set of keywords, and a price range. For now leave all of the fields blank by pressing enter 3 times. You should 
        see all of the investments in the portfoilio which include AAPL and SSETX.
        Now enter "search" and enter "AAPL" and leave the other fields blank. The AAPL investment should only be shown.
        Enter "search" and enter "Growth fund" only in the keyword field and leave the rest blank. You should only see the SSETX investment.
        Enter "search" and enter "40-200" in the price field and leave the others blank you should see both investments. Enter "search" again and enter "50-200" only in the price field
        and you should only see the apple investment.
        Enter "search" and enter "-100" in the price field and leave the others blank. You should only see the SSETX investment.
        Enter "search" and enter "100-" in the price field and leave the others blank. You should only see the AAPL investment.
        Enter "search" and enter "142.23" in the price field and leave the others blank. You should see only the AAPL investment.
        Enter "search" and enter as many of the fields as you like, and if your search does not match any investments in the portfolio, then an error message will be printed.
        Keep entering "search" and enter any of the fields, and the correct investment should be printed as the result.

        For Search:

            If the given item is not on the list:
            The menu will be reprinted with no investment being displayed.

            If the given item is at the start of the list:
            The item will be printed with 'Investment found:' above it, indicated that the search was successful.

            If the given item is at the end of the list:
            The item will be printed with 'Investment found:' above it, indicated that the search was successful.

            If the given item between the two ends in the list:
            The item will be printed with 'Investment found:' above it, indicated that the search was successful.

            For the keywords:
                If the investment name is 'Apple Inc.', the user can enter 'Inc.', 'Apple', 'Inc. Apple', or 'Apple Inc.' with case insensitivity and the investment should be printed.
                If the user only enters 'inc', the investment will not be printed since it is missing '.'.
            
            The more fields the user enters, the more specific the search gets. For example:
                if there are investments "TOR,Toronto bank, 500, 100" and "TORS, Toronto star, 100, 200", the user can enter "TOR, "Toronto" in the search field and only the first investment will be printed.
                if the user just enters "toronto" in the keywords search field, both of the investments will be printed.
                if the user enters "toronto, 100-", both investments will be printed, since toronto is a keyword for both, and both have prices equal to or greater than 100.
                if the user enters "star, 100-", only the second investment will be printed.
                if the user enters "TOR,bank,100", the first investment will be printed and so on.

    //'quit' prompt
        Once you are done with the program, go to the menu and click quit or click the X in the top right.

LIMITATIONS / Assumptions:

    **LIMITATION: Search is formatted the same way as in the previous assignments. More specifically "-100", "100", "100-", and "100-200" are the valid inputs.

    **LIMITATION: Buy - If the user is trying to add a duplicate investment, then the already filled out price and quantity textfield will be added to the duplicate investment.

    **ASSUMPTION: Buy - expects the user to know that it is added a new price and quantity to a duplicate investment.

    **ASSUMPTION: Assuming no file will be read or written as it is not in the assignment description.


IMPROVEMENTS:

    //'buy'
    If an investment is a duplicate, then could add a way for the user to be warned and give a chance to add a new text field instead of adding what they already entered.

    //'search'
    If a search item is not found, could print a message indicates that the item was not found instead of printing nothing.
    Add the higher and lower price fields to make program more robust.

    //Overall interface
    Make the spacing of the fields more visually appealing. Make interface as a whole more visually appealing.

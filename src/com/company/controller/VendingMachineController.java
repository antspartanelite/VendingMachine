package com.company.controller;

import com.company.dto.Change;
import com.company.exception.InsufficientFundsException;
import com.company.exception.NoItemInventoryException;
import com.company.service.ServiceLayer;
import com.company.ui.UserIOHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;

@Component
public class VendingMachineController {
    ServiceLayer serviceLayer;
    UserIOHandler ioHandler;

    @Autowired
    public VendingMachineController(ServiceLayer serviceLayer, UserIOHandler ioHandler){
        //Initialises the user IO and file io objects required
        this.ioHandler = ioHandler;
        this.serviceLayer = serviceLayer;
    }

    //Executes the vending machine software
    public void execute() {
        serviceLayer.updateAuditLog("Program started.");


        HashMap<String, String[]> inventory = serviceLayer.loadInventory();

        ioHandler.displayMenu(inventory);

        //Gives the user the option to exit the program
        String userInput = ioHandler.requestExit().toLowerCase(Locale.ROOT);
        if (userInput.equals("exit")) {
            serviceLayer.updateInventory(inventory);
            serviceLayer.updateAuditLog("User chose to exit program.");
            return;
        }

        //Loops forever until the user chooses to exit
        while (true) {
            //Requests money and an item to buy from the user
            BigDecimal moneyInput = ioHandler.requestMoney();
            String requestedItem = ioHandler.requestItem();

            String[] itemToRetrieve = inventory.get(requestedItem);

            try {
                //Throws NoItemInventoryException if an item is not in the inventory
                if (!inventory.containsKey(requestedItem)) {
                    throw new NoItemInventoryException();
                }

                //If the quantity of the item requested is 0 throws NoItemInventoryException
                if (Integer.parseInt(itemToRetrieve[2]) == 0) {
                    throw new NoItemInventoryException();
                }
                //Calculates if change is not required and completes the transaction if this is the case
                else if (moneyInput.compareTo(new BigDecimal(itemToRetrieve[1])) == 0) {
                    ioHandler.noChange();
                    serviceLayer.updateAuditLog("User has entered the exact amount of money and bought a " + itemToRetrieve[0]);
                }
                //Throws an InsufficientFundsException if the user has not inputted money greater an item's price
                else if (moneyInput.compareTo(new BigDecimal(itemToRetrieve[1])) != 1) {
                    throw new InsufficientFundsException();
                }
                //Otherwise calculates the change due to the user and displays the change given to them
                else {
                    inventory.get(requestedItem)[2] = "" + (Integer.parseInt(itemToRetrieve[2]) - 1);
                    ioHandler.returnChange(new Change(moneyInput.subtract(new BigDecimal(itemToRetrieve[1]))).getCoinsDue());
                    serviceLayer.updateInventory(inventory);
                    serviceLayer.updateAuditLog("User has bought a " + itemToRetrieve[0] + " and entered £" + moneyInput + " £" + moneyInput.subtract(new BigDecimal(itemToRetrieve[1])) + " change is due");
                }


            }
            //Upon a NoItemInventoryException updates the audit log and informs the user of the issue
            catch (NoItemInventoryException e) {
                serviceLayer.updateAuditLog("The user requested " + requestedItem + " but there is currently no stock of this item");
                System.out.println("There is currently no stock of the item you requested");
            }
            //Upon an InsufficientFundsException updates the audit log and informs the user of the issue
            catch (InsufficientFundsException e) {
                serviceLayer.updateAuditLog("Insufficiemt funds exception, the user entered £" + moneyInput +
                        " when the item they wanted(" + requestedItem + " costed £" + itemToRetrieve[1] + ")");
                System.out.println("You did not enter enough money! You entered £" + moneyInput +
                        " but " + requestedItem + " costs £" + itemToRetrieve[1]);
            }

            //Gives the user the option to exit before rerunning the loop
            userInput = ioHandler.requestExit().toLowerCase(Locale.ROOT);
            if (userInput.equals("exit")) {
                serviceLayer.updateInventory(inventory);
                serviceLayer.updateAuditLog("User chose to exit program.");
                return;
            }

            ioHandler.displayMenu(inventory);
        }
    }
}

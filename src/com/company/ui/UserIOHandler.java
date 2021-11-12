package com.company.ui;

import com.company.dto.Coin;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Scanner;

@Component
public class UserIOHandler {
    Scanner inputReader;

    //Displays the menu of all items in the vending machine's inventory
    public void displayMenu(HashMap<String,String[]> inventory){
        System.out.println("The Vending machine currently contains\n");
        for(String[] item : inventory.values()){
            System.out.println("Item Name: "+item[0]);
            System.out.println("Cost: "+item[1]);
            System.out.println("No. Remaining: "+item[2]+"\n\n");
        }

    }

    //Asks the user if they wish to exit the program
    public String requestExit(){
        System.out.println("If you wish to exit please enter exit now, otherwise enter any other input");
        inputReader = new Scanner(System.in);
        return inputReader.nextLine();
    }

    //Asks the user to enter an item to purchase
    public String requestItem(){
        System.out.println("Please enter the name of the item you wish to purchase");
        inputReader = new Scanner(System.in);
        return inputReader.nextLine();
    }

    //Asks the user to enter an amount of money to enter into the machine
    public BigDecimal requestMoney(){
        System.out.println("Please enter an amount of money in pounds to insert into the machine");
        inputReader = new Scanner(System.in);
        return new BigDecimal(inputReader.nextLine()).setScale(2, RoundingMode.DOWN);
    }

    //Displays the change given to the user
    public void returnChange(HashMap<Coin, String> coinsToReturn){
        System.out.println("You have been returned:");
        coinsToReturn.keySet().stream()
                .sorted()
                .filter((p) -> !coinsToReturn.get(p).startsWith("0"))
                .forEach((p) -> System.out.println(coinsToReturn.get(p)));
    }

    public void noChange(){
        System.out.println("You are not owed any change");
    }
}

package com.company.dao;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class InventoryDao {

    //Writes the collection to file
    public void writeToFile(String inventory){
        try {
            FileWriter fileWriter = new FileWriter("./src/com/company/dao/inventory");
            fileWriter.write(inventory);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //Reads the collection from file
    public String readFromFile(){
        String inventory = "";
        try {
            FileInputStream inputStream = new FileInputStream("./src/com/company/dao/inventory");
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()){
                inventory = inventory +scanner.nextLine()+"\n";
            }
            inventory = inventory.substring(0,inventory.length()-1);
        }
        catch (IOException e){
            System.out.println(e);
        }

        return inventory;
    }
}

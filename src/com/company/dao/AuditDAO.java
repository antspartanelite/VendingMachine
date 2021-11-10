package com.company.dao;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AuditDAO {
    //Writes the collection to file
    public void writeToFile(String auditLog){
        try {
            FileWriter fileWriter = new FileWriter("./src/com/company/dao/auditlog");
            fileWriter.write(auditLog);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //Reads the collection from file
    public String readFromFile(){
        String auditLog = "";
        try {
            FileInputStream inputStream = new FileInputStream("./src/com/company/dao/auditlog");
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()){
                auditLog = auditLog + scanner.nextLine()+"\n";
            }
        }
        catch (IOException e){
            System.out.println(e);
        }

        return auditLog;
    }
}

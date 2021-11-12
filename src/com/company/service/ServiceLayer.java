package com.company.service;

import com.company.dao.AuditDAO;
import com.company.dao.InventoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ServiceLayer {
    InventoryDao inventoryDao;
    AuditDAO auditDAO;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date;

    @Autowired
    public ServiceLayer(InventoryDao inventoryDao, AuditDAO auditDAO){
        this.inventoryDao = inventoryDao;
        this.auditDAO = auditDAO;
    }

    //Reformats the inventory Hashmap into a string format and tells the inventory dao to write it to file
    public void updateInventory(HashMap<String,String[]> inventory){
        String inventoryString = "";
        int i=0;
        for(String[] item: inventory.values()){
            for(int j=0;j<3;j++){
                if(j!=2){
                    inventoryString = inventoryString + item[j] + ",";
                }
                else {
                    inventoryString = inventoryString + item[j];
                }
            }
            if(i!=inventory.size()-1){
                inventoryString = inventoryString + "\n";
            }
            i++;
        }
        inventoryDao.writeToFile(inventoryString);
    }

    //Reformats string from inventory file into an arraylist
    public HashMap<String, String[]> loadInventory(){
        HashMap<String,String[]> inventory = new HashMap<String,String[]>();
        String[] items = inventoryDao.readFromFile().split("\n");
        for(String property:items){
            inventory.put(property.split(",")[0].toLowerCase(Locale.ROOT),property.split(","));
        }
        return inventory;
    }

    //Updates the audit log by loading the current audit log as a string and adds a new message to it
    public void updateAuditLog(String updateMessage){
        date = new Date();
        auditDAO.writeToFile(auditDAO.readFromFile()+"\n"+formatter.format(date)+" "+updateMessage);
    }
}

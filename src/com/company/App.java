package com.company;

import com.company.controller.VendingMachineController;
import com.company.dao.AuditDAO;
import com.company.dao.InventoryDao;
import com.company.service.ServiceLayer;
import com.company.ui.UserIOHandler;

public class App {

    public static void main(String[] args) {
	    //Creates a new vending machine controller and tells it to execute
        new VendingMachineController().execute(new ServiceLayer(new InventoryDao(),new AuditDAO()), new UserIOHandler());
    }
}

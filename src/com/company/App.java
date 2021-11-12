package com.company;

import com.company.controller.VendingMachineController;
import com.company.dao.AuditDAO;
import com.company.dao.InventoryDao;
import com.company.service.ServiceLayer;
import com.company.ui.UserIOHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.company");
        appContext.refresh();
	    //Creates a new vending machine controller and tells it to execute
        VendingMachineController controller = appContext.getBean("vendingMachineController",VendingMachineController.class);
        //new VendingMachineController().execute(new ServiceLayer(new InventoryDao(),new AuditDAO()), new UserIOHandler());
        controller.execute();
    }
}

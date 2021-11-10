package com.company.tests;

import com.company.dao.AuditDAO;
import com.company.dao.InventoryDao;
import com.company.service.ServiceLayer;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ServiceLayerTest {

    //Tests both loading and updating
    @Test
    void loadInventory() {
        ServiceLayer serviceLayer = new ServiceLayer(new InventoryDao(),new AuditDAO());

        HashMap<String, String[]> testInventory = serviceLayer.loadInventory();
        testInventory.put("test",new String[]{"test","1.00","1"});
        serviceLayer.updateInventory(testInventory);

        testInventory = serviceLayer.loadInventory();

        assertArrayEquals(new String[]{"test", "1.00", "1"}, testInventory.get("test"));

        //Resets inventory to initial condition
        testInventory.remove("test");
        serviceLayer.updateInventory(testInventory);
    }
}
package com.company.tests;

import com.company.dto.Change;
import com.company.dto.Coin;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Assert;

class ChangeTest {

    @Test
    void getCoinsDue() {
        HashMap<Coin,String> testMap = new HashMap<>();
        testMap.put(Coin.TWOPOUND,"0 £2 coins");
        testMap.put(Coin.ONEPOUND,"0 £1 coins");
        testMap.put(Coin.FIFTYPENCE,"0 50p coins");
        testMap.put(Coin.TWENTYPENCE,"0 20p coins");
        testMap.put(Coin.TENPENCE,"0 10p coins");
        testMap.put(Coin.FIVEPENCE,"0 5p coins");
        testMap.put(Coin.TWOPENCE,"0 2p coins");
        testMap.put(Coin.ONEPENCE,"1 1p coins");
        Assert.assertTrue(new Change(new BigDecimal(0.01)).getCoinsDue().equals(testMap));

        testMap.put(Coin.TWOPOUND, "1 £2 coins");
        testMap.put(Coin.TWOPENCE, "1 2p coins");
        Assert.assertTrue(new Change(new BigDecimal("2.03")).getCoinsDue().equals(testMap));

        testMap.put(Coin.TWOPOUND, "48 £2 coins");
        testMap.put(Coin.ONEPOUND, "1 £1 coins");
        testMap.put(Coin.FIFTYPENCE, "1 50p coins");
        testMap.put(Coin.TWENTYPENCE, "1 20p coins");
        testMap.put(Coin.FIVEPENCE, "1 5p coins");
        testMap.put(Coin.TWOPENCE, "2 2p coins");
        testMap.put(Coin.ONEPENCE, "0 1p coins");
        Assert.assertTrue(new Change(new BigDecimal("97.79")).getCoinsDue().equals(testMap));
    }
}
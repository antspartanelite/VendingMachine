package com.company.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class Change {

    private HashMap<Coin, String> coinsDue = new HashMap<>();

    public Change(BigDecimal changeDue){
        //Iterates through every coin denomination
        for(Coin coin : Coin.values()){
            //If the coin's value is measured in pence rather than pounds(less than 1)
            if(new BigDecimal(1).compareTo(coin.getValue()) == 1) {
                coinsDue.put(coin,
                        changeDue.divide(coin.getValue(), RoundingMode.FLOOR).setScale(0, RoundingMode.FLOOR)
                        + " " +
                        coin.getValue().multiply(new BigDecimal(100)).setScale(0, RoundingMode.FLOOR)
                        + "p coins");
            }
            //If the coin's value is measured in pounds (greater than or equal to 1)
            else{
                coinsDue.put(coin,
                        changeDue.divide(coin.getValue(), RoundingMode.FLOOR).setScale(0, RoundingMode.FLOOR)
                        + " £" +
                        coin.getValue().setScale(0, RoundingMode.FLOOR)
                        + " coins");
            }
            //Calculates new amount of change required after the amount of each coin denomination has been calculated
            changeDue = changeDue.remainder(coin.getValue());
        }

    }

    public HashMap<Coin, String> getCoinsDue() {
        return coinsDue;
    }
}

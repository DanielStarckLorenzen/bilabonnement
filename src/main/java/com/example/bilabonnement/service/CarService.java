package com.example.bilabonnement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarService {

    public Map<String, Integer> kilometersPrice() {
        Map<String,Integer> kmPrice = new HashMap<>();
        kmPrice.put("1.500 km.", 0);
        kmPrice.put("1.750 km.", 300);
        kmPrice.put("2.000 km.", 590);
        kmPrice.put("2.500 km.", 1160);
        kmPrice.put("3.000 km.", 1710);
        kmPrice.put("3.500 km.", 2240);
        kmPrice.put("4.000 km.", 2750);
        kmPrice.put("4.500 km.", 3240);


        return kmPrice;
    }

}

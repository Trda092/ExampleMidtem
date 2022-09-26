package com.example.examplemidtem;

import org.springframework.stereotype.Service;

@Service
public class CalculatorPriceService {

    public CalculatorPriceService() {
    }

    public double getPrice(double cost, double profit){
        return cost+profit;
    }
}

package com.example.examplemidtem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorPriceController {
    private CalculatorPriceService calService = new CalculatorPriceService();

    @GetMapping(value = "/getPrice/{cost}/{profit}")
    public double serviceGetProducts(@PathVariable("cost") double cost, @PathVariable("profit") double profit){
        return calService.getPrice(cost,profit);
    }
}

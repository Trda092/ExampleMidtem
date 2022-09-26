package com.example.examplemidtem;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("Product")
public class Product implements Serializable {
    private String _id, productName;
    private double productCost;
    private double productProfit;
    private double productPrice;

    public Product() {
        this._id = null;
        this.productName = "";
        this.productCost = 0.0;
        this.productProfit = 0.0;
        this.productPrice = 0.0;
    }

    public Product(String _id, String productName, double productCost, double productProfit, double productPrice) {
        this._id = _id;
        this.productName = productName;
        this.productCost = productCost;
        this.productProfit = productProfit;
        this.productPrice = productPrice;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductCost() {
        return productCost;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }

    public double getProductProfit() {
        return productProfit;
    }

    public void setProductProfit(double productProfit) {
        this.productProfit = productProfit;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}

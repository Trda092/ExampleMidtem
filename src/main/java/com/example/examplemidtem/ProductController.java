package com.example.examplemidtem;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ProductService productService;
    protected Product product;
    public ProductController(){
        this.product = new Product();
    }

    @GetMapping(value = "/getAllProduct")
    public Object serviceGetAllProduct(){
        return rabbitTemplate.convertSendAndReceive("ProductExchange", "getall", "");
    }

    @PostMapping(value = "/getProductByName")
    public Object serviceGetProductName(@RequestBody String productName){
        return rabbitTemplate.convertSendAndReceive("ProductExchange", "getname", productName);
    }

    @PostMapping(value = "/addProduct")
    public String serviceAddProduct(@RequestBody Product product){
        rabbitTemplate.convertSendAndReceive("ProductExchange","add",product);
        return "Add Product Completed";
    }

    @PostMapping(value = "/delProduct")
    public String serviceDeleteProduct(@RequestBody Product product){
        rabbitTemplate.convertSendAndReceive("ProductExchange","delete", product);
        return "Delete Product Completed";
    }

    @PostMapping(value = "/updateProduct")
    public String serviceUpdateProduct(@RequestBody Product product){
        Product pds = productService.serviceGetProductId(product.get_id());
        System.out.println(product.get_id());
        System.out.println(product);
        if(pds != null) {
            rabbitTemplate.convertSendAndReceive("ProductExchange", "update", product);
        }
        return "Update Product Completed";
    }

}

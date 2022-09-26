package com.example.examplemidtem;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository = repository;
    }

    @RabbitListener(queues = "GetNameProductQueue")
    public Product serviceGetProductName(String productName){
        return repository.findByName(productName);
    }

    public Product serviceGetProductId(String id){
        return repository.findByID(id);
    }

    @RabbitListener(queues = "GetAllProductQueue")
    public List serviceGetAllProduct(){
        return repository.findAll();
    }

    @RabbitListener(queues = "AddProductQueue")
    public Product serviceAddProduct(Product product){
        product.set_id(null);
        return repository.save(product);
    }

    @RabbitListener(queues = "DeleteProductQueue")
    public Boolean serviceDeleteProduct(Product product){
        try{
            repository.delete(product);
            return true;
        }
        catch(Exception e){
        return false;
    }
    }

    @RabbitListener(queues = "UpdateProductQueue")
    public Product serviceUpdateProduct(Product product){
        return repository.save(product);}
    }


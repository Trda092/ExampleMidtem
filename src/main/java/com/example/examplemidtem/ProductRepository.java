package com.example.examplemidtem;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    @Query(value="{productName:?0}")
    public Product findByName(String productName);

    @Query(value="{_id:?0}")
    public Product findByID(String _id);
}

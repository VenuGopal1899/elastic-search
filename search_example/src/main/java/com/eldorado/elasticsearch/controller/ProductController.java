package com.eldorado.elasticsearch.controller;

import com.eldorado.elasticsearch.dao.ProductDao;
import com.eldorado.elasticsearch.bean.Product;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping
    public Product insertProduct(@RequestBody Product product) throws Exception{
        return productDao.insertProduct(product);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getProductById(@PathVariable String id){
        return productDao.getProductById(id);
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateProductById(@RequestBody Product book, @PathVariable String id){
        return productDao.updateProductById(id, book);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable String id){
        productDao.deleteProductById(id);
    }
}

package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    
    public Product saveProduct(Product product) {
	return productRepository.save(product);
    }
    
    public Product getProductById(Long id) {
	return productRepository.getProductById(id);
    }
    public List<Product> getAllProduct(){
	return productRepository.findAll();
    }
    
    public void deleteProductById(Long id) {
	productRepository.deleteById(id);
    }
}

package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.CustomerProduct;
import com.example.demo.repository.CustomerProductRepository;

@Service
public class CustomerProductService {

    @Autowired
    private CustomerProductRepository custoproRepository;

    public CustomerProduct createCusotmerProduct(CustomerProduct custoPro) {
	return custoproRepository.save(custoPro);
    }

    public CustomerProduct getCustomerProductById(Long id) {
	return custoproRepository.getCustomerProductById(id);
    }

    public List<CustomerProduct> getCustomerProductAll() {
	return custoproRepository.findAll();
    }

    public void deleteCustoProduct(Long id) {
	custoproRepository.deleteById(id);
    }
}

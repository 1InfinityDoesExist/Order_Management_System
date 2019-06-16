package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.Customer;
import com.example.demo.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRespository;

    public Customer saveCustoreDetails(Customer customer) {
	return customerRespository.save(customer);
    }

    public Customer getCustomerById(Long id) {
	return customerRespository.getCustomerById(id);
    }

    public List<Customer> getAllCustomer() {
	return customerRespository.findAll();
    }

    public void deleteCustomerById(Long id) {
	customerRespository.deleteById(id);
    }
}
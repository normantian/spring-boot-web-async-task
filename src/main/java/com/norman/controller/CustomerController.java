package com.norman.controller;

import com.norman.dao.CustomerRepository;
import com.norman.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/customer")
public class CustomerController {


    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public List<Customer> listAll(){
        log.info("find all");
        return customerRepository.findAll();
    }

    @PostMapping
    public String save(@RequestBody Customer customer){
        customerRepository.addCustomer(customer.getName(), customer.getEmail());
        return "success";
    }

}

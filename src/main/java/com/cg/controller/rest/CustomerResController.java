package com.cg.controller.rest;


import com.cg.model.Customer;
import com.cg.model.dto.CustomerCreReqDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerResController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<?> getALl() {
        List<Customer> customers = customerService.findAll();

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CustomerCreReqDTO customerCreReqDTO, BindingResult bindingResult) {

        new CustomerCreReqDTO().validate(customerCreReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Customer customer = new Customer();
        customer.setFullName(customerCreReqDTO.getFullName());
        customer.setEmail(customerCreReqDTO.getEmail());
        customer.setPhone(customerCreReqDTO.getPhone());
//        customer.setAddress(customerCreReqDTO.getAddress());
        customer.setBalance(BigDecimal.ZERO);
        customer.setDeleted(false);

        customerService.save(customer);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }
}

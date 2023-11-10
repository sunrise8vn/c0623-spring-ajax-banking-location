package com.cg.controller.rest;


import com.cg.exception.DataInputException;
import com.cg.model.Customer;
import com.cg.model.dto.CustomerCreReqDTO;
import com.cg.model.dto.CustomerResDTO;
import com.cg.model.dto.CustomerUpReqDTO;
import com.cg.model.dto.RecipientWithOutSenderDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        List<CustomerResDTO> customerResDTOS = customerService.findAllCustomerResDTO();

//        List<Customer> customers = customerService.findAll();
//        List<CustomerResDTO> customerResDTOS = new ArrayList<>();
//
//        for (Customer customer : customers) {
//            CustomerResDTO customerResDTO = customer.toCustomerResDTO();
//            customerResDTOS.add(customerResDTO);
//        }

        return new ResponseEntity<>(customerResDTOS, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getById(@PathVariable Long customerId) {

        Customer customer = customerService.findById(customerId).orElseThrow(() -> {
            throw new DataInputException("Customer not found");
        });

        CustomerResDTO customerResDTO = customer.toCustomerResDTO();


        return new ResponseEntity<>(customerResDTO, HttpStatus.OK);
    }

    @GetMapping("/get-all-recipient-with-out-id/{senderId}")
    public ResponseEntity<?> getAllRecipientsWithOutId(@PathVariable Long senderId) {

        List<RecipientWithOutSenderDTO> recipients = customerService.findAllRecipientWithOutSenderDTO(senderId);

        return new ResponseEntity<>(recipients, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody CustomerCreReqDTO customerCreReqDTO, BindingResult bindingResult) {

        new CustomerCreReqDTO().validate(customerCreReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Customer customer = customerCreReqDTO.toCustomer();
        customer.setBalance(BigDecimal.ZERO);
        customer.setDeleted(false);

        customerService.create(customer);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<?> update(@PathVariable Long customerId, @Validated @RequestBody CustomerUpReqDTO customerUpReqDTO, BindingResult bindingResult) {

        Customer customer = customerService.findById(customerId).orElseThrow(() -> {
           throw new DataInputException("Customer not found");
        });

        new CustomerUpReqDTO().validate(customerUpReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        customerService.update(customerId, customer.getLocationRegion().getId(), customerUpReqDTO);

        customer = customerService.findById(customerId).get();

        return new ResponseEntity<>(customer.toCustomerResDTO(), HttpStatus.OK);
    }
}

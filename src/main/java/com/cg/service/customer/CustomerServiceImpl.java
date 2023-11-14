package com.cg.service.customer;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dto.CustomerResDTO;
import com.cg.model.dto.CustomerUpReqDTO;
import com.cg.model.dto.RecipientWithOutSenderDTO;
import com.cg.model.dto.TransferCreReqDTO;
import com.cg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private WithdrawRepository withdrawRepository;

    @Autowired
    private TransferRepository transferRepository;


    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> findAllByIdNot(Long id) {
        return customerRepository.findAllByIdNot(id);
    }

    @Override
    public List<CustomerResDTO> findAllCustomerResDTO() {
        return customerRepository.findAllCustomerResDTO();
    }

    @Override
    public List<RecipientWithOutSenderDTO> findAllRecipientWithOutSenderDTO(Long customerId) {
        return customerRepository.findAllRecipientWithOutSenderDTO(customerId);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void create(Customer customer) {
        LocationRegion locationRegion = customer.getLocationRegion();
        locationRegionRepository.save(locationRegion);

        customer.setLocationRegion(locationRegion);
        customerRepository.save(customer);
    }


    @Override
    public void update(Long customerId, Long locationRegionId, CustomerUpReqDTO customerUpReqDTO) {
        Customer customer = customerUpReqDTO.toCustomer(customerId);

        LocationRegion locationRegion = customer.getLocationRegion();
        locationRegion.setId(locationRegionId);
        locationRegionRepository.save(locationRegion);

        customer.setId(customerId);
        customerRepository.save(customer);
    }

    @Override
    public void deposit(Deposit deposit) {

        depositRepository.save(deposit);

//        Customer customer = deposit.getCustomer();
//        BigDecimal currentBalance = customer.getBalance();
//        BigDecimal transactionAmount = deposit.getTransactionAmount();
//        BigDecimal newBalance = currentBalance.add(transactionAmount);
//        customer.setBalance(newBalance);
//        customerRepository.save(customer);

        customerRepository.incrementBalance(deposit.getCustomer().getId(), deposit.getTransactionAmount());
    }

    @Override
    public void withdraw(Withdraw withdraw) {
        Customer customer = withdraw.getCustomer();
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal transactionAmount = withdraw.getTransactionAmount();
        BigDecimal newBalance = currentBalance.subtract(transactionAmount);
        customer.setBalance(newBalance);

        customerRepository.save(customer);

        withdrawRepository.save(withdraw);
    }

    @Override
    public void transfer(Long senderId, TransferCreReqDTO transferCreReqDTO) {
        Long recipientId = transferCreReqDTO.getRecipientId();

        if (senderId.equals(recipientId)) {
            throw new DataInputException("Sender must be different from recipient");
        }

        Customer sender = customerRepository.findById(senderId).orElseThrow(() -> {
            throw new DataInputException("Sender not found");
        });

        Customer recipient = customerRepository.findById(recipientId).orElseThrow(() -> {
            throw new DataInputException("Recipient not found");
        });

        BigDecimal senderBalance = sender.getBalance();
        BigDecimal transferAmount = transferCreReqDTO.getTransferAmount();
        long fees = 10L;
        BigDecimal feesAmount = transferAmount.multiply(BigDecimal.valueOf(fees)).divide(BigDecimal.valueOf(100L));
        BigDecimal transactionAmount = transferAmount.add(feesAmount);

        if (senderBalance.compareTo(transactionAmount) < 0) {
            throw new DataInputException("Sender's balance is not enough to transfer");
        }

        customerRepository.decrementBalance(senderId, transactionAmount);

        customerRepository.incrementBalance(recipientId, transferAmount);

        Transfer transfer = new Transfer();
        transfer.setSender(sender);
        transfer.setRecipient(recipient);
        transfer.setTransferAmount(transferAmount);
        transfer.setFees(fees);
        transfer.setFeesAmount(feesAmount);
        transfer.setTransactionAmount(transactionAmount);

        transferRepository.save(transfer);

    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}

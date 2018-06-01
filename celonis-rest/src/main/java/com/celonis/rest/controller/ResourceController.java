package com.celonis.rest.controller;

/*import com.celonis.rest.exceptions.NotFoundException;
import com.celonis.rest.services.impl.GenericServiceImpl;
import com.celonis.rest.domain.BankAccount;
import com.celonis.rest.model.User;
import com.celonis.rest.services.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;*/
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/springjwt")
public class ResourceController {

    /*private static final Logger LOGGER = LoggerFactory.getLogger(GenericServiceImpl.class);

    @Autowired
    private GenericService userService;

    @RequestMapping(value ="/accounts", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public List<BankAccount> getAccounts() {
        LOGGER.info("getAccounts");
        return userService.findAllBankAccounts();
    }

    @RequestMapping(value ="/users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public List<User> getUsers() {
        LOGGER.info("getUsers");
        return userService.findAllUsers();
    }

    @RequestMapping(value ="/accounts", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public BankAccount saveBankAccount(@RequestBody BankAccount bankAccount){
        LOGGER.info("saveBankAccount :: {}", bankAccount);
        if (!bankAccount.validate()) {
            throw new NotFoundException("invalid data for bank account");
        }
        return userService.addBankAccount(bankAccount);
    }

    @PutMapping(value ="/accounts")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public BankAccount editBankAccount(@RequestBody BankAccount bankAccount){
        LOGGER.info("editBankAccount :: {}", bankAccount);
        if (!bankAccount.validate()) {
            throw new NotFoundException("invalid data for bank account");
        }
        return userService.editBankAccount(bankAccount);
    }

    @DeleteMapping(value ="/accounts/{id}")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public boolean deleteBankAccount(@PathVariable Long id){
        LOGGER.info("deleteBankAccount :: {}", id);
        userService.deleteBankAccount(id);
        return true;
    }*/
}

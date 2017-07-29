package com.byteperceptions.graphqldemo.dataloader;

import com.byteperceptions.graphqldemo.account.Account;
import com.byteperceptions.graphqldemo.account.AccountRepository;
import com.byteperceptions.graphqldemo.customer.Customer;
import com.byteperceptions.graphqldemo.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Copyright Ryan Ylitalo and BytePerceptions LLC.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@Component
public class DataLoader {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;

    public void loadData() {
        Customer bobJohnson = createCustomer("Bob", "Johnson");
        Customer sallyBrown = createCustomer("Sally", "Brown");

        Account bobsCheckingAccount = createAccount(bobJohnson, "Bob's Checking Account", 2100.50);
        Account bobsSavingAccount = createAccount(bobJohnson, "Bob's Checking Account", 325.50);
        Account sallysCheckingAccount = createAccount(sallyBrown, "Sally's Checking Account", 500.50);
        Account sallysSavingAccount = createAccount(sallyBrown, "Sally's Checking Account", 5555.50);
    }

    private Customer createCustomer(String firstName, String lastName){
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAccounts(new ArrayList<>());
        return customerRepository.save(customer);
    }

    private Account createAccount(Customer customer, String accountName, double initialBalance){
        Account account = new Account();
        account.setName(accountName);
        account.setBalance(initialBalance);
        account.setCustomer(customer);
        account = accountRepository.save(account);
        customer.getAccounts().add(account);
        customerRepository.save(customer);
        return account;
    }
}

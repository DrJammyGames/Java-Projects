package com.bankproject;

import java.util.HashMap;
import java.util.Map;

public class BankAccountManagementSystem {

    private Map<Integer, Double> accounts;

    public BankAccountManagementSystem() {
        this.accounts = new HashMap<>();
    }

    public boolean createAccount(int accountNumber, double initialBalance) {
       

        if(accounts.containsKey(accountNumber) || initialBalance < 0 ) {
            return false;
        }

        accounts.put(accountNumber, initialBalance);

        return true;
    }

    public double deposit(int accountNumber, double amount) {

        if (!accounts.containsKey(accountNumber)) {
            return -1.0;
        }

        double balance = accounts.get(accountNumber);
        balance += amount;
        accounts.put(accountNumber, balance);
        return balance;
    }

    public double withdraw(int accountNumber, double amount) {
        if (!accounts.containsKey(accountNumber)) {
            return 0.0;
        }

        double balance = accounts.get(accountNumber);

        if (amount<=0) {
            
            return -1.0;
            
        } else  if (amount >= balance) {
               
            return -2.0;

        } else if (amount == 0 || balance == 0){

            return -3.0;

        }

        balance -= amount;
        accounts.put(accountNumber, balance);
        return balance;
    }

    public double getAccountBalance(int accountNumber) {
    
        double balance = 0;

        balance = accounts.get(accountNumber);

        return balance;
    }

    
}



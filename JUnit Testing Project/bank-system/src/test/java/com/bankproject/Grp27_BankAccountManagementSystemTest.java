package com.bankproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Grp27_BankAccountManagementSystemTest {

    private BankAccountManagementSystem bank;

    @BeforeEach
    //Set up account creation for testing
    void setUp() {
        bank = new BankAccountManagementSystem();
        //Sample accounts for testing
        //For the withdraw tests
        bank.createAccount(10, 100); 
        //Additional account for testing if needed
        bank.createAccount(15, 50); 
    }

    //Create account test cases
    //Test for a negative balance
    @Test
    void testCreateNegativeBalance(){
        boolean newAccount = bank.createAccount(1,-1);
        //Cannot start account with a negative balance
        assertFalse(newAccount, "A negative balance is not allowed.");
    }

    //Test for valid account
    @Test
    void testCreateValidAccount(){
        boolean newAccount = bank.createAccount(7, 15);
        //Should return true
        assertTrue(newAccount, "A valid account has been created.");
    }

    //Test for duplicate account number
    @Test
    void testCreateDuplicateAccount(){
        bank.createAccount(7, 15);
        boolean newAccount = bank.createAccount(7, 15);
        //Duplicates not allowed
        assertFalse(newAccount, "An account already exists with this account number.");
    }

    //Test for starting balance as 0
    @Test
    void testCreateZeroBalance(){
        boolean newAccount = bank.createAccount(20, 0);
        //Can start an account with a 0 balance
        assertTrue(newAccount, "An account balance starting with zero is valid.");
    }

    //Deposit test cases
    //Test for checking if account exists
    @Test
    void testDepositAccountNotFound(){
        double depositAmount = bank.deposit(11, 5);
        //Not allowed, should return -1.0
        assertEquals(-1.0, depositAmount, "Deposit to a non-existent account is not allowed.");
    } 

    //Test for a valid deposit--account exists 
    @Test
    void testDepositValid(){
        bank.createAccount(11, 5);
        //Deposit to existing account
        double depositAmount = bank.deposit(11, 5);
        assertEquals(10.0, depositAmount, "Balance updated correctly");
    }

    //Withdraw test cases
    //Check if account is found
    @Test
    void testWithdrawalAccountNotFound() {
        double withdrawAmount = bank.withdraw(100, 10);
        //Account does not exist, should return 0.0
        assertEquals(0.0, withdrawAmount, "Account does not exist, cannot withdraw money.");
    }

    //Check if trying to withdraw a negative number
    @Test
    void testWithdrawalNegativeAmount() {
        double withdrawAmount = bank.withdraw(10, -1);
        //Should return -1.0 as cannot withdraw a negative ammount
        assertEquals(-1.0, withdrawAmount, "Cannot withdraw a negative amount.");
    }

    //Check if there are enough funds to withdraw
    @Test
    void testWithdrawalInsufficientFunds() {
        //More than the balance
        double withdrawAmount = bank.withdraw(10, 110);    
        //Cannot withdraw more than the balance and should return -2.0     
        assertEquals(-2.0, withdrawAmount, "Cannot withdraw more than the balance of the account.");
    }

    //Test that the withdrawal is working correctly
    @Test
    void testWithdrawalValid() {
        double withdrawAmount = bank.withdraw(10, 50); 
        //Should reduce the amount correctly.
        assertEquals(50.0, withdrawAmount, "The withdrawal is valid and has reduced the balance correctly.");
    }

    //Test if the withdrawal is reachable
    @Test
    void testWithdrawalUnreachableLogic() {
        //Demonstrates the unreachable block (amount == 0 || balance == 0)
        double withdrawAmount = bank.withdraw(10, 0);
        //Zero withdrawal triggers the same as negative amounts
        assertEquals(-1.0, withdrawAmount, "Due to code logic, this triggers the negative/zero amount branch");
    }

    //Get account balance test cases
    //Test the account exists and return the correct balance
    @Test
    void testGetBalanceAccountExists() {
        double balance = bank.getAccountBalance(15);
        //Should show the existing account balance
        assertEquals(50.0, balance, "Returns the correct balance of an existing account.");
    }

    //Test showing the account doesn't exist
    @Test
    void testGetBalanceAccountNotFound() {
         assertThrows(NullPointerException.class, () -> {
             bank.getAccountBalance(99);
        }, "Currently throws NullPointerException for non-existent accounts; requirement not implemented in the bank management system original code");        
    }
}

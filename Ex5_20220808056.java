import java.util.Collection;
public class Ex5_20220808056 {
}
class Account{
    private String accountNumber;
    private double balance;
    Account(String accountNumber,double balance)throws InsufficientFundsException{
        if (balance < 0) {
            throw new InsufficientFundsException(balance);
        }
    this.accountNumber=accountNumber;
    this.balance=balance;
    }
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) throws InvalidTransactionException {
        if (amount < 0) {
            throw new InvalidTransactionException(amount);
        }
        balance += amount;
    }

    public void withdraw(double amount) throws InvalidTransactionException, InsufficientFundsException {
        if (amount < 0) {
            throw new InvalidTransactionException(amount);
        }
        if (balance < amount) {
            throw new InsufficientFundsException(balance, amount);
        }
        balance -= amount;
    }

    @Override
    public String toString() {
        return "Account: " + accountNumber + ", Balance: " + balance;
    }
}
class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(double balance) {
        super("Wrong balance: " + balance);
    }

    public InsufficientFundsException(double balance, double amount) {
        super("Required amount is " + amount + " but only " + balance + " remaining");
    }
}
 class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String accountNumber) {
        super("Account number " + accountNumber + " already exists");
    }
}
class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String accountNumber) {
        super("Account number " + accountNumber + " did not found");
    }
}
 class InvalidTransactionException extends Exception {
    public InvalidTransactionException(double amount) {
        super("Invalid amount: " + amount);
    }

    public InvalidTransactionException(InvalidTransactionException e, String message) {
        super(message + ":\n\t" + e.getMessage());
    }
}
class Customer {
    private String name;
    private Collection<Account> accounts;

    public Customer(String name, Collection<Account> accounts) {
        this.name = name;
        this.accounts = accounts;
    }

    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        throw new AccountNotFoundException(accountNumber);
    }

    public void addAccount(Account account) {
        try {
            getAccount(account.getAccountNumber());
            throw new AccountAlreadyExistsException(account.getAccountNumber());
        } catch (AccountNotFoundException e) {
            accounts.add(account);
            System.out.println(this);
            System.out.println("Added account: " + account.getAccountNumber() + " with " + account.getBalance());
        }
    }

    public void removeAccount(String accountNumber) throws AccountNotFoundException{
        Account accountToRemove = getAccount(accountNumber);
        if (accountToRemove==null){
            throw new AccountNotFoundException(accountNumber);
        }
        accounts.remove(accountToRemove);
    }

    public void transfer(String fromAccount, String toAccount, double amount) throws InvalidTransactionException {
        try {
            Account from = getAccount(fromAccount);
            Account to = getAccount(toAccount);
            from.withdraw(amount);
            to.deposit(amount);
        } catch (InvalidTransactionException e) {
            throw new InvalidTransactionException(e, "Cannot transfer funds from account " + fromAccount + " to account " + toAccount);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer ").append(name).append(":").append("\n");
        for (Account account : accounts) {
            sb.append("\t").append(account.toString()).append("\n");
        }
        return sb.toString();
    }
}




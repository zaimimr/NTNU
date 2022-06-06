import javax.persistence.*;


@Entity
public class Account {
    @Id
    private int account_number;
    private double balance;
    private String owner;

    @Version
    private int locking_field;

    public Account(){}

    public Account(int account_number, String owner) {
        this.account_number = account_number;
        this.balance = 0;
        this.owner = owner;
    }

    public int getAccountNumber() {
        return account_number;
    }

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    public void setAccountNumber(int account_number) {
        this.account_number = account_number;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        } else {
            balance -= amount;
            return true;
        }
    }

    public boolean add(double amount) {
        if (amount <= 0) {
            return false;
        } else {
            this.balance += amount;
            return true;
        }
    }

    @Override
    public String toString(){
        return "Accountnumber: " + account_number + "\n" + "Balance: " + balance + "\n" + "Owner: " + owner;
    }


}
import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String accountNumber;
    private String Owner;
    private String Password;
    private String Balance;

    public BankAccount(String Username, String Password, String accountNumber, String Balance) {
        Owner = Username;
        this.Password = Password;
        this.accountNumber = accountNumber;
        this.Balance = Balance;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setPassword(String password) {
        Password = password;
    }

    List<LoginLogout> loginLogoutList = new ArrayList<>();

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwner() {
        return Owner;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public boolean Checkpassword(String password) {
        return password.hashCode() == this.Password.hashCode();
    }
}

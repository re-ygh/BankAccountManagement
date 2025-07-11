import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
import java.util.regex.Pattern;


public class Main {
    Scanner input = new Scanner(System.in);

    //the account that is logged in is named user.
    BankAccount User = new BankAccount(null, null, null, null);


    //all users go to UserAccounts and will be saved there as an arrayList.
    List<BankAccount> UserAccounts = new ArrayList<>();

    public static void main(String[] args) {
        new Main();
    }


    //the Main method


    Main() {
        loadAccounts();

        try {
            while (true) {
                System.out.println("welcome to BAM\n1- create new Bank account \n2- Bank account management\n3- exit\n");
                int choice = input.nextInt();
                input.nextLine();
                switch (choice) {
                    case 1:
                        CreateAcccount();
                        break;
                    case 2:
                        ManageAccount();
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        System.out.println("invalid entry.please try again.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("invalid entry.please try again.");

            new Main();
        }
    }


    //The ManageAccount Methode that is used for the Main function of this program.


    private void ManageAccount() {
        String AccountNumber;
        FirstEntry:
        while (true) {

            UsernameLable:
            while (true) {
                System.out.println("------------------");
                System.out.println("Enter Account Number: (enter B for going back)");
                AccountNumber = input.nextLine();
                if (AccountNumber.equalsIgnoreCase("b")) {
                    System.out.println("------------------");
                    return;
                } else if (Pattern.compile("[a-zA-Z&&[^bB]]").matcher(AccountNumber).find()) {
                    System.out.println("invalid entry. please try again.");
                    continue UsernameLable;
                } else {
                    for (BankAccount user : UserAccounts) {
                        if (user.getAccountNumber().equals(AccountNumber)) {
                            break UsernameLable;
                        }
                    }
                }
                System.out.println("Account Number not found. please try again.");
            }

            System.out.println("Enter Password: \n(enter B for going back and X for exiting the Create Account menu)\n");
            String Password;

            PasswordLable:
            while (true) {
                Password = input.nextLine();
                switch (Password) {
                    case "b":
                    case "B":
                        System.out.println("------------------");
                        continue FirstEntry;
                    case "x":
                    case "X":
                        System.out.println("------------------");
                        return;
                    default:
                        for (BankAccount user : UserAccounts) {
                            if (user.getAccountNumber().equals(AccountNumber)) {
                                if (user.Checkpassword(Password)) {
                                    LoginSaver(user.getOwner());
                                    User.setAccountNumber(AccountNumber);
                                    User.setPassword(Password);
                                    break PasswordLable;
                                }

                                System.out.println("Wrong password.please try again.");
                            }
                        }
                }
            }


            inputLable:
            while (true) {
                // loading balance before displaying it.
                for (int i = 0; i < UserAccounts.size(); i++) {
                    if (UserAccounts.get(i).getAccountNumber().equals(AccountNumber)) {
                        User.setBalance(UserAccounts.get(i).getBalance());
                    }
                }
                System.out.println("------------------");
                System.out.println("Balance: " + User.getBalance() + "\n");
                System.out.println("1- Make a deposit\n2- Transaction\n3- Delete Account\n4- Buy Minutes(sharj pooli) for your phone\n5- Login and Logout reports\n6-Exit Account\nChoose Number:");
                String Choice;
                Choice = input.nextLine();
                if (Pattern.compile("a-zA-Z").matcher(Choice).find()) {
                    System.out.println("invalid entry.please try again.");
                    continue inputLable;
                }
                switch (Choice) {
                    case "1":
                        IncreaseBalance();
                        break;
                    case "2":
                        Transaction();
                        break;
                    case "3":
                        DeleteAccount();
                        break;
                    case "4":
                        ChooseOperatore();
                        break;
                    case "5":
                        SeeLogTime();
                        break;
                    case "6":
                        for (BankAccount user : UserAccounts) {
                            if (user.getAccountNumber().equals(AccountNumber)) {
                                if (user.Checkpassword(Password)) {
                                    LogoutSaver(user.getOwner());
                                }
                            }
                        }
                        return;
                    default:
                        System.out.println("invalid entry.please try again.");
                }
            }
        }
    }

    private void Transaction() {

        transactionlable:
        while (true) {
            try {

                System.out.println("------------------");
                System.out.println("Enter the account number you want to transfer money to:(B for back)\n");

                String transferToAccountNumber = "";
                transferToAccountNumber += input.nextLine();

                if (Pattern.compile("[a-zA-Z&&[^bB]]").matcher(transferToAccountNumber).find()) {
                    System.out.println("Account number only should contain numbers. please try again.\n");
                    continue transactionlable;
                }
                if (Pattern.compile("[bB]").matcher(transferToAccountNumber).find()) {
                    return;
                }
                for (BankAccount user : UserAccounts) {
                    if (user.getAccountNumber().equals(transferToAccountNumber)) {
                        System.out.println("------------------");
                        System.out.println("Account owner: " + user.getOwner());
                        System.out.println("\nyour balance is: " + User.getBalance() + "\n");
                        System.out.println("------------------\n");
                        transferlable:
                        while (true) {
                            System.out.println("enter the amount you like to transfer:(B for back.X for first menu.)\n");
                            String transferAmount = input.nextLine();
                            switch (transferAmount) {
                                case "b":
                                case "B":
                                    continue transactionlable;
                                case "x":
                                case "X":
                                    return;
                                default:
                                    if (Pattern.compile("[^bBxX]+&&[^\\d]").matcher(transferAmount).find()) {
                                        System.out.println("invalid entry.");
                                        System.out.println("------------------");
                                        continue transferlable;
                                    }
                                    break;
                            }

                            int intTransferAmount = Integer.parseInt(transferAmount);
                            int balance = Integer.parseInt(User.getBalance());

                            if (intTransferAmount > balance) {
                                System.out.println("your balance is not enough for this transaction. ");
                                System.out.println("------------------");
                                continue transferlable;
                            } else if (intTransferAmount <= 0) {
                                System.out.println("invalid entry. you can't transfer 0 or negative numbers.");
                                System.out.println("------------------");
                                continue transferlable;
                            } else {

                                passwordLable:
                                while (true) {
                                    System.out.println("------------------");
                                    System.out.println("Enter your password: (B for back.X for the first menu.)\n");
                                    String password = input.nextLine();
                                    switch (password) {
                                        case "b":
                                        case "B":
                                            continue transferlable;
                                        case "x":
                                        case "X":
                                            return;
                                        default:
                                            break;
                                    }
                                    if (!(User.Checkpassword(password))) {
                                        System.out.println("entered password does not match this account password.please try again.");
                                        continue passwordLable;
                                    } else {
                                        user.setBalance(Sum(user.getBalance(), transferAmount));
                                        saveAccounts();
                                        SaveBalance(subtract(User.getBalance(), transferAmount));
                                        return;
                                    }
                                }
                            }

                        }


                    }
                }
                System.out.println("Account Number not found. please try again.\n");
            } catch (NumberFormatException e) {
                System.out.println("invalid entry");
            }
        }
    }

    private void ChooseOperatore() {
        while (true) {
            System.out.println("------------------");
            System.out.println("1) Iransell\n2) Hamrah aval\n3) Rightel\n(B for back):\n");
            String operator = input.nextLine();
            switch (operator) {
                case "1":
                case "2":
                case "3":
                    if (buySharj()) return;
                    break;
                case "b":
                case "B":
                    return;
                default:
                    System.out.println("invalid entry. please try again.");
                    break;
            }
        }
    }

    private boolean buySharj() {
        BalanceLable:
        while (true) {
            System.out.println("------------------");
            System.out.println("1- 5000 toman\n2- 10000 toman\n3- 20000 toman\n4- 50000 toman\n(B for Back and X for the first menu):\n");
            String amount = input.nextLine();
            int After;
            String balance;
            switch (amount) {
                case "1":
                    After = Integer.parseInt(subtract(User.getBalance(), "5000"));
                    if (After < 0) {
                        System.out.println("your Balance is not enough.");
                        break BalanceLable;
                    }
                    balance = Integer.toString(After);
                    User.setBalance(balance);
                    SaveBalance(User.getBalance());
                    return true;
                case "2":
                    After = Integer.parseInt(subtract(User.getBalance(), "10000"));
                    if (After < 0) {
                        System.out.println("your Balance is not enough.");
                        break BalanceLable;
                    }
                    balance = Integer.toString(After);
                    User.setBalance(balance);
                    SaveBalance(User.getBalance());
                    return true;
                case "3":
                    After = Integer.parseInt(subtract(User.getBalance(), "20000"));
                    if (After < 0) {
                        System.out.println("your Balance is not enough.");
                        break BalanceLable;
                    }
                    balance = Integer.toString(After);
                    User.setBalance(balance);
                    SaveBalance(User.getBalance());
                    return true;
                case "4":
                    After = Integer.parseInt(subtract(User.getBalance(), "50000"));
                    if (After < 0) {
                        System.out.println("your Balance is not enough.");
                        break BalanceLable;
                    }
                    balance = Integer.toString(After);
                    User.setBalance(balance);
                    SaveBalance(User.getBalance());
                    return true;
                case "b":
                case "B":
                    break BalanceLable;
                case "x":
                case "X":
                    return true;
                default:
                    System.out.println("invalid entry. please try again.");
                    break;
            }
        }
        return false;
    }

    private void DeleteAccount() {
        passwordlable:
        while (true) {
            System.out.println("------------------");
            System.out.println("to delete this account; please enter your password:(B for back)\n");
            String password = input.nextLine();
            try {
                if (User.Checkpassword(password)) {
                    for (BankAccount user : UserAccounts) {
                        if (user.getAccountNumber().equals(User.getAccountNumber())) {
                            UserAccounts.remove(user);
                            saveAccounts();
                            System.out.println("Account deleted successfully\n");
                            System.out.println("------------------\n");
                            new Main();
                        }
                    }
                } else if (password.equals("b") || password.equals("B")) {
                    return;
                } else {
                    System.out.println("this password is not a match for this account.\n");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }


    private void IncreaseBalance() {
        while (true) {
            System.out.println("------------------");
            System.out.println("1- 5000 toman\n2- 10000 toman\n3- 20000 toman\n4- 50000 toman\nEnter the amount of money that you want to add to your account or choose from 1-4 for default amounts:(B for back)\n");
            String result;
            try {
                String amountOfMoney = input.nextLine();
                if (amountOfMoney.equals("b") || amountOfMoney.equals("B")) {
                    return;

                } else if (Pattern.compile("[a-zA-Z&&[^bB]]").matcher(amountOfMoney).find()) {

                    System.out.println("invalid entry.please try again");
                } else {


                    int intAmountOfMoney = Integer.parseInt(amountOfMoney);
                    if (intAmountOfMoney > 0) {

                        switch (intAmountOfMoney) {
                            case 1:
                                result = Sum(User.getBalance(), "5000");
                                User.setBalance(result);
                                SaveBalance(User.getBalance());
                                return;
                            case 2:
                                result = Sum(User.getBalance(), "10000");
                                User.setBalance(result);
                                SaveBalance(User.getBalance());
                                return;
                            case 3:
                                result = Sum(User.getBalance(), "20000");
                                User.setBalance(result);
                                SaveBalance(User.getBalance());
                                return;
                            case 4:
                                result = Sum(User.getBalance(), "50000");
                                User.setBalance(result);
                                SaveBalance(User.getBalance());
                                return;
                            default:
                                result = Sum(User.getBalance(), amountOfMoney);
                                User.setBalance(result);
                                SaveBalance(User.getBalance());
                                return;
                        }
                    } else {
                        System.out.println("invalid entry. you can't add 0 or less to anything. please try again.");

                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("invalid entry.");
            }
        }
    }


    //method for saving the balance.


    private void SaveBalance(String balance) {
        try {
            File myfile = new File("Users.txt");
            Gson gson = new Gson();

            if (myfile.exists()) {
                Scanner myFileReader = new Scanner(myfile);

                // Clear the UserAccounts ArrayList before reading from the file
                UserAccounts.clear();

                while (myFileReader.hasNextLine()) {
                    String usersData = myFileReader.nextLine();
                    BankAccount user = gson.fromJson(usersData, BankAccount.class);
                    UserAccounts.add(user);
                }
                myFileReader.close();

                // Update the balance for the specified user
                for (BankAccount i : UserAccounts) {
                    if (i.getAccountNumber().equals(User.getAccountNumber())) {
                        i.setBalance(balance);
                        break; // Break out of the loop once the user is found
                    }
                }

                FileWriter myFileWriter = new FileWriter(myfile);
                for (BankAccount i : UserAccounts) {
                    myFileWriter.write(gson.toJson(i) + "\n");
                }
                myFileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException: " + e.getMessage());
        }
    }


//The CreateAccount method.


    private void CreateAcccount() {
        String accountNumber = "";
        String username;

        UsernameLable:
        while (true) {
            System.out.println("------------------");
            System.out.println("username: (enter B for going back)");
            username = input.nextLine();
            switch (username) {
                case "b":
                case "B":
                    return;
                default:
                    if (Pattern.compile("\\d+").matcher(username).find()) {
                        System.out.println("invalid entry.please try again.");
                        continue UsernameLable;
                    }
                    if (Pattern.compile("[a-zA-Z]\\s[a-zA-Z]").matcher(username).find()) {
                        for (BankAccount i : UserAccounts) {
                            if (i.getOwner().equals(username)) {
                                System.out.println("this username has been chosen before. enter another username.");
                                continue UsernameLable;
                            }
                        }
                    } else {
                        System.out.println("invalid entry.please try again.");
                        continue UsernameLable;
                    }
                    System.out.println("Password: (enter B for going back and X for exiting the Create Account menu)");

                    while (true) {
                        String Password = input.nextLine();
                        if (Pattern.compile(".*.[a-zA-Z].*[0-9].*").matcher(Password).find()) {
                            Random random = new Random(new Date().getTime());
                            while (accountNumber.length() < 4) {

                                accountNumber += random.nextInt(10);
                            }
                            String Balance = "10000";
                            UserAccounts.add(new BankAccount(username, Password, accountNumber, Balance));
                            saveAccounts();
                            System.out.println("Your account number is: " + accountNumber + "\n");
                            System.out.println("------------------");
                            return;
                        } else {

                            System.out.println("invalid entry.please try again.");

                        }
                    }
            }
        }
    }


//method for login to be saved.

    private void LoginSaver(String owner) {
        try {
            File myFile = new File(owner + ".txt");
            FileWriter myFileWriter = new FileWriter(myFile, true);
            Gson gson = new Gson();

            LocalDate myDateObj = LocalDate.now();
            DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = myDateObj.format(myFormatObj1);

            LocalTime mytimeObj = LocalTime.now();
            DateTimeFormatter myFormatObj2 = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedtime = mytimeObj.format(myFormatObj2);

            boolean login = true;

            if (!myFile.exists()) {
                myFile.createNewFile(); // Create the file if it doesn't exist
            }
            User.loginLogoutList.clear();
            User.loginLogoutList.add(new LoginLogout(login, formattedtime, formattedDate));
            myFileWriter.write(gson.toJson(User.loginLogoutList) + "\n");
            myFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException, " + e.getMessage());
        }
    }


//method for logout to be saved.


    private void LogoutSaver(String owner) {
        try {
            File myFile = new File(owner + ".txt");
            FileWriter myFileWriter = new FileWriter(myFile, true);
            Gson gson = new Gson();

            LocalDate myDateObj = LocalDate.now();
            DateTimeFormatter myFormatObj1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = myDateObj.format(myFormatObj1);

            LocalTime mytimeObj = LocalTime.now();
            DateTimeFormatter myFormatObj2 = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedtime = mytimeObj.format(myFormatObj2);

            boolean login = false;

            if (!myFile.exists()) {
                myFile.createNewFile(); // Create the file if it doesn't exist
            }
            User.loginLogoutList.clear();
            User.loginLogoutList.add(new LoginLogout(login, formattedtime, formattedDate));
            myFileWriter.write(gson.toJson(User.loginLogoutList) + "\n");
            myFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException, " + e.getMessage());
        }
    }


//method for login and logout to be shown.


    private void SeeLogTime() {
        String owner = null;
        try {
            for (int i = 0; i < UserAccounts.size(); i++) {
                if (UserAccounts.get(i).getAccountNumber().equals(User.getAccountNumber())) {
                    owner = UserAccounts.get(i).getOwner();
                    break;
                }
            }
            File myFile = new File(owner + ".txt");
            Gson gson = new Gson();
            if (myFile.exists()) {
                Scanner myFileReader = new Scanner(myFile);
                while (myFileReader.hasNextLine()) {
                    System.out.println(myFileReader.nextLine());
                }
                myFileReader.close();
            } else {
                System.out.println("File did not exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException, " + e.getMessage());
        }

    }

//the SaveAccounts method.


    private void saveAccounts() {
        try {
            File myfile = new File("Users.txt");
            FileWriter myFileWriter = new FileWriter(myfile);
            Gson gson = new Gson();
            if (myfile.exists()) {
                for (int i = 0; i < UserAccounts.size(); i++) {
                    myFileWriter.write(gson.toJson(UserAccounts.get(i)));
                    if (i != UserAccounts.size()) {
                        myFileWriter.write("\n");
                    }
                }
                myFileWriter.close();
            } else {
                myfile.createNewFile();
                for (int i = 0; i < UserAccounts.size(); i++) {
                    myFileWriter.write(gson.toJson(UserAccounts.get(i)));
                    if (i != UserAccounts.size()) {
                        myFileWriter.write("\n");
                    }
                }
                myFileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException, " + e.getMessage());
        }
    }


//The loadAccounts method.


    private void loadAccounts() {
        try {
            File myFile = new File("Users.txt");
            Gson gson = new Gson();
            if (myFile.exists()) {
                Scanner myFileReader = new Scanner(myFile);
                UserAccounts.clear();
                while (myFileReader.hasNextLine()) {
                    UserAccounts.add(gson.fromJson(myFileReader.nextLine(), BankAccount.class));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException, " + e.getMessage());
        }
    }


    private String Sum(String Balance, String howMuch) {

        int num1 = Integer.parseInt(Balance);
        int num2 = Integer.parseInt(howMuch);

        int sum = num1 + num2;

        String result = String.valueOf(sum);

        return result;
    }

    private String subtract(String Balance, String howMuch) {

        int num1 = Integer.parseInt(Balance);
        int num2 = Integer.parseInt(howMuch);

        int subtract = num1 - num2;


        String result = String.valueOf(subtract);

        return result;
    }

}
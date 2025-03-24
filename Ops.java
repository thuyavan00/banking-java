import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Ops {
    

    private static ArrayList <User> Users = new ArrayList<>();
    static User loggedUser = null;
    static Scanner s = new Scanner(System.in);

    //utility function for admin and existing user check
    public static ArrayList<User> getUsers(){
        return Users;
    }

    //function to create a new user (case 1)
    public static void createUser( String username, String password){

        //check if user already exists
        if(Logger.checkExisting(username)){

            System.out.println("-------------------------");
            System.out.println("User already exists. Create a new username or login");
            return;
        }

        // create admin user
        if (Logger.isAdmin(username, password)){
            Users.add(new User(username, password, 'A'));
            return;
        }

        //create regular user
        Users.add(new User(username, password));
    }


    //for session management
    public static void setCurrentUser(User u){
        loggedUser = u;
    }


    //admin user operations screen
    public static void userOptions(){
        
        if (loggedUser.getUserType() == 'A'){

            boolean loop = true;
        
            while (loop){
                System.out.println("-------------------------");
                System.out.println(" 1. Show all users\n 2. View current Logged in user\n 3. View Top 10\n 4. Logout\n ");
		        int op = s.nextInt();
                switch(op){

                    case 1:
                    {
                        showUsers();
                        break;
                    }

                    case 2:
                    {
                        viewCurrentUser();
                        break;
                    }

                    case 3:
                    {
                        topTen();
                        break;
                    }
                    case 4:
                    {
                        Logger.logout();
                        loop=false;
                        break;
                    }
                } 
            }
        }
        else{
            userOperations();
        }
    }


    //function to display all users (admin function)
    public static void showUsers(){
        for ( User u : Users){
            u.showData();
        }
    }

    
    //session management utility for admin view
    public static void viewCurrentUser(){
        if (loggedUser != null){
            System.out.println(loggedUser.getName());
        }
        else{
            System.out.println("-------------------------");
            System.out.println("No user logged in");
        }
        
    }

    public static void topTen(){
        PriorityQueue <User> pq = new PriorityQueue <User>(Users.size(), new accountComparator());
        ArrayList <User> users = getUsers();
        User help;
        for  (User u : users){
            pq.add(u);
        }

        int top = 10;
        System.out.println("-------------------------");
        while ( top > 0 && !pq.isEmpty()){
            help = pq.poll();
            System.out.printf("Account number : %s \tUsername : %10s \tBalance : %d \n", help.getAccountNo(), help.getName(), help.getBalance());
            top-=1;
        }
    }

    //regular user operation screen
    public static void userOperations(){

        if (loggedUser == null){
            System.out.println("-------------------------");
            System.out.println("You need to login to perform transactions");
            return;
        }
        boolean loop = true;
        
        while (loop){
            System.out.println("-------------------------");
            System.out.println(" 1. Withdraw\n 2. Deposit\n 3. Transfer\n 4. View Balance\n 5. View Transactions\n 6. Logout\n ");
		    int op = s.nextInt();
            int amt;

            switch(op)
            {
                case 1:
                {   
                    System.out.println("-------------------------");
                    System.out.println("Enter amount to withdraw");
                    amt = s.nextInt();
                    withdraw(amt);
                    System.out.println("-------------------------");
                    System.out.println("Amount withdrawn successfully");
                    loggedUser.setTransaction("Withdrawl", amt);
                    break;
                }
                case 2:
                {
                    System.out.println("-------------------------");
                    System.out.println("Enter amount to Deposit");
                    amt = s.nextInt();
                    deposit(amt);
                    System.out.println("-------------------------");
                    System.out.println("Amount Deposited successfully");
                    loggedUser.setTransaction("Deposit", amt);
                    break;
                }
                case 3: 
                {
                    System.out.println("Enter Beneficiary Account number");
                    int benficiaryAccNo = s.nextInt();

                    if (getBenficiary(benficiaryAccNo) == null){
                        System.out.println("Enter valid account number");
                        break;
                    }

                    System.out.println("-------------------------");
                    System.out.println("Enter amount to Transfer");
                    amt = s.nextInt();
                    transfer(benficiaryAccNo, amt);
                    loggedUser.setTransaction("Transfer to " + benficiaryAccNo  + " from " + loggedUser.getAccountNo(), amt);
                    getBenficiary(benficiaryAccNo).setTransaction("Transfer from " + loggedUser.getAccountNo() + " to " + benficiaryAccNo, amt);
                    break;
                }
                case 4: 
                {
                    System.out.println(loggedUser.getBalance());
                    break;
                }

                case 5:
                {
                    viewTransactions();
                    break;
                }
                case 6: 
                {
                    Logger.logout();
                    loop=false;
                    break;
                }
            }
        } 
    }


    public static void viewTransactions(){
        ArrayList <ArrayList<Object>> trans = loggedUser.getTransactions();

        for (ArrayList<Object> a : trans){
            System.out.println(a);
        }
    }

    //withdraw
    public static int withdraw(int amt){
        
        int balance = loggedUser.getBalance();

        if (balance - amt < 0){
            System.out.println("Not enough balance to complete transaction");
            System.out.println("-------------------------");
            return balance;
        }
        else if (balance - amt < 1000){
            System.out.println("Balance below the limit");
            System.out.println("-------------------------");
            balance -= amt;
            loggedUser.setBalance(balance);
            return amt;
        }

        balance -= amt;
        loggedUser.setBalance(balance);
        return amt;

    }

    //overload method for benficiary account deposit
    public static void deposit(int acctNo, int amt){
        User benficiary = getBenficiary(acctNo);
        benficiary.setBalance(benficiary.getBalance() + amt);
    }

    //method to deposit to self
    public static void deposit(int amt){
        loggedUser.setBalance(loggedUser.getBalance() + amt);
    }

    //method to transfer funds
    public static void transfer(int bAccNo, int amt){
        amt = withdraw(amt);
        deposit(bAccNo, amt);
    }

    //utility to get beneficiary user object
    public static User getBenficiary(int accno){   
        for (User u : Users){
            if (u.getAccountNo() == accno){
                return u;
            }
        }
        return null;
    }

    
}


class accountComparator implements Comparator<User>{
    
    public int compare(User O1, User O2) {
        if (O1.getBalance() < O2.getBalance())
            return 1;
        else if (O1.getBalance() > O2.getBalance())
            return -1;
        return 0;
        }

}
import java.util.ArrayList;

public class User {

    private int id;
    private static int idmodder = -1;
    private int accno;
    private static int accnoprovider = 0;
    private String name;
    private String password;
    private int balance;
    private char uType;
    private int transidmodder = 0;
    private ArrayList <ArrayList<Object>> transactions = new ArrayList<ArrayList<Object>>();
    

    //constructor for new user
    public User (String name, String password) {
        this.id = ++idmodder;
        accnoprovider += 11011;
        this.accno = accnoprovider;
        this.name = name;
        this.password = Logger.encryptPass(password);
        this.balance = 10000;
        this.uType = 'U';
        this.transactions.add(initTransSetter());
    }


    public ArrayList<Object> initTransSetter(){
        ArrayList <Object> helper = new ArrayList<>();
        helper.add(++transidmodder);
        helper.add(accno);
        helper.add("Opening balance");
        helper.add(10000);
        helper.add(balance);

        return helper;
    }
    //overload constructor for admin user
    public User (String name, String password, char type){
        this.id = ++idmodder;
        this.name = name;
        this.password = Logger.encryptPass(password);
        this.uType = type;
    }

    public void showData(){
        System.out.println("-------------------------");
        System.out.println(id);
        System.out.println(accno);
        System.out.println(name);
        System.out.println(password);
        System.out.println(balance);
        System.out.println("-------------------------");
    }

    public String getName(){
        return name;
    }

    public String getPass(){
        return password;
    }

    public char getUserType(){
        return uType;
    }

    public int getAccountNo(){
        return accno;
    }

    public int getBalance(){
        return balance;
    }

    public void setBalance(int balance){
        this.balance = balance;
    }

    public void setTransaction(String s, int amt){
        ArrayList <Object> tHelper = new ArrayList<Object>();
        tHelper.add(++transidmodder);
        tHelper.add(accno);
        tHelper.add(s);
        tHelper.add(amt);
        tHelper.add(balance);

        transactions.add(tHelper);
    }

    public ArrayList <ArrayList<Object>> getTransactions(){
        return transactions;
    }

}
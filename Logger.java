import java.util.ArrayList;

public class Logger {
    
    static ArrayList<User> userList = Ops.getUsers();


    //method to login user
    public static boolean userLogin(String name, String password){
        
        String decryptedPass = encryptPass(password);


        for (User u: userList){
 
            if(u.getName().equals(name) && u.getPass().equals(decryptedPass)){
                
                Ops.setCurrentUser(u);
                System.out.println("-------------------------");
                System.out.println(u.getName() + " logged in"); 
                return true;
            }
        }

        System.out.println("Incorrect username or password"); 
        return false;
    
    }

    
    //method to logout user
    public static void logout(){
        Ops.setCurrentUser(null);
        System.out.println("-------------------------");
        System.out.println("You are logged out"); 
    }

    
    //method to check if created user is admin
    public static boolean isAdmin(String username, String password){
        if (username.equals("admin") && password.equals("admin")){
            return true;
        }

        return false;
    }

    //method to check is user already exists
    public static boolean checkExisting(String name){
        for (User u: userList){
            if (u.getName().equals(name)){
                return true;
            }
        }

        return false;
    }



    //method to encrypt password
    public static String encryptPass(String pass){

        char[] cpass = pass.toCharArray();
        int ipass;
        for ( int i =0; i < cpass.length; i++){
            if (cpass[i] == 'z'){
                cpass[i] = 'a';
            }
            else if (cpass[i] == 'Z'){
                cpass[i] = 'Z';
            }
            else if ( cpass[i] == '9'){
                cpass[i] = '0';
            }
            else{
                ipass = (int)cpass[i];
                ipass += 1;
                cpass[i] = (char)ipass;
            }
        }
        return String.valueOf(cpass);

    }

}

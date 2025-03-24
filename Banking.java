import java.util.Scanner;

public class Banking {
    public static void main(String[] args){
		
        boolean loop = true;
		
		while(loop)
		{
			System.out.println("-------------------------");
			System.out.println("\nChoose \n 1. Create User\n 2. User Login\n 3. Exit");
			Scanner s = new Scanner(System.in);
			int n = s.nextInt();
			
			switch(n)
			{
				case 1:
				{
					System.out.println("-----------Create New User--------------");
					System.out.println("Enter name : ");
					String name = s.next();
					System.out.println("Enter Password : ");
					String password = s.next();
					Ops.createUser(name, password);	
					break;
				}
				

				case 2:
				{
					boolean flag = false;
					int count = 1;

					while (!flag){
						if (count == 3) break;
						System.out.println("-----------Login--------------");
						System.out.println("Enter name : ");
						String name = s.next();
						System.out.println("Enter Password : ");
						String password = s.next();
						flag = Logger.userLogin(name, password);
						count += 1;
					}
					if (flag){
						Ops.userOptions();
					}
					
					break;
				}

				case 3:
				{
					System.out.println("-------------------------");
					System.out.println("\tThank you!");
					s.close();
					loop=false;
					break;
				}
				}
		}
    }
}

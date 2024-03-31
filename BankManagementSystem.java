import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.constant.ConstantDescs.NULL;

public class BankManagementSystem {

   static  final  String url = "jdbc:mysql://localhost:3306/bank";
   static  final String username="root";
   static  final String password="Gaya@123";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
           System.out.println(e.getMessage());
        }

        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            Scanner scanner =new Scanner(System.in);
            Account account=new Account(connection,scanner);
            AccountManager accountManager=new AccountManager(connection,scanner);
            User user =new User(connection,scanner);
            String email;
            long accountNumber;
            while (true){
                System.out.println("Welcome to BANK MANAGEMENT SYSTEM");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.println("Enter your Choice");
                int choice=scanner.nextInt();
                switch (choice){
                    case 1:
                           user.register();
                           break;
                    case 2:
                           email=user.login();
                           if(email!=NULL){
                               System.out.println();
                               System.out.println("User is logged in");
                               if(!account.account_exists(email)){
                                   System.out.println();
                                   System.out.println("1. Open Bank Account");
                                   System.out.println("2. Exit");
                                   if(scanner.nextInt()==1){
                                       accountNumber=account.open_account(email);
                                       System.out.println("Account Opened Successfully");
                                       System.out.println("Your account Number is"+" "+accountNumber);
                                   }else{
                                       break;
                                   }
                               }
                           }
                           long account_Number=account.get_accountNumber(email);
                           int choice2=scanner.nextInt();
                           while(choice2!=5){
                               System.out.println();
                               System.out.println("1. Debit Money");
                               System.out.println("2. Credit Money");
                               System.out.println("3. Transfer Money");
                               System.out.println("4. Get Balance");
                               System.out.println("5. Log Out");
                               choice2= scanner.nextInt();
                               switch (choice2){
                                   case 1:
                                          accountManager.debit_Money(account_Number);
                                          break;
                                   case 2:
                                          accountManager.credit_Money(account_Number);
                                          break;
                                   case 3:
                                          accountManager.transfer_Money(account_Number);
                                          break;
                                   case 4:
                                          accountManager.getBalance(account_Number);
                                          break;
                                   case 5:
                                          break;
                                   default:
                                          System.out.println("Invalid Choice");
                                          break;
                               }
                           }
                    case 3:
                          System.out.println("Thank You For Using BANK MANAGEMENT SYSTEM");
                          return;
                    default:
                          System.out.println("Invalid choice");
                }
            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

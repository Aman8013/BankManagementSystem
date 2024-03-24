import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

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


        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

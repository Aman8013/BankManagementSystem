import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Account {
    private  final Connection connection;
    private final Scanner scanner;

    public Account(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }

    public long open_account(String email){
         if(!account_exists(email)){
             System.out.println("Enter the name");
             String  name=scanner.nextLine();
             System.out.println("Enter the initial Amount");
             double balance=scanner.nextDouble();
             System.out.println("Enter the security pin");
             String securityPin=scanner.nextLine();
             String query="Insert into account(accountNumber,name,email,balance,securityPin) values(?,?,?,?,?)";
             try{
                 long accountNumber=generate_account(email);
                 PreparedStatement preparedStatement=connection.prepareStatement(query);
                 preparedStatement.setLong(1,accountNumber);
                 preparedStatement.setString(2,name);
                 preparedStatement.setString(3,email);
                 preparedStatement.setDouble(4,balance);
                 preparedStatement.setString(5,securityPin);
                 int rows= preparedStatement.executeUpdate();
                 if(rows>0){
                     System.out.println("Account Opened Successfully");
                     return accountNumber;
                 }else{
                     System.out.println("Failed to open Account");
                     return  -1;
                 }
             }catch(SQLException e){
                 System.out.println(e.getMessage());
             }
             return -1;
         }
         return -1;
    }
    public long get_accountNumber(String email){
        if(!account_exists(email)){
            System.out.println("No Account Exists for this Email");
            return -1;
        }
         String query="Select accountNumber from account where email=?";
         try{
             PreparedStatement preparedStatement=connection.prepareStatement(query);
             preparedStatement.setString(1,email);
             ResultSet resultSet= preparedStatement.executeQuery();
             if(resultSet.next()){
                 return resultSet.getLong("accountNumber");
             }else {
                 System.out.println("No Account Exists for this Email");
             }
         }catch(Exception e){
             System.out.println(e.getMessage());
         }
         return -1;
    }
    public long generate_account(String email){
         String query="Select accountNumber from account order by accountNumber DESC limit 1";
         try{
             PreparedStatement preparedStatement=connection.prepareStatement(query);
             ResultSet resultSet=preparedStatement.executeQuery();
             if(resultSet.next()){
                 long accountNumber=resultSet.getLong("accountNumber");
                 return accountNumber+1;
             }else{
                 return 10000100;
             }
         }catch(SQLException e){
             System.out.println(e.getMessage());
         }
         return 10000100;
    }
    public boolean account_exists(String email){
           String query="Select * from account where email=?";
           try{
               PreparedStatement preparedStatement=connection.prepareStatement(query);
               preparedStatement.setString(1,email);
               ResultSet resultSet=preparedStatement.executeQuery();
               return resultSet.next();
           }catch(SQLException e){
               System.out.println(e.getMessage());
           }
           return false;
    }

}

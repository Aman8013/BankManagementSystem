import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private final Connection connection;
    private final Scanner scanner;

    public AccountManager(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }
    void credit_Money(long accountNumber)throws SQLException{
        connection.setAutoCommit(false);
        System.out.println("Enter the Amount to be debited");
        double amount=scanner.nextDouble();
        System.out.println("Enter the Security Pin");
        String securityPin=scanner.nextLine();
        try{
            if(accountNumber!=0){
                String query="Select * from account where accountNumber=? and securityPin=?";
                PreparedStatement preparedStatement= connection.prepareStatement(query);
                preparedStatement.setLong(1,accountNumber);
                preparedStatement.setString(2,securityPin);
                ResultSet resultSet= preparedStatement.executeQuery();
                if(resultSet.next()){
                    String sql="update account set balance=balance+? where accountNumber=?";
                    PreparedStatement prepareStatement=connection.prepareStatement(sql);
                    preparedStatement.setDouble(1,amount);
                    preparedStatement.setLong(2,accountNumber);
                    int rows=preparedStatement.executeUpdate();
                    if(rows>0){
                        System.out.println("Amount RS"+" "+amount+" "+"is credited successfully");
                        connection.commit();
                        connection.setAutoCommit(true);
                        return;
                    }else {
                        System.out.println("Transaction failed!!!!!");
                        connection.rollback();
                        return;
                    }
                }
            }else{
                System.out.println("Invalid Pin");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        connection.setAutoCommit(true);
    }
    void debit_Money(long accountNumber)throws SQLException{
        connection.setAutoCommit(false);
        System.out.println("Enter the Amount to be debited");
        double amount=scanner.nextDouble();
        System.out.println("Enter the Security Pin");
        String securityPin=scanner.nextLine();
        try{
            if(accountNumber!=0){
                String query="Select * from account where accountNumber=? and securityPin=?";
                PreparedStatement preparedStatement= connection.prepareStatement(query);
                preparedStatement.setLong(1,accountNumber);
                preparedStatement.setString(2,securityPin);
                ResultSet resultSet= preparedStatement.executeQuery();
                if(resultSet.next()){
                    double curr_Amount= resultSet.getDouble("balance");
                    if(curr_Amount>=amount){
                        String sql="update account set balance=balance-? where accountNumber=?";
                        PreparedStatement prepareStatement=connection.prepareStatement(sql);
                        preparedStatement.setDouble(1,amount);
                        preparedStatement.setLong(2,accountNumber);
                        int rows=preparedStatement.executeUpdate();
                        if(rows>0){
                            System.out.println("Amount RS"+" "+amount+" "+"is debited successfully");
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        }else{
                            System.out.println("Transaction failed!!!!!");
                            connection.rollback();
                            return;
                        }
                    }else{
                        System.out.println("Insufficient Balance");
                    }
                }
            }else{
                System.out.println("Invalid Pin");
            }
        }catch(SQLException e){
           System.out.println(e.getMessage());
        }
        connection.setAutoCommit(true);
    }
    void transfer_Money(long accountNumber)throws  SQLException{
         connection.setAutoCommit(false);
         System.out.println("Enter the receiver account Number");
         long receiver_AccountNumber=scanner.nextLong();
         System.out.println("Enter the security Pin");
         String securityPin=scanner.nextLine();
         System.out.println("Enter the amount");
         double amount=scanner.nextDouble();
         if(accountNumber!=0 && receiver_AccountNumber!=0){
             String query="select * from account where accountNumber=? and securityPin=?";
             try{
                 PreparedStatement preparedStatement= connection.prepareStatement(query);
                 preparedStatement.setLong(1,accountNumber);
                 preparedStatement.setString(2,securityPin);
                 ResultSet resultSet= preparedStatement.executeQuery();
                 double curr_amount=resultSet.getDouble("balance");
                 if(resultSet.next()){
                     if(curr_amount>=amount) {
                         String debit_query = "update account set balance=balance-? where accountNumber=?";
                         String credit_query = "update account set balance=balance+? where accountNumber=?";
                         PreparedStatement preparedStatement1 = connection.prepareStatement(debit_query);
                         PreparedStatement preparedStatement2 = connection.prepareStatement(credit_query);
                         preparedStatement1.setDouble(1, amount);
                         preparedStatement1.setLong(2,accountNumber);
                         preparedStatement2.setDouble(1,amount);
                         preparedStatement2.setLong(2,receiver_AccountNumber);
                         int r1=preparedStatement1.executeUpdate();
                         int r2=preparedStatement2.executeUpdate();
                         if(r1>0 && r2>0){
                             System.out.println("Transaction is Successful");
                             connection.commit();
                             connection.setAutoCommit(true);
                             return;
                         }else{
                             System.out.println("Transaction failed!!!");
                             connection.rollback();
                             connection.setAutoCommit(true);
                             return;
                         }
                     }else{
                         System.out.println("Insufficient Balance");
                     }
                 }else{
                     System.out.println("Invalid Pin");
                 }
             }catch(SQLException e){
                 System.out.println(e.getMessage());
             }
         }
         connection.setAutoCommit(true);
    }
    void getBalance(long accountNumber)throws SQLException{
          System.out.println("Enter the Security Pin");
          String securityPin=scanner.nextLine();
          if(accountNumber!=0){
              try{
                  String query="Select * from account where accountNumber=? and securityPin=?";
                  PreparedStatement preparedStatement=connection.prepareStatement(query);
                  preparedStatement.setLong(1,accountNumber);
                  preparedStatement.setString(2,securityPin);
                  ResultSet resultSet= preparedStatement.executeQuery();
                  if(resultSet.next()){
                      double amount=resultSet.getDouble("balance");
                      System.out.println("Balance is"+" "+amount);
                  }else{
                      System.out.println("Invalid Pin");
                  }
              }catch(SQLException e){
                    System.out.println(e.getMessage());
              }
          }
    }
}

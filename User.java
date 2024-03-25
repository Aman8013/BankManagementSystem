import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private final Connection connection;
    private final Scanner scanner;

    public User(Connection connection,Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    void register(){
        System.out.println("Enter the name");
        String name=scanner.nextLine();
        System.out.println("Enter the password");
        String password=scanner.nextLine();
        System.out.println("Enter the email");
        String email=scanner.nextLine();
        if(user_exist(email)){
            System.out.println("User Already Exists");
            return;
        }
        try{
            String query="Insert into user(name,email,password) values(?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);
            int rows=preparedStatement.executeUpdate();
            if(rows>0){
                System.out.println("Register Successfully");
            }else{
                System.out.println("Failed to Register");
            }
        }catch(Exception e){
             System.out.println(e.getMessage());
        }
    }
    String login(){
        System.out.println("Enter the email");
        String email=scanner.nextLine();
        System.out.println("Enter the password");
        String password=scanner.nextLine();
        if(!user_exist(email)){
            System.out.println("User not Exists");
            System.out.println("Register First");
            return null;
        }
        try{
            String query="Select * from user where email=? and password=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return  email;
            }else{
                return null;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    boolean user_exist(String email){
         try{
             String query="Select * from user where email=?";
             PreparedStatement preparedStatement=connection.prepareStatement(query);
             preparedStatement.setString(1,email);
             ResultSet resultSet=preparedStatement.executeQuery();
             if(resultSet.next()){
                 return true;
             }else {
                 return false;
             }
         }catch (SQLException e){
             System.out.println(e.getMessage());
         }
         return false;
    }
}

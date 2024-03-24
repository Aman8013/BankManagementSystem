import java.sql.Connection;
import java.util.Scanner;

public class Account {
    private  Connection connection;
    private Scanner scanner;

    public Account(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }

}

import java.util.*;


import java.sql.*;
class BaseOp{
    Connection connection;
    Scanner scanner;

    public BaseOp(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner=scanner;
    }
}
class CreateOp extends BaseOp {
    public CreateOp(Connection connection, Scanner scanner) {
        super(connection,scanner);
    }
    public void performOperation(Scanner sc){
        //CREATE OPERATION
        System.out.println();
        String insertSql="INSERT INTO customers(first_name,last_name,email,phone,pan,balance) VALUES(?,?,?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(insertSql)){
            System.out.println("Enter first name: ");
            String first_name=sc.nextLine();
            System.out.println("Enter last name: ");
            String last_name=sc.nextLine();
            System.out.println("Enter email id:");
            String email=sc.nextLine();
            System.out.println("Enter phone number: ");
            String phone=sc.nextLine();
            System.out.println("Enter PAN number: ");
            String pan=sc.nextLine();
            System.out.println("Enter amount desposting to start your account: ");
            Double balance=sc.nextDouble();
	    System.out.println("Testing my cicd lab");
	    System.out.rpintln("Learning to make changes in a branch");
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4,phone);
            preparedStatement.setString(5, pan);
            preparedStatement.setDouble(6,balance);
            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Record Inserted Successfully");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
class ReadOp extends BaseOp{
    public ReadOp(Connection connection, Scanner scanner) {
        super(connection,scanner);
    }
    public void performOperation(){
        String readSql="SELECT * FROM customers";
        try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(readSql)){
            while(resultSet.next()){
                int customerId = resultSet.getInt("id");
                String customerFName = resultSet.getString("first_name");
                String customerLName = resultSet.getString("first_name");
                System.out.println("Customer Id: " + customerId + " Name: " + customerFName + " " + customerLName);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
class UpdateOp extends BaseOp{
    public UpdateOp(Connection connection, Scanner scanner) {
        super(connection,scanner);
    }
    public void performOperation(Scanner sc){
        System.out.println("Enter your customer ID: ");
        int customerIdToUpdate=sc.nextInt();
        System.out.println("Whart would you like to update?");
        System.out.println("Enter 1 to update email address. ");
        System.out.println("Enter 2 to update pan");
        System.out.println("Enter 3 to update phone number");
        int c=sc.nextInt();
        String task="";
        String nEntry="";
        String updateSql="UPDATE customer SET"+task+" = ? +WHERE id = ?";
        switch (c) {
            case 1:
                task="email";
                System.out.println("Enter new email");
                nEntry=sc.nextLine();
                break;
            case 2:
                task="pan";
                System.out.println("Enter new PAN");
                nEntry=sc.nextLine();
            break;
            case 3:
                task="phone";
                System.out.println("Enter new Phone Number");
                nEntry=sc.nextLine();
            break;
            default:
                break;
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement(updateSql)){
            preparedStatement.setString(1, nEntry);
            preparedStatement.setInt(2,customerIdToUpdate); 
            //System.out.println("Before execution");
            int rowsAffected = preparedStatement.executeUpdate();
            //System.out.println("After execution");
            if (rowsAffected>0){
                System.out.println("Updated Successfully");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
class DeleteOp extends BaseOp{
    public DeleteOp(Connection connection, Scanner scanner) {
        super(connection,scanner);
    }
    public void performOperation(Scanner sc){
        String deleteSql="DELETE FROM customers WHERE id=?";
        System.out.println("Enter the customer id to delete");
        int idToDelete=sc.nextInt();
        try(PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)){
            preparedStatement.setInt(1, idToDelete);
            int rowsAffected=preparedStatement.executeUpdate();
            if(rowsAffected>0){
                 System.out.println("Account Record deleted successfully.");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
public class myBank {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String jdbcUrl="jdbc:mysql://localhost:3306/banking_db";
        String username="root";
        String password="Wmk@2003";
        try (Connection connection = DriverManager.getConnection(jdbcUrl,username,password)){ 
            CreateOp cOp = new CreateOp(connection,scanner);
            ReadOp rOp = new ReadOp(connection,scanner);
            UpdateOp uOp = new UpdateOp(connection,scanner);
            DeleteOp dOp = new DeleteOp(connection,scanner);
            System.out.println("Welcome to JDBC Bank");
            System.out.println("Services :");
            System.out.println("1. Create an account");
            System.out.println("2. View your account");
            System.out.println("3. Update account information");
            System.out.println("4. Withdrawal ");
            System.out.println("5. Deposit");
            System.out.println("6. Delete your account");
            int ch=scanner.nextInt();
            switch (ch) {
                case 1:
                    System.out.println("Create a new account");
                    cOp.performOperation(scanner);
                    break;
                case 2:
                    System.out.println("View your account");
                    rOp.performOperation();
                    break;
                case 3:
                    System.out.println("Update account information");
                    uOp.performOperation(scanner);
                    break;
                case 4:
                    System.out.println("Withdrawal");
                    break;
                case 5: 
                    System.out.println("Deposit");
                    break;
                case 6:
                    System.out.println("Delete your account");
                    dOp.performOperation(scanner);
                default:
                    break;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

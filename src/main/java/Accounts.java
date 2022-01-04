
import java.io.*;
import java.sql.*;
import java.util.*;
import org.apache.log4j.*;

public class Accounts {

    public static void loadlog4j(){
        String logpath=System.getProperty("user.dir")+"/log4j.properties";
        PropertyConfigurator.configure(logpath);
    }

    Scanner sc = new Scanner(System.in);
    Random ra = new Random();
    int userAge;
    float balanceAmount = 0;
    float withdrawAmount = 0;
    String userName;
    String phoneNo;
    int accountNo;
    int password;
    public static Logger logger=null;
    public Accounts(){
        logger = Logger.getLogger(Accounts.class.getName());
    }

    static Connection connectionSQL() throws SQLException {
        String MySQLURL = "jdbc:mysql://localhost:3306/kritter?useSSL=false";
        String databseUserName = "root";
        String databasePassword = "Ad@8129405010";
        Connection con = null;
        con = DriverManager.getConnection(MySQLURL, databseUserName, databasePassword);
        try {
            con = DriverManager.getConnection(MySQLURL, databseUserName, databasePassword);
            if (con != null) {
                logger.info("Database connection is successful !!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    int checkFunction(float num) {
        if (num > 0) {
            return 1;
        } else {
            logger.error("Invalid entry!!");
            //System.out.println("Invaild entry ");
            return 0;
        }
    }

    enum Loan_percent {
        HOMELOAN(5), GOLDLOAN(8), AGRICULTURE(2), EDUCATION(3);

        private int value;

        private Loan_percent(int value) {
            this.value = value;
        }
    }

    void loan() {
        //update
        System.out.println("Loan details");
        int i = 1;
        int ln = 0;
        for (Loan_percent s : Loan_percent.values()) {
            System.out.println(i + "." + s + " " + s.value + "%");
            i = i + 1;
        }
        System.out.print("Enter your loan Type :");
        int x = sc.nextInt();
        i = 1;
        for (Loan_percent s : Loan_percent.values()) {
            if (x == i) {
                ln = s.value;
            }
            i = i + 1;
        }

        System.out.print("Enter the amount for loan :");
        int loan_amount = sc.nextInt();
        System.out.print("Enter the number of years :");
        int loan_years = sc.nextInt();
        float interest = (float) (loan_amount * ln / 100.0f * loan_years);
        float Total = loan_amount + interest;
        System.out.println("AMount to be repaid :" + Total);
    }

    void printDetails(int accountNo) throws IOException {
        FileReader fr=null;
        BufferedReader br=null;
        try {
            int tempAccountno = accountNo;
            String fileName = Integer.toString(tempAccountno) + ".txt";
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            int i;
            while ((i = br.read()) != -1) {
                System.out.print((char) i);
            }
        }catch(Exception e){
            e.printStackTrace();

        }
        finally {
            br.close();
            fr.close();
        }

    }
    /*
        System.out.println("\nName = " + userName);
        System.out.println("Age = " + userAge);
        System.out.println("Phone number = " + phoneNo);
        System.out.println("Balance = " + balanceAmount + "\n");
    }*/

    void createAccount() throws SQLException {
        System.out.print("\nEnter your name : ");
        userName = sc.nextLine();
        System.out.print("\nEnter your phone number : ");
        phoneNo = sc.nextLine();
        System.out.print("\nEnter your age : ");
        userAge = sc.nextInt();
        System.out.print("\nEnter the amount you wish to deposite : ");
        balanceAmount = sc.nextFloat();
        accountNo = ra.nextInt(123456);
        System.out.println("Your ac has been created. \nThis is your ac no : " + accountNo);
        password = ra.nextInt(9999);
        System.out.println("This is your password : " + password + "\nPlease save this for future reference");
        try {
            String fileName = Integer.toString(accountNo) + ".txt";
            File fileObject = new File(fileName);
            if (fileObject.createNewFile()) {
                FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.write("Name = " + userName +
                        "\nAge = " + userAge +
                        "\nBalance = " + balanceAmount +
                        "\nPhone No : " + phoneNo);
                logger.info("File created.");
                fileWriter.close();
            } else {
                logger.error("File already exits");
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Connection con = connectionSQL();
        try {
            if (con != null) {
                String sql = "INSERT INTO customer(accountNo, name, age, phoneNo, password, initalDeposit, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement statement = con.prepareStatement(sql);

                statement.setInt(1, accountNo);
                statement.setString(2, userName);
                statement.setInt(3, userAge);
                statement.setString(4, phoneNo);
                statement.setInt(5, password);
                statement.setFloat(6,balanceAmount);
                statement.setFloat(7, balanceAmount);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {

                    //Logging the information
                    logger.info("A new user was inserted successfully!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void withdraw(int accountNo) throws SQLException {
        int tempAc = accountNo;
        System.out.print("Enter amount to be withdrawed : ");
        withdrawAmount = sc.nextFloat();
        if (checkFunction(withdrawAmount) == 1) {
            if (balanceAmount > withdrawAmount) {
                balanceAmount = balanceAmount - withdrawAmount;
                System.out.println("Final balance = " + balanceAmount);
            }
        }
        Connection con = connectionSQL();
        try {
            if (con != null) {
                String sql = "update customer set balance=" + balanceAmount + ",withdraw=" + withdrawAmount + " where accountNo=" + tempAc;
                PreparedStatement statement = con.prepareStatement(sql);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {

                    //Logging the information
                    logger.info("Updated successfully!");
                    //Logger log = Logger.getLogger(Accounts.class.getName());
                    //log.info("Updated successfully!");
                    //System.out.println("Updated successfully!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert con != null;
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void deposit(int accountNo) throws SQLException {
        int tempAc = accountNo;
        System.out.print("Enter amount to deposit : ");
        withdrawAmount = sc.nextFloat();
        if (checkFunction(withdrawAmount) == 1) {
            balanceAmount = balanceAmount + withdrawAmount;
            System.out.println("Final balance = " + balanceAmount);
        }
        Connection con = connectionSQL();
        try {
            if (con != null) {
                String sql = "update customer set balance=" + balanceAmount + " where accountNo=" + tempAc;
                PreparedStatement statement = con.prepareStatement(sql);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {

                    //Logging the information
                    logger.info("Updated successfully!");
                    //Logger log = Logger.getLogger(Accounts.class.getName());
                    //log.info("Updated successfully!");
                    //System.out.println("Updated successfully!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert con != null;
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

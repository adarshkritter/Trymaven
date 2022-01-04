
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Scanner;

//import static com.company.Accounts.connectionSQL;

public class Main extends Accounts{
    Main(){
        logger= Logger.getLogger(Main.class.getName());
    }
    public static void main(String[] args) {
        loadlog4j();
        int switching;
        boolean loopingCheck = true;
        Scanner sc = new Scanner(System.in);
        ArrayList<Accounts> accountDetails = new ArrayList<Accounts>();
        Accounts acc;
        while (loopingCheck) {
            try {
                System.out.print("\n\nWelcome to A.S International Bank \n\nPress 1 for creating an account\n" +
                        "Press 2 for login\n" +
                        "Press 3 to exit\n" +
                        "Please enter your response : ");
                switching = sc.nextInt();

                switch (switching) {
                    case 1:
                        acc = new Accounts();
                        acc.createAccount();
                        accountDetails.add(acc);
                        break;
                    case 2:
                        System.out.print("Enter your account no : ");
                        int userId = sc.nextInt();
                        System.out.print("Enter your password : ");
                        int userPassword = sc.nextInt();
                        for (Accounts i : accountDetails) {
                            if ((i.accountNo == userId) && (i.password == userPassword)) {
                                while (loopingCheck) {
                                    System.out.println("\nWelcome " + i.userName);
                                    System.out.println("Press 1 for display your details\n" +
                                            "Press 2 to withdraw" +
                                            "\nPress 3 to deposite" +
                                            "\nPress 4 to know about loan details" +
                                            "\nPress 5 to exit" +
                                            "\nPlease enter your response : ");
                                    int checkVar = sc.nextInt();
                                    if (checkVar == 1) {
                                        i.printDetails(i.accountNo);
                                    } else if (checkVar == 2) {
                                        i.withdraw(i.accountNo);
                                    } else if (checkVar == 3) {
                                        i.deposit(i.accountNo);
                                    } else if (checkVar == 4) {
                                        i.loan();
                                    } else if (checkVar == 5) {
                                        break;
                                    } else {
                                        System.out.println("Invaild entry");
                                    }
                                    System.out.println("\nDo you wish to go back? (yes-1 | no-0) : ");
                                    int getting = sc.nextInt();
                                    if (getting != 1) loopingCheck = false;
                                }
                            } else {
                                logger.error("Invaild entry. Try again!!");
                                //System.out.println("Invaild entry. Please try again");
                            }
                        }
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        logger.error("Invaild entry.");
                        //System.out.println("Invalid entry");
                }
            } catch (Exception e) {
                logger.error("Invaild entry. Try again!!");
                //System.out.println("Invaild entry type!");
            }
            System.out.print("\nDo you wish to continue?\n Press 1 to continue\n Press 0 to exit\nEnter you option : ");
            sc.nextLine();
            int getting = sc.nextInt();
            if (getting != 1) loopingCheck = false;


        }
        logger.info("Logout successful");
        //System.out.println("Thank you for using our service. Have a nice day!");
    }
}
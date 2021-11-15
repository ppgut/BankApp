package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankApp {

    /**
    --- Designed for training purposes ---

    Simple bank application
    As long as it runs it stores the data of all users and all their accounts.
    Users can simply log in with their name.

    Elements used in this project:
    Scanner, Random, if else, switch case,
    'do while' loop, 'fori' loop, HashMap, ArrayList, printf, try/catch

     To add:
     - storage of data in files
     - logging in with username and password
    */

    public static void createAccountForUser(User user) {
        BankAccount account = new BankAccount();
        user.addAccount(account);
        System.out.printf("Account %s generated%n", account.getAccountId());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, User> users = new HashMap<>(); // key = username

        System.out.println("*** Welcome in our banking system!");

        String option;
        do {

            System.out.println("What's Your name (login)?"); // log in with username
            String customerName = scanner.nextLine();

            User user;
            String message;
            if (users.containsKey(customerName)) { // user already in database - has assigned id
                user = users.get(customerName);
                message = "Welcome back %s! Your client id is %s.\n";
            } else {
                user = new User(customerName); // create new users new randomly generated id
                users.put(customerName, user);
                message = "We are glad You joined our Bank %s! Your new client id is %s.\n";
            }
            System.out.printf(message, user.getName(), user.getClientId());

            // Bank menu
            do {
                System.out.println("""
                        
                        *** Bank: What would You like to do?
                        1. Create new account | 2. Open account menu | 3. Remove account | 4. Change user | 5. Exit""");
                option = scanner.nextLine();

                switch (option) {
                    case "1":
                        createAccountForUser(user);
                        break;
                    case "2":
                        if (user.countAccounts() == 0) {
                            String willingToCreateAccount;
                            do {
                                System.out.println("You don't have any account yet. Would You like to create one? (y/n)");
                                willingToCreateAccount = scanner.nextLine();
                                switch (willingToCreateAccount) {
                                    case "y" -> createAccountForUser(user);
                                    case "n" -> {
                                    } //nothing
                                    default -> System.out.println("Enter \"y\" for yes or \"n\" for no");
                                }
                            } while (!willingToCreateAccount.equals("y") && !willingToCreateAccount.equals("n"));
                        }

                        if (user.countAccounts() == 1) { // separate if statement to open account just created in previous if
                            user.getAccount(1).showMenu();
                        } else if (user.countAccounts() > 1) {
                            System.out.println("Which account would You like to check?");
                            user.getAccountsList();
                            int accountIndex = scanner.nextInt();
                            scanner.nextLine();
                            user.getAccount(accountIndex).showMenu();
                        }
                        break;
                    case "3":
                        if (user.countAccounts() == 0) {
                            System.out.println("There is no account to remove.");
                        } else if (user.countAccounts() == 1) {
                            user.removeAccount(1);
                        } else {
                            System.out.println("Which account would You like to remove?");
                            user.getAccountsList();
                            int accountIndex = scanner.nextInt();
                            scanner.nextLine();
                            user.removeAccount(accountIndex);
                        }
                        break;
                    case "4":
                    case "5":
                        break; // change user or exit application
                    default:
                        System.out.println("Wrong value, try again");
                        break;
                }
            } while (!option.equals("4") && !option.equals("5"));
            System.out.println("User logged out.");
            System.out.println();
        } while (!option.equals("5"));
        System.out.println("Thank You for using our system! See You next time!");
    }
}
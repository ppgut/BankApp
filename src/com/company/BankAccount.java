package com.company;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BankAccount {
    private double balance;
    private double lastTransaction;
    private String accountId;
    public static List<String> existingAccounts = new ArrayList<>();

    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    public BankAccount() {
        balance = 0;
        lastTransaction = 0;
        do {
            accountId = randomAccountId();
        } while (existingAccounts.contains(accountId));
        existingAccounts.add(accountId);
    }

    private static String randomAccountId(){
        Random rand = new Random();
        return String.format("%03d-%03d",rand.nextInt(1000),rand.nextInt(1000));
    }

    public String getAccountId() {
        return accountId;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            lastTransaction = amount;
            System.out.println(currencyFormatter.format(amount) + " deposited");
        } else {
            System.out.println("Nothing deposited");
        }
    }

    public void withdraw(double amount) {
        if (balance == 0) {
            getBalance();
            return;
        }
        if (amount > balance) {
            System.out.println("Not enough money on Your account");
            getBalance();

            Scanner scanner = new Scanner(System.in);
            String choice;
            do {
                System.out.println("Would You like to withdraw " + currencyFormatter.format(balance) +" (y/n)?");
                choice = scanner.nextLine();
                switch (choice){
                    case "y" -> amount = balance;
                    case "n" -> amount = 0;
                }
            } while (!choice.equals("y") && !choice.equals("n"));
        }
        if (amount > 0) {
            balance -= amount;
            lastTransaction = -amount;
            System.out.println(currencyFormatter.format(amount) + " withdrawn");
        } else if (amount <= 0) {
            System.out.println("Nothing withdrawn");
        }
    }

    public void getBalance() {
        System.out.println("Balance of Your account: " + currencyFormatter.format(balance));
    }

    public void lastTransaction(){
        if (lastTransaction > 0) {
            System.out.println("Last transaction: " + currencyFormatter.format(lastTransaction) + " deposited");
        } else if (lastTransaction < 0) {
            System.out.println("Last transaction: " + currencyFormatter.format(-lastTransaction) + " withdrawn");
        } else {
            System.out.println("No transaction occurred");
        }
    }

    public void showMenu() {
        String option;
        Scanner scanner = new Scanner(System.in);

        System.out.printf("Account %s menu opened%n", accountId);

        do {
            System.out.printf("""
                
                *** Account %s: What would You like to do?
                1. Check balance | 2. Deposit | 3. Withdraw | 4. Check previous transaction | 5. Exit%n""", accountId);
            option = scanner.nextLine();

            switch (option) {
                case "1" -> getBalance();
                case "2" -> {
                    System.out.println("How much would You like to deposit?");
                    double amountToDeposit = 0;
                    try {
                        amountToDeposit = scanner.nextDouble();
                    } catch (Exception e) {
                        System.out.println("Invalid value");
                    }
                    scanner.nextLine();
                    deposit(amountToDeposit);
                }
                case "3" -> {
                    if (balance > 0) {
                        System.out.println("How much would You like to withdraw?");
                        double amountToWithdraw = scanner.nextDouble();
                        scanner.nextLine();
                        withdraw(amountToWithdraw);
                    } else {
                        getBalance();
                    }
                }
                case "4" -> lastTransaction();
                case "5" -> {} //exit account menu
                default -> System.out.println("Invalid option! Please enter again.");
            }
        } while (!option.equals("5"));
        System.out.println("Account menu closed");
    }

}
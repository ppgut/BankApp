package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User {
    private final String name;
    private String clientId;
    private final List<BankAccount> accounts = new ArrayList<>();
    public static final List<String> occupiedIDs = new ArrayList<>();

    public User(String name) {
        this.name = name;
        clientId = randomClientId();
        do {
            clientId = randomClientId();
        } while (occupiedIDs.contains(clientId));
        occupiedIDs.add(clientId);
    }

    private String randomClientId(){
        Random rand = new Random();
        return String.format("%03d", rand.nextInt(1000));
    }

    public String getName() {return name;}

    public String getClientId() {return clientId;}

    public void getAccountsList() {
        if (accounts.size() > 0) {
            for (int i = 0; i < accounts.size(); i++) {
                System.out.println((i+1)  + ". " + accounts.get(i).getAccountId());
            }
        } else {
            System.out.println("No accounts.");
        }
    }

    public int countAccounts(){
        return accounts.size();
    }

    public BankAccount getAccount(int accountIndex) {
        BankAccount account = null;
        try {
            account = accounts.get(accountIndex - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public void removeAccount(int accountIndex) {
        try {
            String accountNumber = accounts.get(accountIndex - 1).getAccountId();
            accounts.remove(accountIndex - 1);
            System.out.printf("Account %s removed.%n", accountNumber);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Account NOT removed!");
        }
    }
}

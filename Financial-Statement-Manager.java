import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

class Transaction {
    String date;
    String description;
    double amount;
    String type;

    Transaction(String date, String description, double amount, String type) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }
}

class Account {
    List<Transaction> transactions = new ArrayList<>();

    void addTransaction(Transaction t) {
        transactions.add(t);
    }

    double getBalance() {
        double balance = 0;
        for (Transaction t : transactions) {
            if (t.type.equalsIgnoreCase("credit")) {
                balance += t.amount;
            } else if (t.type.equalsIgnoreCase("debit")) {
                balance -= t.amount;
            }
        }
        return balance;
    }

    void viewTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        System.out.printf("%-15s %-25s %-10s %-10s\n", "Date", "Description", "Type", "Amount");
        System.out.println("---------------------------------------------------------------");
        for (Transaction t : transactions) {
            System.out.printf("%-15s %-25s %-10s $%-10.2f\n", t.date, t.description, t.type, t.amount);
        }
    }

    void generateIncomeStatement() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions available to generate income statement.");
            return;
        }
        double revenue = 0;
        double expenses = 0;
        System.out.println("\n================================ Income Statement ================================" );
        System.out.printf("%-15s %-25s %-10s %-10s\n", "Date", "Description", "Type", "Amount");
        System.out.println("----------------------------------------------------------------------------------");
        for (Transaction t : transactions) {
            System.out.printf("%-15s %-25s %-10s $%-10.2f\n", t.date, t.description, t.type, t.amount);
            if (t.type.equalsIgnoreCase("credit")) {
                revenue += t.amount;
            } else if (t.type.equalsIgnoreCase("debit")) {
                expenses += t.amount;
            }
        }
        double profit = revenue - expenses;
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("%-52s $%-10.2f\n", "Total Revenue:", revenue);
        System.out.printf("%-52s $%-10.2f\n", "Total Expenses:", expenses);
        System.out.printf("%-52s $%-10.2f\n", "Net Profit:", profit);
        System.out.println("==================================================================================");
    }
}

public class FinancialStatementManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Account account = new Account();
        int choice = -1;

        do {
            System.out.println("\n--- Financial Statement Management System ---");
            System.out.println("1. Add Transaction");
            System.out.println("2. View All Transactions");
            System.out.println("3. View Account Balance");
            System.out.println("4. Generate Income Statement");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                System.out.print("Enter your choice: ");
                sc.nextLine();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    String date;
                    do {
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        date = sc.nextLine();
                        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                        }
                    } while (!date.matches("\\d{4}-\\d{2}-\\d{2}"));

                    System.out.print("Enter description: ");
                    String desc = sc.nextLine();

                    Double amt = null;
                    while (amt == null) {
                        System.out.print("Enter amount: ");
                        if (sc.hasNextDouble()) {
                            amt = sc.nextDouble();
                            sc.nextLine();
                        } else {
                            System.out.println("Invalid amount. Must be a number.");
                            System.out.println("Please try again.");
                            sc.nextLine();
                        }
                    }

                    String type;
                    do {
                        System.out.print("Enter type (credit/debit): ");
                        type = sc.nextLine();
                        if (!type.equalsIgnoreCase("credit") && !type.equalsIgnoreCase("debit")) {
                            System.out.println("Invalid type. Must be 'credit' or 'debit'. Please try again.");
                        }
                    } while (!type.equalsIgnoreCase("credit") && !type.equalsIgnoreCase("debit"));

                    account.addTransaction(new Transaction(date, desc, amt, type));
                }
                case 2 -> account.viewTransactions();
                case 3 -> System.out.printf("Current Balance: $%.2f\n", account.getBalance());
                case 4 -> account.generateIncomeStatement();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please enter a number between 0 and 4.");
            }
        } while (choice != 0);
        sc.close();
    }
}

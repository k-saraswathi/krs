import java.io.*;
import java.util.*;

class Expense {
    private Date date;
    private String category;
    private double amount;

    public Expense(Date date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    // Getters and setters
    public Date getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Category: " + category + ", Amount: $" + amount;
    }
}

class ExpenseTracker {
    private List<Expense> expenses;

    public ExpenseTracker() {
        expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void listExpenses() {
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    public void saveExpensesToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Expense expense : expenses) {
                writer.println(expense.getDate() + "," + expense.getCategory() + "," + expense.getAmount());
            }
        } catch (IOException e) {
            System.err.println("Error saving expenses to file: " + e.getMessage());
        }
    }

    public void loadExpensesFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                Date date = new Date(parts[0]); // Example: parsing date from string, you may need to implement this
                String category = parts[1];
                double amount = Double.parseDouble(parts[2]);
                Expense expense = new Expense(date, category, amount);
                expenses.add(expense);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ExpenseTracker tracker = new ExpenseTracker();

        // Adding some sample expenses
        tracker.addExpense(new Expense(new Date(), "Food", 25.50));
        tracker.addExpense(new Expense(new Date(), "Transportation", 15.75));

        // Listing expenses
        System.out.println("List of expenses:");
        tracker.listExpenses();

        // Saving expenses to file
        tracker.saveExpensesToFile("expenses.txt");

        // Clearing expenses and loading from file
        tracker = new ExpenseTracker();
        tracker.loadExpensesFromFile("expenses.txt");

        // Listing loaded expenses
        System.out.println("\nList of expenses after loading from file:");
        tracker.listExpenses();
    }
}

import java.io.*;
import java.util.*;

class Expense {
    double amount;
    String category;
    String note;

    public Expense(double amount, String category, String note) {
        this.amount = amount;
        this.category = category;
        this.note = note;
    }

    public String toString() {
        return "₹" + amount + " | " + category + " | " + note;
    }

    public String toFileString() {
        return amount + "," + category + "," + note;
    }

    public static Expense fromFileString(String line) {
        String[] parts = line.split(",", 3);
        return new Expense(Double.parseDouble(parts[0]), parts[1], parts[2]);
    }
}

public class ExpenseTracker {
    static ArrayList<Expense> expenses = new ArrayList<>();
    static final String FILE_NAME = "expenses.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadExpenses();
        int choice;
        do {
            System.out.println("\n--- Personal Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: addExpense(); break;
                case 2: viewExpenses(); break;
                case 3: saveExpenses(); System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 3);
    }

    static void addExpense() {
        System.out.print("Enter amount: ₹");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter category (Food, Travel, etc.): ");
        String category = sc.nextLine();
        System.out.print("Enter a note: ");
        String note = sc.nextLine();

        Expense e = new Expense(amount, category, note);
        expenses.add(e);
        System.out.println("✅ Expense added!");
    }

    static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses to show.");
            return;
        }

        System.out.println("\nYour Expenses:");
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    static void saveExpenses() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Expense e : expenses) {
                writer.write(e.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving expenses.");
        }
    }

    static void loadExpenses() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                expenses.add(Expense.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading expenses.");
        }
    }
}


package humanresourcemanagement;

import humanresourcemanagement.AB.PayrollAB;
import humanresourcemanagement.model.Payroll;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PayrollManagement {

    public static void managePayrolls(Scanner scanner, PayrollAB payrollAB) {
        int choice;
        do {
            System.out.println("1. Add payroll");
            System.out.println("2. Update payroll");
            System.out.println("3. V");
            System.out.println("4. View all payroll");
            System.out.println("0. Return");
            System.out.print("Choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number!");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addPayroll(scanner, payrollAB);
                case 2 -> updatePayroll(scanner, payrollAB);
                case 3 -> deletePayroll(scanner, payrollAB);
                case 4 -> getAllPayrolls(payrollAB);
                case 0 -> System.out.println("Return to main menu.");
                default -> System.out.println("Invalid selection.");
            }
        } while (choice != 0);
    }

    private static void addPayroll(Scanner scanner, PayrollAB payrollAB) {
        System.out.print("Employee ID: ");
        int employeeId = scanner.nextInt();
        System.out.print("Month: ");
        int month = scanner.nextInt();
        System.out.print("Year: ");
        int year = scanner.nextInt();
        System.out.print("Total working hours: ");
        int totalHours = scanner.nextInt();
        System.out.print("Total salary: ");
        int totalSalary = scanner.nextInt();

        Payroll payroll = new Payroll(0, employeeId, month, year, totalHours, totalSalary);

        try {
            payrollAB.add(payroll);
            System.out.println("Payroll table has been added.");
        } catch (SQLException e) {
            System.err.println(" Error adding payroll table: " + e.getMessage());
        }
    }

    private static void updatePayroll(Scanner scanner, PayrollAB payrollAB) {
        System.out.print("Payroll ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Payroll existing = payrollAB.getById(id);
            if (existing == null) {
                System.out.println("No payroll table with this ID found.");
                return;
            }

            System.out.print("New total hours: ");
            int totalHours = scanner.nextInt();
            System.out.print("New total salary: ");
            int totalSalary = scanner.nextInt();

            Payroll updated = new Payroll(id, existing.getEmployeeID(), existing.getMonth(), existing.getYear(), totalHours, totalSalary);
            payrollAB.update(updated);
            System.out.println("Payroll has been updated.");
        } catch (SQLException e) {
            System.err.println("Payroll update error: " + e.getMessage());
        }
    }

    private static void getAllPayrolls(PayrollAB payrollAB) {
        try {
            List<Payroll> payrolls = payrollAB.getAll();
            if (payrolls.isEmpty()) {
                System.out.println("No payroll data available.");
            } else {
                System.out.println("Payroll List: ");
                for (Payroll p : payrolls) {
                    System.out.println(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving payroll list: " + e.getMessage());
        }
    }

    private static void deletePayroll(Scanner scanner, PayrollAB payrollAB) {
        System.out.print("Enter the payroll ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Payroll existing = payrollAB.getById(id);
            if (existing == null) {
                System.out.println("No payroll with this ID found.");
                return;
            }

            payrollAB.delete(id);
            System.out.println("The payroll has been deleted.");
        } catch (SQLException e) {
            System.err.println(" Error deleting payroll: " + e.getMessage());
        }
    }
}
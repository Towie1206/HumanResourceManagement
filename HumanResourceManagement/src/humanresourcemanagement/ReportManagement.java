package humanresourcemanagement;

import humanresourcemanagement.AB.*;
import humanresourcemanagement.model.Employee;
import humanresourcemanagement.model.Payroll;
import java.sql.SQLException;
import java.util.Scanner;

public class ReportManagement {

    public static void manageReports(Scanner scanner, EmployeeAB employeeAB, PayrollAB payrollAB) {
        int choice = -1;
        do {
            System.out.println("1. Employee statistics");
            System.out.println("2. Monthly total salary report");
            System.out.println("0. Return");
            System.out.print("Choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number!");
                scanner.next();
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 ->
                    printEmployeeStatistics(employeeAB);
                case 2 ->
                    printMonthlyPayrollReport(payrollAB);
                case 0 -> {
                    System.out.println("Return to main menu.");

                }
                default ->
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void printEmployeeStatistics(EmployeeAB employeeAB) {
        try {
            Integer totalEmployees = employeeAB.getTotalEmployeeCount();
            System.out.println("Total number of employees in the company: "
                    + (totalEmployees != null ? totalEmployees : "Data not available"));
        } catch (Exception e) {
            System.err.println("Error while counting employee numbers: " + e.getMessage());
        }
    }

    private static void printMonthlyPayrollReport(PayrollAB payrollAB) {
        try {
            Double totalSalary = payrollAB.getTotalMonthlySalary();
            System.out.println("Total monthly salary: "
                    + (totalSalary != null ? totalSalary : "Data not available"));
        } catch (Exception e) {
            System.err.println("Error reporting total salary: " + e.getMessage());
        }
    }
}


package humanresourcemanagement;

import humanresourcemanagement.AB.EmployeeAB;
import humanresourcemanagement.model.Employee;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagement {

    public static void manageEmployees(Scanner scanner, EmployeeAB employeeAB) {
        int choice = -1;
        do {
            System.out.println("1. Add employee");
            System.out.println("2. Update employee");
            System.out.println("3. Delete employee");
            System.out.println("4. View employee information");
            System.out.println("5. View all employees");
            System.out.println("0. Return");
            System.out.print("Choose: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number!");
                scanner.next();
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 ->
                    addEmployee(scanner, employeeAB);
                case 2 ->
                    updateEmployee(scanner, employeeAB);
                case 3 ->
                    deleteEmployee(scanner, employeeAB);
                case 4 ->
                    getEmployee(scanner, employeeAB);
                case 5 ->
                    getAllEmployees(employeeAB);
                case 0 ->
                    System.out.println("Return to main menu.");
                default ->
                    System.out.println("Invalid selection.");
            }
        } while (choice != 0);
    }

    private static void addEmployee(Scanner scanner, EmployeeAB employeeAB) {
        System.out.print("Employee name: ");
        String name = scanner.nextLine();
        System.out.print("Department ID:  ");
        int departmentId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Position:  ");
        String position = scanner.nextLine();
        System.out.print("Salary: ");
        int salary = scanner.nextInt();

        Employee employee = new Employee(name, departmentId, position, salary);
        try {
            employeeAB.add(employee);
            System.out.println("Employee has been added.");
        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
        }
    }

    private static void updateEmployee(Scanner scanner, EmployeeAB employeeAB) {
        System.out.print("Employee ID want to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Employee existingEmployee = employeeAB.getById(id);
            if (existingEmployee == null) {
                System.out.println("No employee with this ID found.");
                return;
            }

            System.out.print("New name:  ");
            String name = scanner.nextLine();
            System.out.print("New department ID: ");
            int departmentId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("New position:  ");
            String position = scanner.nextLine();
            System.out.print("New salary: ");
            int salary = scanner.nextInt();

            Employee updatedEmployee = new Employee(id, name, departmentId, position, salary);
            employeeAB.update(updatedEmployee);
            System.out.println("Employee has been updated.");
        } catch (SQLException e) {
            System.err.println("Employee update error: " + e.getMessage());
        }
    }

    private static void getEmployee(Scanner scanner, EmployeeAB employeeAB) {
        System.out.print("Enter the employee ID to view: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Employee employee = employeeAB.getById(id);
            if (employee != null) {
                System.out.println("Employee information:");
                System.out.println(employee);
            } else {
                System.out.println("Employee with this ID not found.");
            }
        } catch (SQLException e) {
            System.err.println(" Error retrieving employees: " + e.getMessage());
        }
    }

    private static void getAllEmployees(EmployeeAB employeeAB) {
        try {
            List<Employee> employees = employeeAB.getAll();
            if (employees.isEmpty()) {
                System.out.println("There are no employees.");
            } else {
                System.out.println("List of employees:");
                for (Employee emp : employees) {
                    System.out.println(emp);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving list of employees: " + e.getMessage());
        }
    }

    private static void deleteEmployee(Scanner scanner, EmployeeAB employeeAB) {
        System.out.print("Enter the employee ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Employee existingEmployee = employeeAB.getById(id);
            if (existingEmployee == null) {
                System.out.println("No employee with this ID found");
                return;
            }

            employeeAB.delete(id);
            System.out.println("Delete employee successfull.");
        } catch (SQLException e) {
            System.err.println("Error when deleting employee: " + e.getMessage());
        }
    }

}

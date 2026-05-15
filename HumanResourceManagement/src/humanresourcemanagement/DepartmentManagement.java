
package humanresourcemanagement;
import humanresourcemanagement.AB.DepartmentAB;
import humanresourcemanagement.model.Department;
import humanresourcemanagement.model.Employee;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
public class DepartmentManagement {
    public static void manageDepartments(Scanner scanner, DepartmentAB departmentAB) {
        int choice = -1 ;
        do {
            System.out.println("1. Add department");
            System.out.println("2. Delete department");
            System.out.println("3. View all departments");
            System.out.println("4. View list of employees by department");
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
                case 1 -> addDepartment(scanner, departmentAB);
                case 2 -> deleteDepartment(scanner, departmentAB);
                case 3 -> getAllDepartments(departmentAB);
                case 4 -> getEmployeesByDepartment(scanner, departmentAB);
                case 0 -> System.out.println("Return to main menu.");
                default -> System.out.println("Invalid selection.");
            }
        } while (choice != 0);
    }
    private static void addDepartment(Scanner scanner, DepartmentAB departmentAB) {
        System.out.print("Department name: ");
        String name = scanner.nextLine();

        Department department = new Department(name);
        try {
            departmentAB.add(department);
            System.out.println("Department has been added.");
        } catch (SQLException e) {
            System.err.println(" Error adding department:  " + e.getMessage());
        }
    }
    private static void deleteDepartment(Scanner scanner, DepartmentAB departmentAB) {
        System.out.print("Department ID to delete:  ");
        if (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid ID!");
            scanner.next();
            return;
        }
        int departmentId = scanner.nextInt();
        scanner.nextLine();

        try {
            Department existingDepartment = departmentAB.getById(departmentId);
            if (existingDepartment == null) {
                System.out.println("Department with this ID not found.");
                return;
            }

            departmentAB.delete(departmentId);
            System.out.println(" Department deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting department: " + e.getMessage());
        }
    }
    private static void getAllDepartments(DepartmentAB departmentAB) {
        try {
            List<Department> departments = departmentAB.getAll();
            if (departments.isEmpty()) {
                System.out.println("No departments.");
            } else {
                System.out.println("List of departments:");
                for (Department department : departments) {
                    System.out.println(department);
                }
            }
        } catch (SQLException e) {
            System.err.println(" Error viewing list of departments:  " + e.getMessage());
        }
    }
    private static void getEmployeesByDepartment(Scanner scanner, DepartmentAB departmentAB) {
        System.out.print("Department ID to view employees:  ");
        if (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid ID!");
            scanner.next();
            return;
        }
        int departmentId = scanner.nextInt();
        scanner.nextLine();
        
        try {
            List<Employee> employees = departmentAB.getEmployeesByDepartment(departmentId);
            if (employees.isEmpty()) {
                System.out.println("There are no employees in this department.");
            } else {
                for (Employee employee : employees) {
                    System.out.println(employee);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error viewing employee list:  " + e.getMessage());
        }
    }
}


package humanresourcemanagement;

import humanresourcemanagement.AB.TimekeepingAB;
import humanresourcemanagement.model.Timekeeping;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimekeepingManagement {

    public static void manageTimekeepings(Scanner scanner, TimekeepingAB timekeepingAB) {
        int choice;
        do {
            System.out.println("1. Add timekeeping");
            System.out.println("2. Update timekeeping");
            System.out.println("3. Delete timekeeping");
            System.out.println("4. View all timekeeping");
            System.out.println("0. Return");
            System.out.print("Choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number!");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addTimekeeping(scanner, timekeepingAB);
                case 2 -> updateTimekeeping(scanner, timekeepingAB);
                case 3 -> deleteTimekeeping(scanner, timekeepingAB);
                case 4 -> getAllTimekeepings(timekeepingAB);
                case 0 -> System.out.println("- Back to main menu. -");
                default -> System.out.println(" Invalid Selection! ");
            }
        } while (choice != 0);
    }

    private static void addTimekeeping(Scanner scanner, TimekeepingAB timekeepingAB) {
        System.out.print("Employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("WorkDate (YYYY-MM-DD): ");
        String workDate = scanner.next();
        System.out.print("HoursWork : ");
        int hoursWorked = scanner.nextInt();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date utilDate = dateFormat.parse(workDate);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

          
            Timekeeping timekeeping = new Timekeeping(0, employeeId, sqlDate, hoursWorked);

            
            try {
                timekeepingAB.add(timekeeping);
                System.out.println("- Successful Timekeeping -");
            } catch (SQLException e) {
                System.err.println("Error when add timekeeping : " + e.getMessage());
            }
        } catch (ParseException e) {
            System.err.println(" Date format error. Please enter correct format YYYY-MM-DD.");
        }
    }

    private static void updateTimekeeping(Scanner scanner, TimekeepingAB timekeepingAB) {
        System.out.print("Timekeeping ID needs to be updated: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Timekeeping existing = timekeepingAB.getById(id);
            if (existing == null) {
                System.out.println("No timkeeping found with this ID !.");
                return;
            }

            System.out.print("New HoursWork: ");
            int hoursWorked = scanner.nextInt();

            Timekeeping updated = new Timekeeping(id, existing.getEmployeeID(), existing.getWorkDate(), hoursWorked);
            timekeepingAB.update(updated);
            System.out.println("- Update Timekeeping Successful.- ");
        } catch (SQLException e) {
            System.err.println("Error Update Timekeeping : " + e.getMessage());
        }
    }

    private static void getTimekeeping(Scanner scanner, TimekeepingAB timekeepingAB) {
        System.out.print("Enter the timekeepingID want to view: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Timekeeping timekeeping = timekeepingAB.getById(id);
            if (timekeeping != null) {
                System.out.println("Timekeeping information: " + timekeeping);
            } else {
                System.out.println("No timekeeping found with this ID!");
            }
        } catch (SQLException e) {
            System.err.println("Timekeeping query error: " + e.getMessage());
        }
    }

    private static void getAllTimekeepings(TimekeepingAB timekeepingAB) {
        try {
            List<Timekeeping> timekeepings = timekeepingAB.getAll();
            if (timekeepings.isEmpty()) {
                System.out.println("No timekeeping data");
            } else {
                System.out.println("Timekeeping list:");
                for (Timekeeping t : timekeepings) {
                    System.out.println(t);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting timekeeping: " + e.getMessage());
        }
    }

    private static void deleteTimekeeping(Scanner scanner, TimekeepingAB timekeepingAB) {
        System.out.print("Enter timekeepingID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Timekeeping existing = timekeepingAB.getById(id);
            if (existing == null) {
                System.out.println("No timekeeping found with this ID");
                return;
            }

            timekeepingAB.delete(id);
            System.out.println("- Delete timekeeping successful -.");
        } catch (SQLException e) {
            System.err.println("Error while deleting timekeeping: " + e.getMessage());
        }
    }
}

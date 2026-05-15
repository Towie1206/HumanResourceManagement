
package humanresourcemanagement;
import humanresourcemanagement.AB.ContractAB;
import humanresourcemanagement.model.Contract;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class ContractManagement {
    public static void manageContracts(Scanner scanner, ContractAB contractAB) {
        int choice = -1; // Khởi tạo để tránh lỗi

        do {
            System.out.println("1. Add contract");
            System.out.println("2. Delete contract");
            System.out.println("3. View all contracts");
            System.out.println("4. View insurance information by contract");
            System.out.println("0.  Return");
            System.out.print("Choose: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number!");
                scanner.next();
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // Tiêu thụ dòng trống

            switch (choice) {
                case 1 -> addContract(scanner, contractAB);
                case 2 -> deleteContract(scanner, contractAB);
                case 3 -> getAllContracts(contractAB);
                case 4 -> getInsuranceByContract(contractAB);
                case 0 -> System.out.println("Return to main menu.");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }
    private static void addContract(Scanner scanner, ContractAB contractAB) {
        try {
            System.out.print("Enter employee ID: ");
            if (!scanner.hasNextInt()) {
                System.out.println(" Invalid employee ID!");
                scanner.next();
                return;
            }
            int employeeID = scanner.nextInt();
            scanner.nextLine(); // Tiêu thụ dòng trống

            System.out.print("Contract name: ");
            String contractType = scanner.nextLine();

            System.out.print("Signing Date  (YYYY-MM-DD): ");
            String signingDateStr = scanner.nextLine();

            // Chuyển đổi từ String sang LocalDate
            LocalDate signingDate;
            try {
                signingDate = LocalDate.parse(signingDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please enter in YYYY-MM-DD.");
                return;
            }

            System.out.print("Is the contract insured? (true/false): ");
            if (!scanner.hasNextBoolean()) {
                System.out.println(" Please enter 'true' or 'false'!");
                scanner.next();
                return;
            }
            boolean insurance = scanner.nextBoolean();
            scanner.nextLine(); // Tiêu thụ dòng trống

            // Khởi tạo đối tượng Contract
            Contract contract = new Contract(employeeID, contractType, signingDate, insurance);

            // Thêm vào database
            contractAB.addContract(contract);
            System.out.println("Contract has been added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding contract: " + e.getMessage());
        }
    }
    public static void deleteContract(Scanner scanner, ContractAB contractAB) {
        System.out.print("Enter the contract ID to delete ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid ID!");
            scanner.next();
            return;
        }
        int contractID = scanner.nextInt();
        scanner.nextLine(); // Tiêu thụ dòng trống

        try {
            contractAB.deleteContract(contractID);
            System.out.println("Deleted contract with ID:  " + contractID);
        } catch (SQLException e) {
            System.err.println("Error deleting contract: " + e.getMessage());
        }
    }
    public static void getAllContracts(ContractAB contractAB) {
        try {
            List<Contract> contracts = contractAB.getAllContracts();
            if (contracts.isEmpty()) {
                System.out.println("here are no contracts.");
                return;
            }
            System.out.println(" List of contracts:");
            for (Contract contract : contracts) {
                System.out.println("ID: " + contract.getId() +
                        ", Employee ID: " + contract.getEmployeeID() +
                        ", Contract Type: " + contract.getContractType() +
                        ", Signing Date: " + contract.getSigningDate() +
                        ", Insurance: " + (contract.hasInsurance() ? "Có" : "Không"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting list of contracts:  " + e.getMessage());
        }
    }
    public static void getInsuranceByContract(ContractAB contractAB) {
        try {
            List<Contract> contracts = contractAB.getAllContracts();
            List<Contract> insuredContracts = contracts.stream()
                    .filter(Contract::hasInsurance)
                    .toList();

            if (insuredContracts.isEmpty()) {
                System.out.println("There are no insured contracts.");
                return;
            }

            System.out.println("List of insured contracts:");
            for (Contract contract : insuredContracts) {
                System.out.println("ID: " + contract.getId() +
                        ", Employee ID: " + contract.getEmployeeID() +
                        ", Contract Type: " + contract.getContractType() +
                        ",  Signing Date: " + contract.getSigningDate());
            }
        } catch (SQLException e) {
            System.err.println("Error filtering insurance contracts: " + e.getMessage());
        }
    }
}

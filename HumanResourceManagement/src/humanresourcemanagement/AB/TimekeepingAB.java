package humanresourcemanagement.AB;

import humanresourcemanagement.DBConnection;
import humanresourcemanagement.model.Timekeeping;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TimekeepingAB implements DatabaseView<Timekeeping> {

    @Override
    public void add(Timekeeping timekeeping) throws SQLException {
        String sql = "INSERT INTO Timekeeping (employeeID, workDate, hoursWorked) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, timekeeping.getEmployeeID());
            pstmt.setDate(2, timekeeping.getWorkDate());
            pstmt.setInt(3, timekeeping.getHoursWorked());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Timekeeping timekeeping) throws SQLException {
        String sql = "UPDATE Timekeeping SET workDate = ?, hoursWorked = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, timekeeping.getWorkDate());
            pstmt.setInt(2, timekeeping.getHoursWorked());
            pstmt.setInt(3, timekeeping.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int timekeepingID) throws SQLException {
        String sql = "DELETE FROM Timekeeping WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, timekeepingID);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Timekeeping getById(int timekeepingID) throws SQLException {
        String sql = "SELECT * FROM Timekeeping WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, timekeepingID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Timekeeping(
                            rs.getInt("id"),
                            rs.getInt("employeeID"),
                            rs.getDate("workDate"),
                            rs.getInt("hoursWorked")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Timekeeping> getAll() throws SQLException {
        List<Timekeeping> timekeepingList = new ArrayList<>();
        String sql = "SELECT * FROM Timekeeping";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                timekeepingList.add(new Timekeeping(
                        rs.getInt("id"),
                        rs.getInt("employeeID"),
                        rs.getDate("workDate"),
                        rs.getInt("hoursWorked")
                ));
            }
        }
        return timekeepingList;
    }

    private void addTimekeeping(Scanner scanner) throws SQLException {
        System.out.print("Employee ID: ");
        int employeeID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("WorkDate (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine().trim();
        Date workDate = Date.valueOf(dateInput);

        System.out.print("HoursWork: ");
        int hoursWorked = scanner.nextInt();
        scanner.nextLine();

        Timekeeping timekeeping = new Timekeeping(0, employeeID, workDate, hoursWorked);
        add(timekeeping);
        System.out.println("Add timekeeping successful!");
    }

    private void updateTimekeeping(Scanner scanner) throws SQLException {
        System.out.print("Timekeeping ID needs to be updated : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Timekeeping existing = getById(id);
        if (existing == null) {
            System.out.println("No timekeeping found with this ID: " + id);
            return;
        }

        System.out.print("New HoursWork (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine().trim();
        Date workDate = Date.valueOf(dateInput);

        System.out.print("New WorkDate: ");
        int hoursWorked = scanner.nextInt();
        scanner.nextLine();

        Timekeeping timekeeping = new Timekeeping(id, existing.getEmployeeID(), workDate, hoursWorked);
        update(timekeeping);
        System.out.println("- Update timekeeping successful -");
    }

    private void deleteTimekeeping(Scanner scanner) throws SQLException {
        System.out.print("Enter timekeepingID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Timekeeping existing = getById(id);
        if (existing == null) {
            System.out.println("No timekeeping found with this ID " + id);
            return;
        }

        delete(id);
        System.out.println("- Delete timekeeping successful -");
    }

    private void getTimekeepingById(Scanner scanner) throws SQLException {
        System.out.print("Timekeeping ID need : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Timekeeping timekeeping = getById(id);
        if (timekeeping != null) {
            System.out.println("Timekeeping information: " + timekeeping);
        } else {
            System.out.println("No timekeeping found with this ID: " + id);
        }
    }

}
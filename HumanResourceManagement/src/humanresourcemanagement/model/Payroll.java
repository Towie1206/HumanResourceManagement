
package humanresourcemanagement.model;

import humanresourcemanagement.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Payroll {
    private int id;
    private int employeeID;
    private int month;
    private int year;
    private int totalHours;
    private int totalSalary;
    public Payroll() {
    }
    public Payroll(int id, int employeeID, int month, int year, int totalHours, int totalSalary) {
        this.id = id;
        this.employeeID = employeeID;
        this.month = month;
        this.year = year;
        this.totalHours = totalHours;
        this.totalSalary = totalSalary;
    }
    public int getId() {

        return id;
    }
    public void setId(int id) {

        this.id = id;
    }
    public int getEmployeeID() {

        return employeeID;
    }
    public void setEmployeeID(int employeeID) {

        this.employeeID = employeeID;
    }
    public int getMonth() {

        return month;
    }
    public void setMonth(int month) {

        this.month = month;
    }
    public int getYear() {

        return year;
    }
    public void setYear(int year) {

        this.year = year;
    }
    public int getTotalHours() {

        return totalHours;
    }
    public void setTotalHours(int totalHours) {

        this.totalHours = totalHours;
    }
    public int getTotalSalary() {

        return totalSalary;
    }
    public void setTotalSalary(int totalSalary) {

        this.totalSalary = totalSalary;
    }
    public static List<Payroll> getAllPayrolls() {
        List<Payroll> payrollList = new ArrayList<>();
        String sql = "SELECT * FROM Payroll";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Payroll payroll = new Payroll(
                        rs.getInt("id"),
                        rs.getInt("employeeID"),
                        rs.getInt("month"),
                        rs.getInt("year"),
                        rs.getInt("totalHours"),
                        rs.getInt("totalSalary")
                );
                payrollList.add(payroll);
            }
        } catch (SQLException e) {
            System.err.println("Error getting payroll list: " + e.getMessage());
        }
        return payrollList;
    }
    public static boolean addPayroll(Payroll payroll) {
        String sql = "INSERT INTO Payroll (employeeID, month, year, totalHours, totalSalary) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, payroll.getEmployeeID());
            pstmt.setInt(2, payroll.getMonth());
            pstmt.setInt(3, payroll.getYear());
            pstmt.setInt(4, payroll.getTotalHours());
            pstmt.setInt(5, payroll.getTotalSalary());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error adding payroll: " + e.getMessage());
            return false;
        }
    }
    public static boolean updatePayroll(Payroll payroll) {
        String sql = "UPDATE Payroll SET employeeID = ?, month = ?, year = ?, totalHours = ?, totalSalary = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, payroll.getEmployeeID());
            pstmt.setInt(2, payroll.getMonth());
            pstmt.setInt(3, payroll.getYear());
            pstmt.setInt(4, payroll.getTotalHours());
            pstmt.setInt(5, payroll.getTotalSalary());
            pstmt.setInt(6, payroll.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Payroll update error: " + e.getMessage());
            return false;
        }
    }
    public static boolean deletePayroll(int id) {
        String sql = "DELETE FROM Payroll WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting payroll: " + e.getMessage());
            return false;
        }
    }
    @Override
    public String toString() {
        return "Payroll{" +
                "id=" + id +
                ", employeeID=" + employeeID +
                ", month=" + month +
                ", year=" + year +
                ", totalHours=" + totalHours +
                ", totalSalary=" + totalSalary +
                '}';
    }
}

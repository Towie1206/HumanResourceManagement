package humanresourcemanagement.AB;

import humanresourcemanagement.DBConnection;
import humanresourcemanagement.model.Payroll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollAB implements DatabaseView<Payroll> {

    @Override
    public void add(Payroll payroll) throws SQLException {
        String sql = "INSERT INTO Payroll (employeeID, month, year, totalHours, totalSalary) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, payroll.getEmployeeID());
            pstmt.setInt(2, payroll.getMonth());
            pstmt.setInt(3, payroll.getYear());
            pstmt.setInt(4, payroll.getTotalHours());
            pstmt.setInt(5, payroll.getTotalSalary());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Payroll payroll) throws SQLException {
        String sql = "UPDATE Payroll SET totalHours = ?, totalSalary = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, payroll.getTotalHours());
            pstmt.setInt(2, payroll.getTotalSalary());
            pstmt.setInt(3, payroll.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int payrollID) throws SQLException {
        String sql = "DELETE FROM Payroll WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, payrollID);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Payroll getById(int payrollID) throws SQLException {
        String sql = "SELECT * FROM Payroll WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, payrollID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Payroll(
                            rs.getInt("id"),
                            rs.getInt("employeeID"),
                            rs.getInt("month"),
                            rs.getInt("year"),
                            rs.getInt("totalHours"),
                            rs.getInt("totalSalary")
                    );
                }
            }
        }
        return null;
    }
public Double getTotalMonthlySalary() throws SQLException {
    String query = "SELECT SUM(totalSalary) FROM payroll";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            return rs.getDouble(1);
        }
    }
    return 0.0; // Trả về 0 nếu không có dữ liệu
}
    @Override
    public List<Payroll> getAll() throws SQLException {
        List<Payroll> payrollList = new ArrayList<>();
        String sql = "SELECT * FROM Payroll";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                payrollList.add(new Payroll(
                        rs.getInt("id"),
                        rs.getInt("employeeID"),
                        rs.getInt("month"),
                        rs.getInt("year"),
                        rs.getInt("totalHours"),
                        rs.getInt("totalSalary")
                ));
            }
        }
        return payrollList;
    }

}
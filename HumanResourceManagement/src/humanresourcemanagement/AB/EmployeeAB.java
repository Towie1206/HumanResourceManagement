package humanresourcemanagement.AB;

import humanresourcemanagement.AB.DatabaseView;
import humanresourcemanagement.DBConnection;
import humanresourcemanagement.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeAB implements DatabaseView<Employee> {
    @Override
    public void add(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employees (id, name, position, salary, departmentID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employee.getId());
            pstmt.setString(2, employee.getName());
            pstmt.setString(3, employee.getPosition());
            pstmt.setInt(4, employee.getSalary());
            pstmt.setInt(5, employee.getDepartmentId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Employee employee) throws SQLException {
        String sql = "UPDATE Employees SET name = ?, position = ?, salary = ?, departmentID = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getPosition());
            pstmt.setInt(3, employee.getSalary());
            pstmt.setInt(4, employee.getDepartmentId());
            pstmt.setInt(5, employee.getId());
            pstmt.executeUpdate();
        }
    }
 @Override
    public void delete(int employeeID) throws SQLException {
        String sql = "DELETE FROM Employees WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employeeID);
            pstmt.executeUpdate();
        }
    }
private static void deleteEmployee(Scanner scanner, EmployeeAB employeeAB) {
    System.out.print("Enter employee ID to delete:  ");
    int employeeID = scanner.nextInt();
    scanner.nextLine(); 

    try {
        employeeAB.delete(employeeID);
        System.out.println("Employee has been deleted.");
    } catch (SQLException e) {
        System.err.println("Error deleting employee:  " + e.getMessage());
    }
}


    @Override
    public Employee getById(int employeeID) throws SQLException {
        String sql = "SELECT * FROM Employees WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employeeID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Employee(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("departmentID"),
                            rs.getString("position"),
                            rs.getInt("salary")
                    );
                }
            }
        }
        return null;
    }
public Integer getTotalEmployeeCount() throws SQLException {
    String query = "SELECT COUNT(*) FROM employees";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            return rs.getInt(1);
        }
    }
    return 0; 
}

    @Override
    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("departmentID"),
                        rs.getString("position"),
                        rs.getInt("salary")
                ));
            }
        }
        return employees;
    }
    }



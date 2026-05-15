
package humanresourcemanagement.AB;

import humanresourcemanagement.AB.DatabaseView;
import humanresourcemanagement.DBConnection;
import humanresourcemanagement.model.Department;
import humanresourcemanagement.model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentAB implements DatabaseView<Department> {

    @Override
    public void add(Department department) throws SQLException {
        String sql = "INSERT INTO Departments (name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, department.getDepartmentName());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Department department) throws SQLException {
        String sql = "UPDATE Departments SET name = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, department.getDepartmentName());
            pstmt.setInt(2, department.getDepartmentId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int departmentId) throws SQLException {
        String sql = "DELETE FROM Departments WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, departmentId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Department getById(int departmentId) throws SQLException {
        String sql = "SELECT * FROM Departments WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, departmentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Department(
                            rs.getInt("id"),
                            rs.getString("name")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Department> getAll() throws SQLException {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM Departments";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                departments.add(new Department(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        }
        return departments;
    }

   public List<Employee> getEmployeesByDepartment(int departmentId) throws SQLException {
    List<Employee> employees = new ArrayList<>();
    String sql = "SELECT id, name, position, salary FROM Employees WHERE departmentID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, departmentId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("id"),  
                        rs.getString("name"),  
                        departmentId,  
                        rs.getString("position"),  
                        rs.getInt("salary")   
                ));
            }
        }
    }
    return employees;
}
   }

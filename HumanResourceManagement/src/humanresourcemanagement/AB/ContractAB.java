
package humanresourcemanagement.AB;

import humanresourcemanagement.DBConnection;
import humanresourcemanagement.model.Contract;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
        
public class ContractAB {
    
    public void addContract(Contract contract) throws SQLException {
        String sql = "INSERT INTO Contracts (employeeID, contractType, signingDate, insurance) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, contract.getEmployeeID());
            pstmt.setString(2, contract.getContractType());
            pstmt.setDate(3, contract.getSigningDate());
            pstmt.setBoolean(4, contract.hasInsurance());
            pstmt.executeUpdate();
        }
    }

    public void updateContract(Contract contract) throws SQLException {
        String sql = "UPDATE Contracts SET contractType = ?, signingDate = ?, insurance = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contract.getContractType());
            pstmt.setDate(2, contract.getSigningDate());
            pstmt.setBoolean(3, contract.hasInsurance());
            pstmt.setInt(4, contract.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteContract(int contractID) throws SQLException {
        String sql = "DELETE FROM Contracts WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, contractID);
            pstmt.executeUpdate();
        }
    }

    public List<Contract> getAllContracts() throws SQLException {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM Contracts";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                contracts.add(new Contract(
                        rs.getInt("id"),
                        rs.getInt("employeeID"),
                        rs.getString("contractType"),
                        rs.getDate("signingDate"),
                        rs.getBoolean("insurance")
                ));
            }
        }
        return contracts;
    }
}

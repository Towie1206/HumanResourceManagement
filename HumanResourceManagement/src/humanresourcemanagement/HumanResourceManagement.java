package humanresourcemanagement;

import humanresourcemanagement.frame.MainFrame;

import java.sql.SQLException;

import javax.swing.*;

public class HumanResourceManagement {
    public static void main(String[] args) {
        // Ép giao diện Java thành giao diện hệ điều hành (Windows/macOS)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            try {
                DBConnection.getConnection();
                new MainFrame().setVisible(true);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối CSDL: " + e.getMessage());
            }
        });
    }
}

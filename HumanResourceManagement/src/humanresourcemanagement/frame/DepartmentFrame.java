package humanresourcemanagement.frame;

import humanresourcemanagement.AB.DepartmentAB;
import humanresourcemanagement.model.Department;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DepartmentFrame extends JFrame {
    private DepartmentAB departmentAB = new DepartmentAB();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtName;

    public DepartmentFrame() {
        setTitle("Quản lý Phòng ban");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin phòng ban"));

        inputPanel.add(new JLabel("ID (dùng để Sửa/Xóa):"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Tên phòng ban:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID Phòng ban", "Tên Phòng ban"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnReload = new JButton("Làm mới");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnReload);
        add(buttonPanel, BorderLayout.SOUTH);

        btnReload.addActionListener(e -> loadData());

        btnAdd.addActionListener(e -> {
            try {
                Department dept = new Department(txtName.getText());
                departmentAB.add(dept);
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                Department dept = new Department(id, txtName.getText());
                departmentAB.update(dept);
                JOptionPane.showMessageDialog(this, "Sửa thành công!");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                departmentAB.delete(id);
                JOptionPane.showMessageDialog(this, "Đã xóa!");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });

        loadData();
    }

    private void loadData() {
        try {
            tableModel.setRowCount(0);
            List<Department> list = departmentAB.getAll();
            for (Department d : list) {
                tableModel.addRow(new Object[]{d.getDepartmentId(), d.getDepartmentName()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + ex.getMessage());
        }
    }
}
package humanresourcemanagement.frame;

import humanresourcemanagement.AB.EmployeeAB;
import humanresourcemanagement.model.Employee;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFrame extends JFrame {
    private EmployeeAB employeeAB = new EmployeeAB(); // Sử dụng lại logic cũ
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtName, txtPosition, txtSalary, txtDeptId, txtId;

    public EmployeeFrame() {
        setTitle("Quản lý Nhân viên");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Panel Nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));

        inputPanel.add(new JLabel("ID (chỉ dùng để Sửa/Xóa):"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Họ tên:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Vị trí:"));
        txtPosition = new JTextField();
        inputPanel.add(txtPosition);

        inputPanel.add(new JLabel("Lương:"));
        txtSalary = new JTextField();
        inputPanel.add(txtSalary);

        inputPanel.add(new JLabel("ID Phòng ban:"));
        txtDeptId = new JTextField();
        inputPanel.add(txtDeptId);

        add(inputPanel, BorderLayout.NORTH);

        // 2. Bảng hiển thị
        tableModel = new DefaultTableModel(new String[]{"ID", "Tên", "Vị trí", "Lương", "Phòng ban"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 3. Panel Nút bấm
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

        // --- Xử lý sự kiện ---

        // Load dữ liệu lên bảng
        btnReload.addActionListener(e -> loadData());

        // Thêm nhân viên
        btnAdd.addActionListener(e -> {
            try {
                Employee emp = new Employee(txtName.getText(),
                        Integer.parseInt(txtDeptId.getText()),
                        txtPosition.getText(),
                        Integer.parseInt(txtSalary.getText()));
                employeeAB.add(emp);
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });

        // Sửa nhân viên
        btnUpdate.addActionListener(e -> {
            try {
                // Lấy dữ liệu từ các ô nhập liệu
                int id = Integer.parseInt(txtId.getText());
                String name = txtName.getText();
                int deptId = Integer.parseInt(txtDeptId.getText());
                String position = txtPosition.getText();
                int salary = Integer.parseInt(txtSalary.getText());

                // Tạo đối tượng Employee mới nhưng giữ nguyên ID cũ
                Employee emp = new Employee(id, name, deptId, position, salary);

                // Gọi lớp AB để update vào CSDL
                employeeAB.update(emp);

                JOptionPane.showMessageDialog(this, "Sửa thông tin thành công!");
                loadData(); // Tải lại bảng cho mới
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });

        // Xóa nhân viên
        btnDelete.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                employeeAB.delete(id);
                JOptionPane.showMessageDialog(this, "Đã xóa!");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });

        loadData(); // Tự động load khi mở cửa sổ
    }

    private void loadData() {
        try {
            tableModel.setRowCount(0);
            List<Employee> list = employeeAB.getAll(); // Gọi lại hàm cũ
            for (Employee e : list) {
                tableModel.addRow(new Object[]{e.getId(), e.getName(), e.getPosition(), e.getSalary(), e.getDepartmentId()});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
package humanresourcemanagement.frame;

import humanresourcemanagement.AB.PayrollAB;
import humanresourcemanagement.model.Payroll;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PayrollFrame extends JFrame {
    private PayrollAB payrollAB = new PayrollAB();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtEmpId, txtMonth, txtYear, txtHours, txtSalary;

    public PayrollFrame() {
        setTitle("Quản lý Bảng lương");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin lương"));
        inputPanel.add(new JLabel("Mã Lương (Sửa/Xóa):")); txtId = new JTextField(); inputPanel.add(txtId);
        inputPanel.add(new JLabel("Mã Nhân viên:")); txtEmpId = new JTextField(); inputPanel.add(txtEmpId);
        inputPanel.add(new JLabel("Tháng:")); txtMonth = new JTextField(); inputPanel.add(txtMonth);
        inputPanel.add(new JLabel("Năm:")); txtYear = new JTextField(); inputPanel.add(txtYear);
        inputPanel.add(new JLabel("Tổng giờ:")); txtHours = new JTextField(); inputPanel.add(txtHours);
        inputPanel.add(new JLabel("Tổng lương:")); txtSalary = new JTextField(); inputPanel.add(txtSalary);
        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Mã NV", "Tháng", "Năm", "Giờ", "Lương"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm lương");
        JButton btnDelete = new JButton("Xóa");
        JButton btnReload = new JButton("Làm mới");
        btnPanel.add(btnAdd); btnPanel.add(btnDelete); btnPanel.add(btnReload);
        add(btnPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            try {
                Payroll p = new Payroll(0, Integer.parseInt(txtEmpId.getText()), Integer.parseInt(txtMonth.getText()),
                        Integer.parseInt(txtYear.getText()), Integer.parseInt(txtHours.getText()), Integer.parseInt(txtSalary.getText()));
                payrollAB.add(p);
                loadData();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
        });

        btnReload.addActionListener(e -> loadData());
        loadData();
    }

    private void loadData() {
        try {
            tableModel.setRowCount(0);
            List<Payroll> list = payrollAB.getAll();
            for (Payroll p : list) tableModel.addRow(new Object[]{p.getId(), p.getEmployeeID(), p.getMonth(), p.getYear(), p.getTotalHours(), p.getTotalSalary()});
        } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
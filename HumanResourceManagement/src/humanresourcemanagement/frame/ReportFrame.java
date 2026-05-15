package humanresourcemanagement.frame;

import humanresourcemanagement.AB.EmployeeAB;
import humanresourcemanagement.AB.PayrollAB;
import javax.swing.*;
import java.awt.*;

public class ReportFrame extends JFrame {
    private EmployeeAB empAB = new EmployeeAB();
    private PayrollAB payAB = new PayrollAB();
    private JLabel lblTotalEmp, lblTotalSalary;

    public ReportFrame() {
        setTitle("Báo cáo & Thống kê");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));

        lblTotalEmp = new JLabel("Tổng số nhân viên: Đang tính...", JLabel.CENTER);
        lblTotalSalary = new JLabel("Tổng lương tháng này: Đang tính...", JLabel.CENTER);
        JButton btnRefresh = new JButton("Làm mới báo cáo");

        add(lblTotalEmp);
        add(lblTotalSalary);
        add(btnRefresh);

        btnRefresh.addActionListener(e -> updateStats());
        updateStats();
    }

    private void updateStats() {
        try {
            lblTotalEmp.setText("Tổng số nhân viên: " + empAB.getTotalEmployeeCount());
            lblTotalSalary.setText("Tổng lương tháng này: " + String.format("%,.0f", payAB.getTotalMonthlySalary()) + " VNĐ");
        } catch (Exception e) { e.printStackTrace(); }
    }
}
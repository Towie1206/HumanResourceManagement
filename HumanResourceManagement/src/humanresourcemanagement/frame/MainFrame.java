package humanresourcemanagement.frame;


import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Hệ thống Quản lý Nhân sự - HRMS");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));

        JLabel titleLabel = new JLabel("MENU CHÍNH", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel);

        JButton btnEmployee = new JButton("Quản lý Nhân viên");
        JButton btnDepartment = new JButton("Quản lý Phòng ban");
        JButton btnTimekeeping = new JButton("Quản lý Chấm công");
        JButton btnPayroll = new JButton("Quản lý Lương");
        JButton btnExit = new JButton("Thoát");

        // Sự kiện mở Form Nhân viên
        btnEmployee.addActionListener(e -> new EmployeeFrame().setVisible(true));
        btnDepartment.addActionListener(e -> new DepartmentFrame().setVisible(true));
        btnTimekeeping.addActionListener(e -> new TimekeepingFrame().setVisible(true));
        btnPayroll.addActionListener(e -> new PayrollFrame().setVisible(true));

        JButton btnContract = new JButton("Quản lý Hợp đồng");
        JButton btnReport = new JButton("Báo cáo Thống kê");

        btnContract.addActionListener(e -> new ContractFrame().setVisible(true));
        btnReport.addActionListener(e -> new ReportFrame().setVisible(true));

        add(btnContract);
        add(btnReport);

        btnExit.addActionListener(e -> System.exit(0));

        add(btnEmployee);
        add(btnDepartment);
        add(btnTimekeeping);
        add(btnPayroll);
        add(btnExit);
    }
}
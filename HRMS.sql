CREATE TABLE Departments (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL
);

CREATE TABLE Employees (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    position NVARCHAR(100),
    salary INT NOT NULL,
    departmentID INT FOREIGN KEY REFERENCES Departments(id) ON DELETE CASCADE
);

CREATE TABLE Timekeeping (
    id INT IDENTITY(1,1) PRIMARY KEY,
    employeeID INT NOT NULL FOREIGN KEY REFERENCES Employees(id) ON DELETE CASCADE,
    workDate DATE NOT NULL,
    hoursWorked INT NOT NULL
);

CREATE TABLE Payroll (
    id INT IDENTITY(1,1) PRIMARY KEY,
    employeeID INT NOT NULL FOREIGN KEY REFERENCES Employees(id) ON DELETE CASCADE,
    month INT NOT NULL,
    year INT NOT NULL,
    totalHours INT NOT NULL,
    totalSalary INT NOT NULL
);

CREATE TABLE Contracts (
    id INT IDENTITY(1,1) PRIMARY KEY,
    employeeID INT NOT NULL FOREIGN KEY REFERENCES Employees(id) ON DELETE CASCADE,
    contractType NVARCHAR(100),
    signingDate DATE NOT NULL,
    insurance BIT NOT NULL
);

INSERT INTO Departments (name) VALUES 
(N'Phòng Nhân sự'),
(N'Phòng Kế toán'),
(N'Phòng IT'),
(N'Phòng Kinh doanh');

INSERT INTO Employees (name, position, salary, departmentID) VALUES 
(N'Nguyễn Văn A', N'Nhân viên', 10000000, 1),
(N'Trần Thị B', N'Kế toán trưởng', 15000000, 2),
(N'Phạm Văn C', N'Lập trình viên', 20000000, 3),
(N'Lê Thị D', N'Nhân viên kinh doanh', 12000000, 4);

INSERT INTO Timekeeping (employeeID, workDate, hoursWorked) VALUES 
(1, '2025-03-01', 8),
(2, '2025-03-01', 7),
(3, '2025-03-01', 9),
(4, '2025-03-01', 8);
INSERT INTO Payroll (employeeID, month, year, totalHours, totalSalary) VALUES 
(1, 3, 2025, 176, 22000000),
(2, 3, 2025, 160, 24000000),
(3, 3, 2025, 180, 36000000),
(4, 3, 2025, 170, 20400000);

INSERT INTO Contracts (employeeID, contractType, signingDate, insurance) VALUES 
(1, N'Hợp đồng chính thức', '2024-01-01', 1),
(2, N'Hợp đồng thử việc', '2025-02-01', 0),
(3, N'Hợp đồng chính thức', '2023-12-15', 1),
(4, N'Hợp đồng thời vụ', '2025-01-10', 0);

SELECT * FROM Departments;
SELECT * FROM Employees;
SELECT * FROM Contracts;
SELECT * FROM Timekeeping;
SELECT * FROM Payroll;

DROP TABLE Payroll;
DROP TABLE Timekeeping;
DROP TABLE Contracts;
DROP TABLE Employees;
DROP TABLE Departments;

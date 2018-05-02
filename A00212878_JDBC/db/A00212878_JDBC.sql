/*A00212878*/
DROP DATABASE IF EXISTS A00212878_JDBC;
CREATE DATABASE IF NOT EXISTS A00212878_JDBC;
USE A00212878_JDBC;

SELECT 'CREATING DATABASE TABLES' as 'INFO:';

DROP TABLE IF EXISTS department;
CREATE TABLE department (
	deptId INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
	deptName VARCHAR(15) NOT NULL
);

DROP TABLE IF EXISTS position;
CREATE TABLE position (
	posId INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
	posName VARCHAR(15) NOT NULL
);

DROP TABLE IF EXISTS rate;
CREATE TABLE rate (
	rateId INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
	rateAmount DECIMAL(7,2) NOT NULL
);

DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
	empId INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
	firstName VARCHAR(15) NOT NULL,
	lastName VARCHAR(20) NOT NULL,
	age VARCHAR(2) NOT NULL,
	gender VARCHAR(6) NOT NULL,
	rateId INTEGER NOT NULL,
	posId INTEGER NOT NULL,
	deptId INTEGER NOT NULL,
FOREIGN KEY(rateId) REFERENCES rate (rateId),
FOREIGN KEY(posId) REFERENCES position (posId),
FOREIGN KEY(deptId) REFERENCES department (deptId)
);

DROP TABLE IF EXISTS pay;
CREATE TABLE pay (
	payId INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
	timeStamp DATETIME NOT NULL,
	hours INTEGER NOT NULL,
	empId INTEGER NOT NULL,
FOREIGN KEY(empId) REFERENCES employee (empId)
);

SELECT 'INSERTING DATA INTO DATABASE' as 'INFO:';

INSERT INTO department VALUES(null,'Business');
INSERT INTO department VALUES(null,'Design');
INSERT INTO department VALUES(null,'Engineering');
INSERT INTO department VALUES(null,'Hospitality');
INSERT INTO department VALUES(null,'Humanities');
INSERT INTO department VALUES(null,'Nursing');
INSERT INTO department VALUES(null,'Science');

INSERT INTO position VALUES(null,'Administrator');
INSERT INTO position VALUES(null,'Head');
INSERT INTO position VALUES(null,'Lecturer');
INSERT INTO position VALUES(null,'Professor');
INSERT INTO position VALUES(null,'Researcher');

INSERT INTO rate VALUES(null,38.00);
INSERT INTO rate VALUES(null,45.57);
INSERT INTO rate VALUES(null,63.08);
INSERT INTO rate VALUES(null,76.45);
INSERT INTO rate VALUES(null,98.56);

INSERT INTO employee VALUES(null,'Joe','Mullins',64,'Male',3,3,3);
INSERT INTO employee VALUES(null,'Joan','Macgill',27,'Female',1,5,7);
INSERT INTO employee VALUES(null,'Jim','Mitchell',51,'Male',1,5,1);
INSERT INTO employee VALUES(null,'John','Magner',47,'Male',3,3,5);
INSERT INTO employee VALUES(null,'Jean','Madden',45,'Female',4,4,2);
INSERT INTO employee VALUES(null,'Jack','Minogue',61,'Male',2,1,4);

INSERT INTO employee VALUES(null,'Josephine','Mahony',33,'Female',5,2,6);
INSERT INTO employee VALUES(null,'Juan','Mosley',56,'Male',4,4,3);
INSERT INTO employee VALUES(null,'Jamie','Mullen',45,'Male',1,5,7);
INSERT INTO employee VALUES(null,'Julie','Mooney',39,'Female',3,3,1);
INSERT INTO employee VALUES(null,'Jane','Mccarthy',37,'Female',2,1,2);
INSERT INTO employee VALUES(null,'James','May',38,'Male',1,5,4);
INSERT INTO employee VALUES(null,'Joseph','Manning',32,'Male',3,3,4);
INSERT INTO employee VALUES(null,'Judith','Milner',36,'Female',3,3,6);
INSERT INTO employee VALUES(null,'Jerome','Murphy',26,'Male',5,2,3);
INSERT INTO employee VALUES(null,'Jude','Manley',28,'Male',5,2,7);
INSERT INTO employee VALUES(null,'Juanita','Mahon',59,'Female',2,1,3);
INSERT INTO employee VALUES(null,'Justin','Maguire',25,'Male',3,3,1);
INSERT INTO employee VALUES(null,'Jacqulinen','Musgrave',43,'Female',4,4,1);
INSERT INTO employee VALUES(null,'Julia','Moore',36,'Female',2,1,7);

INSERT INTO pay VALUES(null,now(),12,1);
INSERT INTO pay VALUES(null,now(),35,2);
INSERT INTO pay VALUES(null,now(),25,3);
INSERT INTO pay VALUES(null,now(),16,4);
INSERT INTO pay VALUES(null,now(),14,5);
INSERT INTO pay VALUES(null,now(),37,6);
INSERT INTO pay VALUES(null,now(),40,7);
INSERT INTO pay VALUES(null,now(),11,8);
INSERT INTO pay VALUES(null,now(),37,9);
INSERT INTO pay VALUES(null,now(),18,10);
INSERT INTO pay VALUES(null,now(),45,11);
INSERT INTO pay VALUES(null,now(),9,12);
INSERT INTO pay VALUES(null,now(),16,13);
INSERT INTO pay VALUES(null,now(),20,14);
INSERT INTO pay VALUES(null,now(),42,15);
INSERT INTO pay VALUES(null,now(),41,16);
INSERT INTO pay VALUES(null,now(),49,17);
INSERT INTO pay VALUES(null,now(),16,18);
INSERT INTO pay VALUES(null,now(),10,19);
INSERT INTO pay VALUES(null,now(),38,20);

SELECT 'DATA INSERTED' as 'INFO:';

SELECT 'CREATING employee_view' as 'INFO:';

DROP VIEW IF EXISTS employee_view;
CREATE VIEW employee_view AS
SELECT employee.empId AS Employee_Id, employee.firstName AS Name,
employee.lastName AS Surname, employee.age AS Age,
employee.gender AS Gender, position.posName AS Position,
department.deptName AS Department,rate.rateAmount AS Hour_Rate
FROM employee, rate, department, position
WHERE employee.rateId = rate.rateId
AND employee.deptId = department.deptId
AND employee.posId = position.posId
ORDER BY employee.empId;

SELECT 'employee_view CREATED' as 'INFO:';

SELECT 'CREATING gross_pay_view' as 'INFO:';

DROP VIEW IF EXISTS gross_pay_view;
CREATE VIEW gross_pay_view AS
SELECT pay.payId AS Pay_Id, employee.empId AS Employee_Id, employee.firstName AS Name,
employee.lastName AS Surname, department.deptName AS Department, WEEK(pay.timeStamp,1) AS Week, 
rate.rateAmount AS Hour_Rate, pay.hours AS Hours, pay.hours * rate.rateAmount AS Gross_Pay
FROM employee, rate, pay, department
WHERE employee.rateId = rate.rateId
AND employee.empId = pay.empId
AND employee.deptId = department.deptId
ORDER BY pay.payId;

SELECT 'gross_pay_view CREATED' as 'INFO:';

SELECT * FROM employee_view;
SELECT * FROM gross_pay_view;
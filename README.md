# JDBC

This is Eclipse project.
Run A00212878_JDBC/db/A00212878_JDBC.sql database file in MySQL Workbench.
Import the project into Eclipse, switch to JavaEE perspective, and run A00212878_JDBC/src/MainClass.java file on Tomcat v8 server.
In Java GUI:

------------------------------------------------------------------------------------------------------------------------------------------
CRUD Actions panel buttons
------------------------------------------------------------------------------------------------------------------------------------------
INSERT: Type in First Name, Last Name, Age textfields, then select Gender, Position, Department, Rate and click Insert button. Data will be stored in database. Create and run SELECT * FROM EMPLOYEE; query to check the employee's data stored in database.

DELETE: Type in Employee ID number into same name textfield and click Delete button. Data will be deleted from database. Run SELECT * FROM EMPLOYEE; query to check the employee's data deleted from database.

UPDATE: Fill in all textfields of CRUD Actions panel, except Rate, and click Update button. Data will be updated in database. Run SELECT * FROM EMPLOYEE; query to check the employee's data in database updated.

EXPORT: The button has not implemented yet.

CLEAR: The button clears textfields and resets dropdowns.

PAY: Fill in Employee ID and Hours textfields. Click Pay button and new record will be created in database. Create and run SELECT * FROM PAY; query to check the payroll data stored in database. The record appears in Database Content panel of Java GUI.

------------------------------------------------------------------------------------------------------------------------------------------
Export Data panel buttons
------------------------------------------------------------------------------------------------------------------------------------------
NUMBER OF LECTURERS FOR DEPARTMENT: Type in Department name into textfield beside the button and click it. Data will be stored in A00212878_JDBC/A00212878_JDBC.csv file.

AVERAGE PAY FOR DEPARTMENT: Type in Department name into textfield beside the button and click it. Data will be stored in A00212878_JDBC/A00212878_JDBC.csv file.

LIST ALL DEPARTMENTS: Click the button and the data will be stored in A00212878_JDBC/A00212878_JDBC.csv file.

LIST ALL EMPLOYEES: Click the button and the data will be stored in A00212878_JDBC/A00212878_JDBC.csv file.

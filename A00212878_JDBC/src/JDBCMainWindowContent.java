import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.border.*;

import java.sql.*;

@SuppressWarnings("serial")
public class JDBCMainWindowContent extends JInternalFrame implements ActionListener
{	
	String cmd = null;

	// DB Connectivity Attributes
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	private Container content;

	private JPanel detailsPanel;
	private JPanel exportButtonPanel;
	private JPanel exportConceptDataPanel;
	private JScrollPane dbContentsPanel;

	private Border lineBorder;
	//create labels
	private JLabel IDLabel=new JLabel("Employee ID:                 ");
	private JLabel FirstNameLabel=new JLabel("First Name:               ");
	private JLabel LastNameLabel=new JLabel("Last Name:      ");
	private JLabel AgeLabel=new JLabel("Age:        ");
	private JLabel GenderLabel=new JLabel("Gender:                 ");
	private JLabel PositionLabel=new JLabel("Position:               ");
	private JLabel DepartmentLabel=new JLabel("Department:      ");
	private JLabel RateLabel=new JLabel("Rate:      ");
	private JLabel HoursLabel=new JLabel("Hours:        ");
	//create text fields
	private JTextField IDTF= new JTextField(10);
	private JTextField FirstNameTF=new JTextField(10);
	private JTextField LastNameTF=new JTextField(10);
	private JTextField AgeTF=new JTextField(10);
	//create dropdowns
	String[] GenderStrings = {"Select","Male","Female"};
	String[] PositionStrings = {"Select","Administrator","Head","Lecturer","Professor","Researcher"};
	String[] DepartmentStrings = {"Select","Business","Design","Engineering","Hospitality","Humanities","Nursing","Science"};
	String[] RateStrings = {"Select","38.00","45.57","63.08","76.45","98.56"};
	//Create combo boxes
	JComboBox GenderList = new JComboBox(GenderStrings);
	JComboBox PositionList = new JComboBox(PositionStrings);
	JComboBox DepartmentList = new JComboBox(DepartmentStrings);
	JComboBox RateList = new JComboBox(RateStrings);	
			//private JTextField GenderTF=new JTextField(10);
			//private JTextField PositionTF=new JTextField(10);
			//private JTextField DepartmentTF=new JTextField(10);
			//private JTextField RateTF=new JTextField(10);
	private JTextField HoursTF=new JTextField(10);

	private static QueryTableModel TableModel = new QueryTableModel();
	//Add the models to JTables
	private JTable TableofDBContents=new JTable(TableModel);
	//Buttons for inserting, and updating members
	//also a clear button to clear details panel
	private JButton updateButton = new JButton("Update");
	private JButton insertButton = new JButton("Insert");
	private JButton exportButton  = new JButton("Export");
	private JButton deleteButton  = new JButton("Delete");
	private JButton clearButton  = new JButton("Clear");
	private JButton payButton  = new JButton("Pay");

	private JButton  NumLecturers = new JButton("Number of Lecturers for Department");
	private JTextField NumLecturersTF  = new JTextField(12);
	private JButton AvgPayDepartment  = new JButton("Average Pay for Department");
	private JTextField AvgPayDepartmentTF  = new JTextField(12);
	private JButton ListAllDepartments  = new JButton("List All Departments");
	private JButton ListAllEmployees  = new JButton("List All Employees");



	public JDBCMainWindowContent( String aTitle)
	{	
		//setting up the GUI
		super(aTitle, false,false,false,false);
		setEnabled(true);

		initiate_db_conn();
		//add the 'main' panel to the Internal Frame
		content=getContentPane();
		content.setLayout(null);
		content.setBackground(Color.lightGray);
		lineBorder = BorderFactory.createEtchedBorder(15, Color.red, Color.black);

		//setup details panel and add the components to it
		detailsPanel=new JPanel();
		detailsPanel.setLayout(new GridLayout(11,2));
		detailsPanel.setBackground(Color.lightGray);
		detailsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "CRUD Actions"));

		detailsPanel.add(IDLabel);			
		detailsPanel.add(IDTF);
		detailsPanel.add(FirstNameLabel);		
		detailsPanel.add(FirstNameTF);
		detailsPanel.add(LastNameLabel);		
		detailsPanel.add(LastNameTF);
		detailsPanel.add(AgeLabel);	
		detailsPanel.add(AgeTF);
		detailsPanel.add(GenderLabel);		
		detailsPanel.add(GenderList);
		detailsPanel.add(PositionLabel);
		detailsPanel.add(PositionList);
		detailsPanel.add(DepartmentLabel);
		detailsPanel.add(DepartmentList);
		detailsPanel.add(RateLabel);
		detailsPanel.add(RateList);
		detailsPanel.add(HoursLabel);
		detailsPanel.add(HoursTF);

		//setup details panel and add the components to it
		exportButtonPanel=new JPanel();
		exportButtonPanel.setLayout(new GridLayout(3,2));
		exportButtonPanel.setBackground(Color.lightGray);
		exportButtonPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Export Data"));
		exportButtonPanel.add(NumLecturers);
		exportButtonPanel.add(NumLecturersTF);
		exportButtonPanel.add(AvgPayDepartment);
		exportButtonPanel.add(AvgPayDepartmentTF);
		exportButtonPanel.add(ListAllDepartments);
		exportButtonPanel.add(ListAllEmployees);
		exportButtonPanel.setSize(500, 200);
		exportButtonPanel.setLocation(3, 300);
		content.add(exportButtonPanel);

		insertButton.setSize(100, 30);
		updateButton.setSize(100, 30);
		exportButton.setSize (100, 30);
		deleteButton.setSize (100, 30);
		clearButton.setSize (100, 30);
		payButton.setSize (100, 30);

		insertButton.setLocation(370, 10);
		updateButton.setLocation(370, 110);
		exportButton.setLocation (370, 160);
		deleteButton.setLocation (370, 60);
		clearButton.setLocation (370, 210);
		payButton.setLocation (370, 260);
		
		GenderList.addActionListener(this);
		PositionList.addActionListener(this);
		DepartmentList.addActionListener(this);
		RateList.addActionListener(this);

		insertButton.addActionListener(this);
		updateButton.addActionListener(this);
		exportButton.addActionListener(this);
		deleteButton.addActionListener(this);
		clearButton.addActionListener(this);
		payButton.addActionListener(this);

		this.ListAllDepartments.addActionListener(this);
		this.NumLecturers.addActionListener(this);
		this.ListAllEmployees.addActionListener(this);
		this.AvgPayDepartment.addActionListener(this);

		content.add(insertButton);
		content.add(updateButton);
		content.add(exportButton);
		content.add(deleteButton);
		content.add(clearButton);
		content.add(payButton);


		TableofDBContents.setPreferredScrollableViewportSize(new Dimension(900, 300));

		dbContentsPanel=new JScrollPane(TableofDBContents,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel.setBackground(Color.lightGray);
		dbContentsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder,"Database Content"));

		detailsPanel.setSize(360, 300);
		detailsPanel.setLocation(3,0);
		dbContentsPanel.setSize(700, 300);
		dbContentsPanel.setLocation(477, 0);

		content.add(detailsPanel);
		content.add(dbContentsPanel);

		setSize(982,645);
		setVisible(true);

		TableModel.refreshFromDB(stmt);
	}

	public void initiate_db_conn()
	{
		try
		{
			// Load the JConnector Driver
			Class.forName("com.mysql.jdbc.Driver");
			// Specify the DB Name
			String url="jdbc:mysql://localhost:3306/A00212878_JDBC";
			// Connect to DB using DB URL, user name and password
			con = DriverManager.getConnection(url, "root", "admin");
			//Create a generic statement which is passed to the TestInternalFrame1
			stmt = con.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("Error: Failed to connect to database\n"+e.getMessage());
		}
	}

	//event handling 
	public void actionPerformed(ActionEvent e)
	{
		Object target=e.getSource();
		if (target == clearButton)
		{
			IDTF.setText("");
			FirstNameTF.setText("");
			LastNameTF.setText("");
			AgeTF.setText("");
			GenderList.setSelectedIndex(0);
			PositionList.setSelectedIndex(0);
			DepartmentList.setSelectedIndex(0);
			RateList.setSelectedIndex(0);
				//Gender.setText("");
				//PositionTF.setText("");
				//DepartmentTF.setText("");
				//RateTF.setText("");
			HoursTF.setText("");
		}

		if (target == insertButton)
		{		 
			try
			{			
				int posIndex = PositionList.getSelectedIndex();
				int deptIndex = DepartmentList.getSelectedIndex();
				int rateIndex = RateList.getSelectedIndex();
				
				String updateTemp ="INSERT INTO employee VALUES("+
						null +",'"+FirstNameTF.getText()+"','"+LastNameTF.getText()+
						"','"+AgeTF.getText()+"','"+GenderList.getSelectedItem()+"','"
						+rateIndex+"','"+posIndex+"',"+deptIndex+");";
				
				stmt.executeUpdate(updateTemp);
			}
			catch (SQLException sqle)
			{
				System.err.println("Error with insert:\n"+sqle.toString());
			}
			finally
			{
				TableModel.refreshFromDB(stmt);
			}
		}
		
		if (target == deleteButton)
		{

			try
			{
				String updateTemp ="DELETE FROM employee WHERE empId = '"+IDTF.getText()+"';"; 
				stmt.executeUpdate(updateTemp);

			}
			catch (SQLException sqle)
			{
				System.err.println("Error with delete:\n"+sqle.toString());
			}
			finally
			{
				TableModel.refreshFromDB(stmt);
			}
		}
		
		if (target == updateButton)
		{	 	
			try
			{
				int posIndex = PositionList.getSelectedIndex();
				int deptIndex = DepartmentList.getSelectedIndex();
				int rateIndex = RateList.getSelectedIndex();
				
				String updateTemp ="UPDATE employee SET" +
				"firstName = '"+FirstNameTF.getText()+
				"', lastName = '"+LastNameTF.getText()+
				"', age = '"+AgeTF.getText()+
				"', gender ='"+GenderList.getSelectedItem()+
				"', rateId = '"+rateIndex+
				"', posId = '"+posIndex+
				"', deptId = '"+deptIndex+
				"' WHERE empId = "+IDTF.getText();


				stmt.executeUpdate(updateTemp);
				//these lines do nothing but the table updates when we access the db.
				rs = stmt.executeQuery("SELECT * FROM employee_view");
				rs.next();
				rs.close();	
			}
			catch (SQLException sqle){
				System.err.println("Error with  update:\n"+sqle.toString());
			}
			finally{
				TableModel.refreshFromDB(stmt);
			}
		}
		
		if(target == exportButton){
			
			cmd = "SELECT * FROM gross_pay_view;";
			
			System.out.println(cmd);
			try{					
				rs= stmt.executeQuery(cmd); 	
				writeToFile(rs);
			}
			catch(Exception e1){e1.printStackTrace();}

		} 
		
		if (target == payButton)
		{	 	
			try
			{
				String hoursWorked = HoursTF.getText();
				String Id = IDTF.getText();
				int hours = Integer.parseInt(hoursWorked);
				int empId = Integer.parseInt(Id);
				
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				
				String updateTemp ="INSERT INTO pay VALUES("+
				null +",'"+timestamp+"','"+hours+"',"+""+empId+");";

				stmt.executeUpdate(updateTemp);
				rs = stmt.executeQuery("SELECT * FROM gross_pay_view");
				rs.next();
				rs.close();
			}
			catch (SQLException sqle)
			{
				System.err.println("Error with  insert:\n"+sqle.toString());
			}
			finally
			{
				TableModel.refreshFromDB(stmt);
			}
		}

		/////////////////////////////////////////////////////////////////////////////////////

		if(target == this.ListAllDepartments){

					cmd = "SELECT DISTINCT deptName AS Departments FROM department;";

			try{					
				rs= stmt.executeQuery(cmd); 	
				writeToFile(rs);
			}
			catch(Exception e1){e1.printStackTrace();}
		}

		if(target == this.NumLecturers){
			String deptName = this.NumLecturersTF.getText();

			cmd = "SELECT department.deptName AS Department, COUNT(employee.empId) AS NumLecturers "
			+"FROM employee, department, position "+"WHERE employee.deptId = department.deptId "
			+"AND employee.posId = position.posId "+"AND position.posName = 'Lecturer' "
			+"AND department.deptName = '"+deptName+"';";

			System.out.println(cmd);
			try{					
				rs= stmt.executeQuery(cmd); 	
				writeToFile(rs);
			}
			catch(Exception e1){e1.printStackTrace();}
		} 
		
		if(target == this.ListAllEmployees){

			cmd = "SELECT * FROM employee_view;";		


			System.out.println(cmd);
			try{					
				rs= stmt.executeQuery(cmd); 	
				writeToFile(rs);
			}
			catch(Exception e1){e1.printStackTrace();}
		}
		
		if(target == this.AvgPayDepartment){
			String deptName = this.AvgPayDepartmentTF.getText();

			cmd = "SELECT department.deptName AS Department, AVG(rate.rateAmount*pay.hours) AS Avg_Pay "
					+"FROM employee, department, rate, pay "+"WHERE employee.deptId = department.deptId "
					+"AND employee.rateId = rate.rateId "+"AND employee.empId = pay.empId "
					+"AND department.deptName = '"+deptName+"';";	

			System.out.println(cmd);
			try{					
				rs= stmt.executeQuery(cmd); 	
				writeToFile(rs);
			}
			catch(Exception e1){e1.printStackTrace();}
		} 

	}
	///////////////////////////////////////////////////////////////////////////

	private void writeToFile(ResultSet rs){
		try{
			System.out.println("In writeToFile");
			FileWriter outputFile = new FileWriter("A00212878_JDBC.csv");
			PrintWriter printWriter = new PrintWriter(outputFile);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();

			for(int i=0;i<numColumns;i++){
				printWriter.print(rsmd.getColumnLabel(i+1)+",");
			}
			printWriter.print("\n");
			while(rs.next()){
				for(int i=0;i<numColumns;i++){
					printWriter.print(rs.getString(i+1)+",");
				}
				printWriter.print("\n");
				printWriter.flush();
			}
			printWriter.close();
		}
		catch(Exception e){e.printStackTrace();}
	}
}

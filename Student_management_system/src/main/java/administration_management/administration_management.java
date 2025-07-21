package administration_management;

import java.sql.*;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class administration_management {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n----- Administration Management System -----");
            System.out.println("1. New User Sign Up");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    newUserSignUp();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    private static final String DB_URL = "jdbc:mysql://localhost:3306/admin_mgmt";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Pawankumar@15";
    
//---------------------------------------------------------SIGN UP PAGE-------------------------------------------------------------------------------------------------

    private static void newUserSignUp() {
        while (true) {
            System.out.println("\nSelect Role:");
            System.out.println("a. Admin");
            System.out.println("b. Teacher");
            System.out.println("c. Temporary Student");
            System.out.println("d. User");
            System.out.println("e. Back to Main Menu");
            System.out.print("Enter choice: ");
            String role = scanner.nextLine();

            switch (role.toLowerCase()) {
                case "a":
                    registerAdmin();
                    break;
                case "b":
                    registerTeacher();
                    break;
                case "c":
                    registerTempStudent();
                    break;
                case "d":
                    registerUser();
                    break;
                case "e":
                    return; 
                default:
                    System.out.println("Invalid role.");
            }
        }
    }

//--------------------------------------------------------------------------RAGISTRATION PAGES--------------------------------------------------------------------------

    private static void registerAdmin() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone No: ");
        String phone = scanner.nextLine();
        System.out.print("Designation (admin/Teacher) : ");
        String designation = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = readPassword();

        try (Connection conn = UserDAO.getConnection()) {
            if (UserDAO.isEmailOrPhoneExists(conn, email, phone)) {
                System.out.println("Email or Phone already exists.");
                return;
            }
            String table = "admin_" + designation.toLowerCase();
            if (UserDAO.isUsernameExists(conn, username, table)) {
                System.out.println("Username already taken.");
                return;
            }

            String encryptedPassword = UserDAO.encryptPassword(password);
            UserDAO.createTableIfNotExists(conn, table, "Admin");
            String[] fields = {"name", "email", "phone", "username", "password"};
            String[] values = {name, email, phone, username, encryptedPassword};
            UserDAO.insertUser(table, fields, values);

            System.out.println("Admin registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void registerTeacher() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone No: ");
        String phone = scanner.nextLine();
        System.out.print("Course (Maths/English/Hindi/Science/SST): ");
        String course = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = readPassword();

        try (Connection conn = UserDAO.getConnection()) {
            if (UserDAO.isEmailOrPhoneExists(conn, email, phone)) {
                System.out.println("Email or Phone already exists.");
                return;
            }
            String table = "teacher_" + course.toLowerCase();
            if (UserDAO.isUsernameExists(conn, username, table)) {
                System.out.println("Username already taken.");
                return;
            }

            String encryptedPassword = UserDAO.encryptPassword(password);
            UserDAO.createTableIfNotExists(conn, table, "Teacher");
            String[] fields = {"name", "email", "phone", "course", "username", "password"};
            String[] values = {name, email, phone, course, username, encryptedPassword};
            UserDAO.insertUser(table, fields, values);

            System.out.println("Teacher registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void registerTempStudent() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone No: ");
        String phone = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = readPassword();

        try (Connection conn = UserDAO.getConnection()) {
            if (UserDAO.isEmailOrPhoneExists(conn, email, phone)) {
                System.out.println("Email or Phone already exists.");
                return;
            }
            String table = "temp_student";
            if (UserDAO.isUsernameExists(conn, username, table)) {
                System.out.println("Username already taken.");
                return;
            }

            String encryptedPassword = UserDAO.encryptPassword(password);
            UserDAO.createTableIfNotExists(conn, table, "Temp");
            String[] fields = {"name", "email", "phone", "username", "password"};
            String[] values = {name, email, phone, username, encryptedPassword};
            UserDAO.insertUser(table, fields, values);

            System.out.println("Temporary student registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void registerUser() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone No: ");
        String phone = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = readPassword();

        try (Connection conn = UserDAO.getConnection()) {
            if (UserDAO.isEmailOrPhoneExists(conn, email, phone)) {
                System.out.println("Email or Phone already exists.");
                return;
            }

            String table = "users";  // changed from "user" to "users"
            if (UserDAO.isUsernameExists(conn, username, table)) {
                System.out.println("Username already taken.");
                return;
            }

            String encryptedPassword = UserDAO.encryptPassword(password);  // use consistent encryption
            UserDAO.createTableIfNotExists(conn, table, "User");

            String[] fields = {"name", "email", "phone", "username", "password"};
            String[] values = {name, email, phone, username, encryptedPassword};
            UserDAO.insertUser(table, fields, values);

            System.out.println("✅ User registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
// ------------------------------------------------------------LOGIN PAGE------------------------------------------------------------------------
    private static void login() {
        while (true) {
            System.out.println("\nLogin as:");
            System.out.println("a. Admin");
            System.out.println("b. Teacher");
            System.out.println("c. Temporary Student");
            System.out.println("d. Student");
            System.out.println("e. User");
            System.out.println("f. Back to Main Menu");
            System.out.print("Enter choice: ");
            String role = scanner.nextLine().trim().toLowerCase();

            switch (role) {
                case "a":
                    loginAdmin();
                    break;
                case "b":
                    loginTeacher();
                    break;
                case "c":
                    loginTemporaryStudent();
                    break;
                case "d":
                    loginStudent();
                    break;
                case "e":
                    loginUser();
                    break;
                case "f":
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

//-----------------------------------------------------------------------ADMIN LOGIN-------------------------------------------------------------
	private static void loginAdmin() {
	    System.out.print("Enter Designation: ");
	    String designation = scanner.nextLine();
	    System.out.print("Username: ");
	    String username = scanner.nextLine();
	    System.out.print("Password: ");
	    String password = readPassword();

	    try (Connection conn = UserDAO.getConnection()) {
	        String table = "admin_" + designation;
	        PreparedStatement ps = conn.prepareStatement("SELECT password FROM " + table + " WHERE username = ?");
	        ps.setString(1, username);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            String storedPassword = rs.getString("password");
	            if (storedPassword.equals(UserDAO.encryptPassword(password))) {
	                System.out.println("Admin logged in successfully.");
	                adminMenu();
	            } else {
	                System.out.println("Invalid username or password.");
	            }
	        } else {
	            System.out.println("Invalid username or password.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
//	private static void displayAllStudentsWithPercentage() {
//	    System.out.println("\n--- All Students with Percentage ---");
//
//	    try (Connection conn = UserDAO.getConnection()) {
//	        // Check if percentage column exists
//	        String checkColumn = "SELECT * FROM student LIMIT 1";
//	        Statement checkStmt = conn.createStatement();
//	        ResultSet rs = checkStmt.executeQuery(checkColumn);
//	        ResultSetMetaData rsmd = rs.getMetaData();
//
//	        boolean hasPercentage = false;
//	        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
//	            if (rsmd.getColumnName(i).equalsIgnoreCase("percentage")) {
//	                hasPercentage = true;
//	                break;
//	            }
//	        }
//
//	        String query = hasPercentage
//	            ? "SELECT name, class, stream, username, percentage FROM student"
//	            : "SELECT name, class, stream, username FROM student";
//
//	        Statement stmt = conn.createStatement();
//	        rs = stmt.executeQuery(query);
//
//	        System.out.printf("%-20s %-8s %-15s %-15s %-12s%n",
//	                "Name", "Class", "Stream", "Username", "Percentage");
//
//	        System.out.println("--------------------------------------------------------------------------");
//
//	        while (rs.next()) {
//	            String name = rs.getString("name");
//	            String className = rs.getString("class");
//	            String stream = rs.getString("stream");
//	            String username = rs.getString("username");
//	            String percentage = hasPercentage ? rs.getString("percentage") + "%" : "N/A";
//
//	            System.out.printf("%-20s %-8s %-15s %-15s %-12s%n",
//	                    name, className, stream, username, percentage);
//	        }
//
//	    } catch (SQLException e) {
//	        System.out.println("Error fetching student data:");
//	        e.printStackTrace();
//	    }
//	}

//-----------------------------------------------------------------------------------END OF LOGIN ADMIN-----------------------------------------------------------------
//-----------------------------------------------------------------------------------ADMIN LOGIN OPPTIONS---------------------------------------------------------------

	private static void adminMenu() {
	    while (true) {
	        System.out.println("\n===== Admin Dashboard =====");
	        System.out.println("1. Add Student");
	        System.out.println("2. Remove Student");
	        System.out.println("3. Add Admin");
	        System.out.println("4. Remove Admin");
	        System.out.println("5. Add Teacher");
	        System.out.println("6. Remove Teacher");
	        System.out.println("7. Remove User");
	        System.out.println("8. Log Out");
	        System.out.print("Select an option: ");
	        int choice = Integer.parseInt(scanner.nextLine());

	        switch (choice) {
	            case 1: addStudent(); break;
	            case 2: removeStudent(); break;
	            case 3: addAdmin(); break;
	            case 4: removeAdmin(); break;
	            case 5: addTeacher(); break;
	            case 6: removeTeacher(); break;
	            case 7: removeUser(); break;
	            case 8: System.out.println("Logging out..."); return;
	            default: System.out.println("Invalid option. Try again.");
	        }
	    }
	}
	
	private static void addStudent() {
	    System.out.println("\n--- Add Student ---");

	    System.out.print("Name: ");
	    String name = scanner.nextLine();

	    System.out.print("Email: ");
	    String email = scanner.nextLine();

	    System.out.print("Phone No.: ");
	    String phone = scanner.nextLine();

	    int classNum;
	    while (true) {
	        System.out.print("Class (1-12): ");
	        try {
	            classNum = Integer.parseInt(scanner.nextLine());
	            if (classNum >= 1 && classNum <= 12) break;
	            else System.out.println("Class must be between 1 and 12.");
	        } catch (NumberFormatException e) {
	            System.out.println("Please enter a valid number.");
	        }
	    }

	    String stream = "N/A";
	    if (classNum == 11 || classNum == 12) {
	        System.out.println("Select Stream: ");
	        System.out.println("1. Arts");
	        System.out.println("2. Commerce");
	        System.out.println("3. Science - PCM");
	        System.out.println("4. Science - PCB");
	        System.out.println("5. Science - PCMB");
	        System.out.print("Enter choice: ");
	        int streamChoice = Integer.parseInt(scanner.nextLine());

	        switch (streamChoice) {
	            case 1: stream = "Arts"; break;
	            case 2: stream = "Commerce"; break;
	            case 3: stream = "Science - PCM"; break;
	            case 4: stream = "Science - PCB"; break;
	            case 5: stream = "Science - PCMB"; break;
	            default: System.out.println("Invalid stream selected. Setting as 'Unknown'."); stream = "Unknown";
	        }
	    }

	    try (Connection conn = UserDAO.getConnection()) {

	        // Create tables if not exist
	        Statement stmt = conn.createStatement();

	        String createCommon = "CREATE TABLE IF NOT EXISTS students (" +
	                "id INT AUTO_INCREMENT PRIMARY KEY, " +
	                "name VARCHAR(100), email VARCHAR(100), phone VARCHAR(15), classNum INT, stream VARCHAR(50))";
	        stmt.executeUpdate(createCommon);

	        String createClass1_10 = "CREATE TABLE IF NOT EXISTS class_1_10 (" +
	                "id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), email VARCHAR(100), phone VARCHAR(15), classNum INT)";
	        stmt.executeUpdate(createClass1_10);

	        String createClass11 = "CREATE TABLE IF NOT EXISTS class_11 (" +
	                "id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), email VARCHAR(100), phone VARCHAR(15), stream VARCHAR(50))";
	        stmt.executeUpdate(createClass11);

	        String createClass12 = "CREATE TABLE IF NOT EXISTS class_12 (" +
	                "id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), email VARCHAR(100), phone VARCHAR(15), stream VARCHAR(50))";
	        stmt.executeUpdate(createClass12);

	        // Insert into common table
	        String insertCommon = "INSERT INTO students (name, email, phone, classNum, stream) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement psCommon = conn.prepareStatement(insertCommon);
	        psCommon.setString(1, name);
	        psCommon.setString(2, email);
	        psCommon.setString(3, phone);
	        psCommon.setInt(4, classNum);
	        psCommon.setString(5, stream);
	        psCommon.executeUpdate();

	        // Insert into class-specific tables
	        if (classNum >= 1 && classNum <= 10) {
	            String insertC1_10 = "INSERT INTO class_1_10 (name, email, phone, classNum) VALUES (?, ?, ?, ?)";
	            PreparedStatement ps = conn.prepareStatement(insertC1_10);
	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, phone);
	            ps.setInt(4, classNum);
	            ps.executeUpdate();
	        } else if (classNum == 11) {
	            String insertC11 = "INSERT INTO class_11 (name, email, phone, stream) VALUES (?, ?, ?, ?)";
	            PreparedStatement ps = conn.prepareStatement(insertC11);
	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, phone);
	            ps.setString(4, stream);
	            ps.executeUpdate();
	        } else if (classNum == 12) {
	            String insertC12 = "INSERT INTO class_12 (name, email, phone, stream) VALUES (?, ?, ?, ?)";
	            PreparedStatement ps = conn.prepareStatement(insertC12);
	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, phone);
	            ps.setString(4, stream);
	            ps.executeUpdate();
	        }

	        System.out.println("✅ Student added successfully.");

	    } catch (SQLException e) {
	        System.out.println("❌ Error adding student:");
	        e.printStackTrace();
	    }
	}


	
	private static void removeStudent() {
	    System.out.println("\n--- Remove Student ---");

	    try (Connection conn = UserDAO.getConnection()) {
	        // Show all students
	        String showQuery = "SELECT name, unique_id, class, username, phone FROM student";
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(showQuery);

	        System.out.println("\n--- List of Students ---");
	        boolean hasData = false;
	        while (rs.next()) {
	            hasData = true;
	            System.out.println("Name: " + rs.getString("name") +
	                               ", Unique ID: " + rs.getString("unique_id") +
	                               ", Class: " + rs.getString("class") +
	                               ", Username: " + rs.getString("username") +
	                               ", Phone: " + rs.getString("phone"));
	        }

	        if (!hasData) {
	            System.out.println("No students found.");
	            return;
	        }

	        // Input details
	        System.out.print("\nEnter Name of Student to remove: ");
	        String name = scanner.nextLine();
	        System.out.print("Enter Unique ID: ");
	        String uniqueId = scanner.nextLine();
	        System.out.print("Enter Class: ");
	        String studentClass = scanner.nextLine();

	        // Check if student exists
	        String checkQuery = "SELECT * FROM student WHERE unique_id = ? AND name = ? AND class = ?";
	        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
	        checkStmt.setString(1, uniqueId);
	        checkStmt.setString(2, name);
	        checkStmt.setString(3, studentClass);
	        ResultSet checkRs = checkStmt.executeQuery();

	        if (!checkRs.next()) {
	            System.out.println("No matching student found.");
	            return;
	        }

	        // Confirm deletion
	        System.out.print("Are you sure you want to delete this student? (Y/N): ");
	        String confirm = scanner.nextLine().trim().toUpperCase();

	        if (confirm.equals("Y")) {
	            String deleteQuery = "DELETE FROM student WHERE unique_id = ? AND name = ? AND class = ?";
	            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
	            deleteStmt.setString(1, uniqueId);
	            deleteStmt.setString(2, name);
	            deleteStmt.setString(3, studentClass);

	            int rows = deleteStmt.executeUpdate();
	            System.out.println(rows > 0 ? "Student removed successfully." : "Failed to remove student.");
	        } else {
	            System.out.println("Deletion cancelled.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Error while removing student:");
	        e.printStackTrace();
	    }
	}
	
	private static void addAdmin() {
	    System.out.println("\n--- Add Admin ---");
	    System.out.print("Name: ");
	    String name = scanner.nextLine();
	    
	    System.out.print("Email: ");
	    String email = scanner.nextLine();
	    
	    System.out.print("Phone No.: ");
	    String phone = scanner.nextLine();
	    
	    System.out.print("Designation: ");
	    String designation = scanner.nextLine().toLowerCase(); // use lowercase to match table naming convention
	    
	    System.out.print("Username: ");
	    String username = scanner.nextLine();
	    System.out.print("Password: ");
	    String password = readPassword();
	    String encryptedPassword = UserDAO.encryptPassword(password);
	    
	    String table = "admin_" + designation;

	    try (Connection conn = UserDAO.getConnection()) {
	        // Create the table if it doesn't exist
	        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + table + " ("
	                + "name VARCHAR(100), "
	                + "email VARCHAR(100), "
	                + "phone VARCHAR(15), "
	                + "username VARCHAR(50) PRIMARY KEY, "
	                + "password VARCHAR(255))";
	        Statement stmt = conn.createStatement();
	        stmt.executeUpdate(createTableQuery);

	        // Check if username already exists
	        String checkQuery = "SELECT username FROM " + table + " WHERE username = ?";
	        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
	        checkStmt.setString(1, username);
	        ResultSet rs = checkStmt.executeQuery();

	        if (rs.next()) {
	            System.out.println("Username already exists. Please choose a different username.");
	            return;
	        }

	        // Insert the new admin
	        String insertQuery = "INSERT INTO " + table + " (name, email, phone, username, password) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
	        insertStmt.setString(1, name);
	        insertStmt.setString(2, email);
	        insertStmt.setString(3, phone);
	        insertStmt.setString(4, username);
	        insertStmt.setString(5, encryptedPassword);

	        int rows = insertStmt.executeUpdate();
	        if (rows > 0) {
	            System.out.println("Admin added successfully.");
	        } else {
	            System.out.println("Failed to add admin. Please try again.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Error while adding admin:");
	        e.printStackTrace();
	    }
	}
	
	private static void removeAdmin() {
	    System.out.println("\n--- Remove Admin ---");
	    System.out.print("Designation: ");
	    String designation = scanner.nextLine().toLowerCase();
	    String table = "admin_" + designation;

	    try (Connection conn = UserDAO.getConnection()) {
	        // Display all admins in the table
	        String showQuery = "SELECT name, username, email, phone FROM " + table;
	        Statement stmt = conn.createStatement();
	        ResultSet result = stmt.executeQuery(showQuery);

	        System.out.println("\n--- List of Admins in '" + table + "' ---");
	        boolean hasData = false;
	        while (result.next()) {
	            hasData = true;
	            System.out.println("Name: " + result.getString("name") +
	                               ", Username: " + result.getString("username") +
	                               ", Email: " + result.getString("email") +
	                               ", Phone: " + result.getString("phone"));
	        }

	        if (!hasData) {
	            System.out.println("No admins found in the table.");
	            return;
	        }

	        // Take admin details to remove
	        System.out.print("\nEnter Name of Admin to remove: ");
	        String name = scanner.nextLine();
	        System.out.print("Enter Username of Admin to remove: ");
	        String username = scanner.nextLine();

	        // Check if admin exists
	        String checkQuery = "SELECT * FROM " + table + " WHERE username = ? AND name = ?";
	        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
	        checkStmt.setString(1, username);
	        checkStmt.setString(2, name);
	        ResultSet rs = checkStmt.executeQuery();

	        if (!rs.next()) {
	            System.out.println("No matching admin found with the provided name and username.");
	            return;
	        }

	        // Confirm deletion
	        System.out.print("Are you sure you want to delete this admin? (Y/N): ");
	        String confirm = scanner.nextLine().trim().toUpperCase();

	        if (confirm.equals("Y")) {
	            String deleteQuery = "DELETE FROM " + table + " WHERE username = ? AND name = ?";
	            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
	            deleteStmt.setString(1, username);
	            deleteStmt.setString(2, name);

	            int rowsAffected = deleteStmt.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Admin removed successfully.");
	            } else {
	                System.out.println("Failed to remove admin. Please try again.");
	            }
	        } else {
	            System.out.println("Deletion cancelled.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Error while removing admin:");
	        e.printStackTrace();
	    }
	}
	private static void addTeacher() {
	    System.out.println("\n--- Add Teacher ---");

	    System.out.print("Name: ");
	    String name = scanner.nextLine();

	    System.out.print("Email: ");
	    String email = scanner.nextLine();

	    System.out.print("Phone No.: ");
	    String phone = scanner.nextLine();

	    System.out.print("Course: ");
	    String course = scanner.nextLine();

	    System.out.print("Username: ");
	    String username = scanner.nextLine();
	    System.out.print("Password: ");
	    String password = readPassword();
	    String encryptedPassword = UserDAO.encryptPassword(password);

	    try (Connection conn = UserDAO.getConnection()) {
	        // Create teacher table if it doesn't exist
	        String createTable = "CREATE TABLE IF NOT EXISTS admin_teacher ("
	                + "name VARCHAR(100), "
	                + "email VARCHAR(100), "
	                + "phone VARCHAR(15), "
	                + "course VARCHAR(100), "
	                + "username VARCHAR(50) PRIMARY KEY, "
	                + "password VARCHAR(255))";

	        Statement stmt = conn.createStatement();
	        stmt.executeUpdate(createTable);

	        // Check for duplicate username
	        String checkQuery = "SELECT username FROM admin_teacher WHERE username = ?";
	        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
	        checkStmt.setString(1, username);
	        ResultSet rs = checkStmt.executeQuery();

	        if (rs.next()) {
	            System.out.println("Username already exists. Please choose another.");
	            return;
	        }

	        // Insert new teacher
	        String insertQuery = "INSERT INTO admin_teacher (name, email, phone, course, username, password) VALUES (?, ?, ?, ?, ?, ?)";
	        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
	        insertStmt.setString(1, name);
	        insertStmt.setString(2, email);
	        insertStmt.setString(3, phone);
	        insertStmt.setString(4, course);
	        insertStmt.setString(5, username);
	        insertStmt.setString(6, encryptedPassword);

	        int rowsInserted = insertStmt.executeUpdate();
	        if (rowsInserted > 0) {
	            System.out.println("Teacher added successfully.");
	        } else {
	            System.out.println("Failed to add teacher. Please try again.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Error while adding teacher:");
	        e.printStackTrace();
	    }
	}



	private static void removeTeacher() {
	    System.out.println("\n--- Remove Teacher ---");

	    try (Connection conn = UserDAO.getConnection()) {
	        // Show all teachers
	        String showQuery = "SELECT name, username, email, phone, course FROM admin_teacher";
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(showQuery);

	        System.out.println("\n--- List of Teachers ---");
	        boolean hasData = false;
	        while (rs.next()) {
	            hasData = true;
	            System.out.println("Name: " + rs.getString("name") +
	                               ", Username: " + rs.getString("username") +
	                               ", Email: " + rs.getString("email") +
	                               ", Phone: " + rs.getString("phone") +
	                               ", Course: " + rs.getString("course"));
	        }

	        if (!hasData) {
	            System.out.println("No teachers found.");
	            return;
	        }

	        // Take input to delete
	        System.out.print("\nEnter Name of Teacher to remove: ");
	        String name = scanner.nextLine();
	        System.out.print("Enter Username of Teacher to remove: ");
	        String username = scanner.nextLine();
	        System.out.print("Enter Course: ");
	        String course = scanner.nextLine();

	        // Check if teacher exists
	        String checkQuery = "SELECT * FROM admin_teacher WHERE username = ? AND name = ? AND course = ?";
	        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
	        checkStmt.setString(1, username);
	        checkStmt.setString(2, name);
	        checkStmt.setString(3, course);
	        ResultSet checkRs = checkStmt.executeQuery();

	        if (!checkRs.next()) {
	            System.out.println("No matching teacher found.");
	            return;
	        }

	        // Confirm deletion
	        System.out.print("Are you sure you want to delete this teacher? (Y/N): ");
	        String confirm = scanner.nextLine().trim().toUpperCase();

	        if (confirm.equals("Y")) {
	            String deleteQuery = "DELETE FROM admin_teacher WHERE username = ? AND name = ? AND course = ?";
	            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
	            deleteStmt.setString(1, username);
	            deleteStmt.setString(2, name);
	            deleteStmt.setString(3, course);

	            int rows = deleteStmt.executeUpdate();
	            System.out.println(rows > 0 ? "Teacher removed successfully." : "Failed to remove teacher.");
	        } else {
	            System.out.println("Deletion cancelled.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Error while removing teacher:");
	        e.printStackTrace();
	    }
	}
	
	private static void removeUser() {
	    System.out.println("\n--- Remove User ---");

	    try (Connection conn = UserDAO.getConnection()) {
	        // Show all users
	        String showQuery = "SELECT username, role, created_at FROM user";
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(showQuery);

	        System.out.println("\n--- List of Users ---");
	        boolean hasData = false;
	        while (rs.next()) {
	            hasData = true;
	            System.out.println("Username: " + rs.getString("username") +
	                               ", Role: " + rs.getString("role") +
	                               ", Created At: " + rs.getString("created_at"));
	        }

	        if (!hasData) {
	            System.out.println("No users found.");
	            return;
	        }

	        // Input username to delete
	        System.out.print("\nEnter Username of the user to remove: ");
	        String username = scanner.nextLine();

	        // Check if user exists
	        String checkQuery = "SELECT * FROM user WHERE username = ?";
	        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
	        checkStmt.setString(1, username);
	        ResultSet checkRs = checkStmt.executeQuery();

	        if (!checkRs.next()) {
	            System.out.println("User not found with that username.");
	            return;
	        }

	        // Confirm deletion
	        System.out.print("Are you sure you want to delete this user? (Y/N): ");
	        String confirm = scanner.nextLine().trim().toUpperCase();

	        if (confirm.equals("Y")) {
	            String deleteQuery = "DELETE FROM user WHERE username = ?";
	            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
	            deleteStmt.setString(1, username);

	            int rows = deleteStmt.executeUpdate();
	            System.out.println(rows > 0 ? "User removed successfully." : "Failed to remove user.");
	        } else {
	            System.out.println("Deletion cancelled.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Error while removing user:");
	        e.printStackTrace();
	    }
	}
//-----------------------------------------------------------------------------END OF ADMIN LOG IN OPTIONS--------------------------------------------------------------
	
	
//-----------------------------------------------------------------------------LOGIN USER PAGE--------------------------------------------------------------------------

	private static void loginUser() {
	    try {
	        System.out.print("Enter Username: ");
	        String username = scanner.nextLine().trim();

	        String password;
	        Console console = System.console();
	        if (console != null) {
	            char[] passwordChars = console.readPassword("Enter Password: ");
	            password = new String(passwordChars);
	        } else {
	            System.out.print("Enter Password: ");
	            password = scanner.nextLine();
	        }

	        // Consistent hashing
	        String hashedPassword = UserDAO.encryptPassword(password);

	        Connection conn = UserDAO.getConnection();
	        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, username);
	        pstmt.setString(2, hashedPassword);

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            System.out.println("✅ Login successful. Welcome, " + rs.getString("name") + "!");
	            userMenu();
	        } else {
	            System.out.println("❌ Invalid username or password.");
	        }

	        rs.close();
	        pstmt.close();
	        conn.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("❌ Error during user login.");
	    }
	}

//*****************************************************************************USER MENU********************************************************************************

    private static void userMenu() {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Display All Student Marks with Aggregate Percentage");
            System.out.println("2. Display All Teacher Names");
            System.out.println("3. Log Out");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    displayAllStudentMarks();
                    break;
                case "2":
                    displayTeacherNames();
                    break;
                case "3":
                    System.out.println("Logging out...\n");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void displayAllStudentMarks() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            System.out.print("Enter class (1-12): ");
            String className = scanner.nextLine().trim();
            String tableName = getClassTableName(className);

            if (tableName == null) {
                System.out.println("Invalid class entered.");
                return;
            }
            List<String> subjectColumns = new ArrayList<>();
            String columnQuery = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " +
                                 "WHERE TABLE_NAME = ? AND COLUMN_NAME LIKE 'subject_%'";

            PreparedStatement colStmt = conn.prepareStatement(columnQuery);
            colStmt.setString(1, tableName);
            ResultSet colRs = colStmt.executeQuery();
            while (colRs.next()) {
                subjectColumns.add(colRs.getString("COLUMN_NAME"));
            }
            colRs.close();
            colStmt.close();

            if (subjectColumns.isEmpty()) {
                System.out.println("No subject data found for this class.");
                return;
            }

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT name, unique_id, ");

            String sumExpr = String.join(" + ", subjectColumns);
            int subjectCount = subjectColumns.size();
            sql.append("(").append(sumExpr).append(")/").append(subjectCount).append(" AS aggregate_percentage ");
            sql.append("FROM ").append(tableName).append(" ORDER BY aggregate_percentage DESC");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());

            System.out.println("\n--- Student Marks with Aggregate Percentage ---");
            System.out.printf("%-20s %-15s %-10s\n", "Name", "Unique ID", "Percentage");
            while (rs.next()) {
                System.out.printf("%-20s %-15s %-10.2f\n",
                        rs.getString("name"),
                        rs.getString("unique_id"),
                        rs.getDouble("aggregate_percentage"));
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error displaying student marks.");
        }
    }

	private static void loginStudent() {
	    try {
	        System.out.print("Enter Username: ");
	        String username = scanner.nextLine().trim();

	        String password;
	        Console console = System.console();
	        if (console != null) {
	            char[] passwordChars = console.readPassword("Enter Password: ");
	            password = new String(passwordChars);
	        } else {
	            System.out.print("Enter Password: ");
	            password = scanner.nextLine();
	        }

	        String hashedPassword = hashPassword(password);

	        System.out.print("Enter Class (1-12): ");
	        String className = scanner.nextLine().trim();
	        String tableName = getClassTableName(className);
	        if (tableName == null) {
	            System.out.println("Invalid class entered.");
	            return;
	        }

	        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

	        String query = "SELECT * FROM " + tableName + " WHERE username = ? AND password = ?";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, username);
	        pstmt.setString(2, hashedPassword);

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            String name = rs.getString("name");
	            String uniqueId = rs.getString("unique_id");

	            System.out.println(" Login successful. Welcome, " + name + "!");
	            studentMenu(name, uniqueId, className);
	        } else {
	            System.out.println(" Invalid username or password.");
	        }

	        rs.close();
	        pstmt.close();
	        conn.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println(" Error during student login.");
	    }
	}
	private static void studentMenu(String name, String uniqueId, String className) {
	    while (true) {
	        System.out.println("\n--- Student Menu ---");
	        System.out.println("1. Display Marks");
	        System.out.println("2. Display Class Performance");
	        System.out.println("3. Percentage of Classes Attended");
	        System.out.println("4. Change Password");
	        System.out.println("5. Log Out");
	        System.out.print("Enter your choice: ");
	        String choice = scanner.nextLine().trim();

	        switch (choice) {
	            case "1":
	                displayStudentMarks();
	                break;
	            case "2":
	                displayClassPerformance();
	                break;
	            case "3":
	                displayAttendancePercentage(uniqueId, className);
	                break;
	            case "4":
	                changeStudentPassword(uniqueId, className);
	                break;
	            case "5":
	                System.out.println("Logging out...\n");
	                return;
	            default:
	                System.out.println("Invalid choice. Try again.");
	        }
	    }
	}


	private static void changeStudentPassword(String uniqueId, String className) {
	    String tableName = getClassTableName(className);
	    if (tableName == null) {
	        System.out.println("❌ Invalid class.");
	        return;
	    }

	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
	      
	        System.out.print("Enter current password: ");
	        String oldPassword = readPassword();
	        String encryptedOld = hashPassword(oldPassword);

	        String checkSQL = "SELECT * FROM " + tableName + " WHERE unique_id = ? AND password = ?";
	        PreparedStatement checkStmt = conn.prepareStatement(checkSQL);
	        checkStmt.setString(1, uniqueId);
	        checkStmt.setString(2, encryptedOld);
	        ResultSet rs = checkStmt.executeQuery();

	        if (!rs.next()) {
	            System.out.println("❌ Current password is incorrect.");
	            return;
	        }
	        rs.close();
	        checkStmt.close();
	        System.out.print("Enter new password: ");
	        String newPassword = readPassword();
	        System.out.print("Confirm new password: ");
	        String confirmPassword = readPassword();

	        if (!newPassword.equals(confirmPassword)) {
	            System.out.println("❌ Passwords do not match.");
	            return;
	        }
	        String encryptedNew = hashPassword(newPassword);
	        String updateSQL = "UPDATE " + tableName + " SET password = ? WHERE unique_id = ?";
	        PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
	        updateStmt.setString(1, encryptedNew);
	        updateStmt.setString(2, uniqueId);

	        int updated = updateStmt.executeUpdate();
	        updateStmt.close();

	        if (updated > 0) {
	            System.out.println(" Password updated successfully.");
	        } else {
	            System.out.println("Failed to update password.");
	        }

	    } catch (SQLException | NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        System.out.println("Error while changing password.");
	    }
	}
	private static String readPassword() {
	    Console console = System.console();
	    if (console != null) {
	        char[] pwdArray = console.readPassword();
	        return new String(pwdArray);
	    } else {
	        return scanner.nextLine();
	    }
	}
	
	private static void displayAttendancePercentage(String uniqueId, String className) {
	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

	        String presentSQL = "SELECT COUNT(*) AS present_days FROM attendance WHERE student_id = ? AND class_name = ?";
	        PreparedStatement presentStmt = conn.prepareStatement(presentSQL);
	        presentStmt.setString(1, uniqueId);
	        presentStmt.setString(2, className);
	        ResultSet presentRs = presentStmt.executeQuery();

	        int presentDays = 0;
	        if (presentRs.next()) {
	            presentDays = presentRs.getInt("present_days");
	        }
	        presentRs.close();
	        presentStmt.close();
	        String totalSQL = "SELECT COUNT(DISTINCT attendance_date) AS total_days FROM attendance WHERE class_name = ?";
	        PreparedStatement totalStmt = conn.prepareStatement(totalSQL);
	        totalStmt.setString(1, className);
	        ResultSet totalRs = totalStmt.executeQuery();

	        int totalDays = 0;
	        if (totalRs.next()) {
	            totalDays = totalRs.getInt("total_days");
	        }
	        totalRs.close();
	        totalStmt.close();
	        if (totalDays == 0) {
	            System.out.println("ℹ️ No attendance data available for class " + className);
	            return;
	        }

	        double percentage = ((double) presentDays / totalDays) * 100;

	        System.out.println("\n📅 Attendance Report:");
	        System.out.println("Student ID   : " + uniqueId);
	        System.out.println("Class        : " + className);
	        System.out.println("Present Days : " + presentDays);
	        System.out.println("Total Days   : " + totalDays);
	        System.out.printf ("Percentage   : %.2f%%\n", percentage);

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("⚠️ Error calculating attendance percentage.");
	    }
	}


	private static void displayClassPerformance() {
	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
	        System.out.print("Enter student name: ");
	        String studentName = scanner.nextLine().trim();

	        System.out.print("Enter unique ID: ");
	        String uniqueId = scanner.nextLine().trim();

	        System.out.print("Enter class (1–12): ");
	        String className = scanner.nextLine().trim();
	        String tableName = getClassTableName(className);

	        if (tableName == null) {
	            System.out.println("❌ Invalid class.");
	            return;
	        }

	        List<String> subjectColumns = new ArrayList<>();
	        String colQuery = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " +
	                          "WHERE TABLE_NAME = ? AND COLUMN_NAME LIKE 'subject_%'";
	        PreparedStatement colStmt = conn.prepareStatement(colQuery);
	        colStmt.setString(1, tableName);
	        ResultSet colRs = colStmt.executeQuery();
	        while (colRs.next()) {
	            subjectColumns.add(colRs.getString("COLUMN_NAME"));
	        }
	        colRs.close();
	        colStmt.close();

	        if (subjectColumns.isEmpty()) {
	            System.out.println("⚠️ No subjects found for this class.");
	            return;
	        }

	        int totalSubjects = subjectColumns.size();
	        StringBuilder query = new StringBuilder("SELECT name, unique_id, ");
	        for (String subject : subjectColumns) {
	            query.append(subject).append(", ");
	        }
	        query.setLength(query.length() - 2);
	        query.append(" FROM ").append(tableName);

	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(query.toString());

	        List<Map<String, Object>> results = new ArrayList<>();

	        while (rs.next()) {
	            String name = rs.getString("name");
	            String uid = rs.getString("unique_id");
	            int totalMarks = 0;

	            for (String subject : subjectColumns) {
	                totalMarks += rs.getInt(subject);
	            }

	            double percentage = (double) totalMarks / totalSubjects;

	            Map<String, Object> student = new HashMap<>();
	            student.put("name", name);
	            student.put("unique_id", uid);
	            student.put("percentage", percentage);
	            results.add(student);
	        }
	        rs.close();

	        // Step 3: Sort by percentage descending
	        results.sort((a, b) -> Double.compare((double) b.get("percentage"), (double) a.get("percentage")));

	        // Step 4: Display leaderboard
	        System.out.println("\nClass Performance for Class " + className + ":");
	        System.out.printf("%-20s %-12s %-10s\n", "Name", "Unique ID", "Percentage");
	        System.out.println("-----------------------------------------------------");
	        for (Map<String, Object> student : results) {
	            System.out.printf("%-20s %-12s %-10.2f%%\n",
	                    student.get("name"),
	                    student.get("unique_id"),
	                    (double) student.get("percentage"));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error while displaying class performance.");
	    }
	}


	private static void displayStudentMarks() {
	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
	        System.out.print("Enter student name: ");
	        String name = scanner.nextLine().trim();

	        System.out.print("Enter unique ID: ");
	        String uniqueId = scanner.nextLine().trim();

	        System.out.print("Enter class (1–12): ");
	        String className = scanner.nextLine().trim();
	        String tableName = getClassTableName(className);

	        if (tableName == null) {
	            System.out.println("❌ Invalid class.");
	            return;
	        }
	        List<String> subjectColumns = new ArrayList<>();
	        String colQuery = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " +
	                          "WHERE TABLE_NAME = ? AND COLUMN_NAME LIKE 'subject_%'";
	        PreparedStatement colStmt = conn.prepareStatement(colQuery);
	        colStmt.setString(1, tableName);
	        ResultSet colRs = colStmt.executeQuery();
	        while (colRs.next()) {
	            subjectColumns.add(colRs.getString("COLUMN_NAME"));
	        }
	        colRs.close();
	        colStmt.close();

	        if (subjectColumns.isEmpty()) {
	            System.out.println("⚠️ No subjects found.");
	            return;
	        }
	        StringBuilder query = new StringBuilder("SELECT ");
	        for (String subject : subjectColumns) {
	            query.append(subject).append(", ");
	        }
	        query.setLength(query.length() - 2);
	        query.append(" FROM ").append(tableName).append(" WHERE unique_id = ?");

	        PreparedStatement stmt = conn.prepareStatement(query.toString());
	        stmt.setString(1, uniqueId);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            System.out.println("\n📋 Marks for " + name + " (ID: " + uniqueId + ")");
	            int totalMarks = 0;

	            for (String subject : subjectColumns) {
	                int mark = rs.getInt(subject);
	                totalMarks += mark;
	                System.out.printf("- %-15s : %3d\n", subject, mark);
	            }

	            double percentage = (double) totalMarks / subjectColumns.size();
	            System.out.printf("📈 Percentage: %.2f%%\n", percentage);
	        } else {
	            System.out.println("❌ Student not found.");
	        }

	        rs.close();
	        stmt.close();

	    } catch (SQLException | NumberFormatException e) {
	        e.printStackTrace();
	        System.out.println(" Error while displaying student marks.");
	    }
	}


	private static void loginTemporaryStudent() {
        try {
            System.out.print("Enter Username: ");
            String username = scanner.nextLine().trim();

            String password;
            Console console = System.console();
            if (console != null) {
                char[] passwordChars = console.readPassword("Enter Password: ");
                password = new String(passwordChars);
            } else {
                System.out.print("Enter Password: ");
                password = scanner.nextLine();
            }

            String hashedPassword = hashPassword(password);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String query = "SELECT * FROM temp_student WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Timestamp createdAt = rs.getTimestamp("created_at");
                long daysElapsed = Duration.between(createdAt.toInstant(), Instant.now()).toDays();

                if (daysElapsed > 7) {
                    System.out.println("❌ Account expired. Temporary users are valid for 7 days.");
                    deleteTemporaryStudent(); 
                } else {
                    System.out.println("✅ Login successful. Welcome, " + rs.getString("name") + "!");
                    temporaryStudentMenu();
                }
            } else {
                System.out.println("❌ Invalid username or password.");
            }
            
            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("⚠️ Error during temporary student login.");
        }
    }

	private static void deleteTemporaryStudent() {
	    String tableName = "temporary_students";

	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
	        String deleteSQL = "DELETE FROM " + tableName + " WHERE created_at < NOW() - INTERVAL 7 DAY";
	        PreparedStatement stmt = conn.prepareStatement(deleteSQL);

	        int rowsDeleted = stmt.executeUpdate();
	        stmt.close();

	        if (rowsDeleted > 0) {
	            System.out.println("✅ Deleted " + rowsDeleted + " temporary student(s) older than 7 days.");
	        } else {
	            System.out.println("ℹ️ No expired temporary student accounts found.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error while deleting temporary students.");
	    }
	}


	private static String hashPassword(String password) throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
	    StringBuilder sb = new StringBuilder();
	    for (byte b : hashBytes) {
	        sb.append(String.format("%02x", b));
	    }
	    return sb.toString();
	}

    private static void temporaryStudentMenu() {
        while (true) {
            System.out.println("\n--- Temporary Student Menu ---");
            System.out.println("1. Display Leaderboard");
            System.out.println("2. Display All Teacher Names");
            System.out.println("3. Display All Faculty Designations");
            System.out.println("4. Log Out");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    displayLeaderboard();
                    break;
                case "2":
                    displayTeacherNames();
                    break;
                case "3":
                    displayFacultyDesignations();
                    break;
                case "4":
                    System.out.println("Logging out...\n");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void displayLeaderboard() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            System.out.print("Enter class (1-12): ");
            String className = scanner.nextLine().trim();

            String tableName = getClassTableName(className);
            if (tableName == null) {
                System.out.println("Invalid class entered.");
                return;
            }

            String query = "SELECT name, unique_id, " +
                           "(SELECT COUNT(*) FROM information_schema.columns " +
                           "WHERE table_name = ? AND column_name LIKE 'subject_%') AS subject_count, " +
                           "(subject_1 + subject_2 + subject_3 + subject_4 + subject_5) / 5 AS percentage " +
                           "FROM " + tableName + " ORDER BY percentage DESC";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, tableName);

            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- Leaderboard for Class " + className + " ---");
            System.out.printf("%-20s %-15s %-10s\n", "Name", "Unique ID", "Percentage");
            while (rs.next()) {
                System.out.printf("%-20s %-15s %-10.2f\n",
                        rs.getString("name"),
                        rs.getString("unique_id"),
                        rs.getDouble("percentage"));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error displaying leaderboard.");
        }
    }
    private static void displayTeacherNames() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "SELECT name FROM admin_teacher";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n--- List of Teachers ---");
            while (rs.next()) {
                System.out.println("- " + rs.getString("name"));
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error retrieving teacher names.");
        }
    }
    private static void displayFacultyDesignations() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "SELECT DISTINCT designation FROM admins";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n--- Faculty Designations ---");
            while (rs.next()) {
                System.out.println("- " + rs.getString("designation"));
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error retrieving faculty designations.");
        }
    }
    private static String getClassTableName(String className) {
        switch (className) {
            case "1": case "2": case "3": case "4": case "5":
            case "6": case "7": case "8": case "9": case "10":
                return "class_" + className;
            case "11": case "12":
                return "class_" + className + "_streams";
            default:
                return null;
        }
    }

    private static void loginTeacher() {
        try {
            System.out.print("Enter Username: ");
            String username = scanner.nextLine().trim();

            String password;
            Console console = System.console();
            if (console != null) {
                char[] passwordChars = console.readPassword("Enter Password: ");
                password = new String(passwordChars);
            } else {
                System.out.print("Enter Password: ");
                password = scanner.nextLine();
            }

            // Use same encryption method used in registration
            String encryptedPassword = UserDAO.encryptPassword(password);

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String query = "SELECT * FROM admin_teacher WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, encryptedPassword);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n✅ Teacher Login Successful. Welcome, " + rs.getString("name") + "!");
                String teacherName = rs.getString("name"); // FIXED: pass actual name
                teacherMenu(teacherName);
            } else {
                System.out.println(" Invalid username or password.");
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" Error occurred during teacher login.");
        }
    }



	private static void teacherMenu(String teacherName) {
	    while (true) {
	        System.out.println("\n--- Teacher Menu ---");
	        System.out.println("1. Add Marks");
	        System.out.println("2. Edit Marks");
	        System.out.println("3. Remove Marks");
	        System.out.println("4. Add Attendance");
	        System.out.println("5. Remove Attendance");
	        System.out.println("6. Log Out");
	        System.out.print("Enter your choice: ");
	        String choice = scanner.nextLine().trim();

	        switch (choice) {
	            case "1":
	                addMarks();
	                break;
	            case "2":
	                editMarks();
	                break;
	            case "3":
	                removeMarks();
	                break;
	            case "4":
	                addAttendance();
	                break;
	            case "5":
	                removeAttendance();
	                break;
	            case "6":
	                System.out.println("Logging out...");
	                return;
	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }
	    }
	}
	private static void removeAttendance() {
	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
	        System.out.print("Enter class (1–12): ");
	        String className = scanner.nextLine().trim();
	        String tableName = getClassTableName(className);

	        if (tableName == null) {
	            System.out.println("Invalid class.");
	            return;
	        }
	        System.out.println("\n--- Students in Class " + className + " ---");
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT name, unique_id FROM " + tableName);
	        while (rs.next()) {
	            System.out.printf("- %-20s ID: %s\n", rs.getString("name"), rs.getString("unique_id"));
	        }
	        rs.close();
	        System.out.print("\nEnter Student Unique ID: ");
	        String studentId = scanner.nextLine().trim();
	        System.out.println("\nRemove Attendance By:");
	        System.out.println("1. Specific Date");
	        System.out.println("2. Date Range");
	        System.out.println("3. Multiple Dates");
	        System.out.print("Enter choice: ");
	        String mode = scanner.nextLine().trim();

	        List<LocalDate> dates = new ArrayList<>();

	        switch (mode) {
	            case "1":
	                System.out.print("Enter date (yyyy-mm-dd): ");
	                dates.add(LocalDate.parse(scanner.nextLine().trim()));
	                break;
	            case "2":
	                System.out.print("Enter start date (yyyy-mm-dd): ");
	                LocalDate start = LocalDate.parse(scanner.nextLine().trim());
	                System.out.print("Enter end date (yyyy-mm-dd): ");
	                LocalDate end = LocalDate.parse(scanner.nextLine().trim());
	                for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
	                    dates.add(d);
	                }
	                break;
	            case "3":
	                System.out.print("Enter number of dates: ");
	                int count = Integer.parseInt(scanner.nextLine().trim());
	                for (int i = 0; i < count; i++) {
	                    System.out.print("Enter date " + (i + 1) + " (yyyy-mm-dd): ");
	                    dates.add(LocalDate.parse(scanner.nextLine().trim()));
	                }
	                break;
	            default:
	                System.out.println("Invalid choice.");
	                return;
	        }
	        String deleteSQL = "DELETE FROM attendance WHERE student_id = ? AND attendance_date = ?";
	        PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);

	        int deleted = 0;
	        for (LocalDate date : dates) {
	            deleteStmt.setString(1, studentId);
	            deleteStmt.setDate(2, Date.valueOf(date));
	            deleted += deleteStmt.executeUpdate();
	        }

	        deleteStmt.close();
	        System.out.println("Attendance removed for " + deleted + " day(s).");

	    } catch (SQLException e) {
	        System.err.println("SQLException caught:");
	        System.err.println("Message: " + e.getMessage());
	        System.err.println("SQLState: " + e.getSQLState());
	        System.err.println("Error Code: " + e.getErrorCode());
	        e.printStackTrace();
	    }
	}

	private static void addAttendance() {
	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
	        System.out.print("Enter class (1–12): ");
	        String className = scanner.nextLine().trim();
	        String tableName = getClassTableName(className);

	        if (tableName == null) {
	            System.out.println("Invalid class.");
	            return;
	        }
	        System.out.println("\n--- Students in Class " + className + " ---");
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT name, unique_id FROM " + tableName);
	        while (rs.next()) {
	            System.out.printf("- %-20s ID: %s\n", rs.getString("name"), rs.getString("unique_id"));
	        }
	        rs.close();
	        System.out.print("\nEnter Unique ID of student: ");
	        String studentId = scanner.nextLine().trim();
	        System.out.println("\nAdd Attendance By:");
	        System.out.println("1. Specific Date");
	        System.out.println("2. Date Range");
	        System.out.println("3. Multiple Specific Dates");
	        System.out.print("Enter choice: ");
	        String mode = scanner.nextLine().trim();

	        List<LocalDate> dates = new ArrayList<>();

	        switch (mode) {
	            case "1":
	                System.out.print("Enter date (yyyy-mm-dd): ");
	                dates.add(LocalDate.parse(scanner.nextLine().trim()));
	                break;
	            case "2":
	                System.out.print("Enter start date (yyyy-mm-dd): ");
	                LocalDate start = LocalDate.parse(scanner.nextLine().trim());
	                System.out.print("Enter end date (yyyy-mm-dd): ");
	                LocalDate end = LocalDate.parse(scanner.nextLine().trim());
	                for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
	                    dates.add(d);
	                }
	                break;
	            case "3":
	                System.out.print("Enter number of days: ");
	                int n = Integer.parseInt(scanner.nextLine().trim());
	                for (int i = 0; i < n; i++) {
	                    System.out.print("Enter date " + (i + 1) + " (yyyy-mm-dd): ");
	                    dates.add(LocalDate.parse(scanner.nextLine().trim()));
	                }
	                break;
	            default:
	                System.out.println("Invalid choice.");
	                return;
	        }
	        String insertSQL = "INSERT IGNORE INTO attendance (student_id, class_name, attendance_date) VALUES (?, ?, ?)";
	        PreparedStatement insertStmt = conn.prepareStatement(insertSQL);

	        int added = 0;
	        for (LocalDate date : dates) {
	            insertStmt.setString(1, studentId);
	            insertStmt.setString(2, className);
	            insertStmt.setDate(3, Date.valueOf(date));
	            added += insertStmt.executeUpdate();
	        }

	        insertStmt.close();

	        System.out.println("✅ Attendance added for " + added + " day(s).");

	    } catch (SQLException e) {
	        System.err.println("SQLException caught:");
	        System.err.println("Message: " + e.getMessage());
	        System.err.println("SQLState: " + e.getSQLState());
	        System.err.println("Error Code: " + e.getErrorCode());
	        e.printStackTrace();
	    }

	}


	private static void removeMarks() {
	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
	        System.out.print("Enter class (1–12): ");
	        String className = scanner.nextLine().trim();
	        String tableName = getClassTableName(className);

	        if (tableName == null) {
	            System.out.println("Invalid class.");
	            return;
	        }
	        System.out.println("\n--- Students in Class " + className + " ---");
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT name, unique_id FROM " + tableName);
	        while (rs.next()) {
	            System.out.printf("- Name: %-20s ID: %s\n", rs.getString("name"), rs.getString("unique_id"));
	        }
	        rs.close();
	        System.out.print("\nEnter Student Name: ");
	        String name = scanner.nextLine().trim();
	        System.out.print("Enter Unique ID: ");
	        String uniqueId = scanner.nextLine().trim();
	        List<String> subjectColumns = new ArrayList<>();
	        String colQuery = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " +
	                          "WHERE TABLE_NAME = ? AND COLUMN_NAME LIKE 'subject_%'";
	        PreparedStatement colStmt = conn.prepareStatement(colQuery);
	        colStmt.setString(1, tableName);
	        ResultSet colRs = colStmt.executeQuery();
	        while (colRs.next()) {
	            subjectColumns.add(colRs.getString("COLUMN_NAME"));
	        }
	        colRs.close();
	        colStmt.close();

	        if (subjectColumns.isEmpty()) {
	            System.out.println("No subjects found.");
	            return;
	        }
	        System.out.println("\nAvailable Subjects:");
	        for (String subject : subjectColumns) {
	            System.out.println("- " + subject);
	        }

	        System.out.print("Enter subject(s) to remove (comma-separated): ");
	        String[] subjectsToRemove = scanner.nextLine().split(",");
	        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
	        List<String> cleanedSubjects = new ArrayList<>();
	        for (String s : subjectsToRemove) {
	            String subject = s.trim();
	            if (subjectColumns.contains(subject)) {
	                sql.append(subject).append(" = NULL, ");
	                cleanedSubjects.add(subject);
	            } else {
	                System.out.println("Subject '" + subject + "' not found. Skipping.");
	            }
	        }

	        if (cleanedSubjects.isEmpty()) {
	            System.out.println(" No valid subjects to remove.");
	            return;
	        }

	        sql.setLength(sql.length() - 2);
	        sql.append(" WHERE unique_id = ?");

	        PreparedStatement updateStmt = conn.prepareStatement(sql.toString());
	        updateStmt.setString(1, uniqueId);

	        int updated = updateStmt.executeUpdate();
	        if (updated > 0) {
	            System.out.println("Marks removed successfully for selected subject(s).");
	        } else {
	            System.out.println("Failed to remove marks. Check Unique ID.");
	        }

	        updateStmt.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error while removing marks.");
	    }
	}


	private static void editMarks() {
	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
	        System.out.print("Enter class (1–12): ");
	        String className = scanner.nextLine().trim();
	        String tableName = getClassTableName(className);

	        if (tableName == null) {
	            System.out.println("Invalid class.");
	            return;
	        }
	        System.out.println("\n--- Students in Class " + className + " ---");
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT name, unique_id FROM " + tableName);
	        while (rs.next()) {
	            System.out.printf("- Name: %-20s ID: %s\n", rs.getString("name"), rs.getString("unique_id"));
	        }
	        rs.close();

	        System.out.print("\nEnter Student Name: ");
	        String name = scanner.nextLine().trim();
	        System.out.print("Enter Unique ID: ");
	        String uniqueId = scanner.nextLine().trim();
	        List<String> subjectColumns = new ArrayList<>();
	        String colQuery = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " +
	                          "WHERE TABLE_NAME = ? AND COLUMN_NAME LIKE 'subject_%'";
	        PreparedStatement colStmt = conn.prepareStatement(colQuery);
	        colStmt.setString(1, tableName);
	        ResultSet colRs = colStmt.executeQuery();
	        while (colRs.next()) {
	            subjectColumns.add(colRs.getString("COLUMN_NAME"));
	        }
	        colRs.close();
	        colStmt.close();

	        if (subjectColumns.isEmpty()) {
	            System.out.println("No subjects found for this class.");
	            return;
	        }
	        System.out.println("\nAvailable Subjects:");
	        for (String subject : subjectColumns) {
	            System.out.println("- " + subject);
	        }

	        System.out.print("Enter subject(s) to edit (comma-separated): ");
	        String[] subjectsToEdit = scanner.nextLine().split(",");

	        Map<String, Integer> updatedMarks = new LinkedHashMap<>();

	        for (String subject : subjectsToEdit) {
	            String s = subject.trim();
	            if (subjectColumns.contains(s)) {
	                System.out.print("Enter new marks for " + s + ": ");
	                int mark = Integer.parseInt(scanner.nextLine().trim());
	                updatedMarks.put(s, mark);
	            } else {
	                System.out.println("Invalid subject: " + s + " (ignored)");
	            }
	        }

	        if (updatedMarks.isEmpty()) {
	            System.out.println("No valid subjects to update.");
	            return;
	        }
	        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
	        for (String subject : updatedMarks.keySet()) {
	            sql.append(subject).append(" = ?, ");
	        }
	        sql.setLength(sql.length() - 2);
	        sql.append(" WHERE unique_id = ?");

	        PreparedStatement updateStmt = conn.prepareStatement(sql.toString());
	        int i = 1;
	        for (int mark : updatedMarks.values()) {
	            updateStmt.setInt(i++, mark);
	        }
	        updateStmt.setString(i, uniqueId);

	        int updated = updateStmt.executeUpdate();
	        if (updated > 0) {
	            System.out.println("Marks updated successfully.");
	        } else {
	            System.out.println("Failed to update. Check Unique ID.");
	        }

	        updateStmt.close();

	    } catch (SQLException | NumberFormatException e) {
	        e.printStackTrace();
	        System.out.println("Error while editing marks.");
	    }
	}


	private static void addMarks() {
	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
	        System.out.print("Enter class (1-12): ");
	        String className = scanner.nextLine().trim();
	        String tableName = getClassTableName(className);

	        if (tableName == null) {
	            System.out.println("Invalid class.");
	            return;
	        }
	        System.out.println("\n--- Student List ---");
	        String selectStudents = "SELECT name, unique_id FROM " + tableName;
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(selectStudents);

	        while (rs.next()) {
	            System.out.printf("- Name: %-20s ID: %s\n", rs.getString("name"), rs.getString("unique_id"));
	        }
	        rs.close();
	        System.out.print("\nEnter Student Name: ");
	        String studentName = scanner.nextLine().trim();
	        System.out.print("Enter Unique ID: ");
	        String uniqueId = scanner.nextLine().trim();
	        List<String> subjectColumns = new ArrayList<>();
	        String colQuery = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " +
	                          "WHERE TABLE_NAME = ? AND COLUMN_NAME LIKE 'subject_%'";
	        PreparedStatement colStmt = conn.prepareStatement(colQuery);
	        colStmt.setString(1, tableName);
	        ResultSet colRs = colStmt.executeQuery();
	        while (colRs.next()) {
	            subjectColumns.add(colRs.getString("COLUMN_NAME"));
	        }
	        colRs.close();
	        colStmt.close();

	        if (subjectColumns.isEmpty()) {
	            System.out.println("No subject columns found for this class.");
	            return;
	        }
	        Map<String, Integer> subjectMarks = new LinkedHashMap<>();
	        System.out.println("\n--- Enter Marks ---");
	        for (String subject : subjectColumns) {
	            System.out.print(subject + ": ");
	            int mark = Integer.parseInt(scanner.nextLine().trim());
	            subjectMarks.put(subject, mark);
	        }
	        StringBuilder updateSQL = new StringBuilder("UPDATE " + tableName + " SET ");
	        for (String col : subjectColumns) {
	            updateSQL.append(col).append(" = ?, ");
	        }
	        updateSQL.setLength(updateSQL.length() - 2); // Remove trailing comma
	        updateSQL.append(" WHERE unique_id = ?");

	        PreparedStatement updateStmt = conn.prepareStatement(updateSQL.toString());
	        int index = 1;
	        for (String col : subjectColumns) {
	            updateStmt.setInt(index++, subjectMarks.get(col));
	        }
	        updateStmt.setString(index, uniqueId);

	        int rows = updateStmt.executeUpdate();
	        if (rows > 0) {
	            System.out.println("Marks updated successfully for " + studentName);
	        } else {
	            System.out.println("Failed to update marks. Check Unique ID.");
	        }

	        updateStmt.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error in addMarks().");
	    }
	}
}

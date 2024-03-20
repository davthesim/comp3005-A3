
import java.sql.*;
import java.util.Scanner;

public class Main {
    private static String url = "jdbc:postgresql://localhost:5432/A3Q1"; //Your localhost port and database name.
    private static String user = "postgres"; //Your postsql user.
    private static String password = "theawesome01"; //Your postsql password.
    private static Connection connection; //Connection we will use once established.

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password); //Establish connection and login.
            if (connection != null) {
                System.out.println("Connected to database.");
                int choice = 0;
                Scanner scan = new Scanner(System.in);
                while (choice != 6) { //Loop that goes through until wish to exit to call each function.
                    System.out.println("Input function choice: \n[1] = getAllStudents()\n[2] = addStudent(first_name, last_name, email, enrollment_date)\n[3] = updateStudentEmail(student_id, new_email)\n[4] = deleteStudent(student_id)\n[5] = createData()\n[6] = exit.");
                    choice = scan.nextInt();
                    if (choice == 1) {
                        getAllStudents(); //Calls function.
                    } else if (choice == 2) {
                        System.out.println("Input first_name: ");
                        String first_name = scan.next(); //Test first_name: David
                        System.out.println("Input last_name: ");
                        String last_name = scan.next(); //Test last_name: Simonov
                        System.out.println("Input email: ");
                        String email = scan.next(); //Test email: davsimonov@gmail.com
                        System.out.println("Input enrollment_date: ");
                        String enrollment_date = scan.next(); //Test enrollment_date: 2021-09-01
                        addStudent(first_name,last_name,email,enrollment_date); //Calls function.
                    } else if (choice == 3) {
                        System.out.println("Input existing student_id: ");
                        int student_id = scan.nextInt(); //Test 4
                        System.out.println("Input new_email: ");
                        String new_email = scan.next(); //Test davNEWemail@gmail.com
                        updateStudentEmail(student_id,new_email); //Calls function.
                    } else if (choice == 4) {
                        System.out.println("Input existing student_id to delete: ");
                        int student_id = scan.nextInt(); //Test 4
                        deleteStudent(student_id); //Calls function.
                    } else if (choice == 5) {
                        createData();
                    } else if (choice == 6) { break; }
                }
            } else {
                System.out.println("Failed to connect to database.");
            }
        }
        catch(Exception e){System.out.println(e);}
    }

    static public void getAllStudents() throws SQLException {//Function correctly retrieves and displays records.
        Statement statement = connection.createStatement(); //Creates statement to be executed.
        statement.executeQuery("SELECT * FROM students"); //Executes command but returns a result.
        ResultSet resultSet = statement.getResultSet(); //Grabs results from query.
        while(resultSet.next()) { //Traverses through all results (each row).
            System.out.print(resultSet.getInt("student_id") + "\t"); //"\t" = tab //Gets and prints student_id.
            System.out.print(resultSet.getString("first_name") + "\t"); //Gets and prints first_name.
            System.out.print(resultSet.getString("last_name") + "\t"); //Gets and prints last_name.
            System.out.print(resultSet.getString("email") + "\t"); //Gets and prints email.
            System.out.print(resultSet.getString("enrollment_date") + "\t\n"); //Gets and prints enrollment_date.
        }
        System.out.print("\n");
    } //Complete.

    static public void addStudent(String first_name, String last_name, String email, String enrollment_date) throws SQLException { //Inserts a new student record into the students table.
        Statement statement = connection.createStatement(); //Creates statement to be executed.
        String sqlCmd = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES ('" + first_name + "', '" + last_name + "', '" + email + "', '" + enrollment_date + "')";
        statement.executeUpdate(sqlCmd); //Executes command.
        //Since the student ID auto increments, we just input everything else.
    } //Complete.

    static public void updateStudentEmail(int student_id,String new_email) throws SQLException { //function correctly updates the email of a student
        Statement statement = connection.createStatement(); //Creates statement to be executed.
        String sqlCmd = "UPDATE students SET email = '" + new_email + "' WHERE student_id = " + student_id;
        statement.executeUpdate(sqlCmd); //Executes command.
        //Search in the table for a matching student ID and if found, set that rows email to the new one using "SET".
    } //Complete.

    static public void deleteStudent(int student_id) throws SQLException { //Function successfully removes a student record
        Statement statement = connection.createStatement(); //Creates statement to be executed.
        String sqlCmd = "DELETE FROM students WHERE student_id = " + student_id; //Deletes row where student_id matches input.
        statement.executeUpdate(sqlCmd); //Executes command.
    } //Complete.

    static public void createData() throws SQLException { //Creates data set and inserts default values as spec requires.
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS students (student_id SERIAL PRIMARY KEY,first_name TEXT NOT NULL,last_name TEXT NOT NULL,email TEXT NOT NULL UNIQUE,enrollment_date DATE)");
        Statement statement2 = connection.createStatement();
        statement2.executeUpdate("INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES ('John', 'Doe', 'john.doe@example.com', '2023-09-01'),('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');");
    } //Complete.
}
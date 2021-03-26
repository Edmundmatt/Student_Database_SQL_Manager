package nz.ac.wgtn.swen301.assignment1;

import nz.ac.wgtn.swen301.studentdb.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A student managers providing basic CRUD operations for instances of Student, and a read operation for instances of Degree.
 * @author jens dietrich
 */
public class StudentManager {

    // DO NOT REMOVE THE FOLLOWING -- THIS WILL ENSURE THAT THE DATABASE IS AVAILABLE
    // AND THE APPLICATION CAN CONNECT TO IT WITH JDBC
    static {
        StudentDB.init();
    }
    // DO NOT REMOVE BLOCK ENDS HERE

    // THE FOLLOWING METHODS MUST IMPLEMENTED :

    /**
     * Return a student instance with values from the row with the respective id in the database.
     * If an instance with this id already exists, return the existing instance and do not create a second one.
     * @param id
     * @return
     * @throws NoSuchRecordException if no record with such an id exists in the database
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_readStudent (followed by optional numbers if multiple tests are used)
     */
    public static Student readStudent(String id) throws NoSuchRecordException,ClassNotFoundException, SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String url = "jdbc:derby:memory:studentdb";
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT * FROM STUDENTS WHERE id= ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet results = stmt.executeQuery();
            Student student = null;
            while (results.next()) {
                Degree degree = readDegree(results.getString("degree"));

                student = new Student(id, results.getString("name"), results.getString("first_name"),
                        degree);
            }
            return student;
        }catch(Exception e){
            System.err.println("Error: readStudent()");
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Return a degree instance with values from the row with the respective id in the database.
     * If an instance with this id already exists, return the existing instance and do not create a second one.
     * @param id
     * @return
     * @throws NoSuchRecordException if no record with such an id exists in the database
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_readDegree (followed by optional numbers if multiple tests are used)
     */
    public static Degree readDegree(String id) throws NoSuchRecordException, SQLException, ClassNotFoundException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String url = "jdbc:derby:memory:studentdb";
            Connection conn = DriverManager.getConnection(url);
            String sql = "SELECT * FROM DEGREES WHERE id= ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet results = stmt.executeQuery();
            Degree degree = new Degree();
            while (results.next()) {
                degree = new Degree(id, results.getString("name"));
            }
            if(degree == null) throw new NoSuchRecordException("Error: readDegree() - Degree null value");
            return degree;
        }catch(Exception e){
            System.err.println("Error: readDegree()");
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Delete a student instance from the database.
     * I.e., after this, trying to read a student with this id will result in a NoSuchRecordException.
     * @param student
     * @throws NoSuchRecordException if no record corresponding to this student instance exists in the database
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_delete
     */
    public static void delete(Student student) throws NoSuchRecordException {
        String sqlStatement = "DELETE FROM STUDENTS WHERE id = \'" + student.getId() + "\'";
        updateDataBase(sqlStatement);
    }

    /**
     * Update (synchronize) a student instance with the database.
     * The id will not be changed, but the values for first names or degree in the database might be changed by this operation.
     * After executing this command, the attribute values of the object and the respective database value are consistent.
     * Note that names and first names can only be max 1o characters long.
     * There is no special handling required to enforce this, just ensure that tests only use values with < 10 characters.
     * @param student
     * @throws NoSuchRecordException if no record corresponding to this student instance exists in the database
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_update (followed by optional numbers if multiple tests are used)
     */
    public static void update(Student student) throws NoSuchRecordException {
        String sqlStatement = "UPDATE STUDENTS " +
                "SET name  = \'" + student.getName() + "\', first_name = \'" + student.getFirstName() + "\', degree = \'" + student.getDegree().getId() + "\'" +
                " WHERE id = \'" + student.getId() + "\'";
        updateDataBase(sqlStatement);
    }


    /**
     * Create a new student with the values provided, and save it to the database.
     * The student must have a new id that is not been used by any other Student instance or STUDENTS record (row).
     * Note that names and first names can only be max 1o characters long.
     * There is no special handling required to enforce this, just ensure that tests only use values with < 10 characters.
     * @param name
     * @param firstName
     * @param degree
     * @return a freshly created student instance
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_createStudent (followed by optional numbers if multiple tests are used)
     */
    public static Student createStudent(String name,String firstName,Degree degree) throws SQLException, ClassNotFoundException {
        List<String> allIDs = (List<String>) getAllStudentIds();
        if(allIDs.size() >= 10000){
            System.out.println("No space in database for new student");
            return null;
        }
        int lastID = Integer.valueOf(allIDs.get(allIDs.size()-1).substring(2));
        int newIDInt = lastID++;
        String newID = "id" + newIDInt;
        Student student = new Student(newID, name, firstName, degree);
        String sqlStatement = "INSERT INTO STUDENTS "
                + "VALUES (\'" + newID + "\', \'" + name + "\', \'" + firstName + "\', \"" + degree + "\')";
        updateDataBase(sqlStatement);
        return student;
    }

    /**
     * Get all student ids currently being used in the database.
     * @return
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_getAllStudentIds (followed by optional numbers if multiple tests are used)
     */
    public static Collection<String> getAllStudentIds() throws SQLException, ClassNotFoundException {
        List<String> studentIDs = new ArrayList<>(); //Change to an Iterable
        ResultSet results = connectToDataBase("SELECT id FROM STUDENTS");
        while(results.next()){
             studentIDs.add(results.getString("id"));
        }
        return studentIDs;
    }

    /**
     * Get all degree ids currently being used in the database.
     * @return
     * This functionality is to be tested in test.nz.ac.wgtn.swen301.assignment1.TestStudentManager::test_getAllDegreeIds (followed by optional numbers if multiple tests are used)
     */
    public static Iterable<String> getAllDegreeIds() throws SQLException, ClassNotFoundException {
        List<String> degreeIDs = new ArrayList<>(); //Change to Iterable
        ResultSet results = connectToDataBase("SELECT id FROM DEGREES");
        while(results.next()){
            degreeIDs.add(results.getString("id"));
        }
        return degreeIDs;
    }

    /**
     * Returns ResultSet from the database given a an input String sqlStatement.
     * @param sqlStatement
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static ResultSet connectToDataBase(String sqlStatement) throws ClassNotFoundException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String url = "jdbc:derby:memory:studentdb";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sqlStatement);
        }catch(Exception e){
            System.err.println("Error: connectToDataBase()");
            e.printStackTrace();
            return null;
        }
    }



    private static void updateDataBase(String sqlStatement){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String url = "jdbc:derby:memory:studentdb";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sqlStatement);
        }catch(Exception e){
            System.err.println("Error: updateDataBase()");
            e.printStackTrace();
        }
    }

}

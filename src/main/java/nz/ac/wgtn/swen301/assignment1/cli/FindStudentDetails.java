package nz.ac.wgtn.swen301.assignment1.cli;

import nz.ac.wgtn.swen301.assignment1.StudentManager;
import nz.ac.wgtn.swen301.studentdb.NoSuchRecordException;
import nz.ac.wgtn.swen301.studentdb.Student;

import java.sql.SQLException;

public class FindStudentDetails {

    // THE FOLLOWING METHOD MUST IMPLEMENTED
    /**
     * Executable: the user will provide a student id as single argument, and if the details are found,
     * the respective details are printed to the console.
     * E.g. a user could invoke this by running "java -cp <someclasspath> nz.ac.wgtn.swen301.assignment1.cli.FindStudentDetails id42"
     * @param arg
     */
    public static void main (String[] args) throws SQLException, NoSuchRecordException, ClassNotFoundException {
        System.out.println("Student details should be printed here");
        String id = args[0];
        Student student = StudentManager.readStudent(id);

        System.out.println(student.getId() + "\t" + student.getName() + "\t" + student.getFirstName() + "\t");

    }
}

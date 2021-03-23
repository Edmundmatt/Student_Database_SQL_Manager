package test.nz.ac.wgtn.swen301.assignment1;

import nz.ac.wgtn.swen301.assignment1.StudentManager;
import nz.ac.wgtn.swen301.studentdb.Degree;
import nz.ac.wgtn.swen301.studentdb.Student;
import nz.ac.wgtn.swen301.studentdb.StudentDB;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TestStudentManager {

    // DO NOT REMOVE THE FOLLOWING -- THIS WILL ENSURE THAT THE DATABASE IS AVAILABLE
    // AND IN ITS INITIAL STATE BEFORE EACH TEST RUNS
    @Before
    public  void init () {
        StudentDB.init();
    }
    // DO NOT REMOVE BLOCK ENDS HERE

    @Test
    public void dummyTest() throws Exception {
        Student student = new StudentManager().readStudent("id42");
        // THIS WILL INITIALLY FAIL !!
        assertNotNull(student);
    }

    @Test
    public void test_readStudent1() throws Exception{
        Student student = new StudentManager().readStudent("id0");
        Degree degree = new Degree("id0", "BSc Computer Science");
        Student expected = new Student("id0", "Smith", "James", degree);
        assertTrue(student.equals(expected));
    }

    @Test
    public void test_readDegree1() throws Exception {
        Degree degree = new StudentManager().readDegree("id42");
        assertNotNull(degree);
    }

    @Test
    public void test_readDegree2() throws Exception {
        Degree expected = new Degree("deg2", "BE Cybersecurity");
        Degree degree = new StudentManager().readDegree("deg2");
        assertTrue(degree.equals(expected));
    }

    @Test
    public void test_createStudent1() throws Exception {
        //Assert student cannot be created when none have been deleted, no room in database
        StudentManager sM = new StudentManager();
        Degree degree = new Degree("deg0", "BSc Computer Science");
        assertNull(sM.createStudent("","", degree));
    }

    @Test
    public void test_createStudent2() throws Exception {
        //Assert once a student has been deleted a new student can be added to the database
        //
    }

    @Test
    public void test_delete1() throws Exception {
        StudentManager sM = new StudentManager();
//        Degree degree = new Degree("id0", "BSc Computer Science");
//        Student student = new Student("id0", "Smith", "James", degree);
//        sM.delete(student);
//        assertNull(sM.readStudent(student.getId()));
        String id = "id0";
        sM.delete(sM.readStudent(id));
        assertNull(sM.readStudent(id));
    }

    @Test
    public void test_update1() throws Exception {
        StudentManager sM = new StudentManager();
        Degree degree = new Degree("deg2", "BE Cybersecurity");
        Student student1 = new Student("id0", "", "", degree);
        sM.update(student1);
        Student student2 = sM.readStudent("id0");
        assertTrue(student1.equals(student2));

    }

    @Test
    public void test_update2() throws Exception {
        //Update doesn't work inputting null values
        //Does it need to?

    }



    @Test
    public void testPerformance() throws Exception {
        //Expected the database manager can handle 1000 random read requests per second

    }
}

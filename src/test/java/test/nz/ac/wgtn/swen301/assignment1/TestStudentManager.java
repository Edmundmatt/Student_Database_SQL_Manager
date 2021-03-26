package test.nz.ac.wgtn.swen301.assignment1;

import nz.ac.wgtn.swen301.assignment1.StudentManager;
import nz.ac.wgtn.swen301.studentdb.Degree;
import nz.ac.wgtn.swen301.studentdb.Student;
import nz.ac.wgtn.swen301.studentdb.StudentDB;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

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
        StudentManager sM = new StudentManager();
        Student student = sM.readStudent("id0");
        sM.delete(student);
        Degree degree = sM.readDegree("deg0");
        Student s1 = sM.createStudent("Bunny", "Bugs",  degree);
        assertTrue(sM.readStudent(s1.getId()).getFirstName().equals(s1.getFirstName()));
    }

    @Test
    public void test_delete1() throws Exception {
        StudentManager sM = new StudentManager();
        String id = "id0";
        sM.delete(sM.readStudent(id));
        assertNull(sM.readStudent(id));
    }

    @Test
    public void test_delete2() throws Exception {
        StudentManager sM = new StudentManager();
        Degree degree = new Degree("id0", "BSc Computer Science");
        Student student = new Student("id0", "Smith", "James", degree);
        sM.delete(student);
        assertNull(sM.readStudent(student.getId()));
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
    public void test_getAllStudentIds() throws Exception {
        StudentManager sM = new StudentManager();
        int idsCount = sM.getAllStudentIds().size();
        assertTrue(idsCount == 10000);
    }

    @Test
    public void test_getAllDegreeIds() throws Exception {
        StudentManager sM = new StudentManager();
        List<String> ids = (List)sM.getAllDegreeIds();
        assertTrue(ids.size() == 10);

    }

    @Test
    public void testPerformance() throws Exception {
        //Expected the database manager can handle 1000 random read requests per second
        StudentManager sM = new StudentManager();
        String id;
        Random random = new Random();
        int j;
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++){
            j = random.nextInt(10000);
            id = "id" + j;
            sM.readStudent(id);
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}

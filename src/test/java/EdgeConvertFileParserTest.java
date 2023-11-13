// import static org.junit.Assert.*;
// import src.main.java.EdgeConnector;

// import org.junit.Before;
// import org.junit.Test;
// package src.test.java;
import static org.junit.Assert.*;
import java.io.File;
// import src.main.java.EdgeConnector;  

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;

public class EdgeConvertFileParserTest {
    
    private static final String PROJECT_ROOT = System.getProperty("user.dir");
    // "~/Desktop/code/422/classprojectGit/rit-iste422-project";

    public File testsave = new File(PROJECT_ROOT +"src/test/resources/testsave.sav");
    public File testsavenoheadding = new File(PROJECT_ROOT +"src/test/resources/testsavenoheadding.sav");
    public File testsavemissingchar = new File(PROJECT_ROOT +"src/test/resources/testsavemissingchar.sav");
    public File testsaveinvalidvalues = new File(PROJECT_ROOT +"src/test/resources/testsaveinvalidvalues.sav");
    public EdgeConvertFileParser testObj = new EdgeConvertFileParser(testsave);
    
    // test --tests EdgeConvertFileParserTest -i

    /*
    public File testsave = new File("./resources/testsave.sav");
    public File testsavenoheadding = new File("./resources/testsavenoheadding.sav");
    public File testsavemissingchar = new File("./resources/testsavemissingchar.sav");
    public File testsaveinvalidvalues = new File("./resources/testsaveinvalidvalues.sav");
    public EdgeConvertFileParser testObj = new EdgeConvertFileParser(testsave);
    */
    @Test
    public void openFileTest(){
        try{
            testObj.openFile(testsave);
            assertTrue(true);
        }catch(Exception ex){
            fail("Unable to open file with error of:" + ex);
        }
    }
    @Test
    public void ableToGetEdgeTables(){
        testObj.openFile(testsave);

        EdgeTable table1 = new EdgeTable("1|STUDENT");
        table1.addNativeField(7);
        table1.addNativeField(8);
        table1.setRelatedField(0, 0);

        EdgeTable table2 = new EdgeTable("2|FACULTY");
        table2.addNativeField(11);
        table2.addNativeField(6);
        table2.addRelatedTable(13);
        table2.setRelatedField(0, 0);

        EdgeTable table3 = new EdgeTable("13|COURSES");
        table3.addNativeField(3);
        table3.addNativeField(5);
        table3.addRelatedTable(2);
        table3.setRelatedField(0, 0);

        EdgeTable[] testEXedgeTables = {table1,table2,table3};

        assertArrayEquals(testEXedgeTables,testObj.getEdgeTables());
    }

    @Test
    public void ableToGetEdgeFields(){
        testObj.openFile(testsave);

        EdgeField field1 = new EdgeField("3|Grade");
        field1.setTableID(13);
        field1.setTableBound(0);
        field1.setFieldBound(0);
        field1.setDataType(2);
        field1.setVarcharValue(1);
        field1.setIsPrimaryKey(false);
        field1.setDisallowNull(false);

        EdgeField field2 = new EdgeField("4|CourseName");
        field2.setTableID(0);
        field2.setTableBound(0);
        field2.setFieldBound(0);
        field2.setDataType(0);
        field2.setVarcharValue(1);
        field2.setIsPrimaryKey(false);
        field2.setDisallowNull(false);

        EdgeField field3 = new EdgeField("5|Number");
        field3.setTableID(13);
        field3.setTableBound(0);
        field3.setFieldBound(0);
        field3.setDataType(2);
        field3.setVarcharValue(1);
        field3.setIsPrimaryKey(true);
        field3.setDisallowNull(false);

        EdgeField field4 = new EdgeField("6|FacSSN");
        field4.setTableID(2);
        field4.setTableBound(0);
        field4.setFieldBound(0);
        field4.setDataType(0);
        field4.setVarcharValue(9);
        field4.setIsPrimaryKey(true);
        field4.setDisallowNull(false);

        EdgeField field5 = new EdgeField("7|StudentSSN");
        field5.setTableID(1);
        field5.setTableBound(0);
        field5.setFieldBound(0);
        field5.setDataType(0);
        field5.setVarcharValue(9);
        field5.setIsPrimaryKey(true);
        field5.setDisallowNull(false);

        EdgeField field6 = new EdgeField("8|StudentName");
        field6.setTableID(1);
        field6.setTableBound(0);
        field6.setFieldBound(0);
        field6.setDataType(0);
        field6.setVarcharValue(20);
        field6.setIsPrimaryKey(false);
        field6.setDisallowNull(false);

        EdgeField field7 = new EdgeField("11|FacultyName");
        field7.setTableID(2);
        field7.setTableBound(0);
        field7.setFieldBound(0);
        field7.setDataType(0);
        field7.setVarcharValue(1);
        field7.setIsPrimaryKey(false);
        field7.setDisallowNull(false);

        EdgeField[] testEXedgeField = {field1,field2,field3,field4,field5,field6,field7};

        assertArrayEquals(testEXedgeField,testObj.getEdgeFields());
    }
    @Test
    public void openFileWithBadPath(){
        try{
            final File testbadsave = new File("./resources/nonexistantfile.sav");
            testObj.openFile(testsave);
            assertTrue(true);
        }catch(Exception ex){
            fail("Unable to open file with error of:" + ex);
        }
    }
    @Test
    public void openFileWithNoHead(){
        testObj.openFile(testsavenoheadding);
        assertTrue(testObj.testFailed);
    }
    @Test
    public void parseSaveFileMissingChar(){
        testObj.openFile(testsavemissingchar);
        EdgeField[] exampleArray = {};
        assertArrayEquals(exampleArray,testObj.getEdgeFields());
    }
    @Test
    public void parseSaveFileMissingInput(){
        testObj.openFile(testsaveinvalidvalues);
        assertNull(testObj.getEdgeTables());
    }
}
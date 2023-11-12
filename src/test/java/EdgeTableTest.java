// import static org.junit.Assert.*;
// import src.main.java.EdgeConnector;

// import org.junit.Before;
// import org.junit.Test;
// package src.test.java;
import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.core.util.Assert;

// import src.main.java.EdgeConnector;  

import org.junit.Before;
import org.junit.Test;

public class EdgeTableTest{

    public static EdgeTable testTable;
    public static NoSuchElementException nseException;
    public static NumberFormatException nfException;

    @Before
    public void createTable(){
        String inputString = "5|Trees";
        testTable = new EdgeTable(inputString);
    }
    @Test
    public void testCreateTableBaseCase(){
        String inputString = "10|Shoes";
        testTable = new EdgeTable(inputString);
        assertEquals("test",10, testTable.getNumFigure());
        assertEquals("test","Shoes", testTable.getName());
    }

    @Test
    public void testCreateTableNegativeNum(){
        String inputString = "-15|Weather";
        testTable = new EdgeTable(inputString);
        assertEquals("test",-15, testTable.getNumFigure());
        assertEquals("test","Weather", testTable.getName());
    }

    @Test
    public void testGetTablesArrayEmpty(){
        testTable.makeArrays();
        assertEquals("test", 0, testTable.getRelatedTablesArray().length);
    }

    @Test
    public void testGetFieldsArrayEmpty(){
        testTable.makeArrays();
        assertEquals("test", 0, testTable.getRelatedFieldsArray().length);
    }

    @Test
    public void testGetNativeFieldsArrayEmpty(){
        testTable.makeArrays();
        assertEquals("test", 0, testTable.getNativeFieldsArray().length);
    }

    @Test
    public void testAddRelatedTable(){
        int relatedTable = 6;
        testTable.addRelatedTable(relatedTable);
        testTable.makeArrays();
        assertEquals("test", 6, testTable.getRelatedTablesArray()[0]);
    }

    @Test
    public void testAddNegativeRelatedTable(){
        int relatedTable = -6;
        testTable.addRelatedTable(relatedTable);
        testTable.makeArrays();
        assertEquals("test", -6, testTable.getRelatedTablesArray()[0]);
    }

    @Test
    public void testAddNativeField(){
        int field = 4;
        testTable.addNativeField(field);
        testTable.makeArrays();
        assertEquals("test", 4, testTable.getNativeFieldsArray()[0]);
    }

    @Test
    public void testAddNegativeNativeField(){
        int field = -4;
        testTable.addNativeField(field);
        testTable.makeArrays();
        assertEquals("test", -4, testTable.getNativeFieldsArray()[0]);
    }

    @Test
    public void testMoveFieldUp(){
        int field1 = 1;
        int field2 = 2;
        int field3 = 3;
        int midIndex = field2 - 1;
        testTable.addNativeField(field1);
        testTable.addNativeField(field2);
        testTable.addNativeField(field3);
        testTable.makeArrays();
        testTable.moveFieldUp(midIndex);
        assertEquals("test", 2, testTable.getNativeFieldsArray()[0]);
        // assertEquals("test", 2, testTable.getRelatedFieldsArray()[0]);
    }

    @Test
    public void testMoveFieldDown(){
        int field1 = 1;
        int field2 = 2;
        int field3 = 3;
        int midIndex = field2 - 1;
        testTable.addNativeField(field1);
        testTable.addNativeField(field2);
        testTable.addNativeField(field3);
        testTable.makeArrays();
        testTable.moveFieldDown(midIndex);
        assertEquals("test", 2, testTable.getNativeFieldsArray()[2]);
        // assertEquals("test", 2, testTable.getRelatedFieldsArray()[2]);
    }

    @Test
    public void testMoveFieldUpEdge(){
        int field1 = 1;
        int field2 = 2;
        int field3 = 3;
        testTable.addNativeField(field1);
        testTable.addNativeField(field2);
        testTable.addNativeField(field3);
        testTable.makeArrays();
        testTable.moveFieldUp(0);
        assertEquals("test", 1, testTable.getNativeFieldsArray()[0]);
        // assertEquals("test", 1, testTable.getRelatedFieldsArray()[0]);
    }

    @Test
    public void testMoveFieldDownEdge(){
        int field1 = 1;
        int field2 = 2;
        int field3 = 3;
        testTable.addNativeField(field1);
        testTable.addNativeField(field2);
        testTable.addNativeField(field3);
        testTable.makeArrays();
        testTable.moveFieldDown(2);
        assertEquals("test", 3, testTable.getNativeFieldsArray()[2]);
        // assertEquals("test", 3, testTable.getRelatedFieldsArray()[2]);
    }




//     public void testCreateTableNoString() throws NoSuchElementException{
//         String inputString = "10|";
//         testTable = new EdgeTable(inputString);
//         assertEquals("test",10, testTable.getNumFigure());
//         Throwable exception = Assert.assertThrows(
//       NoSuchElementException.class, 
//       () -> {
//           throw new IllegalArgumentException("Exception message");
//       }
//     );
//     assertEquals("Exception message", exception.getMessage());
//     testTable.getName();
//     }


//     public void testCreateTableNoName() throws NoSuchElementException{
//         String inputString = "10";
//         testTable = new EdgeTable(inputString);
//         assertEquals("test",10, testTable.getNumFigure());
//         assertEquals("test",nseException, testTable.getName());
//     }

//     @Test
//     public void testCreateTableStringNum() throws NumberFormatException{
//         String inputString = "Apple|Fruit";
//         testTable = new EdgeTable(inputString);
//         assertEquals("test",nfException, testTable.getNumFigure());
//         assertEquals("test","Frozen", testTable.getName());
//     }

}
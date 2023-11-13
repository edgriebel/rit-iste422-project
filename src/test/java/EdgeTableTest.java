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
    public void testCreateTableNoString() throws NoSuchElementException{
        String inputString = "10|";
        try {
            testTable = new EdgeTable(inputString);
            testTable.getName();
            fail("Exception not thrown");
        } catch (NoSuchElementException e) {
            assertEquals(e.getMessage(), e.getMessage());
        }
    }

    @Test
    public void testCreateTableNoName() throws NoSuchElementException{
        String inputString = "10";
        try {
            testTable = new EdgeTable(inputString);
            testTable.getName();
            fail("Exception not thrown");
        } catch (NoSuchElementException e) {
            assertEquals(e.getMessage(), e.getMessage());
        }
    }

    @Test
    public void testCreateTableStringNum() throws NumberFormatException{
        String inputString = "Apple|Fruit";
        try {
            testTable = new EdgeTable(inputString);
            testTable.getNumFigure();
            fail("Exception not thrown");
        } catch (NumberFormatException e) {
            assertEquals(e.getMessage(), e.getMessage());
        }
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
    }

    @Test
    public void testSetRelatedFields(){
        testTable.addNativeField(1);
        testTable.addNativeField(2);
        testTable.addNativeField(3);
        testTable.addNativeField(4);
        testTable.addNativeField(5);
        testTable.addNativeField(6);
        testTable.makeArrays();
        testTable.setRelatedField(2, 5);
        testTable.setRelatedField(0, 100);
        testTable.setRelatedField(5, 1);
        assertEquals(5, testTable.getRelatedFieldsArray()[2]);
        assertEquals(100, testTable.getRelatedFieldsArray()[0]);
        assertEquals(1, testTable.getRelatedFieldsArray()[5]);
    }

    @Test
    public void testSetRelatedFieldsNegative(){
        testTable.addNativeField(1);
        testTable.addNativeField(2);
        testTable.addNativeField(3);
        testTable.addNativeField(4);
        testTable.addNativeField(5);
        testTable.addNativeField(6);
        testTable.makeArrays();
        testTable.setRelatedField(0, -15);   
        testTable.setRelatedField(5, -150);
        assertEquals(-15, testTable.getRelatedFieldsArray()[0]);   
        assertEquals(-150, testTable.getRelatedFieldsArray()[5]);   
    }

    @Test
    public void testSetRelatedFieldsOutOfBounds() throws IndexOutOfBoundsException{
        testTable.addNativeField(1);
        testTable.addNativeField(2);
        testTable.addNativeField(3);
        testTable.addNativeField(4);
        testTable.addNativeField(5);
        testTable.addNativeField(6);
        testTable.makeArrays();
        try {
            testTable.setRelatedField(-2, 5);   
            testTable.setRelatedField(20, 43);
            fail("Exception not thrown");
        } catch (IndexOutOfBoundsException e) {
            assertEquals(e.getMessage(), e.getMessage());
        } 
    }

    @Test
    public void testMoveFieldDownRelatedFields(){
        testTable.addNativeField(1);
        testTable.addNativeField(2);
        testTable.addNativeField(3);
        testTable.addNativeField(4);
        testTable.addNativeField(5);
        testTable.addNativeField(6);
        testTable.makeArrays();
        testTable.setRelatedField(2, 5);
        testTable.moveFieldDown(2);
        assertEquals("test", 5, testTable.getRelatedFieldsArray()[3]);   
    }

    @Test
    public void testMoveFieldUpRelatedFields(){
        testTable.addNativeField(1);
        testTable.addNativeField(2);
        testTable.addNativeField(3);
        testTable.addNativeField(4);
        testTable.addNativeField(5);
        testTable.addNativeField(6);
        testTable.makeArrays();
        testTable.setRelatedField(5, 1);
        testTable.moveFieldUp(5);
        assertEquals(1, testTable.getRelatedFieldsArray()[4]);
    }

    @Test
    public void testMakeArraysTwice(){
        testTable.addRelatedTable(6);
        testTable.addRelatedTable(10);
        testTable.addRelatedTable(14);
        testTable.addRelatedTable(12);
        testTable.makeArrays();
        testTable.addRelatedTable(0);
        testTable.addRelatedTable(104);
        testTable.addRelatedTable(55);
        testTable.makeArrays();
        assertEquals(7, testTable.getRelatedTablesArray().length);
        assertEquals(55, testTable.getRelatedTablesArray()[6]);
    }

}
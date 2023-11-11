// import static org.junit.Assert.*;
// import src.main.java.EdgeConnector;

// import org.junit.Before;
// import org.junit.Test;
// package src.test.java;
import static org.junit.Assert.*;

// import src.main.java.EdgeConnector;  

import org.junit.Before;
import org.junit.Test;

public class EdgeTableTest{

    @Test
    public void testCreateTableBaseCase(){
        String inputString = "10|Shoes";
        EdgeTable testTable = new EdgeTable(inputString);
        assertEquals("test",10, testTable.getNumFigure());
        assertEquals("test","Shoes", testTable.getName());
    }

    public void testCreateTableNegativeNum(){
        String inputString = "-15|Weather";
        EdgeTable testTable = new EdgeTable(inputString);
        assertEquals("test",-15, testTable.getNumFigure());
        assertEquals("test","Weather", testTable.getName());
    }

    public void testCreateTableNoString(){
        String inputString = "10|";
        EdgeTable testTable = new EdgeTable(inputString);
        assertEquals("test",10, testTable.getNumFigure());
        assertEquals("test","", testTable.getName());
    }

}
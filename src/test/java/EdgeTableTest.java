import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EdgeTableTest {
    
    @Test
    public void matchInputString(){

        String edg = "11|TestName";

        EdgeTable et = new EdgeTable(edg);        
        assertEquals("numFigure should match the inputString param", 11, et.getNumFigure());
        
    }

    // @Test
    // public void givenEdgeTableHasCorrectFigureNumber(){
    //     assertEquals("Checking the correct figure number", ett.EdgeTable.numFigure);
    // }

    // @Test
    // public void givenEdgeTableHasCorrectStringName(){
    //     assertEquals("Checking the correct figure number", ett.EdgeTable.name);
    // }
}

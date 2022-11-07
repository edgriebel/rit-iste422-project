import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EdgeTableTest {
    
    EdgeTableTest ett;

    @Test
    public void givenEdgeTableInputStringShouldExist(){
        assertEquals("There should be an input string", ett.EdgeTable.inputString);
    }

    @Test
    public void givenEdgeTableHasCorrectFigureNumber(){
        assertEquals("Checking the correct figure number", ett.EdgeTable.numFigure);
    }

    @Test
    public void givenEdgeTableHasCorrectStringName(){
        assertEquals("Checking the correct figure number", ett.EdgeTable.name);
    }
}

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TestName;

public class EdgeTableTest {
    
    @Test
    public void matchInputString(){

        String inputString = "11|TestName";

        EdgeTable et = new EdgeTable(inputString);        
        assertEquals("numFigure should match the inputString param", 11, et.getNumFigure());
        assertEquals("name should match the inputString param", "TestName", et.getName());

    }

    @Test
    public void callFieldsOnParserUp(){

        String inputString = "22|TestName";

        EdgeTable et = new EdgeTable(inputString);
        et.addNativeField(22);        
        et.addNativeField(33);     
        et.addNativeField(44);  
        
        //Makes arrays to manipulate
        et.makeArrays();

        int secondIndex = et.nativeFields[1];
   
        et.setRelatedField(0, 111);
        et.setRelatedField(1, 222);
        et.setRelatedField(2, 333);

        for(int i = 0; i < et.nativeFields.length; i++){
            System.out.println(et.nativeFields[i]);
        }

        et.moveFieldUp(1);

        for(int i = 0; i < et.nativeFields.length; i++){
            System.out.println(et.nativeFields[i]);
        }
        
        assertEquals("Checking if the property has been moved up", et.nativeFields[0], secondIndex);
    }

    @Test
    public void callFieldsOnParserDown(){

        String inputString = "22|TestName";

        EdgeTable et = new EdgeTable(inputString);
        et.addNativeField(22);        
        et.addNativeField(33);     
        et.addNativeField(44);  
        
        //Makes arrays to manipulate
        et.makeArrays();

        int secondIndex = et.nativeFields[1];
   
        et.setRelatedField(0, 111);
        et.setRelatedField(1, 222);
        et.setRelatedField(2, 333);

        for(int i = 0; i < et.nativeFields.length; i++){
            System.out.println(et.nativeFields[i]);
        }

        et.moveFieldDown(0);

        for(int i = 0; i < et.nativeFields.length; i++){
            System.out.println(et.nativeFields[i]);
        }
        
        assertEquals("Checking if the property has been moved down", et.nativeFields[0], secondIndex);
    }

    @Test
    public void callFieldsOnParser(){

        String inputString = "22|TestName";

        EdgeTable et = new EdgeTable(inputString);
        et.addNativeField(22);        
        et.addNativeField(33);     
        et.addNativeField(44);  
        
        et.addRelatedTable(6);
        et.addRelatedTable(7);
        et.addRelatedTable(8);

        //Makes arrays
        et.makeArrays();
        
        assertEquals(et.alNativeFields.size(), et.nativeFields.length);
        assertEquals(et.alRelatedTables.size(), et.relatedTables.length);
        assertEquals(et.relatedFields.length, et.nativeFields.length);

        for(int i = 0; i > et.alNativeFields.size(); i++){
            assertEquals(et.nativeFields[i], et.alNativeFields.get(i));
        }
        for(int i = 0; i > et.alRelatedTables.size(); i++){
            assertEquals(et.relatedTables[i], et.alRelatedTables.get(i));
        }

        for(int i = 0; i > et.relatedFields.length; i++){
            assertEquals(0, et.relatedFields[i]);
        }
    }
}

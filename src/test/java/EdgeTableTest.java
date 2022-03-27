import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EdgeTableTest {
  EdgeTable et1; //1|TestTable
  
  @Before
  public void setup() throws Exception {
    et1 = new EdgeTable("1|TestTable");

    //NativeField Order: 1, 5, 3, 10, 12
    et1.addNativeField(1);
    et1.addNativeField(5);
    et1.addNativeField(3);
    et1.addNativeField(10);
    et1.addNativeField(12);

    //RelatedTable Order: 2, 1, 4, 3, 5
    et1.addRelatedTable(2);
    et1.addRelatedTable(1);
    et1.addRelatedTable(4);
    et1.addRelatedTable(3);
    et1.addRelatedTable(5);
  }

  @Test
  public void checkNumberFigureTest(){
    assertThat("It should return 1", et1.getNumFigure(), is(1));
  }

  @Test
  public void checkNameTest(){
    assertThat("It should be TestTable", et1.getName(), is("TestTable"));
  }

  @Test
  public void checkNativeFieldTest_Get(){
    et1.makeArrays();
    
    int[] arr = et1.getNativeFieldsArray();

    //NativeField Order: 1, 5, 3, 10, 12
    assertThat("The second native field should be 5", arr[1], is(5));
    assertThat("The fourth native field should be 10", arr[3], is(10));
    assertThat("The fifth native field should be 12", arr[4], is(12));
  }

  @Test
  public void checkNativeFieldTest_Add(){
    et1.addNativeField(15);
    et1.addNativeField(17);
    
    et1.makeArrays();
    
    int[] arr = et1.getNativeFieldsArray();

    //NativeField Order: 1, 5, 3, 10, 12, 15, 17
    assertThat("The sixth native field should be 15", arr[5], is(15));
    assertThat("The seventh native field should be 17", arr[6], is(17));
  }

  @Test
  public void checkRelatedFieldTest_Get(){
    et1.makeArrays();

    int[] arr = et1.getRelatedFieldsArray();

    //All values in it should be empty
    //Length: 5
    assertThat("the first related field should be 0", arr[0], is(0));
    assertThat("the third related field should be 0", arr[2], is(0));
    assertThat("the last related field should be 0", arr[arr.length - 1], is(0));
  }

  @Test
  public void checkRelatedFieldTest_Set(){
    et1.makeArrays();

    et1.setRelatedField(1, 5);
    et1.setRelatedField(3, 7);
    et1.setRelatedField(4, 10);

    int[] arr = et1.getRelatedFieldsArray();

    //Length should be 5, Order: 0, 5, 0, 7, 0
    assertThat("the second related field should be 5", arr[1], is(5));
    assertThat("the fourth related field should be 7", arr[3], is(7));
    assertThat("the fifth related field should be 10", arr[4], is(10));
    assertThat("the first related field should be 0", arr[0], is(0));
  }

  @Test
  public void checkRelatedTableTest_Get(){
    et1.makeArrays();

    int[] arr = et1.getRelatedTablesArray();
    
    //RelatedTable Order: 2, 1, 4, 3, 5
    assertThat("the first related table should be 2", arr[0], is(2));
    assertThat("the third related table should be 4", arr[2], is(4));
    assertThat("the fourth related table should be 3", arr[3], is(3));
  }
  
  @Test
  public void checkRelatedTableTest_Add(){
    et1.addRelatedTable(8);
    et1.addRelatedTable(10);
    
    et1.makeArrays();
    
    int[] arr = et1.getRelatedTablesArray();

    //RelatedTable Order: 2, 1, 4, 3, 5, 8, 10
    assertThat("The sixth related table should be 8", arr[5], is(8));
    assertThat("The seventh related table should be 10", arr[6], is(10));
  }

  @Test
  public void checkFieldMovesUp(){
    et1.makeArrays();

    //Original order: 0, 0, 0, 0, 0
    et1.setRelatedField(0, 1);
    et1.setRelatedField(1, 2);
    et1.setRelatedField(2, 3);
    et1.setRelatedField(3, 4);
    et1.setRelatedField(4, 5);
    //Current order: 1, 2, 3, 4, 5 

    //This should do nothing
    et1.moveFieldUp(0);
    int[] arr = et1.getRelatedFieldsArray();
    assertThat("The first related field should remain 1", arr[0], is(1));

    //Field 3 should be at 2nd spot now
    //Field 2 should be at 3rd spot now
    et1.moveFieldUp(2);
    //Current order: 1, 3, 2, 4, 5 
    arr = et1.getRelatedFieldsArray();
    assertThat("The second related field should be 3 now", arr[1], is(3));
    assertThat("The third related field should be 2 now", arr[2], is(2));

    //Field 3 should be at first spot now
    //Field 1 should be at second spot now
    et1.moveFieldUp(1);
    //Current order: 3, 1, 2, 4, 5 
    arr = et1.getRelatedFieldsArray();
    assertThat("The first related field should be 3 now", arr[0], is(3));
    assertThat("The second related field should be 1 now", arr[1], is(1));
  }

  @Test
  public void checkFieldMovesDown(){
    et1.makeArrays();

    //Original order: 0, 0, 0, 0, 0
    et1.setRelatedField(0, 1);
    et1.setRelatedField(1, 2);
    et1.setRelatedField(2, 3);
    et1.setRelatedField(3, 4);
    et1.setRelatedField(4, 5);
    //Current order: 1, 2, 3, 4, 5

    int[] arr = et1.getRelatedFieldsArray();
    //This should do nothing
    et1.moveFieldDown(arr.length - 1);
    arr = et1.getRelatedFieldsArray();
    assertThat("The last related field should remain 5", arr[arr.length - 1], is(5));

    //Field 3 should be at 4th spot now
    //Field 4 should be at 3rd spot now
    et1.moveFieldDown(2);
    //Current order: 1, 2, 4, 3, 5 
    arr = et1.getRelatedFieldsArray();
    assertThat("The second related field should be 3 now", arr[3], is(3));
    assertThat("The third related field should be 2 now", arr[2], is(4));

    //Field 1 should be at 2nd spot now
    //Field 2 should be at 1st spot now
    et1.moveFieldDown(0);
    //Current order: 2, 1, 4, 3, 5
    arr = et1.getRelatedFieldsArray();
    assertThat("The first related field should be 2 now", arr[0], is(2));
    assertThat("The second related field should be 1 now", arr[1], is(1));
  }

  @Test
  public void checkMakeArrays(){
    //NativeField Order: 1, 5, 3, 10, 12
    //RelatedTable Order: 2, 1, 4, 3, 5
    //Check if the MakeArrays() works properly 

    et1.makeArrays();
    int[] tableArr = et1.getRelatedTablesArray();
    int[] fieldArr = et1.getNativeFieldsArray();

    //Testing native field array
    assertThat("The first field in the array should be 1", fieldArr[0], is(1));
    assertThat("The fourth field in the array should be 10", fieldArr[3], is(10));

    //Testing related table array
    assertThat("The third table in the array should be 4", tableArr[2], is(4));
    assertThat("the fifth table in the array should be 5", tableArr[4], is(5));
  }

  
}
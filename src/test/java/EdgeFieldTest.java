import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EdgeFieldTest {

    private EdgeField testObj;

    @Before
    public void setUp() throws Exception {
        testObj = new EdgeField("1234|Example");
    }

    @Test
    public void testGetNumFigure() {
        assertEquals("numFigure should be 1234", 1234, testObj.getNumFigure());
    }

    @Test
    public void testGetNumFigureFalse() {
        int wrongNum = 1235;
        assertFalse("numFigure was initialized to 1234, so it should not be 1235",
                (long) wrongNum == testObj.getNumFigure());
    }

    @Test
    public void testGetName() {
        assertEquals("Name should be 'Example'", "Example", testObj.getName());
    }

    @Test
    public void testGetNameFalse() {
        assertFalse("Name should not be 'WrongName'", "WrongName".equals(testObj.getName()));
    }

    @Test
    public void testDefaultTableID() {
        assertEquals("Default tableID should be 0", 0, testObj.getTableID());
    }

    @Test
    public void testDefaultTableIDFalse() {
        assertFalse("Default tableID should not be 1", 1 == testObj.getTableID());
    }

    @Test
    public void testSetTableID() {
        testObj.setTableID(5);
        assertEquals("tableID should be set to 5", 5, testObj.getTableID());
    }

    @Test
    public void testSetTableIDFalse() {
        testObj.setTableID(7);
        assertFalse("tableID should not be set to 6", 6 == testObj.getTableID());
    }

    @Test
    public void testDefaultTableBound() {
        assertEquals("Default tableBound should be 0", 0, testObj.getTableBound());
    }

    @Test
    public void testDefaultTableBoundFalse() {
        assertFalse("Default tableBound should not be 1", 1 == testObj.getTableBound());
    }

    @Test
    public void testSetTableBound() {
        testObj.setTableBound(3);
        assertEquals("tableBound should be set to 3", 3, testObj.getTableBound());
    }

    @Test
    public void testSetTableBoundFalse() {
        testObj.setTableBound(5);
        assertFalse("tableBound should not be set to 4", 4 == testObj.getTableBound());
    }

    @Test
    public void testDefaultFieldBound() {
        assertEquals("Default fieldBound should be 0", 0, testObj.getFieldBound());
    }

    @Test
    public void testDefaultFieldBoundFalse() {
        assertFalse("Default fieldBound should not be 1", 1 == testObj.getFieldBound());
    }

    @Test
    public void testSetFieldBound() {
        testObj.setFieldBound(7);
        assertEquals("fieldBound should be set to 7", 7, testObj.getFieldBound());
    }

    @Test
    public void testSetFieldBoundFalse() {
        testObj.setFieldBound(9);
        assertFalse("fieldBound should not be set to 8", 8 == testObj.getFieldBound());
    }

    @Test
    public void testDefaultDisallowNull() {
        assertFalse("Default disallowNull should be false", testObj.getDisallowNull());
    }

    @Test
    public void testDefaultDisallowNullFalse() {
        assertFalse("Default disallowNull should not be true", testObj.getDisallowNull());
    }

    @Test
    public void testSetDisallowNull() {
        testObj.setDisallowNull(true);
        assertTrue("disallowNull should be set to true", testObj.getDisallowNull());
    }

    @Test
    public void testSetDisallowNullFalse() {
        testObj.setDisallowNull(false);
        assertFalse("disallowNull should not be set to false", testObj.getDisallowNull());
    }

    @Test
    public void testDefaultIsPrimaryKey() {
        assertFalse("Default isPrimaryKey should be false", testObj.getIsPrimaryKey());
    }

    @Test
    public void testDefaultIsPrimaryKeyFalse() {
        assertFalse("Default isPrimaryKey should not be true", testObj.getIsPrimaryKey());
    }

    @Test
    public void testSetIsPrimaryKey() {
        testObj.setIsPrimaryKey(true);
        assertTrue("isPrimaryKey should be set to true", testObj.getIsPrimaryKey());
    }

    @Test
    public void testSetIsPrimaryKeyFalse() {
        testObj.setIsPrimaryKey(false);
        assertFalse("isPrimaryKey should not be set to false", testObj.getIsPrimaryKey());
    }

    @Test
    public void testDefaultDefaultValue() {
        assertEquals("Default defaultValue should be an empty string", "", testObj.getDefaultValue());
    }

    @Test
    public void testDefaultDefaultValueFalse() {
        assertFalse("Default defaultValue should not be 'Default'", "Default".equals(testObj.getDefaultValue()));
    }

    @Test
    public void testSetDefaultValue() {
        testObj.setDefaultValue("default");
        assertEquals("defaultValue should be set to 'default'", "default", testObj.getDefaultValue());
    }

    @Test
    public void testSetDefaultValueFalse() {
        testObj.setDefaultValue("otherDefault");
        assertFalse("defaultValue should not be set to 'wrongDefault'",
                "wrongDefault".equals(testObj.getDefaultValue()));
    }

    @Test
    public void testDefaultVarcharValue() {
        assertEquals("Default varcharValue should be 1", 1, testObj.getVarcharValue());
    }

    @Test
    public void testDefaultVarcharValueFalse() {
        assertFalse("Default varcharValue should not be 2", 2 == testObj.getVarcharValue());
    }

    @Test
    public void testSetVarcharValue() {
        testObj.setVarcharValue(3);
        assertEquals("varcharValue should be set to 3", 3, testObj.getVarcharValue());
    }

    @Test
    public void testSetVarcharValueFalse() {
        testObj.setVarcharValue(5);
        assertFalse("varcharValue should not be set to 4", 4 == testObj.getVarcharValue());
    }

    @Test
    public void testDefaultDataType() {
        assertEquals("Default dataType should be 0", 0, testObj.getDataType());
    }

    @Test
    public void testDefaultDataTypeFalse() {
        assertFalse("Default dataType should not be 1", 1 == testObj.getDataType());
    }

    @Test
    public void testSetDataType() {
        testObj.setDataType(1);
        assertEquals("dataType should be set to 1", 1, testObj.getDataType());
    }

    @Test
    public void testSetDataTypeFalse() {
        testObj.setDataType(4);
        assertFalse("dataType should not be set to 3", 3 == testObj.getDataType());
    }
}

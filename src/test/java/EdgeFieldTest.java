import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.NoSuchElementException;

public class EdgeFieldTest {
	EdgeField testObj;

	@Before
	public void setUp() throws Exception {
		testObj = new EdgeField("2|Test");
	}

	@Test
	public void testToString() {
		String expected = "2|Test|0|0|0|0|1|false|false|";
		assertEquals("String does not match expected result", expected, testObj.toString());
	}

	@Test
	public void testToStringChanges() {
		String expected = "2|Test|1|1|1|1|2|true|true|Test";
		testObj.setTableID(1);
		testObj.setTableBound(1);
		testObj.setFieldBound(1);
		testObj.setDataType(1);
		testObj.setVarcharValue(2);
		testObj.setDisallowNull(true);
		testObj.setIsPrimaryKey(true);
		testObj.setDefaultValue("Test");
		assertEquals("String does not match expected result", expected, testObj.toString());
	}

	@Test(expected = NumberFormatException.class)
	public void testConstructorInputStringInWrongOrder() {
		EdgeField failObj = new EdgeField("Test|2");
		System.out.println(failObj.toString());
	}

	@Test(expected = NoSuchElementException.class)
	public void testConstructorEmptyInputString() {
		EdgeField failObj = new EdgeField("");
		System.out.println(failObj.toString());
	}

	@Test(expected = NoSuchElementException.class)
	public void testConstructorSingleFieldInInputString() {
		EdgeField failObj = new EdgeField("2");
		System.out.println(failObj.toString());
	}

	@Test(expected = NoSuchElementException.class)
	public void testConstructorEmptyStringAfterDelimiter() {
		EdgeField failObj = new EdgeField("2|");
		System.out.println(failObj.toString());
	}

	@Test
	public void testConstructorIntInsteadOfString() {
		EdgeField testObj2 = new EdgeField("2|2");
		assertEquals("name should have been initialized to 2", "2", testObj2.getName());
	}

	@Test
	public void testConstructorValid() {
		assertEquals("numFigure should have been initialized to 2", 2, testObj.getNumFigure());
		assertEquals("name should have been initialized to Test", "Test", testObj.getName());
		assertEquals("tableID should have been initialized to 0", 0, testObj.getTableID());
		assertEquals("tableBound should have been initialized to 0", 0, testObj.getTableBound());
		assertEquals("fieldBound should have been initialized to 0", 0, testObj.getFieldBound());
		assertEquals("disallowNull should have been initialized to false", false, testObj.getDisallowNull());
		assertEquals("isPrimaryKey should have been initialized to false", false, testObj.getIsPrimaryKey());
		assertEquals("defaultValue should have been initialized to an empty string", "", testObj.getDefaultValue());
		assertEquals("varcharValue should have been initialized to 1", 1, testObj.getVarcharValue());
		assertEquals("dataType should have been initialized to 0", 0, testObj.getDataType());
	}
	
	@Test
	public void testSetVarcharValueNegative() {
		testObj.setVarcharValue(-1);
		assertNotEquals("varcharValue should not have been changed to -1", -1, testObj.getVarcharValue());
	}

	@Test
	public void testSetVarcharValueZero() {
		testObj.setVarcharValue(0);
		assertNotEquals("varcharValue should not have been changed to 0", 0, testObj.getVarcharValue());
	}
	
	@Test
	public void testSetVarcharValueValid() {
		testObj.setVarcharValue(2);
		assertEquals("varcharValue should have been changed to 2", 2, testObj.getVarcharValue());
	}

	@Test
	public void testSetVarcharValueHigh() {
		testObj.setVarcharValue(10000);
		assertEquals("varcharValue should have been changed to 10000", 10000, testObj.getVarcharValue());
	}

	@Test
	public void testSetDataTypeNegative() {
		testObj.setDataType(-1);
		assertNotEquals("dataType should not have been changed to -1", -1, testObj.getDataType());
	}

	@Test
	public void testSetDataTypeToEdgeOfUpperBound() {
		testObj.setDataType(4);
		assertNotEquals("dataType should not have been changed to 4", 4, testObj.getDataType());
	}

	@Test
	public void testSetDataTypeWithinUpperBound() {
		testObj.setDataType(3);
		assertEquals("dataType should have been changed to 3", 3, testObj.getDataType());
	}
	
	@Test
	public void testSetDataTypeTooHigh() {
		testObj.setDataType(20);
		assertNotEquals("dataType should not have been changed to 20", 20, testObj.getDataType());
	}

	@Test
	public void testSetDataTypeValid() {
		testObj.setDataType(2);
		assertEquals("dataType should have been changed to 2", 2, testObj.getDataType());
	}

	@Test
	public void testSetDataTypeZero() {
		testObj.setDataType(0);
		assertEquals("dataType should have been changed to 0", 0, testObj.getDataType());
	}
}
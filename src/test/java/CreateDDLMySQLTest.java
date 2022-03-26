import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CreateDDLMySQLTest {
	CreateDDLMySQL testObj1;


	@Before
	public void setUp() throws Exception {
		// with args
		// EdgeTable[] inputTables, EdgeField[] inputFields
		EdgeField[] efa = new EdgeField[1];
		EdgeTable[] eta = new EdgeTable[1];
		EdgeTable et = new EdgeTable("1|testTable");
		EdgeField ef = new EdgeField("2|testField");
		// et.addNativeField(0, 2);
		et.makeArrays();
		efa[0] = ef;
		eta[0] = et;
		testObj1 = new CreateDDLMySQL(eta, efa);
		// testObj1.toString();
	}

	@Test
	public void confirmObjectSuperAccess() {
		// confirm super object was created, and can access unprotected objects
		assertEquals("Testing object's super access to unprotected attribute products{MySQL}","MySQL",testObj1.products[0]);
	}

	@Test
	public void testCreateDDL_enter_blank_thenEnter_MySQLDB(){
		testObj1.createDDL();
		// TRY ENTERING AN EMPTY STRING: ""
		// WHEN THAT DISPLAYS WARNING, ENTER: "MySQLDB"
		assertEquals("Testing createDDL() databasename is 'MySQLDB'", "MySQLDB", testObj1.databaseName);
	}

	@Test
	public void testStringBufferNotNull(){
		assertNotNull("Checking that sb is not null", testObj1.sb);
	}

	@Test
	public void testConvertStrBooleanToInt_true(){
		assertEquals("convertStrBooleanToInt test: 'true' --> 1", 1, testObj1.convertStrBooleanToInt("true"));
	}

	@Test
	public void testConvertStrBooleanToInt_false(){
		assertEquals("convertStrBooleanToInt test: 'false' --> 0", 0, testObj1.convertStrBooleanToInt("false"));
	}

	@Test
	public void testConvertStrBooleanToInt_pizza(){
		assertEquals("convertStrBooleanToInt test: 'pizza' --> 0", 0, testObj1.convertStrBooleanToInt("pizza"));
	}

	@Test
	public void testConvertStrBooleanToInt_TRUE(){
		assertEquals("convertStrBooleanToInt test: 'TRUE' --> 0", 0, testObj1.convertStrBooleanToInt("TRUE"));
	}

	@Test
	public void testGenerateDatabaseName_enter_blank_thenEnter_MySQLDB(){
		// TRY ENTERING AN EMPTY STRING: ""
		// WHEN THAT DISPLAYS WARNING, ENTER: "MySQLDB"
		assertEquals("Testing generateDatabaseName() databasename is 'MySQLDB'", "MySQLDB", testObj1.generateDatabaseName());
	}

	@Test
	public void testGetDatabaseName_usedForOtherClasses_protected_generated(){
		// need to generate a DB name before trying to retrieve it
		assertEquals("Testing generateDatabaseName() databasename is 'MySQLDB'", "MySQLDB", testObj1.generateDatabaseName());
		assertEquals("Testing getDatabaseName() expecting 'MySQLDB'", "MySQLDB", testObj1.getDatabaseName());
	}

	@Test
	public void testGetDatabaseName_usedForOtherClasses_protected_null(){
		assertNull("Testing getDatabaseName() expecting null", testObj1.getDatabaseName());
	}

	@Test
	public void testDatabaseName_protected_null(){
		assertNull("Testing protected variable databaseName", testObj1.databaseName);
	}

	@Test
	public void testGetProductName_usedForOtherClasses_protected(){
		assertEquals("Testing getProductName() expecting 'MySQL'", "MySQL", testObj1.getProductName());
	}

	@Test
	public void testGetSQLStringLengthReturned(){
		assertNotEquals("Testing getSQLString() returns a string with a length longer than 5", 5, testObj1.getSQLString());
	}

	@Test
	public void testGetSQLStringNameGeneratedProperly(){
		assertNotEquals("Run getSQLString() to generate db name", 5, testObj1.getSQLString());
		assertNotNull("Testing getDatabaseName() expecting not null",testObj1.getDatabaseName());
	}

}


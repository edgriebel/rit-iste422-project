import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EdgeConvertCreateDDLTest{

	EdgeConvertCreateDDL withoutArgs;
	EdgeConvertCreateDDL withArgs;
	EdgeTable[] testTables;
	EdgeField[] testFields;

	// Because EdgeConvertCreateDDL is an abstract class, creates a test class that extends it.
	// All constructors for test class push arguments through to the super class.
	public class Tester extends EdgeConvertCreateDDL{

		public Tester(){
			super();
		}

		public Tester(EdgeTable[] tables, EdgeField[] fields){
			super(tables, fields);
		}
		
		@Override
		public String getDatabaseName(){
			return null;
		}

		@Override
		public String getProductName(){
			return null;
		}

		@Override
   	public String getSQLString(){
		 	return null;
	 	}

		@Override
   	public void createDDL(){
		 	return;
	 	}
	}

	@Before
	public void setUp(){
		testTables = new EdgeTable[3];
		EdgeTable testTableOne = new EdgeTable("0|testTableOne");
		EdgeTable testTableTwo = new EdgeTable("1|testTableTwo");
		EdgeTable testTableThree = new EdgeTable("-2|testTableThree");
		testTableOne.makeArrays();
		testTableTwo.makeArrays();
		testTableThree.makeArrays();
		testTables[0] = testTableOne;
		testTables[1] = testTableTwo;
		testTables[2] = testTableThree;
		testFields = new EdgeField[3];
		testFields[0] = new EdgeField("0|testFieldOne");
		testFields[1] = new EdgeField("1|testFieldTwo");
		testFields[2] = new EdgeField("-2|testFieldThree");
		
		withoutArgs = new Tester();
		withArgs = new Tester(testTables, testFields);
	}

	@Test
	public void testConstructor(){
		assertNotNull("Constructor with arguments created object", withArgs);
	}

	
	@Test
	public void testNumBoundTablesInitialized(){
		assertNotNull("numBoundTables was initialized by initialize() when constructed with args", withArgs.numBoundTables);
	}

	@Test
	public void testMaxBoundTablesInitialized(){
		assertNotNull("maxBound was initialized by initialize() when constructed with args", withArgs.maxBound);
	}
	
	@Test
	public void testConstructorWithoutArgs(){
		assertNotNull("Constructor without arguments created object", withoutArgs);
	}

	@Test
	public void testNumBoundTablesUnsetOnUninitializedObject(){
		assertNull("numBoundTables confirmed unset when uninitialized", withoutArgs.numBoundTables );
	}

	@Test
	public void testMaxBoundUnsetOnUninitializedObject(){
		assertEquals("maxBound confirmed unset when uninitialized", withoutArgs.maxBound, 0 );
	}

	@Test
	public void testNumBoundTablesSetWithInitializeOnObjectWithoutArgs(){
		withoutArgs.tables = testTables;
		withoutArgs.fields = testFields;
		withoutArgs.initialize();
		assertNotNull("numBoundTables was initialized by initialize() when constructed without args", withoutArgs.numBoundTables);
	}

	@Test
	public void testMaxBoundTablesSetWithInitializeOnObjectWithoutArgs(){
		withoutArgs.tables = testTables;
		withoutArgs.fields = testFields;
		withoutArgs.initialize();
		assertNotNull("maxBound was initialized by initialize() when constructed without args", withoutArgs.maxBound );
	}

	@Test
	public void testEdgeTablesPlaced(){
		assertArrayEquals("Edge Tables placed correctly", withArgs.tables, testTables);
	}

	@Test
	public void testEdgeFieldsPlaced(){
		assertArrayEquals("Edge Fields placed correctly", withArgs.fields, testFields);
	}

	@Test
	public void testGetTable(){
		assertEquals("getTable confirmed to find existent tables", withArgs.getTable(1), testTables[1]);
	}

	@Test
	public void testGetNonexistentTable(){
		assertNull("getTable confirmed to handle nonexistent values", withArgs.getTable(4));
	}

	@Test
	public void testGetNegativeTable(){
		assertEquals("getTable confirmed to find negative tables", withArgs.getTable(-2), testTables[2]);
	}
	
	@Test
	public void testGetNonexistentNegativeTable(){
		assertNull("getTable confirmed to handle nonexistent negative values", withArgs.getTable(-1));
	}

	@Test
	public void testGetField(){
		assertEquals("getField confirmed to find existent fields", withArgs.getField(1), testFields[1]);
	}
	
	@Test
	public void testGetNonexistentField(){
		assertNull("getField confirmed to handle nonexistent fields", withArgs.getField(4));
	}

	@Test
	public void testGetNegativeField(){
		assertEquals("getField confirmed to find negative fields", withArgs.getField(-2), testFields[2]);
	}

	@Test
	public void testGetNonexistentNegativeField(){
		assertNull("getField confirmed to handle nonexistent negative values", withArgs.getField(-1));
	}
}
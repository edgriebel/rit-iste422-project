import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EdgeConvertCreateDDLTest {
    EdgeConvertCreateDDL eccd;
    EdgeTable[] tables;
    EdgeField[] fields;

    @Before
    public void setup() {

        tables = new EdgeTable[2];
        EdgeTable table1 = new EdgeTable("0|TEST");
        EdgeTable table2 = new EdgeTable("1|JUNIT");
        table1.makeArrays();
        table2.makeArrays();
        tables[0] = table1;
        tables[1] = table2;

        fields = new EdgeField[2];
        EdgeField field1 = new EdgeField("0|Test");
        EdgeField field2 = new EdgeField("1|Junit");
        fields[0] = field1;
        fields[1] = field2;

        eccd = new EdgeConvertCreateDDL(tables, fields) {

            @Override
            public String getProductName() {
                return null;
            }

            @Override
            public String getDatabaseName() {
                return null;
            }

            @Override
            public String getSQLString() {
                return null;
            }

            @Override
            public void createDDL() {

            }
        };
    }

    @Test
    public void givenCreateDDLMySQLWithTwoBoundTables() {
        assertEquals("numBoundTables length should be two", eccd.numBoundTables.length, 2);
    }

    @Test
    public void givenCreateDDLMySQLWithTableAtIndex0ContainsNameTEST() {
        EdgeTable table = eccd.getTable(0);
        assertEquals("eccd should have a table at index 0 named TEST", table.getName(), "TEST");
    }

    @Test
    public void givenCreateDDLMySQLWithTableAtIndex3shouldBeNull() {
        EdgeTable table = eccd.getTable(3);
        assertEquals("eccd shouldnt have a table at index 3", table, null);
    }

    @Test
    public void givenCreateDDLMySQLWithFieldAtIndex1ContainsNameJunit() {
        EdgeField field = eccd.getField(1);
        assertEquals("eccd should have a table at index 0 named Junit", field.getName(), "Junit");
    }

    @Test
    public void givenCreateDDLMySQLWithFieldAtIndex4shouldBeNull() {
        EdgeField field = eccd.getField(4);
        assertEquals("eccd shouldnt have a field at index 4", field, null);
    }

    @Test
    public void givenEdgeConvertCreateDDLTestWithNoParams_ThenLeaveMostPropertiesNull() {

        EdgeConvertCreateDDL eccdWithNoParams = new EdgeConvertCreateDDL() {

            @Override
            public String getProductName() {
                return null;
            }

            @Override
            public String getDatabaseName() {
                return null;
            }

            @Override
            public String getSQLString() {
                return null;
            }

            @Override
            public void createDDL() {

            }
        };
        assertArrayEquals("eccdWithNoParams shouldnt have any numBoundTables", eccdWithNoParams.numBoundTables, null);
        assertEquals("maxBound should be zero because numBoundTables is null", eccdWithNoParams.maxBound, 0);
        assertEquals("stringBuffer should be null", eccdWithNoParams.sb, null);
        assertEquals("selected be zero", eccdWithNoParams.selected, 0);
        assertArrayEquals("eccdWithNoParams shouldnt have any table", eccdWithNoParams.tables, null);
        assertArrayEquals("eccdWithNoParams shouldnt have any field", eccdWithNoParams.fields, null);
    }
}

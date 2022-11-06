import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EdgeConvertCreateDDLTest {
    EdgeConvertCreateDDL eccd;
    EdgeTable[] tables;
    EdgeField[] fields;

    @Before
    public void setup() {

        tables = new EdgeTable[3];
        EdgeTable table1 = new EdgeTable("0|TEST");
        EdgeTable table2 = new EdgeTable("1|JUNIT");
        EdgeTable table3 = new EdgeTable("999|JAVA");
        table1.makeArrays();
        table2.makeArrays();
        table3.makeArrays();
        tables[0] = table1;
        tables[1] = table2;
        tables[2] = table3;

        fields = new EdgeField[3];
        EdgeField field1 = new EdgeField("0|Test");
        EdgeField field2 = new EdgeField("5|Junit");
        EdgeField field3 = new EdgeField("999|Java");
        fields[0] = field1;
        fields[1] = field2;
        fields[2] = field3;

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
    public void givenCreateDDLMySQLWithTableNumFigure0ContainsNameTEST() {
        EdgeTable table = eccd.getTable(0);
        assertEquals("table with numFigure 0 should have name TEST", table.getName(), "TEST");
    }

    @Test
    public void givenCreateDDLMySQLWithTableNumFigure1ContainsNameJUNIT() {
        EdgeTable table = eccd.getTable(1);
        assertEquals("table with numFigure 1 should have name JUNIT", table.getName(), "JUNIT");
    }

    @Test
    public void givenCreateDDLMySQLWithTableNumFigure999ContainsNameJAVA() {
        EdgeTable table = eccd.getTable(999);
        assertEquals("table with numFigure 999 should have name JAVA", table.getName(), "JAVA");
    }

    @Test
    public void givenCreateDDLMySQLWithTableNumFigure3shouldBeNull() {
        EdgeTable table = eccd.getTable(3);
        assertEquals("table with numFigure 3 should be null", table, null);
    }

    @Test
    public void givenCreateDDLMySQLWithFieldNumFigure0ContainsNameTest() {
        EdgeField field = eccd.getField(0);
        assertEquals("field with numFigure 0 should have name Test", field.getName(), "Test");
    }

    @Test
    public void givenCreateDDLMySQLWithFieldNumFigure5ContainsNameJunit() {
        EdgeField field = eccd.getField(5);
        assertEquals("field with numFigure 5 should have named Junit", field.getName(), "Junit");
    }

    @Test
    public void givenCreateDDLMySQLWithFieldNumFigure999ContainsNameJava() {
        EdgeField field = eccd.getField(999);
        assertEquals("field with numFigure 999 should have named Java", field.getName(), "Java");
    }

    @Test
    public void givenCreateDDLMySQLWithFieldNumFigure4shouldBeNull() {
        EdgeField field = eccd.getField(4);
        assertEquals("eccd shouldnt have a field at index 4", field, null);
    }

    @Test
    public void givenAnyCreateDDLMySQLWithProductArray() {
        String[] products = { "MySQL" };
        assertArrayEquals("", EdgeConvertCreateDDL.products, products);
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

    @Test
    public void givenEdgeConvertCreateDDLTestWithParams() {

        EdgeTable[] tables = new EdgeTable[3];
        EdgeTable table1 = new EdgeTable("1000|COURSE");
        EdgeTable table2 = new EdgeTable("1|STUDENT");
        EdgeTable table3 = new EdgeTable("3|FACULTY");
        table1.makeArrays();
        table2.makeArrays();
        table3.makeArrays();
        tables[0] = table1;
        tables[1] = table2;
        tables[2] = table3;

        EdgeField[] fields = new EdgeField[3];
        EdgeField field1 = new EdgeField("5|Test");
        EdgeField field2 = new EdgeField("1|Junit");
        EdgeField field3 = new EdgeField("3|Edge");
        fields[0] = field1;
        fields[1] = field2;
        fields[2] = field3;

        EdgeConvertCreateDDL eccdWithParams = new EdgeConvertCreateDDL(tables, fields) {

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

        int[] actualNumBoundTables = { 0, 0, 0 };
        assertArrayEquals("eccdWithParams should have 3 numBoundTables with zero value each",
                eccdWithParams.numBoundTables, actualNumBoundTables);

        assertArrayEquals("eccdWithParams should have three tables with specific params", eccdWithParams.tables,
                tables);

        assertArrayEquals("eccdWithParams should have three fields with specific params", eccdWithParams.fields,
                fields);

        assertEquals("field at index 0, numFigure should be 5 and name should be Test",
                eccdWithParams.fields[0].toString(), "5|Test|0|0|0|0|1|false|false|");

        assertEquals("table at index 0, numFigure should be 1000 and name should be COURSE",
                eccdWithParams.tables[0].toString(),
                "Table: 1000\r\n{\r\nTableName: COURSE\r\nNativeFields: \r\nRelatedTables: \r\nRelatedFields: \r\n}\r\n");
    }
}

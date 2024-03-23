import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   
import javax.swing.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;

public class CreateDDLMySQL extends EdgeConvertCreateDDL {
    Logger logger = Logger.getLogger(CreateDDLMySQL.class.getName());

    protected String databaseName;
    protected String[] strDataType = {"VARCHAR", "BOOL", "INT", "DOUBLE"};
    protected StringBuffer sb;

    public CreateDDLMySQL(EdgeTable[] inputTables, EdgeField[] inputFields) {
        super(inputTables, inputFields);
        sb = new StringBuffer();
    }

    public CreateDDLMySQL() {
        // default constructor
    }

    public void createDDL() {
        try {
            EdgeConvertGUI.setReadSuccess(true);
            databaseName = generateDatabaseName();
            logger.info("Database Name set to " + databaseName);
            logger.log(Level.FINE, "Debug message: Creating database " + databaseName); // Debug log

            sb.append("CREATE DATABASE " + databaseName + ";\r\n");
            sb.append("USE " + databaseName + ";\r\n");

            for (int boundCount = 0; boundCount <= maxBound; boundCount++) {
                for (int tableCount = 0; tableCount < numBoundTables.length; tableCount++) {
                    if (numBoundTables[tableCount] == boundCount) {
                        sb.append("CREATE TABLE " + tables[tableCount].getName() + " (\r\n");
                        logger.info("Creating table: " + tables[tableCount].getName());

                        int[] nativeFields = tables[tableCount].getNativeFieldsArray();
                        int[] relatedFields = tables[tableCount].getRelatedFieldsArray();
                        boolean[] primaryKey = new boolean[nativeFields.length];
                        int numPrimaryKey = 0;
                        int numForeignKey = 0;

                        for (int nativeFieldCount = 0; nativeFieldCount < nativeFields.length; nativeFieldCount++) {
                            EdgeField currentField = getField(nativeFields[nativeFieldCount]);
                            sb.append("\t" + currentField.getName() + " " + strDataType[currentField.getDataType()]);

                            if (currentField.getDataType() == 0) {
                                sb.append("(" + currentField.getVarcharValue() + ")");
                            }

                            if (currentField.getDisallowNull()) {
                                sb.append(" NOT NULL");
                            }

                            if (!currentField.getDefaultValue().equals("")) {
                                if (currentField.getDataType() == 1) {
                                    sb.append(" DEFAULT " + convertStrBooleanToInt(currentField.getDefaultValue()));
                                } else {
                                    sb.append(" DEFAULT " + currentField.getDefaultValue());
                                }
                            }

                            if (currentField.getIsPrimaryKey()) {
                                primaryKey[nativeFieldCount] = true;
                                numPrimaryKey++;
                            } else {
                                primaryKey[nativeFieldCount] = false;
                            }

                            if (currentField.getFieldBound() != 0) {
                                numForeignKey++;
                            }

                            sb.append(",\r\n");
                        }

                        // Constraints
                        if (numPrimaryKey > 0) {
                            sb.append("CONSTRAINT " + tables[tableCount].getName() + "_PK PRIMARY KEY (");
                            for (int i = 0; i < primaryKey.length; i++) {
                                if (primaryKey[i]) {
                                    sb.append(getField(nativeFields[i]).getName());
                                    numPrimaryKey--;
                                    if (numPrimaryKey > 0) {
                                        sb.append(", ");
                                    }
                                }
                            }
                            sb.append(")");
                            if (numForeignKey > 0) {
                                sb.append(",");
                            }
                            sb.append("\r\n");
                        }

                        if (numForeignKey > 0) {
                            int currentFK = 1;
                            for (int i = 0; i < relatedFields.length; i++) {
                                if (relatedFields[i] != 0) {
                                    sb.append("CONSTRAINT " + tables[tableCount].getName() + "_FK" + currentFK +
                                            " FOREIGN KEY(" + getField(nativeFields[i]).getName() + ") REFERENCES " +
                                            getTable(getField(nativeFields[i]).getTableBound()).getName() + "(" + getField(relatedFields[i]).getName() + ")");
                                    if (currentFK < numForeignKey) {
                                        sb.append(",\r\n");
                                    }
                                    currentFK++;
                                }
                            }
                            sb.append("\r\n");
                        }

                        sb.append(");\r\n\r\n");
                    }
                }
            }
            logger.info("DDL Creation finished");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Fatal error occurred while creating DDL: " + e.getMessage(), e);
            EdgeConvertGUI.setReadSuccess(false);
        }
    }

    protected int convertStrBooleanToInt(String input) {
        return input.equals("true") ? 1 : 0;
    }

    public String generateDatabaseName() {
        try {
            String dbNameDefault = "MySQLDB";

            do {
                databaseName = (String) JOptionPane.showInputDialog(
                               null,
                               "Enter the database name:",
                               "Database Name",
                               JOptionPane.PLAIN_MESSAGE,
                               null,
                               null,
                               dbNameDefault);
                if (databaseName == null) {
                    EdgeConvertGUI.setReadSuccess(false);
                    return "";
                }
                if (databaseName.equals("")) {
                    JOptionPane.showMessageDialog(null, "You must select a name for your database.");
                    logger.info("Database selected: " + databaseName);
                }
            } while (databaseName.equals(""));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Fatal error occurred while creating DDL: " + e.getMessage(), e);
            EdgeConvertGUI.setReadSuccess(false);
        }
        return databaseName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getProductName() {
        return "MySQL";
    }

    public String getSQLString() {
        try {
            createDDL();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error occurred while creating DDL: " + e.getMessage(), e);
        }
        return sb.toString();
    }
}

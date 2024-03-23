import java.util.StringTokenizer;
import java.util.logging.Logger;

public class EdgeField {
   public static Logger logger = Logger.getLogger(EdgeField.class.getName());
   private int numFigure, tableID, tableBound, fieldBound, dataType, varcharValue;
   private String name, defaultValue;
   private boolean disallowNull, isPrimaryKey;
   private static String[] strDataType = {"Varchar", "Boolean", "Integer", "Double"};
   public static final int VARCHAR_DEFAULT_LENGTH = 1;
   
   public EdgeField(String inputString) {
      StringTokenizer st = new StringTokenizer(inputString, EdgeConvertFileParser.DELIM);
      numFigure = Integer.parseInt(st.nextToken());
      name = st.nextToken();
      tableID = 0;
      tableBound = 0;
      fieldBound = 0;
      disallowNull = false;
      isPrimaryKey = false;
      defaultValue = "";
      varcharValue = VARCHAR_DEFAULT_LENGTH;
      dataType = 0;
   }
   
   public int getNumFigure() {
      logger.info("NumFigure is " + numFigure);
      return numFigure;
   }
   
   public String getName() {
      logger.info("Name is " + name);
      return name;
   }
   
   public int getTableID() {
      logger.info("Table ID is " + tableID);
      return tableID;
   }
   
   public void setTableID(int value) {
      logger.info("Setting table ID to " + value);
      tableID = value;
   }
   
   public int getTableBound() {
      logger.info("Table bound is " + tableBound);
      return tableBound;
   }
   
   public void setTableBound(int value) {
      logger.info("Setting table bound to " + value);
      tableBound = value;
   }

   public int getFieldBound() {
      logger.info("Field bound is " + fieldBound);
      return fieldBound;
   }
   
   public void setFieldBound(int value) {
      logger.info("Setting field bound to " + value);
      fieldBound = value;
   }

   public boolean getDisallowNull() {
      return disallowNull;
   }
   
   public void setDisallowNull(boolean value) {
      disallowNull = value;
   }
   
   public boolean getIsPrimaryKey() {
      logger.info("Primary key is " + isPrimaryKey);
      return isPrimaryKey;
   }
   
   public void setIsPrimaryKey(boolean value) {
      logger.info("Setting Primary Key to " + value);
      isPrimaryKey = value;
   }
   
   public String getDefaultValue() {
      logger.info("Default value is " + defaultValue);
      return defaultValue;
   }
   
   public void setDefaultValue(String value) {
      logger.info("Setting default value to " + value);
      defaultValue = value;
   }
   
   public int getVarcharValue() {
      logger.info("VARCHAR value is " + varcharValue);
      return varcharValue;
   }
   
   public void setVarcharValue(int value) {
      if (value > 0) {
         logger.info("Setting VARCHAR value to " + value);
         varcharValue = value;
      }
   }
   public int getDataType() {
      logger.info("DataType is " + dataType);
      return dataType;
   }
   
   public void setDataType(int value) {
      if (value >= 0 && value < strDataType.length) {
         logger.info("Setting DataType to " + value);
         dataType = value;
      }
   }
   
   public static String[] getStrDataType() {
      logger.info("StrDataType is " + strDataType);
      return strDataType;
   }
   
   public String toString() {
      return numFigure + EdgeConvertFileParser.DELIM +
      name + EdgeConvertFileParser.DELIM +
      tableID + EdgeConvertFileParser.DELIM +
      tableBound + EdgeConvertFileParser.DELIM +
      fieldBound + EdgeConvertFileParser.DELIM +
      dataType + EdgeConvertFileParser.DELIM +
      varcharValue + EdgeConvertFileParser.DELIM +
      isPrimaryKey + EdgeConvertFileParser.DELIM +
      disallowNull + EdgeConvertFileParser.DELIM +
      defaultValue;
   }
}

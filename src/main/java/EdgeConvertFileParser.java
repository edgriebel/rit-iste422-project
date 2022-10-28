import java.io.*;
import java.util.*;
import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EdgeConvertFileParser {
   //private String filename = "test.edg";
   private File parseFile;
   private FileReader fr;
   private BufferedReader br;
   private String currentLine;
   private ArrayList alTables, alFields, alConnectors;
   private EdgeTable[] tables;
   private EdgeField[] fields;
   private EdgeField tempField;
   private EdgeConnector[] connectors;
   private String style;
   private String text;
   private String tableName;
   private String fieldName;
   private boolean isEntity, isAttribute, isUnderlined = false;
   private int numFigure, numConnector, numFields, numTables, numNativeRelatedFields;
   private int endPoint1, endPoint2;
   private int numLine;
   private String endStyle1, endStyle2;
   public static final String EDGE_ID = "EDGE Diagram File"; //first line of .edg files should be this
   public static final String SAVE_ID = "EdgeConvert Save File"; //first line of save files should be this
   public static final String DELIM = "|";
   
   public static Logger logger = LogManager.getLogger(EdgeConvertFileParser.class.getName());
   public static Logger timeLogger = LogManager.getLogger("timer." + EdgeConvertFileParser.class.getName());
   
   public EdgeConvertFileParser(File constructorFile) {
      timeLogger.info("Constructor called.");
      logger.debug(String.format("Constructor called with file: %s", constructorFile.getAbsolutePath()));

      numFigure = 0;
      numConnector = 0;
      alTables = new ArrayList();
      alFields = new ArrayList();
      alConnectors = new ArrayList();
      isEntity = false;
      isAttribute = false;
      parseFile = constructorFile;
      numLine = 0;
      
      this.openFile(parseFile);

      timeLogger.info("Constructor ended.");
   }

   public void parseEdgeFile() throws IOException {
      timeLogger.info("parseEdgeFile() called.");

      logger.info("Reading Edge Diagrammer file.");
      logger.debug("About to read all lines in Edge Diagrammer file.");

      while ((currentLine = br.readLine()) != null) {
         currentLine = currentLine.trim();

         logger.debug(String.format("Current line in diagrammer file is: %s", currentLine));

         if (currentLine.startsWith("Figure ")) { //this is the start of a Figure entry
            logger.info("Parsing diagrammer file Figures.");

            numFigure = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); //get the Figure number
            currentLine = br.readLine().trim(); // this should be "{"
            currentLine = br.readLine().trim();

            logger.debug(String.format("Current line in diagrammer file is: %s", currentLine));
            logger.debug(String.format("Current figure number in loop is: %d", numFigure));

            if (!currentLine.startsWith("Style")) { // this is to weed out other Figures, like Labels
               logger.debug("Current line does not start with 'Style'. Continuing through loop.");

               continue;
            } else {
               style = currentLine.substring(currentLine.indexOf("\"") + 1, currentLine.lastIndexOf("\"")); //get the Style parameter

               logger.debug(String.format("Current style parameter in loop is: %s", style));

               if (style.startsWith("Relation")) { //presence of Relations implies lack of normalization
                  logger.info("Failed to read Edge Diagram file.");
                  logger.warn("Style parameter found with Relation. Lack of normalization.");
                  logger.debug("'Relation' keyword found in style parameter.");

                  JOptionPane.showMessageDialog(null, "The Edge Diagrammer file\n" + parseFile + "\ncontains relations.  Please resolve them and try again.");
                  EdgeConvertGUI.setReadSuccess(false);
                  break;
               }

               if (style.startsWith("Entity")) {
                  logger.debug("'Entity' keyword found in style parameter. Setting isEntity to 'true'.");

                  isEntity = true;
               }

               if (style.startsWith("Attribute")) {
                  logger.debug("'Attribute' keyword found in style parameter. Setting isAttribute to 'true'.");

                  isAttribute = true;
               }

               if (!(isEntity || isAttribute)) { //these are the only Figures we're interested in
                  logger.debug("isEntity and isAttribute are both false. Continuing through loop.");

                  continue;
               }

               currentLine = br.readLine().trim(); //this should be Text
               text = currentLine.substring(currentLine.indexOf("\"") + 1, currentLine.lastIndexOf("\"")).replaceAll(" ", ""); //get the Text parameter

               logger.debug(String.format("Current line in diagrammer file is: %s", currentLine));
               logger.debug(String.format("Current text parameter in loop is: %s", text));

               if (text.equals("")) {
                  logger.debug("Text parameter is an empty string.");
                  logger.warn("Certain entities/attributes contain blank names.");

                  JOptionPane.showMessageDialog(null, "There are entities or attributes with blank names in this diagram.\nPlease provide names for them and try again.");
                  EdgeConvertGUI.setReadSuccess(false);
                  break;
               }

               int escape = text.indexOf("\\");

               logger.debug(String.format("Escape character found at index '%d' of text parameter.", escape));

               if (escape > 0) { //Edge denotes a line break as "\line", disregard anything after a backslash
                  logger.debug("Escape character position = 0. Ignoring all text that comes after it.");

                  text = text.substring(0, escape);
               }

               logger.debug("About to loop through current record to search for underlined text.");

               do { //advance to end of record, look for whether the text is underlined
                  currentLine = br.readLine().trim();

                  logger.debug(String.format("Current line in diagrammer file is: %s", currentLine));

                  if (currentLine.startsWith("TypeUnderl")) {
                     logger.debug("'TypeUnder1' keyword found in current record. Setting isUnderlined to 'true'.");

                     isUnderlined = true;
                  }
               } while (!currentLine.equals("}")); // this is the end of a Figure entry

               logger.debug("'}' character found in current record. Finished looping through current record to search for underlined text.");
               
               if (isEntity) { //create a new EdgeTable object and add it to the alTables ArrayList
                  logger.debug("isEntity is 'true'. Checking for duplicate tables.");

                  if (isTableDup(text)) {
                     logger.debug(String.format("Duplicate tables found in text '%s'.", text));

                     JOptionPane.showMessageDialog(null, "There are multiple tables called " + text + " in this diagram.\nPlease rename all but one of them and try again.");
                     EdgeConvertGUI.setReadSuccess(false);
                     break;
                  }

                  EdgeTable table = new EdgeTable(numFigure + DELIM + text);

                  logger.debug(String.format("Adding table '%s' to alTables ArrayList.", table.getName()));

                  alTables.add(table);
               }

               if (isAttribute) { //create a new EdgeField object and add it to the alFields ArrayList
                  logger.debug("isAttribute is 'true'.");

                  tempField = new EdgeField(numFigure + DELIM + text);
                  tempField.setIsPrimaryKey(isUnderlined);
                  alFields.add(tempField);

                  logger.debug(String.format("Setting field '%s' as primary key: '%b'.", tempField.getName(), isUnderlined));
                  logger.debug(String.format("Adding field '%s' to alFields ArrayList.", tempField.getName()));
               }

               //reset flags
               isEntity = false;
               isAttribute = false;
               isUnderlined = false;
            }
         } // if("Figure")

         if (currentLine.startsWith("Connector ")) { //this is the start of a Connector entry
            logger.info("Parsing diagrammer file Connectors.");

            numConnector = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); //get the Connector number

            logger.debug(String.format("Current Connector number is: %d", numConnector));

            currentLine = br.readLine().trim(); // this should be "{"
            currentLine = br.readLine().trim(); // not interested in Style
            currentLine = br.readLine().trim(); // Figure1

            logger.debug(String.format("Current line in diagrammer file is: %s", currentLine));

            endPoint1 = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1));

            logger.debug(String.format("Current end point 1 is: %d", endPoint1));

            currentLine = br.readLine().trim(); // Figure2

            logger.debug(String.format("Current line in diagrammer file is: %s", currentLine));

            endPoint2 = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1));

            logger.debug(String.format("Current end point 2 is: %d", endPoint2));

            currentLine = br.readLine().trim(); // not interested in EndPoint1
            currentLine = br.readLine().trim(); // not interested in EndPoint2
            currentLine = br.readLine().trim(); // not interested in SuppressEnd1
            currentLine = br.readLine().trim(); // not interested in SuppressEnd2
            currentLine = br.readLine().trim(); // End1

            logger.debug(String.format("Current line in diagrammer file is: %s", currentLine));

            endStyle1 = currentLine.substring(currentLine.indexOf("\"") + 1, currentLine.lastIndexOf("\"")); //get the End1 parameter

            logger.debug(String.format("Current end style 1 is: %s", endStyle1));

            currentLine = br.readLine().trim(); // End2

            logger.debug(String.format("Current line in diagrammer file is: %s", currentLine));

            endStyle2 = currentLine.substring(currentLine.indexOf("\"") + 1, currentLine.lastIndexOf("\"")); //get the End2 parameter

            logger.debug(String.format("Current end style 2 is: %s", endStyle2));

            logger.debug("About to advance to end of current record.");

            do { //advance to end of record
               currentLine = br.readLine().trim();

               logger.debug(String.format("Current line in diagrammer file is: %s", currentLine));
            } while (!currentLine.equals("}")); // this is the end of a Connector entry

            logger.debug("'}' character found. Done advancing to end of current record.");

            String inputString = numConnector + DELIM + endPoint1 + DELIM + endPoint2 + DELIM + endStyle1 + DELIM + endStyle2;

            logger.debug(String.format("Adding new Connector '%s' to alConnectors ArrayList.", inputString));
            
            alConnectors.add(new EdgeConnector(inputString));
         } // if("Connector")
      } // while()

      logger.debug("Finished reading all lines in Edge Diagrammer file.");
      logger.info("Finished reading Edge Diagrammer file.");

      timeLogger.info("parseEdgeFile() ended.");
   } // parseEdgeFile()
   
   private void resolveConnectors() { //Identify nature of Connector endpoints
      timeLogger.info("resolveConnectors() called.");

      int endPoint1, endPoint2;
      int fieldIndex = 0, table1Index = 0, table2Index = 0;

      logger.debug(String.format("Looping through connectors of length: %d", connectors.length));

      for (int cIndex = 0; cIndex < connectors.length; cIndex++) {
         endPoint1 = connectors[cIndex].getEndPoint1();
         endPoint2 = connectors[cIndex].getEndPoint2();
         fieldIndex = -1;

         logger.debug(String.format("Current connector endpoint 1: %d", endPoint1));
         logger.debug(String.format("Current connector endpoint 2: %d", endPoint2));

         logger.debug(String.format("Looping through fields of length: %d", fields.length));

         for (int fIndex = 0; fIndex < fields.length; fIndex++) { //search fields array for endpoints
            if (endPoint1 == fields[fIndex].getNumFigure()) { //found endPoint1 in fields array
               logger.debug(String.format("Endpoint 1 of fields found at field index '%d', named '%s'.", fIndex, fields[fIndex].getName()));

               connectors[cIndex].setIsEP1Field(true); //set appropriate flag
               fieldIndex = fIndex; //identify which element of the fields array that endPoint1 was found in
            }
            if (endPoint2 == fields[fIndex].getNumFigure()) { //found endPoint2 in fields array
               logger.debug(String.format("Endpoint 2 of fields found at field index '%d', named '%s'.", fIndex, fields[fIndex].getName()));

               connectors[cIndex].setIsEP2Field(true); //set appropriate flag
               fieldIndex = fIndex; //identify which element of the fields array that endPoint2 was found in
            }
         }

         logger.debug("Finished looping through fields.");

         logger.debug(String.format("Looping through tables of length: %d", tables.length));

         for (int tIndex = 0; tIndex < tables.length; tIndex++) { //search tables array for endpoints
            if (endPoint1 == tables[tIndex].getNumFigure()) { //found endPoint1 in tables array
               logger.debug(String.format("Endpoint 1 of tables found at table index '%d', named '%s'.", tIndex, tables[tIndex].getName()));

               connectors[cIndex].setIsEP1Table(true); //set appropriate flag
               table1Index = tIndex; //identify which element of the tables array that endPoint1 was found in
            }

            if (endPoint2 == tables[tIndex].getNumFigure()) { //found endPoint2 in tables array
               logger.debug(String.format("Endpoint 2 of tables found at table index '%d', named '%s'.", tIndex, tables[tIndex].getName()));

               connectors[cIndex].setIsEP2Table(true); //set appropriate flag
               table2Index = tIndex; //identify which element of the tables array that endPoint2 was found in
            }
         }

         logger.debug("Finished looping through tables.");
         
         if (connectors[cIndex].getIsEP1Field() && connectors[cIndex].getIsEP2Field()) { //both endpoints are fields, implies lack of normalization
            logger.info("Failed to read file.");
            logger.warn("Composite attributes found in Edge Diagrammer file. Lack of normalization.");
            logger.debug(String.format("File %s contains composite attributes.", parseFile.getAbsolutePath()));

            JOptionPane.showMessageDialog(null, "The Edge Diagrammer file\n" + parseFile + "\ncontains composite attributes. Please resolve them and try again.");
            EdgeConvertGUI.setReadSuccess(false); //this tells GUI not to populate JList components
            break; //stop processing list of Connectors
         }

         if (connectors[cIndex].getIsEP1Table() && connectors[cIndex].getIsEP2Table()) { //both endpoints are tables
            if ((connectors[cIndex].getEndStyle1().indexOf("many") >= 0) &&
                (connectors[cIndex].getEndStyle2().indexOf("many") >= 0)) { //the connector represents a many-many relationship, implies lack of normalization
               logger.info("Failed to read file.");
               logger.warn("Many-to-many relationship found in connector. Lack of normalization.");
               logger.debug(String.format("Many-to-many relation found in connectors. EdgeConnector.getEndStyle1() returned: %s. EdgeConnector.getEndStyle2() returned: %s", connectors[cIndex].getEndStyle1(), connectors[cIndex].getEndStyle2()));

               JOptionPane.showMessageDialog(null, "There is a many-many relationship between tables\n\"" + tables[table1Index].getName() + "\" and \"" + tables[table2Index].getName() + "\"" + "\nPlease resolve this and try again.");
               EdgeConvertGUI.setReadSuccess(false); //this tells GUI not to populate JList components
               break; //stop processing list of Connectors
            } else { //add Figure number to each table's list of related tables
               logger.debug(String.format("Adding related table to table 1. Table 1 is '%s'. The related table is '%s'.", tables[table1Index].getName(), tables[table2Index].getName()));
               logger.debug(String.format("Adding related table to table 2. Table 2 is '%s'. The related table is '%s'.", tables[table2Index].getName(), tables[table1Index].getName()));

               tables[table1Index].addRelatedTable(tables[table2Index].getNumFigure());
               tables[table2Index].addRelatedTable(tables[table1Index].getNumFigure());
               continue; //next Connector
            }
         }
         
         if (fieldIndex >=0 && fields[fieldIndex].getTableID() == 0) { //field has not been assigned to a table yet
            logger.debug(String.format("fieldIndex '%s' has not been assigned a table yet.", fields[fieldIndex].getName()));

            if (connectors[cIndex].getIsEP1Table()) { //endpoint1 is the table
               logger.debug(String.format("End point 1, index '%d', is the table.", cIndex));

               logger.debug(String.format("Adding native field '%s' to table 1, named '%s'.", fields[fieldIndex].getName(), tables[table1Index].getName()));
               logger.debug(String.format("Setting table id to '%d' for field '%s'.", tables[table1Index].getNumFigure(), fields[fieldIndex].getName()));

               tables[table1Index].addNativeField(fields[fieldIndex].getNumFigure()); //add to the appropriate table's field list
               fields[fieldIndex].setTableID(tables[table1Index].getNumFigure()); //tell the field what table it belongs to
            } else { //endpoint2 is the table
               logger.debug("End point 2 is the table.");

               logger.debug(String.format("Adding native field '%s' to table 2, named '%s'.", fields[fieldIndex].getName(), tables[table2Index].getName()));
               logger.debug(String.format("Setting table id to '%d' for field '%s'.", tables[table2Index].getNumFigure(), fields[fieldIndex].getName()));

               tables[table2Index].addNativeField(fields[fieldIndex].getNumFigure()); //add to the appropriate table's field list
               fields[fieldIndex].setTableID(tables[table2Index].getNumFigure()); //tell the field what table it belongs to
            }
         } else if (fieldIndex >=0) { //field has already been assigned to a table
            logger.info("Failed to read file.");
            logger.debug(String.format("Field '%s' has already been assigned a table.", fields[fieldIndex].getName()));
            logger.warn(String.format("The attribute %s is connected to multiple tables.\nPlease resolve this and try again.", fields[fieldIndex].getName()));

            JOptionPane.showMessageDialog(null, "The attribute " + fields[fieldIndex].getName() + " is connected to multiple tables.\nPlease resolve this and try again.");
            EdgeConvertGUI.setReadSuccess(false); //this tells GUI not to populate JList components
            break; //stop processing list of Connectors
         }
      } // connectors for() loop

      logger.debug("Finished looping through connectors");

      timeLogger.info("resolveConnectors() ended.");
   } // resolveConnectors()
   
   public void parseSaveFile() throws IOException { //this method is unclear and confusing in places
      timeLogger.info("parseSaveFile() called.");

      StringTokenizer stTables, stNatFields, stRelFields, stNatRelFields, stField;
      EdgeTable tempTable;
      EdgeField tempField;
      currentLine = br.readLine();
      currentLine = br.readLine(); //this should be "Table: "

      logger.debug(String.format("Current line in save file is: %s", currentLine));
      logger.debug("About to check for 'Table: ' string inside of save file.");

      logger.info("Reading save file.");

      while (currentLine.startsWith("Table: ")) {
         logger.info("Parsing save file tables.");
         logger.debug(String.format("Current line in save file is: %s", currentLine));

         numFigure = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); //get the Table number
         currentLine = br.readLine(); //this should be "{"
         currentLine = br.readLine(); //this should be "TableName"
         tableName = currentLine.substring(currentLine.indexOf(" ") + 1);

         logger.debug(String.format("Current line in save file is: %s", currentLine));
         logger.debug(String.format("Current table number in loop is: %d", numFigure));
         logger.debug(String.format("Current table name in loop is: %s", tableName));

         logger.debug("Creating new EdgeTable.");

         tempTable = new EdgeTable(numFigure + DELIM + tableName);
         
         currentLine = br.readLine(); //this should be the NativeFields list
         stNatFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
         numFields = stNatFields.countTokens();

         logger.debug(String.format("Current line in save file is: %s", currentLine));
         logger.debug(String.format("Current number of fields in loop: %d", numFields));

         logger.debug(String.format("Looping through current table fields of length: %d", numFields));

         for (int i = 0; i < numFields; i++) {
            String nextToken = stNatFields.nextToken();

            logger.debug(String.format("Adding native field to EdgeTable: %s", nextToken));

            tempTable.addNativeField(Integer.parseInt(nextToken));
         }

         logger.debug("Finished looping through current table fields.");
         
         currentLine = br.readLine(); //this should be the RelatedTables list
         stTables = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
         numTables = stTables.countTokens();

         logger.debug(String.format("Current line in save file is: %s", currentLine));
         logger.debug(String.format("Current number of related tables in loop is: %d", numTables));

         logger.debug(String.format("Looping through current related tables of length: %d", numTables));

         for (int i = 0; i < numTables; i++) {
            String nextTable = stTables.nextToken();

            logger.debug(String.format("Adding related table to EdgeTable: %s", nextTable));

            tempTable.addRelatedTable(Integer.parseInt(nextTable));
         }

         logger.debug("Finished looping through current related tables.");

         tempTable.makeArrays();
         
         currentLine = br.readLine(); //this should be the RelatedFields list
         stRelFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
         numFields = stRelFields.countTokens();

         logger.debug(String.format("Current line in save file is: %s", currentLine));
         logger.debug(String.format("Current number of related fields in loop is: %d", numFields));

         logger.debug(String.format("Looping through current related fields of length: %d", numFields));

         for (int i = 0; i < numFields; i++) {
            String nextRelatedField = stRelFields.nextToken();

            logger.debug(String.format("Adding related field to EdgeTable: %s", nextRelatedField));

            tempTable.setRelatedField(i, Integer.parseInt(nextRelatedField));
         }

         logger.debug("Finished looping through current relatedfields.");

         logger.debug(String.format("Adding table '%s' to alTables ArrayList.", tempTable.getName()));

         alTables.add(tempTable);
         currentLine = br.readLine(); //this should be "}"
         currentLine = br.readLine(); //this should be "\n"
         currentLine = br.readLine(); //this should be either the next "Table: ", #Fields#

         logger.debug(String.format("Current line in save file is: %s", currentLine));

         logger.info("Done parsing save file tables.");
      
      }

      logger.debug("Finished checking for 'Table: ' string inside of save file.");

      logger.debug("About to check for fields inside of save file.");

      while ((currentLine = br.readLine()) != null) {
         logger.info("Parsing save file fields.");

         stField = new StringTokenizer(currentLine, DELIM);
         numFigure = Integer.parseInt(stField.nextToken());
         fieldName = stField.nextToken();

         logger.debug(String.format("Current line in save file is: %s", currentLine));
         logger.debug(String.format("Current figure number in loop is: %d", numFigure));
         logger.debug(String.format("Current field name in loop is: %s", fieldName));

         logger.debug("Creating new EdgeField.");

         tempField = new EdgeField(numFigure + DELIM + fieldName);
         tempField.setTableID(Integer.parseInt(stField.nextToken()));

         logger.debug(String.format("Setting table ID in EdgeField to: %d", tempField.getTableID()));

         tempField.setTableBound(Integer.parseInt(stField.nextToken()));

         logger.debug(String.format("Setting table bound in EdgeField to: %d", tempField.getTableBound()));

         tempField.setFieldBound(Integer.parseInt(stField.nextToken()));

         logger.debug(String.format("Setting field bound in EdgeField to: %d", tempField.getFieldBound()));

         tempField.setDataType(Integer.parseInt(stField.nextToken()));

         logger.debug(String.format("Setting data type in EdgeField to: %d", tempField.getDataType()));

         tempField.setVarcharValue(Integer.parseInt(stField.nextToken()));

         logger.debug(String.format("Setting varchar value in EdgeField to: %d", tempField.getVarcharValue()));

         tempField.setIsPrimaryKey(Boolean.valueOf(stField.nextToken()).booleanValue());

         logger.debug(String.format("Setting field as primary in EdgeField: %b", tempField.getIsPrimaryKey()));

         tempField.setDisallowNull(Boolean.valueOf(stField.nextToken()).booleanValue());

         logger.debug(String.format("Setting disallow null in EdgeField to: %b", tempField.getDisallowNull()));

         if (stField.hasMoreTokens()) { //Default Value may not be defined
            tempField.setDefaultValue(stField.nextToken());

            logger.debug(String.format("Setting default value in EdgeField to: %s", tempField.getDefaultValue()));
         }

         logger.debug(String.format("Adding field '%s' to alFields ArrayList.", tempField.getName()));

         alFields.add(tempField);

         logger.info("Done parsing save file fields.");
      
      }

      logger.info("Finished reading save file.");
      logger.debug("Finished checking for fields inside of save file.");

      timeLogger.info("parseSaveFile() ended.");
   } // parseSaveFile()

   private void makeArrays() { //convert ArrayList objects into arrays of the appropriate Class type
      timeLogger.info("makeArrays() called.");

      if (alTables != null) {
         logger.debug("alTables property is not null.");

         tables = (EdgeTable[])alTables.toArray(new EdgeTable[alTables.size()]);
      }
      if (alFields != null) {
         logger.debug("alFields property is not null.");

         fields = (EdgeField[])alFields.toArray(new EdgeField[alFields.size()]);
      }
      if (alConnectors != null) {
         logger.debug("alConnectors property is not null.");
         
         connectors = (EdgeConnector[])alConnectors.toArray(new EdgeConnector[alConnectors.size()]);
      }

      timeLogger.info("makeArrays() ended.");
   }
   
   private boolean isTableDup(String testTableName) {
      timeLogger.info("isTableDup() called.");
      logger.debug(String.format("isTableDup() called with testTableName: %s", testTableName));

      logger.debug(String.format("About to loop through alTables property of size: %d", alTables.size()));

      for (int i = 0; i < alTables.size(); i++) {
         EdgeTable tempTable = (EdgeTable)alTables.get(i);
         
         logger.debug(String.format("Current EdgeTable name in loop: %s", tempTable.getName()));

         if (tempTable.getName().equals(testTableName)) {
            logger.warn("Duplicate table found.");
            logger.debug(String.format("Duplicate table name: %s", tempTable.getName()));

            timeLogger.info("isTableDup() ended.");
            logger.debug("Finished looping through alTables property.");
            return true;
         }
      }

      logger.debug("Finished looping through alTables property.");

      timeLogger.info("isTableDup() ended.");
      return false;
   }
   
   public EdgeTable[] getEdgeTables() {
      logger.info("Getting edge tables.");

      return tables;
   }
   
   public EdgeField[] getEdgeFields() {
      logger.info("Getting edge fields.");

      return fields;
   }
   
   public void openFile(File inputFile) {
      timeLogger.info("openFile() called.");
      logger.debug(String.format("openFile() called with file: %s", inputFile.getAbsolutePath()));

      try {
         fr = new FileReader(inputFile);
         br = new BufferedReader(fr);

         //test for what kind of file we have
         currentLine = br.readLine().trim();
         numLine++;

         if (currentLine.startsWith(EDGE_ID)) { //the file chosen is an Edge Diagrammer file
            logger.info("Chosen file is an Edge Diagrammer file.");
            logger.debug(String.format("Edge Diagrammer file's first line is: %s", currentLine));

            this.parseEdgeFile(); //parse the file
            br.close();
            this.makeArrays(); //convert ArrayList objects into arrays of the appropriate Class type
            this.resolveConnectors(); //Identify nature of Connector endpoints
         } else {
            if (currentLine.startsWith(SAVE_ID)) { //the file chosen is a Save file created by this application
               logger.info("Chosen file is a Save file.");
               logger.debug(String.format("Save file's first line is: %s", currentLine));

               this.parseSaveFile(); //parse the file
               br.close();
               this.makeArrays(); //convert ArrayList objects into arrays of the appropriate Class type
            } else { //the file chosen is something else
               logger.warn("Unrecognized file format chosen.");
               logger.debug(String.format("Save file's first line is: %s", currentLine));

               logger.info("Warning user of unrecognized file format.");
               JOptionPane.showMessageDialog(null, "Unrecognized file format");
            }
         }
      } // try
      catch (FileNotFoundException fnfe) {
         logger.error(String.format("Cannot find: \"%s\".", inputFile.getAbsolutePath()));
         logger.trace(String.format("Cannot find: \"%s\".", inputFile.getAbsolutePath()));

         logger.info("Exiting program.");
         System.exit(0);
      } // catch FileNotFoundException
      catch (IOException ioe) {
         logger.error(String.format("Error reading file: \"%s\".", inputFile.getAbsolutePath()));
         logger.trace(String.format("Cannot reading file: \"%s\".", inputFile.getAbsolutePath()));

         logger.info("Exiting program.");
         System.exit(0);
      } // catch IOException

      timeLogger.info("EdgeConvertFileParser.openFile() ended.");
   } // openFile()
} // EdgeConvertFileHandler

// package src.main.java;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EdgeTable {
   private int numFigure;
   private String name;
   private ArrayList<Integer> alRelatedTables, alNativeFields;
   private int[] relatedTables, relatedFields, nativeFields;
   private static Logger logger = LogManager.getLogger(EdgeConnector.class.getName());

   
   public EdgeTable(String inputString) {
      logger.debug("Edge Table");
      StringTokenizer st = new StringTokenizer(inputString, EdgeConvertFileParser.DELIM);
      numFigure = Integer.parseInt(st.nextToken());
      name = st.nextToken();
      alRelatedTables = new ArrayList<Integer>();
      alNativeFields = new ArrayList<Integer>();
   }
   
   public int getNumFigure() {
      logger.debug("Getting Number Figure");
      return numFigure;
   }
   
   public String getName() {
      logger.debug("Getting EdgeTable name");
      return name;
   }
   
   public void addRelatedTable(int relatedTable) {
      alRelatedTables.add(relatedTable);
      logger.debug("add Related Table");
   }
   
   public int[] getRelatedTablesArray() {
      logger.debug("getting Related Tables array");
      return relatedTables;
   }
   
   public int[] getRelatedFieldsArray() {
      logger.debug("getting Related Fields array");
      return relatedFields;
   }
   
   public void setRelatedField(int index, int relatedValue) {
      logger.debug("getting Related Field");
      relatedFields[index] = relatedValue;
   }
   
   public int[] getNativeFieldsArray() {
      logger.debug("getting Native Fields array");
      return nativeFields;
   }

   public void addNativeField(int value) {
      logger.debug("add Native Field");
      alNativeFields.add(value);
   }

   public void moveFieldUp(int index) { //move the field closer to the beginning of the list
      if (index == 0) {
         logger.info("Field is at max level");
         return;
         
      }
      int tempNative = nativeFields[index - 1]; //save element at destination index
      nativeFields[index - 1] = nativeFields[index]; //copy target element to destination
      nativeFields[index] = tempNative; //copy saved element to target's original location
      int tempRelated = relatedFields[index - 1]; //save element at destination index
      relatedFields[index - 1] = relatedFields[index]; //copy target element to destination
      relatedFields[index] = tempRelated; //copy saved element to target's original location
      logger.debug("Move field up");
   }
   
   public void moveFieldDown(int index) { //move the field closer to the end of the list
      if (index == (nativeFields.length - 1)) {
         logger.info("Field is at lowest level");
         return;
      }
      int tempNative = nativeFields[index + 1]; //save element at destination index
      nativeFields[index + 1] = nativeFields[index]; //copy target element to destination
      nativeFields[index] = tempNative; //copy saved element to target's original location
      int tempRelated = relatedFields[index + 1]; //save element at destination index
      relatedFields[index + 1] = relatedFields[index]; //copy target element to destination
      relatedFields[index] = tempRelated; //copy saved element to target's original location
      logger.debug("Move field down");
   }

   public void makeArrays() { //convert the ArrayLists into int[]
      Integer[] temp;
      temp = (Integer[])alNativeFields.toArray(new Integer[alNativeFields.size()]);
      nativeFields = new int[temp.length];
      for (int i = 0; i < temp.length; i++) {
         nativeFields[i] = temp[i].intValue();
      }
      
      temp = (Integer[])alRelatedTables.toArray(new Integer[alRelatedTables.size()]);
      relatedTables = new int[temp.length];
      for (int i = 0; i < temp.length; i++) {
         relatedTables[i] = temp[i].intValue();
      }
      
      relatedFields = new int[nativeFields.length];
      for (int i = 0; i < relatedFields.length; i++) {
         relatedFields[i] = 0;
      }
      logger.debug("make Arrays for edge table");
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("Table: " + numFigure + "\r\n");
      sb.append("{\r\n");
      sb.append("TableName: " + name + "\r\n");
      sb.append("NativeFields: ");
      for (int i = 0; i < nativeFields.length; i++) {
         sb.append(nativeFields[i]);
         if (i < (nativeFields.length - 1)){
            sb.append(EdgeConvertFileParser.DELIM);
            logger.info("Native Field found");
         }
      }
      sb.append("\r\nRelatedTables: ");
      for (int i = 0; i < relatedTables.length; i++) {
         sb.append(relatedTables[i]);
         if (i < (relatedTables.length - 1)){
            sb.append(EdgeConvertFileParser.DELIM);
            logger.info("Related Table found");
         }
      }
      sb.append("\r\nRelatedFields: ");
      for (int i = 0; i < relatedFields.length; i++) {
         sb.append(relatedFields[i]);
         if (i < (relatedFields.length - 1)){
            sb.append(EdgeConvertFileParser.DELIM);
            logger.info("Related Field found");
         }
      }
      sb.append("\r\n}\r\n");
      logger.debug("convert table data to String");
      return sb.toString();
   }
}

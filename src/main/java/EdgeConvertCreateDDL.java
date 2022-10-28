import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class EdgeConvertCreateDDL {
   static String[] products = {"MySQL"};
   protected EdgeTable[] tables; //master copy of EdgeTable objects
   protected EdgeField[] fields; //master copy of EdgeField objects
   protected int[] numBoundTables;
   protected int maxBound;
   protected StringBuffer sb;
   protected int selected;
   
	public static Logger logger = LogManager.getLogger(EdgeConvertCreateDDL.class.getName());
   public static Logger timeLogger = LogManager.getLogger("timer." + EdgeConvertCreateDDL.class.getName());

   public EdgeConvertCreateDDL(EdgeTable[] tables, EdgeField[] fields) {
		timeLogger.info("Constructor called.");
      this.tables = tables;
      this.fields = fields;
      initialize();
		timeLogger.info("Constructor ended.");
   } //EdgeConvertCreateDDL(EdgeTable[], EdgeField[])
   
   public EdgeConvertCreateDDL() { //default constructor with empty arg list for to allow output dir to be set before there are table and field objects
      
   } //EdgeConvertCreateDDL()

   public void initialize() {
		timeLogger.info("method initialize called.");
      numBoundTables = new int[tables.length];
      maxBound = 0;
      sb = new StringBuffer();

      for (int i = 0; i < tables.length; i++) { //step through list of tables
         int numBound = 0; //initialize counter for number of bound tables
         int[] relatedFields = tables[i].getRelatedFieldsArray();
         for (int j = 0; j < relatedFields.length; j++) { //step through related fields list
            if (relatedFields[j] != 0) {
               numBound++; //count the number of non-zero related fields
            }
         }
         numBoundTables[i] = numBound;
         if (numBound > maxBound) {
            maxBound = numBound;
         }
      }
	   timeLogger.info("method initialize ended");
   }
   
   protected EdgeTable getTable(int numFigure) {
		timeLogger.info("method getTable called.");
      for (int tIndex = 0; tIndex < tables.length; tIndex++) {
         if (numFigure == tables[tIndex].getNumFigure()) {
            logger.debug("returning "+tIndex);
            timeLogger.info("method getTable ended."); 
            return tables[tIndex];
         }
      }
		timeLogger.info("method getTable ended.");
      return null;
   }
   
   protected EdgeField getField(int numFigure) {
		timeLogger.info("method getField called.");
      for (int fIndex = 0; fIndex < fields.length; fIndex++) {
         if (numFigure == fields[fIndex].getNumFigure()) {
            logger.debug("returning "+tIndex);
            timeLogger.info("method getField ended.");
            return fields[fIndex];
         }
      }
		timeLogger.info("method getField ended.");
      return null;
   }

   public abstract String getDatabaseName();

   public abstract String getProductName();
   
   public abstract String getSQLString();
   
   public abstract void createDDL();
   
}//EdgeConvertCreateDDL

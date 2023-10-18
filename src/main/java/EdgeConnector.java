// package src.main.java;
import java.util.StringTokenizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EdgeConnector {
   private int numConnector, endPoint1, endPoint2;
   private String endStyle1, endStyle2;
   private boolean isEP1Field, isEP2Field, isEP1Table, isEP2Table;
   private static Logger logger = LogManager.getLogger(EdgeConnector.class.getName());

   public EdgeConnector(String inputString) {
      StringTokenizer st = new StringTokenizer(inputString, EdgeConvertFileParser.DELIM);
      numConnector = Integer.parseInt(st.nextToken());
      endPoint1 = Integer.parseInt(st.nextToken());
      endPoint2 = Integer.parseInt(st.nextToken());
      endStyle1 = st.nextToken();
      endStyle2 = st.nextToken();
      isEP1Field = false;
      isEP2Field = false;
      isEP1Table = false;
      isEP2Table = false;
   }
   
   public int getNumConnector() {
      logger.debug("getting number connector {}");
      return numConnector;
   }
   
   public int getEndPoint1() {
      logger.debug("getting end point1 {}");
      return endPoint1;
   }
   
   public int getEndPoint2() {
      logger.debug("getting second end point {}");
      return endPoint2;
   }
   
   public String getEndStyle1() {
      logger.debug("getting end style 1 {}");
      return endStyle1;
   }
   
   public String getEndStyle2() {
      logger.debug("getting end style 2 {}");
      return endStyle2;
   }
   public boolean getIsEP1Field() {
      logger.debug("getting if end point 1 is a field {}");
      return isEP1Field;
   }
   
   public boolean getIsEP2Field() {
      logger.debug("getting if end point 2 is a field {}");
      return isEP2Field;
   }

   public boolean getIsEP1Table() {
      logger.debug("getting if end point 1 is a table {}");
      return isEP1Table;
   }

   public boolean getIsEP2Table() {
      logger.debug("getting if end point 2 is a table {}");
      return isEP2Table;
   }

   public void setIsEP1Field(boolean value) {
      logger.debug("setting if end point 1 is a field {}");
      isEP1Field = value;
   }
   
   public void setIsEP2Field(boolean value) {
      logger.debug("setting if end point 2 is a field {}");
      isEP2Field = value;
   }

   public void setIsEP1Table(boolean value) {
      logger.debug("setting if end point 1 is a table {}");
      isEP1Table = value;
   }

   public void setIsEP2Table(boolean value) {
      logger.debug("setting if end point 2 is a table {}");
      isEP2Table = value;
   }
}

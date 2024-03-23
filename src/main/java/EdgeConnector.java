import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EdgeConnector {
   public static Logger logger = Logger.getLogger(EdgeConnector.class.getName()); 
   private int numConnector, endPoint1, endPoint2;
   private String endStyle1, endStyle2;
   private boolean isEP1Field, isEP2Field, isEP1Table, isEP2Table;
      
   public EdgeConnector(String inputString) {
      try{
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
      catch(Exception e){
         logger.log(Level.WARNING, "An unexpected error occurred: " + e.getMessage(), e);//error logs
      }
   }
   
   public int getNumConnector() {
      logger.info("Getting Num Connector "+ numConnector);
      return numConnector;
   }
   
   public int getEndPoint1() {
      logger.info("Getting EndPoint 1 "+ endPoint1);
      return endPoint1;
   }
   
   public int getEndPoint2() {
      logger.info("Getting EndPoint 2 "+ endPoint2);
      return endPoint2;
   }
   
   public String getEndStyle1() {
      logger.info("Getting EndStyle 1 "+ endStyle1);
      return endStyle1;
   }
   
   public String getEndStyle2() {
      logger.info("Getting EndStyle 2 "+ endStyle2);
      return endStyle2;
   }
   public boolean getIsEP1Field() {
      logger.info("Is endPoint 1 a field "+ isEP1Field);
      return isEP1Field;
   }
   
   public boolean getIsEP2Field() {
      logger.info("Is endPoint 2 a field "+ isEP2Field);
      return isEP2Field;
   }

   public boolean getIsEP1Table() {
      logger.info("Is endPoint 1 a table "+ isEP1Table);
      return isEP1Table;
   }

   public boolean getIsEP2Table() {
      logger.info("Is endPoint 2 a table "+ isEP2Table);
      return isEP2Table;
   }

   public void setIsEP1Field(boolean value) {
      logger.info("Setting EndPoint 1 field to "+ value);
      isEP1Field = value;
   }
   
   public void setIsEP2Field(boolean value) {
      logger.info("Setting EndPoint 2 field to "+ value);
      isEP2Field = value;
   }

   public void setIsEP1Table(boolean value) {
      logger.info("Setting EndPoint 1 table to "+ value);
      isEP1Table = value;
   }

   public void setIsEP2Table(boolean value) {
      logger.info("Setting EndPoint 2 table to "+ value);
      isEP2Table = value;
   }
}

import java.util.StringTokenizer;
import org.apache.logging.log4j.*;

public class EdgeConnector {
   private int numConnector, endPoint1, endPoint2;
   private String endStyle1, endStyle2;
   private boolean isEP1Field, isEP2Field, isEP1Table, isEP2Table;

   private static final Logger logger = LogManager.getLogger("runner." + RunEdgeConvert.class.getName());
   private static final Logger timeLogger = LogManager.getLogger("timer." + RunEdgeConvert.class.getName());
      
   public EdgeConnector(String inputString) {
      timeLogger.info("Constructor called.");
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
      timeLogger.info("Constructor ended.");
   }
   
   public int getNumConnector() {
      logger.debug("return from getNumConnector() - {}", numConnector);
      return numConnector;
   }
   
   public int getEndPoint1() {
      logger.debug("return from getEndPoint1() - {}", endPoint1);
      return endPoint1;
   }
   
   public int getEndPoint2() {
      logger.debug("return from getEndPoint2() - {}", endPoint2);
      return endPoint2;
   }
   
   public String getEndStyle1() {
      logger.debug("return from getEndStyle1() - {}", endStyle1);
      return endStyle1;
   }
   
   public String getEndStyle2() {
      return endStyle2;
   }
   public boolean getIsEP1Field() {
      return isEP1Field;
   }
   
   public boolean getIsEP2Field() {
      return isEP2Field;
   }

   public boolean getIsEP1Table() {
      return isEP1Table;
   }

   public boolean getIsEP2Table() {
      return isEP2Table;
   }

   public void setIsEP1Field(boolean value) {
      isEP1Field = value;
   }
   
   public void setIsEP2Field(boolean value) {
      isEP2Field = value;
   }

   public void setIsEP1Table(boolean value) {
      isEP1Table = value;
   }

   public void setIsEP2Table(boolean value) {
      isEP2Table = value;
   }
}

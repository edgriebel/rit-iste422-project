import org.apache.logging.log4j.*;

public class RunEdgeConvert {

   private static final Logger logger = LogManager.getLogger("runner." + RunEdgeConvert.class.getName());
   private static final Logger timeLogger = LogManager.getLogger("timer." + RunEdgeConvert.class.getName());
   public static void main(String[] args) {
      timeLogger.info("Start Method: [main] Line: [7]");
      EdgeConvertGUI edge = new EdgeConvertGUI();

      logger.debug("An object of EdgeConvertGUI has been created");
      logger.info("GUI has been loaded");
      timeLogger.info("End Method: [main] Line: [15]");
   }
}
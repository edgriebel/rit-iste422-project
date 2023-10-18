import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RunEdgeConvert {
   private static Logger logger = LogManager.getLogger(RunEdgeConvert.class.getName());

   public static void main(String[] args) {
      logger.info("Starting application {}", RunEdgeConvert.class);
      EdgeConvertGUI edge = new EdgeConvertGUI();
   }
}
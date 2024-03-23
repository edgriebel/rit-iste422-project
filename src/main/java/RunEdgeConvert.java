import java.util.logging.Level;
import java.util.logging.Logger;

public class RunEdgeConvert {
   public static Logger logger = Logger.getLogger(RunEdgeConvert.class.getName());
   public static void main(String[] args) {
      try {
         EdgeConvertGUI edge = new EdgeConvertGUI();
      } catch (Exception e) {
         logger.log(Level.SEVERE, "An unexpected error occurred: " + e.getMessage(), e);
      }
   }
}
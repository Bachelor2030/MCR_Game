package network.utilities;

public abstract class Info {
  private static final String receptionistClassName = "Receptionist";
  private static final String servantClassName = "Servant";
  private static final String clientClassName = "Client";

  public enum MessageLevel {
    Info,
    Error
  }

  /**
   * Prints a message given a message level, a name of the class calling and a message
   */
  public static void printMessage(MessageLevel level, String className, String message) {
    System.out.println(level + ": " + className + ": " + message);
  }

  /**
   * Prints a message with level Info, a name of the class calling and a message
   */
  public static void printMessage(String className, String message) {
    printMessage(MessageLevel.Info, className, message);
  }

  /**
   * Prints a message with level info and className debug, and a message
   */
  public static void debugMessage(String message) {
    printMessage("Debug", message);
  }

  /**
   * Usual name for the receptionist class
   */
  public static String receptionistClassName() {
    return receptionistClassName;
  }

  /**
   * Usual name for the servant worker class
   */
  public static String servantClassName(int playerId) {
    return servantClassName + playerId;
  }

  /**
   * Usual name for the client class
   */
  public static String clientClassName() {
    return clientClassName;
  }
}

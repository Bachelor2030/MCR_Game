package Common.Network.Utilities;

public abstract class Info {
    private static final String receptionistClassName = "Receptionist";
    private static final String servantClassName = "Servant";
    private static final String clientClassName = "Client";

    public enum MessageLevel {
        Info, Error
    }

    public static void printMessage(MessageLevel level, String className, String message) {
        System.out.println(level + ": " + className + ": " + message);
    }

    public static void printMessage(String className, String message) {
        printMessage(MessageLevel.Info, className, message);
    }

    public static void debugMessage(String message) {
        printMessage("Debug", message);
    }

    public static String receptionistClassName() {
        return receptionistClassName;
    }

    public static String servantClassName(int playerId) {
        return servantClassName + playerId;
    }

    public static String clientClassName() {
        return clientClassName;
    }
}

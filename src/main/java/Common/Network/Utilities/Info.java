package Common.Network.Utilities;

public abstract class Info {
    public static final String receptionistName = "Receptionist";
    public static final String servantWorkerName = "Servant";

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

    public static String servantNameForPlayer(int playerId) {
        return servantWorkerName + playerId;
    }
}

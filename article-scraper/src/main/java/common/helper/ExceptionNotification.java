package common.helper;

import application.Application;

public class ExceptionNotification {

    public static void printException (Exception ex) {
        if (Application.ENABLE_LOGS) {
            FileManager.writeLogs(Application.LOG_FILE_LOCATION, ex.getMessage());
        }

        if (Application.ENABLE_DEBUG) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }
}

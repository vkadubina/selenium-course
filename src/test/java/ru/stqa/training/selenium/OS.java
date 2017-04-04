package ru.stqa.training.selenium;

/**
 * @author Victoria Kadubina
 */
public enum OS {
    MAC, WINDOWS, UNIX;

    private static String OS_NAME = System.getProperty("os.name").toLowerCase();

    public static OS detect() {
        if (OS_NAME.contains("win"))
            return WINDOWS;
        else if (OS_NAME.contains("mac"))
            return MAC;
        else if (OS_NAME.contains("nix") || OS_NAME.contains("nux") || OS_NAME.indexOf("aix") > 0 || OS_NAME.contains("sunos"))
            return UNIX;
        throw new IllegalStateException("Cannot detect OS");
    }
}

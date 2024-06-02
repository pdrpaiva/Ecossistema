package pt.isec.pa.javalife.ui;

import java.util.Scanner;

public final class PAInput {
    private PAInput() {}

    private static Scanner sc;

    static {
        resetScanner();
    }

    public static void resetScanner() {
        sc = new Scanner(System.in);
    }

}
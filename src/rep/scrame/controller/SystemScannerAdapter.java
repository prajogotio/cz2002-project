package rep.scrame.controller;

import java.util.Scanner;

/**
 * Created by prajogotio on 23/3/15.
 */
public class SystemScannerAdapter {
    private static final Scanner SCANNER_INSTANCE = new Scanner(System.in);
    private SystemScannerAdapter() {}
    public static Scanner getInstance() { return SCANNER_INSTANCE; }
}

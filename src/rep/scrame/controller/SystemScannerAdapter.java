package rep.scrame.controller;

import java.util.Scanner;

/**
 * The adapter that acts as the singleton for the system wide scanner.
 */
public class SystemScannerAdapter {
	/**
	 * The only instance of the scanner in the system. It reads from the standard input.
	 */
    private static final Scanner SCANNER_INSTANCE = new Scanner(System.in);
    
    /**
     * To make sure that the scanner is not instantiable, because we want a Scanner singleton.
     */
    private SystemScannerAdapter() {}
    
    /**
     * Gets the only instance of the standard input scanner.
     * @return	Scanner of System.in
     */
    public static Scanner getInstance() { return SCANNER_INSTANCE; }
}

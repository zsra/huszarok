package app;


import org.pmw.tinylog.Logger;

import java.time.LocalDate;

/**
 * Program indító.
 */
public class FXAppLauncher {

    /**
     * Main metódus a valódi amin lehíváshoz.
     *
     * @param args <code>args</code> CL argument.
     */
    public static void main(String[] args) {

        Logger.info("Project start at {}", LocalDate.now());
        FXApp.main(args);
    }
}

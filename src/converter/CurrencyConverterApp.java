package converter;

import java.awt.EventQueue;
import javax.swing.UIManager;

/**
 * Entry point for the Currency Converter application.
 * Sets the system look and feel and launches the main converter window.
 * 
 * @author Aniket Chaudhari
 */
public class CurrencyConverterApp {

    public static void main(String[] args) {
        // Apply native OS look and feel for better appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Could not set system look and feel: " + ex.getMessage());
        }

        // Launch the converter window on the Event Dispatch Thread
        EventQueue.invokeLater(() -> {
            try {
                ConverterWindow window = new ConverterWindow();
                window.setVisible(true);
            } catch (Exception ex) {
                System.err.println("Failed to launch application: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }
}

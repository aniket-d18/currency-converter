package converter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Main application window for the Currency Converter.
 * Provides UI for selecting source/target currencies, entering an amount,
 * and displaying the converted result.
 * 
 * @author Aniket Chaudhari
 */
public class ConverterWindow extends JFrame {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("localization.translation");

    private JPanel mainPanel;
    private JTextField amountField;
    private List<CurrencyData> supportedCurrencies;

    /**
     * Constructs and lays out the main converter window.
     */
    public ConverterWindow() {
        supportedCurrencies = CurrencyData.createCurrencies();
        initializeWindow();
        buildMenuBar();
        buildConverterUI();
    }

    /**
     * Sets up the window properties (title, size, close behavior).
     */
    private void initializeWindow() {
        setTitle(BUNDLE.getString("MainWindow.this.title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 589, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.setLayout(null);
        setContentPane(mainPanel);
    }

    /**
     * Creates the menu bar with File and Help menus.
     */
    private void buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // --- File Menu ---
        JMenu fileMenu = new JMenu(BUNDLE.getString("MainWindow.mnFile.text"));
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        JMenuItem quitItem = new JMenuItem(BUNDLE.getString("MainWindow.mntmQuit.text"));
        quitItem.setMnemonic(KeyEvent.VK_Q);
        quitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(quitItem);

        // --- Help Menu ---
        JMenu helpMenu = new JMenu(BUNDLE.getString("MainWindow.mnHelp.text"));
        helpMenu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(helpMenu);

        JMenuItem aboutItem = new JMenuItem(BUNDLE.getString("MainWindow.mntmAbout.text"));
        aboutItem.setMnemonic(KeyEvent.VK_A);
        aboutItem.addActionListener(e -> {
            EventQueue.invokeLater(() -> {
                try {
                    AboutDialog.getInstance().setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
        helpMenu.add(aboutItem);
    }

    /**
     * Builds the main converter UI: dropdowns, amount field, convert button, and result label.
     */
    private void buildConverterUI() {
        // "Convert" label
        JLabel convertLabel = new JLabel(BUNDLE.getString("MainWindow.lblConvert.text"));
        convertLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        convertLabel.setBounds(0, 14, 92, 15);
        mainPanel.add(convertLabel);

        // Source currency dropdown
        final JComboBox<String> sourceCurrencyBox = new JComboBox<>();
        sourceCurrencyBox.setBounds(147, 7, 240, 28);
        fillCurrencyDropdown(sourceCurrencyBox);
        mainPanel.add(sourceCurrencyBox);

        // "To" label
        JLabel toLabel = new JLabel(BUNDLE.getString("MainWindow.lblTo.text"));
        toLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        toLabel.setBounds(66, 54, 26, 15);
        mainPanel.add(toLabel);

        // Target currency dropdown
        final JComboBox<String> targetCurrencyBox = new JComboBox<>();
        targetCurrencyBox.setBounds(147, 47, 240, 28);
        fillCurrencyDropdown(targetCurrencyBox);
        mainPanel.add(targetCurrencyBox);

        // "Amount" label
        JLabel amountLabel = new JLabel(BUNDLE.getString("MainWindow.lblAmount.text"));
        amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        amountLabel.setBounds(23, 108, 69, 15);
        mainPanel.add(amountLabel);

        // Amount input field with character limit
        amountField = new JTextField("0.00");
        amountField.setBounds(147, 101, 103, 29);
        amountField.setColumns(10);
        amountField.setDocument(new InputLengthFilter(8));
        mainPanel.add(amountField);

        // Result display label
        final JLabel resultLabel = new JLabel("");
        resultLabel.setHorizontalAlignment(SwingConstants.LEFT);
        resultLabel.setBounds(147, 188, 428, 38);
        mainPanel.add(resultLabel);

        // Convert button
        JButton convertButton = new JButton(BUNDLE.getString("MainWindow.btnConvert.text"));
        convertButton.setBounds(147, 142, 129, 38);
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                performConversion(sourceCurrencyBox, targetCurrencyBox, resultLabel);
            }
        });
        mainPanel.add(convertButton);
    }

    /**
     * Handles the conversion logic when the Convert button is clicked.
     */
    private void performConversion(JComboBox<String> sourceBox, JComboBox<String> targetBox, JLabel resultLabel) {
        String sourceName = sourceBox.getSelectedItem().toString();
        String targetName = targetBox.getSelectedItem().toString();
        DecimalFormat formatter = new DecimalFormat("#0.00");

        // Parse the user-entered amount safely
        double amount;
        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException ex) {
            amount = 0.0;
        }

        // Perform conversion
        double convertedAmount = findAndConvert(sourceName, targetName, amount);

        // Display result
        String formattedSource = formatter.format(amount);
        String formattedResult = formatter.format(convertedAmount);
        resultLabel.setText(formattedSource + " " + sourceName + " = " + formattedResult + " " + targetName);
    }

    /**
     * Populates a JComboBox with the names of all supported currencies.
     */
    private void fillCurrencyDropdown(JComboBox<String> dropdown) {
        for (CurrencyData currency : supportedCurrencies) {
            dropdown.addItem(currency.getCurrencyName());
        }
    }

    /**
     * Looks up the exchange rate between two currencies and returns the converted amount.
     * Uses .equals() for proper String comparison.
     * 
     * @param sourceName  Full name of the source currency
     * @param targetName  Full name of the target currency
     * @param amount      The amount to convert
     * @return            The converted amount
     */
    private double findAndConvert(String sourceName, String targetName, double amount) {
        // First, find the currency code for the target currency
        String targetCode = null;
        for (CurrencyData currency : supportedCurrencies) {
            if (currency.getCurrencyName().equals(targetName)) {
                targetCode = currency.getCurrencyCode();
                break;
            }
        }

        // Then find the source currency's exchange rate for the target code
        if (targetCode != null) {
            for (CurrencyData currency : supportedCurrencies) {
                if (currency.getCurrencyName().equals(sourceName)) {
                    Double rate = currency.getExchangeRates().get(targetCode);
                    if (rate != null) {
                        return CurrencyData.convertAmount(amount, rate);
                    }
                }
            }
        }

        return 0.0;
    }
}

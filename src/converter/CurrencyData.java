package converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a single currency with its name, code, and exchange rates
 * relative to other supported currencies.
 * 
 * @author Aniket Chaudhari
 */
public class CurrencyData {

    private String currencyName;
    private String currencyCode;
    private Map<String, Double> exchangeRates;

    /**
     * Creates a new CurrencyData instance.
     * @param currencyName  Full name of the currency (e.g., "US Dollar")
     * @param currencyCode  ISO 4217 currency code (e.g., "USD")
     */
    public CurrencyData(String currencyName, String currencyCode) {
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
        this.exchangeRates = new HashMap<>();
    }

    // --- Getters and Setters ---

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Map<String, Double> getExchangeRates() {
        return exchangeRates;
    }

    public void addExchangeRate(String targetCode, Double rate) {
        this.exchangeRates.put(targetCode, rate);
    }

    /**
     * Loads hardcoded exchange rates for this currency based on its name.
     * Rates are approximate and used for demonstration purposes.
     */
    public void loadExchangeRates() {
        switch (this.currencyName) {
            case "US Dollar":
                exchangeRates.put("USD", 1.00);
                exchangeRates.put("EUR", 0.93);
                exchangeRates.put("GBP", 0.66);
                exchangeRates.put("CHF", 1.01);
                exchangeRates.put("CNY", 6.36);
                exchangeRates.put("JPY", 123.54);
                exchangeRates.put("INR", 83.12);
                break;

            case "Euro":
                exchangeRates.put("USD", 1.073);
                exchangeRates.put("EUR", 1.00);
                exchangeRates.put("GBP", 0.71);
                exchangeRates.put("CHF", 1.08);
                exchangeRates.put("CNY", 6.83);
                exchangeRates.put("JPY", 132.57);
                exchangeRates.put("INR", 89.20);
                break;

            case "British Pound":
                exchangeRates.put("USD", 1.51);
                exchangeRates.put("EUR", 1.41);
                exchangeRates.put("GBP", 1.00);
                exchangeRates.put("CHF", 1.52);
                exchangeRates.put("CNY", 9.60);
                exchangeRates.put("JPY", 186.41);
                exchangeRates.put("INR", 125.60);
                break;

            case "Swiss Franc":
                exchangeRates.put("USD", 0.99);
                exchangeRates.put("EUR", 0.93);
                exchangeRates.put("GBP", 0.66);
                exchangeRates.put("CHF", 1.00);
                exchangeRates.put("CNY", 6.33);
                exchangeRates.put("JPY", 122.84);
                exchangeRates.put("INR", 82.50);
                break;

            case "Chinese Yuan":
                exchangeRates.put("USD", 0.16);
                exchangeRates.put("EUR", 0.15);
                exchangeRates.put("GBP", 0.11);
                exchangeRates.put("CHF", 0.16);
                exchangeRates.put("CNY", 1.00);
                exchangeRates.put("JPY", 19.41);
                exchangeRates.put("INR", 13.07);
                break;

            case "Japanese Yen":
                exchangeRates.put("USD", 0.008);
                exchangeRates.put("EUR", 0.007);
                exchangeRates.put("GBP", 0.005);
                exchangeRates.put("CHF", 0.008);
                exchangeRates.put("CNY", 0.051);
                exchangeRates.put("JPY", 1.00);
                exchangeRates.put("INR", 0.67);
                break;

            case "Indian Rupee":
                exchangeRates.put("USD", 0.012);
                exchangeRates.put("EUR", 0.011);
                exchangeRates.put("GBP", 0.008);
                exchangeRates.put("CHF", 0.012);
                exchangeRates.put("CNY", 0.077);
                exchangeRates.put("JPY", 1.49);
                exchangeRates.put("INR", 1.00);
                break;
        }
    }

    /**
     * Initializes and returns the list of all supported currencies
     * with their exchange rates pre-loaded.
     */
    public static List<CurrencyData> createCurrencies() {
        List<CurrencyData> supportedCurrencies = new ArrayList<>();

        supportedCurrencies.add(new CurrencyData("US Dollar", "USD"));
        supportedCurrencies.add(new CurrencyData("Euro", "EUR"));
        supportedCurrencies.add(new CurrencyData("British Pound", "GBP"));
        supportedCurrencies.add(new CurrencyData("Swiss Franc", "CHF"));
        supportedCurrencies.add(new CurrencyData("Chinese Yuan", "CNY"));
        supportedCurrencies.add(new CurrencyData("Japanese Yen", "JPY"));
        supportedCurrencies.add(new CurrencyData("Indian Rupee", "INR"));

        // Load exchange rates for each currency
        for (CurrencyData currency : supportedCurrencies) {
            currency.loadExchangeRates();
        }

        return supportedCurrencies;
    }

    /**
     * Converts an amount using the given exchange rate.
     * Result is rounded to 2 decimal places.
     * 
     * @param amount        The amount to convert
     * @param exchangeRate  The rate to multiply by
     * @return              The converted amount, rounded to 2 decimals
     */
    public static double convertAmount(double amount, double exchangeRate) {
        double result = amount * exchangeRate;
        // Round to 2 decimal places
        return Math.round(result * 100.0) / 100.0;
    }
}

package converter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Fetches live currency exchange rates from the Open Exchange Rate API.
 * Uses https://open.er-api.com which is free and requires no API key.
 * Falls back gracefully if the network is unavailable.
 * 
 * @author Aniket Chaudhari
 */
public class ExchangeRateService {

    private static final String API_BASE_URL = "https://open.er-api.com/v6/latest/";
    private static final int TIMEOUT_MS = 5000; // 5 second timeout

    /**
     * Fetches live exchange rates for the given base currency.
     * 
     * @param baseCurrencyCode  ISO 4217 code (e.g., "USD", "INR")
     * @return Map of currency code to exchange rate, or null if fetch fails
     */
    public static Map<String, Double> fetchRates(String baseCurrencyCode) {
        try {
            String apiUrl = API_BASE_URL + baseCurrencyCode;
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(TIMEOUT_MS);
            connection.setReadTimeout(TIMEOUT_MS);

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.err.println("API returned status: " + responseCode);
                return null;
            }

            // Read the JSON response
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
            );
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();

            // Parse the rates from JSON response
            return parseRatesFromJson(response.toString());

        } catch (Exception ex) {
            System.err.println("Failed to fetch live rates for " + baseCurrencyCode + ": " + ex.getMessage());
            return null;
        }
    }

    /**
     * Simple JSON parser to extract the "rates" object from the API response.
     * Avoids external JSON library dependency.
     * 
     * Expected format: { ... "rates": { "USD": 1.0, "EUR": 0.93, ... } ... }
     */
    private static Map<String, Double> parseRatesFromJson(String json) {
        Map<String, Double> rates = new HashMap<>();

        try {
            // Find the "rates" object in the JSON
            int ratesStart = json.indexOf("\"rates\"");
            if (ratesStart == -1) return null;

            // Find the opening brace of the rates object
            int braceStart = json.indexOf("{", ratesStart);
            if (braceStart == -1) return null;

            // Find the matching closing brace
            int braceEnd = findMatchingBrace(json, braceStart);
            if (braceEnd == -1) return null;

            // Extract the rates content between braces
            String ratesContent = json.substring(braceStart + 1, braceEnd);

            // Parse each key-value pair like "USD": 1.0
            String[] pairs = ratesContent.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    String code = keyValue[0].trim().replace("\"", "");
                    try {
                        double rate = Double.parseDouble(keyValue[1].trim());
                        rates.put(code, rate);
                    } catch (NumberFormatException ignored) {
                        // Skip non-numeric values
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Error parsing JSON: " + ex.getMessage());
            return null;
        }

        return rates.isEmpty() ? null : rates;
    }

    /**
     * Finds the matching closing brace for an opening brace in a string.
     */
    private static int findMatchingBrace(String text, int openPos) {
        int depth = 0;
        for (int i = openPos; i < text.length(); i++) {
            if (text.charAt(i) == '{') depth++;
            else if (text.charAt(i) == '}') {
                depth--;
                if (depth == 0) return i;
            }
        }
        return -1;
    }
}

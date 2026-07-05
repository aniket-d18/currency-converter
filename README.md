# Currency Converter — Java Swing Application

A desktop currency converter application built using **Java Swing**. It provides a clean graphical interface to convert amounts between multiple international currencies using live, real-time exchange rates.

## Features

- **Live Exchange Rates**: Fetches real-time exchange rates dynamically from a free API (`https://open.er-api.com`).
- **Offline Fallback**: Gracefully falls back to pre-defined offline exchange rates if no internet connection is available.
- **7 Supported Currencies**: USD, EUR, GBP, CHF, CNY, JPY, and INR (Indian Rupee).
- **GUI Design**: Native Look and Feel design with anti-aliased elements.
- **Input Validation**: Limits inputs to valid numbers and handles characters/lengths safely.
- **Localization**: Full localization support for English and French translations.

## Supported Currencies

| Currency             | Code |
|----------------------|------|
| US Dollar            | USD  |
| Euro                 | EUR  |
| British Pound        | GBP  |
| Swiss Franc          | CHF  |
| Chinese Yuan         | CNY  |
| Japanese Yen         | JPY  |
| Indian Rupee         | INR  |

## How to Run

### Prerequisites
- Java JDK 8 or higher

### Compile and Run

```bash
# Navigate to the source directory
cd src

# Compile all Java files inside the converter package
javac converter/*.java

# Run the application
java converter.CurrencyConverterApp
```

## Project Structure

```
src/
├── converter/
│   ├── CurrencyConverterApp.java    # Application entry point
│   ├── CurrencyData.java            # Currency model (stores codes, names, and rates)
│   ├── ExchangeRateService.java     # Live API fetching service
│   ├── ConverterWindow.java         # Main UI Window and layout logic
│   ├── AboutDialog.java             # About dialog (Singleton window)
│   └── InputLengthFilter.java       # Text input filter
└── localization/
    ├── translation.properties       # Default (English) strings
    ├── translation_en.properties    # English UI translations
    └── translation_fr.properties    # French UI translations
```

## Tech Stack

- **Language**: Java (JDK 8+)
- **UI Toolkit**: Java Swing & AWT
- **Network**: Native `HttpURLConnection` for fetching API data
- **API**: Open Exchange Rates API (free, no API key required)

## Author

**Aniket Chaudhari**
- GitHub: [github.com/aniket-d18](https://github.com/aniket-d18)

## License

This project is open-source and licensed under the [MIT License](LICENSE).

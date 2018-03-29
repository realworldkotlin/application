
package org.joda.money;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;
import org.joda.convert.FromString;
import org.joda.convert.ToString;

public final class CurrencyUnit implements Comparable<CurrencyUnit>, Serializable {

    private static final long serialVersionUID = 327835287287L;
    private static final Pattern CODE = Pattern.compile("[A-Z][A-Z][A-Z]");
    private static final ConcurrentMap<String, CurrencyUnit> currenciesByCode = new ConcurrentHashMap<String, CurrencyUnit>();
    private static final ConcurrentMap<Integer, CurrencyUnit> currenciesByNumericCode = new ConcurrentHashMap<Integer, CurrencyUnit>();


    private static final ConcurrentMap<String, CurrencyUnit> currenciesByCountry = new ConcurrentHashMap<String, CurrencyUnit>();

    static {
        // load one data provider by system property
        try {
            try {
                String clsName = System.getProperty(
                    "org.joda.money.CurrencyUnitDataProvider", "org.joda.money.DefaultCurrencyUnitDataProvider");
                Class<? extends CurrencyUnitDataProvider> cls =
                    CurrencyUnit.class.getClassLoader().loadClass(clsName).asSubclass(CurrencyUnitDataProvider.class);
                cls.getDeclaredConstructor().newInstance().registerCurrencies();
            } catch (SecurityException ex) {
                new DefaultCurrencyUnitDataProvider().registerCurrencies();
            }
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString(), ex);
        }
    }

    // a selection of commonly traded, stable currencies


    public static final CurrencyUnit USD = of("USD");


    public static final CurrencyUnit EUR = of("EUR");


    public static final CurrencyUnit JPY = of("JPY");


    public static final CurrencyUnit GBP = of("GBP");


    public static final CurrencyUnit CHF = of("CHF");


    public static final CurrencyUnit AUD = of("AUD");


    public static final CurrencyUnit CAD = of("CAD");


    private final String code;


    private final short numericCode;


    private final short decimalPlaces;

    //-----------------------------------------------------------------------


    public static synchronized CurrencyUnit registerCurrency(
        String currencyCode, int numericCurrencyCode, int decimalPlaces, List<String> countryCodes) {
        return registerCurrency(currencyCode, numericCurrencyCode, decimalPlaces, countryCodes, false);
    }


    public static synchronized CurrencyUnit registerCurrency(
        String currencyCode, int numericCurrencyCode, int decimalPlaces, List<String> countryCodes, boolean force) {
        MoneyUtils.checkNotNull(currencyCode, "Currency code must not be null");
        if (currencyCode.length() != 3) {
            throw new IllegalArgumentException("Invalid string code, must be length 3");
        }
        if (CODE.matcher(currencyCode).matches() == false) {
            throw new IllegalArgumentException("Invalid string code, must be ASCII upper-case letters");
        }
        if (numericCurrencyCode < -1 || numericCurrencyCode > 999) {
            throw new IllegalArgumentException("Invalid numeric code");
        }
        if (decimalPlaces < -1 || decimalPlaces > 30) {
            throw new IllegalArgumentException("Invalid number of decimal places");
        }
        MoneyUtils.checkNotNull(countryCodes, "Country codes must not be null");

        CurrencyUnit currency = new CurrencyUnit(currencyCode, (short) numericCurrencyCode, (short) decimalPlaces);
        if (force) {
            currenciesByCode.remove(currencyCode);
            currenciesByNumericCode.remove(numericCurrencyCode);
            for (String countryCode : countryCodes) {
                currenciesByCountry.remove(countryCode);
            }
        } else {
            if (currenciesByCode.containsKey(currencyCode) || currenciesByNumericCode.containsKey(numericCurrencyCode)) {
                throw new IllegalArgumentException("Currency already registered: " + currencyCode);
            }
            for (String countryCode : countryCodes) {
                if (currenciesByCountry.containsKey(countryCode)) {
                    throw new IllegalArgumentException("Currency already registered for country: " + countryCode);
                }
            }
        }
        currenciesByCode.putIfAbsent(currencyCode, currency);
        if (numericCurrencyCode >= 0) {
            currenciesByNumericCode.putIfAbsent(numericCurrencyCode, currency);
        }
        for (String countryCode : countryCodes) {
            currenciesByCountry.put(countryCode, currency);
        }
        return currenciesByCode.get(currencyCode);
    }


    public static List<CurrencyUnit> registeredCurrencies() {
        ArrayList<CurrencyUnit> list = new ArrayList<CurrencyUnit>(currenciesByCode.values());
        Collections.sort(list);
        return list;
    }

    //-----------------------------------------------------------------------


    public static CurrencyUnit of(Currency currency) {
        MoneyUtils.checkNotNull(currency, "Currency must not be null");
        return of(currency.getCurrencyCode());
    }


    @FromString
    public static CurrencyUnit of(String currencyCode) {
        MoneyUtils.checkNotNull(currencyCode, "Currency code must not be null");
        CurrencyUnit currency = currenciesByCode.get(currencyCode);
        if (currency == null) {
            throw new IllegalCurrencyException("Unknown currency '" + currencyCode + '\'');
        }
        return currency;
    }


    public static CurrencyUnit ofNumericCode(String numericCurrencyCode) {
        MoneyUtils.checkNotNull(numericCurrencyCode, "Currency code must not be null");
        switch (numericCurrencyCode.length()) {
            case 1:
                return ofNumericCode(numericCurrencyCode.charAt(0) - '0');
            case 2:
                return ofNumericCode((numericCurrencyCode.charAt(0) - '0') * 10 +
                    numericCurrencyCode.charAt(1) - '0');
            case 3:
                return ofNumericCode((numericCurrencyCode.charAt(0) - '0') * 100 +
                    (numericCurrencyCode.charAt(1) - '0') * 10 +
                    numericCurrencyCode.charAt(2) - '0');
            default:
                throw new IllegalCurrencyException("Unknown currency '" + numericCurrencyCode + '\'');
        }
    }


    public static CurrencyUnit ofNumericCode(int numericCurrencyCode) {
        CurrencyUnit currency = currenciesByNumericCode.get(numericCurrencyCode);
        if (currency == null) {
            throw new IllegalCurrencyException("Unknown currency '" + numericCurrencyCode + '\'');
        }
        return currency;
    }


    public static CurrencyUnit of(Locale locale) {
        MoneyUtils.checkNotNull(locale, "Locale must not be null");
        CurrencyUnit currency = currenciesByCountry.get(locale.getCountry());
        if (currency == null) {
            throw new IllegalCurrencyException("Unknown currency for locale '" + locale + '\'');
        }
        return currency;
    }


    public static CurrencyUnit ofCountry(String countryCode) {
        MoneyUtils.checkNotNull(countryCode, "Country code must not be null");
        CurrencyUnit currency = currenciesByCountry.get(countryCode);
        if (currency == null) {
            throw new IllegalCurrencyException("Unknown currency for country '" + countryCode + '\'');
        }
        return currency;
    }

    //-----------------------------------------------------------------------


    public static CurrencyUnit getInstance(String currencyCode) {
        return CurrencyUnit.of(currencyCode);
    }


    public static CurrencyUnit getInstance(Locale locale) {
        return CurrencyUnit.of(locale);
    }

    //-----------------------------------------------------------------------


    CurrencyUnit(String code, short numericCode, short decimalPlaces) {
        assert code != null : "Joda-Money bug: Currency code must not be null";
        this.code = code;
        this.numericCode = numericCode;
        this.decimalPlaces = decimalPlaces;
    }


    private void readObject(ObjectInputStream ois) throws InvalidObjectException {
        throw new InvalidObjectException("Serialization delegate required");
    }


    private Object writeReplace() {
        return new Ser(Ser.CURRENCY_UNIT, this);
    }

    //-----------------------------------------------------------------------


    public String getCode() {
        return code;
    }


    public int getNumericCode() {
        return numericCode;
    }


    public String getNumeric3Code() {
        if (numericCode < 0) {
            return "";
        }
        String str = Integer.toString(numericCode);
        if (str.length() == 1) {
            return "00" + str;
        }
        if (str.length() == 2) {
            return "0" + str;
        }
        return str;
    }


    public Set<String> getCountryCodes() {
        Set<String> countryCodes = new HashSet<String>();
        for (Entry<String, CurrencyUnit> entry : currenciesByCountry.entrySet()) {
            if (this.equals(entry.getValue())) {
                countryCodes.add(entry.getKey());
            }
        }
        return countryCodes;
    }

    //-----------------------------------------------------------------------


    public int getDecimalPlaces() {
        return decimalPlaces < 0 ? 0 : decimalPlaces;
    }


    public boolean isPseudoCurrency() {
        return decimalPlaces < 0;
    }

    //-----------------------------------------------------------------------


    public String getCurrencyCode() {
        return code;
    }


    public int getDefaultFractionDigits() {
        return decimalPlaces;
    }

    //-----------------------------------------------------------------------


    public String getSymbol() {
        try {
            return Currency.getInstance(code).getSymbol();
        } catch (IllegalArgumentException ex) {
            return code;
        }
    }


    public String getSymbol(Locale locale) {
        MoneyUtils.checkNotNull(locale, "Locale must not be null");
        try {
            return Currency.getInstance(code).getSymbol(locale);
        } catch (IllegalArgumentException ex) {
            return code;
        }
    }

    //-----------------------------------------------------------------------


    public Currency toCurrency() {
        return Currency.getInstance(code);
    }

    //-----------------------------------------------------------------------


    @Override
    public int compareTo(CurrencyUnit other) {
        return code.compareTo(other.code);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CurrencyUnit) {
            return code.equals(((CurrencyUnit) obj).code);
        }
        return false;
    }


    @Override
    public int hashCode() {
        return code.hashCode();
    }

    //-----------------------------------------------------------------------


    @Override
    @ToString
    public String toString() {
        return code;
    }

}

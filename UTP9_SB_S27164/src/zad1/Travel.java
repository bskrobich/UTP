package zad1;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Travel {
    private final Locale locale;
    private final String country;
    private final Date dateFrom;
    private final Date dateTo;
    private final String place;
    private final double price;
    private final Currency currency;

    public Travel(String localeToString, String country, String dateFrom, String dateTo, String place, String price, String currency) {

        Locale locale = Locale.forLanguageTag(localeToString.replace("_", "-"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        NumberFormat nf = NumberFormat.getInstance(locale);
        try {
            this.locale = locale;
            this.country = CountryInterpreter.interpret(country, locale, Locale.ENGLISH);
            this.dateFrom = sdf.parse(dateFrom);
            this.dateTo = sdf.parse(dateTo);
            this.place = this.getPlace(place, locale);
            this.price = nf.parse(price).doubleValue();
            this.currency = Currency.getInstance(currency);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Travel(Locale locale, String country, Date dateFrom, Date dateTo, String place, double price, Currency currency) {
        this.locale = locale;
        this.country = country;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.place = place;
        this.price = price;
        this.currency = currency;
    }

    public String getPlace(String place, Locale locale) {
        ResourceBundle rb = ResourceBundle.getBundle("properties.dictionary", locale);
        String[] places = { "sea", "lake", "mountains" };

        for (String p : places) {
            if (place.equals(rb.getString(p))) {
                return p;
            }
        }
        return "";
    }

    public String formatString(Locale locale) {
        ResourceBundle rb = ResourceBundle.getBundle("properties.dictionary", locale);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        NumberFormat nf = NumberFormat.getInstance(locale);

        String country = CountryInterpreter.interpret(this.country, Locale.ENGLISH, locale);
        String dateFrom = sdf.format(this.dateFrom);
        String dateTo = sdf.format(this.dateTo);
        String place = rb.getString(this.place);
        String price = nf.format(this.price);

        return country + " " + dateFrom + " " + dateTo + " "  + place + " " + price + " " + this.currency;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getCountry(Locale locale) {
        return CountryInterpreter.interpret(this.country, Locale.ENGLISH, locale);
    }

    public String getCountry() {
        return country;
    }

    public String getDate(Locale locale) {
        DateFormat dFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        return dFormat.format(this.dateFrom);
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public String getPlace(Locale locale) {
        ResourceBundle rb = ResourceBundle.getBundle("properties.dictionary", locale);
        return rb.getString(this.place);
    }

    public String getPlace() {
        return place;
    }

    public String getPrice(Locale locale) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        nf.setCurrency(this.currency);
        return nf.format(this.price);
    }

    public double getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }
}

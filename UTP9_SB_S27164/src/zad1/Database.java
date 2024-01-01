package zad1;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class Database {
    private final TravelData travelData;
    private final Connection connection;
    public Database(String url, TravelData travelData) {
        this.travelData = travelData;
        try {
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void create() {
        String createTable = "CREATE TABLE travel (\n" +
                "    id BIGSERIAL PRIMARY KEY,\n" +
                "    locale VARCHAR(8) NOT NULL,\n" +
                "    country VARCHAR(64) NOT NULL,\n" +
                "    dateFrom DATE NOT NULL,\n" +
                "    dateTo DATE NOT NULL,\n" +
                "    place VARCHAR(16) NOT NULL,\n" +
                "    price DOUBLE PRECISION NOT NULL,\n" +
                "    currency varchar(4) NOT NULL\n" +
                ");";

        String dropTable = "DROP TABLE IF EXISTS travel;";

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeUpdate(dropTable);
            stmt.executeUpdate(createTable);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.insertData();
    }

    public void insertData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Statement stmt = this.connection.createStatement();
            for (Travel travel : this.travelData.getList()) {
                String locale = travel.getLocale().toLanguageTag();
                String country = travel.getCountry();
                String dateFrom = sdf.format(travel.getDateFrom());
                String dateTo = sdf.format(travel.getDateTo());
                String place = travel.getPlace();
                double price = travel.getPrice();
                Currency currency = travel.getCurrency();

                String insert = "INSERT INTO travel \n" +
                        "    (locale, country, dateFrom, dateTo, place, price, currency)\n" +
                        "    VALUES(\n" +
                        "        '" + locale + "',\n" +
                        "        '" + country + "',\n" +
                        "        '" + dateFrom +"',\n" +
                        "        '" + dateTo + "',\n" +
                        "        '" + place + "',\n" +
                        "        " + price + ",\n" +
                        "        '" + currency + "'\n" +
                        "    );";

                stmt.executeUpdate(insert);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Travel[] retrieveData() {
        Statement stmt;
        try {
            stmt = this.connection.createStatement();
            String query = "SELECT locale, country, dateFrom, dateTo, place, price, currency FROM travel;";
            ResultSet resultSet = stmt.executeQuery(query);
            ArrayList<Travel> list = new ArrayList<>();

            while (resultSet.next()) {
                Locale locale = Locale.forLanguageTag(resultSet.getString("locale"));
                String country = resultSet.getString("country");
                Date dateFrom = resultSet.getDate("dateFrom");
                Date dateTo = resultSet.getDate("dateTo");
                String place = resultSet.getString("place");
                double price = resultSet.getDouble("price");
                Currency currency = Currency.getInstance(resultSet.getString("currency"));

                list.add(new Travel(locale, country, dateFrom, dateTo, place, price, currency));
            }
            stmt.close();

            Travel[] resultArr = new Travel[list.size()];
            list.toArray(resultArr);
            return resultArr;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showGui() {
        new GUI(this.retrieveData());
    }
}

package zad1;

import javax.swing.table.AbstractTableModel;
import java.util.Locale;
import java.util.ResourceBundle;

public class TableModel extends AbstractTableModel {
    private final Travel[] travels;
    private final Locale locale;
    private final String[] columns;
    private final ResourceBundle rb;
    public TableModel(Travel[] travels, Locale locale) {
        this.travels = travels;
        this.locale = locale;
        this.columns = new String[]{"country", "dateFrom", "dateTo", "place", "price"};
        this.rb = ResourceBundle.getBundle("dictionary", locale);
    }

    @Override
    public int getRowCount() {
        return this.travels.length;
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Travel row = this.travels[rowIndex];
        String cName = this.columns[columnIndex];

        switch (cName) {
            case "country" : return row.getCountry(this.locale);
            case "dateFrom" :
            case "dateTo" : return row.getDate(this.locale);
            case "place" : return row.getPlace(this.locale);
            case "price" : return row.getPrice(this.locale);
            default: return "";
        }
    }

    public String getColumnName(int column) {
        return this.rb.getString(this.columns[column]);
    }
}

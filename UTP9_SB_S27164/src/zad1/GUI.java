package zad1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class GUI extends JFrame implements ActionListener {
    private Locale locale = Locale.getDefault();
    private final Travel[] travels;
    private final JTable table;
    private final JLabel label;


    public GUI(Travel[] travels) {
        this.travels = travels;
        this.table = new JTable();
        this.label = new JLabel();

        JScrollPane scrollPane = new JScrollPane(table);

        JComboBox<String> languages = new JComboBox<>(getItems());
        languages.setSelectedIndex(getSelectedItem());
        languages.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.WEST);
        panel.add(languages, BorderLayout.CENTER);

        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        update(locale);
        create();
    }

    private void create() {
        setTitle("Travel Agency");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void update(Locale locale) {
        TableModel tableModel = new TableModel(travels, locale);
        ResourceBundle rb = ResourceBundle.getBundle("dictionary", locale);
        table.setModel(tableModel);
        label.setText(rb.getString("language"));
    }

    private HashMap<String, Locale> getSupportedLocales() {
        HashMap<String, Locale> supportedLocales = new HashMap<>();
        for (Locale supportedLocale : SupportedLocales.supportedLocales) {
            String language = supportedLocale.getDisplayLanguage(supportedLocale);
            supportedLocales.put(language, supportedLocale);
        }
        return supportedLocales;
    }

    private String[] getItems() {
        String[] supportedLocales = new String[SupportedLocales.supportedLocales.length];
        for (int i = 0; i < SupportedLocales.supportedLocales.length; i++) {
            Locale supportedLocale = SupportedLocales.supportedLocales[i];
            supportedLocales[i] = supportedLocale.getDisplayLanguage(supportedLocale);
        }
        return supportedLocales;
    }

    private int getSelectedItem() {
        String selectedLanguage = locale.getDisplayLanguage(locale);
        for (int i = 0; i < SupportedLocales.supportedLocales.length; i++) {
            String selectedItem = SupportedLocales.supportedLocales[i].getDisplayLanguage();
            if (selectedItem.equals(selectedLanguage))
                return i;
        }
        return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<String> comboBox = (JComboBox<String>)e.getSource();
        String selectedItem = (String)comboBox.getSelectedItem();
        locale = getSupportedLocales().get(selectedItem);
        update(locale);
    }
}


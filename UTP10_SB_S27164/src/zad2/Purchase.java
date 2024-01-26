/**
 *
 *  @author Skrobich Bartosz S27164
 *
 */

package zad2;

import java.beans.*;
import java.io.Serializable;

public class Purchase implements Serializable {
    private String prod;
    private String data;
    private double price;
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private final VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);

    public Purchase(String prod, String data, double price) {
        this.prod = prod;
        this.data = data;
        this.price = price;

    }
    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public String getData() {
        return data;
    }

    public synchronized void setData(String newData) {
        propertyChangeSupport.firePropertyChange("data", this.data, newData);
        this.data = newData;
    }

    public double getPrice() {
        return price;
    }

    public synchronized void setPrice(double newPrice) throws PropertyVetoException {
        vetoableChangeSupport.fireVetoableChange("price", this.price, newPrice);
        propertyChangeSupport.firePropertyChange("price", this.price, newPrice);
        this.price = newPrice;
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener changeListener) {
        propertyChangeSupport.addPropertyChangeListener(changeListener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener changeListener) {
        propertyChangeSupport.removePropertyChangeListener(changeListener);
    }

    public synchronized void addVetoableChangeListener(VetoableChangeListener changeListener) {
        vetoableChangeSupport.addVetoableChangeListener(changeListener);
    }

    public synchronized void removePropertyChangeListener(VetoableChangeListener changeListener) {
        vetoableChangeSupport.removeVetoableChangeListener(changeListener);
    }

    @Override
    public String toString() {
        return "Purchase [prod=" + prod + ", data=" + data + ", price=" + price + "]";
    }
}

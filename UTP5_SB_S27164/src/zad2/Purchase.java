/**
 *
 *  @author Skrobich Bartosz S27164
 *
 */

package zad2;


public class Purchase {
    private String customerId;
    private String customerName;
    private String productName;
    private double quantity;
    private double price;

    public Purchase(String customerId, String customerName, String productName, double quantity, double price) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String toString(boolean withPrice) {
        String result = this.customerId + ';' + this.customerName + ';' + this.productName + ';' + this.quantity + ';' + this.price;

        if (!withPrice)
            return result;

        return result + " (koszt: " + this.quantity * this.price + ")";
    }

    public String toString() {
        return this.toString(false);
    }
}

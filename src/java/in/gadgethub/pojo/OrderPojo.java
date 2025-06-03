
package in.gadgethub.pojo;

/**
 *
 * @author amans
 */
public class OrderPojo {
    private String orderId;
    private String prodId;
    private int Quantity;
    private double amount;
    private int shipped;

    public OrderPojo() {
    }

    public OrderPojo(String orderId, String prodId, int Quantity, double amount, int shipped) {
        this.orderId = orderId;
        this.prodId = prodId;
        this.Quantity = Quantity;
        this.amount = amount;
        this.shipped = shipped;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getShipped() {
        return shipped;
    }

    public void setShipped(int shipped) {
        this.shipped = shipped;
    }

    @Override
    public String toString() {
        return "OrderPojo{" + "orderId=" + orderId + ", prodId=" + prodId + ", Quantity=" + Quantity + ", amount=" + amount + ", shipped=" + shipped + '}';
    }
    
    
}

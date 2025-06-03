package in.gadgethub.dao;

import in.gadgethub.pojo.OrderDetailsPojo;
import in.gadgethub.pojo.OrderPojo;
import in.gadgethub.pojo.TransactionPojo;
import java.util.List;

public interface OrderDAO {
    
    public boolean addOrder(OrderPojo order);
    
    public boolean addTransaction(TransactionPojo transaction);
    
    public List<OrderPojo> getAllOrders();

    public List<OrderDetailsPojo> getAllOrderDetails (String userEmailId);

    public String shipNow(String orderId, String prodId);

    public String paymentSuccess (String username, double paidAmount);

    public int getSoldQuantity(String prodId);
 
}

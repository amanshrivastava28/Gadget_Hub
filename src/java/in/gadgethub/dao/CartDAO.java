package in.gadgethub.dao;

import in.gadgethub.pojo.CartPojo;
import java.util.List;

public interface CartDAO {
     public String updateProductInCart(CartPojo cart);
     
     public String addProductToCart(CartPojo cart);
     
      public List<CartPojo> getAllCartItems(String userId);
      
      public String removeProductFromCart(String userId, String itemId);
      
      public boolean removeAProduct(String userId, String itemId);
      
      public int getCartItemCount(String userId, String itemId);
}

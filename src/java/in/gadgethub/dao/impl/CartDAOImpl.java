
package in.gadgethub.dao.impl;

import in.gadgethub.dao.CartDAO;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.DemandPojo;
import in.gadgethub.utility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author amans
 */
public class CartDAOImpl implements CartDAO{
    
    public String updateProductInCart(CartPojo cart){
        String status="Failed to Add to Cart";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        ResultSet rs=null;
        int ans=0;
        try{
            ps1=conn.prepareStatement("Select * from usercart where prodid=? and useremail=?");
            ps1.setString(1, cart.getProdId());
            ps1.setString(2,cart.getUseremail());
            rs=ps1.executeQuery();
            if(rs.next()){
                int qty=cart.getQuantity();
                if(qty>0){
                    ps2=conn.prepareStatement("Update usercart set quantity=? where useremail=? and prodid=?");
                    ps2.setInt(1, cart.getQuantity());
                    ps2.setString(2, cart.getUseremail());
                    ps2.setString(3, cart.getProdId());
                    ans=ps2.executeUpdate();
                    if(ans>0){
                        status="Product successfully updated in the Cart";
                    }else{
                             status="Could not update the Product";
                         }
                    } else if(qty==0){
                         ps2=conn.prepareStatement("Delete from usercart where useremail=? and prodid=?");
                         ps2.setString(1, cart.getUseremail());
                         ps2.setString(2,cart.getProdId());
                         ans=ps2.executeUpdate();
                         if(ans>0){
                            status="Product successfully deleted from the Cart";
                        }else{
                             status="Could not delete the Product";
                         }
                }
            }else{
                ps2=conn.prepareStatement("Insert into usercart values(?,?,?)");
                ps2.setString(1, cart.getUseremail());
                ps2.setString(2,cart.getProdId());
                ps2.setInt(3,cart.getQuantity());
                ans=ps2.executeUpdate();
                if(ans>0){
                    status="Product successfully added in the Cart";
               }else{
                    status="Could not add the product";
                }
            }
        }catch(SQLException ex){
            status="Updation failed due to exception";
            System.out.println("Error in updateProductInCart"+ex);
            ex.printStackTrace();
         }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        DBUtil.closeResultSet(rs);
        return status;
    }

    @Override
    public String addProductToCart(CartPojo cart) {
        String status="Failed to Add to Cart";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        ResultSet rs=null;
        try{
            ps1=conn.prepareStatement("Select quantity from usercart where prodid=? and useremail=?");
            ps1.setString(1, cart.getProdId());
            ps1.setString(2,cart.getUseremail());
            rs=ps1.executeQuery();
            if(rs.next()){
                ProductDaoImpl prodDao=new ProductDaoImpl();
                int stockQty=prodDao.getProductQuantity(cart.getProdId());
                int newQty=cart.getQuantity() + rs.getInt("quantity");
                if(stockQty<newQty){
                   cart.setQuantity(stockQty);
                   updateProductInCart(cart); 
                   status="Only "+stockQty+" no of items are available in our stock so we are adding "+stockQty+" in your cart";
                   DemandPojo demPojo=new DemandPojo();
                   demPojo.setProdId(cart.getProdId());
                   demPojo.setUseremail(cart.getUseremail());
                   demPojo.setDemandQuantity(newQty-stockQty);
                   DemandImpl demDao=new DemandImpl();
                   boolean result=demDao.addProduct(demPojo);
                   if(result==true){
                       status="We will mail you when "+(newQty-stockQty)+" no of items will be available";
                   }
                }else{
                    cart.setQuantity(newQty);
                    status=updateProductInCart(cart);  
                }
            }
        }catch(SQLException ex){
            status="Addition failed due to exception";
            System.out.println("Error in addProductInCart"+ex);
            ex.printStackTrace();
         }
        DBUtil.closeStatement(ps1);
        DBUtil.closeResultSet(rs);
        return status;
    }

    @Override
    public List<CartPojo> getAllCartItems(String userId) {
        List<CartPojo>itemList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select * from usercart where useremail=?");
            ps.setString(1, userId);
            rs=ps.executeQuery();
            while(rs.next()){
                CartPojo cart=new CartPojo();
                cart.setUseremail(rs.getString("useremail"));
                cart.setProdId(rs.getString("prodid"));
                cart.setQuantity(rs.getInt("quantity"));
                itemList.add(cart);
            }
        }catch(SQLException ex){
            System.out.println("Error in getAllCartItems"+ex);
            ex.printStackTrace();
         }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return itemList;
    }

    @Override
    public int getCartItemCount(String userId, String itemId) {
        if(userId==null || itemId==null){
            return 0;
        }
        int count=0;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select quantity from usercart where useremail=? and prodid=?");
            ps.setString(1, userId);
            ps.setString(2, itemId);
            rs=ps.executeQuery();
            if(rs.next()){
                count=rs.getInt(1);
            }
        }catch(SQLException ex){
            System.out.println("Error in getCartItemCount"+ex);
            ex.printStackTrace();
         }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return count;
    }

    @Override
    public String removeProductFromCart(String userId, String itemId) {
        String status="Product Removal Failed";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        ResultSet rs=null;
        try{
            ps1=conn.prepareStatement("select * from usercart where useremail=? and prodid=?");
            ps1.setString(1, userId);
            ps1.setString(2, itemId);
            rs=ps1.executeQuery();
            if(rs.next()){
                int prodQuantity=rs.getInt("quantity");
                prodQuantity-=1;
                int k;
                if(prodQuantity>0){
                    ps2=conn.prepareStatement("Update usercart set quantity=? where useremail=? and prodid=?");
                    ps2.setInt(1,prodQuantity);
                    ps2.setString(2,userId);
                    ps2.setString(3,itemId);
                    k=ps2.executeUpdate();
                    if(k>0){
                        status="Product successfully removed from the Cart";
                    }else{
                        ps2=conn.prepareStatement("Delete from usercart where useremail=? and prodid=?");
                        ps2.setString(1,userId);
                        ps2.setString(2,itemId);
                        k=ps2.executeUpdate();
                        if(k>0){
                            status="Product successfully removed from the Cart";
                        } 
                    }
                }
            }
        }catch(SQLException ex){
            status="Product Removal failed due to Exception";
            System.out.println("Error in removeProductFromCart"+ex);
            ex.printStackTrace();
         }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        DBUtil.closeResultSet(rs);
        return status;
    }

    @Override
    public boolean removeAProduct(String userId, String itemId) {
        boolean flag=false;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("Delete from usercart where useremail=? and prodid=?");
            ps.setString(1,userId);
            ps.setString(2,itemId);
            int k=ps.executeUpdate();
            if(k>0){
                flag=true;
            } 
        }catch(SQLException ex){
            System.out.println("Error in removeAProduct"+ex);
            ex.printStackTrace();
         }
        DBUtil.closeStatement(ps);
        return flag;
    }
}
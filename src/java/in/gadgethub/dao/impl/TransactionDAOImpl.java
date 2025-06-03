
package in.gadgethub.dao.impl;

import in.gadgethub.dao.TransactionDAO;
import in.gadgethub.utility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author amans
 */
public class TransactionDAOImpl implements TransactionDAO {
       @Override
    public String getUserId(String transId) {
        String userId="";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("Select useremail from transactions where transid=?");
            ps.setString(1,transId);
            rs=ps.executeQuery();
            if(rs.next()){
                userId=rs.getString(1);
            }
        }catch(SQLException ex){
            System.out.println("Error in haveDemanded:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return userId;
    }
    
}
   


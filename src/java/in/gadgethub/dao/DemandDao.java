package in.gadgethub.dao;

import in.gadgethub.pojo.DemandPojo;
import java.util.List;

public interface DemandDao {
    public boolean addProduct(DemandPojo demandPojo);  
    
    public boolean removeProduct(String userid,String prodId);
    
    public List<DemandPojo>haveDemanded(String prodId);
    
}

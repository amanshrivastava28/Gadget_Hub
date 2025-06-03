
package in.gadgethub.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author amans
 */
public class IDutil {
    public static String generateProdId(){
        Date today=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
        String prodId=sdf.format(today);
        prodId="P"+prodId;
        return prodId;
    }
    public static String generateTransId(){
        Date today=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
        String TransId=sdf.format(today);
        TransId="T"+TransId;
        return TransId;
    }
}


package in.gadgethub.servlet;

import in.gadgethub.dao.impl.UserDaoImpl;
import in.gadgethub.pojo.UserPojo;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author amans
 */
public class RegisterServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status="Please input same password";
        String userName=request.getParameter("username");
        String userEmail=request.getParameter("useremail");
        String userAddress=request.getParameter("useraddress");
        String userMobile=request.getParameter("mobileno");
        int pincode=Integer.parseInt(request.getParameter("pincode"));
        String password=request.getParameter("password");
        String confPassword=request.getParameter("confpassword");
        if(password.equals(confPassword)){
        UserDaoImpl userDao=new UserDaoImpl();
        UserPojo user=new UserPojo(userName,userEmail,userMobile,userAddress,pincode,password);
        status=userDao.registerUser(user);
    }
        RequestDispatcher rd=request.getRequestDispatcher("register.jsp?message="+status);
        rd.include(request, response);
 }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

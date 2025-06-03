
package in.gadgethub.servlet;

import in.gadgethub.dao.OrderDAO;
import in.gadgethub.dao.TransactionDAO;
import in.gadgethub.dao.UserDao;
import in.gadgethub.dao.impl.OrderDAOImpl;
import in.gadgethub.dao.impl.TransactionDAOImpl;
import in.gadgethub.dao.impl.UserDaoImpl;
import in.gadgethub.pojo.OrderPojo;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShippedItemServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        String password = (String) session.getAttribute("password");
        String userType = (String) session.getAttribute("userType");
        if (userType == null || password == null || userName == null) {
            response.sendRedirect("login.jsp?message=Access denied ! Please login first");
            return;
        } else if (!userType.equals("admin")) {
            response.sendRedirect("login.jsp?message=Access denied ! Please login as admin");
            return;
        }
        
        OrderDAO orderDao = new OrderDAOImpl();
        UserDao userDao = new UserDaoImpl();
        TransactionDAO transDao=new TransactionDAOImpl();
        List<OrderPojo> orders = orderDao.getAllOrders(); 
        Map<String, String> user_Id = new HashMap<>();
        Map<String, String> user_address = new HashMap<>();
        for (OrderPojo order : orders) {
            String users=transDao.getUserId(order.getOrderId());
            user_Id.put(order.getOrderId(),users);
            user_address.put(users, userDao.getUserAddr(users));
        }
        RequestDispatcher rd = request.getRequestDispatcher("shippedItems.jsp");
        request.setAttribute("orders", orders);
        request.setAttribute("user_Id", user_Id);
        request.setAttribute("user_address", user_address);
        rd.forward(request, response);
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
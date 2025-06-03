package in.gadgethub.servlet;

import in.gadgethub.dao.OrderDAO;
import in.gadgethub.dao.TransactionDAO;
import in.gadgethub.dao.UserDao;
import in.gadgethub.dao.impl.OrderDAOImpl;
import in.gadgethub.dao.impl.TransactionDAOImpl;
import in.gadgethub.dao.impl.UserDaoImpl;
import in.gadgethub.pojo.OrderPojo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnshippedItemServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        String password = (String) session.getAttribute("password");
        String userType = (String) session.getAttribute("userType");

        if (userType == null || password == null || userName == null) {
            response.sendRedirect("login.jsp?message=Access denied! Please login first.");
            return;
        } else if (!userType.equals("admin")) {
            response.sendRedirect("login.jsp?message=Access denied! Please login as admin.");
            return;
        }
        OrderDAO orderDao = new OrderDAOImpl();
        UserDao userDao = new UserDaoImpl();
        TransactionDAO transDao=new TransactionDAOImpl();
        List<OrderPojo> orders = orderDao.getAllOrders();
        Map<String, String> user_Id = new HashMap<>();
        Map<String, String> user_address = new HashMap<>();
        for(OrderPojo order:orders){
            String users=transDao.getUserId(order.getOrderId());
            user_Id.put(order.getOrderId(),users);
            user_address.put(users, userDao.getUserAddr(users));
        }
        request.setAttribute("orders", orders);
        request.setAttribute("user_Id", user_Id);
        request.setAttribute("user_address", user_address);

        RequestDispatcher rd = request.getRequestDispatcher("unshippedItems.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.frontend;

import core.FrontendServlet;
import entity.Book;
import entity.Invoice;
import entity.InvoiceFood;
import entity.Room;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.InvoiceModel;
import model.RoomModel;
import utility.Helper;

/**
 *
 * @author MyPC
 */
public class OrderServlet extends FrontendServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try {
            String action = request.getParameter("action");
            switch (action) {
                case "food":
                    this.actionFood(request, response);
                    break;
                case "view":
                    this.actionView(request, response);
                    break;
            }
        } catch (NullPointerException e) {
            response.sendRedirect("index");
        }
    }
    
    private void actionFood(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (this.isPost(request)) {
            response.setContentType("application/json");
            int id = Integer.parseInt(request.getParameter("id"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            try (PrintWriter out = response.getWriter()) {
                HttpSession session = request.getSession();
                InvoiceFood cart = (InvoiceFood) session.getAttribute("cart");
                if (cart == null) {
                    cart = new InvoiceFood();
                }
                cart.addCartFood(id, quantity);
                session.setAttribute("cart", cart);
                out.print("{\"count\": \"" + cart.getTotalCount() + "\", \"total\": \"" + Helper.currency(cart.getTotalPrice()) + "\"}");
            }
        }
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

    private void actionView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        Invoice i = InvoiceModel.find("WHERE token = '" + token + "'");
        this.setTitle(request, "Your order!");
        request.setAttribute("invoice", i);
        this.include("order/view.jsp", request, response);
    }
}

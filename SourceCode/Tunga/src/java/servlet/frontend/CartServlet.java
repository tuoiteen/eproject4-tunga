/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.frontend;

import core.FrontendServlet;
import entity.InvoiceFood;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utility.Helper;

/**
 *
 * @author Hoangha.FPT
 */
public class CartServlet extends FrontendServlet {

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
        String action = request.getParameter("action");
        switch (action) {
            case "view":
                this.actionView(request, response);
                break;
            case "delete":
                this.actionDelete(request, response);
                break;
        }
    }

    private void actionView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.setTitle(request, "Your shopping cart");
        HttpSession session = request.getSession();
        request.setAttribute("cart", session.getAttribute("cart"));
        this.include("cart/view.jsp", request, response);
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

    private void actionDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (this.isPost(request)) {
            int id = Integer.parseInt(request.getParameter("id"));
            HttpSession session = request.getSession();
            InvoiceFood cart = (InvoiceFood) session.getAttribute("cart");
            cart.deleteCartFood(id);
            if (cart.getCartFood().isEmpty()) {
                session.removeAttribute("cart");
            } else {
                session.setAttribute("cart", cart);
            }
            response.setContentType("application/json");
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"isEmpty\": \"" + cart.getCartFood().isEmpty() + "\", \"count\": \"" + cart.getTotalCount() + "\", \"totalText\": \"" + Helper.currency(cart.getTotalPrice()) + "\", \"total\": \"" + cart.getTotalPrice() + "\"}");
            }
        }
    }
}
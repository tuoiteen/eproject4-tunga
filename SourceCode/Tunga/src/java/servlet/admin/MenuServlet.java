/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.admin;

import core.AdminServlet;
import entity.Menu;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MenuModel;

/**
 *
 * @author MyPC
 */
//TODO cần phải kế thừa AdminServlet
public class MenuServlet extends AdminServlet {

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
//        this.checkLogin(request, response);
        String action = request.getParameter("action");
        switch (action) {
            case "add":
                this.actionAdd(request, response);
                break;
            case "index":
                this.actionIndex(request, response);
                break;
            case "edit":
                this.actionEdit(request, response);
                break;
            case "view":
                this.actionView(request, response);
                break;
            case "delete":
                this.actionDelete(request, response);
                break;
            default:
                this.actionIndex(request, response);
                break;
        }
    }

    private void actionView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.setTitle(request, "View an existing menu");
        this.setActiveSidebar(request, "menu/index");
        int id = Integer.parseInt(request.getParameter("id"));
        Menu m = MenuModel.find(id);
        if (m == null) {
            throw new ServletException("Not found");
        }
        request.setAttribute("menu", m);
        this.include("views/menu/view.jsp", request, response);
    }

    private void actionDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Menu m = MenuModel.find(id);
        if (m != null && MenuModel.delete(id)) {
            request.setAttribute("message", "success");
        } else {
            request.setAttribute("message", "error");
        }
        this.actionIndex(request, response);
    }

    private void actionEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.setTitle(request, "Edit a menu");
        this.setActiveSidebar(request, "menu/index");
        int id = Integer.parseInt(request.getParameter("id"));
        Menu m = MenuModel.find(id);
        if (this.isPost(request) && m != null) {
            String name = request.getParameter("name");
            int order = Integer.parseInt(request.getParameter("order"));
            m.setName(name);
            m.setOrder(order);
            if (MenuModel.update(id, m)) {
                request.setAttribute("message", "success");
            } else {
                request.setAttribute("message", "error");
            }
        }
        request.setAttribute("menu", m);
        this.include("views/menu/edit.jsp", request, response);
    }

    private void actionAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.setTitle(request, "Add a new menu");
        this.setActiveSidebar(request, "menu/add");
        if (this.isPost(request)) {
            String name = request.getParameter("name");
            int order = Integer.parseInt(request.getParameter("order"));
            Menu m = new Menu(name, order);
            if (MenuModel.insert(m)) {
                request.setAttribute("message", "success");
            } else {
                request.setAttribute("message", "error");
            }
        }
        this.include("views/menu/add.jsp", request, response);
    }

    private void actionIndex(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.setTitle(request, "List menu");
        this.setActiveSidebar(request, "menu/index");
        List<Menu> list = MenuModel.findAll();
        request.setAttribute("menus", list);
        this.include("views/menu/index.jsp", request, response);
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

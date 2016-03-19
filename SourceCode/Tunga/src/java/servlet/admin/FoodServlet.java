/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.admin;

import core.AdminServlet;
import entity.Food;
import entity.Menu;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.FoodModel;
import model.MenuModel;
import utility.Helper;

/**
 *
 * @author TuanDo
 */
@MultipartConfig
public class FoodServlet extends AdminServlet {

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

    private String uploadFile(String column, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = Helper.appPath() + "uploads";
        Part imagePart = request.getPart(column);
        String imageName = getFileName(imagePart);
        if (imageName != null) {
            OutputStream out;
            InputStream fileContent;
            try {
                out = new FileOutputStream(new File(path + File.separator + imageName));
                fileContent = imagePart.getInputStream();
                int read;
                byte[] bytes = new byte[1024];
                while ((read = fileContent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return imageName;
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private void actionAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.setTitle(request, "Add a new food");
        this.setActiveSidebar(request, "food/add");
        if (this.isPost(request)) {
            int menuId = Integer.parseInt(request.getParameter("menuId"));
            String name = request.getParameter("name");
            float price = Float.parseFloat(request.getParameter("price"));
            String imageName = this.uploadFile("image", request, response);
            Food f = new Food(menuId, name, price, imageName);
            if (FoodModel.insert(f)) {
                request.setAttribute("message", "success");
            } else {
                request.setAttribute("message", "failed");
            }
        }
        List<Menu> list = MenuModel.findAll();
        request.setAttribute("menus", list);
        this.include("views/food/add.jsp", request, response);
    }

    private void actionIndex(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.setTitle(request, "List Food");
        this.setActiveSidebar(request, "food/index");
        List<Food> list = FoodModel.findAll();
        request.setAttribute("foods", list);
        this.include("views/food/index.jsp", request, response);
    }

    private void actionView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.setTitle(request, "View an existing food");
        this.setActiveSidebar(request, "food/index");
        int id = Integer.parseInt(request.getParameter("id"));
        Food f = FoodModel.find(id);
        if (f == null) {
            throw new ServletException("Not found");
        }
        List<Menu> list = MenuModel.findAll();
        request.setAttribute("menus", list);
        request.setAttribute("food", f);
        this.include("views/food/view.jsp", request, response);
    }

    private void actionDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Food f = FoodModel.find(id);
        if (f != null && FoodModel.delete(id)) {
            request.setAttribute("message", "success");
        } else {
            request.setAttribute("message", "error");
        }
        this.actionIndex(request, response);
    }

    private void actionEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.setTitle(request, "Edit a food");
        this.setActiveSidebar(request, "food/index");
        int id = Integer.parseInt(request.getParameter("id"));
        Food f = FoodModel.find(id);
        if (this.isPost(request) && f != null) {
            int menuId = Integer.parseInt(request.getParameter("menuId"));
            String name = request.getParameter("name");
            float price = Float.parseFloat(request.getParameter("price"));
            String imageName = this.uploadFile("image", request, response);
            f.setName(name);
            f.setMenuId(menuId);
            f.setPrice(price);
            if (imageName != null) {
                f.setImage(imageName);
            }
            if (FoodModel.update(id, f)) {
                request.setAttribute("message", "success");
            } else {
                request.setAttribute("message", "error");
            }
        }
        List<Menu> list = MenuModel.findAll();
        request.setAttribute("menus", list);
        request.setAttribute("food", f);
        this.include("views/food/edit.jsp", request, response);
    }

}

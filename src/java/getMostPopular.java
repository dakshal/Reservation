/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author dakshal
 */
@WebServlet(urlPatterns = {"/getMostPopular"})
public class getMostPopular extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,
                   IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials",
                "true");
        response.setHeader("Access-Control-Expose-Headers",
                "FooBar");
        response.setHeader("Content-Type",
                "text/html; charset=utf-8");
        response.setContentType("application/json");
        if (request.getMethod().equalsIgnoreCase("GET")) {
            PrintWriter out = response.getWriter();
            try {
                Statement stmt;
                Connection conn = connectSQL();
                stmt = (Statement) conn.createStatement();
                String sqlQuery;
                JSONArray jc = new JSONArray();
                sqlQuery = "SELECT r.id, r.name, r.area, r.imageId, r.price, r.type, r.state, r.Description, r.rating from reservation.reservationList r, reservation.mostPopular m where r.id = m.uniqueId ORDER By m.popularity DESC;";
                ResultSet rs = stmt.executeQuery(sqlQuery);
                int i=1;
                if (rs.first()) {
                    do{
                        JSONObject json = new JSONObject();
                        json.put("id", rs.getInt("id"));
                        json.put("name", rs.getString("name"));
                        json.put("area", rs.getString("area"));
                        json.put("imageId", rs.getString("imageId"));
                        json.put("price", rs.getString("price"));
                        json.put("type", rs.getString("type"));
                        json.put("state", rs.getString("state"));
                        json.put("Description", rs.getString("Description"));
                        json.put("rating", rs.getString("rating"));
                        jc.add(json);
                        i++;
                    }while(rs.next());
                        out.println(jc.toJSONString());
                        out.close();
                }
                else {
                    JSONObject js = new JSONObject();
                    js.put("success", "userid doesn't exists");
                    response.setContentType("application/json");
                    out.println(js);
                    out.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.setContentType("application/json");
                out.println("{\"success\": \"Error\"}");
                out.close();
            }
        }
        else {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            out.println("{\"success\": \"Invalid request\"}");
            out.close();
        }
    }

    private Connection connectSQL() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            return null;
        }

//        System.out.println("MySQL JDBC Driver Registered!");
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/recipeRecommender", SQLID.
                    getUserId(),
                    SQLID.getPassword());

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            return conn;
        }
        if (conn != null) {
            System.out.println("requested through addNewUser");
        }
        else {
            System.out.println("Failed to make connection!");
        }
        return conn;
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
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,
                   IOException {
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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,
                   IOException {
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

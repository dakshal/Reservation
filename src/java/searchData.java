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
import org.json.simple.JSONObject;

/**
 *
 * @author dakshal
 */
@WebServlet(urlPatterns = {"/searchData"})
public class searchData extends HttpServlet {

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
        if (request.getMethod().equalsIgnoreCase("GET")) {
            PrintWriter out = response.getWriter();
            try {
                Statement stmt;
                Connection conn = connectSQL();
                stmt = (Statement) conn.createStatement();
                String sqlQuery;
                JSONObject jc = new JSONObject();
                String state = request.getHeader("state");
                String area = request.getHeader("area");
                String type = request.getHeader("type");
                sqlQuery = "SELECT * FROM reservation.reservationList WHERE state = \"" + state + "\" AND area = \"" + area + "\" AND availability > 0 AND type = \"" + type + "\";";
                System.out.println(sqlQuery);
                ResultSet rs = stmt.executeQuery(sqlQuery);
                int i = 1;
                if (rs.first()) {
                    do {
                        JSONObject jObj = new JSONObject();
                        jObj.put("id", rs.getString("id"));
                        jObj.put("name", rs.getString("name"));
                        jObj.put("location", rs.getString("location"));
                        jObj.put("area", rs.getString("area"));
                        jObj.put("state", rs.getString("state"));
                        jObj.put("type", rs.getString("type"));
                        jObj.put("availability", rs.getString("availability"));
                        jObj.put("rating", rs.getString("rating"));
                        jObj.put("price", rs.getString("price"));
                        jObj.put("timing", rs.getString("timing"));
                        jObj.put("imageId", rs.getString("imageId"));
                        jObj.put("description", rs.getString("description"));
                        jc.put(Integer.toString(i++), jObj);
                    } while (rs.next());
                    response.setContentType("application/json");
                    out.println(jc);
                    out.close();
                }
                else {
                    jc.put("success", "No Result Found");
                    response.setContentType("application/json");
                    out.println(jc);
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

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
@WebServlet(urlPatterns = {"/addNewUser"})
public class addNewUser extends HttpServlet {

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
        if (request.getMethod().equalsIgnoreCase("POST")) {
            PrintWriter out = response.getWriter();
            try {
                Statement stmt;
                Connection conn = connectSQL();
                stmt = (Statement) conn.createStatement();
                String sqlQuery;
                JSONObject jc = new JSONObject();
                sqlQuery = "SELECT emailId FROM reservation.userData WHERE emailId = \"" + request.
                        getHeader("emailId") + "\";";
                ResultSet rs = stmt.executeQuery(sqlQuery);
                if (rs.first()) {
                    jc.put("success", "already exists");
                    response.setContentType("application/json");
                    out.println(jc);
                    out.close();
                }
                else {
                    String emailId = request.getHeader("emailId");
                    String name = request.getHeader("name");
                    String password = request.getHeader("password");
                    if (addToDatabase(emailId, name, password)) {
                        jc.put("success", "true");
                        response.setContentType("application/json");
                        out.println(jc);
                        out.close();
                    }
                    else {
                        jc.put("success", "false");
                        response.setContentType("application/json");
                        out.println(jc);
                        out.close();
                    }
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
            out.println("{\"success\": \"Invalid\"}");
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

    private boolean addToDatabase(String emailId, String name, String password)
            throws SQLException {
        Statement stmt;
        Connection conn = connectSQL();
        stmt = (Statement) conn.createStatement();
        String sqlQuery;
        JSONObject jc = new JSONObject();
        sqlQuery = "INSERT INTO `reservation`.`userData` (`emailId`, `password`,"
                + " `name`) VALUES ('" + emailId + "', '" + password + "', '" + name + "');";
        if (!stmt.execute(sqlQuery)) {
            return true;
        }
        else {
            return false;   
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

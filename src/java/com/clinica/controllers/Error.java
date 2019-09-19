package com.clinica.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Error extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PrintWriter out = response.getWriter();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        String statusName = (String)request.getAttribute("javax.servlet.error.status_name");
        String statusUri = (String)request.getAttribute("javax.servlet.error.status_uri");
        
        switch(statusCode){
            case 404:
                request.getRequestDispatcher("error/error404.jsp").forward(request, response);
                break;
            case 500:
                request.getRequestDispatcher("error/error500.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("error/error500.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

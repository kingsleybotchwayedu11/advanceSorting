package controllers;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import  jakarta.servlet.http.*;
import java.io.IOException;



@WebServlet("/sort")
public class SortingController extends  HttpServlet{
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
        //handles  sorting algorithms
        

    };
}

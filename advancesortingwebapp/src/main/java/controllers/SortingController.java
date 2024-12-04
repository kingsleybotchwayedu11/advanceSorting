package controllers;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import  jakarta.servlet.http.*;
import storage.Storage;
import utils.SortingAlgorithms;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;



@WebServlet("/sort/*")
public class SortingController extends  HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
        String params = req.getPathInfo();
        List<Integer> sortedDataset = SortingAlgorithms.GetFunciton("mergesort").apply(Storage.getNumbers());
        new ObjectMapper().writeValue(resp.getWriter(), sortedDataset);
    };
}

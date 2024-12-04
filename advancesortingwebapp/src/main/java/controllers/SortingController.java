package controllers;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import  jakarta.servlet.http.*;
import storage.Storage;
import utils.SortingAlgorithms;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.databind.ObjectMapper;



@WebServlet("/sort/*")
public class SortingController extends  HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
        String algorithmName = req.getPathInfo();
        if(algorithmName == null || algorithmName.equals("/") ) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("The algorithm name is required");
        }
        else {
            //check if the algorithms name is correct
            Function<List<Integer>, List<Integer>> algorithm = SortingAlgorithms.GetFunciton(algorithmName.substring(1));
            if(algorithm == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("wrong sorting algorithm name, accepted values are mergesort, heapsort, radixsort, bucketsort and quicksort");
            }
            else {
                List<Integer> sortedDataset = algorithm.apply(Storage.getNumbers());
                new ObjectMapper().writeValue(resp.getWriter(), sortedDataset);
            }
        } 
    };
}

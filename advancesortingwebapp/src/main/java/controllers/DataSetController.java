package controllers;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import storage.Storage;
import utils.Report;
import utils.RequestHelper;
import utils.Urls;

import java.io.IOException;


@WebServlet("/dataset/*")
public class DataSetController extends HttpServlet {

    @Override
    protected void doPost(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws jakarta.servlet.ServletException, java.io.IOException {
        Report report = new Report();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
        // Accepts input as a JSON
        String json = RequestHelper.getJson(req);
        // Convert the object to JsonNode using ObjectMapper
        
         List<Integer> numbers = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Integer.class));
        // Send response to the user in JSON format
        Storage.addToDataSet(numbers);
        report.setMessage("Numbers added successfull to dataset");
        report.setData(Storage.getNumbers());
        //add the links
        report.addLink("Get dataset", Urls.getNumbers[0], Urls.getNumbers[1], Urls.getNumbers[2], "");
        report.setData(Storage.getNumbers());
        } catch (Exception ex) {
            ex.printStackTrace();
            report.setMessage("Internal error check message");
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            
        }  finally {
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(),report);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException, ServletException
    {
        //get object mapper
        ObjectMapper objectMapper = new ObjectMapper();
        //the the content type to json
        resp.setContentType("application/json");
        Report report = new Report();
        report.setMessage("Current Data set");
        report.addLink("Add to dataset", Urls.addNumber[0], Urls.addNumber[1], Urls.addNumber[2], "");
        report.setData(Storage.getNumbers());
        //get the list and retur as json a list
        objectMapper.writeValue(resp.getWriter(), report);
    }


    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp)
    throws IOException, ServletException
    {
        //get iindex
        String index = req.getPathInfo();
        //the the content type to json
        resp.setContentType("application/json");
        if(index == null || index.equals("/")) { // if no user id return all users
            //return all list
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("number index is requred");
        } else {
            try {
                //get user id as int
                boolean deleted = Storage.deleteNumber(Integer.parseInt(index.substring(1)));
                if(!deleted) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("index out of range");
                } else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    Report report = new Report();
                    report.setMessage("Number successfully deleted");
                    report.setData(Storage.getNumbers());
                    report.setData(Storage.getNumbers());
                    report.addLink("Add to dataset", Urls.addNumber[0], Urls.addNumber[1], Urls.addNumber[2], "");
                    report.addLink("Get dataset", Urls.getNumbers[0], Urls.getNumbers[1], Urls.getNumbers[2], "");

                    new ObjectMapper().writeValue(resp.getWriter(), Storage.getNumbers());
                }
                
            } catch(Exception ex) {
                ex.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("There is an internal error");
            }
        }
    }



    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //update a user
       Report report = new Report();
       // Accepts input as a JSON
       String json = RequestHelper.getJson(req);
       // Convert the object to JsonNode using ObjectMapper
       ObjectMapper objectMapper = new ObjectMapper();
       JsonNode rootNode = objectMapper.readTree(json);

       // Validate input and return error accordingly
       if (!(rootNode.has("index") && rootNode.has("number"))) {
           report.setMessage("index is required");
           resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Bad request
       } else {
           // Process and add the user to the storage
           boolean isUpdated = Storage.changeNuber(rootNode.get("index").asInt(), rootNode.get("number").asInt());
           if(!isUpdated) {
            report.setMessage("index out of range");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
           } else {
                report.setMessage("successfully updated");
                report.setData(Storage.getNumbers());
                //add affected links
                report.addLink("remove number", Urls.deleteNumber[0], Urls.deleteNumber[1], Urls.deleteNumber[2], "/" + rootNode.get("index").asText());
                report.addLink("Add to dataset", Urls.addNumber[0], Urls.addNumber[1], Urls.addNumber[2], "");
                report.addLink("Get dataset", Urls.getNumbers[0], Urls.getNumbers[1], Urls.getNumbers[2], "");
             }
           }
       resp.setContentType("application/json");
       objectMapper.writeValue(resp.getWriter(), report);
    }
}

package controllers;

import java.util.HashMap;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Report;
import utils.RequestHelper;
import utils.Storage;
import utils.Urls;
import models.Person;

import java.io.IOException;


@WebServlet("/person/*")
public class PersonController extends HttpServlet {

    @Override
    protected void doPost(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws jakarta.servlet.ServletException, java.io.IOException {
        Report statusReport = new Report();

        // Accepts input as a JSON
        String json = RequestHelper.getJson(req);
        // Convert the object to JsonNode using ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);

        // Validate input and return error accordingly
        if (!(rootNode.has("name") && rootNode.has("age"))) {
            statusReport.setMessage("name and age required");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Bad request
        } else {
            // Process and add the user to the storage
            int userId = Storage.addPerson(rootNode.get("name").asText(), rootNode.get("age").asInt());
            statusReport.setId(userId + "");
            statusReport.setMessage("user saved successfull");
            statusReport.addLink("get person", Urls.getPerson[0], Urls.getPerson[1], Urls.getPerson[2], "/" + userId);
            statusReport.addLink("delete person", Urls.deletePerosn[0], Urls.deletePerosn[1], Urls.deletePerosn[2], "/" + userId);
            statusReport.addLink("update person", Urls.updatePerson[0], Urls.updatePerson[1], Urls.updatePerson[2], "/" + userId);
            
            resp.setStatus(HttpServletResponse.SC_OK); // User saved
        }

        // Send response to the user in JSON format
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getWriter(), statusReport);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException, ServletException
    {
        //get id using parmamans
        ObjectMapper objectMapper = new ObjectMapper();
        String personId = req.getPathInfo();
        //the the content type to json
        resp.setContentType("application/json");
        if(personId == null || personId.equals("/")) { // if no user id return all users
            //return all list
            objectMapper.writeValue(resp.getWriter(), Storage.getAllPerson());
        } else {
            try {
                //get user id as int
                objectMapper.writeValue(resp.getWriter(), Storage.findPersonById(Integer.parseInt(personId.substring(1))));
            } catch(Exception ex) {
                ex.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("wrong person id");
            }
        }
    }


    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp)
    throws IOException, ServletException
    {
        //get id using parmamans
        String personId = req.getPathInfo();
        //the the content type to json
        resp.setContentType("application/json");
        if(personId == null || personId.equals("/")) { // if no user id return all users
            //return all list
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("person id is requred");
        } else {
            try {
                //get user id as int
                Person person = Storage.deletePerson(Integer.parseInt(personId.substring(1)));
                if(person == null) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("wrong person id");
                } else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().write("Person deleted");
                }
                
            } catch(Exception ex) {
                ex.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("person id should be number");
            }
        }
    }



    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //update a user
       Report statusReport = new Report();

       // Accepts input as a JSON
       String json = RequestHelper.getJson(req);
       // Convert the object to JsonNode using ObjectMapper
       ObjectMapper objectMapper = new ObjectMapper();
       JsonNode rootNode = objectMapper.readTree(json);

       // Validate input and return error accordingly
       if (!rootNode.has("id")) {
           statusReport.setMessage("User Id is required");
           resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Bad request
       } else {
           // Process and add the user to the storage
           Person person = Storage.findPersonById(rootNode.get("id").asInt());
           if(person == null) {
            statusReport.setMessage("no person found");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
           } else {
            if(rootNode.has("name"))  person.setName(rootNode.get("name").asText());
            if(rootNode.has("age"))   person.setAge(rootNode.get("age").asInt());
            statusReport.setId(person.getId() + "");
            statusReport.setMessage("user updated successfull");
            statusReport.addLink("get person", Urls.getPerson[0], Urls.getPerson[1], Urls.getPerson[2], "/" + person.getId());
            statusReport.addLink("delete person", Urls.deletePerosn[0], Urls.deletePerosn[1], Urls.deletePerosn[2], "/" + person.getId());
             // User saved
             }
           }
       resp.setContentType("application/json");
       objectMapper.writeValue(resp.getWriter(), statusReport);
    }
}

package utils;

import java.util.HashMap;

public class Report {

    private String message;
    private String id;
    public HashMap<String, HashMap<String, String>> links = new HashMap<> ();
    
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public HashMap<String, HashMap<String, String>> getLinks() {
        return links;
    }

    public void setLinks(HashMap<String, HashMap<String, String>> links) {
        this.links = links;
    }

    public void addLink(String name, String bauseUrl, String description, String method, String specUrl) {
        HashMap<String, String> link = new  HashMap<>();
        link.put("url", bauseUrl +  specUrl);
        link.put("method",method );
        link.put("description", description);
        links.put(name, link);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
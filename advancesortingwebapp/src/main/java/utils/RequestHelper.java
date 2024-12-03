package utils;

import java.io.BufferedReader;

import jakarta.servlet.http.HttpServletRequest;

public class RequestHelper {

    public static String getJson(HttpServletRequest req) throws jakarta.servlet.ServletException, java.io.IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        }

    return jsonBuilder.toString();
    }
}
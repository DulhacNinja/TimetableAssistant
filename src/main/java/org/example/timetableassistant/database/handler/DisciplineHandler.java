package org.example.timetableassistant.database.handler;
import com.google.gson.Gson;
import org.example.timetableassistant.database.OperationResult;
import org.example.timetableassistant.database.crud.DisciplineCRUD;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.Map;

public class DisciplineHandler {
    private static final DisciplineCRUD disciplineCRUD = new DisciplineCRUD();


    public static String createDiscipline(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";
        String name = req.queryParams("name");

        if (name == null || name.isEmpty()) {
            res.status(400);  // Bad Request
            return "Missing required fields (name).";
        }

        OperationResult result = disciplineCRUD.insertDiscipline(name);
        assert result != null : "result cannot be null";
        if (result.success) {
            res.status(201);
            return "{\"message\":\"" + result.message + "\"}";
        } else {
            res.status(500);
            return "{\"error\":\"" + result.message + "\"}";
        }
    }



    public static String getDisciplineById(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        int id = Integer.parseInt(req.params(":id"));
        assert id > 0 : "id must be greater than 0";
        OperationResult result = disciplineCRUD.getDisciplineById(id);
        assert result != null : "result cannot be null";
        Gson gson = new Gson();

        if (result.success) {
            res.status(200);
            Map<String, Object> response = new HashMap<>();
            response.put("message", result.message);
            return gson.toJson(response);
        } else {
            res.status(404);
            Map<String, Object> response = new HashMap<>();
            response.put("error", result.message);
            return gson.toJson(response);
        }
    }


    public static String getAllDisciplines(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";
        OperationResult result = disciplineCRUD.getAllDisciplines();
        assert result != null : "result cannot be null";
        Gson gson = new Gson();
        if (result.success) {
            res.status(200);
            Map<String, Object> response = new HashMap<>();
            response.put("message", result.message);
            return gson.toJson(response);
        } else {
            res.status(404);
            Map<String, Object> response = new HashMap<>();
            response.put("error", result.message);
            return gson.toJson(response);
        }
    }



    public static String updateDiscipline(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        int id = Integer.parseInt(req.params(":id"));
        assert id > 0 : "id must be greater than 0";
        String newName = req.queryParams("name");

        if (newName == null || newName.isEmpty()) {
            res.status(400);  // Bad Request
            return "Missing required fields (name).";
        }

        OperationResult result = disciplineCRUD.updateDiscipline(id, newName);
        assert result != null : "result cannot be null";
        if (result.success) {
            res.status(200);
            return "{\"message\":\"" + result.message + "\"}";
        } else {
            res.status(500);
            return "{\"error\":\"" + result.message + "\"}";
        }
    }


    public static String deleteDiscipline(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";
        int id = Integer.parseInt(req.params(":id"));
        assert id > 0 : "id must be greater than 0";
        OperationResult result = disciplineCRUD.deleteDiscipline(id);
        assert result != null : "result cannot be null";
        if (result.success) {
            res.status(200);
            return "{\"message\":\"" + result.message + "\"}";
        } else {
            res.status(500);
            return "{\"error\":\"" + result.message + "\"}";
        }
    }
}

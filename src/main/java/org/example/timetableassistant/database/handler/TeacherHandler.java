package org.example.timetableassistant.database.handler;
import com.google.gson.Gson;
import org.example.timetableassistant.database.OperationResult;
import org.example.timetableassistant.database.crud.TeacherCRUD;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.Map;

public class TeacherHandler {
    private static final TeacherCRUD teacherCRUD = new TeacherCRUD();

    public static String createTeacher(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        String name = req.queryParams("name");

        if (name == null) {
            res.status(400); // Bad Request
            return "{\"error\":\"Missing required field (name).\"}";
        }

        OperationResult result = teacherCRUD.insertTeacher(name);
        assert result != null : "result cannot be null";

        if (result.success) {
            res.status(201); // Created
            return "{\"message\":\"" + result.message + "\"}";
        } else {
            res.status(500);
            return "{\"error\":\"" + result.message + "\"}";
        }
    }

    public static String getTeacherById(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        int id = Integer.parseInt(req.params(":id"));
        assert id > 0 : "id must be greater than 0";

        OperationResult result = teacherCRUD.getTeacherById(id);
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

    public static String getAllTeachers(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        OperationResult result = teacherCRUD.getAllTeachers();
        assert result != null : "result cannot be null";
        Gson gson = new Gson();
        Map<String, Object> response = new HashMap<>();

        if (result.success) {
            res.status(200);
            response.put("message", result.message);
        } else {
            res.status(404);
            response.put("error", result.message);
        }

        return gson.toJson(response);
    }


    public static String updateTeacher(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        int id = Integer.parseInt(req.params(":id"));
        assert id > 0 : "id must be greater than 0";

        String newName = req.queryParams("name");

        if (newName == null) {
            res.status(400);
            return "{\"error\":\"Missing required field (name).\"}";
        }

        OperationResult result = teacherCRUD.updateTeacher(id, newName);

        if (result.success) {
            res.status(200);
            return "{\"message\":\"" + result.message + "\"}";
        } else {
            res.status(500);
            return "{\"error\":\"" + result.message + "\"}";
        }
    }


    public static String deleteTeacher(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        int id = Integer.parseInt(req.params(":id"));
        assert id > 0 : "id must be greater than 0";

        OperationResult result = teacherCRUD.deleteTeacher(id);
        assert result != null : "result cannot be null";

        if (result.success) {
            res.status(200);
            return "{\"message\":\"" + result.message + "\"}";
        } else {
            res.status(404);
            return "{\"error\":\"" + result.message + "\"}";
        }
    }


    public static String getTeacherByName(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        String name = req.params("name");

        if (name == null) {
            res.status(400);
            return "{\"error\":\"Missing required field (name).\"}";
        }

        OperationResult result = teacherCRUD.getTeacherByName(name);
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
}

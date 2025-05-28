package org.example.timetableassistant.database.handler;

import com.google.gson.Gson;
import org.example.timetableassistant.database.OperationResult;
import org.example.timetableassistant.database.crud.DisciplineAllocationsCRUD;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class DisciplineAllocationHandler {
    private static final DisciplineAllocationsCRUD disciplineAllocationCRUD = new DisciplineAllocationsCRUD();

    public static String createDisciplineAllocation(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        int disciplineId = Integer.parseInt(req.queryParams("discipline_id"));
        int teacherId = Integer.parseInt(req.queryParams("teacher_id"));
        int classTypeId = Integer.parseInt(req.queryParams("class_type_id"));
        int hoursPerWeek = Integer.parseInt(req.queryParams("hours_per_week"));

        if (disciplineId == 0 || teacherId == 0 || classTypeId == 0 || hoursPerWeek == 0) {
            res.status(400);  // Bad Request
            return "Missing required fields.";
        }

        OperationResult result = disciplineAllocationCRUD.insertDisciplineAllocation(disciplineId, teacherId, classTypeId, hoursPerWeek);
        assert result != null : "result cannot be null";

        if (result.success) {
            res.status(201);
            return "{\"message\":\"" + result.message + "\"}";
        } else {
            res.status(500);
            return "{\"error\":\"" + result.message + "\"}";
        }
    }

    public static String getDisciplineAllocationById(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";
        int id = Integer.parseInt(req.params(":id"));
        assert id > 0 : "id must be greater than 0";

        OperationResult result = disciplineAllocationCRUD.getDisciplineAllocationById(id);
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

    public static String getAllDisciplineAllocations(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";
        OperationResult result = disciplineAllocationCRUD.getAllDisciplineAllocations();
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

    public static String getAllDisciplineAllocationsByTeacherId(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";
        int teacherId;
        try {
            teacherId = Integer.parseInt(req.params(":id"));
            assert teacherId > 0 : "teacherId must be greater than 0";
        } catch (NumberFormatException e) {
            res.status(400);
            return "{\"error\":\"ID invalid pentru profesor.\"}";
        }

        OperationResult result = disciplineAllocationCRUD.getAllDisciplineAllocationsByTeacherId(teacherId);
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

    public static String getAllDisciplineAllocationsByDisciplineId(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        int disciplineId;
        try {
            disciplineId = Integer.parseInt(req.params(":id"));
            assert disciplineId > 0 : "disciplineId must be greater than 0";
        } catch (NumberFormatException e) {
            res.status(400);
            return "{\"error\":\"ID invalid pentru disciplină.\"}";
        }

        OperationResult result = disciplineAllocationCRUD.getAllDisciplineAllocationsByDisciplineId(disciplineId);
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




    public static String updateDisciplineAllocation(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        int id = Integer.parseInt(req.params(":id"));
        assert id > 0 : "id must be greater than 0";

        int newDisciplineId = Integer.parseInt(req.queryParams("discipline_id"));
        int newTeacherId = Integer.parseInt(req.queryParams("teacher_id"));
        int newClassTypeId = Integer.parseInt(req.queryParams("class_type_id"));
        int newHoursPerWeek = Integer.parseInt(req.queryParams("hours_per_week"));

        if (newDisciplineId == 0 || newTeacherId == 0 || newClassTypeId == 0 || newHoursPerWeek == 0) {
            res.status(400);  // Bad Request
            return "Missing required fields.";
        }

        OperationResult result = disciplineAllocationCRUD.updateDisciplineAllocation(id, newDisciplineId, newTeacherId, newClassTypeId, newHoursPerWeek);
        assert result != null : "result cannot be null";
        if (result.success) {
            res.status(201);
            return "{\"message\":\"" + result.message + "\"}";
        } else {
            res.status(500);
            return "{\"error\":\"" + result.message + "\"}";
        }
    }

    public static String deleteDisciplineAllocation(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        int id = Integer.parseInt(req.params(":id"));
        assert id > 0 : "id must be greater than 0";
        OperationResult result = disciplineAllocationCRUD.deleteDisciplineAllocation(id);
        assert result != null : "result cannot be null";

        if (result.success) {
            res.status(201);
            return "{\"message\":\"" + result.message + "\"}";
        } else {
            res.status(500);
            return "{\"error\":\"" + result.message + "\"}";
        }
    }

}

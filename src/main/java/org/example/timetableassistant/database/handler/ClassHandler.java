package org.example.timetableassistant.database.handler;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import org.example.timetableassistant.database.OperationResult;
import org.example.timetableassistant.database.crud.ClassCRUD;
import org.example.timetableassistant.database.crud.ClassType;
import org.example.timetableassistant.model.Semiyear;
import spark.Request;
import spark.Response;

public class ClassHandler {
    private static final ClassCRUD classCRUD = new ClassCRUD();
    public static String createClass(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        int disciplineId = Integer.parseInt(req.queryParams("discipline_id"));
        assert disciplineId > 0 : "discipline_id must be greater 0";

        String classTypeString = req.queryParams("class_type");
        assert classTypeString != null && !classTypeString.isEmpty(): "class_type cannot be null nor empty";

        int roomId = Integer.parseInt(req.queryParams("room_id"));
        assert roomId > 0 : "room_id must be greater 0";
        int timeSlotId = Integer.parseInt(req.queryParams("time_slot_id"));
        assert timeSlotId > 0 : "time_slot_id must be greater 0";
        int teacherId = Integer.parseInt(req.queryParams("teacher_id"));
        assert teacherId > 0 : "teacher_id must be greater 0";

        // Valori opționale
        String semiyearStr = req.queryParams("semiyear");
        Semiyear semiyear = null;
        if (semiyearStr != null && !semiyearStr.isEmpty()) {
            try{
                semiyear = Semiyear.valueOf(semiyearStr);
            } catch(Exception e){
                res.status(400);
                return "{\"error\":\"Invalid semiyear value: " + semiyearStr + "\"}";
            }
        }
        Integer groupId = (req.queryParams("group_id") != null && !req.queryParams("group_id").isEmpty())
                    ? Integer.parseInt(req.queryParams("group_id"))
                    : null;
        
        if (disciplineId == 0 || classTypeString == null || roomId == 0 || timeSlotId == 0 || teacherId == 0) {
            res.status(400);
            return "Missing required fields.";
        }

        ClassType classType;
        try {
            classType = ClassType.valueOf(classTypeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            res.status(400);
            return "{\"error\":\"Invalid class type. Please use COURSE, LABORATORY, or SEMINAR.\"}";
        }

        OperationResult result = classCRUD.insertClass(
                disciplineId,
                classType,
                roomId,
                timeSlotId,
                semiyear,
                groupId,
                teacherId
        );
        assert result != null : "result cannot be null";

        if (result.success) {
            res.status(201);
            return "{\"message\":\"" + result.message + "\"}";
        } else {
            res.status(500);
            return "{\"error\":\"" + result.message + "\"}";
        }
    }


    public static String getClassById(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";
        int id = Integer.parseInt(req.params(":id"));
        assert id > 0 : "id must be greater 0";

        OperationResult result = classCRUD.getClassById(id);
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

    public static String getClassesByTimeSlotId(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";
        int time_slot_id = Integer.parseInt(req.params(":time_slot_id"));

        OperationResult result = classCRUD.getClassesByTimeSlotId(time_slot_id);
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


    public static String updateClass(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";
        int id = Integer.parseInt(req.params(":id"));
        assert id > 0 : "id must be greater 0";
        int disciplineId = Integer.parseInt(req.queryParams("discipline_id"));
        assert disciplineId > 0 : "discipline_id must be greater 0";
        String classTypeString = req.queryParams("class_type");
        assert classTypeString != null && !classTypeString.isEmpty(): "class_type cannot be null";
        int roomId = Integer.parseInt(req.queryParams("room_id"));
        assert roomId > 0 : "room_id must be greater 0";
        int timeSlotId = Integer.parseInt(req.queryParams("time_slot_id"));
        assert timeSlotId > 0 : "time_slot_id must be greater 0";
        int teacherId = Integer.parseInt(req.queryParams("teacher_id"));
        assert teacherId > 0 : "teacher_id must be greater 0";

        String semiyearStr = req.queryParams("semiyear");
        Semiyear semiyear = null;
        if (semiyearStr != null) {
            try {
                semiyear = Semiyear.valueOf(semiyearStr);
            } catch (Exception e) {
                res.status(400);
                return "{\"error\":\"Invalid semiyear value: " + semiyearStr + "\"}";
            }
        }
        Integer groupId = req.queryParams("group_id") != null ? Integer.parseInt(req.queryParams("group_id")) : null;

        if (disciplineId == 0 || classTypeString == null || roomId == 0 || timeSlotId == 0 || teacherId == 0) {
            res.status(400);
            return "Missing required fields.";
        }

        ClassType classType;
        try {
            classType = ClassType.valueOf(classTypeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            res.status(400);
            return "{\"error\":\"Invalid class type. Please use COURSE, LABORATORY, or SEMINAR.\"}";
        }

        OperationResult result = classCRUD.updateClass(
                id,
                disciplineId,
                classType,
                roomId,
                timeSlotId,
                semiyear,
                groupId,
                teacherId
        );
        assert result != null : "result cannot be null";

        if (result.success) {
            res.status(200);
            return "{\"message\":\"" + result.message + "\"}";
        } else {
            res.status(500);
            return "{\"error\":\"" + result.message + "\"}";
        }
    }


    public static String deleteClass(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";
        int id = Integer.parseInt(req.params(":id"));
        assert id > 0 : "id must be greater 0";

        OperationResult result = classCRUD.deleteClass(id);
        assert result != null : "result cannot be null";

        if (result.success) {
            res.status(201);
            return "{\"message\":\"" + result.message + "\"}";
        } else {
            res.status(500);
            return "{\"error\":\"" + result.message + "\"}";
        }
    }


    public static String getClassesByGroupId(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";
        int groupId = Integer.parseInt(req.params(":groupId"));
        assert groupId > 0 : "group_id must be greater 0";

        OperationResult result = classCRUD.getClassesByGroupId(groupId);
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

    public static String getClassesByRoomId(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        int roomId = Integer.parseInt(req.params(":roomId"));
        assert roomId > 0 : "room_id must be greater 0";

        OperationResult result = classCRUD.getClassesByRoomId(roomId);
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

    public static String getClassesBySemiyear(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        String semiyearStr = req.params(":semiyear");
        assert semiyearStr != null && !semiyearStr.isEmpty(): "semiyear cannot be null";
        if (semiyearStr == null) {
            res.status(400);
            return "{\"error\":\"Semiyear is required and cannot be null.\"}";
        }

        Semiyear semiyear = null;
        if (semiyearStr != null) {
            try {
                semiyear = Semiyear.valueOf(semiyearStr);
            } catch (Exception e) {
                res.status(400);
                return "{\"error\":\"Invalid semiyear value: " + semiyearStr + "\"}";
            }
        }

        OperationResult result = classCRUD.getClassesBySemiyear(semiyear);
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

    public static String getClassesByTeacherId(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        int teacherId = Integer.parseInt(req.params(":teacherId"));
        assert teacherId > 0 : "teacher_id must be greater 0";

        OperationResult result = classCRUD.getClassesByTeacherId(teacherId);
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


    public static String getClassesByDisciplineId(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        int disciplineId;

        try {
            disciplineId = Integer.parseInt(req.params(":disciplineId"));
            assert disciplineId > 0 : "discipline_id must be greater 0";
        } catch (NumberFormatException e) {
            res.status(400);
            return "{\"error\":\"ID invalid pentru disciplină.\"}";
        }

        OperationResult result = classCRUD.getClassesByDisciplineId(disciplineId);
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


    public static String getAllClasses(Request req, Response res) {
        assert req != null : "req cannot be null";
        assert res != null : "res cannot be null";

        OperationResult result = classCRUD.getAllClasses();
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
}

package org.example.timetableassistant.database.handler;
import com.google.gson.Gson;
import org.example.timetableassistant.database.OperationResult;
import org.example.timetableassistant.database.crud.RoomCRUD;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class RoomHandler {
    private static final RoomCRUD roomCRUD = new RoomCRUD();

    public static String createRoom(Request req, Response res) {
        assert req != null : "req must not be null";
        assert res != null : "res must not be null";

        String name = req.queryParams("name");
        int capacity = Integer.parseInt(req.queryParams("capacity"));
        assert capacity > 0 : "capacity must be greater than 0";
        int roomTypeId = Integer.parseInt(req.queryParams("room_type_id"));
        assert roomTypeId > 0 : "room_type_id must be greater than 0";

        if (name == null || roomTypeId <= 0) {
            res.status(400);  // Bad Request
            return "Missing required fields (name, capacity, room_type_id).";
        }

        OperationResult result = roomCRUD.insertRoom(name, capacity, roomTypeId);
        assert result != null : "result must not be null";
        if (result.success) {
            res.status(201);  // Created
            return "{\"message\":\"" + result.message + "\"}";
        } else {
            res.status(500);  // Internal Server Error
            return "{\"error\":\"" + result.message + "\"}";
        }
    }


    public static String getRoomById(Request req, Response res) {
        assert req != null : "req must not be null";
        assert res != null : "res must not be null";
        int id = Integer.parseInt(req.params(":id"));
        assert id > 0 : "id must be greater than 0";
        OperationResult result = roomCRUD.getRoomById(id);
        assert result != null : "result must not be null";
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

    public static String getAllRooms(Request req, Response res) {
        assert req != null : "req must not be null";
        assert res != null : "res must not be null";
        OperationResult result = roomCRUD.getAllRooms();
        assert result != null : "result must not be null";
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


    public static String updateRoom(Request req, Response res) {
        assert req != null : "req must not be null";
        assert res != null : "res must not be null";

        int id = Integer.parseInt(req.params(":id"));
        assert id > 0 : "id must be greater than 0";

        String newName = req.queryParams("name");
        int newCapacity = Integer.parseInt(req.queryParams("capacity"));
        assert newCapacity > 0 : "capacity must be greater than 0";
        int newRoomTypeId = Integer.parseInt(req.queryParams("room_type_id"));
        assert newRoomTypeId > 0 : "room_type_id must be greater than 0";

        if (newName == null || newRoomTypeId <= 0) {
            res.status(400);  // Bad Request
            return "Missing required fields (name, capacity, room_type_id).";
        }

        OperationResult result = roomCRUD.updateRoom(id, newName, newCapacity, newRoomTypeId);
        assert result != null : "result must not be null";
        if (result.success) {
            res.status(200);  // OK
            return "{\"message\":\"" + result.message + "\"}";
        } else {
            res.status(500);  // Internal Server Error
            return "{\"error\":\"" + result.message + "\"}";
        }
    }


    public static String deleteRoom(Request req, Response res) {
        assert req != null : "req must not be null";
        assert res != null : "res must not be null";
        int id = Integer.parseInt(req.params(":id"));
        assert id > 0 : "id must be greater than 0";
        OperationResult result = roomCRUD.deleteRoom(id);
        assert result != null : "result must not be null";
        if (result.success) {
            res.status(200);  // OK
            return "{\"message\":\"" + result.message + "\"}";
        } else {
            res.status(500);  // Internal Server Error
            return "{\"error\":\"" + result.message + "\"}";
        }
    }
}

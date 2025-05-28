package org.example.timetableassistant.service;

import org.example.timetableassistant.model.Room;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URL;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class RoomService {
    private static final String BASE_URL = "http://localhost:4567/db/room";

    public static String createRoom(String name, int capacity, int type) throws Exception {
        assert name != null : "Name must not be null";
        assert capacity > 0 : "Capacity must be greater than 0";
        assert type > 0 : "Type id  must be greater than 0";

        String encodedName = java.net.URLEncoder.encode(name, "UTF-8");
        assert encodedName != null : "encodedName must not be null";

        URL url = new URI(BASE_URL + "?name=" + encodedName + "&capacity=" + capacity+ "&room_type_id=" + type).toURL();
        assert url != null : "URL must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection must not be null";

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            return "Room created successfully.";
        } else {
            throw new Exception("Failed to create room. HTTP error code: " + responseCode);
        }
    }

    public static List<Room> getAllRooms() throws Exception {
        URL url = new URI(BASE_URL + "/get-all").toURL();
        assert url != null : "URL must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection must not be null";

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            assert in != null : "Input stream must not be null";

            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                assert inputLine != null : "inputLine must not be null";
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            assert jsonResponse != null : "JSON response must not be null";

            JSONArray roomsArray = jsonResponse.getJSONArray("message");
            assert roomsArray != null : "Rooms array must not be null";

            List<Room> rooms = new ArrayList<>();
            assert rooms.size() <= roomsArray.length() : "rooms array must have less or equal amount of elements than roomsArray";
            for (int i = 0; i < roomsArray.length(); i++) {
                JSONObject roomObj = roomsArray.getJSONObject(i);
                assert roomObj != null : "roomObj must not be null";

                int id = roomObj.getInt("id");
                assert id > 0 : "id must be greater than 0";

                String name = roomObj.getString("name");
                assert name != null : "name must not be null";

                int typeId = roomObj.getInt("room_type_id");
                assert typeId > 0 : "type id must be greater than 0";

                RoomTypeService roomTypeService = new RoomTypeService();
                assert roomTypeService != null : "RoomTypeService must not be null";

                String typeName = roomTypeService.getRoomTypeById(typeId).getName();
                assert typeName != null && !typeName.isEmpty(): "type name must not be null";

                int capacity = roomObj.getInt("capacity");
                assert capacity > 0 : "capacity must be greater than 0";

                Room room = new Room(id, name, typeName, capacity);
                assert room != null : "Room must not be null";
                rooms.add(room);
                assert rooms.size() <= roomsArray.length() : "rooms array must have less or equal amount of elements than roomsArray";
            }
            return rooms;
        } else {
            throw new Exception("Failed to get rooms. HTTP error code: " + responseCode);
        }
    }

    public String editRoom(int id, String name, int capacity, int type) throws Exception {
        assert id > 0 : "id must be greater than 0";
        assert name != null : "Name must not be null";
        assert capacity > 0 : "Capacity must be greater than 0";
        assert type > 0 : "Type id  must be greater than 0";

        String encodedName = java.net.URLEncoder.encode(name, "UTF-8");
        assert encodedName != null : "encodedName must not be null";

        URL url = new URI(BASE_URL + "/" + id + "?name=" + encodedName + "&capacity=" + capacity + "&room_type_id=" + type).toURL();
        assert url != null : "URL must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "Room updated successfully.";
        } else {
            throw new Exception("Failed to update room. HTTP error code: " + responseCode);
        }
    }

    public String deleteRoom(int id) throws Exception {
        assert id > 0 : "id must be greater than 0";

        URL url = new URI(BASE_URL + "/" + id).toURL();
        assert url != null : "URL must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection must not be null";

        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "Room deleted successfully.";
        } else {
            throw new Exception("Failed to delete room. HTTP error code: " + responseCode);
        }
    }
    
}

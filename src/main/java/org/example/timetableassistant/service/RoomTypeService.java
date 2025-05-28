package org.example.timetableassistant.service;
import org.example.timetableassistant.model.RoomType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeService {
    private static final String BASE_URL = "http://localhost:4567/db/roomType";

    public List<RoomType> getAllRoomTypes() {
        try {
            URL url = new URI(BASE_URL + "/get-all").toURL();
            assert url != null : "URL must not be null";

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            assert conn != null : "Connection must not be null";

            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (java.util.Scanner scanner = new java.util.Scanner(conn.getInputStream())) {
                    scanner.useDelimiter("\\A");
                    String json = scanner.hasNext() ? scanner.next() : "";
                    assert json != null : "JSON must not be null";

                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(json);
                    assert root != null : "Root node must not be null";

                    JsonNode messageNode = root.get("message");
                    assert messageNode != null : "Message node must not be null";

                    List<RoomType> roomTypes = new ArrayList<>();
                    if (messageNode != null && messageNode.isArray()) {
                        assert messageNode.size() <= roomTypes.size() : "messageNode size must be less or equal to roomTypes size";
                        for (JsonNode node : messageNode) {
                            RoomType roomType = new RoomType();
                            roomType.setName(node.get("name").asText());
                            assert roomType.getName() != null : "Room type name must not be null";
                            // Set id if present
                            if (node.has("id")) {
                                // RoomType class needs a setId method for this to work
                                try {
                                    java.lang.reflect.Method setId = RoomType.class.getDeclaredMethod("setId", int.class);
                                    setId.invoke(roomType, node.get("id").asInt());
                                } catch (NoSuchMethodException e) {
                                    // Ignore if setter doesn't exist
                                }
                            }
                            assert roomType != null : "Room type must not be null";
                            roomTypes.add(roomType);
                            assert messageNode.size() <= roomTypes.size() : "messageNode size must be less or equal to roomTypes size";

                        }
                    }
                    return roomTypes;
                }
            } else {
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching room types", e);
        }
    }

    public RoomType getRoomTypeById(int id) {
        assert id > 0 : "Id must be greater than 0";
        try {
            URL url = new URI(BASE_URL + "/" + id).toURL();
            assert url != null : "URL must not be null";

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            assert conn != null : "Connection must not be null";

            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (java.util.Scanner scanner = new java.util.Scanner(conn.getInputStream())) {
                    scanner.useDelimiter("\\A");
                    String json = scanner.hasNext() ? scanner.next() : "";
                    assert json != null : "json must not be null";

                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(json);
                    assert root != null : "Root node must not be null";

                    JsonNode messageNode = root.get("message");
                    assert messageNode != null : "Message node must not be null";

                    if (messageNode != null && messageNode.isObject()) {
                        RoomType roomType = new RoomType();
                        roomType.setName(messageNode.get("name").asText());
                        assert roomType.getName() != null : "Room type name must not be null";
                        // Set id if present
                        if (messageNode.has("id")) {
                            // RoomType class needs a setId method for this to work
                            try {
                                java.lang.reflect.Method setId = RoomType.class.getDeclaredMethod("setId", int.class);
                                setId.invoke(roomType, messageNode.get("id").asInt());
                            } catch (NoSuchMethodException e) {
                                // Ignore if setter doesn't exist
                            }
                        }
                        assert roomType != null : "Room type must not be null";
                        return roomType;
                    }
                }
            } else {
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching room type by ID", e);
        }
        return null;
    }

//    public String createRoomTypes(String name) throws Exception {
//        URL url = new URI(BASE_URL + "?name=" + name).toURL();
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("POST");
//        connection.setDoOutput(true);
//
//        int responseCode = connection.getResponseCode();
//        if (responseCode == HttpURLConnection.HTTP_CREATED) {
//            return "RoomType created successfully.";
//        } else {
//            throw new Exception("Failed to create room. HTTP error code: " + responseCode);
//        }
//    }
}

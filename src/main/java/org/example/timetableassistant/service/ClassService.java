package org.example.timetableassistant.service;

import org.example.timetableassistant.database.crud.ClassType;
import org.example.timetableassistant.model.Class;
import org.example.timetableassistant.model.Semiyear;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ClassService {
    private static final String BASE_URL = "http://localhost:4567/db/class";

    public static String createClass(int disciplineId, ClassType classType, int roomId, int timeSlotId, Semiyear semiyear, Integer groupId, int teacherId) throws Exception {
        assert disciplineId > 0 : "disciplineId must be greater than 0";
        assert classType != null : "classType must not be null";
        assert roomId > 0 : "roomId must be greater than 0";
        assert timeSlotId > 0 : "timeSlotId must be greater than 0";
        assert groupId > 0 : "groupId must be greater than 0";
        assert teacherId > 0 : "teacherId must be greater than 0";
        assert semiyear != null : "semiyear must not be null";

        AssistantService.verifyClassCreation(roomId, timeSlotId, semiyear, groupId, teacherId);

        if (classType == classType.COURSE){
            groupId = null;
        } else{
            semiyear = null;
        }

        String query = String.format("?discipline_id=%d&class_type=%s&room_id=%d&time_slot_id=%d&teacher_id=%d",
                disciplineId, URLEncoder.encode(classType.name(), "UTF-8"), roomId, timeSlotId, teacherId);
        assert query != null && !query.isEmpty(): "query must not be null";

        if ( semiyear != null ) {
            query += "&semiyear=" + URLEncoder.encode(semiyear.name(), "UTF-8");
        }
        if (groupId != null) {
            query += String.format("&group_id=%d", groupId);
        }

        URL url = new URI(BASE_URL + query).toURL();
        assert url != null: "url must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null: "connection must not be null";

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            return "Class created successfully";
        } else {
            throw new Exception("Failed to create class. HTTP error code: " + responseCode);
        }
    }

    public static List<Class> getAllClasses() throws Exception {
        URL url = new URI(BASE_URL + "/get-all").toURL();
        assert url != null: "url must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null: "connection must not be null";

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            assert reader != null: "reader must not be null";
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                assert line != null: "line must not be null";
                response.append(line);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            assert jsonResponse != null: "jsonResponse must not be null";

            JSONArray classesArray = jsonResponse.getJSONArray("message");
            assert classesArray != null: "classesArray must not be null";

            List<Class> classes = new ArrayList<>();
            assert classes.size() <= classesArray.length() : "classes.size() must be less than classesArray.length()";

            for (int i = 0; i < classesArray.length(); i++) {
                JSONObject classObject = classesArray.getJSONObject(i);
                assert classObject != null: "classObject must not be null";
                Class cls = new Class(
                        classObject.getInt("id"),
                        classObject.getInt("discipline_id"),
                        ClassType.valueOf(classObject.getString("class_type")),
                        classObject.getInt("room_id"),
                        classObject.getInt("time_slot_id"),
                        classObject.opt("group_id") instanceof Integer ? classObject.getInt("group_id") : null,
                        classObject.optString("semiyear", null) != null ? Semiyear.valueOf(classObject.getString("semiyear")) : null,
                        classObject.getInt("teacher_id")
                );
                assert cls != null: "cls must not be null";

                classes.add(cls);
                assert classes.size() <= classesArray.length() : "classes.size() must be less than classesArray.length()";

            }
            return classes;
        } else {
            throw new Exception("Failed to get all classes. HTTP error code: " + responseCode);
        }
    }

    public static String updateClass(int id, int disciplineId, ClassType classType, int roomId, int timeSlotId, Semiyear semiyear, Integer groupId, int teacherId) throws Exception {
        assert id > 0 : "id must be greater than 0";
        assert disciplineId > 0 : "disciplineId must be greater than 0";
        assert classType != null : "classType must not be null";
        assert roomId > 0 : "roomId must be greater than 0";
        assert timeSlotId > 0 : "timeSlotId must be greater than 0";
        assert groupId != null : "groupId must be greater than 0";
        assert teacherId > 0 : "teacherId must be greater than 0";
        assert semiyear != null : "semiyear must be greater than 0";

        String query = String.format("?discipline_id=%d&class_type=%s&room_id=%d&time_slot_id=%d&teacher_id=%d",
                disciplineId, URLEncoder.encode(classType.name(), "UTF-8"), roomId, timeSlotId, teacherId);
        assert query != null && !query.isEmpty(): "query must not be null";

        if (semiyear != null) {
            query += "&semiyear=" + URLEncoder.encode(semiyear.name(), "UTF-8");
        }
        if (groupId != null) {
            query += "&group_id=" + groupId;
        }

        URL url = new URI(BASE_URL + "/" + id + query).toURL();
        assert url != null: "url must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null: "connection must not be null";

        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "Class updated successfully";
        } else {
            throw new Exception("Failed to update class. HTTP error code: " + responseCode);
        }
    }

    public static String deleteClass(int id) throws Exception {
        assert id > 0 : "id must be greater than 0";

        URL url = new URI(BASE_URL + "/" + id).toURL();
        assert url != null: "url must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null: "connection must not be null";
        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "Class deleted successfully";
        } else {
            throw new Exception("Failed to delete class. HTTP error code: " + responseCode);
        }
    }

    public static List<Class> getByTimeSlotId(int timeSlotId) throws Exception {
        assert timeSlotId > 0 : "timeSlotId must be greater than 0";

        URL url = new URI("http://localhost:4567/db/classes/get-by-time-slot-id/" + timeSlotId).toURL();
        assert url != null: "url must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null: "connection must not be null";

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            assert reader != null: "reader must not be null";

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                assert line != null: "line must not be null";
                response.append(line);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            assert jsonResponse != null: "jsonResponse must not be null";

            JSONArray classesArray = jsonResponse.getJSONArray("message");
            assert classesArray != null: "classesArray must not be null";

            List<Class> classes = new ArrayList<>();
            assert classes.size() <= classesArray.length() : "classes.size() must be less than classesArray.length()";

            for (int i = 0; i < classesArray.length(); i++) {
                JSONObject classObject = classesArray.getJSONObject(i);
                assert classObject != null: "classObject must not be null";
                Semiyear semiyear = null;
                if (classObject.has("semiyear") && !classObject.isNull("semiyear")) {
                    semiyear = Semiyear.valueOf(classObject.getString("semiyear"));
                    assert semiyear != null: "semiyear must not be null";
                }
                Class cls = new Class(
                    classObject.getInt("id"),
                    classObject.getInt("discipline_id"),
                    ClassType.valueOf(classObject.getString("class_type")),
                    classObject.getInt("room_id"),
                    classObject.getInt("time_slot_id"),
                    classObject.opt("group_id") instanceof Integer ? classObject.getInt("group_id") : null,
                    semiyear,
                    classObject.getInt("teacher_id")
                );
                assert cls != null: "cls must not be null";
                classes.add(cls);
                assert classes.size() <= classesArray.length() : "classes.size() must be less than classesArray.length()";

            }
            return classes;
        } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
            return new ArrayList<>();
        } else {
            throw new Exception("Failed to get classes by time slot id. HTTP error code: " + responseCode);
        }
    }
}

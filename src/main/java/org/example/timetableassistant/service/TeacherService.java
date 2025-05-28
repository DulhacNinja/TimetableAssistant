package org.example.timetableassistant.service;

import org.example.timetableassistant.model.Discipline;
import org.example.timetableassistant.model.Teacher;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class TeacherService {
    private static final String BASE_URL = "http://localhost:4567/db/teacher";

    public static String createTeacher(String name) throws Exception{
        assert name != null && !name.isEmpty() : "Name must not be null or empty";

        String encodedName = java.net.URLEncoder.encode(name, "UTF-8");
        assert encodedName != null && !encodedName.isEmpty() : "Encoded name must not be null or empty";

        URL url = new URI(BASE_URL + "?name=" + encodedName).toURL();
        assert url != null : "URL must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection must not be null";

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            return "Teacher created successfully.";
        } else {
            throw new Exception("Failed to create teacher. HTTP error code: " + responseCode);
        }
    }

    public static List<Teacher> getAllTeachers() throws Exception{
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
            assert response != null : "Response must not be null";
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                assert inputLine != null : "Input line must not be null";
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            assert jsonResponse != null : "JSON response must not be null";

            JSONArray teachersArray = jsonResponse.getJSONArray("message");
            assert teachersArray != null : "Teachers array must not be null";

            List<Teacher> teachers = new ArrayList<>();
            assert teachers.size() <= teachersArray.length() : "teachers array must have less or same amount of elements as teachersArray";

            for (int i = 0; i < teachersArray.length(); i++) {
                JSONObject roomObj = teachersArray.getJSONObject(i);
                assert roomObj != null : "Room object must not be null";

                int id = roomObj.getInt("id");
                assert id > 0 : "Room id must be greater than 0";

                String name = roomObj.getString("name");
                assert name != null && !name.isEmpty() : "Name must not be null or empty";

                Teacher newTeacher = new Teacher(id, name);
                assert newTeacher != null : "New Teacher must not be null";

                teachers.add(newTeacher);
                assert teachers.size() <= teachersArray.length() : "teachers array must have less or same amount of elements as teachersArray";
            }
            return teachers;
        } else {
            throw new Exception("Failed to get teachers. HTTP error code: " + responseCode);
        }
    }

    public static Teacher getTeacherById(int id) throws Exception{
        URL url = new URI(BASE_URL + "/get-by-id/" + id).toURL();
        assert url != null : "URL must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection must not be null";
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            assert in != null : "Input stream must not be null";

            StringBuilder response = new StringBuilder();
            assert response != null : "Response must not be null";
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                assert inputLine != null : "Input line must not be null";
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            assert jsonResponse != null : "jsonResponse shouldn't be null";

            Object message = jsonResponse.get("message");
            assert message != null : "Message must not be null";

            if (message instanceof JSONArray) {
                JSONArray teachersArray = (JSONArray) message;
                assert teachersArray != null : "Teachers array must not be null";
                for (int i = 0; i < teachersArray.length(); i++) {
                    JSONObject teacherObj = teachersArray.getJSONObject(i);
                    assert teacherObj != null : "Teacher object must not be null";
                    int idTeacher = teacherObj.getInt("id");
                    assert idTeacher > 0 : "Teacher id must be greater than 0";
                    String name = teacherObj.getString("name");
                    assert name != null && !name.isEmpty() : "Name must not be null or empty";
                    if (idTeacher == id) {
                        Teacher teacher = new Teacher(id, name);
                        assert teacher != null : "New Teacher must not be null";
                        return teacher;
                    }
                }
            } else if (message instanceof JSONObject) {
                JSONObject teacherObj = (JSONObject) message;
                assert teacherObj != null : "Teacher object must not be null";
                int idTeacher = teacherObj.getInt("id");
                assert idTeacher > 0 : "Teacher id must be greater than 0";
                String name = teacherObj.getString("name");
                assert name != null && !name.isEmpty() : "Name must not be null or empty";
                if (idTeacher == id) {
                    Teacher teacher = new Teacher(id, name);
                    assert teacher != null : "New Teacher must not be null";
                    return teacher;
                }
            } else {
                throw new Exception("Unexpected message format in response.");
            }
        } else {
            throw new Exception("Failed to get teacher. HTTP error code: " + responseCode);
        }
        return null;
    }

    public String editTeacher(int id, String name) throws Exception{
        assert name != null && !name.isEmpty() : "Name must not be null or empty";
        assert id > 0 : "Teacher id must be greater than 0";

        String encodedName = java.net.URLEncoder.encode(name, "UTF-8");
        assert encodedName != null && !encodedName.isEmpty() : "Encoded name must not be null or empty";

        URL url = new URI(BASE_URL + "/" + id + "?name=" + encodedName).toURL();
        assert url != null : "URL must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection must not be null";

        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "Teacher updated successfully.";
        } else {
            throw new Exception("Failed to update teacher. HTTP error code: " + responseCode);
        }
    }

    public String deleteTeacher(int id) throws Exception{
        assert id > 0 : "Teacher id must be greater than 0";

        URL url = new URI(BASE_URL + "/" + id).toURL();
        assert url != null : "URL must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection must not be null";

        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "Teacher deleted successfully.";
        } else {
            throw new Exception("Failed to delete teacher. HTTP error code: " + responseCode);
        }
    }

}

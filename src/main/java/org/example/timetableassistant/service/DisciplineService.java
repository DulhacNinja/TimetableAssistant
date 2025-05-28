package org.example.timetableassistant.service;

import org.example.timetableassistant.model.Discipline;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class DisciplineService {
    private static final String BASE_URL = "http://localhost:4567/db/discipline";

    public static String createDiscipline(String name) throws Exception {
        assert name != null && !name.isEmpty() : "Name cannot be null nor empty";

        String encodedName = java.net.URLEncoder.encode(name, "UTF-8");
        assert encodedName != null && !encodedName.isEmpty() : "Name cannot be null nor empty";

        URL url = new URI(BASE_URL + "?name=" + encodedName).toURL();
        assert url != null : "URL cannot be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection cannot be null";

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            return "Discipline created successfully.";
        } else {
            throw new Exception("Failed to create discipline. HTTP error code: " + responseCode);
        }
    }

    public static Discipline getDisciplineById(int id) throws Exception {
        assert id > 0: "ID cannot be negative";

        URL url = new URI(BASE_URL + "/" + id).toURL();
        assert url != null : "URL cannot be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection cannot be null";

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            assert in != null : "Input stream cannot be null";

            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                assert inputLine != null : "inputLine cannot be null";
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            assert jsonResponse != null : "Response cannot be null";

            Object message = jsonResponse.get("message");
            assert message != null : "Message cannot be null";

            if (message instanceof JSONArray disciplinesArray) {
                for (int i = 0; i < disciplinesArray.length(); i++) {
                    JSONObject roomObj = disciplinesArray.getJSONObject(i);
                    assert roomObj != null : "Room object cannot be null";

                    int idDiscipline = roomObj.getInt("id");
                    assert idDiscipline > 0 : "ID cannot be negative";

                    if (idDiscipline == id) {
                        String name = roomObj.getString("name");
                        assert name != null && !name.isEmpty() : "Name cannot be null nor empty";

                        Discipline discipline = new Discipline(id, name);
                        assert discipline != null : "Discipline cannot be null";

                        return discipline;
                    }
                }
            } else if (message instanceof JSONObject disciplineObj) {
                int idDiscipline = disciplineObj.getInt("id");
                assert idDiscipline > 0 : "ID cannot be negative";

                String name = disciplineObj.getString("name");
                assert name != null && !name.isEmpty() : "Name cannot be null nor empty";

                if (idDiscipline == id) {
                    Discipline discipline = new Discipline(id, name);
                    assert discipline != null : "Discipline cannot be null";

                    return discipline;
                }
            } else {
                throw new Exception("Unexpected message format in response.");
            }
        } else {
            throw new Exception("Failed to get discipline. HTTP error code: " + responseCode);
        }
        return null;
    }

    public static List<Discipline> getAllDisciplines() throws Exception {
        URL url = new URI(BASE_URL + "/get-all").toURL();
        assert url != null : "URL cannot be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection cannot be null";

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            assert in != null : "Input stream cannot be null";

            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                assert inputLine != null : "inputLine cannot be null";
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            assert jsonResponse != null : "Response cannot be null";

            JSONArray disciplinesArray = jsonResponse.getJSONArray("message");
            assert disciplinesArray != null : "Discipline array cannot be null";

            List<Discipline> disciplines = new ArrayList<>();
            assert disciplines.size() <= disciplinesArray.length() : "disciplines must have less or equal amount of elements as disciplinesArray";
            for (int i = 0; i < disciplinesArray.length(); i++) {
                JSONObject disciplineObj = disciplinesArray.getJSONObject(i);
                assert disciplineObj != null : "Discipline object cannot be null";

                int id = disciplineObj.getInt("id");
                assert id > 0 : "ID cannot be negative";

                String name = disciplineObj.getString("name");
                assert name != null && !name.isEmpty() : "Name cannot be null nor empty";

                disciplines.add(new Discipline(id, name));
                assert disciplines.size() <= disciplinesArray.length() : "disciplines must have less or equal amount of elements as disciplinesArray";
            }
            return disciplines;
        } else {
            throw new Exception("Failed to get all disciplines. HTTP error code: " + responseCode);
        }
    }

    public static String editDiscipline(int id, String newName) throws Exception {
        assert id > 0 : "ID cannot be negative";
        assert newName != null && !newName.isEmpty() : "New name cannot be null nor empty";

        String encodedName = java.net.URLEncoder.encode(newName, "UTF-8");
        assert encodedName != null && !encodedName.isEmpty() : "New name cannot be null nor empty";

        URL url = new URI(BASE_URL + "/" + id + "?name=" + encodedName).toURL();
        assert url != null : "URL cannot be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection cannot be null";

        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "Discipline updated successfully.";
        } else {
            throw new Exception("Failed to update discipline. HTTP error code: " + responseCode);
        }
    }

    public static String deleteDiscipline(int id) throws Exception {
        assert id > 0 : "ID cannot be negative";

        URL url = new URI(BASE_URL + "/" + id).toURL();
        assert url != null : "URL cannot be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection cannot be null";

        connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "Discipline deleted successfully.";
        } else {
            throw new Exception("Failed to delete discipline. HTTP error code: " + responseCode);
        }
    }
}

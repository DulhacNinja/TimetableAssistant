package org.example.timetableassistant.service;

import org.example.timetableassistant.model.Group;
import org.example.timetableassistant.model.Semiyear;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GroupService {
    private static final String BASE_URL = "http://localhost:4567/db/group";

    public static String createGroup(int number, Semiyear semiyear) throws Exception {
        assert number > 0 : "Number must be positive";
        assert semiyear != null : "Semiyear must not be null";

        String encodedSemiyear = java.net.URLEncoder.encode(semiyear.getValue(), "UTF-8");
        assert encodedSemiyear != null : "encodedSemiyear must not be null";

        URL url = new URI(BASE_URL + "?number=" + number + "&semiyear=" + encodedSemiyear).toURL();
        assert url != null : "URL must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection must not be null";

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            return "Group created successfully";
        } else {
            throw new Exception("Failed to create group. HTTP error code: " + responseCode);
        }
    }

    public static List<Group> getAllGroups() throws Exception {
        URL url = new URI(BASE_URL + "/get-all").toURL();
        assert url != null : "URL must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection must not be null";

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            assert reader != null : "Reader stream must not be null";

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                assert line != null : "Line must not be null";
                response.append(line);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            assert jsonResponse != null : "JSON response must not be null";

            JSONArray groupsArray = jsonResponse.getJSONArray("message");
            assert groupsArray != null : "Groups array must not be null";

            List<Group> groups = new ArrayList<Group>();

            assert groups.size() <= groupsArray.length() : "groups must have less or equal amount of elements than groupsArray";
            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject groupObject = groupsArray.getJSONObject(i);
                assert groupObject != null : "GroupObject must not be null";

                int groupId = groupObject.getInt("id");
                assert groupId > 0 : "GroupId must be positive";

                int groupNumber = groupObject.getInt("number");
                assert groupNumber > 0 : "GroupNumber must be positive";

                String groupSemiyear = groupObject.getString("semiyear");
                assert groupSemiyear != null : "Semiyear must not be null";

                Group group = new Group(groupId, groupNumber, Semiyear.fromString(groupSemiyear));
                assert group != null : "Group must not be null";
                groups.add(group);
                assert groups.size() <= groupsArray.length() : "groups must have less or equal amount of elements than groupsArray";

            }
            return groups;
        } else {
            throw new Exception("Failed to get all groups. HTTP error code: " + responseCode);
        }
    }

    public static String updateGroup(int groupId, int number, Semiyear semiyear) throws Exception {
        assert groupId > 0 : "GroupId must be positive";
        assert number > 0 : "Number must be positive";
        assert semiyear != null : "Semiyear must not be null";

        String encodedSemiyear = java.net.URLEncoder.encode(semiyear.getValue(), "UTF-8");
        assert encodedSemiyear != null : "encodedSemiyear must not be null";

        URL url = new URI(BASE_URL + "/" + groupId + "?number=" + number + "&semiyear=" + encodedSemiyear).toURL();
        assert url != null : "URL must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection must not be null";

        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "Group updated successfully";
        } else {
            throw new Exception("Failed to update group. HTTP error code: " + responseCode);
        }
    }

    public static String deleteGroup(int groupId) throws Exception {
        assert groupId > 0 : "GroupId must be positive";

        URL url = new URI(BASE_URL + "/" + groupId).toURL();
        assert url != null : "URL must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection must not be null";

        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "Group deleted successfully";
        } else {
            throw new Exception("Failed to delete group. HTTP error code: " + responseCode);
        }
    }
}
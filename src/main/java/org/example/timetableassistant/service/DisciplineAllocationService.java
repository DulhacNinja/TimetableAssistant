package org.example.timetableassistant.service;

import org.example.timetableassistant.database.crud.ClassType;
import org.example.timetableassistant.model.Discipline;
import org.example.timetableassistant.model.DisciplineAllocation;
import org.example.timetableassistant.model.Teacher;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class DisciplineAllocationService {
    private static final String BASE_URL = "http://localhost:4567/db/discipline-allocation";

    public static String createDisciplineAllocation(int disciplineId, int teacherId, int classTypeId, int hoursPerWeek) throws Exception {
        assert disciplineId > 0 : "disciplineID must be positive";
        assert teacherId > 0 : "teacherID must be positive";
        assert classTypeId > 0 : "classTypeID must be positive";
        assert hoursPerWeek > 0 : "hoursPerWeek must be positive";

        URL url = new URI(BASE_URL + "?discipline_id=" + disciplineId +
                                        "&teacher_id=" + teacherId +
                                        "&class_type_id=" + classTypeId +
                                        "&hours_per_week=" + hoursPerWeek)
                        .toURL();
        assert url != null : "URL must not be null";

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        assert connection != null : "Connection must not be null";

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            return "Discipline Allocation created successfully.";
        } else {
            throw new Exception("Failed to create discipline allocation. HTTP error code: " + responseCode);
        }
    }

    public static List<DisciplineAllocation> getAllDisciplineAllocations() throws Exception {
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
                assert inputLine != null : "Input stream must not be null";
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            assert jsonResponse != null : "Response must not be null";

            JSONArray daArray = jsonResponse.getJSONArray("message");
            assert daArray != null : "Message array must not be null";

            List<DisciplineAllocation> das = new ArrayList<>();
            assert das.size() <= daArray.length() : "das must have less or equal amount of elements than daArray";
            for (int i = 0; i < daArray.length(); i++) {
                JSONObject roomObj = daArray.getJSONObject(i);
                assert roomObj != null : "Room object must not be null";

                int id = roomObj.getInt("id");
                assert id > 0 : "id must be positive";

                Discipline discipline = DisciplineService.getDisciplineById(roomObj.getInt("discipline_id"));
                assert discipline != null : "Discipline must not be null";

                Teacher teacher = TeacherService.getTeacherById(roomObj.getInt("teacher_id"));
                assert teacher != null : "Teacher must not be null";

                ClassType classType = ClassType.fromInt(roomObj.getInt("class_type_id"));
                assert classType != null : "ClassType must not be null";

                DisciplineAllocation d = new DisciplineAllocation(id, discipline, teacher, classType);
                assert d != null : "Discipline allocation must not be null";

                das.add(d);
                assert das.size() <= daArray.length() : "das must have less or equal amount of elements than daArray";
            }
            return das;
        } else {
            throw new Exception("Failed to get teachers. HTTP error code: " + responseCode);
        }
    }

    public static List<DisciplineAllocation> getByTeacherId(int id) throws Exception {
        assert id > 0 : "id must be positive";

        URL url = new URI(BASE_URL + "/get-by-teacher-id/" + id).toURL();
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
            assert jsonResponse != null : "Response must not be null";

            JSONArray daArray = jsonResponse.getJSONArray("message");
            assert daArray != null : "Message array must not be null";

            List<DisciplineAllocation> das = new ArrayList<>();
            assert das.size() <= daArray.length() : "das must have less or equal amount of elements than daArray";

            for (int i = 0; i < daArray.length(); i++) {
                JSONObject roomObj = daArray.getJSONObject(i);
                assert roomObj != null : "Room object must not be null";

                int das_id = roomObj.getInt("id");
                assert das_id > 0 : "id must be positive";

                Discipline discipline = DisciplineService.getDisciplineById(roomObj.getInt("discipline_id"));
                assert discipline != null : "Discipline must not be null";

                Teacher teacher = TeacherService.getTeacherById(roomObj.getInt("teacher_id"));
                assert teacher != null : "Teacher must not be null";

                ClassType classType = ClassType.valueOf(roomObj.getString("class_type"));
                assert classType != null : "ClassType must not be null";

                DisciplineAllocation d = new DisciplineAllocation(id, discipline, teacher, classType);
                assert d != null : "Discipline allocation must not be null";

                das.add(d);
                assert das.size() <= daArray.length() : "das must have less or equal amount of elements than daArray";

            }
            return das;
        } else {
            throw new Exception("Failed to get teachers. HTTP error code: " + responseCode);
        }
    }

    public static List<DisciplineAllocation> getByDisciplineId(int id) throws Exception {
        assert id > 0 : "id must be positive";

        URL url = new URI(BASE_URL + "/get-by-discipline-id/" + id).toURL();
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
            assert jsonResponse != null : "Response must not be null";

            JSONArray daArray = jsonResponse.getJSONArray("message");
            assert daArray != null : "Message array must not be null";

            List<DisciplineAllocation> das = new ArrayList<>();
            assert das.size() <= daArray.length() : "das must have less or equal amount of elements than daArray";

            for (int i = 0; i < daArray.length(); i++) {
                JSONObject roomObj = daArray.getJSONObject(i);
                assert roomObj != null : "Room object must not be null";

                int das_id = roomObj.getInt("id");
                assert das_id > 0 : "id must be positive";

                Discipline discipline = DisciplineService.getDisciplineById(roomObj.getInt("discipline_id"));
                assert discipline != null : "Discipline must not be null";

                Teacher teacher = TeacherService.getTeacherById(roomObj.getInt("teacher_id"));
                assert teacher != null : "Teacher must not be null";

                ClassType classType = ClassType.valueOf(roomObj.getString("class_type"));
                assert classType != null : "ClassType must not be null";

                DisciplineAllocation d = new DisciplineAllocation(id, discipline, teacher, classType);
                assert d != null : "Discipline allocation must not be null";

                das.add(d);
                assert das.size() <= daArray.length() : "das must have less or equal amount of elements than daArray";

            }
            return das;
        } else {
            throw new Exception("Failed to get teachers. HTTP error code: " + responseCode);
        }
    }

    public static void deleteAllocation(int id) {
        assert id > 0 : "id must be positive";
        try {
            URL url = new URI(BASE_URL + "/" + id).toURL();
            assert url != null : "URL must not be null";

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            assert connection != null : "Connection must not be null";

            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Discipline Allocation deleted successfully.");
            } else {
                System.out.println("Failed to delete Discipline Allocation. HTTP error code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

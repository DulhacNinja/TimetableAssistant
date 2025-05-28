package org.example.timetableassistant.service;

import org.example.timetableassistant.database.OperationResult;
import org.example.timetableassistant.database.crud.TimeSlotCRUD;
import org.example.timetableassistant.model.TimeSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimeSlotService {
    private static final TimeSlotCRUD crud = new TimeSlotCRUD();

    public static List<TimeSlot> getAllTimeSlots() {
        OperationResult result = crud.getAllTimeSlots();
        assert result != null : "OperationResult should not be null";

        List<TimeSlot> slots = new ArrayList<>();

        if (result.success && result.message instanceof List<?>) {
            List<?> rawList = (List<?>) result.message;
            assert rawList != null : "Raw list should not be null";
            assert slots.size() <= rawList.size() : "Slots list should have less or the same amount of elements as rawList";

            for (Object obj : rawList) {
                assert obj instanceof Map : "Each element should be a Map";
                if (obj instanceof Map) {

                    Map<?, ?> map = (Map<?, ?>) obj;
                    int id = (int) map.get("id");
                    String day = (String) map.get("day_of_week");
                    String start = (String) map.get("start_time");
                    String end = (String) map.get("end_time");

                    assert id > 0 : "ID must be an Integer";
                    assert day instanceof String : "Day must be a String";
                    assert start instanceof String : "Start time must be a String";
                    assert end instanceof String : "End time must be a String";

                    slots.add(new TimeSlot(id, day, start, end));
                    assert slots.size() <= rawList.size() : "Slots list should have less or the same amount of elements as rawList";
                }
            }
        }
        assert slots != null : "Returned slots list should not be null";
        return slots;
    }
}

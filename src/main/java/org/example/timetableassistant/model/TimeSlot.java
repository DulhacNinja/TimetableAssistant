package org.example.timetableassistant.model;

public class TimeSlot {
    private final int id;
    private final String dayOfWeek;
    private final String startTime;
    private final String endTime;

    public TimeSlot(int id, String dayOfWeek, String startTime, String endTime) {
        assert id > 0 : "id should be greater than 0";
        assert dayOfWeek != null && !dayOfWeek.isEmpty(): "dayOfWeek should not be null nor empty";
        assert startTime != null && !startTime.isEmpty(): "startTime should not be null nor empty";
        assert endTime != null && !endTime.isEmpty(): "endTime should not be null nor empty";

        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        assertCurrentState();
        return id;
    }

    public String getDayOfWeek() {
        assertCurrentState();
        return dayOfWeek;
    }

    public String getStartTime() {
        assertCurrentState();
        return startTime;
    }

    public String getEndTime() {
        assertCurrentState();
        return endTime;
    }

    @Override
    public String toString() {
        assertCurrentState();
        return dayOfWeek + " " + startTime + "-" + endTime;
    }

    public void assertCurrentState(){
        assert this.id > 0 : "id should be greater than 0";
        assert this.dayOfWeek != null && !this.dayOfWeek.isEmpty(): "dayOfWeek should not be null nor empty";
        assert this.startTime != null && !this.startTime.isEmpty(): "startTime should not be null nor empty";
        assert this.endTime != null && !this.endTime.isEmpty(): "endTime should not be null nor empty";
    }
}

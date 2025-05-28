package org.example.timetableassistant.model;

public class ScheduleEntry {
    private String day, time, subject, teacher, room, group, classType;

    public ScheduleEntry(String day, String time, String subject, String teacher, String room, String group, String classType) {
        assert day != null && !day.isEmpty() : "day must not be null nor empty";
        assert time != null && !time.isEmpty() : "time must not be null nor empty";
        assert subject != null && !subject.isEmpty() : "subject must not be null nor empty";
        assert room != null && !room.isEmpty() : "room must not be null nor empty";
        assert group != null && !group.isEmpty() : "group must not be null nor empty";
        assert classType != null && !classType.isEmpty() : "classType must not be null nor empty";
        assert teacher != null && !teacher.isEmpty() : "teacher must not be null nor empty";

        this.day = day;
        this.time = time;
        this.subject = subject;
        this.teacher = teacher;
        this.room = room;
        this.group = group;
        this.classType = classType;
    }

    public String getDay() {
        assertCurrentState();
        return day; }
    public String getTime() {
        assertCurrentState();
        return time; }
    public String getSubject() {
        assertCurrentState();
        return subject; }
    public String getTeacher() {
        assertCurrentState();
        return teacher; }
    public String getRoom() {
        assertCurrentState();
        return room; }
    public String getGroup() {
        assertCurrentState();
        return group; }
    public String getClassType() {
        assertCurrentState();
        return classType; }

    public void assertCurrentState(){
        assert day != null && !day.isEmpty() : "day must not be null nor empty";
        assert time != null && !time.isEmpty() : "time must not be null nor empty";
        assert subject != null && !subject.isEmpty() : "subject must not be null nor empty";
        assert room != null && !room.isEmpty() : "room must not be null nor empty";
        assert group != null && !group.isEmpty() : "group must not be null nor empty";
        assert classType != null && !classType.isEmpty() : "classType must not be null nor empty";
        assert teacher != null && !teacher.isEmpty() : "teacher must not be null nor empty";
    }
}
package org.example.timetableassistant.model;

import org.example.timetableassistant.database.crud.ClassType;

public class Class {
    private int classId;
    private int disciplineId;
    private ClassType classType;
    private int roomId;
    private int timeSlotId;
    private int teacherId;
    private Semiyear semiyear;
    private Integer groupId;

    public Class(int classId, int roomId, ClassType classType, int disciplineId, int timeSlotId, Integer groupId, Semiyear semiyear, int teacherId) {
        assert classId > 0 : "classId should be greater than 0";
        assert disciplineId > 0  :  "disciplineId should be greater than 0";
        assert timeSlotId > 0  :  "timeSlotId should be greater than 0";
        assert teacherId > 0  :  "teacherId should be greater than 0";
        assert groupId > 0  :  "groupId should be greater than 0";
        assert roomId > 0  :  "roomId should be greater than 0";
        assert semiyear != null  :  "semiyear shouldn't be null";
        assert classType != null  :  "classType shouldn't be null";

        this.classId = classId;
        this.roomId = roomId;
        this.classType = classType;
        this.disciplineId = disciplineId;
        this.timeSlotId = timeSlotId;
        this.groupId = groupId;
        this.semiyear = semiyear;
        this.teacherId = teacherId;
    }

    public int getClassId() {
        assertCurrentState();
        return classId;
    }

    public void setClassId(int classId) {
        assert classId > 0 : "classId should be greater than 0";
        this.classId = classId;
    }

    public int getDisciplineId() {
        assertCurrentState();
        return disciplineId;
    }

    public void setDisciplineId(int disciplineId) {
        assert disciplineId > 0  :  "disciplineId should be greater than 0";
        this.disciplineId = disciplineId;
    }

    public ClassType getClassType() {
        assertCurrentState();
        return classType;
    }

    public void setClassType(ClassType classType) {
        assert classType != null  :  "classType shouldn't be null";
        this.classType = classType;
    }

    public int getRoomId() {
        assertCurrentState();
        return roomId;
    }

    public void setRoomId(int roomId) {
        assert roomId > 0  :  "roomId should be greater than 0";
        this.roomId = roomId;
    }

    public int getTimeSlotId() {
        assertCurrentState();
        return timeSlotId;
    }

    public void setTimeSlotId(int timeSlotId) {
        assert timeSlotId > 0  :  "timeSlotId should be greater than 0";
        this.timeSlotId = timeSlotId;
    }

    public int getTeacherId() {
        assertCurrentState();
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        assert teacherId > 0  :  "teacherId should be greater than 0";
        this.teacherId = teacherId;
    }

    public Semiyear getSemiyear() {
        assertCurrentState();
        return semiyear;
    }

    public void setSemiyear(Semiyear semiyear) {
        assert semiyear != null  :  "semiyear shouldn't be null";
        this.semiyear = semiyear;
    }

    public Integer getGroupId() {
        assertCurrentState();
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void assertCurrentState(){
        assert classId > 0 : "classId should be greater than 0";
        assert disciplineId > 0  :  "disciplineId should be greater than 0";
        assert timeSlotId > 0  :  "timeSlotId should be greater than 0";
        assert teacherId > 0  :  "teacherId should be greater than 0";
        assert groupId > 0  :  "groupId should be greater than 0";
        assert roomId > 0  :  "roomId should be greater than 0";
        assert semiyear != null  :  "semiyear shouldn't be null";
        assert classType != null  :  "classType shouldn't be null";
    }
}

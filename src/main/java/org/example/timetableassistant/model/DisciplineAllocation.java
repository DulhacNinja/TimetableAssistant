package org.example.timetableassistant.model;

import org.example.timetableassistant.database.crud.ClassType;

public class DisciplineAllocation {
    private int id;
    private Discipline discipline;
    private Teacher teacher;
    private ClassType classType;

    public DisciplineAllocation(int id, Discipline discipline, Teacher teacher, ClassType classType) {
        assert id > 0 : "id must be greater than 0";
        assert discipline != null : "discipline must not be null";
        assert teacher != null : "teacher must not be null";
        assert classType != null : "classType must not be null";

        this.id = id;
        this.discipline = discipline;
        this.teacher = teacher;
        this.classType = classType;
    }

    public int getId() {
        assertCurrentState();
        return id;}
    public Discipline getDiscipline() {
        assertCurrentState();
        return discipline;}
    public Teacher getTeacher() {
        assertCurrentState();
        return teacher;}
    public ClassType getClassType() {
        assertCurrentState();
        return classType;}

    public void setId(int id) {
        assert id > 0 : "id must be greater than 0";
        this.id = id;}
    public void setDiscipline(Discipline discipline) {
        assert discipline != null : "discipline must not be null";
        this.discipline = discipline;}
    public void setTeacher(Teacher teacher) {
        assert teacher != null : "teacher must not be null";
        this.teacher = teacher;}
    public void setClassType(ClassType classType) {
        assert classType != null : "classType must not be null";
        this.classType = classType;}

    public void assertCurrentState(){
        assert id > 0 : "id must be greater than 0";
        assert discipline != null : "discipline must not be null";
        assert teacher != null : "teacher must not be null";
        assert classType != null : "classType must not be null";
    }
}

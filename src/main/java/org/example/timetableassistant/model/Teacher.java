package org.example.timetableassistant.model;

import org.example.timetableassistant.database.crud.ClassType;
import org.example.timetableassistant.service.DisciplineAllocationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teacher {
    private int id;
    private String name;

    public Teacher(int id, String name) {
        assert id > 0 : "id should be greater than 0";
        assert name != null && !name.isEmpty() : "name should not be empty";

        this.id = id;
        this.name = name;
    }

    public int getId() {
        assertCurrentState();
        return id; }
    public String getName() {
        assertCurrentState();
        return name; }

    public void setId(int id) {
        assert id > 0 : "id must be greater than 0";
        this.id = id; }
    public void setName(String name) {
        assert name != null && !name.isEmpty() : "name should not be null nor empty";
        this.name = name; }

    public Map<String, List<ClassType>> getDisciplines() throws Exception {
        assertCurrentState();
        Map<String, List<ClassType>> disciplines = new HashMap<>();
        try {
            List<DisciplineAllocation> allocations = DisciplineAllocationService.getByTeacherId(this.id);
            allocations.forEach(disciplineAllocation -> {
                disciplines.computeIfAbsent(disciplineAllocation.getDiscipline().getName(), k -> new ArrayList<>())
                        .add(disciplineAllocation.getClassType());
            });
        } catch (Exception e) {
            return disciplines;
        }
        return disciplines;
    }

    @Override
    public String toString() {
        assertCurrentState();
        return this.getName(); // or whatever method returns the teacher's name
    }

    public void assertCurrentState(){
        assert this.id > 0 : "id should be greater than 0";
        assert this.name != null && !this.name.isEmpty() : "name should not be empty";
    }
}

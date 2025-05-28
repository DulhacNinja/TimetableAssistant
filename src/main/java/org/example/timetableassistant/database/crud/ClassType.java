package org.example.timetableassistant.database.crud;

public enum ClassType {
    COURSE(1),
    LABORATORY(2),
    SEMINAR(3);

    private final int value;

    ClassType(int value) {
        assert value >= 1 && value <= 3;
        this.value = value;
    }

    public int getValue() {
        assert value >= 1 && value <= 3;
        return value;
    }

    public static ClassType fromInt(int value) {
        assert value >= 1 && value <= 3;
        for (ClassType type : ClassType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ClassType value: " + value);
    }
}

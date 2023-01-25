package com.hristoforov.elective.entities.enums;

public enum Status {
    BLOCKED("blocked"),
    UNBLOCKED("unblocked");

    Status(String status) {
    }

    public static Status checkStatus(String status){
        for (Status s :
                Status.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        return null;
    }
}

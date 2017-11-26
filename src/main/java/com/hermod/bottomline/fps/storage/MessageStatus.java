package com.hermod.bottomline.fps.storage;

public enum MessageStatus {
    PENDING("pending"),
    COMPLETED("completed");

    private final String name;

    public String getName() {
        return name;
    }

    MessageStatus(String name) {
        this.name = name;
    }
}
